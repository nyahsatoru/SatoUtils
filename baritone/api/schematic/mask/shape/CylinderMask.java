package baritone.api.schematic.mask.shape;

import baritone.api.schematic.mask.AbstractMask;
import baritone.api.schematic.mask.StaticMask;
import net.minecraft.class_2350;
import net.minecraft.class_2350.class_2351;

public final class CylinderMask extends AbstractMask implements StaticMask {
   private final double centerA;
   private final double centerB;
   private final double radiusSqA;
   private final double radiusSqB;
   private final boolean filled;
   private final class_2350.class_2351 alignment;

   public CylinderMask(int var1, int var2, int var3, boolean var4, class_2350.class_2351 var5) {
      super(var1, var2, var3);
      this.centerA = (double)getA(var1, var2, var5) / (double)2.0F;
      this.centerB = (double)getB(var2, var3, var5) / (double)2.0F;
      this.radiusSqA = (this.centerA - (double)1.0F) * (this.centerA - (double)1.0F);
      this.radiusSqB = (this.centerB - (double)1.0F) * (this.centerB - (double)1.0F);
      this.filled = var4;
      this.alignment = var5;
   }

   public final boolean partOfMask(int var1, int var2, int var3) {
      double var4 = Math.abs((double)getA(var1, var2, this.alignment) + (double)0.5F - this.centerA);
      double var6 = Math.abs((double)getB(var2, var3, this.alignment) + (double)0.5F - this.centerB);
      if (this.outside(var4, var6)) {
         return false;
      } else {
         return this.filled || this.outside(var4 + (double)1.0F, var6) || this.outside(var4, var6 + (double)1.0F);
      }
   }

   private boolean outside(double var1, double var3) {
      return var1 * var1 / this.radiusSqA + var3 * var3 / this.radiusSqB > (double)1.0F;
   }

   private static int getA(int var0, int var1, class_2350.class_2351 var2) {
      return var2 == class_2351.field_11048 ? var1 : var0;
   }

   private static int getB(int var0, int var1, class_2350.class_2351 var2) {
      return var2 == class_2351.field_11051 ? var0 : var1;
   }
}
