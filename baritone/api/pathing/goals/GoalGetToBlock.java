package baritone.api.pathing.goals;

import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.SettingsUtil;
import baritone.api.utils.interfaces.IGoalRenderPos;
import net.minecraft.class_2338;

public class GoalGetToBlock implements Goal, IGoalRenderPos {
   public final int x;
   public final int y;
   public final int z;

   public GoalGetToBlock(class_2338 var1) {
      this.x = var1.method_10263();
      this.y = var1.method_10264();
      this.z = var1.method_10260();
   }

   public class_2338 getGoalPos() {
      return new class_2338(this.x, this.y, this.z);
   }

   public boolean isInGoal(int var1, int var2, int var3) {
      var1 -= this.x;
      var2 -= this.y;
      var3 -= this.z;
      return Math.abs(var1) + Math.abs(var2 < 0 ? var2 + 1 : var2) + Math.abs(var3) <= 1;
   }

   public double heuristic(int var1, int var2, int var3) {
      var1 -= this.x;
      var2 -= this.y;
      var3 -= this.z;
      return GoalBlock.calculate((double)var1, var2 < 0 ? var2 + 1 : var2, (double)var3);
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         GoalGetToBlock var2 = (GoalGetToBlock)var1;
         return this.x == var2.x && this.y == var2.y && this.z == var2.z;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return (int)BetterBlockPos.longHash(this.x, this.y, this.z) * -49639096;
   }

   public String toString() {
      return String.format("GoalGetToBlock{x=%s,y=%s,z=%s}", SettingsUtil.maybeCensor(this.x), SettingsUtil.maybeCensor(this.y), SettingsUtil.maybeCensor(this.z));
   }
}
