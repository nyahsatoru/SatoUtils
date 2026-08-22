package com.nyahsatoru.satoaddon.hud;

import java.util.Locale;
import meteordevelopment.meteorclient.systems.hud.HudElementInfo;
import meteordevelopment.meteorclient.systems.hud.HudRenderer;

public final class VieCoordinatesHud extends VieHudBase {
   public static final HudElementInfo<VieCoordinatesHud> INFO;
   private String text;

   public VieCoordinatesHud() {
      this(INFO);
   }

   public VieCoordinatesHud(HudElementInfo<?> var1) {
      super(var1);
      this.text = "XYZ 0.0 0.0 0.0";
      this.setSize((double)145.0F, (double)13.0F);
   }

   public void tick(HudRenderer var1) {
      Object var2 = field(mc(), "player");
      this.text = String.format(Locale.ROOT, "XYZ %.1f %.1f %.1f", number(invoke(var2, "getX", new Object[0]), (double)0.0F), number(invoke(var2, "getY", new Object[0]), (double)0.0F), number(invoke(var2, "getZ", new Object[0]), (double)0.0F));
   }

   public void render(HudRenderer var1) {
      var1.text(this.text, (double)this.x, (double)this.y, TEXT, true, (double)-1.0F);
   }

   static {
      INFO = new HudElementInfo(GROUP, "vie-coordinates", "Coordinates", "Vie-style player XYZ coordinates.", VieCoordinatesHud::new);
   }
}
