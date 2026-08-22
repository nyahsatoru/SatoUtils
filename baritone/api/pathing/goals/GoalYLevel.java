package baritone.api.pathing.goals;

import baritone.api.pathing.movement.ActionCosts;
import baritone.api.utils.SettingsUtil;

public class GoalYLevel implements Goal, ActionCosts {
   public final int level;

   public GoalYLevel(int var1) {
      this.level = var1;
   }

   public boolean isInGoal(int var1, int var2, int var3) {
      return var2 == this.level;
   }

   public double heuristic(int var1, int var2, int var3) {
      return calculate(this.level, var2);
   }

   public static double calculate(int var0, int var1) {
      if (var1 > var0) {
         return FALL_N_BLOCKS_COST[2] / (double)2.0F * (double)(var1 - var0);
      } else {
         return var1 < var0 ? (double)(var0 - var1) * JUMP_ONE_BLOCK_COST : (double)0.0F;
      }
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         GoalYLevel var2 = (GoalYLevel)var1;
         return this.level == var2.level;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.level * 1271009915;
   }

   public String toString() {
      return String.format("GoalYLevel{y=%s}", SettingsUtil.maybeCensor(this.level));
   }
}
