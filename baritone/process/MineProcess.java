package baritone.process;

import baritone.Baritone;
import baritone.api.BaritoneAPI;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalBlock;
import baritone.api.pathing.goals.GoalComposite;
import baritone.api.pathing.goals.GoalRunAway;
import baritone.api.pathing.goals.GoalTwoBlocks;
import baritone.api.process.IMineProcess;
import baritone.api.process.PathingCommand;
import baritone.api.process.PathingCommandType;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.BlockOptionalMeta;
import baritone.api.utils.BlockOptionalMetaLookup;
import baritone.api.utils.BlockUtils;
import baritone.api.utils.Rotation;
import baritone.api.utils.RotationUtils;
import baritone.api.utils.SettingsUtil;
import baritone.api.utils.input.Input;
import baritone.cache.CachedChunk;
import baritone.pathing.movement.CalculationContext;
import baritone.pathing.movement.MovementHelper;
import baritone.utils.BaritoneProcessHelper;
import baritone.utils.BlockStateInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.class_1297;
import net.minecraft.class_1542;
import net.minecraft.class_1799;
import net.minecraft.class_2189;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2346;
import net.minecraft.class_2680;
import net.minecraft.class_638;

public final class MineProcess extends BaritoneProcessHelper implements IMineProcess {
   private BlockOptionalMetaLookup a;
   private List<class_2338> a;
   private List<class_2338> b;
   private Map<class_2338, Long> a;
   private BetterBlockPos a;
   private <undefinedtype> a;
   private int a;
   private int b;

   public MineProcess(Baritone var1) {
      super(var1);
   }

   public final boolean isActive() {
      return this.a != null;
   }

