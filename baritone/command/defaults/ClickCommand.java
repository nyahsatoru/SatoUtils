package baritone.command.defaults;

import baritone.Baritone;
import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ClickCommand extends Command {
   public ClickCommand(Baritone var1) {
      super(var1, "click");
   }

   public void execute(String var1, IArgConsumer var2) {
      var2.requireMax(0);
      super.baritone.openClick();
      this.logDirect("aight dude");
   }

   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return Stream.empty();
   }

   public String getShortDesc() {
      return "Open click";
   }

   public List<String> getLongDesc() {
      return Arrays.asList("Opens click dude", "", "Usage:", "> click");
   }
}
