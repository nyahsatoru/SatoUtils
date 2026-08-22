package baritone.command.defaults;

import baritone.Baritone;
import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.command.datatypes.RelativeGoalXZ;
import baritone.api.pathing.goals.GoalXZ;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ExploreCommand extends Command {
   public ExploreCommand(Baritone var1) {
      super(var1, "explore");
   }

   public void execute(String var1, IArgConsumer var2) {
      if (var2.hasAny()) {
         var2.requireExactly(2);
      } else {
         var2.requireMax(0);
      }

      GoalXZ var3 = var2.hasAny() ? (GoalXZ)var2.getDatatypePost(RelativeGoalXZ.INSTANCE, super.ctx.playerFeet()) : new GoalXZ(super.ctx.playerFeet());
      super.baritone.getExploreProcess().explore(var3.getX(), var3.getZ());
      this.logDirect(String.format("Exploring from %s", var3.toString()));
   }

   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return var2.hasAtMost(2) ? var2.tabCompleteDatatype(RelativeGoalXZ.INSTANCE) : Stream.empty();
   }

   public String getShortDesc() {
      return "Explore things";
   }

   public List<String> getLongDesc() {
      return Arrays.asList("Tell Baritone to explore randomly. If you used explorefilter before this, it will be applied.", "", "Usage:", "> explore - Explore from your current position.", "> explore <x> <z> - Explore from the specified X and Z position.");
   }
}