   public final PathingCommand onTick(boolean var1, boolean var2) {
      int var3;
      if (this.a > 0 && (var3 = super.a.player().method_31548().method_67533().stream().filter((var1x) -> this.a.has(var1x)).mapToInt(class_1799::method_7947).sum()) >= this.a) {
         this.logDirect("Have " + var3 + " valid items");
         this.cancel();
         return null;
      } else {
         if (var1) {
            if (this.a.isEmpty() || !(Boolean)Baritone.a().blacklistClosestOnFailure.value) {
               this.logDirect("Unable to find any path to " + String.valueOf(this.a) + ", canceling mine");
               if ((Boolean)Baritone.a().notificationOnMineFail.value) {
                  this.logNotification("Unable to find any path to " + String.valueOf(this.a) + ", canceling mine", true);
               }

               this.cancel();
               return null;
            }

            this.logDirect("Unable to find any path to " + String.valueOf(this.a) + ", blacklisting presumably unreachable closest instance...");
            if ((Boolean)Baritone.a().notificationOnMineFail.value) {
               this.logNotification("Unable to find any path to " + String.valueOf(this.a) + ", blacklisting presumably unreachable closest instance...", true);
            }

            Stream var10000 = this.a.stream();
            BetterBlockPos var10001 = super.a.playerFeet();
            Objects.requireNonNull(var10001);
            Optional var31 = var10000.min(Comparator.comparingDouble(var10001::method_10262));
            List var36 = this.b;
            Objects.requireNonNull(var36);
            var31.ifPresent(var36::add);
            List var32 = this.a;
            var36 = this.b;
            Objects.requireNonNull(var36);
            var32.removeIf(var36::contains);
         }

         HashMap var4 = new HashMap(this.a);
         super.a.getSelectedBlock().ifPresent((var2x) -> {
            if (this.a.contains(var2x)) {
               var4.put(var2x, System.currentTimeMillis() + (Long)Baritone.a().mineDropLoiterDurationMSThanksLouca.value);
            }

         });

         for(class_2338 var6 : this.a.keySet()) {
            if ((Long)var4.get(var6) < System.currentTimeMillis()) {
               var4.remove(var6);
            }
         }

         this.a = var4;
         var3 = (Integer)Baritone.a().mineGoalUpdateInterval.value;
         ArrayList var12 = new ArrayList(this.a);
         if (var3 != 0 && this.b++ % var3 == 0) {
            CalculationContext var18 = new CalculationContext(super.a, true);
            Baritone.a().execute(() -> this.a(var12, var18));
         }

         if ((Boolean)Baritone.a().legitMine.value) {
            MineProcess var19 = this;
            List var23 = this.a();
            this.a.addAll(var23);
            BetterBlockPos var25 = super.a.playerFeet();
            BlockStateInterface var27 = new BlockStateInterface(super.a);
            BlockOptionalMetaLookup var7;
            boolean var33;
            if ((var7 = this.a()) == null) {
               var33 = false;
            } else {
               for(int var8 = ((class_2338)var25).method_10263() - 10; var8 <= ((class_2338)var25).method_10263() + 10; ++var8) {
                  for(int var9 = ((class_2338)var25).method_10264() - 10; var9 <= ((class_2338)var25).method_10264() + 10; ++var9) {
                     for(int var10 = ((class_2338)var25).method_10260() - 10; var10 <= ((class_2338)var25).method_10260() + 10; ++var10) {
                        if (var7.has(var27.a(var8, var9, var10))) {
                           class_2338 var11 = new class_2338(var8, var9, var10);
                           if ((Boolean)Baritone.a().legitMineIncludeDiagonals.value && var19.a.stream().anyMatch((var1x) -> var1x.method_10262(var11) <= (double)2.0F) || RotationUtils.reachable(var19.a, var11, (double)20.0F).isPresent()) {
                              var19.a.add(var11);
                           }
                        }
                     }
                  }
               }

               var19.a = a(new CalculationContext(var19.a), var19.a, var7, (Integer)Baritone.a().mineMaxOreLocationsCount.value, var19.b, var23);
               var33 = true;
            }

            if (!var33) {
               this.cancel();
               return null;
            }
         }

         Stream var34 = var12.stream().filter((var1x) -> var1x.method_10263() == super.a.playerFeet().method_10263() && var1x.method_10260() == super.a.playerFeet().method_10260()).filter((var1x) -> var1x.method_10264() >= super.a.playerFeet().method_10264()).filter((var1x) -> !(BlockStateInterface.a(super.a, var1x).method_26204() instanceof class_2189));
         BetterBlockPos var38 = super.a.playerFeet().above();
         Objects.requireNonNull(var38);
         Optional var20 = var34.min(Comparator.comparingDouble(var38::method_10262));
         super.a.a.clearAllKeys();
         if (var20.isPresent() && super.a.player().method_24828()) {
            class_2338 var13 = (class_2338)var20.get();
            class_2680 var21 = super.a.a.a(var13);
            Optional var22;
            if (!MovementHelper.a(super.a.a, var13.method_10263(), var13.method_10264(), var13.method_10260(), var21) && (var22 = RotationUtils.reachable(super.a, var13)).isPresent() && var2) {
               super.a.a.updateTarget((Rotation)var22.get(), true);
               MovementHelper.a(super.a, super.a.world().method_8320(var13));
               if (super.a.isLookingAt(var13) || super.a.playerRotations().isReallyCloseTo((Rotation)var22.get())) {
                  super.a.a.setInputForceState(Input.CLICK_LEFT, true);
               }

               return new PathingCommand((Goal)null, PathingCommandType.REQUEST_PAUSE);
            }
         }

         BlockOptionalMetaLookup var24;
         PathingCommand var35;
         if ((var24 = this.a()) == null) {
            var35 = null;
         } else {
            boolean var26 = (Boolean)Baritone.a().legitMine.value;
            List var28;
            if (!(var28 = this.a).isEmpty()) {
               CalculationContext var29;
               List var14 = a(var29 = new CalculationContext(super.a), new ArrayList(var28), var24, (Integer)Baritone.a().mineMaxOreLocationsCount.value, this.b, this.a());
               GoalComposite var16 = new GoalComposite((Goal[])var14.stream().map((var3x) -> {
                  boolean var5 = !(super.a.a.a(var3x.method_10084()).method_26204() instanceof class_2346);
                  if (!(Boolean)Baritone.a().forceInternalMining.value) {
                     return (Goal)(var5 ? new GoalThreeBlocks(var3x) : new GoalTwoBlocks(var3x));
                  } else {
                     boolean var6 = this.a(var3x.method_10084(), var29, var14);
                     boolean var7 = this.a(var3x.method_10074(), var29, var14);
                     boolean var8 = this.a(var3x.method_10087(2), var29, var14);
                     if (var6 == var7) {
                        return (Goal)(var8 && var5 ? new GoalThreeBlocks(var3x) : new GoalTwoBlocks(var3x));
                     } else if (var6) {
                        return new GoalBlock(var3x);
                     } else {
                        return (Goal)(var8 && var5 ? new GoalTwoBlocks(var3x.method_10074()) : new GoalBlock(var3x.method_10074()));
                     }
                  }
               }).toArray((var0) -> new Goal[var0]));
               this.a = var14;
               var35 = new PathingCommand(var16, var26 ? PathingCommandType.FORCE_REVALIDATE_GOAL_AND_PATH : PathingCommandType.REVALIDATE_GOAL_AND_PATH);
            } else if (!var26 && !(Boolean)Baritone.a().exploreForBlocks.value) {
               var35 = null;
            } else {
               int var30 = (Integer)Baritone.a().legitMineYLevel.value;
               if (this.a == null) {
                  this.a = super.a.playerFeet();
               }

               if (this.a == null) {
                  this.a = new GoalRunAway(var30, new class_2338[]{this.a}) {
                     public boolean isInGoal(int var1, int var2, int var3) {
                        return false;
                     }

                     public double heuristic() {
                        return Double.NEGATIVE_INFINITY;
                     }
                  };
               }

               var35 = new PathingCommand(this.a, PathingCommandType.REVALIDATE_GOAL_AND_PATH);
            }
         }

         PathingCommand var15 = var35;
         if (var35 == null) {
            this.cancel();
            return null;
         } else {
            return var15;
         }
      }
   }

