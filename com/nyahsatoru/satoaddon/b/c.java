package com.nyahsatoru.satoaddon.b;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;

public final class c {
   private String b = "";

   public boolean b(String runId) {
      return this.c(c._b.b, "startManagedRun", new Class[]{String.class}, runId);
   }

   public boolean b(String var1, List<String> var2) {
      SatoSellGui.start(var2);
      return true;
   }

   public _c e(_b var1, String var2) {
      return !SatoSellGui.isDone() ? c._c.i : c._c.e;
   }

   public String d(_b executor, String runId) {
      Object value = this.b(executor, "getManagedFailure", new Class[]{String.class}, runId);
      String var10000;
      if (value instanceof String text) {
         if (!text.isBlank()) {
            var10000 = text;
            return var10000;
         }
      }

      var10000 = this.b;
      return var10000;
   }

   public boolean c(_b executor, String runId) {
      return this.c(executor, "cancelManagedRun", new Class[]{String.class}, runId);
   }

   public boolean c(_b var1) {
      return SatoSellGui.isRunning();
   }

   public boolean b(_b var1, String var2) {
      SatoSellGui.cancel();
      return true;
   }

   public boolean b(_b var1) {
      return true;
   }

   public String b() {
      return this.b;
   }

   private boolean c(_b executor, String method, Class<?>[] parameterTypes, Object... args) {
      Object value = this.b(executor, method, parameterTypes, args);
      return Boolean.TRUE.equals(value);
   }

   private Object b(_b executor, String methodName, Class<?>[] parameterTypes, Object... args) {
      Module module = this.d(executor);
      if (module == null) {
         this.b = executor.c + " is not installed or registered.";
         return null;
      } else {
         try {
            Method method = module.getClass().getMethod(methodName, parameterTypes);
            return method.invoke(module, args);
         } catch (NoSuchMethodException var8) {
            this.b = executor.c + " does not provide the Phase 5 managed API.";
         } catch (IllegalAccessException var9) {
            this.b = "Cannot access " + executor.c + " managed API.";
         } catch (InvocationTargetException exception) {
            Throwable cause = exception.getCause();
            this.b = cause == null ? exception.getMessage() : cause.getMessage();
         }

         return null;
      }
   }

   private Module d(_b executor) {
      Modules modules = Modules.get();
      if (modules == null) {
         return null;
      } else {
         Module module = modules.get(executor.c);
         if (module != null) {
            return module;
         } else {
            for(Module candidate : modules.getAll()) {
               if (candidate.name.equalsIgnoreCase(executor.c)) {
                  return candidate;
               }
            }

            return null;
         }
      }
   }

   public static enum _b {
      b("satomine-order-bot"),
      e("satomine-sell-bot");

      private final String c;

      private _b(String moduleName) {
         this.c = moduleName;
      }

      // $FF: synthetic method
      private static _b[] b() {
         return new _b[]{b, e};
      }
   }

   public static enum _c {
      h,
      f,
      i,
      e,
      g,
      b,
      d;

      public boolean c() {
         return this == e || this == g || this == b;
      }

      // $FF: synthetic method
      private static _c[] b() {
         return new _c[]{h, f, i, e, g, b, d};
      }
   }
}
