package baritone.pathing.movement;

import baritone.api.pathing.movement.MovementStatus;
import baritone.api.utils.Rotation;
import baritone.api.utils.input.Input;
import java.util.HashMap;
import java.util.Map;

public class MovementState {
   public MovementStatus a;
   public MovementTarget a = new MovementTarget();
   final Map<Input, Boolean> a = new HashMap();

   public MovementState a(MovementTarget var1) {
      this.a = var1;
      return this;
   }

   public final MovementState a(Input var1, boolean var2) {
      this.a.put(var1, var2);
      return this;
   }

   public static class MovementTarget {
      public Rotation a;
      boolean a;

      public MovementTarget() {
         this((Rotation)null, false);
      }

      public MovementTarget(Rotation var1, boolean var2) {
         this.a = var1;
         this.a = var2;
      }
   }
}
