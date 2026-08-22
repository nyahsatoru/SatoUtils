package baritone.pathing.calc;

import baritone.Baritone;
import baritone.api.pathing.calc.IPath;
import baritone.api.pathing.goals.Goal;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.SettingsUtil;
import baritone.cache.CachedRegion;
import baritone.pathing.calc.openset.BinaryHeapOpenSet;
import baritone.pathing.movement.CalculationContext;
import baritone.pathing.movement.Moves;
import baritone.utils.BlockStateInterface;
import baritone.utils.pathing.BetterWorldBorder;
import baritone.utils.pathing.Favoring;
import baritone.utils.pathing.MutableMoveResult;
import java.io.PrintStream;
import java.util.Optional;
import net.minecraft.class_2806;
import net.minecraft.class_2818;

public final class AStarPathFinder extends AbstractNodeCostSearch {
   private final Favoring a;
   private final CalculationContext a;

   public AStarPathFinder(BetterBlockPos var1, int var2, int var3, int var4, Goal var5, Favoring var6, CalculationContext var7) {
      super(var1, var2, var3, var4, var5, var7);
      this.a = var6;
      this.a = var7;
   }

   protected final Optional<IPath> a(long var1, long var3) {
      int var5 = this.a.a.method_8597().comp_651();
      int var6 = this.a.a.method_8597().comp_652();
      super.a = this.a(super.a, super.b, super.c, BetterBlockPos.longHash(super.a, super.b, super.c));
      super.a.b = (double)0.0F;
      super.a.c = super.a.a;
      BinaryHeapOpenSet var7;
      (var7 = new BinaryHeapOpenSet()).a(super.a);
      double[] var8 = new double[a.length];

      for(int var9 = 0; var9 < var8.length; ++var9) {
         var8[var9] = super.a.a;
         super.a[var9] = super.a;
      }

      MutableMoveResult var52 = new MutableMoveResult();
      BetterWorldBorder var10 = new BetterWorldBorder(this.a.a.method_8621());
      long var11 = System.currentTimeMillis();
      boolean var13;
      if (var13 = (Boolean)Baritone.a().slowPath.value) {
         String var10001 = String.valueOf(Baritone.a().slowPathTimeoutMS.value);
         this.logDebug("slowPath is on, path timeout will be " + var10001 + "ms instead of " + var1 + "ms");
      }

      long var14 = var11 + (var13 ? (Long)Baritone.a().slowPathTimeoutMS.value : var1);
      long var16 = var11 + (var13 ? (Long)Baritone.a().slowPathTimeoutMS.value : var3);
      boolean var50 = true;
      int var2 = 0;
      int var51 = 0;
      int var4 = 0;
      boolean var18 = !this.a.a.isEmpty();
      int var19 = (Integer)Baritone.a().pathingMaxChunkBorderFetch.value;
      double var24 = (Boolean)Baritone.a().minimumImprovementRepropagation.value ? 0.01 : (double)0.0F;
      Moves[] var20 = Moves.values();

      label236:
      while(true) {
         long var27;
         if (var7.a != 0 && var4 < var19 && !super.a && ((var2 & 63) != 0 || (var27 = System.currentTimeMillis()) - var16 < 0L && (var50 || var27 - var14 < 0L))) {
            if (var13) {
               try {
                  Thread.sleep((Long)Baritone.a().slowPathTimeDelayMS.value);
               } catch (InterruptedException var49) {
               }
            }

            BinaryHeapOpenSet var22 = var7;
            if (var7.a == 0) {
               throw new IllegalStateException("Cannot remove from empty heap");
            }

            PathNode var41 = var7.a[1];
            PathNode var42 = var7.a[var7.a];
            var7.a[1] = var42;
            var42.d = 1;
            var7.a[var7.a] = null;
            --var7.a;
            var41.d = -1;
            if (var7.a >= 2) {
               int var30 = 1;
               int var31 = 2;
               double var43 = var42.c;

               do {
                  PathNode var32;
                  double var45 = (var32 = var22.a[var31]).c;
                  if (var31 < var22.a) {
                     PathNode var21;
                     double var47 = (var21 = var22.a[var31 + 1]).c;
                     if (var45 > var47) {
                        ++var31;
                        var45 = var47;
                        var32 = var21;
                     }
                  }

                  if (var43 <= var45) {
                     break;
                  }

                  var22.a[var30] = var32;
                  var22.a[var31] = var42;
                  var42.d = var31;
                  var32.d = var30;
                  var30 = var31;
               } while((var31 <<= 1) <= var22.a);
            }

            PathNode var58 = var41;
            super.b = var41;
            ++var2;
            if (super.a.isInGoal(var41.a, var41.b, var41.c)) {
               long var66 = System.currentTimeMillis() - var11;
               this.logDebug("Took " + var66 + "ms, " + var51 + " movements considered");
               return Optional.of(new Path(super.a, super.a, var41, var2, super.a, this.a));
            }

            Moves[] var28 = var20;
            int var53 = var20.length;
            int var54 = 0;

            while(true) {
               if (var54 >= var53) {
                  continue label236;
               }

               label276: {
                  Moves var23 = var28[var54];
                  int var26 = var58.a + var23.a;
                  int var29 = var58.c + var23.c;
                  if (var26 >> 4 != var58.a >> 4 || var29 >> 4 != var58.c >> 4) {
                     class_2818 var44;
                     BlockStateInterface var59;
                     boolean var63;
                     if ((var44 = (var59 = this.a.a).a) != null && var44.method_12004().field_9181 == var26 >> 4 && var44.method_12004().field_9180 == var29 >> 4) {
                        var63 = true;
                     } else if ((var44 = var59.a.method_2857(var26 >> 4, var29 >> 4, class_2806.field_12803, false)) != null && !var44.method_12223()) {
                        var59.a = var44;
                        var63 = true;
                     } else {
                        CachedRegion var60;
                        if ((var60 = var59.a) != null && var60.getX() == var26 >> 9 && var60.getZ() == var29 >> 9) {
                           var63 = var60.isCached(var26 & 511, var29 & 511);
                        } else if (var59.a == null) {
                           var63 = false;
                        } else if ((var60 = var59.a.a.a(var26 >> 9, var29 >> 9)) == null) {
                           var63 = false;
                        } else {
                           var59.a = var60;
                           var63 = var60.isCached(var26 & 511, var29 & 511);
                        }
                     }

                     if (!var63) {
                        if (!var23.a) {
                           ++var4;
                        }
                        break label276;
                     }
                  }

                  if ((var23.a || var10.a(var26, var29)) && var58.b + var23.b <= var6 && var58.b + var23.b >= var5) {
                     var52.a();
                     var23.a(this.a, var58.a, var58.b, var58.c, var52);
                     ++var51;
                     double var33;
                     if (!((var33 = var52.a) >= (double)1000000.0F)) {
                        if (var33 <= (double)0.0F || Double.isNaN(var33)) {
                           throw new IllegalStateException(String.format("%s from %s %s %s calculated implausible cost %s", var23, SettingsUtil.maybeCensor(var58.a), SettingsUtil.maybeCensor(var58.b), SettingsUtil.maybeCensor(var58.c), var33));
                        }

                        if (!var23.a || var10.a(var52.a, var52.c)) {
                           if (!var23.a && (var52.a != var26 || var52.c != var29)) {
                              throw new IllegalStateException(String.format("%s from %s %s %s ended at x z %s %s instead of %s %s", var23, SettingsUtil.maybeCensor(var58.a), SettingsUtil.maybeCensor(var58.b), SettingsUtil.maybeCensor(var58.c), SettingsUtil.maybeCensor(var52.a), SettingsUtil.maybeCensor(var52.c), SettingsUtil.maybeCensor(var26), SettingsUtil.maybeCensor(var29)));
                           }

                           if (!var23.b && var52.b != var58.b + var23.b) {
                              throw new IllegalStateException(String.format("%s from %s %s %s ended at y %s instead of %s", var23, SettingsUtil.maybeCensor(var58.a), SettingsUtil.maybeCensor(var58.b), SettingsUtil.maybeCensor(var58.c), SettingsUtil.maybeCensor(var52.b), SettingsUtil.maybeCensor(var58.b + var23.b)));
                           }

                           long var35 = BetterBlockPos.longHash(var52.a, var52.b, var52.c);
                           if (var18) {
                              var33 *= this.a.a.get(var35);
                           }

                           PathNode var55 = this.a(var52.a, var52.b, var52.c, var35);
                           double var37 = var58.b + var33;
                           if (var55.b - var37 > var24) {
                              var55.a = var58;
                              var55.b = var37;
                              var55.c = var37 + var55.a;
                              if (var55.d != -1) {
                                 var7.b(var55);
                              } else {
                                 var7.a(var55);
                              }

                              for(int var56 = 0; var56 < a.length; ++var56) {
                                 double var39 = var55.a + var55.b / a[var56];
                                 if (var8[var56] - var39 > var24) {
                                    var8[var56] = var39;
                                    super.a[var56] = var55;
                                    if (var50 && this.a(var55) > (double)25.0F) {
                                       var50 = false;
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }

               ++var54;
            }
         }

         if (super.a) {
            return Optional.empty();
         }

         System.out.println(var51 + " movements considered");
         System.out.println("Open set size: " + var7.a);
         System.out.println("PathNode map size: " + this.a());
         PrintStream var10000 = System.out;
         double var64 = (double)var2;
         float var10002 = (float)(System.currentTimeMillis() - var11);
         var10000.println((int)(var64 / (double)(var10002 / 1000.0F)) + " nodes per second");
         Optional var57;
         if ((var57 = this.a(true, var2)).isPresent()) {
            long var65 = System.currentTimeMillis() - var11;
            this.logDebug("Took " + var65 + "ms, " + var51 + " movements considered");
         }

         return var57;
      }
   }
}
