package baritone.api.schematic.mask;

public abstract class AbstractMask implements Mask {
   private final int widthX;
   private final int heightY;
   private final int lengthZ;

   public AbstractMask(int var1, int var2, int var3) {
      this.widthX = var1;
      this.heightY = var2;
      this.lengthZ = var3;
   }

   public int widthX() {
      return this.widthX;
   }

   public int heightY() {
      return this.heightY;
   }

   public int lengthZ() {
      return this.lengthZ;
   }
}
