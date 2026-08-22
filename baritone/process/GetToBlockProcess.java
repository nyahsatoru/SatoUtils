package baritone.process;

import baritone.Baritone;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalBlock;
import baritone.api.pathing.goals.GoalComposite;
import baritone.api.pathing.goals.GoalGetToBlock;
import baritone.api.pathing.goals.GoalRunAway;
import baritone.api.pathing.goals.GoalTwoBlocks;
import baritone.api.process.IGetToBlockProcess;
import baritone.api.process.PathingCommand;
import baritone.api.process.PathingCommandType;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.BlockOptionalMeta;
import baritone.api.utils.BlockOptionalMetaLookup;
import baritone.api.utils.Rotation;
import baritone.api.utils.RotationUtils;
import baritone.api.utils.input.Input;
import baritone.pathing.movement.CalculationContext;
import baritone.pathing.movement.MovementHelper;
import baritone.utils.BaritoneProcessHelper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.class_1723;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2680;

public final class GetToBlockProcess extends BaritoneProcessHelper implements IGetToBlockProcess {
   private BlockOptionalMeta a;
   private List<class_2338> a;
   private List<class_2338> b;
   private BetterBlockPos a;
   private int a = 0;
   private int b = 0;

   public GetToBlockProcess(Baritone var1) {
      super(var1);
   }

   public final void getToBlock(BlockOptionalMeta var1) {
      this.onLostControl();
      this.a = var1;
      this.a = super.a.playerFeet();
      this.b = new ArrayList();
      this.b = 0;
      this.a(new ArrayList(), new GetToBlockCalculationContext(false));
   }

   public final boolean isActive() {
      return this.a != null;
   }

   public final synchronized PathingCommand onTick(boolean var1, boolean var2) {
      if (this.a == null) {
         this.a(new ArrayList(), new GetToBlockCalculationContext(false));
      }

      if (this.a.isEmpty()) {
         if ((Boolean)Baritone.a().exploreForBlocks.value && !var1) {
            return new PathingCommand(new GoalRunAway(new class_2338[]{this.a}) {
               public boolean isInGoal(int var1, int var2, int var3) {
                  return false;
               }

               public double heuristic() {
                  return Double.NEGATIVE_INFINITY;
               }
            }, PathingCommandType.FORCE_REVALIDATE_GOAL_AND_PATH);
         } else {
            this.logDirect("No known locations of " + String.valueOf(this.a) + ", canceling GetToBlock");
            if (var2) {
               this.onLostControl();
            }

            return new PathingCommand((Goal)null, PathingCommandType.CANCEL_AND_SET_GOAL);
         }
      } else {
         GoalComposite var3 = new GoalComposite((Goal[])this.a.stream().map(this::a).toArray((var0) -> new Goal[var0]));
         if (var1) {
            if ((Boolean)Baritone.a().blacklistClosestOnFailure.value) {
               this.logDirect("Unable to find any path to " + String.valueOf(this.a) + ", blacklisting presumably unreachable closest instances...");
               this.blacklistClosest();
               return this.onTick(false, var2);
            } else {
               this.logDirect("Unable to find any path to " + String.valueOf(this.a) + ", canceling GetToBlock");
               if (var2) {
                  this.onLostControl();
               }

               return new PathingCommand(var3, PathingCommandType.CANCEL_AND_SET_GOAL);
            }
         } else {
            if ((var1 = (Integer)Baritone.a().mineGoalUpdateInterval.value) != 0 && this.a++ % var1 == 0) {
               ArrayList var6 = new ArrayList(this.a);
               GetToBlockCalculationContext var4 = new GetToBlockCalculationContext(true);
               Baritone.a().execute(() -> this.a(var6, var4));
            }

            if (var3.isInGoal(super.a.playerFeet()) && var3.isInGoal(super.a.a.a()) && var2) {
               if (!a(this.a.getBlock())) {
                  this.onLostControl();
                  return new PathingCommand((Goal)null, PathingCommandType.CANCEL_AND_SET_GOAL);
               }

               GetToBlockProcess var7 = this;
               Iterator var8 = this.a.iterator();

               boolean var10000;
               while(true) {
                  if (!var8.hasNext()) {
                     var7.logDirect("Arrived but failed to right click open");
                     var10000 = true;
                     break;
                  }

                  class_2338 var9 = (class_2338)var8.next();
                  Optional var10;
                  if ((var10 = RotationUtils.reachable(var7.a, var9, var7.a.playerController().getBlockReachDistance())).isPresent()) {
                     var7.a.a.updateTarget((Rotation)var10.get(), true);
                     if (var7.a.contains(var7.a.getSelectedBlock().orElse((Object)null))) {
                        var7.a.a.setInputForceState(Input.CLICK_RIGHT, true);
                        System.out.println(var7.a.player().field_7512);
                        if (!(var7.a.player().field_7512 instanceof class_1723)) {
                           var10000 = true;
                           break;
                        }
                     }

                     if (var7.b++ > 20) {
                        var7.logDirect("Right click timed out");
                        var10000 = true;
                     } else {
                        var10000 = false;
                     }
                     break;
                  }
               }

               if (var10000) {
                  this.onLostControl();
                  return new PathingCommand((Goal)null, PathingCommandType.CANCEL_AND_SET_GOAL);
               }
            }

            return new PathingCommand(var3, PathingCommandType.REVALIDATE_GOAL_AND_PATH);
         }
      }
   }

