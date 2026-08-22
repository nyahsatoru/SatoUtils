package com.nyahsatoru.satoaddon.persistence;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public final class SatoAddonConfig {
   private static final AtomicBoolean READY = new AtomicBoolean();
   private static final AtomicBoolean DIRTY = new AtomicBoolean();
   private static volatile String lastDigest = "";
   private static Path path;

   private SatoAddonConfig() {
   }

   public static void init() {
      if (READY.compareAndSet(false, true)) {
         try {
            path = findPath();
            load();
            registerTickHook();
         } catch (Throwable var1) {
         }

      }
   }

   public static void flush() {
      try {
         saveIfChanged();
      } catch (Throwable var1) {
      }

   }

   private static Path findPath() {
      Path root = null;

      try {
         Class<?> mc = Class.forName("net.minecraft.class_310");
         Object client = mc.getMethod("method_1551").invoke((Object)null);
         Field f = mc.getField("field_1697");
         Object file = f.get(client);
         if (file instanceof File) {
            root = ((File)file).toPath();
         }
      } catch (Throwable var5) {
      }

      if (root == null) {
         root = Paths.get(System.getProperty("user.dir", "."));
      }

      return root.toAbsolutePath().normalize().resolve("satoaddon.json");
   }

   private static List<Object> modules() {
      List<Object> out = new ArrayList();

      try {
         Class<?> modulesCls = Class.forName("meteordevelopment.meteorclient.systems.modules.Modules");
         Object modules = modulesCls.getMethod("get").invoke((Object)null);

         for(Object m : (Collection)modulesCls.getMethod("getAll").invoke(modules)) {
            if (m.getClass().getName().startsWith("com.nyahsatoru.satoaddon.modules.")) {
               out.add(m);
            }
         }
      } catch (Throwable var5) {
      }

      return out;
   }

   private static String moduleTag(Object module) {
      try {
         return String.valueOf(module.getClass().getMethod("toTag").invoke(module));
      } catch (Throwable var2) {
         return "{}";
      }
   }

   private static void load() {
      if (Files.isRegularFile(path, new LinkOption[0])) {
         try {
            String json = Files.readString(path, StandardCharsets.UTF_8);
            int mi = json.indexOf("\"modules\"");
            List<Object> mods = modules();
            if (mi < 0) {
               int a = json.indexOf("\"autoLoginServers\"");
               if (a >= 0) {
                  int open = json.indexOf(91, a);
                  int close = json.indexOf(93, open);
                  if (open >= 0 && close > open) {
                     List<String> migrated = new ArrayList();
                     String raw = json.substring(open + 1, close);
                     boolean quoted = false;
                     boolean esc = false;
                     StringBuilder item = new StringBuilder();

                     for(int i = 0; i < raw.length(); ++i) {
                        char ch = raw.charAt(i);
                        if (esc) {
                           item.append(unescapeChar(ch));
                           esc = false;
                        } else if (ch == '\\') {
                           esc = true;
                        } else if (ch == '"') {
                           quoted = !quoted;
                        } else if (ch == ',' && !quoted) {
                           String x = item.toString().trim();
                           if (!x.isEmpty()) {
                              migrated.add(x);
                           }

                           item.setLength(0);
                        } else if (quoted) {
                           item.append(ch);
                        }
                     }

                     String x = item.toString().trim();
                     if (!x.isEmpty()) {
                        migrated.add(x);
                     }

                     for(Object m : mods) {
                        if (m.getClass().getName().endsWith(".AutoLogin")) {
                           try {
                              Field f = m.getClass().getDeclaredField("servers");
                              f.setAccessible(true);
                              Object setting = f.get(m);
                              setting.getClass().getMethod("set", Object.class).invoke(setting, migrated);
                           } catch (Throwable var19) {
                           }
                        }
                     }
                  }
               }

               return;
            }

            for(Object m : mods) {
               String key = escape(m.getClass().getName());
               int p = json.indexOf("\"" + key + "\"");
               if (p >= 0) {
                  int colon = json.indexOf(58, p);
                  int q = json.indexOf(34, colon + 1);
                  if (colon >= 0 && q >= 0) {
                     StringBuilder sb = new StringBuilder();
                     boolean esc = false;

                     for(int i = q + 1; i < json.length(); ++i) {
                        char c = json.charAt(i);
                        if (!esc && c == '"') {
                           break;
                        }

                        if (esc) {
                           sb.append(unescapeChar(c));
                           esc = false;
                        } else if (c == '\\') {
                           esc = true;
                        } else {
                           sb.append(c);
                        }
                     }

                     String snbt = sb.toString();
                     if (!snbt.isBlank()) {
                        try {
                           Class<?> reader = Class.forName("net.minecraft.class_2522");
                           Object tag = reader.getMethod("method_67315", String.class).invoke((Object)null, snbt);
                           m.getClass().getMethod("fromTag", tag.getClass().getInterfaces().length > 0 ? tag.getClass().getInterfaces()[0] : tag.getClass()).invoke(m, tag);
                        } catch (Throwable var21) {
                           try {
                              for(Method meth : m.getClass().getMethods()) {
                                 if (meth.getName().equals("fromTag") && meth.getParameterCount() == 1) {
                                    Class<?> reader = Class.forName("net.minecraft.class_2522");
                                    Object tag = reader.getMethod("method_67315", String.class).invoke((Object)null, snbt);
                                    meth.invoke(m, tag);
                                    break;
                                 }
                              }
                           } catch (Throwable var20) {
                           }
                        }
                     }
                  }
               }
            }
         } catch (Throwable var22) {
         }

      }
   }

   private static void saveIfChanged() throws Exception {
      List<Object> mods = modules();
      StringBuilder body = new StringBuilder();
      body.append("{\n  \"schema\": 2,\n  \"addon\": \"SatoAddon\",\n  \"modules\": {\n");

      for(int i = 0; i < mods.size(); ++i) {
         if (i > 0) {
            body.append(",\n");
         }

         Object m = mods.get(i);
         body.append("    \"").append(escape(m.getClass().getName())).append("\": \"").append(escape(moduleTag(m))).append("\"");
      }

      body.append("\n  }\n}\n");
      String json = body.toString();
      String digest = Integer.toHexString(json.hashCode());
      if (!digest.equals(lastDigest) || !Files.isRegularFile(path, new LinkOption[0])) {
         Files.createDirectories(path.getParent());
         Path tmp = path.resolveSibling(String.valueOf(path.getFileName()) + ".tmp");
         Files.writeString(tmp, json, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);

         try {
            Files.move(tmp, path, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
         } catch (AtomicMoveNotSupportedException var6) {
            Files.move(tmp, path, StandardCopyOption.REPLACE_EXISTING);
         }

         lastDigest = digest;
      }
   }

   private static void registerTickHook() {
      try {
         Class<?> c = Class.forName("net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents");
         Field f = c.getField("END_CLIENT_TICK");
         Object event = f.get((Object)null);
         Method register = event.getClass().getMethod("register", Class.forName("net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents$EndTick"));
         Class<?> iface = register.getParameterTypes()[0];
         Object listener = Proxy.newProxyInstance(iface.getClassLoader(), new Class[]{iface}, (p, m, a) -> {
            if ("onEndTick".equals(m.getName())) {
               saveIfChanged();
            }

            return null;
         });
         register.invoke(event, listener);
      } catch (Throwable var6) {
      }

   }

   private static String escape(String s) {
      StringBuilder b = new StringBuilder();

      for(char c : s.toCharArray()) {
         switch (c) {
            case '\t':
               b.append("\\t");
               break;
            case '\n':
               b.append("\\n");
               break;
            case '\r':
               b.append("\\r");
               break;
            case '"':
               b.append("\\\"");
               break;
            case '\\':
               b.append("\\\\");
               break;
            default:
               b.append(c);
         }
      }

      return b.toString();
   }

   private static char unescapeChar(char c) {
      char var10000;
      switch (c) {
         case 'n' -> var10000 = '\n';
         case 'r' -> var10000 = '\r';
         case 't' -> var10000 = '\t';
         default -> var10000 = c;
      }

      return var10000;
   }
}
