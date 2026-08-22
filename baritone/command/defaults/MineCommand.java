package baritone.command.defaults;

import baritone.Baritone;
import baritone.api.BaritoneAPI;
import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.command.datatypes.ForBlockOptionalMeta;
import baritone.api.utils.BlockOptionalMeta;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class MineCommand extends Command {
   public MineCommand(Baritone var1) {
      super(var1, "mine");
   }

   public void execute(String var1, IArgConsumer var2) {
      int var4 = (Integer)var2.getAsOrDefault(Integer.class, 0);
      var2.requireMin(1);
      ArrayList var3 = new ArrayList();

      while(var2.hasAny()) {
         var3.add((BlockOptionalMeta)var2.getDatatypeFor(ForBlockOptionalMeta.INSTANCE));
      }

      BaritoneAPI.getProvider().getWorldScanner().repack(super.ctx);
      this.logDirect(String.format("Mining %s", var3.toString()));
      super.baritone.getMineProcess().mine(var4, (BlockOptionalMeta[])var3.toArray(new BlockOptionalMeta[0]));
   }

   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      var2.getAsOrDefault(Integer.class, 0);

      while(var2.has(2)) {
         var2.getDatatypeFor(ForBlockOptionalMeta.INSTANCE);
      }

      return var2.tabCompleteDatatype(ForBlockOptionalMeta.INSTANCE);
   }

   public String getShortDesc() {
      return "Mine some blocks";
   }

   public List<String> getLongDesc() {
      return Arrays.asList("The mine command allows you to tell Baritone to search for and mine individual blocks.", "", "The specified blocks can be ores, or any other block.", "", "Also see the legitMine settings (see #set l legitMine).", "", "Usage:", "> mine diamond_ore - Mines all diamonds it can find.");
   }
}
