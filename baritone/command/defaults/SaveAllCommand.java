package baritone.command.defaults;

import baritone.Baritone;
import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class SaveAllCommand extends Command {
   public SaveAllCommand(Baritone var1) {
      super(var1, "saveall");
   }

   public void execute(String var1, IArgConsumer var2) {
      var2.requireMax(0);
      super.ctx.worldData().getCachedWorld().save();
      this.logDirect("Saved");
   }

   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return Stream.empty();
   }

   public String getShortDesc() {
      return "Saves Baritone's cache for this world";
   }

   public List<String> getLongDesc() {
      return Arrays.asList("The saveall command saves Baritone's world cache.", "", "Usage:", "> saveall");
   }
}
