package baritone.process.elytra;

import baritone.Baritone;
import baritone.api.pathing.goals.Goal;
import baritone.api.process.IElytraProcess;
import baritone.api.process.PathingCommand;
import baritone.utils.BaritoneProcessHelper;
import net.minecraft.class_2338;

public final class NullElytraProcess extends BaritoneProcessHelper implements IElytraProcess {
   public NullElytraProcess(Baritone var1) {
      super(var1);
   }

   public final void repackChunks() {
      throw new UnsupportedOperationException("Called repackChunks() on NullElytraBehavior");
   }

   public final class_2338 currentDestination() {
      return null;
   }

   public final void pathTo(class_2338 var1) {
      throw new UnsupportedOperationException("Called pathTo() on NullElytraBehavior");
   }

   public final void pathTo(Goal var1) {
      throw new UnsupportedOperationException("Called pathTo() on NullElytraBehavior");
   }

   public final void resetState() {
   }

   public final boolean isActive() {
      return false;
   }

   public final PathingCommand onTick(boolean var1, boolean var2) {
      throw new UnsupportedOperationException("Called onTick on NullElytraProcess");
   }

   public final void onLostControl() {
   }

   public final String displayName0() {
      return "NullElytraProcess";
   }

   public final boolean isLoaded() {
      return false;
   }

   public final boolean isSafeToCancel() {
      return true;
   }
}
