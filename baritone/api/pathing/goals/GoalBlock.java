package baritone.api.pathing.goals;

import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.SettingsUtil;
import baritone.api.utils.interfaces.IGoalRenderPos;
import net.minecraft.class_2338;

public class GoalBlock implements Goal, IGoalRenderPos {
   public final int x;
   public final int y;
   public final int z;

   public GoalBlock(class_2338 var1) {
      this(var1.method_10263(), var1.method_10264(), var1.method_10260());
   }

   public GoalBlock(int var1, int var2, int var3) {
      this.x = var1;
      this.y = var2;
      this.z = var3;
   }

   public boolean isInGoal(int var1, int var2, int var3) {
      return var1 == this.x && var2 == this.y && var3 == this.z;
   }

   public double heuristic(int var1, int var2, int var3) {
      var1 -= this.x;
      var2 -= this.y;
      var3 -= this.z;
      return calculate((double)var1, var2, (double)var3);
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         GoalBlock var2 = (GoalBlock)var1;
         return this.x == var2.x && this.y == var2.y && this.z == var2.z;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return (int)BetterBlockPos.longHash(this.x, this.y, this.z) * 905165533;
   }

   public String toString() {
      return String.format("GoalBlock{x=%s,y=%s,z=%s}", SettingsUtil.maybeCensor(this.x), SettingsUtil.maybeCensor(this.y), SettingsUtil.maybeCensor(this.z));
   }

   public class_2338 getGoalPos() {
      return new class_2338(this.x, this.y, this.z);
   }

   public static double calculate(double var0, int var2, double var3) {
      return (double)0.0F + GoalYLevel.calculate(0, var2) + GoalXZ.calculate(var0, var3);
   }
}
