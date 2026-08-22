package baritone.process;

import baritone.Baritone;
import baritone.api.pathing.goals.Goal;
import baritone.api.process.PathingCommand;
import baritone.api.process.PathingCommandType;
import baritone.utils.BaritoneProcessHelper;

public class InventoryPauserProcess extends BaritoneProcessHelper {
   private boolean a;
   private boolean b;
   private int a;

   public InventoryPauserProcess(Baritone var1) {
      super(var1);
   }

   public boolean isActive() {
      return super.a.player() != null && super.a.world() != null;
   }

   public final boolean a() {
      this.a = true;
      return this.b && this.a > 1;
   }

   public PathingCommand onTick(boolean var1, boolean var2) {
      this.b = var2;
      if (this.a) {
         this.a = false;
         if (super.a.player().method_18798().method_18805((double)1.0F, (double)0.0F, (double)1.0F).method_1033() < 1.0E-5) {
            ++this.a;
         }

         return new PathingCommand((Goal)null, PathingCommandType.REQUEST_PAUSE);
      } else {
         this.a = 0;
         return new PathingCommand((Goal)null, PathingCommandType.DEFER);
      }
   }

   public void onLostControl() {
   }

   public String displayName0() {
      return "inventory pauser";
   }

   public double priority() {
      return 5.1;
   }

   public boolean isTemporary() {
      return true;
   }
}