   public final void onLostControl() {
      this.mine(0, (BlockOptionalMetaLookup)null);
   }

   public final String displayName0() {
      return "Mine " + String.valueOf(this.a);
   }

   private void a(List<class_2338> var1, CalculationContext var2) {
      BlockOptionalMetaLookup var3;
      if ((var3 = this.a()) != null) {
         if (!(Boolean)Baritone.a().legitMine.value) {
            List var4 = this.a();
            List var5;
            (var5 = a(var2, var3, (Integer)Baritone.a().mineMaxOreLocationsCount.value, var1, this.b, var4)).addAll(var4);
            if (var5.isEmpty() && !(Boolean)Baritone.a().exploreForBlocks.value) {
               this.logDirect("No locations for " + String.valueOf(var3) + " known, cancelling");
               if ((Boolean)Baritone.a().notificationOnMineFail.value) {
                  this.logNotification("No locations for " + String.valueOf(var3) + " known, cancelling", true);
               }

               this.cancel();
            } else {
               this.a = var5;
            }
         }
      }
   }

   private boolean a(class_2338 var1, CalculationContext var2, List<class_2338> var3) {
      if (var3.contains(var1)) {
         return true;
      } else {
         class_2680 var4 = var2.a.a(var1);
         if ((Boolean)Baritone.a().internalMiningAirException.value && var4.method_26204() instanceof class_2189) {
            return true;
         } else {
            return this.a.has(var4) && a(var2, var1);
         }
      }
   }

   private List<class_2338> a() {
      if (!(Boolean)Baritone.a().mineScanDroppedItems.value) {
         return Collections.emptyList();
      } else {
         ArrayList var1 = new ArrayList();
         Iterator var2 = ((class_638)super.a.world()).method_18112().iterator();

         while(var2.hasNext()) {
            class_1297 var3;
            if ((var3 = (class_1297)var2.next()) instanceof class_1542) {
               class_1542 var4 = (class_1542)var3;
               if (this.a.has(var4.method_6983())) {
                  var1.add(var3.method_24515());
               }
            }
         }

         var1.addAll(this.a.keySet());
         return var1;
      }
   }

   public static List<class_2338> a(CalculationContext var0, BlockOptionalMetaLookup var1, int var2, List<class_2338> var3, List<class_2338> var4, List<class_2338> var5) {
      List var6 = new ArrayList();
      ArrayList var7 = new ArrayList();
      Iterator var8 = var1.blocks().iterator();

      while(var8.hasNext()) {
         class_2248 var9 = ((BlockOptionalMeta)var8.next()).getBlock();
         if (CachedChunk.a.contains(var9)) {
            BetterBlockPos var10 = var0.a.getPlayerContext().playerFeet();
            var6.addAll(var0.a.getCachedWorld().getLocationsOf(BlockUtils.blockToString(var9), (Integer)Baritone.a().maxCachedWorldScanCount.value, var10.x, var10.z, 2));
         } else {
            var7.add(var9);
         }
      }

      var6 = a(var0, var6, var1, var2, var4, var5);
      if (!var7.isEmpty() || (Boolean)Baritone.a().extendCacheOnThreshold.value && var6.size() < var2) {
         var6.addAll(BaritoneAPI.getProvider().getWorldScanner().scanChunkRadius(var0.a.getPlayerContext(), var1, var2, 10, 32));
      }

      var6.addAll(var3);
      return a(var0, var6, var1, var2, var4, var5);
   }

