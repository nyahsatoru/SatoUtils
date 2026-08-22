package com.nyahsatoru.satoaddon.hud;

import meteordevelopment.meteorclient.systems.hud.HudElementInfo;
import meteordevelopment.meteorclient.systems.hud.HudRenderer;

public final class VieSpotifyHud extends VieHudBase {
   public static final HudElementInfo<VieSpotifyHud> INFO;

   public VieSpotifyHud() {
      this(INFO);
   }

   public VieSpotifyHud(HudElementInfo<?> var1) {
      super(var1);
      this.setSize((double)170.0F, (double)26.0F);
   }

   public void render(HudRenderer var1) {
      var1.text("Spotify", (double)this.x, (double)this.y, ACCENT, true, (double)-1.0F);
      var1.text("No track detected", (double)this.x, (double)(this.y + 13), SECONDARY, true, (double)-1.0F);
   }

   static {
      INFO = new HudElementInfo(GROUP, "vie-spotify", "Spotify HUD", "Vie-style media panel placeholder with graceful fallback when no player is exposed.", VieSpotifyHud::new);
   }
}
