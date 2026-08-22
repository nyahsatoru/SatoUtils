package baritone.process;

import baritone.Baritone;
import baritone.api.BaritoneAPI;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalBlock;
import baritone.api.pathing.goals.GoalComposite;
import baritone.api.pathing.goals.GoalGetToBlock;
import baritone.api.process.IFarmProcess;
import baritone.api.process.PathingCommand;
import baritone.api.process.PathingCommandType;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.RayTraceUtils;
import baritone.api.utils.Rotation;
import baritone.api.utils.RotationUtils;
import baritone.api.utils.input.Input;
import baritone.pathing.movement.MovementHelper;
import baritone.utils.BaritoneProcessHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.class_1297;
import net.minecraft.class_1542;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1937;
import net.minecraft.class_2189;
import net.minecraft.class_2211;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2256;
import net.minecraft.class_2266;
import net.minecraft.class_2282;
import net.minecraft.class_2302;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_239;
import net.minecraft.class_2421;
import net.minecraft.class_243;
import net.minecraft.class_2523;
import net.minecraft.class_2680;
import net.minecraft.class_3965;
import net.minecraft.class_2350.class_2353;

public final class FarmProcess extends BaritoneProcessHelper implements IFarmProcess {
   private boolean a;
   private List<class_2338> a;
   private int a;
   private int b;
   private class_2338 a;
   private static final List<class_1792> b;
   private static final List<class_1792> c;

   public FarmProcess(Baritone var1) {
      super(var1);
   }

   public final boolean isActive() {
      return this.a;
   }

   public final void farm(int var1, class_2338 var2) {
      if (var2 == null) {
         this.a = super.a.getPlayerContext().playerFeet();
      } else {
         this.a = var2;
      }

      this.b = var1;
      this.a = true;
      this.a = null;
   }

   private boolean a(class_1799 var1) {
      return b.contains(var1.method_7909());
   }

   private boolean b(class_1799 var1) {
      return !var1.method_7960() && var1.method_7909().equals(class_1802.field_8324);
   }

   private boolean c(class_1799 var1) {
      return !var1.method_7960() && var1.method_7909().equals(class_1802.field_8790);
   }

   private boolean d(class_1799 var1) {
      return !var1.method_7960() && var1.method_7909().equals(class_1802.field_8116);
   }

