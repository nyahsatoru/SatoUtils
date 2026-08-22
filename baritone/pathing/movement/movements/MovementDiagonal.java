package baritone.pathing.movement.movements;

import baritone.Baritone;
import baritone.api.IBaritone;
import baritone.api.pathing.movement.MovementStatus;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.input.Input;
import baritone.pathing.movement.CalculationContext;
import baritone.pathing.movement.Movement;
import baritone.pathing.movement.MovementHelper;
import baritone.pathing.movement.MovementState;
import baritone.utils.BlockStateInterface;
import baritone.utils.pathing.MutableMoveResult;
import com.google.common.collect.ImmutableSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2680;
import net.minecraft.class_746;

public class MovementDiagonal extends Movement {
   private static final double a = Math.sqrt((double)2.0F);

   public MovementDiagonal(IBaritone var1, BetterBlockPos var2, class_2350 var3, class_2350 var4, int var5) {
      this(var1, var2, var2.relative(var3), var2.relative(var4), var4, var5);
   }

   private MovementDiagonal(IBaritone var1, BetterBlockPos var2, BetterBlockPos var3, BetterBlockPos var4, class_2350 var5, int var6) {
      this(var1, var2, var3.relative(var5).above(var6), var3, var4);
   }

   private MovementDiagonal(IBaritone var1, BetterBlockPos var2, BetterBlockPos var3, BetterBlockPos var4, BetterBlockPos var5) {
      super(var1, var2, var3, new BetterBlockPos[]{var4, var4.above(), var5, var5.above(), var3, var3.above()});
   }

   public final boolean b(MovementState var1) {
      class_746 var8;
      double var2 = (var8 = super.a.player()).method_73189().field_1352;
      double var4 = var8.method_73189().field_1351 - (double)1.0F;
      double var6 = var8.method_73189().field_1350;
      if (super.a.playerFeet().equals(super.a)) {
         return true;
      } else if (MovementHelper.b(super.a, new class_2338(super.a.x, super.a.y - 1, super.b.z)) && MovementHelper.b(super.a, new class_2338(super.b.x, super.a.y - 1, super.a.z))) {
         return true;
      } else if (!super.a.playerFeet().equals(new BetterBlockPos(super.a.x, super.a.y, super.b.z)) && !super.a.playerFeet().equals(new BetterBlockPos(super.b.x, super.a.y, super.a.z))) {
         return true;
      } else {
         return MovementHelper.b(super.a, new BetterBlockPos(var2 + (double)0.25F, var4, var6 + (double)0.25F)) || MovementHelper.b(super.a, new BetterBlockPos(var2 + (double)0.25F, var4, var6 - (double)0.25F)) || MovementHelper.b(super.a, new BetterBlockPos(var2 - (double)0.25F, var4, var6 + (double)0.25F)) || MovementHelper.b(super.a, new BetterBlockPos(var2 - (double)0.25F, var4, var6 - (double)0.25F));
      }
   }

   public final double a(CalculationContext var1) {
      MutableMoveResult var2 = new MutableMoveResult();
      a(var1, super.a.x, super.a.y, super.a.z, super.b.x, super.b.z, var2);
      return var2.b != super.b.y ? (double)1000000.0F : var2.a;
   }

   public final Set<BetterBlockPos> a() {
      BetterBlockPos var1 = new BetterBlockPos(super.a.x, super.a.y, super.b.z);
      BetterBlockPos var2 = new BetterBlockPos(super.b.x, super.a.y, super.a.z);
      if (super.b.y < super.a.y) {
         return ImmutableSet.of(super.a, super.b.above(), var1, var2, super.b, var1.below(), new BetterBlockPos[]{var2.below()});
      } else {
         return super.b.y > super.a.y ? ImmutableSet.of(super.a, super.a.above(), var1, var2, super.b, var1.above(), new BetterBlockPos[]{var2.above()}) : ImmutableSet.of(super.a, super.b, var1, var2);
      }
   }

