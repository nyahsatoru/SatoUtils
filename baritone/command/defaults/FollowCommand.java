package baritone.command.defaults;

import baritone.Baritone;
import baritone.KeepName;
import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.command.datatypes.EntityClassById;
import baritone.api.command.datatypes.IDatatypeFor;
import baritone.api.command.datatypes.NearbyPlayer;
import baritone.api.command.exception.CommandErrorMessageException;
import baritone.api.command.helpers.TabCompleteHelper;
import baritone.api.process.IFollowProcess;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_2960;
import net.minecraft.class_7922;
import net.minecraft.class_7923;

public class FollowCommand extends Command {
   public FollowCommand(Baritone var1) {
      super(var1, "follow");
   }

   public void execute(String var1, IArgConsumer var2) {
      var2.requireMin(1);
      ArrayList var4 = new ArrayList();
      ArrayList var5 = new ArrayList();
      FollowGroup var7;
      IFollowProcess var10000;
      Predicate var10001;
      if (var2.hasExactlyOne()) {
         var10000 = super.baritone.getFollowProcess();
         var10001 = (var7 = (FollowGroup)var2.getEnum(FollowGroup.class)).a;
      } else {
         var2.requireMin(2);
         var7 = null;
         FollowList var3 = (FollowList)var2.getEnum(FollowList.class);

         while(var2.hasAny()) {
            Object var6;
            if ((var6 = var2.getDatatypeFor(var3.a)) instanceof class_1299) {
               var5.add((class_1299)var6);
            } else if (var6 != null) {
               var4.add((class_1297)var6);
            }
         }

         var10000 = super.baritone.getFollowProcess();
         if (var5.isEmpty()) {
            Objects.requireNonNull(var4);
            var10001 = var4::contains;
         } else {
            var10001 = (var1x) -> var5.stream().anyMatch((var1) -> var1x.method_5864().equals(var1));
         }
      }

      var10000.follow(var10001);
      if (var7 != null) {
         this.logDirect(String.format("Following all %s", var7.name().toLowerCase(Locale.US)));
      } else if (var5.isEmpty()) {
         if (var4.isEmpty()) {
            throw new NoEntitiesException();
         } else {
            this.logDirect("Following these entities:");
            var4.stream().map(class_1297::toString).forEach(this::logDirect);
         }
      } else {
         this.logDirect("Following these types of entities:");
         Stream var8 = var5.stream();
         class_7922 var9 = class_7923.field_41177;
         Objects.requireNonNull(var9);
         var8.map(var9::method_10221).map(Objects::requireNonNull).map(class_2960::toString).forEach(this::logDirect);
      }
   }

   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      if (var2.hasExactlyOne()) {
         return (new TabCompleteHelper()).append(FollowGroup.class).append(FollowList.class).filterPrefix(var2.getString()).stream();
      } else {
         try {
            var4 = ((FollowList)var2.getEnum(FollowList.class)).a;
         } catch (NullPointerException var3) {
            return Stream.empty();
         }

         while(var2.has(2)) {
            if (var2.peekDatatypeOrNull(var4) == null) {
               return Stream.empty();
            }

            var2.get();
         }

         return var2.tabCompleteDatatype(var4);
      }
   }

   public String getShortDesc() {
      return "Follow entity things";
   }

   public List<String> getLongDesc() {
      return Arrays.asList("The follow command tells Baritone to follow certain kinds of entities.", "", "Usage:", "> follow entities - Follows all entities.", "> follow entity <entity1> <entity2> <...> - Follow certain entities (for example 'skeleton', 'horse' etc.)", "> follow players - Follow players", "> follow player <username1> <username2> <...> - Follow certain players");
   }

   @KeepName
   static enum FollowGroup {
      a,
      b;

      final Predicate<class_1297> a;

      private FollowGroup(Predicate<class_1297> var3) {
         this.a = var3;
      }

      static {
         Objects.requireNonNull(class_1309.class);
         a = new FollowGroup("ENTITIES", 0, class_1309.class::isInstance);
         Objects.requireNonNull(class_1657.class);
         b = new FollowGroup("PLAYERS", 1, class_1657.class::isInstance);
      }
   }

   @KeepName
   static enum FollowList {
      a(EntityClassById.INSTANCE),
      b(NearbyPlayer.INSTANCE);

      final IDatatypeFor a;

      private FollowList(IDatatypeFor var3) {
         this.a = var3;
      }
   }

   public static class NoEntitiesException extends CommandErrorMessageException {
      protected NoEntitiesException() {
         super("No valid entities in range!");
      }
   }
}
