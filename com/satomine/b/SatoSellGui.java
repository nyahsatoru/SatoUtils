package com.satomine.b;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;

public final class SatoSellGui {
   private static final Object LOCK = new Object();
   private static final List<String> TARGETS = new ArrayList();
   private static final Path CONFIG = Paths.get("config", "satoutils-sell-targets.txt");
   private static int state;
   private static int age;
   private static int noMatchTicks;
   private static int clickCooldown;
   private static int movedStacks;
   private static String lastError = "";
   private static boolean commandSent;

   private SatoSellGui() {
   }

   public static void start(List<String> var0) {
      synchronized(LOCK) {
         List var2 = normalize(var0);
         if (!var2.isEmpty()) {
            TARGETS.clear();
            TARGETS.addAll(var2);
            saveTargets();
         } else if (TARGETS.isEmpty()) {
            TARGETS.addAll(loadTargets());
         }

         movedStacks = 0;
         clickCooldown = 0;
         noMatchTicks = 0;
         age = 0;
         lastError = "";
         commandSent = false;
         if (TARGETS.isEmpty()) {
            state = 0;
            lastError = "No sell targets configured.";
         } else {
            state = 1;
         }
      }
   }

   public static boolean isRunning() {
      synchronized(LOCK) {
         return state != 0;
      }
   }

   public static boolean isDone() {
      synchronized(LOCK) {
         return state == 0;
      }
   }

   public static String status() {
      synchronized(LOCK) {
         if (state != 0) {
            return "RUNNING";
         } else {
            return lastError.isEmpty() ? "SUCCEEDED" : "FAILED";
         }
      }
   }

   public static String lastError() {
      synchronized(LOCK) {
         return lastError;
      }
   }

   public static List<String> targets() {
      synchronized(LOCK) {
         return new ArrayList(TARGETS);
      }
   }

   public static void cancel() {
      synchronized(LOCK) {
         state = 0;
         age = 0;
         clickCooldown = 0;
         noMatchTicks = 0;
         commandSent = false;
         closeScreenSafe();
      }
   }

   public static void tick() {
      synchronized(LOCK) {
         if (state != 0) {
            try {
               Object var1 = minecraftClient();
               if (var1 == null) {
                  return;
               }

               Object var2 = player(var1);
               if (var2 == null) {
                  finishFailure("Player unavailable.");
                  return;
               }

               if (++age > 800) {
                  finishFailure("Sell GUI timed out.");
                  return;
               }

               Object var3 = currentScreen(var1);
               if (state == 1) {
                  if (age < 2) {
                     return;
                  }

                  if (!commandSent) {
                     sendSellCommand(var2);
                     commandSent = true;
                     age = 0;
                  }

                  state = 2;
                  return;
               }

               if (state == 2) {
                  if (var3 == null) {
                     return;
                  }

                  Object var10 = screenHandler(var3);
                  List var11 = var10 == null ? null : slots(var10);
                  if (var10 == null || var11 == null || var11.size() <= 36) {
                     return;
                  }

                  state = 3;
                  age = 0;
                  clickCooldown = 2;
                  noMatchTicks = 0;
                  return;
               }

               if (state == 3) {
                  if (var3 == null) {
                     finishSuccess();
                     return;
                  }

                  Object var4 = screenHandler(var3);
                  List var5 = var4 == null ? null : slots(var4);
                  if (var4 == null || var5 == null || var5.size() <= 36) {
                     return;
                  }

                  if (clickCooldown > 0) {
                     --clickCooldown;
                     return;
                  }

                  int var6 = findMatchingPlayerSlot(var5);
                  if (var6 >= 0 && clickQuickMove(var1, var4, var6, var2)) {
                     ++movedStacks;
                     clickCooldown = 2;
                     noMatchTicks = 0;
                     age = 0;
                     return;
                  }

                  ++noMatchTicks;
                  if (noMatchTicks >= 8) {
                     if (movedStacks > 0) {
                        finishSuccess();
                     } else {
                        finishFailure("Sell GUI opened, but no configured item could be moved. Targets=" + String.valueOf(TARGETS));
                     }
                  }
               }
            } catch (Throwable var8) {
               String var10000 = var8.getClass().getSimpleName();
               finishFailure(var10000 + ": " + String.valueOf(var8.getMessage()));
            }

         }
      }
   }