   public final PathingCommand onTick(boolean var1, boolean var2) {
      if ((Integer)Baritone.a().mineGoalUpdateInterval.value != 0 && this.a++ % (Integer)Baritone.a().mineGoalUpdateInterval.value == 0) {
         ArrayList var3 = new ArrayList();

         Harvest[] var4;
         for(Harvest var7 : var4 = FarmProcess.Harvest.values()) {
            var3.add(var7.a);
         }

         if ((Boolean)Baritone.a().replantCrops.value) {
            var3.add(class_2246.field_10362);
            var3.add(class_2246.field_10306);
            if ((Boolean)Baritone.a().replantNetherWart.value) {
               var3.add(class_2246.field_10114);
            }
         }

         Baritone.a().execute(() -> this.a = BaritoneAPI.getProvider().getWorldScanner().scanChunkRadius(super.a, (List)var3, (Integer)Baritone.a().farmMaxScanSize.value, 10, 10));
      }

      if (this.a == null) {
         return new PathingCommand((Goal)null, PathingCommandType.REQUEST_PAUSE);
      } else {
         ArrayList var18 = new ArrayList();
         ArrayList var19 = new ArrayList();
         ArrayList var20 = new ArrayList();
         ArrayList var21 = new ArrayList();
         ArrayList var22 = new ArrayList();

         for(class_2338 var9 : this.a) {
            if (this.b == 0 || !(var9.method_10262(this.a) > (double)(this.b * this.b))) {
               class_2680 var10 = super.a.world().method_8320(var9);
               boolean var11 = super.a.world().method_8320(var9.method_10084()).method_26204() instanceof class_2189;
               if (var10.method_26204() == class_2246.field_10362) {
                  if (var11) {
                     var19.add(var9);
                  }
               } else if (var10.method_26204() == class_2246.field_10114) {
                  if (var11) {
                     var21.add(var9);
                  }
               } else if (var10.method_26204() == class_2246.field_10306) {
                  for(class_2350 var43 : class_2353.field_11062) {
                     if (super.a.world().method_8320(var9.method_10093(var43)).method_26204() instanceof class_2189) {
                        var22.add(var9);
                        break;
                     }
                  }
               } else {
                  class_1937 var10000 = super.a.world();
                  class_2680 var13 = var10;
                  class_1937 var25 = var10000;
                  Harvest[] var14;
                  int var15 = (var14 = FarmProcess.Harvest.values()).length;
                  int var16 = 0;

                  while(true) {
                     if (var16 >= var15) {
                        var63 = false;
                        break;
                     }

                     Harvest var17;
                     if ((var17 = var14[var16]).a == var13.method_26204()) {
                        var63 = var17.a(var25, var9, var13);
                        break;
                     }

                     ++var16;
                  }

                  if (var63) {
                     var18.add(var9);
                  } else {
                     class_2256 var12;
                     if (var10.method_26204() instanceof class_2256 && (var12 = (class_2256)var10.method_26204()).method_9651(super.a.world(), var9, var10) && var12.method_9650(super.a.world(), super.a.world().field_9229, var9, var10)) {
                        var20.add(var9);
                     }
                  }
               }
            }
         }

         super.a.a.clearAllKeys();
         BetterBlockPos var23 = super.a.playerFeet();
         double var24 = super.a.playerController().getBlockReachDistance();

         for(class_2338 var38 : var18) {
            Optional var44;
            if (!(var23.method_10262(var38) > var24 * var24) && (var44 = RotationUtils.reachable(super.a, var38)).isPresent() && var2) {
               super.a.a.updateTarget((Rotation)var44.get(), true);
               MovementHelper.a(super.a, super.a.world().method_8320(var38));
               if (super.a.isLookingAt(var38)) {
                  super.a.a.setInputForceState(Input.CLICK_LEFT, true);
               }

               return new PathingCommand((Goal)null, PathingCommandType.REQUEST_PAUSE);
            }
         }

         ArrayList var27;
         (var27 = new ArrayList(var19)).addAll(var21);

         for(class_2338 var45 : var27) {
            if (!(var23.method_10262(var45) > var24 * var24)) {
               boolean var28 = var21.contains(var45);
               Optional var54;
               class_239 var58;
               if ((var54 = RotationUtils.reachableOffset(super.a, var45, new class_243((double)var45.method_10263() + (double)0.5F, (double)(var45.method_10264() + 1), (double)var45.method_10260() + (double)0.5F), var24, false)).isPresent() && var2 && super.a.a.a(true, var28 ? this::c : this::a) && (var58 = RayTraceUtils.rayTraceTowards(super.a.player(), (Rotation)var54.get(), var24)) instanceof class_3965 && ((class_3965)var58).method_17780() == class_2350.field_11036) {
                  super.a.a.updateTarget((Rotation)var54.get(), true);
                  if (super.a.isLookingAt(var45)) {
                     super.a.a.setInputForceState(Input.CLICK_RIGHT, true);
                  }

                  return new PathingCommand((Goal)null, PathingCommandType.REQUEST_PAUSE);
               }
            }
         }

         for(class_2338 var46 : var22) {
            if (!(var23.method_10262(var46) > var24 * var24)) {
               for(class_2350 var55 : class_2353.field_11062) {
                  if (super.a.world().method_8320(var46.method_10093(var55)).method_26204() instanceof class_2189) {
                     class_243 var59 = class_243.method_24953(var46).method_1019(class_243.method_24954(var55.method_62675()).method_1021((double)0.5F));
                     Optional var60;
                     class_239 var62;
                     if ((var60 = RotationUtils.reachableOffset(super.a, var46, var59, var24, false)).isPresent() && var2 && super.a.a.a(true, this::d) && (var62 = RayTraceUtils.rayTraceTowards(super.a.player(), (Rotation)var60.get(), var24)) instanceof class_3965 && ((class_3965)var62).method_17780() == var55) {
                        super.a.a.updateTarget((Rotation)var60.get(), true);
                        if (super.a.isLookingAt(var46)) {
                           super.a.a.setInputForceState(Input.CLICK_RIGHT, true);
                        }

                        return new PathingCommand((Goal)null, PathingCommandType.REQUEST_PAUSE);
                     }
                  }
               }
            }
         }

         for(class_2338 var47 : var20) {
            Optional var30;
            if (!(var23.method_10262(var47) > var24 * var24) && (var30 = RotationUtils.reachable(super.a, var47)).isPresent() && var2 && super.a.a.a(true, this::b)) {
               super.a.a.updateTarget((Rotation)var30.get(), true);
               if (super.a.isLookingAt(var47)) {
                  super.a.a.setInputForceState(Input.CLICK_RIGHT, true);
               }

               return new PathingCommand((Goal)null, PathingCommandType.REQUEST_PAUSE);
            }
         }

         if (var1) {
            this.logDirect("Farm failed");
            if ((Boolean)Baritone.a().notificationOnFarmFail.value) {
               this.logNotification("Farm failed", true);
            }

            this.onLostControl();
            return new PathingCommand((Goal)null, PathingCommandType.REQUEST_PAUSE);
         } else {
            ArrayList var42 = new ArrayList();

            for(class_2338 var31 : var18) {
               var42.add(new BuilderProcess.GoalBreak(var31));
            }

            if (super.a.a.a(false, this::a)) {
               for(class_2338 var32 : var19) {
                  var42.add(new GoalBlock(var32.method_10084()));
               }
            }

            if (super.a.a.a(false, this::c)) {
               for(class_2338 var33 : var21) {
                  var42.add(new GoalBlock(var33.method_10084()));
               }
            }

            if (super.a.a.a(false, this::d)) {
               for(class_2338 var34 : var22) {
                  for(class_2350 var61 : class_2353.field_11062) {
                     if (super.a.world().method_8320(var34.method_10093(var61)).method_26204() instanceof class_2189) {
                        var42.add(new GoalGetToBlock(var34.method_10093(var61)));
                     }
                  }
               }
            }

            if (super.a.a.a(false, this::b)) {
               for(class_2338 var35 : var20) {
                  var42.add(new GoalBlock(var35));
               }
            }

            Iterator var53 = super.a.entities().iterator();

            while(var53.hasNext()) {
               class_1297 var36;
               if ((var36 = (class_1297)var53.next()) instanceof class_1542 && var36.method_24828()) {
                  class_1542 var57 = (class_1542)var36;
                  if (c.contains(var57.method_6983().method_7909())) {
                     var42.add(new GoalBlock(new BetterBlockPos(var36.method_73189().field_1352, var36.method_73189().field_1351 + 0.1, var36.method_73189().field_1350)));
                  }
               }
            }

            if (var42.isEmpty()) {
               this.logDirect("Farm failed");
               if ((Boolean)Baritone.a().notificationOnFarmFail.value) {
                  this.logNotification("Farm failed", true);
               }

               this.onLostControl();
               return new PathingCommand((Goal)null, PathingCommandType.REQUEST_PAUSE);
            } else {
               return new PathingCommand(new GoalComposite((Goal[])var42.toArray(new Goal[0])), PathingCommandType.SET_GOAL_AND_PATH);
            }
         }
      }
   }

