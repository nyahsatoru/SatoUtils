package baritone.utils;

import baritone.api.pathing.goals.Goal;
import baritone.api.process.PathingCommand;
import baritone.api.process.PathingCommandType;
import baritone.pathing.movement.CalculationContext;

public class PathingCommandContext extends PathingCommand {
   public final CalculationContext a;

   public PathingCommandContext(Goal var1, PathingCommandType var2, CalculationContext var3) {
      super(var1, var2);
      this.a = var3;
   }
}