   private static int findMatchingPlayerSlot(List<?> var0) throws Exception {
      int var1 = Math.max(0, var0.size() - 36);

      for(int var2 = var1; var2 < var0.size(); ++var2) {
         Object var3 = var0.get(var2);
         if (var3 != null) {
            Object var4 = invokeNoArg(var3, "method_7677");
            if (var4 != null && !isEmptyStack(var4)) {
               String var5 = itemId(var4);
               if (isTarget(var5)) {
                  return var2;
               }
            }
         }
      }

      return -1;
   }

   private static boolean clickQuickMove(Object var0, Object var1, int var2, Object var3) throws Exception {
      Object var4 = findFieldObject(var0, "field_1761");
      if (var4 == null) {
         return false;
      } else {
         Class var5 = Class.forName("net.minecraft.class_1713");
         Object var6 = enumConstant(var5, "QUICK_MOVE");
         if (var6 == null) {
            return false;
         } else {
            int var7 = intField(var1, "field_7763", firstIntField(var1, 0));

            try {
               Method var16 = var4.getClass().getMethod("method_2906", Integer.TYPE, Integer.TYPE, Integer.TYPE, var5, Class.forName("net.minecraft.class_1657"));
               var16.setAccessible(true);
               var16.invoke(var4, var7, var2, 0, var6, var3);
               return true;
            } catch (Throwable var15) {
               for(Method var11 : var4.getClass().getMethods()) {
                  Class[] var12 = var11.getParameterTypes();
                  if (var12.length == 5 && var12[0] == Integer.TYPE && var12[1] == Integer.TYPE && var12[2] == Integer.TYPE && var12[3].isEnum() && var12[3].isAssignableFrom(var5) && var12[4].isAssignableFrom(var3.getClass())) {
                     try {
                        var11.setAccessible(true);
                        var11.invoke(var4, var7, var2, 0, var6, var3);
                        return true;
                     } catch (Throwable var14) {
                     }
                  }
               }

               return false;
            }
         }
      }
   }

