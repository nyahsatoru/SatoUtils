package baritone.utils.pathing;

import baritone.Baritone;
import baritone.api.BaritoneAPI;
import baritone.api.pathing.calc.IPath;
import baritone.api.pathing.goals.Goal;
import baritone.pathing.path.CutoffPath;
import baritone.utils.BlockStateInterface;
import net.minecraft.class_2338;

public abstract class PathBase implements IPath {
   // $FF: synthetic method
   public IPath staticCutoff(Goal var1) {
      int var3 = (Integer)BaritoneAPI.getSettings().pathCutoffMinimumLength.value;
      if (this.length() < var3) {
         return this;
      } else if (var1 != null && !var1.isInGoal(this.getDest())) {
         double var4 = (Double)BaritoneAPI.getSettings().pathCutoffFactor.value;
         int var2 = (int)((double)(this.length() - var3) * var4) + var3 - 1;
         return new CutoffPath(this, var2);
      } else {
         return this;
      }
   }

   // $FF: synthetic method
   public IPath cutoffAtLoadedChunks(Object var1) {
      PathBase var5 = this;
      if ((Boolean)Baritone.a().cutoffAtLoadBoundary.value) {
         BlockStateInterface var6 = (BlockStateInterface)var1;

         for(int var3 = 0; var3 < var5.positions().size(); ++var3) {
            class_2338 var4 = (class_2338)var5.positions().get(var3);
            if (!var6.a(var4.method_10263(), var4.method_10260())) {
               return new CutoffPath(var5, var3);
            }
         }
      }

      return var5;
   }
}
