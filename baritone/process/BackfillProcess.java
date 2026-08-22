package baritone.process;

import baritone.Baritone;
import baritone.api.pathing.goals.Goal;
import baritone.api.process.PathingCommand;
import baritone.api.process.PathingCommandType;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.Rotation;
import baritone.api.utils.input.Input;
import baritone.pathing.movement.Movement;
import baritone.pathing.movement.MovementHelper;
import baritone.pathing.movement.MovementState;
import baritone.pathing.path.PathExecutor;
import baritone.utils.BaritoneProcessHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_2680;
import net.minecraft.class_2812;

public final class BackfillProcess extends BaritoneProcessHelper {
   private HashMap<class_2338, class_2680> a = new HashMap();

   public BackfillProcess(Baritone var1) {
      super(var1);
   }

   public final boolean isActive() {
      if (super.a.player() != null && super.a.world() != null) {
         if (!(Boolean)Baritone.a().backfill.value) {
            return false;
         } else if ((Boolean)Baritone.a().allowParkour.value) {
            this.logDirect("Backfill cannot be used with allowParkour true");
            Baritone.a().backfill.value = (T)Boolean.FALSE;
            return false;
         } else {
            for(class_2338 var2 : new ArrayList(this.a.keySet())) {
               if (super.a.world().method_22350(var2) instanceof class_2812 || super.a.world().method_8320(var2).method_26204() != class_2246.field_10124) {
                  this.a.remove(var2);
               }
            }

            if (super.a.getSelectedBlock().isPresent() && super.a.a.isPathing()) {
               this.a.put((class_2338)super.a.getSelectedBlock().get(), super.a.world().method_8320((class_2338)super.a.getSelectedBlock().get()));
            }

            super.a.a.clearAllKeys();
            if (!this.a().isEmpty()) {
               return true;
            } else {
               return false;
            }
         }
      } else {
         return false;
      }
   }

   public final PathingCommand onTick(boolean var1, boolean var2) {
      if (!var2) {
         return new PathingCommand((Goal)null, PathingCommandType.REQUEST_PAUSE);
      } else {
         super.a.a.clearAllKeys();

         for(class_2338 var5 : this.a()) {
            MovementState var3 = new MovementState();
            switch (MovementHelper.a(var3, super.a, var5, false, false)) {
               case c:
                  break;
               case a:
                  super.a.a.setInputForceState(Input.CLICK_RIGHT, true);
                  return new PathingCommand((Goal)null, PathingCommandType.REQUEST_PAUSE);
               case b:
                  super.a.a.updateTarget((Rotation)Optional.ofNullable(var3.a.a).get(), true);
                  return new PathingCommand((Goal)null, PathingCommandType.REQUEST_PAUSE);
               default:
                  throw new IllegalStateException();
            }
         }

         return new PathingCommand((Goal)null, PathingCommandType.DEFER);
      }
   }

   private List<class_2338> a() {
      Stream var10000 = this.a.keySet().stream().filter((var1) -> super.a.world().method_8320(var1).method_26204() == class_2246.field_10124).filter((var1) -> super.a.a.a(var1, class_2246.field_10566.method_9564())).filter((var1) -> {
         class_2338 var2 = var1;
         PathExecutor var3;
         return !((var3 = super.a.a.a) != null && !var3.b() && !var3.a ? Arrays.asList(((Movement)var3.getPath().movements().get(var3.getPosition())).a).contains(var2) : false);
      });
      BetterBlockPos var10001 = super.a.playerFeet();
      Objects.requireNonNull(var10001);
      return (List)var10000.sorted(Comparator.comparingDouble(var10001::method_10262).reversed()).collect(Collectors.toList());
   }

   public final void onLostControl() {
      if (this.a != null && !this.a.isEmpty()) {
         this.a.clear();
      }

   }

   public final String displayName0() {
      return "Backfill";
   }

   public final boolean isTemporary() {
      return true;
   }

   public final double priority() {
      return (double)5.0F;
   }
}
