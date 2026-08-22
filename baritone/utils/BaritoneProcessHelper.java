package baritone.utils;

import baritone.Baritone;
import baritone.api.process.IBaritoneProcess;
import baritone.api.utils.Helper;
import baritone.api.utils.IPlayerContext;

public abstract class BaritoneProcessHelper implements IBaritoneProcess, Helper {
   public final Baritone a;
   public final IPlayerContext a;

   public BaritoneProcessHelper(Baritone var1) {
      this.a = var1;
      this.a = var1.getPlayerContext();
   }

   public boolean isTemporary() {
      return false;
   }
}
