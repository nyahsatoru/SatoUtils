package baritone.api.schematic;

import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.class_2415;
import net.minecraft.class_2680;

public class MirroredSchematic implements ISchematic {
   private final ISchematic schematic;
   private final class_2415 mirror;

   public MirroredSchematic(ISchematic var1, class_2415 var2) {
      this.schematic = var1;
      this.mirror = var2;
   }

   public boolean inSchematic(int var1, int var2, int var3, class_2680 var4) {
      return this.schematic.inSchematic(mirrorX(var1, this.widthX(), this.mirror), var2, mirrorZ(var3, this.lengthZ(), this.mirror), mirror(var4, this.mirror));
   }

   public class_2680 desiredState(int var1, int var2, int var3, class_2680 var4, List<class_2680> var5) {
      return mirror(this.schematic.desiredState(mirrorX(var1, this.widthX(), this.mirror), var2, mirrorZ(var3, this.lengthZ(), this.mirror), mirror(var4, this.mirror), mirror(var5, this.mirror)), this.mirror);
   }

   public void reset() {
      this.schematic.reset();
   }

   public int widthX() {
      return this.schematic.widthX();
   }

   public int heightY() {
      return this.schematic.heightY();
   }

   public int lengthZ() {
      return this.schematic.lengthZ();
   }

   private static int mirrorX(int var0, int var1, class_2415 var2) {
      switch (var2) {
         case field_11302:
         case field_11300:
            return var0;
         case field_11301:
            return var1 - var0 - 1;
         default:
            throw new IllegalArgumentException("Unknown mirror");
      }
   }

   private static int mirrorZ(int var0, int var1, class_2415 var2) {
      switch (var2) {
         case field_11302:
         case field_11301:
            return var0;
         case field_11300:
            return var1 - var0 - 1;
         default:
            throw new IllegalArgumentException("Unknown mirror");
      }
   }

   private static class_2680 mirror(class_2680 var0, class_2415 var1) {
      return var0 == null ? null : var0.method_26185(var1);
   }

   private static List<class_2680> mirror(List<class_2680> var0, class_2415 var1) {
      return var0 == null ? null : (List)var0.stream().map((var1x) -> mirror(var1x, var1)).collect(Collectors.toList());
   }
}
