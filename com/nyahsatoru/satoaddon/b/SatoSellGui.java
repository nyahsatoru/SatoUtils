package com.nyahsatoru.satoaddon.b;

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
   private static int state;
   private static int age;
   private static int noMatchTicks;
   private static int retryCount;
   private static int movedStacks;
   private static int pendingSlotIndex = -1;
   private static int pendingBeforeCount = -1;
   private static int pendingBeforeTotal = -1;
   private static String pendingTarget = "";
   private static String lastError = "";
   private static boolean commandSent;
   private static final int OPEN_DELAY_TICKS = 1;
   private static final int MAX_AGE_TICKS = 800;
   private static final int MAX_CLICK_RETRIES = 5;
   private static final int VERIFY_WAIT_TICKS = 1;
   private static int verifyWait;

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

         resetRuntime();
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
         resetTransientOnly();
         closeScreenSafe();
      }
   }

   public static void tick() {
      synchronized(LOCK) {
         if (state != 0) {
            Object var1 = minecraftClient();
            if (var1 != null) {
               Object var2;
               try {
                  var2 = player(var1);
               } catch (Throwable var13) {
                  finishFailure("Player unavailable: " + message(var13));
                  return;
               }

               if (var2 == null) {
                  finishFailure("Player unavailable.");
               } else if (++age > 800) {
                  finishFailure("Sell GUI timed out.");
               } else {
                  try {
                     if (state == 1) {
                        if (age < 2) {
                           return;
                        }

                        if (!commandSent) {
                           if (!sendSellCommand(var1)) {
                              return;
                           }

                           commandSent = true;
                           age = 0;
                        }

                        state = 2;
                        return;
                     }

                     Object var3 = currentScreen(var1);
                     if (state == 2) {
                        if (var3 == null) {
                           return;
                        }

                        Object var4 = screenHandler(var3);
                        List var5 = var4 == null ? null : slots(var4);
                        if (var4 == null || var5 == null || var5.size() <= 36) {
                           return;
                        }

                        state = 3;
                        age = 0;
                        noMatchTicks = 0;
                        verifyWait = 0;
                        retryCount = 0;
                     }

                     if (state != 3) {
                        return;
                     }

                     Object var16 = screenHandler(var3);
                     List var17 = var16 == null ? null : slots(var16);
                     if (var16 == null || var17 == null || var17.size() <= 36) {
                        return;
                     }

                     if (verifyWait > 0) {
                        --verifyWait;
                        if (verifyPendingResult(var2, var17)) {
                           ++movedStacks;
                           pendingSlotIndex = -1;
                           pendingTarget = "";
                           retryCount = 0;
                           noMatchTicks = 0;
                        }

                        if (pendingSlotIndex >= 0 && verifyWait == 0) {
                           ++retryCount;
                           if (retryCount > 5) {
                              finishFailure("Sell click was rejected for target " + pendingTarget + ".");
                              return;
                           }
                        } else if (pendingSlotIndex >= 0) {
                           return;
                        }
                     }

                     int var6 = findMatchingPlayerSlot(var2, var17);
                     if (var6 < 0) {
                        ++noMatchTicks;
                        if (noMatchTicks >= 4) {
                           if (movedStacks <= 0 && hasAnyTarget(var2)) {
                              finishFailure("No selected sell target was found in the player inventory: " + String.valueOf(TARGETS));
                           } else {
                              finishSuccess();
                           }

                           return;
                        }

                        return;
                     }

                     Object var7 = var17.get(var6);
                     Object var8 = var7 == null ? null : invokeNoArg(var7, "method_7677");
                     int var9 = stackCount(var8);
                     String var10 = itemId(var8);
                     int var11 = countTargetInPlayerInventory(var2, var10);
                     if (clickQuickMove(var1, var16, var6, var2)) {
                        pendingSlotIndex = var6;
                        pendingBeforeCount = var9;
                        pendingBeforeTotal = var11;
                        pendingTarget = var10;
                        verifyWait = 1;
                        noMatchTicks = 0;
                        age = 0;
                     } else {
                        ++retryCount;
                        if (retryCount > 5) {
                           finishFailure("Could not submit a SellGUI quick-move click.");
                        }
                     }
                  } catch (Throwable var14) {
                     String var10000 = var14.getClass().getSimpleName();
                     finishFailure(var10000 + ": " + message(var14));
                     return;
                  }

               }
            }
         }
      }
   }

   private static boolean verifyPendingResult(Object var0, List<?> var1) throws Exception {
      if (pendingSlotIndex < 0) {
         return true;
      } else {
         int var2 = countTargetInPlayerInventory(var0, pendingTarget);
         if (var2 < 0) {
            return false;
         } else if (pendingSlotIndex >= var1.size()) {
            return true;
         } else {
            Object var3 = var1.get(pendingSlotIndex);
            Object var4 = var3 == null ? null : invokeNoArg(var3, "method_7677");
            String var5 = itemId(var4);
            int var6 = stackCount(var4);
            if (var6 < pendingBeforeCount) {
               return true;
            } else if (!pendingTarget.isEmpty() && !pendingTarget.equals(var5)) {
               return true;
            } else {
               return pendingBeforeTotal >= 0 && var2 < pendingBeforeTotal;
            }
         }
      }
   }

   private static int findMatchingPlayerSlot(Object var0, List<?> var1) throws Exception {
      Object var2 = invokeNoArg(var0, "method_31548");
      int var3 = Math.max(0, var1.size() - 36);

      for(int var4 = 0; var4 < var1.size(); ++var4) {
         Object var5 = var1.get(var4);
         if (var5 != null) {
            Object var6 = invokeNoArg(var5, "method_7677");
            if (!isEmptyStack(var6) && (isPlayerInventorySlot(var5, var2) || var4 >= var3)) {
               String var7 = itemId(var6);
               if (isTarget(var7)) {
                  return var4;
               }
            }
         }
      }

      return -1;
   }

   private static boolean isPlayerInventorySlot(Object var0, Object var1) {
      if (var0 != null && var1 != null) {
         try {
            Field[] var2 = allFields(var0.getClass());

            for(Field var6 : var2) {
               try {
                  if (var6.getType().isAssignableFrom(var1.getClass()) || var1.getClass().isAssignableFrom(var6.getType())) {
                     var6.setAccessible(true);
                     if (var6.get(var0) == var1) {
                        return true;
                     }
                  }
               } catch (Throwable var8) {
               }
            }
         } catch (Throwable var9) {
         }

         return false;
      } else {
         return false;
      }
   }

   private static boolean hasAnyTarget(Object var0) throws Exception {
      Object var1 = invokeNoArg(var0, "method_31548");
      if (var1 == null) {
         return false;
      } else {
         for(int var2 = 0; var2 < 36; ++var2) {
            Object var3 = invokeMethod(var1, "method_5438", Integer.TYPE, var2);
            if (!isEmptyStack(var3) && isTarget(itemId(var3))) {
               return true;
            }
         }

         return false;
      }
   }

   private static int countTargetInPlayerInventory(Object var0, String var1) throws Exception {
      if (var1 != null && !var1.isEmpty()) {
         Object var2 = invokeNoArg(var0, "method_31548");
         if (var2 == null) {
            return -1;
         } else {
            int var3 = 0;

            for(int var4 = 0; var4 < 36; ++var4) {
               Object var5 = invokeMethod(var2, "method_5438", Integer.TYPE, var4);
               if (!isEmptyStack(var5) && var1.equals(itemId(var5))) {
                  var3 += stackCount(var5);
               }
            }

            return var3;
         }
      } else {
         return -1;
      }
   }

   private static boolean clickQuickMove(Object var0, Object var1, int var2, Object var3) throws Exception {
      Object var4 = findFieldObject(var0, "field_1755");
      var4 = findFieldObject(var0, "field_1762");
      if (var4 == null) {
         var4 = findFieldByTypeName(var0, "net.minecraft.class_635");
      }

      if (var4 == null) {
         return false;
      } else {
         Class var5 = Class.forName("net.minecraft.class_1713");
         Object var6 = enumConstant(var5, "QUICK_MOVE");
         if (var6 == null) {
            return false;
         } else {
            int var7 = intField(var1, "field_7763", firstIntField(var1, 0));
            Class var8 = Class.forName("net.minecraft.class_1657");
            Method var9 = findMethod(var4.getClass(), "method_2906", 5, Integer.TYPE, Integer.TYPE, Integer.TYPE, var5, var8);
            if (var9 == null) {
               var9 = findClickSlotMethod(var4.getClass(), var5, var3);
            }

            if (var9 == null) {
               return false;
            } else {
               var9.setAccessible(true);
               var9.invoke(var4, var7, var2, 0, var6, var3);
               return true;
            }
         }
      }
   }

   private static Method findClickSlotMethod(Class<?> var0, Class<?> var1, Object var2) {
      for(Method var6 : allMethods(var0)) {
         try {
            Class[] var7 = var6.getParameterTypes();
            if (var7.length == 5 && var7[0] == Integer.TYPE && var7[1] == Integer.TYPE && var7[2] == Integer.TYPE && var7[3].isEnum() && var7[3].isAssignableFrom(var1) && var7[4].isAssignableFrom(var2.getClass())) {
               return var6;
            }
         } catch (Throwable var8) {
         }
      }

      return null;
   }

   private static boolean sendSellCommand(Object var0) throws Exception {
      Object var1 = findFieldObject(var0, "field_3944");
      if (var1 == null) {
         return false;
      } else {
         for(String var3 : List.of("method_45730", "sendChatCommand", "sendCommand")) {
            Method var4 = findMethod(var1.getClass(), var3, 1, String.class);
            if (var4 != null) {
               var4.setAccessible(true);
               var4.invoke(var1, "sellgui");
               return true;
            }
         }

         return false;
      }
   }

   private static String itemId(Object var0) throws Exception {
      if (var0 == null) {
         return "";
      } else {
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
               return var5 == null ? "" : String.valueOf(var5).toLowerCase(Locale.ROOT);
            }
         }
      }
   }

   private static int stackCount(Object var0) throws Exception {
      if (var0 != null && !isEmptyStack(var0)) {
         for(String var2 : List.of("method_4942", "method_7947")) {
            try {
               Object var3 = invokeNoArg(var0, var2);
               if (var3 instanceof Number) {
                  Number var4 = (Number)var3;
                  return var4.intValue();
               }
            } catch (Throwable var5) {
            }
         }

         return 1;
      } else {
         return 0;
      }
   }

   private static boolean isEmptyStack(Object var0) throws Exception {
      if (var0 == null) {
         return true;
      } else {
         try {
            return Boolean.TRUE.equals(invokeNoArg(var0, "method_7960"));
         } catch (Throwable var2) {
            return false;
         }
      }
   }

   private static Object minecraftClient() {
      try {
         Class var0 = Class.forName("net.minecraft.class_310");
         Method var1 = findMethod(var0, "method_1551", 0);
         return var1 == null ? null : var1.invoke((Object)null);
      } catch (Throwable var2) {
         return null;
      }
   }

   private static Object player(Object var0) throws Exception {
      Object var1 = findFieldObject(var0, "field_1724");
      return var1;
   }

   private static Object currentScreen(Object var0) throws Exception {
      return findFieldObject(var0, "field_1755");
   }

   private static Object screenHandler(Object var0) {
      try {
         Object var1 = findFieldObject(var0, "field_1735");
         if (var1 != null) {
            return var1;
         }
      } catch (Throwable var2) {
      }

      return null;
   }

   private static List<?> slots(Object var0) {
      try {
         Object var1 = findFieldObject(var0, "field_7761");
         List var10000;
         if (var1 instanceof List var2) {
            var10000 = var2;
         } else {
            var10000 = null;
         }

         return var10000;
      } catch (Throwable var3) {
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

         Method var2 = findMethod(var1.getClass(), "method_7346", 0);
         if (var2 != null) {
            var2.setAccessible(true);
            var2.invoke(var1);
         }
      } catch (Throwable var3) {
      }

   }

   private static boolean isTarget(String var0) {
      if (var0 != null && !var0.isEmpty()) {
         String var1 = var0.toLowerCase(Locale.ROOT);
         return TARGETS.contains(var1);
      } else {
         return false;
      }
   }

   private static void resetRuntime() {
      state = 0;
      resetTransientOnly();
      lastError = "";
   }

   private static void resetTransientOnly() {
      age = 0;
      noMatchTicks = 0;
      retryCount = 0;
      movedStacks = 0;
      pendingSlotIndex = -1;
      pendingBeforeCount = -1;
      pendingBeforeTotal = -1;
      pendingTarget = "";
      verifyWait = 0;
      commandSent = false;
   }

   private static void finishSuccess() {
      state = 0;
      lastError = "";
      resetTransientOnly();
      closeScreenSafe();
   }

   private static void finishFailure(String var0) {
      state = 0;
      lastError = var0 != null && !var0.isBlank() ? var0 : "Unknown error";
      resetTransientOnly();
      closeScreenSafe();
   }

   private static Object invokeNoArg(Object var0, String var1) throws Exception {
      if (var0 == null) {
         return null;
      } else {
         Method var2 = findMethod(var0.getClass(), var1, 0);
         if (var2 == null) {
            throw new NoSuchMethodException(var1);
         } else {
            var2.setAccessible(true);
            return var2.invoke(var0);
         }
      }
   }

   private static Object invokeMethod(Object var0, String var1, Class<?> var2, Object var3) throws Exception {
      if (var0 == null) {
         return null;
      } else {
         Method var4 = findMethod(var0.getClass(), var1, 1, var2);
         if (var4 == null) {
            throw new NoSuchMethodException(var1);
         } else {
            var4.setAccessible(true);
            return var4.invoke(var0, var3);
         }
      }
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

   private static Object findFieldByTypeName(Object var0, String var1) {
      if (var0 == null) {
         return null;
      } else {
         for(Field var5 : allFields(var0.getClass())) {
            try {
               if (var5.getType().getName().equals(var1)) {
                  var5.setAccessible(true);
                  return var5.get(var0);
               }
            } catch (Throwable var7) {
            }
         }

         return null;
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

   private static Method findMethod(Class<?> var0, String var1, int var2, Class<?>... var3) {
      for(Class var4 = var0; var4 != null; var4 = var4.getSuperclass()) {
         for(Method var8 : var4.getDeclaredMethods()) {
            if (var8.getName().equals(var1) && var8.getParameterCount() == var2) {
               if (var3.length != var2) {
                  return var8;
               }

               Class[] var9 = var8.getParameterTypes();
               boolean var10 = true;

               for(int var11 = 0; var11 < var2; ++var11) {
                  if (var9[var11] != var3[var11] && !var9[var11].isAssignableFrom(var3[var11]) && !var3[var11].isAssignableFrom(var9[var11])) {
                     var10 = false;
                     break;
                  }
               }

               if (var10) {
                  return var8;
               }
            }
         }
      }

      return null;
   }

   private static Method[] allMethods(Class<?> var0) {
      ArrayList var1 = new ArrayList();

      for(Class var2 = var0; var2 != null; var2 = var2.getSuperclass()) {
         Collections.addAll(var1, var2.getDeclaredMethods());
      }

      return (Method[])var1.toArray((var0x) -> new Method[var0x]);
   }

   private static Field[] allFields(Class<?> var0) {
      ArrayList var1 = new ArrayList();

      for(Class var2 = var0; var2 != null; var2 = var2.getSuperclass()) {
         Collections.addAll(var1, var2.getDeclaredFields());
      }

      return (Field[])var1.toArray((var0x) -> new Field[var0x]);
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

   private static Object enumConstant(Class<?> var0, String var1) {
      try {
         Enum var2 = Enum.valueOf(var0.asSubclass(Enum.class), var1);
         return var2;
      } catch (Throwable var3) {
         return null;
      }
   }

   private static String message(Throwable var0) {
      Throwable var1;
      for(var1 = var0; var1.getCause() != null && var1.getCause() != var1; var1 = var1.getCause()) {
      }

      String var2 = var1.getMessage();
      return var2 != null && !var2.isBlank() ? var2 : var1.getClass().getSimpleName();
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
         Path var0 = Paths.get("config", "satoutils-sell-targets.txt");
         return !Files.exists(var0, new LinkOption[0]) ? List.of() : normalize(Files.readAllLines(var0));
      } catch (Throwable var1) {
         return List.of();
      }
   }

   private static void saveTargets() {
      try {
         Path var0 = Paths.get("config", "satoutils-sell-targets.txt");
         Path var1 = var0.getParent();
         if (var1 != null) {
            Files.createDirectories(var1);
         }

         Files.write(var0, TARGETS);
      } catch (Throwable var2) {
      }

   }
}
