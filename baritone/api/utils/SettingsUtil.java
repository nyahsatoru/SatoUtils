package baritone.api.utils;

import baritone.api.BaritoneAPI;
import baritone.api.Settings;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.class_1792;
import net.minecraft.class_2248;
import net.minecraft.class_2382;
import net.minecraft.class_2415;
import net.minecraft.class_2470;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_6880;
import net.minecraft.class_7923;

public class SettingsUtil {
   public static final String SETTINGS_DEFAULT_NAME = "settings.txt";
   private static final Pattern SETTING_PATTERN = Pattern.compile("^(?<setting>[^ ]+) +(?<value>.+)");

   private static boolean isComment(String var0) {
      return var0.startsWith("#") || var0.startsWith("//");
   }

   private static void forEachLine(Path var0, Consumer<String> var1) {
      BufferedReader var5 = Files.newBufferedReader(var0);

      String var2;
      try {
         while((var2 = var5.readLine()) != null) {
            if (!var2.isEmpty() && !isComment(var2)) {
               var1.accept(var2);
            }
         }
      } catch (Throwable var4) {
         if (var5 != null) {
            try {
               var5.close();
            } catch (Throwable var3) {
               var4.addSuppressed(var3);
            }
         }

         throw var4;
      }

      if (var5 != null) {
         var5.close();
      }
   }

   public static void readAndApply(Settings var0, String var1) {
      try {
         forEachLine(settingsByName(var1), (var1x) -> {
            Matcher var2;
            if (!(var2 = SETTING_PATTERN.matcher(var1x)).matches()) {
               Helper.HELPER.logDirect("Invalid syntax in setting file: " + var1x);
            } else {
               String var3 = var2.group("setting").toLowerCase();
               String var5 = var2.group("value");
               if ("allowjumpat256".equals(var3)) {
                  var3 = "allowjumpatbuildlimit";
               }

               try {
                  parseAndApply(var0, var3, var5);
               } catch (Exception var4) {
                  Helper.HELPER.logDirect("Unable to parse line " + var1x);
                  var4.printStackTrace();
               }
            }
         });
      } catch (NoSuchFileException var2) {
         Helper.HELPER.logDirect("Baritone settings file not found, resetting.");
      } catch (Exception var3) {
         Helper.HELPER.logDirect("Exception while reading Baritone settings, some settings may be reset to default values!");
         var3.printStackTrace();
      }
   }

   public static synchronized void save(Settings var0) {
      try {
         BufferedWriter var1 = Files.newBufferedWriter(settingsByName("settings.txt"));

         try {
            for(Settings.Setting var2 : modifiedSettings(var0)) {
               var1.write(settingToString(var2) + "\n");
            }
         } catch (Throwable var4) {
            if (var1 != null) {
               try {
                  var1.close();
               } catch (Throwable var3) {
                  var4.addSuppressed(var3);
               }
            }

            throw var4;
         }

         if (var1 != null) {
            var1.close();
         }
      } catch (Exception var5) {
         Helper.HELPER.logDirect("Exception thrown while saving Baritone settings!");
         var5.printStackTrace();
      }
   }

   private static Path settingsByName(String var0) {
      return class_310.method_1551().field_1697.toPath().resolve("baritone").resolve(var0);
   }

   public static List<Settings.Setting> modifiedSettings(Settings var0) {
      ArrayList var1 = new ArrayList();
      Iterator var3 = var0.allSettings.iterator();

      while(var3.hasNext()) {
         Settings.Setting var2;
         if ((var2 = (Settings.Setting)var3.next()).value == null) {
            System.out.println("NULL SETTING?" + var2.getName());
         } else if (!var2.isJavaOnly() && var2.value != var2.defaultValue) {
            var1.add(var2);
         }
      }

      return var1;
   }

   public static String settingTypeToString(Settings.Setting var0) {
      return var0.getType().getTypeName().replaceAll("(?:\\w+\\.)+(\\w+)", "$1");
   }

   public static <T> String settingValueToString(Settings.Setting<T> var0, T var1) {
      Parser var2;
      if ((var2 = SettingsUtil.Parser.getParser(var0.getType())) == null) {
         String var10002 = String.valueOf(var0.getValueClass());
         throw new IllegalStateException("Missing " + var10002 + " " + var0.getName());
      } else {
         return var2.toString(var0.getType(), var1);
      }
   }

