package baritone.api.pathing.goals;

import java.util.Objects;

public class GoalInverted implements Goal {
   public final Goal origin;

   public GoalInverted(Goal var1) {
      this.origin = var1;
   }

   public boolean isInGoal(int var1, int var2, int var3) {
      return false;
   }

   public double heuristic(int var1, int var2, int var3) {
      return -this.origin.heuristic(var1, var2, var3);
   }

   public double heuristic() {
      return Double.NEGATIVE_INFINITY;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         GoalInverted var2 = (GoalInverted)var1;
         return Objects.equals(this.origin, var2.origin);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.origin.hashCode() * 495796690;
   }

   public String toString() {
      return String.format("GoalInverted{%s}", this.origin.toString());
   }
}