   public final void onLostControl() {
      this.a = false;
   }

   public final String displayName0() {
      return "Farming";
   }

   static {
      b = Arrays.asList(class_1802.field_8309, class_1802.field_46250, class_1802.field_8317, class_1802.field_46249, class_1802.field_8567, class_1802.field_8179);
      c = Arrays.asList(class_1802.field_8309, class_1802.field_8186, class_1802.field_46250, class_1802.field_8497, class_2246.field_46283.method_8389(), class_1802.field_8317, class_1802.field_8861, class_1802.field_46249, class_2246.field_46282.method_8389(), class_1802.field_8567, class_1802.field_8179, class_1802.field_8790, class_1802.field_8116, class_2246.field_10424.method_8389(), class_2246.field_10211.method_8389(), class_2246.field_10029.method_8389());
   }

   static enum Harvest permits null, null, null {
      a((class_2302)class_2246.field_10293),
      b((class_2302)class_2246.field_10609),
      c((class_2302)class_2246.field_10247),
      d((class_2302)class_2246.field_10341),
      e(class_2246.field_46282, (var0) -> true),
      f(class_2246.field_46283, (var0) -> true),
      g(class_2246.field_9974, (var0) -> (Integer)var0.method_11654(class_2421.field_11306) >= 3),
      h(class_2246.field_10302, (var0) -> (Integer)var0.method_11654(class_2282.field_10779) >= 2),
      a {
         public final boolean a(class_1937 var1, class_2338 var2, class_2680 var3) {
            return (Boolean)Baritone.a().replantCrops.value ? var1.method_8320(var2.method_10074()).method_26204() instanceof class_2523 : true;
         }
      },
      a {
         public final boolean a(class_1937 var1, class_2338 var2, class_2680 var3) {
            return (Boolean)Baritone.a().replantCrops.value ? var1.method_8320(var2.method_10074()).method_26204() instanceof class_2211 : true;
         }
      },
      a {
         public final boolean a(class_1937 var1, class_2338 var2, class_2680 var3) {
            return (Boolean)Baritone.a().replantCrops.value ? var1.method_8320(var2.method_10074()).method_26204() instanceof class_2266 : true;
         }
      };

      public final class_2248 a;
      private Predicate<class_2680> a;

      private Harvest(class_2302 var3) {
         Objects.requireNonNull(var3);
         this(var3, var3::method_9825);
      }

      Harvest(class_2248 var3, Predicate<class_2680> var4) {
         this.a = var3;
         this.a = var4;
      }

      public boolean a(class_1937 var1, class_2338 var2, class_2680 var3) {
         return this.a.test(var3);
      }
   }
}