   public final synchronized boolean blacklistClosest() {
      ArrayList var1 = new ArrayList();
      Stream var10000 = this.a.stream();
      BetterBlockPos var10001 = super.a.playerFeet();
      Objects.requireNonNull(var10001);
      Optional var10 = var10000.min(Comparator.comparingDouble(var10001::method_10262));
      Objects.requireNonNull(var1);
      var10.ifPresent(var1::add);

      label33:
      while(true) {
         for(class_2338 var3 : this.a) {
            for(class_2338 var5 : var1) {
               int var7 = Math.abs(var3.method_10263() - var5.method_10263());
               int var8 = Math.abs(var3.method_10264() - var5.method_10264());
               int var9 = Math.abs(var3.method_10260() - var5.method_10260());
               if (var7 + var8 + var9 == 1) {
                  var1.add(var3);
                  this.a.remove(var3);
                  continue label33;
               }
            }
         }

         var1.size();
         this.logDebug("Blacklisting unreachable locations " + String.valueOf(var1));
         this.b.addAll(var1);
         if (!var1.isEmpty()) {
            return true;
         }

         return false;
      }
   }

   public final synchronized void onLostControl() {
      this.a = null;
      this.a = null;
      this.a = null;
      this.b = null;
      super.a.a.clearAllKeys();
   }

   public final String displayName0() {
      if (this.a.isEmpty()) {
         return "Exploring randomly to find " + String.valueOf(this.a) + ", no known locations";
      } else {
         String var10000 = String.valueOf(this.a);
         return "Get To " + var10000 + ", " + this.a.size() + " known locations";
      }
   }

   private synchronized void a(List<class_2338> var1, CalculationContext var2) {
      List var3;
      List var10000 = var3 = MineProcess.a(var2, new BlockOptionalMetaLookup(new BlockOptionalMeta[]{this.a}), 64, var1, this.b, Collections.emptyList());
      List var10001 = this.b;
      Objects.requireNonNull(var10001);
      var10000.removeIf(var10001::contains);
      this.a = var3;
   }

   private Goal a(class_2338 var1) {
      class_2248 var2 = this.a.getBlock();
      if ((Boolean)Baritone.a().enterPortal.value && var2 == class_2246.field_10316) {
         return new GoalTwoBlocks(var1);
      } else {
         return (Goal)(a(var2 = this.a.getBlock()) && (var2 == class_2246.field_10443 || var2 == class_2246.field_10034 || var2 == class_2246.field_10380) && MovementHelper.h(super.a.a.a(var1.method_10084())) ? new GoalBlock(var1.method_10084()) : new GoalGetToBlock(var1));
      }
   }

   private static boolean a(class_2248 var0) {
      if (!(Boolean)Baritone.a().rightClickContainerOnArrival.value) {
         return false;
      } else {
         return var0 == class_2246.field_9980 || var0 == class_2246.field_10181 || var0 == class_2246.field_16333 || var0 == class_2246.field_10443 || var0 == class_2246.field_10034 || var0 == class_2246.field_10380;
      }
   }

   // $FF: synthetic method
   static Baritone a(GetToBlockProcess var0) {
      return var0.a;
   }

   public class GetToBlockCalculationContext extends CalculationContext {
      public GetToBlockCalculationContext(boolean var2) {
         super(GetToBlockProcess.a((GetToBlockProcess)GetToBlockProcess.this), var2);
      }

      public final double b(int var1, int var2, int var3, class_2680 var4) {
         return (double)1.0F;
      }
   }
}
