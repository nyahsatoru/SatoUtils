package baritone.pathing.path;

import baritone.api.pathing.calc.IPath;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.movement.IMovement;
import baritone.api.utils.BetterBlockPos;
import baritone.utils.pathing.PathBase;
import java.util.Collections;
import java.util.List;

public class CutoffPath extends PathBase {
   private final List<BetterBlockPos> a;
   private final List<IMovement> b;
   private final int a;
   private final Goal a;

   public CutoffPath(IPath var1, int var2, int var3) {
      this.a = var1.positions().subList(var2, var3 + 1);
      this.b = var1.movements().subList(var2, var3);
      this.a = var1.getNumNodesConsidered();
      this.a = var1.getGoal();
      this.sanityCheck();
   }

   public CutoffPath(PathBase var1, int var2) {
      this(var1, 0, var2);
   }

   public Goal getGoal() {
      return this.a;
   }

   public List<IMovement> movements() {
      return Collections.unmodifiableList(this.b);
   }

   public List<BetterBlockPos> positions() {
      return Collections.unmodifiableList(this.a);
   }

   public int getNumNodesConsidered() {
      return this.a;
   }
}
