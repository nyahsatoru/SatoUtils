package com.nyahsatoru.satoaddon.hud;

import meteordevelopment.meteorclient.systems.hud.HudElementInfo;
import meteordevelopment.meteorclient.systems.hud.HudRenderer;

public final class VieStaffListsHud extends VieHudBase {
   public static final HudElementInfo<VieStaffListsHud> INFO;

   public VieStaffListsHud() {
      this(INFO);
   }

   public VieStaffListsHud(HudElementInfo<?> var1) {
      super(var1);
      this.setSize((double)160.0F, (double)26.0F);
   }

   public void render(HudRenderer var1) {
      var1.text("STAFF LISTS", (double)this.x, (double)this.y, ACCENT, true, (double)-1.0F);
      var1.text("No staff detected", (double)this.x, (double)(this.y + 13), SECONDARY, true, (double)-1.0F);
   }

   static {
      INFO = new HudElementInfo(GROUP, "vie-staff-lists", "Staff Lists", "Compact staff-list slot matching Vie's HUD taxonomy.", VieStaffListsHud::new);
   }
}
