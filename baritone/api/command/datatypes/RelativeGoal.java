package baritone.api.command.datatypes;

import baritone.api.command.argument.IArgConsumer;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalBlock;
import baritone.api.pathing.goals.GoalXZ;
import baritone.api.pathing.goals.GoalYLevel;
import baritone.api.utils.BetterBlockPos;
import java.util.stream.Stream;

public enum RelativeGoal implements IDatatypePost<Goal, BetterBlockPos> {
   INSTANCE;

   public final Goal apply(IDatatypeContext var1, BetterBlockPos var2) {
      if (var2 == null) {
         var2 = BetterBlockPos.ORIGIN;
      }

      GoalBlock var3;
      IArgConsumer var4;
      if ((var3 = (GoalBlock)(var4 = var1.getConsumer()).peekDatatypePostOrNull(RelativeGoalBlock.INSTANCE, var2)) != null) {
         return var3;
      } else if ((var3 = (GoalXZ)var4.peekDatatypePostOrNull(RelativeGoalXZ.INSTANCE, var2)) != null) {
         return var3;
      } else {
         GoalYLevel var5;
         return (Goal)((var5 = (GoalYLevel)var4.peekDatatypePostOrNull(RelativeGoalYLevel.INSTANCE, var2)) != null ? var5 : new GoalBlock(var2));
      }
   }

   public final Stream<String> tabComplete(IDatatypeContext var1) {
      return var1.getConsumer().tabCompleteDatatype(RelativeCoordinate.INSTANCE);
   }

   // $FF: synthetic method
   private static RelativeGoal[] $values() {
      return new RelativeGoal[]{INSTANCE};
   }
}
