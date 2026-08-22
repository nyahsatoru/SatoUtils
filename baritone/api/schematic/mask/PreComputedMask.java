package baritone.api.schematic.mask;

final class PreComputedMask extends AbstractMask implements StaticMask {
   private final boolean[][][] mask = new boolean[((AbstractMask)this).heightY()][((AbstractMask)this).lengthZ()][((AbstractMask)this).widthX()];

   public PreComputedMask(StaticMask var1) {
      super(var1.widthX(), var1.heightY(), var1.lengthZ());

      for(int var2 = 0; var2 < ((AbstractMask)this).heightY(); ++var2) {
         for(int var3 = 0; var3 < ((AbstractMask)this).lengthZ(); ++var3) {
            for(int var4 = 0; var4 < ((AbstractMask)this).widthX(); ++var4) {
               this.mask[var2][var3][var4] = var1.partOfMask(var4, var2, var3);
            }
         }
      }

   }

   public final boolean partOfMask(int var1, int var2, int var3) {
      return this.mask[var2][var3][var1];
   }
}
