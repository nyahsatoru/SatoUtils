package com.nyahsatoru.satoaddon.hud;

import java.util.Locale;
import meteordevelopment.meteorclient.systems.hud.HudElementInfo;
import meteordevelopment.meteorclient.systems.hud.HudRenderer;

public final class ViePlayerHud extends VieHudBase {
   public static final HudElementInfo<ViePlayerHud> INFO;
   private String line1;
   private String line2;

   public ViePlayerHud() {
      this(INFO);
   }

   public ViePlayerHud(HudElementInfo<?> var1) {
      super(var1);
      this.line1 = "Player";
      this.line2 = "HP 20.0";
      this.setSize((double)145.0F, (double)26.0F);
   }

   public void tick(HudRenderer var1) {
      Object var2 = field(mc(), "player");
      double var3 = number(invoke(var2, "getHealth", new Object[0]), (double)0.0F);
      this.line1 = playerName();
      this.line2 = String.format(Locale.ROOT, "HP %.1f", var3);
   }

   public void render(HudRenderer var1) {
      var1.text(this.line1, (double)this.x, (double)this.y, ACCENT, true, (double)-1.0F);
      var1.text(this.line2, (double)this.x, (double)(this.y + 13), TEXT, true, (double)-1.0F);
   }

   static {
      INFO = new HudElementInfo(GROUP, "vie-player", "Player HUD", "Vie-style compact player information card.", ViePlayerHud::new);
   }
}
