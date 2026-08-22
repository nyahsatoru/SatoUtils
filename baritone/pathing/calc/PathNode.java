package baritone.pathing.calc;

import baritone.api.pathing.goals.Goal;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.SettingsUtil;

public final class PathNode {
   public final int a;
   public final int b;
   public final int c;
   public final double a;
   public double b = (double)1000000.0F;
   public double c;
   public PathNode a = null;
   public int d;

   public PathNode(int var1, int var2, int var3, Goal var4) {
      this.a = var4.heuristic(var1, var2, var3);
      if (Double.isNaN(this.a)) {
         throw new IllegalStateException(String.format("%s calculated implausible heuristic NaN at %s %s %s", var4, SettingsUtil.maybeCensor(var1), SettingsUtil.maybeCensor(var2), SettingsUtil.maybeCensor(var3)));
      } else {
         this.d = -1;
         this.a = var1;
         this.b = var2;
         this.c = var3;
      }
   }

   public final int hashCode() {
      return (int)BetterBlockPos.longHash(this.a, this.b, this.c);
   }

   public final boolean equals(Object var1) {
      PathNode var2 = (PathNode)var1;
      return this.a == var2.a && this.b == var2.b && this.c == var2.c;
   }
}
