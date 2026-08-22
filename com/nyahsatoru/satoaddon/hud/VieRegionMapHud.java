package com.nyahsatoru.satoaddon.hud;

import meteordevelopment.meteorclient.systems.hud.HudElementInfo;
import meteordevelopment.meteorclient.systems.hud.HudRenderer;

public final class VieRegionMapHud extends VieHudBase {
   public static final HudElementInfo<VieRegionMapHud> INFO;
   private String text;

   public VieRegionMapHud() {
      this(INFO);
   }

   public VieRegionMapHud(HudElementInfo<?> var1) {
      super(var1);
      this.text = "REGION  /  WORLD";
      this.setSize((double)150.0F, (double)13.0F);
   }

   public void tick(HudRenderer var1) {
      Object var2 = mc();
      Object var3 = field(var2, "world");
      Object var4 = invoke(var3, "getRegistryKey", new Object[0]);
      String var10001 = var4 == null ? "WORLD" : var4.toString();
      this.text = "REGION  /  " + var10001;
   }

   public void render(HudRenderer var1) {
      var1.text(this.text, (double)this.x, (double)this.y, TEXT, true, (double)-1.0F);
   }

   static {
      INFO = new HudElementInfo(GROUP, "vie-region-map", "Region Map", "Compact server/world region readout matching Vie's HUD slot.", VieRegionMapHud::new);
   }
}
