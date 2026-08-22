package baritone.command.defaults;

import baritone.Baritone;
import baritone.api.BaritoneAPI;
import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.process.ICustomGoalProcess;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class PathCommand extends Command {
   public PathCommand(Baritone var1) {
      super(var1, "path");
   }

   public void execute(String var1, IArgConsumer var2) {
      ICustomGoalProcess var3 = super.baritone.getCustomGoalProcess();
      var2.requireMax(0);
      BaritoneAPI.getProvider().getWorldScanner().repack(super.ctx);
      var3.path();
      this.logDirect("Now pathing");
   }

   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return Stream.empty();
   }

   public String getShortDesc() {
      return "Start heading towards the goal";
   }

   public List<String> getLongDesc() {
      return Arrays.asList("The path command tells Baritone to head towards the current goal.", "", "Usage:", "> path - Start the pathing.");
   }
}
