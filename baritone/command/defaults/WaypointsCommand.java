package baritone.command.defaults;

import baritone.Baritone;
import baritone.api.cache.IWaypoint;
import baritone.api.cache.IWaypointCollection;
import baritone.api.cache.IWorldData;
import baritone.api.cache.Waypoint;
import baritone.api.command.Command;
import baritone.api.command.IBaritoneChatControl;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.command.datatypes.ForWaypoints;
import baritone.api.command.datatypes.RelativeBlockPos;
import baritone.api.command.exception.CommandInvalidStateException;
import baritone.api.command.exception.CommandInvalidTypeException;
import baritone.api.command.helpers.Paginator;
import baritone.api.command.helpers.TabCompleteHelper;
import baritone.api.pathing.goals.GoalBlock;
import baritone.api.utils.BetterBlockPos;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.class_124;
import net.minecraft.class_2558;
import net.minecraft.class_2561;
import net.minecraft.class_5250;

public class WaypointsCommand extends Command {
   private Map<IWorldData, List<IWaypoint>> a = new HashMap();

   public WaypointsCommand(Baritone var1) {
      super(var1, "waypoints", "waypoint", "wp");
   }

   public void execute(String var1, IArgConsumer var2) {
      Action var3;
      if ((var3 = var2.hasAny() ? WaypointsCommand.Action.a(var2.getString()) : WaypointsCommand.Action.a) == null) {
         throw new CommandInvalidTypeException(var2.consumed(), "an action");
      } else {
         BiFunction var4;
         Function var5 = (var2x) -> (class_2561)var0.apply(var2x, var3 == WaypointsCommand.Action.a ? WaypointsCommand.Action.d : var3);
         if (var3 == WaypointsCommand.Action.a) {
            IWaypoint.Tag var20;
            IWaypoint.Tag var49 = var20 = var2.hasAny() ? IWaypoint.Tag.getByName(var2.peekString()) : null;
            if (var49 != null) {
               var2.get();
            }

            IWaypoint[] var24;
            if ((var24 = var20 != null ? ForWaypoints.getWaypointsByTag(super.baritone, var20) : ForWaypoints.getWaypoints(super.baritone)).length > 0) {
               var2.requireMax(1);
               Paginator.paginate(var2, var24, () -> this.logDirect(var20 != null ? String.format("All waypoints by tag %s:", var20.name()) : "All waypoints:"), var5, String.format("%s%s %s%s", IBaritoneChatControl.FORCE_COMMAND_PREFIX, var1, var3.a[0], var20 != null ? " " + var20.getName() : ""));
            } else {
               var2.requireMax(0);
               throw new CommandInvalidStateException(var20 != null ? "No waypoints found by that tag" : "No waypoints found");
            }
         } else if (var3 == WaypointsCommand.Action.c) {
            IWaypoint.Tag var19;
            IWaypoint.Tag var47 = var19 = var2.hasAny() ? IWaypoint.Tag.getByName(var2.peekString()) : null;
            if (var47 == null) {
               var19 = IWaypoint.Tag.USER;
            } else {
               var2.get();
            }

            String var23 = !var2.hasExactlyOne() && !var2.hasExactly(4) ? "" : var2.getString();
            BetterBlockPos var32 = var2.hasAny() ? (BetterBlockPos)var2.getDatatypePost(RelativeBlockPos.INSTANCE, super.ctx.playerFeet()) : super.ctx.playerFeet();
            var2.requireMax(0);
            Waypoint var36 = new Waypoint(var23, var19, var32);
            ForWaypoints.waypoints(super.baritone).addWaypoint(var36);
            class_5250 var40;
            class_5250 var48 = var40 = class_2561.method_43470("Waypoint added: ");
            var48.method_10862(var48.method_10866().method_10977(class_124.field_1080));
            var40.method_10852((class_2561)var4.apply(var36, WaypointsCommand.Action.d));
            this.logDirect(new class_2561[]{var40});
         } else if (var3 == WaypointsCommand.Action.b) {
            var2.requireMax(1);
            String var18;
            IWaypoint.Tag var22;
            if ((var22 = IWaypoint.Tag.getByName(var18 = var2.getString())) == null) {
               throw new CommandInvalidStateException("Invalid tag, \"" + var18 + "\"");
            } else {
               IWaypoint[] var31;
               IWaypoint[] var34;
               for(IWaypoint var41 : var34 = var31 = ForWaypoints.getWaypointsByTag(super.baritone, var22)) {
                  ForWaypoints.waypoints(super.baritone).removeWaypoint(var41);
               }

               ((List)this.a.computeIfAbsent(super.baritone.getWorldProvider().getCurrentWorld(), (var0) -> new ArrayList())).addAll(Arrays.asList(var31));
               class_5250 var35;
               class_5250 var46 = var35 = class_2561.method_43470(String.format("Cleared %d waypoints, click to restore them", var31.length));
               var46.method_10862(var46.method_10866().method_10958(new class_2558.class_10609(String.format("%s%s restore @ %s", IBaritoneChatControl.FORCE_COMMAND_PREFIX, var1, Stream.of(var31).map((var0) -> Long.toString(var0.getCreationTimestamp())).collect(Collectors.joining(" "))))));
               this.logDirect(new class_2561[]{var35});
            }
         } else if (var3 == WaypointsCommand.Action.f) {
            ArrayList var17 = new ArrayList();
            List var21 = (List)this.a.getOrDefault(super.baritone.getWorldProvider().getCurrentWorld(), Collections.emptyList());
            if (var2.peekString().equals("@")) {
               var2.get();

               while(var2.hasAny()) {
                  long var29 = (Long)var2.getAs(Long.class);
                  Iterator var38 = var21.iterator();

                  while(var38.hasNext()) {
                     IWaypoint var15;
                     if ((var15 = (IWaypoint)var38.next()).getCreationTimestamp() == var29) {
                        var17.add(var15);
                        break;
                     }
                  }
               }
            } else {
               var2.requireExactly(1);
               int var30;
               int var33 = Math.min(var30 = var21.size(), (Integer)var2.getAs(Integer.class));
               var17 = new ArrayList(var21.subList(var30 - var33, var30));
            }

            IWaypointCollection var10001 = ForWaypoints.waypoints(super.baritone);
            Objects.requireNonNull(var10001);
            var17.forEach(var10001::addWaypoint);
            Objects.requireNonNull(var17);
            var21.removeIf(var17::contains);
            this.logDirect(String.format("Restored %d waypoints", var17.size()));
         } else {
            IWaypoint[] var6 = (IWaypoint[])var2.getDatatypeFor(ForWaypoints.INSTANCE);
            IWaypoint var7 = null;
            if (var2.hasAny() && var2.peekString().equals("@")) {
               var2.requireExactly(2);
               var2.get();
               long var8 = (Long)var2.getAs(Long.class);
               IWaypoint[] var10 = var6;
               int var13 = var6.length;

               for(int var11 = 0; var11 < var13; ++var11) {
                  IWaypoint var12;
                  if ((var12 = var10[var11]).getCreationTimestamp() == var8) {
                     var7 = var12;
                     break;
                  }
               }

               if (var7 == null) {
                  throw new CommandInvalidStateException("Timestamp was specified but no waypoint was found");
               }
            } else {
               switch (var6.length) {
                  case 0 -> throw new CommandInvalidStateException("No waypoints found");
                  case 1 -> var7 = var6[0];
               }
            }

            if (var7 == null) {
               var2.requireMax(1);
               Paginator.paginate(var2, var6, () -> this.logDirect("Multiple waypoints were found:"), var5, String.format("%s%s %s %s", IBaritoneChatControl.FORCE_COMMAND_PREFIX, var1, var3.a[0], var2.consumedString()));
            } else if (var3 == WaypointsCommand.Action.d) {
               this.logDirect(new class_2561[]{(class_2561)var5.apply(var7)});
               this.logDirect(String.format("Position: %s", var7.getLocation()));
               class_5250 var28;
               class_5250 var42 = var28 = class_2561.method_43470("Click to delete this waypoint");
               var42.method_10862(var42.method_10866().method_10958(new class_2558.class_10609(String.format("%s%s delete %s @ %d", IBaritoneChatControl.FORCE_COMMAND_PREFIX, var1, var7.getTag().getName(), var7.getCreationTimestamp()))));
               class_5250 var9;
               var42 = var9 = class_2561.method_43470("Click to set goal to this waypoint");
               var42.method_10862(var42.method_10866().method_10958(new class_2558.class_10609(String.format("%s%s goal %s @ %d", IBaritoneChatControl.FORCE_COMMAND_PREFIX, var1, var7.getTag().getName(), var7.getCreationTimestamp()))));
               class_5250 var37;
               var42 = var37 = class_2561.method_43470("Click to show a command to recreate this waypoint");
               var42.method_10862(var42.method_10866().method_10958(new class_2558.class_10610(String.format("%s%s save %s %s %s %s %s", Baritone.a().prefix.value, var1, var7.getTag().getName(), var7.getName(), var7.getLocation().x, var7.getLocation().y, var7.getLocation().z))));
               class_5250 var14;
               var42 = var14 = class_2561.method_43470("Click to return to the waypoints list");
               var42.method_10862(var42.method_10866().method_10958(new class_2558.class_10609(String.format("%s%s list", IBaritoneChatControl.FORCE_COMMAND_PREFIX, var1))));
               this.logDirect(new class_2561[]{var28});
               this.logDirect(new class_2561[]{var9});
               this.logDirect(new class_2561[]{var37});
               this.logDirect(new class_2561[]{var14});
            } else if (var3 == WaypointsCommand.Action.e) {
               ForWaypoints.waypoints(super.baritone).removeWaypoint(var7);
               ((List)this.a.computeIfAbsent(super.baritone.getWorldProvider().getCurrentWorld(), (var0) -> new ArrayList())).add(var7);
               class_5250 var27;
               class_5250 var10000 = var27 = class_2561.method_43470("That waypoint has successfully been deleted, click to restore it");
               var10000.method_10862(var10000.method_10866().method_10958(new class_2558.class_10609(String.format("%s%s restore @ %s", IBaritoneChatControl.FORCE_COMMAND_PREFIX, var1, var7.getCreationTimestamp()))));
               this.logDirect(new class_2561[]{var27});
            } else if (var3 == WaypointsCommand.Action.g) {
               GoalBlock var26 = new GoalBlock(var7.getLocation());
               super.baritone.getCustomGoalProcess().setGoal(var26);
               this.logDirect(String.format("Goal: %s", var26));
            } else {
               if (var3 == WaypointsCommand.Action.h) {
                  GoalBlock var25 = new GoalBlock(var7.getLocation());
                  super.baritone.getCustomGoalProcess().setGoalAndPath(var25);
                  this.logDirect(String.format("Going to: %s", var25));
               }

            }
         }
      }
   }

   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      if (var2.hasAny()) {
         if (var2.hasExactlyOne()) {
            return (new TabCompleteHelper()).append(WaypointsCommand.Action.a()).sortAlphabetically().filterPrefix(var2.getString()).stream();
         }

         Action var3 = WaypointsCommand.Action.a(var2.getString());
         if (var2.hasExactlyOne()) {
            if (var3 != WaypointsCommand.Action.a && var3 != WaypointsCommand.Action.c && var3 != WaypointsCommand.Action.b) {
               if (var3 == WaypointsCommand.Action.f) {
                  return Stream.empty();
               }

               return var2.tabCompleteDatatype(ForWaypoints.INSTANCE);
            }

            return (new TabCompleteHelper()).append(IWaypoint.Tag.getAllNames()).sortAlphabetically().filterPrefix(var2.getString()).stream();
         }

         if (var2.has(3) && var3 == WaypointsCommand.Action.c) {
            var2.get();
            var2.get();
            return var2.tabCompleteDatatype(RelativeBlockPos.INSTANCE);
         }
      }

