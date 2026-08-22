package baritone.process;

import baritone.Baritone;
import baritone.api.pathing.goals.Goal;
import baritone.api.process.ICustomGoalProcess;
import baritone.api.process.PathingCommand;
import baritone.api.process.PathingCommandType;
import baritone.utils.BaritoneProcessHelper;
import net.minecraft.class_1937;
import net.minecraft.class_2561;
import net.minecraft.class_638;

public final class CustomGoalProcess extends BaritoneProcessHelper implements ICustomGoalProcess {
   private Goal a;
   private Goal b;
   private State a;

   public CustomGoalProcess(Baritone var1) {
      super(var1);
   }

   public final void setGoal(Goal var1) {
      this.a = var1;
      this.b = var1;
      if (super.a.getElytraProcess().isActive()) {
         super.a.getElytraProcess().pathTo(var1);
      }

      if (this.a == CustomGoalProcess.State.a) {
         this.a = CustomGoalProcess.State.b;
      }

      if (this.a == CustomGoalProcess.State.d) {
         this.a = CustomGoalProcess.State.c;
      }

   }

   public final void path() {
      this.a = CustomGoalProcess.State.c;
   }

   public final Goal getGoal() {
      return this.a;
   }

   public final Goal mostRecentGoal() {
      return this.b;
   }

   public final boolean isActive() {
      return this.a != CustomGoalProcess.State.a;
   }

   public final PathingCommand onTick(boolean var1, boolean var2) {
      switch (this.a.ordinal()) {
         case 1:
            return new PathingCommand(this.a, PathingCommandType.CANCEL_AND_SET_GOAL);
         case 2:
            PathingCommand var4 = new PathingCommand(this.a, PathingCommandType.FORCE_REVALIDATE_GOAL_AND_PATH);
            this.a = CustomGoalProcess.State.d;
            return var4;
         case 3:
            if (var1) {
               this.onLostControl();
               return new PathingCommand(this.a, PathingCommandType.CANCEL_AND_SET_GOAL);
            } else {
               if (this.a == null || this.a.isInGoal(super.a.playerFeet()) && this.a.isInGoal(super.a.a.a())) {
                  this.onLostControl();
                  class_1937 var3;
                  if ((Boolean)Baritone.a().disconnectOnArrival.value && (var3 = super.a.world()) instanceof class_638) {
                     ((class_638)var3).method_8525(class_2561.method_43470("[Baritone] Arrived at goal!"));
                  }

                  if ((Boolean)Baritone.a().notificationOnPathComplete.value) {
                     this.logNotification("Pathing complete", false);
                  }

                  return new PathingCommand(this.a, PathingCommandType.CANCEL_AND_SET_GOAL);
               }

               return new PathingCommand(this.a, PathingCommandType.SET_GOAL_AND_PATH);
            }
         default:
            throw new IllegalStateException("Unexpected state " + String.valueOf(this.a));
      }
   }

   public final void onLostControl() {
      this.a = CustomGoalProcess.State.a;
      this.a = null;
   }

   public final String displayName0() {
      return "Custom Goal " + String.valueOf(this.a);
   }

   protected static enum State {
      a,
      b,
      c,
      d;
   }
}