   public static String settingValueToString(Settings.Setting var0) {
      return settingValueToString(var0, var0.value);
   }

   public static String settingDefaultToString(Settings.Setting var0) {
      return settingValueToString(var0, var0.defaultValue);
   }

   public static String maybeCensor(int var0) {
      return (Boolean)BaritoneAPI.getSettings().censorCoordinates.value ? "<censored>" : Integer.toString(var0);
   }

   public static String settingToString(Settings.Setting var0) {
      if (var0.isJavaOnly()) {
         return var0.getName();
      } else {
         String var10000 = var0.getName();
         return var10000 + " " + settingValueToString(var0);
      }
   }

   @Deprecated
   public static boolean javaOnlySetting(Settings.Setting var0) {
      return var0.isJavaOnly();
   }

   public static void parseAndApply(Settings var0, String var1, String var2) {
      Settings.Setting var4;
      if ((var4 = (Settings.Setting)var0.byLowerName.get(var1)) == null) {
         throw new IllegalStateException("No setting by that name");
      } else {
         Class var5 = var4.getValueClass();
         Parser var3;
         Object var6 = (var3 = SettingsUtil.Parser.getParser(var4.getType())).parse(var4.getType(), var2);
         if (!var5.isInstance(var6)) {
            String var10002 = String.valueOf(var3);
            throw new IllegalStateException(var10002 + " parser returned incorrect type, expected " + String.valueOf(var5) + " got " + String.valueOf(var6) + " which is " + String.valueOf(var6.getClass()));
         } else {
            var4.value = (T)var6;
         }
      }
   }

   interface ISettingParser<T> {
      T parse(Type var1, String var2);

      String toString(Type var1, T var2);

      boolean accepts(Type var1);
   }

