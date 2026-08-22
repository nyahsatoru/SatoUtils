package baritone.behavior;

import baritone.Baritone;
import baritone.api.behavior.IBehavior;
import baritone.api.utils.IPlayerContext;

public class Behavior implements IBehavior {
   public final Baritone a;
   public final IPlayerContext a;

   public Behavior(Baritone var1) {
      this.a = var1;
      this.a = var1.getPlayerContext();
   }
}
