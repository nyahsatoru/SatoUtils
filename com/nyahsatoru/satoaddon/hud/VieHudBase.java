package com.nyahsatoru.satoaddon.hud;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import meteordevelopment.meteorclient.systems.hud.HudElement;
import meteordevelopment.meteorclient.systems.hud.HudElementInfo;
import meteordevelopment.meteorclient.systems.hud.HudGroup;
import meteordevelopment.meteorclient.systems.hud.HudRenderer;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;

abstract class VieHudBase extends HudElement {
   protected static final Color TEXT = new SettingColor(245, 239, 248, 255);
   protected static final Color SECONDARY = new SettingColor(207, 195, 214, 255);
   protected static final Color ACCENT = new SettingColor(185, 96, 193, 255);
   protected static final HudGroup GROUP = new HudGroup("Sato / Vie");

   protected VieHudBase(HudElementInfo<?> var1) {
      super(var1);
   }

   protected static Object mc() {
      try {
         Class var0 = Class.forName("meteordevelopment.meteorclient.MeteorClient");
         Field var1 = var0.getField("mc");
         return var1.get((Object)null);
      } catch (Throwable var2) {
         return null;
      }
   }

   protected static Object field(Object var0, String var1) {
      if (var0 == null) {
         return null;
      } else {
         try {
            Field var2 = var0.getClass().getField(var1);
            var2.setAccessible(true);
            return var2.get(var0);
         } catch (Throwable var3) {
            return null;
         }
      }
   }

   protected static Object invoke(Object var0, String var1, Object... var2) {
      if (var0 == null) {
         return null;
      } else {
         try {
            Method var3 = null;

            for(Method var7 : var0.getClass().getMethods()) {
               if (var7.getName().equals(var1) && var7.getParameterCount() == var2.length) {
                  var3 = var7;
                  break;
               }
            }

            if (var3 == null) {
               return null;
            } else {
               var3.setAccessible(true);
               return var3.invoke(var0, var2);
            }
         } catch (Throwable var8) {
            return null;
         }
      }
   }

   protected static String safe(Object var0, String var1) {
      return var0 == null ? var1 : String.valueOf(var0);
   }

   protected static double number(Object var0, double var1) {
      double var10000;
      if (var0 instanceof Number var3) {
         var10000 = var3.doubleValue();
      } else {
         var10000 = var1;
      }

      return var10000;
   }

   protected static String playerName() {
      Object var0 = mc();
      Object var1 = field(var0, "player");
      Object var2 = invoke(var1, "getName");
      Object var3 = invoke(var2, "getString");
      return safe(var3, "Player");
   }

   protected void drawLine(HudRenderer var1, String var2, String var3, int var4) {
      double var5 = (double)this.x;
      var1.text(var2, var5, (double)var4, ACCENT, true, (double)-1.0F);
      var1.text(var3, var5 + var1.textWidth(var2, true) + (double)5.0F, (double)var4, TEXT, true, (double)-1.0F);
   }
}
