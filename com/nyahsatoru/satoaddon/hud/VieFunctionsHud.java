package com.nyahsatoru.satoaddon.hud;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import meteordevelopment.meteorclient.systems.hud.HudElementInfo;
import meteordevelopment.meteorclient.systems.hud.HudRenderer;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;

public final class VieFunctionsHud extends VieHudBase {
   public static final HudElementInfo<VieFunctionsHud> INFO;
   private final List<String> lines;

   public VieFunctionsHud() {
      this(INFO);
   }

   public VieFunctionsHud(HudElementInfo<?> var1) {
      super(var1);
      this.lines = new ArrayList();
      this.setSize((double)180.0F, (double)13.0F);
   }

   public void tick(HudRenderer var1) {
      this.lines.clear();

      try {
         for(Module var3 : Modules.get().getActive()) {
            this.lines.add(var3.title);
         }
      } catch (Throwable var4) {
      }

      this.lines.sort(Comparator.naturalOrder());
      if (this.lines.isEmpty()) {
         this.lines.add("No active functions");
      }

      this.setSize((double)Math.max(180, this.lines.stream().mapToInt(String::length).max().orElse(12) * 7 + 20), (double)Math.min(13 * Math.min(this.lines.size(), 8), 104));
   }

   public void render(HudRenderer var1) {
      int var2 = 0;

      for(String var4 : this.lines) {
         if (var2 >= 8) {
            break;
         }

         var1.text(var4, (double)this.x, (double)(this.y + var2 * 13), var2 == 0 ? ACCENT : TEXT, true, (double)-1.0F);
         ++var2;
      }

   }

   static {
      INFO = new HudElementInfo(GROUP, "vie-functions", "Functions (Grouped)", "Active Meteor modules grouped in a compact Vie-style list.", VieFunctionsHud::new);
   }
}