   private static List<class_2338> a(CalculationContext var0, List<class_2338> var1, BlockOptionalMetaLookup var2, int var3, List<class_2338> var4, List<class_2338> var5) {
      var5.removeIf((var3x) -> {
         Iterator var5 = var1.iterator();

         while(var5.hasNext()) {
            class_2338 var4;
            if ((var4 = (class_2338)var5.next()).method_10262(var3x) <= (double)9.0F && var2.has(var0.a(var4.method_10263(), var4.method_10264(), var4.method_10260())) && a(var0, var4)) {
               return true;
            }
         }

         return false;
      });
      Stream var10000 = var1.stream().distinct().filter((var3x) -> !var0.a.a(var3x.method_10263(), var3x.method_10260()) || var2.has(var0.a(var3x.method_10263(), var3x.method_10264(), var3x.method_10260())) || var5.contains(var3x)).filter((var1x) -> a(var0, var1x)).filter((var1x) -> {
         if (!(Boolean)Baritone.a().allowOnlyExposedOres.value) {
            return true;
         } else {
            int var2;
            for(int var3 = -(var2 = (Integer)Baritone.a().allowOnlyExposedOresDistance.value); var3 <= var2; ++var3) {
               for(int var4 = -var2; var4 <= var2; ++var4) {
                  for(int var5 = -var2; var5 <= var2; ++var5) {
                     if (Math.abs(var3) + Math.abs(var4) + Math.abs(var5) <= var2 && MovementHelper.a(var0.a(var1x.method_10263() + var3, var1x.method_10264() + var4, var1x.method_10260() + var5))) {
                        return true;
                     }
                  }
               }
            }

            return false;
         }
      }).filter((var1x) -> var1x.method_10264() >= (Integer)Baritone.a().minYLevelWhileMining.value + var0.a.method_8597().comp_651()).filter((var0x) -> var0x.method_10264() <= (Integer)Baritone.a().maxYLevelWhileMining.value).filter((var1x) -> !var4.contains(var1x));
      class_2338 var10001 = var0.a.getPlayerContext().player().method_24515();
      Objects.requireNonNull(var10001);
      List var6;
      return (var6 = (List)var10000.sorted(Comparator.comparingDouble(var10001::method_10262)).collect(Collectors.toList())).size() > var3 ? var6.subList(0, var3) : var6;
   }

   private static boolean a(CalculationContext var0, class_2338 var1) {
      class_2680 var2 = var0.a.a(var1);
      if (MovementHelper.a(var0, var1.method_10263(), var1.method_10264(), var1.method_10260(), var2, true) >= (double)1000000.0F) {
         return false;
      } else if (MovementHelper.a(var0.a, var1.method_10263(), var1.method_10264(), var1.method_10260(), var2)) {
         return false;
      } else {
         return var0.a.a(var1.method_10084()).method_26204() != class_2246.field_9987 || var0.a.a(var1.method_10074()).method_26204() != class_2246.field_9987;
      }
   }

   public final void mineByName(int var1, String... var2) {
      this.mine(var1, new BlockOptionalMetaLookup(var2));
   }

   public final void mine(int var1, BlockOptionalMetaLookup var2) {
      this.a = var2;
      if (this.a() == null) {
         this.a = null;
      }

      this.a = var1;
      this.a = new ArrayList();
      this.b = new ArrayList();
      this.a = null;
      this.a = null;
      this.a = new HashMap();
      if (var2 != null) {
         this.a(new ArrayList(), (CalculationContext)(new CalculationContext(super.a)));
      }

   }

   private BlockOptionalMetaLookup a() {
      if (this.a == null) {
         return null;
      } else if (!(Boolean)Baritone.a().allowBreak.value) {
         BlockOptionalMetaLookup var1;
         if ((var1 = new BlockOptionalMetaLookup((BlockOptionalMeta[])this.a.blocks().stream().filter((var0) -> ((List)Baritone.a().allowBreakAnyway.value).contains(var0.getBlock())).toArray((var0) -> new BlockOptionalMeta[var0]))).blocks().isEmpty()) {
            this.logDirect("Unable to mine when allowBreak is false and target block is not in allowBreakAnyway!");
            return null;
         } else {
            return var1;
         }
      } else {
         return this.a;
      }
   }

   static class GoalThreeBlocks extends GoalTwoBlocks {
      public GoalThreeBlocks(class_2338 var1) {
         super(var1);
      }

      public boolean isInGoal(int var1, int var2, int var3) {
         return var1 == super.x && (var2 == super.y || var2 == super.y - 1 || var2 == super.y - 2) && var3 == super.z;
      }

      public double heuristic(int var1, int var2, int var3) {
         var1 -= super.x;
         var2 -= super.y;
         var3 -= super.z;
         return GoalBlock.calculate((double)var1, var2 < -1 ? var2 + 2 : (var2 == -1 ? 0 : var2), (double)var3);
      }

      public boolean equals(Object var1) {
         return super.equals(var1);
      }

      public int hashCode() {
         return super.hashCode() * 393857768;
      }

      public String toString() {
         return String.format("GoalThreeBlocks{x=%s,y=%s,z=%s}", SettingsUtil.maybeCensor(super.x), SettingsUtil.maybeCensor(super.y), SettingsUtil.maybeCensor(super.z));
      }
   }
}
