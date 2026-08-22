package baritone.command.defaults;

import baritone.Baritone;
import baritone.api.behavior.IPathingBehavior;
import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.command.exception.CommandInvalidStateException;
import baritone.api.process.IBaritoneProcess;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ETACommand extends Command {
   public ETACommand(Baritone var1) {
      super(var1, "eta");
   }

   public void execute(String var1, IArgConsumer var2) {
      var2.requireMax(0);
      if ((IBaritoneProcess)super.baritone.getPathingControlManager().mostRecentInControl().orElse((Object)null) == null) {
         throw new CommandInvalidStateException("No process in control");
      } else {
         IPathingBehavior var7;
         double var3 = (Double)(var7 = super.baritone.getPathingBehavior()).ticksRemainingInSegment().orElse(Double.NaN);
         double var5 = (Double)var7.estimatedTicksToGoal().orElse(Double.NaN);
         this.logDirect(String.format("Next segment: %.1fs (%.0f ticks)\nGoal: %.1fs (%.0f ticks)", var3 / (double)20.0F, var3, var5 / (double)20.0F, var5));
      }
   }

   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return Stream.empty();
   }

   public String getShortDesc() {
      return "View the current ETA";
   }

   public List<String> getLongDesc() {
      return Arrays.asList("The ETA command provides information about the estimated time until the next segment.", "and the goal", "", "Be aware that the ETA to your goal is really unprecise", "", "Usage:", "> eta - View ETA, if present");
   }
}
