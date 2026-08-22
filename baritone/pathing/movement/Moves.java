package baritone.pathing.movement;

import baritone.api.utils.BetterBlockPos;
import baritone.pathing.movement.movements.MovementAscend;
import baritone.pathing.movement.movements.MovementDescend;
import baritone.pathing.movement.movements.MovementDiagonal;
import baritone.pathing.movement.movements.MovementDownward;
import baritone.pathing.movement.movements.MovementFall;
import baritone.pathing.movement.movements.MovementParkour;
import baritone.pathing.movement.movements.MovementPillar;
import baritone.pathing.movement.movements.MovementTraverse;
import baritone.utils.pathing.MutableMoveResult;
import net.minecraft.class_2350;

public enum Moves {
   a {
      public final Movement a(CalculationContext var1, BetterBlockPos var2) {
         return new MovementDownward(var1.a, var2, var2.below());
      }

      public final double a(CalculationContext var1, int var2, int var3, int var4) {
         return MovementDownward.a(var1, var2, var3, var4);
      }
   },
   a {
      public final Movement a(CalculationContext var1, BetterBlockPos var2) {
         return new MovementPillar(var1.a, var2, var2.above());
      }

      public final double a(CalculationContext var1, int var2, int var3, int var4) {
         return MovementPillar.a(var1, var2, var3, var4);
      }
   },
   a {
      public final Movement a(CalculationContext var1, BetterBlockPos var2) {
         return new MovementTraverse(var1.a, var2, var2.north());
      }

      public final double a(CalculationContext var1, int var2, int var3, int var4) {
         return MovementTraverse.a(var1, var2, var3, var4, var2, var4 - 1);
      }
   },
   a {
      public final Movement a(CalculationContext var1, BetterBlockPos var2) {
         return new MovementTraverse(var1.a, var2, var2.south());
      }

      public final double a(CalculationContext var1, int var2, int var3, int var4) {
         return MovementTraverse.a(var1, var2, var3, var4, var2, var4 + 1);
      }
   },
   a {
      public final Movement a(CalculationContext var1, BetterBlockPos var2) {
         return new MovementTraverse(var1.a, var2, var2.east());
      }

      public final double a(CalculationContext var1, int var2, int var3, int var4) {
         return MovementTraverse.a(var1, var2, var3, var4, var2 + 1, var4);
      }
   },
   a {
      public final Movement a(CalculationContext var1, BetterBlockPos var2) {
         return new MovementTraverse(var1.a, var2, var2.west());
      }

      public final double a(CalculationContext var1, int var2, int var3, int var4) {
         return MovementTraverse.a(var1, var2, var3, var4, var2 - 1, var4);
      }
   },
   a {
      public final Movement a(CalculationContext var1, BetterBlockPos var2) {
         return new MovementAscend(var1.a, var2, new BetterBlockPos(var2.x, var2.y + 1, var2.z - 1));
      }

      public final double a(CalculationContext var1, int var2, int var3, int var4) {
         return MovementAscend.a(var1, var2, var3, var4, var2, var4 - 1);
      }
   },
   a {
      public final Movement a(CalculationContext var1, BetterBlockPos var2) {
         return new MovementAscend(var1.a, var2, new BetterBlockPos(var2.x, var2.y + 1, var2.z + 1));
      }

      public final double a(CalculationContext var1, int var2, int var3, int var4) {
         return MovementAscend.a(var1, var2, var3, var4, var2, var4 + 1);
      }
   },
   a {
      public final Movement a(CalculationContext var1, BetterBlockPos var2) {
         return new MovementAscend(var1.a, var2, new BetterBlockPos(var2.x + 1, var2.y + 1, var2.z));
      }

      public final double a(CalculationContext var1, int var2, int var3, int var4) {
         return MovementAscend.a(var1, var2, var3, var4, var2 + 1, var4);
      }
   },
   a {
      public final Movement a(CalculationContext var1, BetterBlockPos var2) {
         return new MovementAscend(var1.a, var2, new BetterBlockPos(var2.x - 1, var2.y + 1, var2.z));
      }

      public final double a(CalculationContext var1, int var2, int var3, int var4) {
         return MovementAscend.a(var1, var2, var3, var4, var2 - 1, var4);
      }
   },
   a {
      public final Movement a(CalculationContext var1, BetterBlockPos var2) {
         MutableMoveResult var3 = new MutableMoveResult();
         ((Moves)this).a(var1, var2.x, var2.y, var2.z, var3);
         return (Movement)(var3.b == var2.y - 1 ? new MovementDescend(var1.a, var2, new BetterBlockPos(var3.a, var3.b, var3.c)) : new MovementFall(var1.a, var2, new BetterBlockPos(var3.a, var3.b, var3.c)));
      }

      public final void a(CalculationContext var1, int var2, int var3, int var4, MutableMoveResult var5) {
         MovementDescend.a(var1, var2, var3, var4, var2 + 1, var4, var5);
      }
   },
   a {
      public final Movement a(CalculationContext var1, BetterBlockPos var2) {
         MutableMoveResult var3 = new MutableMoveResult();
         ((Moves)this).a(var1, var2.x, var2.y, var2.z, var3);
         return (Movement)(var3.b == var2.y - 1 ? new MovementDescend(var1.a, var2, new BetterBlockPos(var3.a, var3.b, var3.c)) : new MovementFall(var1.a, var2, new BetterBlockPos(var3.a, var3.b, var3.c)));
      }

      public final void a(CalculationContext var1, int var2, int var3, int var4, MutableMoveResult var5) {
         MovementDescend.a(var1, var2, var3, var4, var2 - 1, var4, var5);
      }
   },
   a {
      public final Movement a(CalculationContext var1, BetterBlockPos var2) {
         MutableMoveResult var3 = new MutableMoveResult();
         ((Moves)this).a(var1, var2.x, var2.y, var2.z, var3);
         return (Movement)(var3.b == var2.y - 1 ? new MovementDescend(var1.a, var2, new BetterBlockPos(var3.a, var3.b, var3.c)) : new MovementFall(var1.a, var2, new BetterBlockPos(var3.a, var3.b, var3.c)));
      }

      public final void a(CalculationContext var1, int var2, int var3, int var4, MutableMoveResult var5) {
         MovementDescend.a(var1, var2, var3, var4, var2, var4 - 1, var5);
      }
   },
   a {
      public final Movement a(CalculationContext var1, BetterBlockPos var2) {
         MutableMoveResult var3 = new MutableMoveResult();
         ((Moves)this).a(var1, var2.x, var2.y, var2.z, var3);
         return (Movement)(var3.b == var2.y - 1 ? new MovementDescend(var1.a, var2, new BetterBlockPos(var3.a, var3.b, var3.c)) : new MovementFall(var1.a, var2, new BetterBlockPos(var3.a, var3.b, var3.c)));
      }

      public final void a(CalculationContext var1, int var2, int var3, int var4, MutableMoveResult var5) {
         MovementDescend.a(var1, var2, var3, var4, var2, var4 + 1, var5);
      }
   },
   a {
      public final Movement a(CalculationContext var1, BetterBlockPos var2) {
         MutableMoveResult var3 = new MutableMoveResult();
         ((Moves)this).a(var1, var2.x, var2.y, var2.z, var3);
         return new MovementDiagonal(var1.a, var2, class_2350.field_11043, class_2350.field_11034, var3.b - var2.y);
      }

      public final void a(CalculationContext var1, int var2, int var3, int var4, MutableMoveResult var5) {
         MovementDiagonal.a(var1, var2, var3, var4, var2 + 1, var4 - 1, var5);
      }
   },
   a {
      public final Movement a(CalculationContext var1, BetterBlockPos var2) {
         MutableMoveResult var3 = new MutableMoveResult();
         ((Moves)this).a(var1, var2.x, var2.y, var2.z, var3);
         return new MovementDiagonal(var1.a, var2, class_2350.field_11043, class_2350.field_11039, var3.b - var2.y);
      }

      public final void a(CalculationContext var1, int var2, int var3, int var4, MutableMoveResult var5) {
         MovementDiagonal.a(var1, var2, var3, var4, var2 - 1, var4 - 1, var5);
      }
   },
   a {
      public final Movement a(CalculationContext var1, BetterBlockPos var2) {
         MutableMoveResult var3 = new MutableMoveResult();
         ((Moves)this).a(var1, var2.x, var2.y, var2.z, var3);
         return new MovementDiagonal(var1.a, var2, class_2350.field_11035, class_2350.field_11034, var3.b - var2.y);
      }

      public final void a(CalculationContext var1, int var2, int var3, int var4, MutableMoveResult var5) {
         MovementDiagonal.a(var1, var2, var3, var4, var2 + 1, var4 + 1, var5);
      }
   },
   a {
      public final Movement a(CalculationContext var1, BetterBlockPos var2) {
         MutableMoveResult var3 = new MutableMoveResult();
         ((Moves)this).a(var1, var2.x, var2.y, var2.z, var3);
         return new MovementDiagonal(var1.a, var2, class_2350.field_11035, class_2350.field_11039, var3.b - var2.y);
      }

      public final void a(CalculationContext var1, int var2, int var3, int var4, MutableMoveResult var5) {
         MovementDiagonal.a(var1, var2, var3, var4, var2 - 1, var4 + 1, var5);
      }
   },
   a {
      public final Movement a(CalculationContext var1, BetterBlockPos var2) {
         return MovementParkour.a(var1, var2, class_2350.field_11043);
      }

      public final void a(CalculationContext var1, int var2, int var3, int var4, MutableMoveResult var5) {
         MovementParkour.a(var1, var2, var3, var4, class_2350.field_11043, var5);
      }
   },
   a {
      public final Movement a(CalculationContext var1, BetterBlockPos var2) {
         return MovementParkour.a(var1, var2, class_2350.field_11035);
      }

      public final void a(CalculationContext var1, int var2, int var3, int var4, MutableMoveResult var5) {
         MovementParkour.a(var1, var2, var3, var4, class_2350.field_11035, var5);
      }
   },
   a {
      public final Movement a(CalculationContext var1, BetterBlockPos var2) {
         return MovementParkour.a(var1, var2, class_2350.field_11034);
      }

      public final void a(CalculationContext var1, int var2, int var3, int var4, MutableMoveResult var5) {
         MovementParkour.a(var1, var2, var3, var4, class_2350.field_11034, var5);
      }
   },
   a {
      public final Movement a(CalculationContext var1, BetterBlockPos var2) {
         return MovementParkour.a(var1, var2, class_2350.field_11039);
      }

      public final void a(CalculationContext var1, int var2, int var3, int var4, MutableMoveResult var5) {
         MovementParkour.a(var1, var2, var3, var4, class_2350.field_11039, var5);
      }
   };

   public final boolean a;
   public final boolean b;
   public final int a;
   public final int b;
   public final int c;

   Moves(int var3, int var4, int var5, boolean var6, boolean var7) {
      this.a = var3;
      this.b = var4;
      this.c = var5;
      this.a = var6;
      this.b = var7;
   }

   Moves(int var3, int var4, int var5) {
      this(var3, var4, var5, false, false);
   }

   public abstract Movement a(CalculationContext var1, BetterBlockPos var2);

   public void a(CalculationContext var1, int var2, int var3, int var4, MutableMoveResult var5) {
      if (!this.a && !this.b) {
         var5.a = var2 + this.a;
         var5.b = var3 + this.b;
         var5.c = var4 + this.c;
         var5.a = this.a(var1, var2, var3, var4);
      } else {
         throw new UnsupportedOperationException("Movements with dynamic offset must override `apply`");
      }
   }

   public double a(CalculationContext var1, int var2, int var3, int var4) {
      throw new UnsupportedOperationException("Movements must override `cost` or `apply`");
   }
}
