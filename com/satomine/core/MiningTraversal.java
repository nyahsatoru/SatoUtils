package com.satomine.core;

public enum MiningTraversal {
   ZIG_ZAG,
   LAYER_BY_LAYER,
   NEAREST;

   // $FF: synthetic method
   private static MiningTraversal[] b() {
      return new MiningTraversal[]{ZIG_ZAG, LAYER_BY_LAYER, NEAREST};
   }
}
