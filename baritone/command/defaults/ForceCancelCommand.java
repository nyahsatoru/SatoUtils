package baritone.command.defaults;

import baritone.Baritone;
import baritone.api.behavior.IPathingBehavior;
import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ForceCancelCommand extends Command {
   public ForceCancelCommand(Baritone var1) {
      super(var1, "forcecancel");
   }

   public void execute(String var1, IArgConsumer var2) {
      var2.requireMax(0);
      IPathingBehavior var3;
      (var3 = super.baritone.getPathingBehavior()).cancelEverything();
      var3.forceCancel();
      this.logDirect("ok force canceled");
   }

   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return Stream.empty();
   }

   public String getShortDesc() {
      return "Force cancel";
   }

   public List<String> getLongDesc() {
      return Arrays.asList("Like cancel, but more forceful.", "", "Usage:", "> forcecancel");
   }
}