   public static void a(CalculationContext var0, int var1, int var2, int var3, int var4, int var5, MutableMoveResult var6) {
      if (MovementHelper.a(var0, var4, var2 + 1, var5)) {
         class_2680 var7 = var0.a(var4, var2, var5);
         boolean var9 = false;
         boolean var11 = false;
         boolean var12 = false;
         class_2680 var8;
         class_2680 var10;
         if (!MovementHelper.a(var0, var4, var2, var5, var7)) {
            var9 = true;
            if (!var0.m || !MovementHelper.a(var0, var1, var2 + 2, var3) || !MovementHelper.b(var0, var4, var2, var5, var7) || !MovementHelper.a(var0, var4, var2 + 2, var5)) {
               return;
            }

            var10 = var7;
            var8 = var0.a(var1, var2 - 1, var3);
         } else {
            var10 = var0.a(var4, var2 - 1, var5);
            var8 = var0.a(var1, var2 - 1, var3);
            if (!(var12 = MovementHelper.c(var0, var1, var2 - 1, var3, var8) && MovementHelper.b(var0, var10)) && !MovementHelper.b(var0, var4, var2 - 1, var5, var10)) {
               var11 = true;
               if (!var0.l || !MovementHelper.c(var0, var4, var2 - 2, var5) || !MovementHelper.a(var0, var4, var2 - 1, var5, var10)) {
                  return;
               }
            }

            var12 &= !var0.j;
         }

         double var13 = 4.63284688441047;
         if (var10.method_26204() == class_2246.field_10114) {
            var13 = 6.949270326615705;
         } else if (!var12 && var10.method_26204() == class_2246.field_10382) {
            var13 = 4.63284688441047 + var0.f * a;
         }

         class_2248 var21;
         if ((var21 = var8.method_26204()) != class_2246.field_9983 && var21 != class_2246.field_10597) {
            if (var21 == class_2246.field_10114) {
               var13 += 2.316423442205235;
            }

            class_2680 var22;
            if ((var22 = var0.a(var1, var2 - 1, var5)).method_26204() != class_2246.field_10092 && !MovementHelper.e(var22)) {
               if ((var22 = var0.a(var4, var2 - 1, var3)).method_26204() != class_2246.field_10092 && !MovementHelper.e(var22)) {
                  boolean var24 = false;
                  class_2248 var30 = (var10 = var0.a(var1, var2, var3)).method_26204();
                  if (MovementHelper.d(var10) || MovementHelper.d(var7)) {
                     if (var9) {
                        return;
                     }

                     var13 = var0.b;
                     var24 = true;
                  }

                  var7 = var0.a(var1, var2, var5);
                  var10 = var0.a(var4, var2, var3);
                  if (var9) {
                     boolean var33 = MovementHelper.a(var0, var1, var2 + 2, var5);
                     boolean var17 = MovementHelper.a(var0, var1, var2 + 1, var5);
                     boolean var35 = MovementHelper.a(var0, var1, var2, var5, var7);
                     boolean var19 = MovementHelper.a(var0, var4, var2 + 2, var3);
                     var9 = MovementHelper.a(var0, var4, var2 + 1, var3);
                     boolean var31 = MovementHelper.a(var0, var4, var2, var3, var10);
                     if ((var33 && var17 && var35 || var19 && var9 && var31) && !MovementHelper.b(var7) && !MovementHelper.b(var10) && (!var33 || !var17 || !MovementHelper.b(var0, var1, var2, var5, var7)) && (!var19 || !var9 || !MovementHelper.b(var0, var4, var2, var3, var10)) && (var33 || !var17 || !var35) && (var19 || !var9 || !var31)) {
                        var6.a = var13 * a + JUMP_ONE_BLOCK_COST;
                        var6.a = var4;
                        var6.c = var5;
                        var6.b = var2 + 1;
                     }
                  } else {
                     double var16 = MovementHelper.a(var0, var1, var2, var5, var7, false);
                     double var18 = MovementHelper.a(var0, var4, var2, var3, var10, false);
                     if (var16 == (double)0.0F || var18 == (double)0.0F) {
                        class_2680 var25 = var0.a(var1, var2 + 1, var5);
                        if ((var16 = var16 + MovementHelper.a(var0, var1, var2 + 1, var5, var25, true)) == (double)0.0F || var18 == (double)0.0F) {
                           class_2680 var15 = var0.a(var4, var2 + 1, var3);
                           if (var16 != (double)0.0F || (!MovementHelper.b(var10) || var10.method_26204() == class_2246.field_10382) && !MovementHelper.b(var15)) {
                              var18 += MovementHelper.a(var0, var4, var2 + 1, var3, var15, true);
                              if (var16 == (double)0.0F || var18 == (double)0.0F) {
                                 if (var18 != (double)0.0F || (!MovementHelper.b(var7) || var7.method_26204() == class_2246.field_10382) && !MovementHelper.b(var25)) {
                                    if (var16 == (double)0.0F && var18 == (double)0.0F) {
                                       if (var0.d && !var24) {
                                          var13 *= 0.7692444761225944;
                                       }
                                    } else {
                                       var13 *= a - 0.001;
                                       if (var30 == class_2246.field_9983 || var30 == class_2246.field_10597) {
                                          return;
                                       }
                                    }

                                    var6.a = var13 * a;
                                    if (var11) {
                                       var6.a += Math.max(FALL_N_BLOCKS_COST[1], 0.9265693768820937);
                                       var6.b = var2 - 1;
                                    } else {
                                       var6.b = var2;
                                    }

                                    var6.a = var4;
                                    var6.c = var5;
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public final MovementState a(MovementState var1) {
      super.a(var1);
      if (var1.a != MovementStatus.RUNNING) {
         return var1;
      } else if (super.a.playerFeet().equals(super.b)) {
         MovementStatus var5 = MovementStatus.SUCCESS;
         var1.a = var5;
         return var1;
      } else if (this.a() || MovementHelper.e(super.a, super.a) && ((Movement)this).b().contains(super.a.playerFeet().above())) {
         if (super.b.y > super.a.y) {
            double var10001 = (double)super.a.y;
            if (super.a.player().method_73189().field_1351 < var10001 + 0.1 && super.a.player().field_5976) {
               var1.a(Input.JUMP, true);
            }
         }

         MovementDiagonal var6 = this;
         boolean var8;
         if (MovementHelper.e(super.a, super.a.playerFeet()) && !(Boolean)Baritone.a().sprintInWater.value) {
            var8 = false;
         } else {
            int var3 = 0;

            while(true) {
               if (var3 >= 4) {
                  var8 = true;
                  break;
               }

               if (!MovementHelper.a(var6.a, var6.a[var3])) {
                  var8 = false;
                  break;
               }

               ++var3;
            }
         }

         if (var8) {
            var1.a(Input.SPRINT, true);
         }

         MovementHelper.a(super.a, (MovementState)var1, (class_2338)super.b);
         return var1;
      } else {
         MovementStatus var4 = MovementStatus.UNREACHABLE;
         var1.a = var4;
         return var1;
      }
   }

   public final boolean a(MovementState var1) {
      return true;
   }

   public final List<class_2338> a(BlockStateInterface var1) {
      if (super.a != null) {
         return super.a;
      } else {
         ArrayList var2 = new ArrayList();

         for(int var3 = 4; var3 < 6; ++var3) {
            if (!MovementHelper.a(var1, super.a[var3].x, super.a[var3].y, super.a[var3].z)) {
               var2.add(super.a[var3]);
            }
         }

         super.a = var2;
         return var2;
      }
   }

   public final List<class_2338> c(BlockStateInterface var1) {
      if (super.b == null) {
         super.b = new ArrayList();
      }

      ArrayList var2 = new ArrayList();

      for(int var3 = 0; var3 < 4; ++var3) {
         if (!MovementHelper.a(var1, super.a[var3].x, super.a[var3].y, super.a[var3].z)) {
            var2.add(super.a[var3]);
         }
      }

      super.b = var2;
      return super.b;
   }
}
