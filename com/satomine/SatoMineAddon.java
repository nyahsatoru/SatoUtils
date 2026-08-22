package com.satomine;

import com.satomine.core.SatoMineCoordinator;
import com.satomine.modules.SatoMineInventory;
import com.satomine.modules.SatoMineLoot;
import com.satomine.modules.SatoMineMining;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Modules;

public class SatoMineAddon extends MeteorAddon {
   public void onInitialize() {
      SatoMineCoordinator coordinator = new SatoMineCoordinator();
      Modules.get().add(new SatoMineMining(coordinator));
      Modules.get().add(new SatoMineLoot(coordinator));
      Modules.get().add(new SatoMineInventory(coordinator));
   }

   public void onRegisterCategories() {
      Modules.registerCategory(b.b);
   }

   public String getPackage() {
      return "com.satomine";
   }
}
