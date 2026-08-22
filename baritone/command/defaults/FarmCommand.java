package baritone.command.defaults;

import baritone.Baritone;
import baritone.api.cache.IWaypoint;
import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.command.datatypes.ForWaypoints;
import baritone.api.command.exception.CommandInvalidStateException;
import baritone.api.utils.BetterBlockPos;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class FarmCommand extends Command {
   public FarmCommand(Baritone var1) {
      super(var1, "farm");
   }

   public void execute(String var1, IArgConsumer var2) {
      var2.requireMax(2);
      int var4 = 0;
      BetterBlockPos var3 = null;
      if (var2.has(1)) {
         var4 = (Integer)var2.getAs(Integer.class);
      }

      if (var2.has(1)) {
         IWaypoint[] var5;
         switch (((Object[])(var5 = (IWaypoint[])var2.getDatatypeFor(ForWaypoints.INSTANCE))).length) {
            case 0:
               throw new CommandInvalidStateException("No waypoints found");
            case 1:
               var5 = ((Object[])var5)[0];
               var3 = var5.getLocation();
               break;
            default:
               throw new CommandInvalidStateException("Multiple waypoints were found");
         }
      }

      super.baritone.getFarmProcess().farm(var4, var3);
      this.logDirect("Farming");
   }

   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return Stream.empty();
   }

   public String getShortDesc() {
      return "Farm nearby crops";
   }

   public List<String> getLongDesc() {
      return Arrays.asList("The farm command starts farming nearby plants. It harvests mature crops and plants new ones.", "", "Usage:", "> farm - farms every crop it can find.", "> farm <range> - farm crops within range from the starting position.", "> farm <range> <waypoint> - farm crops within range from waypoint.");
   }
}
