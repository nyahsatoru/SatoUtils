package baritone.pathing.calc;

import baritone.Baritone;
import baritone.api.pathing.calc.IPath;
import baritone.api.pathing.calc.IPathFinder;
import baritone.api.pathing.goals.Goal;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.Helper;
import baritone.api.utils.PathCalculationResult;
import baritone.pathing.movement.CalculationContext;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.Optional;

public abstract class AbstractNodeCostSearch implements IPathFinder, Helper {
   protected final BetterBlockPos a;
   protected final int a;
   protected final int b;
   protected final int c;
   protected final Goal a;
   private final CalculationContext a;
   private final Long2ObjectOpenHashMap<PathNode> a;
   protected PathNode a;
   protected PathNode b;
   protected final PathNode[] a;
   private volatile boolean b;
   protected boolean a;
   protected static final double[] a = new double[]{(double)1.5F, (double)2.0F, (double)2.5F, (double)3.0F, (double)4.0F, (double)5.0F, (double)10.0F};

   AbstractNodeCostSearch(BetterBlockPos var1, int var2, int var3, int var4, Goal var5, CalculationContext var6) {
      this.a = new PathNode[a.length];
      this.a = var1;
      this.a = var2;
      this.b = var3;
      this.c = var4;
      this.a = var5;
      this.a = var6;
      this.a = new Long2ObjectOpenHashMap((Integer)Baritone.a().pathingMapDefaultSize.value, (Float)Baritone.a().pathingMapLoadFactor.value);
   }

   public void a() {
      this.a = true;
   }

   public synchronized PathCalculationResult calculate(long var1, long var3) {
      if (this.b) {
         throw new IllegalStateException("Path finder cannot be reused!");
      } else {
         this.a = false;

         PathCalculationResult var14;
         try {
            IPath var9 = (IPath)this.a(var1, var3).map(IPath::postProcess).orElse((Object)null);
            if (!this.a) {
               if (var9 == null) {
                  var14 = new PathCalculationResult(PathCalculationResult.Type.FAILURE);
                  return var14;
               }

               int var15 = var9.length();
               if ((var9 = var9.cutoffAtLoadedChunks(this.a.a)).length() < var15) {
                  Helper.HELPER.logDebug("Cutting off path at edge of loaded chunks");
                  Helper var10000 = Helper.HELPER;
                  int var10001 = var15 - var9.length();
                  var10000.logDebug("Length decreased by " + var10001);
               } else {
                  Helper.HELPER.logDebug("Path ends within loaded chunks");
               }

               var15 = var9.length();
               if ((var9 = var9.staticCutoff(this.a)).length() < var15) {
                  Helper.HELPER.logDebug("Static cutoff " + var15 + " to " + var9.length());
               }

               if (this.a.isInGoal(var9.getDest())) {
                  PathCalculationResult var13 = new PathCalculationResult(PathCalculationResult.Type.SUCCESS_TO_GOAL, var9);
                  return var13;
               }

               PathCalculationResult var12 = new PathCalculationResult(PathCalculationResult.Type.SUCCESS_SEGMENT, var9);
               return var12;
            }

            var14 = new PathCalculationResult(PathCalculationResult.Type.CANCELLATION);
         } catch (Exception var7) {
            Helper.HELPER.logDirect("Pathing exception: " + String.valueOf(var7));
            var7.printStackTrace();
            var14 = new PathCalculationResult(PathCalculationResult.Type.EXCEPTION);
            return var14;
         } finally {
            this.b = true;
         }

         return var14;
      }
   }

   protected abstract Optional<IPath> a(long var1, long var3);

   protected final double a(PathNode var1) {
      int var2 = var1.a - this.a;
      int var3 = var1.b - this.b;
      int var4 = var1.c - this.c;
      return (double)(var2 * var2 + var3 * var3 + var4 * var4);
   }

   protected final PathNode a(int var1, int var2, int var3, long var4) {
      PathNode var6;
      if ((var6 = (PathNode)this.a.get(var4)) == null) {
         var6 = new PathNode(var1, var2, var3, this.a);
         this.a.put(var4, var6);
      }

      return var6;
   }

   public Optional<IPath> pathToMostRecentNodeConsidered() {
      return Optional.ofNullable(this.b).map((var1) -> new Path(this.a, this.a, var1, 0, this.a, this.a));
   }

   public Optional<IPath> bestPathSoFar() {
      return this.a(false, 0);
   }

   protected final Optional<IPath> a(boolean var1, int var2) {
      if (this.a == null) {
         return Optional.empty();
      } else {
         double var3 = (double)0.0F;

         for(int var5 = 0; var5 < a.length; ++var5) {
            if (this.a[var5] != null) {
               double var6;
               if ((var6 = this.a(this.a[var5])) > var3) {
                  var3 = var6;
               }

               if (var6 > (double)25.0F) {
                  if (var1) {
                     if (a[var5] >= (double)3.0F) {
                        System.out.println("Warning: cost coefficient is greater than three! Probably means that");
                        System.out.println("the path I found is pretty terrible (like sneak-bridging for dozens of blocks)");
                        System.out.println("But I'm going to do it anyway, because yolo");
                     }

                     System.out.println("Path goes for " + Math.sqrt(var6) + " blocks");
                     double var10001 = a[var5];
                     this.logDebug("A* cost coefficient " + var10001);
                  }

                  return Optional.of(new Path(this.a, this.a, this.a[var5], var2, this.a, this.a));
               }
            }
         }

         if (var1) {
            double[] var8 = a;
            this.logDebug("Even with a cost coefficient of " + var8[var8.length - 1] + ", I couldn't get more than " + Math.sqrt(var3) + " blocks");
            this.logDebug("No path found =(");
            this.logNotification("No path found =(", true);
         }

         return Optional.empty();
      }
   }

   public final boolean isFinished() {
      return this.b;
   }

   public final Goal getGoal() {
      return this.a;
   }

   public final BetterBlockPos a() {
      return new BetterBlockPos(this.a, this.b, this.c);
   }

   protected final int a() {
      return this.a.size();
   }
}
