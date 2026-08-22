package com.nyahsatoru.satoaddon;

import com.nyahsatoru.satoaddon.core.SatoMineCoordinator;
import com.nyahsatoru.satoaddon.hud.VieCoordinatesHud;
import com.nyahsatoru.satoaddon.hud.VieFunctionsHud;
import com.nyahsatoru.satoaddon.hud.VieHotkeysHud;
import com.nyahsatoru.satoaddon.hud.ViePlayerHud;
import com.nyahsatoru.satoaddon.hud.VieRegionMapHud;
import com.nyahsatoru.satoaddon.hud.VieSpotifyHud;
import com.nyahsatoru.satoaddon.hud.VieStaffListsHud;
import com.nyahsatoru.satoaddon.hud.VieTopInfoHud;
import com.nyahsatoru.satoaddon.modules.SatoMineInventory;
import com.nyahsatoru.satoaddon.modules.SatoMineLoot;
import com.nyahsatoru.satoaddon.modules.SatoMineMining;
import com.nyahsatoru.satoaddon.persistence.SatoAddonConfig;
import com.nyahsatoru.satoaddon.theme.SatoGuiTheme;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.gui.GuiThemes;
import meteordevelopment.meteorclient.systems.hud.Hud;
import meteordevelopment.meteorclient.systems.modules.Modules;

public final class SatoAddon extends MeteorAddon {
   public void onInitialize() {
      SatoMineCoordinator var1 = new SatoMineCoordinator();
      Modules.get().add(new SatoMineMining(var1));
      Modules.get().add(new SatoMineLoot(var1));
      Modules.get().add(new SatoMineInventory(var1));
      SatoAddonConfig.init();
      GuiThemes.add(new SatoGuiTheme());
      GuiThemes.select("Sato");
      Hud.get().register(VieTopInfoHud.INFO);
      Hud.get().register(VieCoordinatesHud.INFO);
      Hud.get().register(VieFunctionsHud.INFO);
      Hud.get().register(VieHotkeysHud.INFO);
      Hud.get().register(ViePlayerHud.INFO);
      Hud.get().register(VieRegionMapHud.INFO);
      Hud.get().register(VieSpotifyHud.INFO);
      Hud.get().register(VieStaffListsHud.INFO);
   }

   public void onRegisterCategories() {
      Modules.registerCategory(b.b);
   }

   public String getPackage() {
      return "com.nyahsatoru.satoaddon";
   }
}
