package baritone.api.command.datatypes;

import baritone.api.IBaritone;
import baritone.api.cache.IWaypoint;
import baritone.api.cache.IWaypointCollection;
import baritone.api.command.helpers.TabCompleteHelper;
import java.util.Comparator;
import java.util.stream.Stream;

public enum ForWaypoints implements IDatatypeFor<IWaypoint[]> {
   INSTANCE;

   public final IWaypoint[] get(IDatatypeContext var1) {
      String var2;
      IWaypoint.Tag var3;
      return (var3 = IWaypoint.Tag.getByName(var2 = var1.getConsumer().getString())) == null ? getWaypointsByName(var1.getBaritone(), var2) : getWaypointsByTag(var1.getBaritone(), var3);
   }

   public final Stream<String> tabComplete(IDatatypeContext var1) {
      return (new TabCompleteHelper()).append(getWaypointNames(var1.getBaritone())).sortAlphabetically().prepend(IWaypoint.Tag.getAllNames()).filterPrefix(var1.getConsumer().getString()).stream();
   }

   public static IWaypointCollection waypoints(IBaritone var0) {
      return var0.getWorldProvider().getCurrentWorld().getWaypoints();
   }

   public static IWaypoint[] getWaypoints(IBaritone var0) {
      return (IWaypoint[])waypoints(var0).getAllWaypoints().stream().sorted(Comparator.comparingLong(IWaypoint::getCreationTimestamp).reversed()).toArray((var0x) -> new IWaypoint[var0x]);
   }

   public static String[] getWaypointNames(IBaritone var0) {
      return (String[])Stream.of(getWaypoints(var0)).map(IWaypoint::getName).filter((var0x) -> !var0x.isEmpty()).toArray((var0x) -> new String[var0x]);
   }

   public static IWaypoint[] getWaypointsByTag(IBaritone var0, IWaypoint.Tag var1) {
      return (IWaypoint[])waypoints(var0).getByTag(var1).stream().sorted(Comparator.comparingLong(IWaypoint::getCreationTimestamp).reversed()).toArray((var0x) -> new IWaypoint[var0x]);
   }

   public static IWaypoint[] getWaypointsByName(IBaritone var0, String var1) {
      return (IWaypoint[])Stream.of(getWaypoints(var0)).filter((var1x) -> var1x.getName().equalsIgnoreCase(var1)).toArray((var0x) -> new IWaypoint[var0x]);
   }

   // $FF: synthetic method
   private static ForWaypoints[] $values() {
      return new ForWaypoints[]{INSTANCE};
   }
}
