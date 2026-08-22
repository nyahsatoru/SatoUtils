package com.nyahsatoru.satoaddon.core;

public enum SatoMineTask {
   IDLE,
   MINING,
   LOOT,
   SELL;

   // $FF: synthetic method
   private static SatoMineTask[] b() {
      return new SatoMineTask[]{IDLE, MINING, LOOT, SELL};
   }
}
