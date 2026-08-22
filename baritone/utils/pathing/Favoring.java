package baritone.utils.pathing;

import baritone.api.pathing.calc.IPath;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.Helper;
import baritone.api.utils.IPlayerContext;
import baritone.pathing.movement.CalculationContext;
import it.unimi.dsi.fastutil.longs.Long2DoubleOpenHashMap;

public final class Favoring {
   public final Long2DoubleOpenHashMap a;

   public Favoring(IPlayerContext var1, IPath var2, CalculationContext var3) {
      this(var2, var3);

      for(Avoidance var10000 : Avoidance.a(var1)) {
         Long2DoubleOpenHashMap var11 = this.a;
         Avoidance var10 = var10000;

         for(int var4 = -var10000.d; var4 <= var10.d; ++var4) {
            for(int var5 = -var10.d; var5 <= var10.d; ++var5) {
               for(int var6 = -var10.d; var6 <= var10.d; ++var6) {
                  if (var4 * var4 + var5 * var5 + var6 * var6 <= var10.d * var10.d) {
                     long var7 = BetterBlockPos.longHash(var10.a + var4, var10.b + var5, var10.c + var6);
                     var11.put(var7, var11.get(var7) * var10.a);
                  }
               }
            }
         }
      }

      Helper.HELPER.logDebug("Favoring size: " + this.a.size());
   }

   private Favoring(IPath var1, CalculationContext var2) {
      this.a = new Long2DoubleOpenHashMap();
      this.a.defaultReturnValue((double)1.0F);
      double var3;
      if ((var3 = var2.d) != (double)1.0F && var1 != null) {
         var1.positions().forEach((var3x) -> this.a.put(BetterBlockPos.longHash(var3x), var3));
      }

   }
}
