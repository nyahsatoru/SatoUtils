package com.nyahsatoru.satoaddon.hud;

import java.util.ArrayList;
import java.util.List;
import meteordevelopment.meteorclient.systems.hud.HudElementInfo;
import meteordevelopment.meteorclient.systems.hud.HudRenderer;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;

public final class VieHotkeysHud extends VieHudBase {
   public static final HudElementInfo<VieHotkeysHud> INFO;
   private final List<String> lines;

   public VieHotkeysHud() {
      this(INFO);
   }

   public VieHotkeysHud(HudElementInfo<?> var1) {
      super(var1);
      this.lines = new ArrayList();
      this.setSize((double)165.0F, (double)13.0F);
   }

   public void tick(HudRenderer var1) {
      this.lines.clear();

      try {
         for(Module var3 : Modules.get().getActive()) {
            String var4 = String.valueOf(var3.keybind);
            if (!"None".equalsIgnoreCase(var4)) {
               this.lines.add(var3.title + " [" + var4 + "]");
            }
         }
      } catch (Throwable var5) {
      }

      if (this.lines.isEmpty()) {
         this.lines.add("No bound functions active");
      }

      this.setSize((double)190.0F, (double)Math.min(13 * this.lines.size(), 104));
   }

   public void render(HudRenderer var1) {
      for(int var2 = 0; var2 < this.lines.size() && var2 < 8; ++var2) {
         var1.text((String)this.lines.get(var2), (double)this.x, (double)(this.y + var2 * 13), TEXT, true, (double)-1.0F);
      }

   }

   static {
      INFO = new HudElementInfo(GROUP, "vie-hotkeys", "Hotkeys", "Compact active-module keybind list.", VieHotkeysHud::new);
   }
}
