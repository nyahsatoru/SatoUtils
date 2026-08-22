package baritone.pathing.movement.movements;

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
import baritone.utils.pathing.MutableMoveResult;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import net.minecraft.class_1661;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1937;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2382;
import net.minecraft.class_2399;
import net.minecraft.class_243;
import net.minecraft.class_2680;
import net.minecraft.class_3621;

public class MovementFall extends Movement {
   private static final class_1799 a;
   private static final class_1799 b;

   public MovementFall(IBaritone var1, BetterBlockPos var2, BetterBlockPos var3) {
      super(var1, var2, var3, a(var2, var3));
   }

   public final double a(CalculationContext var1) {
      MutableMoveResult var2 = new MutableMoveResult();
      MovementDescend.a(var1, super.a.x, super.a.y, super.a.z, super.b.x, super.b.z, var2);
      return var2.b != super.b.y ? (double)1000000.0F : var2.a;
   }

   public final Set<BetterBlockPos> a() {
      HashSet var1;
      (var1 = new HashSet()).add(super.a);

      for(int var2 = super.a.y - super.b.y; var2 >= 0; --var2) {
         var1.add(super.b.above(var2));
      }

      return var1;
   }

   public final MovementState a(MovementState var1) {
      super.a(var1);
      if (var1.a != MovementStatus.RUNNING) {
         return var1;
      } else {
         BetterBlockPos var2 = super.a.playerFeet();
         Rotation var3 = RotationUtils.calcRotationFromVec3d(super.a.playerHead(), VecUtils.getBlockPosCenter(super.b), super.a.playerRotations());
         Rotation var4 = null;
         class_2680 var5;
         (var5 = super.a.world().method_8320(super.b)).method_26204();
         boolean var18;
         if (!(var18 = var5.method_26227().method_15772() instanceof class_3621)) {
            CalculationContext var7 = new CalculationContext(super.a);
            MutableMoveResult var8 = new MutableMoveResult();
            int var10001 = super.a.x;
            int var10002 = super.a.z;
            if (MovementDescend.a(var7, super.a.y, super.b.x, super.b.z, (double)0.0F, var7.a(super.b.x, super.a.y - 2, super.b.z), var8) && !((class_2338)var2).equals(super.b)) {
               if (!class_1661.method_7380(super.a.player().method_31548().method_7395(a)) || super.a.world().method_27983() == class_1937.field_25180) {
                  MovementStatus var11 = MovementStatus.UNREACHABLE;
                  var1.a = var11;
                  return var1;
               }

               if (super.a.player().method_73189().field_1351 - (double)super.b.method_10264() < super.a.playerController().getBlockReachDistance() && !super.a.player().method_24828()) {
                  super.a.player().method_31548().method_61496(super.a.player().method_31548().method_7395(a));
                  var4 = new Rotation(var3.getYaw(), 90.0F);
                  if (super.a.isLookingAt(super.b) || super.a.isLookingAt(super.b.below())) {
                     var1.a(Input.CLICK_RIGHT, true);
                  }
               }
            }
         }

         if (var4 != null) {
            var1.a(new MovementState.MovementTarget(var4, true));
         } else {
            var1.a(new MovementState.MovementTarget(var3, false));
         }

         if (((class_2338)var2).equals(super.b) && (super.a.player().method_73189().field_1351 - (double)((class_2338)var2).method_10264() < 0.094 || var18)) {
            if (!var18) {
               MovementStatus var10 = MovementStatus.SUCCESS;
               var1.a = var10;
               return var1;
            }

            if (class_1661.method_7380(super.a.player().method_31548().method_7395(b))) {
               super.a.player().method_31548().method_61496(super.a.player().method_31548().method_7395(b));
               if (super.a.player().method_18798().field_1351 >= (double)0.0F) {
                  return var1.a(Input.CLICK_RIGHT, true);
               }

               return var1;
            }

            if (super.a.player().method_18798().field_1351 >= (double)0.0F) {
               MovementStatus var9 = MovementStatus.SUCCESS;
               var1.a = var9;
               return var1;
            }
         }

         class_243 var12 = VecUtils.getBlockPosCenter(super.b);
         if (Math.abs(super.a.player().method_73189().field_1352 + super.a.player().method_18798().field_1352 - var12.field_1352) > 0.1 || Math.abs(super.a.player().method_73189().field_1350 + super.a.player().method_18798().field_1350 - var12.field_1350) > 0.1) {
            if (!super.a.player().method_24828() && Math.abs(super.a.player().method_18798().field_1351) > 0.4) {
               var1.a(Input.SNEAK, true);
            }

            var1.a(Input.MOVE_FORWARD, true);
         }

         MovementFall var6 = this;
         int var19 = 0;

         class_2350 var10000;
         while(true) {
            if (var19 >= 15) {
               var10000 = null;
               break;
            }

            class_2680 var20;
            if ((var20 = var6.a.world().method_8320(var6.a.playerFeet().below(var19))).method_26204() == class_2246.field_9983) {
               var10000 = (class_2350)var20.method_11654(class_2399.field_11253);
               break;
            }

            ++var19;
         }

         Object var17;
         if ((var17 = (class_2382)Optional.ofNullable(var10000).map(class_2350::method_62675).orElse((Object)null)) == null) {
            var17 = super.a.method_10059(super.b);
         } else if (Math.abs((double)((class_2382)var17).method_10263() * (var12.field_1352 - (double)((class_2382)var17).method_10263() / (double)2.0F - super.a.player().method_73189().field_1352)) + Math.abs((double)((class_2382)var17).method_10260() * (var12.field_1350 - (double)((class_2382)var17).method_10260() / (double)2.0F - super.a.player().method_73189().field_1350)) < 0.6) {
            var1.a(Input.MOVE_FORWARD, true);
         } else if (!super.a.player().method_24828()) {
            var1.a(Input.SNEAK, false);
         }

         if (var4 == null) {
            var12 = new class_243(var12.field_1352 + (double)0.125F * (double)((class_2382)var17).method_10263(), var12.field_1351, var12.field_1350 + (double)0.125F * (double)((class_2382)var17).method_10260());
            var1.a(new MovementState.MovementTarget(RotationUtils.calcRotationFromVec3d(super.a.playerHead(), var12, super.a.playerRotations()), false));
         }

         return var1;
      }
   }

   public final boolean b(MovementState var1) {
      return super.a.playerFeet().equals(super.a) || var1.a != MovementStatus.RUNNING;
   }

   private static BetterBlockPos[] a(BetterBlockPos var0, BetterBlockPos var1) {
      int var2 = var0.method_10263() - var1.method_10263();
      int var3 = var0.method_10260() - var1.method_10260();
      var1 = new BetterBlockPos[Math.abs(var0.method_10264() - var1.method_10264()) + 2];

      for(int var4 = 0; var4 < ((Object[])var1).length; ++var4) {
         ((Object[])var1)[var4] = new BetterBlockPos(var0.method_10263() - var2, var0.method_10264() + 1 - var4, var0.method_10260() - var3);
      }

      return var1;
   }

   public final boolean a(MovementState var1) {
      if (var1.a == MovementStatus.WAITING) {
         return true;
      } else {
         for(int var2 = 0; var2 < 4 && var2 < super.a.length; ++var2) {
            if (!MovementHelper.a(super.a, super.a[var2])) {
               return super.a(var1);
            }
         }

         return true;
      }
   }

   static {
      a = new class_1799(class_1802.field_8705);
      b = new class_1799(class_1802.field_8550);
   }
}
