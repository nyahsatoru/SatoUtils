package baritone.pathing.movement.movements;

import baritone.Baritone;
import baritone.api.IBaritone;
import baritone.api.pathing.movement.MovementStatus;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.Rotation;
import baritone.api.utils.RotationUtils;
import baritone.api.utils.VecUtils;
import baritone.api.utils.input.Input;
import baritone.pathing.movement.CalculationContext;
import baritone.pathing.movement.Movement;
import baritone.pathing.movement.MovementHelper;
import baritone.pathing.movement.MovementState;
import baritone.utils.BlockStateInterface;
import com.google.common.collect.ImmutableSet;
import java.util.Optional;
import java.util.Set;
import net.minecraft.class_2189;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2323;
import net.minecraft.class_2338;
import net.minecraft.class_2349;
import net.minecraft.class_2350;
import net.minecraft.class_2399;
import net.minecraft.class_243;
import net.minecraft.class_2482;
import net.minecraft.class_2577;
import net.minecraft.class_2680;
import net.minecraft.class_2771;

public class MovementTraverse extends Movement {
   private boolean a = true;

   public MovementTraverse(IBaritone var1, BetterBlockPos var2, BetterBlockPos var3) {
      super(var1, var2, var3, new BetterBlockPos[]{var3.above(), var3}, var3.below());
   }

   public void reset() {
      super.reset();
      this.a = true;
   }

   public final double a(CalculationContext var1) {
      return a(var1, super.a.x, super.a.y, super.a.z, super.b.x, super.b.z);
   }

   public final Set<BetterBlockPos> a() {
      return ImmutableSet.of(super.a, super.b);
   }

   public static double a(CalculationContext var0, int var1, int var2, int var3, int var4, int var5) {
      class_2680 var6 = var0.a(var4, var2 + 1, var5);
      class_2680 var7 = var0.a(var4, var2, var5);
      class_2680 var8 = var0.a(var4, var2 - 1, var5);
      class_2680 var9;
      class_2248 var10 = (var9 = var0.a(var1, var2 - 1, var3)).method_26204();
      boolean var11;
      boolean var12;
      if (!(var12 = (var11 = MovementHelper.c(var0, var1, var2 - 1, var3, var9)) && !var0.j && MovementHelper.b(var0, var8)) && !MovementHelper.b(var0, var4, var2 - 1, var5, var8)) {
         if (var10 != class_2246.field_9983 && var10 != class_2246.field_10597) {
            if (!MovementHelper.a(var4, var5, var8, var0.a)) {
               return (double)1000000.0F;
            } else {
               boolean var27 = MovementHelper.d(var6) || MovementHelper.d(var7);
               if (MovementHelper.d(var8) && var27) {
                  return (double)1000000.0F;
               } else {
                  double var14;
                  if ((var14 = var0.a(var4, var2 - 1, var5, var8)) >= (double)1000000.0F) {
                     return (double)1000000.0F;
                  } else {
                     double var28;
                     if ((var28 = MovementHelper.a(var0, var4, var2, var5, var7, false)) >= (double)1000000.0F) {
                        return (double)1000000.0F;
                     } else {
                        double var29 = MovementHelper.a(var0, var4, var2 + 1, var5, var6, true);
                        double var20 = var27 ? var0.b : 4.63284688441047;

                        for(int var22 = 0; var22 < 5; ++var22) {
                           int var24 = var4 + a[var22].method_10148();
                           int var25 = var2 - 1 + a[var22].method_10164();
                           var12 = var5 + a[var22].method_10165();
                           if ((var24 != var1 || var12 != var3) && MovementHelper.c(var0.a, var24, var25, var12)) {
                              return var20 + var14 + var28 + var29;
                           }
                        }

                        if (var10 != class_2246.field_10114 && (!(var10 instanceof class_2482) || var9.method_11654(class_2482.field_11501) == class_2771.field_12682)) {
                           if (!var11) {
                              return (double)1000000.0F;
                           } else {
                              class_2248 var23;
                              if (((var23 = var0.a(var1, var2, var3)) == class_2246.field_10588 || var23 instanceof class_2577) && !var9.method_26227().method_15769()) {
                                 return (double)1000000.0F;
                              } else {
                                 return var20 * 3.3207692307692307 + var14 + var28 + var29;
                              }
                           }
                        } else {
                           return (double)1000000.0F;
                        }
                     }
                  }
               }
            }
         } else {
            return (double)1000000.0F;
         }
      } else {
         double var13 = 4.63284688441047;
         boolean var15 = false;
         if (!MovementHelper.d(var6) && !MovementHelper.d(var7)) {
            if (var8.method_26204() == class_2246.field_10114) {
               var13 = 6.949270326615705;
            } else if (!var12 && var8.method_26204() == class_2246.field_10382) {
               var13 = 4.63284688441047 + var0.f;
            }

            if (var10 == class_2246.field_10114) {
               var13 += 2.316423442205235;
            }
         } else {
            var13 = var0.b;
            var15 = true;
         }

         double var16;
         if ((var16 = MovementHelper.a(var0, var4, var2, var5, var7, false)) >= (double)1000000.0F) {
            return (double)1000000.0F;
         } else {
            double var18 = MovementHelper.a(var0, var4, var2 + 1, var5, var6, true);
            if (var16 == (double)0.0F && var18 == (double)0.0F) {
               if (!var15 && var0.d) {
                  var13 *= 0.7692444761225944;
               }

               return var13;
            } else {
               if (var10 == class_2246.field_9983 || var10 == class_2246.field_10597) {
                  var16 *= (double)5.0F;
                  var18 *= (double)5.0F;
               }

               return var13 + var16 + var18;
            }
         }
      }
   }

