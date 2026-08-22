package baritone.api.schematic.mask.shape;

import baritone.api.schematic.mask.AbstractMask;
import baritone.api.schematic.mask.StaticMask;

public final class SphereMask extends AbstractMask implements StaticMask {
   private final double centerX;
   private final double centerY;
   private final double centerZ;
   private final double radiusSqX;
   private final double radiusSqY;
   private final double radiusSqZ;
   private final boolean filled;

   public SphereMask(int var1, int var2, int var3, boolean var4) {
      super(var1, var2, var3);
      this.centerX = (double)var1 / (double)2.0F;
      this.centerY = (double)var2 / (double)2.0F;
      this.centerZ = (double)var3 / (double)2.0F;
      this.radiusSqX = this.centerX * this.centerX;
      this.radiusSqY = this.centerY * this.centerY;
      this.radiusSqZ = this.centerZ * this.centerZ;
      this.filled = var4;
   }

   public final boolean partOfMask(int var1, int var2, int var3) {
      double var4 = Math.abs((double)var1 + (double)0.5F - this.centerX);
      double var6 = Math.abs((double)var2 + (double)0.5F - this.centerY);
      double var8 = Math.abs((double)var3 + (double)0.5F - this.centerZ);
      if (this.outside(var4, var6, var8)) {
         return false;
      } else {
         return this.filled || this.outside(var4 + (double)1.0F, var6, var8) || this.outside(var4, var6 + (double)1.0F, var8) || this.outside(var4, var6, var8 + (double)1.0F);
      }
   }

   private boolean outside(double var1, double var3, double var5) {
      return var1 * var1 / this.radiusSqX + var3 * var3 / this.radiusSqY + var5 * var5 / this.radiusSqZ > (double)1.0F;
   }
}
