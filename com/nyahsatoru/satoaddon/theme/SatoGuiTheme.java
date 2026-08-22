package com.nyahsatoru.satoaddon.theme;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import meteordevelopment.meteorclient.gui.themes.meteor.MeteorGuiTheme;

public final class SatoGuiTheme extends MeteorGuiTheme {
   private static final int ACCENT = rgba(255, 62, 156, 255);
   private static final int ACCENT_SOFT = rgba(255, 62, 156, 110);
   private static final int PANEL = rgba(26, 21, 32, 255);
   private static final int PANEL_HOVER = rgba(88, 71, 107, 255);
   private static final int PANEL_PRESSED = rgba(106, 77, 119, 255);
   private static final int BG = rgba(18, 16, 22, 240);
   private static final int OUTLINE = rgba(174, 104, 192, 210);

   public SatoGuiTheme() {
      this.renameTheme();
      this.tuneColors();
      this.tuneGeneral();
   }

   private void renameTheme() {
      try {
         Field var1 = this.findField("name");
         if (var1 != null) {
            var1.setAccessible(true);
            var1.set(this, "Sato");
         }
      } catch (Throwable var2) {
      }

   }

   private void tuneColors() {
      this.setColorField("accentColor", ACCENT);
      this.setColorField("checkboxColor", ACCENT);
      this.setThreeState("moduleBackground", PANEL, PANEL_HOVER, PANEL_PRESSED);
      this.setColorField("textColor", rgba(237, 234, 242, 255));
      this.setColorField("textSecondaryColor", rgba(154, 147, 168, 255));
      this.setColorField("textHighlightColor", rgba(255, 62, 156, 180));
      this.setColorField("titleTextColor", rgba(237, 234, 242, 255));
      this.setColorField("loggedInColor", rgba(77, 230, 140, 255));
      this.setColorField("placeholderColor", rgba(255, 255, 255, 30));
      this.setThreeState("backgroundColor", BG, rgba(28, 25, 40, 245), rgba(21, 16, 25, 245));
      this.setThreeState("outlineColor", OUTLINE, rgba(190, 117, 205, 230), rgba(255, 62, 156, 230));
      this.setThreeState("sliderHandle", rgba(156, 83, 171, 255), ACCENT, ACCENT);
      this.setColorField("sliderLeft", rgba(255, 62, 156, 255));
      this.setColorField("sliderRight", rgba(43, 39, 57, 245));
      this.setColorField("separatorText", rgba(229, 218, 232, 255));
      this.setColorField("separatorCenter", ACCENT);
      this.setColorField("separatorEdges", rgba(210, 190, 215, 150));
      this.setThreeState("scrollbarColor", rgba(47, 41, 61, 200), rgba(75, 58, 88, 230), rgba(156, 83, 171, 255));
   }

   private void tuneGeneral() {
      try {
         Field var1 = this.findField("moduleAlignment");
         if (var1 != null) {
            var1.setAccessible(true);
            Object var2 = var1.get(this);
            if (var2 != null) {
               Method var3 = var2.getClass().getMethod("set", Object.class);
               Class var4 = Class.forName("meteordevelopment.meteorclient.gui.utils.AlignmentX");
               var3.invoke(var2, Enum.valueOf(var4, "Left"));
            }
         }
      } catch (Throwable var6) {
      }

      try {
         Field var7 = this.findField("categoryIcons");
         if (var7 != null) {
            var7.setAccessible(true);
            Object var8 = var7.get(this);
            if (var8 != null) {
               Method var9 = var8.getClass().getMethod("set", Object.class);
               var9.invoke(var8, Boolean.TRUE);
            }
         }
      } catch (Throwable var5) {
      }

   }

   private void setColorField(String var1, int var2) {
      try {
         Field var3 = this.findField(var1);
         if (var3 != null) {
            var3.setAccessible(true);
            var3.setInt(this, var2);
         }
      } catch (Throwable var4) {
      }

   }

   private void setThreeState(String var1, int var2, int var3, int var4) {
      try {
         Field var5 = this.findField(var1);
         if (var5 == null) {
            return;
         }

         var5.setAccessible(true);
         Object var6 = var5.get(this);
         if (var6 == null) {
            return;
         }

         Method var7 = var6.getClass().getMethod("set", Object.class, Object.class, Object.class);
         var7.invoke(var6, var2, var3, var4);
      } catch (Throwable var8) {
      }

   }

   private Field findField(String var1) {
      for(Class var2 = this.getClass(); var2 != null; var2 = var2.getSuperclass()) {
         try {
            return var2.getDeclaredField(var1);
         }
      }

      return null;
   }

   private static int rgba(int var0, int var1, int var2, int var3) {
      return (var3 & 255) << 24 | (var0 & 255) << 16 | (var1 & 255) << 8 | var2 & 255;
   }
}
