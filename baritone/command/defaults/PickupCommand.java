package baritone.command.defaults;

import baritone.Baritone;
import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.command.datatypes.ItemById;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import net.minecraft.class_1792;
import net.minecraft.class_2960;
import net.minecraft.class_7922;
import net.minecraft.class_7923;

public class PickupCommand extends Command {
   public PickupCommand(Baritone var1) {
      super(var1, "pickup");
   }

   public void execute(String var1, IArgConsumer var2) {
      HashSet var4 = new HashSet();

      while(var2.hasAny()) {
         class_1792 var3 = (class_1792)var2.getDatatypeFor(ItemById.INSTANCE);
         var4.add(var3);
      }

      if (var4.isEmpty()) {
         super.baritone.getFollowProcess().pickup((var0) -> true);
         this.logDirect("Picking up all items");
      } else {
         super.baritone.getFollowProcess().pickup((var1x) -> var4.contains(var1x.method_7909()));
         this.logDirect("Picking up these items:");
         Stream var10000 = var4.stream();
         class_7922 var10001 = class_7923.field_41178;
         Objects.requireNonNull(var10001);
         var10000.map(var10001::method_10221).map(class_2960::toString).forEach(this::logDirect);
      }
   }

   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      while(var2.has(2)) {
         if (var2.peekDatatypeOrNull(ItemById.INSTANCE) == null) {
            return Stream.empty();
         }

         var2.get();
      }

      return var2.tabCompleteDatatype(ItemById.INSTANCE);
   }

   public String getShortDesc() {
      return "Pickup items";
   }

   public List<String> getLongDesc() {
      return Arrays.asList("Usage:", "> pickup - Pickup anything", "> pickup <item1> <item2> <...> - Pickup certain items");
   }
}
