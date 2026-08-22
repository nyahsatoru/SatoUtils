package com.nyahsatoru.satoaddon.hud;

import java.util.Locale;
import meteordevelopment.meteorclient.systems.hud.HudElementInfo;
import meteordevelopment.meteorclient.systems.hud.HudRenderer;

public final class VieTopInfoHud extends VieHudBase {
   public static final HudElementInfo<VieTopInfoHud> INFO;
   private String text;

   public VieTopInfoHud() {
      this(INFO);
   }

   public VieTopInfoHud(HudElementInfo<?> var1) {
      super(var1);
      this.text = "FPS 0  |  PING N/A  |  TPS 20.0  |  BPS 0.0";
      this.setSize((double)285.0F, (double)13.0F);
   }

   public void tick(HudRenderer var1) {
      Object var2 = mc();
      Object var3 = invoke(var2, "getCurrentFps", new Object[0]);
      if (var3 == null) {
         var3 = invoke(var2, "getFps", new Object[0]);
      }

      if (var3 == null) {
         var3 = field(var2, "fps");
      }

      Object var4 = field(var2, "player");
      Object var5 = invoke(var4, "getVelocity", new Object[0]);
      double var6 = var5 == null ? (double)0.0F : number(invoke(var5, "horizontalLength", new Object[0]), (double)0.0F) * (double)20.0F;
      Object var8 = invoke(var2, "getCurrentServerEntry", new Object[0]);
      String var9 = var8 == null ? "N/A" : safe(field(var8, "ping"), "N/A");
      this.text = String.format(Locale.ROOT, "FPS %s  |  PING %s  |  TPS %.1f  |  BPS %.1f", safe(var3, "0"), var9, (double)20.0F, var6);
   }

   public void render(HudRenderer var1) {
      var1.text(this.text, (double)this.x, (double)this.y, TEXT, true, (double)-1.0F);
   }

   static {
      INFO = new HudElementInfo(GROUP, "vie-top-info", "Top Info", "Vie-style FPS, ping, TPS and speed HUD.", VieTopInfoHud::new);
   }
}