      return Stream.empty();
   }

   public String getShortDesc() {
      return "Manage waypoints";
   }

   public List<String> getLongDesc() {
      return Arrays.asList("The waypoint command allows you to manage Baritone's waypoints.", "", "Waypoints can be used to mark positions for later. Waypoints are each given a tag and an optional name.", "", "Note that the info, delete, and goal commands let you specify a waypoint by tag. If there is more than one waypoint with a certain tag, then they will let you select which waypoint you mean.", "", "Missing arguments for the save command use the USER tag, creating an unnamed waypoint and your current position as defaults.", "", "Usage:", "> wp [l/list] - List all waypoints.", "> wp <l/list> <tag> - List all waypoints by tag.", "> wp <s/save> - Save an unnamed USER waypoint at your current position", "> wp <s/save> [tag] [name] [pos] - Save a waypoint with the specified tag, name and position.", "> wp <i/info/show> <tag/name> - Show info on a waypoint by tag or name.", "> wp <d/delete> <tag/name> - Delete a waypoint by tag or name.", "> wp <restore> <n> - Restore the last n deleted waypoints.", "> wp <c/clear> <tag> - Delete all waypoints with the specified tag.", "> wp <g/goal> <tag/name> - Set a goal to a waypoint by tag or name.", "> wp <goto> <tag/name> - Set a goal to a waypoint by tag or name and start pathing.");
   }

   static enum Action {
      a(new String[]{"list", "get", "l"}),
      b(new String[]{"clear", "c"}),
      c(new String[]{"save", "s"}),
      d(new String[]{"info", "show", "i"}),
      e(new String[]{"delete", "d"}),
      f(new String[]{"restore"}),
      g(new String[]{"goal", "g"}),
      h(new String[]{"goto"});

      final String[] a;

      private Action(String... var3) {
         this.a = var3;
      }

      public static Action a(String var0) {
         Action[] var1;
         int var2 = (var1 = values()).length;

         for(int var3 = 0; var3 < var2; ++var3) {
            Action var4;
            String[] var5;
            int var6 = (var5 = (var4 = var1[var3]).a).length;

            for(int var7 = 0; var7 < var6; ++var7) {
               if (var5[var7].equalsIgnoreCase(var0)) {
                  return var4;
               }
            }
         }

         return null;
      }

      public static String[] a() {
         HashSet var0 = new HashSet();

         Action[] var1;
         for(Action var4 : var1 = values()) {
            var0.addAll(Arrays.asList(var4.a));
         }

         return (String[])var0.toArray(new String[0]);
      }
   }
}