   static enum Parser implements ISettingParser permits null, null {
      DOUBLE(Double.class, Double::parseDouble),
      BOOLEAN(Boolean.class, Boolean::parseBoolean),
      INTEGER(Integer.class, Integer::parseInt),
      FLOAT(Float.class, Float::parseFloat),
      LONG(Long.class, Long::parseLong),
      STRING(String.class, String::new),
      MIRROR(class_2415.class, class_2415::valueOf, Enum::name),
      ROTATION(class_2470.class, class_2470::valueOf, Enum::name),
      COLOR(Color.class, (var0) -> new Color(Integer.parseInt(var0.split(",")[0]), Integer.parseInt(var0.split(",")[1]), Integer.parseInt(var0.split(",")[2])), (var0) -> {
         int var10000 = var0.getRed();
         return var10000 + "," + var0.getGreen() + "," + var0.getBlue();
      }),
      VEC3I(class_2382.class, (var0) -> new class_2382(Integer.parseInt(var0.split(",")[0]), Integer.parseInt(var0.split(",")[1]), Integer.parseInt(var0.split(",")[2])), (var0) -> {
         int var10000 = var0.method_10263();
         return var10000 + "," + var0.method_10264() + "," + var0.method_10260();
      }),
      BLOCK(class_2248.class, (var0) -> BlockUtils.stringToBlockRequired(var0.trim()), BlockUtils::blockToString),
      ITEM(class_1792.class, (var0) -> (class_1792)class_7923.field_41178.method_10223(class_2960.method_60654(var0.trim())).map(class_6880.class_6883::comp_349).orElse((Object)null), (var0) -> class_7923.field_41178.method_10221(var0).toString()),
      LIST {
         public final Object parse(Type var1, String var2) {
            Type var4;
            SettingsUtil.Parser var3 = SettingsUtil.Parser.getParser(var4 = ((ParameterizedType)var1).getActualTypeArguments()[0]);
            return Stream.of(var2.split(",")).map((var2) -> var0.parse(var1, var2)).collect(Collectors.toList());
         }

         public final String toString(Type var1, Object var2) {
            Type var4;
            SettingsUtil.Parser var3 = SettingsUtil.Parser.getParser(var4 = ((ParameterizedType)var1).getActualTypeArguments()[0]);
            return (String)((List)var2).stream().map((var2) -> var0.toString(var1, var2)).collect(Collectors.joining(","));
         }

         public final boolean accepts(Type var1) {
            return List.class.isAssignableFrom(TypeUtils.resolveBaseClass(var1));
         }

         // $FF: synthetic method
         private static String lambda$toString$1(SettingsUtil.Parser var0, Type var1, Object var2) {
            return var0.toString(var1, var2);
         }

         // $FF: synthetic method
         private static Object lambda$parse$0(SettingsUtil.Parser var0, Type var1, String var2) {
            return var0.parse(var1, var2);
         }
      },
      MAPPING {
         public final Object parse(Type var1, String var2) {
            Type var3 = ((ParameterizedType)var1).getActualTypeArguments()[0];
            var1 = ((ParameterizedType)var1).getActualTypeArguments()[1];
            SettingsUtil.Parser var4 = SettingsUtil.Parser.getParser(var3);
            SettingsUtil.Parser var5 = SettingsUtil.Parser.getParser(var1);
            return Stream.of(var2.split(",(?=[^,]*->)")).map((var0) -> var0.split("->")).collect(Collectors.toMap((var2) -> var0.parse(var1, var2[0]), (var2) -> var0.parse(var1, var2[1])));
         }

         public final String toString(Type var1, Object var2) {
            Type var3 = ((ParameterizedType)var1).getActualTypeArguments()[0];
            var1 = ((ParameterizedType)var1).getActualTypeArguments()[1];
            SettingsUtil.Parser var4 = SettingsUtil.Parser.getParser(var3);
            SettingsUtil.Parser var5 = SettingsUtil.Parser.getParser(var1);
            return (String)((Map)var2).entrySet().stream().map((var4) -> {
               String var10000 = var0.toString(var1, var4.getKey());
               return var10000 + "->" + var2.toString(var3, var4.getValue());
            }).collect(Collectors.joining(","));
         }

         public final boolean accepts(Type var1) {
            return Map.class.isAssignableFrom(TypeUtils.resolveBaseClass(var1));
         }

         // $FF: synthetic method
         private static String lambda$toString$3(SettingsUtil.Parser var0, Type var1, SettingsUtil.Parser var2, Type var3, Map.Entry var4) {
            String var10000 = var0.toString(var1, var4.getKey());
            return var10000 + "->" + var2.toString(var3, var4.getValue());
         }

         // $FF: synthetic method
         private static Object lambda$parse$2(SettingsUtil.Parser var0, Type var1, String[] var2) {
            return var0.parse(var1, var2[1]);
         }

         // $FF: synthetic method
         private static Object lambda$parse$1(SettingsUtil.Parser var0, Type var1, String[] var2) {
            return var0.parse(var1, var2[0]);
         }

         // $FF: synthetic method
         private static String[] lambda$parse$0(String var0) {
            return var0.split("->");
         }
      };

      private final Class<?> cla$$;
      private final Function<String, Object> parser;
      private final Function<Object, String> toString;

      Parser() {
         this.cla$$ = null;
         this.parser = null;
         this.toString = null;
      }

      private <T> Parser(Class<T> var3, Function<String, T> var4) {
         this(var3, var4, Object::toString);
      }

      private <T> Parser(Class<T> var3, Function<String, T> var4, Function<T, String> var5) {
         this.cla$$ = var3;
         Objects.requireNonNull(var4);
         this.parser = var4::apply;
         this.toString = (var1x) -> (String)var5.apply(var1x);
      }

      public Object parse(Type var1, String var2) {
         Object var3;
         Objects.requireNonNull(var3 = this.parser.apply(var2));
         return var3;
      }

      public String toString(Type var1, Object var2) {
         return (String)this.toString.apply(var2);
      }

      public boolean accepts(Type var1) {
         return var1 instanceof Class && this.cla$$.isAssignableFrom((Class)var1);
      }

      public static Parser getParser(Type var0) {
         return (Parser)Stream.of(values()).filter((var1) -> var1.accepts(var0)).findFirst().orElse((Object)null);
      }

      // $FF: synthetic method
      private static Parser[] $values() {
         return new Parser[]{DOUBLE, BOOLEAN, INTEGER, FLOAT, LONG, STRING, MIRROR, ROTATION, COLOR, VEC3I, BLOCK, ITEM, LIST, MAPPING};
      }
   }
}