   private static boolean isTarget(String var0) {
      if (var0 != null && !var0.isEmpty()) {
         String var1 = var0.toLowerCase(Locale.ROOT);

         for(String var3 : TARGETS) {
            if (var1.equals(var3)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private static String itemId(Object var0) throws Exception {
      Object var1 = invokeNoArg(var0, "method_7909");
      if (var1 == null) {
         return "";
      } else {
         Class var2 = Class.forName("net.minecraft.class_7923");
         Object var3 = findStaticField(var2, "field_41178");
         if (var3 == null) {
            return "";
         } else {
            Method var4 = var3.getClass().getMethod("method_10221", Object.class);
            Object var5 = var4.invoke(var3, var1);
            return String.valueOf(var5).toLowerCase(Locale.ROOT);
         }
      }
   }

   private static boolean isEmptyStack(Object var0) throws Exception {
      Object var1 = invokeNoArg(var0, "method_7960");
      return Boolean.TRUE.equals(var1);
   }

   private static Object minecraftClient() {
      try {
         Class var0 = Class.forName("net.minecraft.class_310");
         return var0.getMethod("method_1551").invoke((Object)null);
      } catch (Throwable var1) {
         return null;
      }
   }

   private static Object player(Object var0) throws Exception {
      Object var1 = findFieldObject(var0, "field_1724");
      return var1 != null ? var1 : null;
   }

   private static Object currentScreen(Object var0) throws Exception {
      return findFieldObject(var0, "field_1755");
   }

   private static Object screenHandler(Object var0) {
      try {
         return findFieldObject(var0, "field_1735");
      } catch (Throwable var2) {
         return null;
      }
   }

   private static List<?> slots(Object var0) {
      try {
         Object var1 = findFieldObject(var0, "field_7761");
         return var1 instanceof List ? (List)var1 : null;
      } catch (Throwable var2) {
         return null;
      }
   }

   private static void closeScreenSafe() {
      try {
         Object var0 = minecraftClient();
         Object var1 = var0 == null ? null : player(var0);
         if (var1 == null) {
            return;
         }

         Method var2 = var1.getClass().getMethod("method_7346");
         var2.setAccessible(true);
         var2.invoke(var1);
      } catch (Throwable var3) {
      }

   }

   private static void sendSellCommand(Object var0) throws Exception {
      Object var1 = findFieldObject(var0, "field_3944");
      if (var1 != null) {
         Method var2 = var1.getClass().getMethod("method_45730", String.class);
         var2.setAccessible(true);
         var2.invoke(var1, "sell");
      }
   }

   private static Object invokeNoArg(Object var0, String var1) throws Exception {
      Method var2 = var0.getClass().getMethod(var1);
      var2.setAccessible(true);
      return var2.invoke(var0);
   }

   private static Object findFieldObject(Object var0, String var1) throws Exception {
      if (var0 == null) {
         return null;
      } else {
         Field var2 = findField(var0.getClass(), var1);
         if (var2 == null) {
            return null;
         } else {
            var2.setAccessible(true);
            return var2.get(var0);
         }
      }
   }

   private static Object findStaticField(Class<?> var0, String var1) throws Exception {
      Field var2 = findField(var0, var1);
      if (var2 == null) {
         return null;
      } else {
         var2.setAccessible(true);
         return var2.get((Object)null);
      }
   }

   private static Field findField(Class<?> var0, String var1) {
      for(Class var2 = var0; var2 != null; var2 = var2.getSuperclass()) {
         try {
            return var2.getDeclaredField(var1);
         }
      }

      return null;
   }

   private static int intField(Object var0, String var1, int var2) {
      try {
         Field var3 = findField(var0.getClass(), var1);
         if (var3 == null) {
            return var2;
         } else {
            var3.setAccessible(true);
            return var3.getInt(var0);
         }
      } catch (Throwable var4) {
         return var2;
      }
   }

   private static int firstIntField(Object var0, int var1) {
      for(Field var5 : allFields(var0.getClass())) {
         try {
            if (var5.getType() == Integer.TYPE) {
               var5.setAccessible(true);
               return var5.getInt(var0);
            }
         } catch (Throwable var7) {
         }
      }

      return var1;
   }

   private static Field[] allFields(Class<?> var0) {
      ArrayList var1 = new ArrayList();

      for(Class var2 = var0; var2 != null; var2 = var2.getSuperclass()) {
         Collections.addAll(var1, var2.getDeclaredFields());
      }

      return (Field[])var1.toArray(new Field[0]);
   }

   private static Object enumConstant(Class<?> var0, String var1) {
      try {
         return Enum.valueOf(var0, var1);
      } catch (Throwable var3) {
         return null;
      }
   }

   private static List<String> normalize(Collection<String> var0) {
      LinkedHashSet var1 = new LinkedHashSet();
      if (var0 != null) {
         for(String var3 : var0) {
            if (var3 != null && !var3.isBlank()) {
               String var4 = var3.trim().toLowerCase(Locale.ROOT);
               if (!var4.contains(":")) {
                  var4 = "minecraft:" + var4;
               }

               var1.add(var4);
            }
         }
      }

      return new ArrayList(var1);
   }

   private static List<String> loadTargets() {
      try {
         return !Files.exists(CONFIG, new LinkOption[0]) ? List.of() : normalize(Files.readAllLines(CONFIG));
      } catch (Throwable var1) {
         return List.of();
      }
   }

   private static void saveTargets() {
      try {
         Path var0 = CONFIG.getParent();
         if (var0 != null) {
            Files.createDirectories(var0);
         }

         Files.write(CONFIG, TARGETS);
      } catch (Throwable var1) {
      }

   }

   private static void finishSuccess() {
      state = 0;
      lastError = "";
      age = 0;
      noMatchTicks = 0;
      clickCooldown = 0;
      commandSent = false;
      closeScreenSafe();
   }

   private static void finishFailure(String var0) {
      state = 0;
      lastError = var0 == null ? "Unknown error" : var0;
      age = 0;
      noMatchTicks = 0;
      clickCooldown = 0;
      commandSent = false;
      closeScreenSafe();
   }
}