   public final MovementState a(MovementState var1) {
      super.a(var1);
      class_2680 var2 = BlockStateInterface.a(super.a, (class_2338)super.a[0]);
      class_2680 var3 = BlockStateInterface.a(super.a, (class_2338)super.a[1]);
      if (var1.a != MovementStatus.RUNNING) {
         if (!(Boolean)Baritone.a().walkWhileBreaking.value) {
            return var1;
         } else if (var1.a != MovementStatus.PREPPING) {
            return var1;
         } else if (MovementHelper.b(var2)) {
            return var1;
         } else if (MovementHelper.b(var3)) {
            return var1;
         } else if (Math.max(Math.abs(super.a.player().method_73189().field_1352 - ((double)super.b.method_10263() + (double)0.5F)), Math.abs(super.a.player().method_73189().field_1350 - ((double)super.b.method_10260() + (double)0.5F))) < 0.83) {
            return var1;
         } else if (!Optional.ofNullable(var1.a.a).isPresent()) {
            return var1;
         } else {
            float var29 = RotationUtils.calcRotationFromVec3d(super.a.playerHead(), VecUtils.calculateBlockCenter(super.a.world(), super.b), super.a.playerRotations()).getYaw();
            float var35 = ((Rotation)Optional.ofNullable(var1.a.a).get()).getPitch();
            if (MovementHelper.h(var2) || var2.method_26204() instanceof class_2189 && (MovementHelper.h(var3) || var3.method_26204() instanceof class_2189)) {
               var35 = 26.0F;
            }

            return var1.a(new MovementState.MovementTarget(new Rotation(var29, var35), true)).a(Input.MOVE_FORWARD, true).a(Input.SPRINT, true);
         }
      } else {
         var1.a(Input.SNEAK, false);
         class_2248 var4;
         boolean var5 = (var4 = BlockStateInterface.a(super.a, (class_2338)super.a.below()).method_26204()) == class_2246.field_9983 || var4 == class_2246.field_10597;
         if (var2.method_26204() instanceof class_2323 || var3.method_26204() instanceof class_2323) {
            boolean var25 = var2.method_26204() instanceof class_2323 && !MovementHelper.a(super.a, super.a, super.b) || var3.method_26204() instanceof class_2323 && !MovementHelper.a(super.a, super.b, super.a);
            boolean var6 = !class_2246.field_9973.equals(var2.method_26204()) && !class_2246.field_9973.equals(var3.method_26204());
            if (var25 && var6) {
               return var1.a(new MovementState.MovementTarget(RotationUtils.calcRotationFromVec3d(super.a.playerHead(), VecUtils.calculateBlockCenter(super.a.world(), super.a[0]), super.a.playerRotations()), true)).a(Input.CLICK_RIGHT, true);
            }
         }

         BetterBlockPos var26;
         Optional var33;
         if ((var2.method_26204() instanceof class_2349 || var3.method_26204() instanceof class_2349) && (var26 = !MovementHelper.b(super.a, super.a[0], super.a.above()) ? super.a[0] : (!MovementHelper.b(super.a, super.a[1], super.a) ? super.a[1] : null)) != null && (var33 = RotationUtils.reachable(super.a, var26)).isPresent()) {
            return var1.a(new MovementState.MovementTarget((Rotation)var33.get(), true)).a(Input.CLICK_RIGHT, true);
         } else {
            boolean var27 = MovementHelper.b(super.a, super.c) || var5 || MovementHelper.c(super.a, super.c);
            BetterBlockPos var34;
            if ((var34 = super.a.playerFeet()).method_10264() != super.b.method_10264() && !var5) {
               this.logDebug("Wrong Y coordinate");
               if (((class_2338)var34).method_10264() < super.b.method_10264()) {
                  System.out.println("In movement traverse");
                  return var1.a(Input.JUMP, true);
               } else {
                  return var1;
               }
            } else if (var27) {
               if (((class_2338)var34).equals(super.b)) {
                  MovementState var41 = var1;
                  MovementStatus var17 = MovementStatus.SUCCESS;
                  MovementState var32 = var41;
                  var41.a = var17;
                  return var32;
               } else if (!(Boolean)Baritone.a().overshootTraverse.value || !((class_2338)var34).equals(super.b.method_10081(((Movement)this).getDirection())) && !((class_2338)var34).equals(super.b.method_10081(((Movement)this).getDirection()).method_10081(((Movement)this).getDirection()))) {
                  class_2248 var21 = BlockStateInterface.a(super.a, (class_2338)super.a).method_26204();
                  class_2248 var36 = BlockStateInterface.a(super.a, (class_2338)super.a.above()).method_26204();
                  double var10001 = (double)super.a.y;
                  if (!(super.a.player().method_73189().field_1351 > var10001 + 0.1) || super.a.player().method_24828() || var21 != class_2246.field_10597 && var21 != class_2246.field_9983 && var36 != class_2246.field_10597 && var36 != class_2246.field_9983) {
                     class_2338 var8 = super.b.method_10059(super.a).method_10081(super.b);
                     class_2680 var22 = BlockStateInterface.a(super.a, var8);
                     class_2680 var38 = BlockStateInterface.a(super.a, var8.method_10084());
                     if (this.a && (!MovementHelper.e(super.a, var34) || (Boolean)Baritone.a().sprintInWater.value) && (!MovementHelper.b(var22) || MovementHelper.d(var22)) && !MovementHelper.b(var38)) {
                        var1.a(Input.SPRINT, true);
                     }

                     class_2680 var10 = BlockStateInterface.a(super.a, (class_2338)super.b.below());
                     BetterBlockPos var39 = super.a[0];
                     if (((class_2338)var34).method_10264() != super.b.method_10264() && var5 && (var10.method_26204() == class_2246.field_10597 || var10.method_26204() == class_2246.field_9983) && (var39 = var10.method_26204() == class_2246.field_10597 ? MovementPillar.a(new CalculationContext(super.a), super.b.below()) : super.b.relative(((class_2350)var10.method_11654(class_2399.field_11253)).method_10153())) == null) {
                        this.logDirect("Unable to climb vines. Consider disabling allowVines.");
                        MovementState var40 = var1;
                        MovementStatus var16 = MovementStatus.UNREACHABLE;
                        MovementState var31 = var40;
                        var40.a = var16;
                        return var31;
                     } else {
                        MovementHelper.a(super.a, (MovementState)var1, (class_2338)var39);
                        return var1;
                     }
                  } else {
                     return var1;
                  }
               } else {
                  MovementState var10000 = var1;
                  MovementStatus var15 = MovementStatus.SUCCESS;
                  MovementState var30 = var10000;
                  var10000.a = var15;
                  return var30;
               }
            } else {
               this.a = false;
               class_2248 var18;
               if (((var18 = BlockStateInterface.a(super.a, ((class_2338)var34).method_10074()).method_26204()).equals(class_2246.field_10114) || var18 instanceof class_2482) && Math.max(Math.abs((double)super.b.method_10263() + (double)0.5F - super.a.player().method_73189().field_1352), Math.abs((double)super.b.method_10260() + (double)0.5F - super.a.player().method_73189().field_1350)) < 0.85) {
                  MovementHelper.a(super.a, (MovementState)var1, (class_2338)super.b);
                  return var1.a(Input.MOVE_FORWARD, false).a(Input.MOVE_BACK, true);
               } else {
                  double var7 = Math.max(Math.abs(super.a.player().method_73189().field_1352 - ((double)super.b.method_10263() + (double)0.5F)), Math.abs(super.a.player().method_73189().field_1350 - ((double)super.b.method_10260() + (double)0.5F)));
                  MovementHelper.PlaceResult var19;
                  if (((var19 = MovementHelper.a(var1, super.a, super.b.below(), false, true)) == MovementHelper.PlaceResult.a || var7 < 0.6) && !(Boolean)Baritone.a().assumeSafeWalk.value) {
                     var1.a(Input.SNEAK, true);
                  }

                  switch (var19) {
                     case a:
                        if (super.a.player().method_18276() || (Boolean)Baritone.a().assumeSafeWalk.value) {
                           var1.a(Input.CLICK_RIGHT, true);
                        }

                        return var1;
                     case b:
                        if (var7 > 0.83) {
                           float var9 = RotationUtils.calcRotationFromVec3d(super.a.playerHead(), VecUtils.getBlockPosCenter(super.b), super.a.playerRotations()).getYaw();
                           if ((double)Math.abs(var1.a.a.getYaw() - var9) < 0.1) {
                              return var1.a(Input.MOVE_FORWARD, true);
                           }
                        } else if (super.a.playerRotations().isReallyCloseTo(var1.a.a)) {
                           return var1.a(Input.CLICK_LEFT, true);
                        }

                        return var1;
                     default:
                        if (((class_2338)var34).equals(super.b)) {
                           double var37 = ((double)(super.b.method_10263() + super.a.method_10263()) + (double)1.0F) * (double)0.5F;
                           double var11 = ((double)(super.b.method_10264() + super.a.method_10264()) - (double)1.0F) * (double)0.5F;
                           double var13 = ((double)(super.b.method_10260() + super.a.method_10260()) + (double)1.0F) * (double)0.5F;
                           BetterBlockPos var20 = super.a.below();
                           Rotation var23;
                           float var28 = (var23 = RotationUtils.calcRotationFromVec3d(super.a.playerHead(), new class_243(var37, var11, var13), super.a.playerRotations())).getPitch();
                           if (Math.max(Math.abs(super.a.player().method_73189().field_1352 - var37), Math.abs(super.a.player().method_73189().field_1350 - var13)) < 0.29) {
                              float var24 = RotationUtils.calcRotationFromVec3d(VecUtils.getBlockPosCenter(super.b), super.a.playerHead(), super.a.playerRotations()).getYaw();
                              var1.a(new MovementState.MovementTarget(new Rotation(var24, var28), true));
                              var1.a(Input.MOVE_BACK, true);
                           } else {
                              var1.a(new MovementState.MovementTarget(var23, true));
                           }

                           if (super.a.isLookingAt(var20)) {
                              return var1.a(Input.CLICK_RIGHT, true);
                           } else {
                              if (super.a.playerRotations().isReallyCloseTo(var1.a.a)) {
                                 var1.a(Input.CLICK_LEFT, true);
                              }

                              return var1;
                           }
                        } else {
                           MovementHelper.a(super.a, (MovementState)var1, (class_2338)super.a[0]);
                           return var1;
                        }
                  }
               }
            }
         }
      }
   }

   public final boolean b(MovementState var1) {
      return var1.a != MovementStatus.RUNNING || MovementHelper.b(super.a, super.b.below());
   }

   public final boolean a(MovementState var1) {
      class_2248 var2;
      if ((super.a.playerFeet().equals(super.a) || super.a.playerFeet().equals(super.a.below())) && ((var2 = BlockStateInterface.a(super.a, super.a.below())) == class_2246.field_9983 || var2 == class_2246.field_10597)) {
         var1.a(Input.SNEAK, true);
      }

      return super.a(var1);
   }
}
