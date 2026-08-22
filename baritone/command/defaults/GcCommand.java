package baritone.command.defaults;

import baritone.Baritone;
import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class GcCommand extends Command {
   public GcCommand(Baritone var1) {
      super(var1, "gc");
   }

   public void execute(String var1, IArgConsumer var2) {
      var2.requireMax(0);
      System.gc();
      this.logDirect("ok called System.gc()");
   }

   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return Stream.empty();
   }

   public String getShortDesc() {
      return "Call System.gc()";
   }

   public List<String> getLongDesc() {
      return Arrays.asList("Calls System.gc().", "", "Usage:", "> gc");
   }
}
