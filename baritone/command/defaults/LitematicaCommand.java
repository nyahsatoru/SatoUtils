package baritone.command.defaults;

import baritone.Baritone;
import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class LitematicaCommand extends Command {
   public LitematicaCommand(Baritone var1) {
      super(var1, "litematica");
   }

   public void execute(String var1, IArgConsumer var2) {
      var2.requireMax(1);
      int var3 = var2.hasAny() ? (Integer)var2.getAs(Integer.class) - 1 : 0;
      super.baritone.getBuilderProcess().buildOpenLitematic(var3);
   }

   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return Stream.empty();
   }

   public String getShortDesc() {
      return "Builds the loaded schematic";
   }

   public List<String> getLongDesc() {
      return Arrays.asList("Build a schematic currently open in Litematica.", "", "Usage:", "> litematica", "> litematica <#>");
   }
}
