package baritone.command.defaults;

import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class SchematicaCommand extends Command {
   public void execute(String var1, IArgConsumer var2) {
      var2.requireMax(0);
      super.baritone.getBuilderProcess().buildOpenSchematic();
   }

   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return Stream.empty();
   }

   public String getShortDesc() {
      return "Builds the loaded schematic";
   }

   public List<String> getLongDesc() {
      return Arrays.asList("Builds the schematic currently open in Schematica.", "", "Usage:", "> schematica");
   }
}
