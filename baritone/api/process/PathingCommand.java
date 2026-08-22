package baritone.api.process;

import baritone.api.pathing.goals.Goal;
import java.util.Objects;

public class PathingCommand {
   public final Goal goal;
   public final PathingCommandType commandType;

   public PathingCommand(Goal var1, PathingCommandType var2) {
      Objects.requireNonNull(var2);
      this.goal = var1;
      this.commandType = var2;
   }

   public String toString() {
      String var10000 = String.valueOf(this.commandType);
      return var10000 + " " + String.valueOf(this.goal);
   }
}
