package baritone.api.pathing.goals;

import baritone.api.BaritoneAPI;

public class GoalAxis implements Goal {
   private static final double SQRT_2_OVER_2 = Math.sqrt((double)2.0F) / (double)2.0F;

   public boolean isInGoal(int var1, int var2, int var3) {
      return var2 == (Integer)BaritoneAPI.getSettings().axisHeight.value && (var1 == 0 || var3 == 0 || Math.abs(var1) == Math.abs(var3));
   }

   public double heuristic(int var1, int var2, int var3) {
      var1 = Math.abs(var1);
      var3 = Math.abs(var3);
      int var4 = Math.min(var1, var3);
      var4 = Math.max(var1, var3) - var4;
      return Math.min((double)var1, Math.min((double)var3, (double)var4 * SQRT_2_OVER_2)) * (Double)BaritoneAPI.getSettings().costHeuristic.value + GoalYLevel.calculate((Integer)BaritoneAPI.getSettings().axisHeight.value, var2);
   }

   public boolean equals(Object var1) {
      return var1.getClass() == GoalAxis.class;
   }

   public int hashCode() {
      return 201385781;
   }

   public String toString() {
      return "GoalAxis";
   }
}
