package baritone.api.pathing.goals;

import java.util.Arrays;

public class GoalComposite implements Goal {
   private final Goal[] goals;

   public GoalComposite(Goal... var1) {
      this.goals = var1;
   }

   public boolean isInGoal(int var1, int var2, int var3) {
      Goal[] var4;
      int var5 = (var4 = this.goals).length;

      for(int var6 = 0; var6 < var5; ++var6) {
         if (var4[var6].isInGoal(var1, var2, var3)) {
            return true;
         }
      }

      return false;
   }

   public double heuristic(int var1, int var2, int var3) {
      double var4 = Double.MAX_VALUE;

      Goal[] var6;
      for(Goal var9 : var6 = this.goals) {
         var4 = Math.min(var4, var9.heuristic(var1, var2, var3));
      }

      return var4;
   }

   public double heuristic() {
      double var1 = Double.MAX_VALUE;

      Goal[] var3;
      for(Goal var6 : var3 = this.goals) {
         var1 = Math.min(var1, var6.heuristic());
      }

      return var1;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         GoalComposite var2 = (GoalComposite)var1;
         return Arrays.equals(this.goals, var2.goals);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Arrays.hashCode(this.goals);
   }

   public String toString() {
      return "GoalComposite" + Arrays.toString(this.goals);
   }

   public Goal[] goals() {
      return this.goals;
   }
}
