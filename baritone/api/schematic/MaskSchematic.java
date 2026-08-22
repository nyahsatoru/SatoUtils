package baritone.api.schematic;

import baritone.api.schematic.mask.Mask;
import java.util.List;
import net.minecraft.class_2680;

public abstract class MaskSchematic extends AbstractSchematic {
   private final ISchematic schematic;

   public MaskSchematic(ISchematic var1) {
      super(var1.widthX(), var1.heightY(), var1.lengthZ());
      this.schematic = var1;
   }

   protected abstract boolean partOfMask(int var1, int var2, int var3, class_2680 var4);

   public boolean inSchematic(int var1, int var2, int var3, class_2680 var4) {
      return this.schematic.inSchematic(var1, var2, var3, var4) && this.partOfMask(var1, var2, var3, var4);
   }

   public class_2680 desiredState(int var1, int var2, int var3, class_2680 var4, List<class_2680> var5) {
      return this.schematic.desiredState(var1, var2, var3, var4, var5);
   }

   public static MaskSchematic create(ISchematic var0, final Mask var1) {
      return new MaskSchematic(var0) {
         protected boolean partOfMask(int var1x, int var2, int var3, class_2680 var4) {
            return var1.partOfMask(var1x, var2, var3, var4);
         }
      };
   }
}
