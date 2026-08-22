package baritone.command.defaults;

import baritone.Baritone;
import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.command.exception.CommandInvalidStateException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class VersionCommand extends Command {
   public VersionCommand(Baritone var1) {
      super(var1, "version");
   }

   public void execute(String var1, IArgConsumer var2) {
      var2.requireMax(0);
      if ((var1 = this.getClass().getPackage().getImplementationVersion()) == null) {
         throw new CommandInvalidStateException("Null version (this is normal in a dev environment)");
      } else {
         this.logDirect(String.format("You are running Baritone v%s", var1));
      }
   }

   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return Stream.empty();
   }

   public String getShortDesc() {
      return "View the Baritone version";
   }

   public List<String> getLongDesc() {
      return Arrays.asList("The version command prints the version of Baritone you're currently running.", "", "Usage:", "> version - View version information, if present");
   }
}
