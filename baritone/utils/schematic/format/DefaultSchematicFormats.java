package baritone.utils.schematic.format;

import baritone.api.schematic.IStaticSchematic;
import baritone.api.schematic.format.ISchematicFormat;
import baritone.utils.schematic.format.defaults.LitematicaSchematic;
import baritone.utils.schematic.format.defaults.MCEditSchematic;
import baritone.utils.schematic.format.defaults.SpongeSchematic;
import java.io.File;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import net.minecraft.class_2487;
import net.minecraft.class_2505;
import net.minecraft.class_2507;
import org.apache.commons.io.FilenameUtils;

public enum DefaultSchematicFormats implements ISchematicFormat {
   a {
      public final IStaticSchematic parse(InputStream var1) {
         return new MCEditSchematic(class_2507.method_10629(var1, class_2505.method_53898()));
      }
   },
   a {
      public final IStaticSchematic parse(InputStream var1) {
         class_2487 var2;
         switch ((Integer)(var2 = class_2507.method_10629(var1, class_2505.method_53898())).method_10550("Version").orElse(-1)) {
            case 1:
            case 2:
               return new SpongeSchematic(var2);
            default:
               throw new UnsupportedOperationException("Unsupported Version of a Sponge Schematic");
         }
      }
   },
   a {
      public final IStaticSchematic parse(InputStream var1) {
         class_2487 var2;
         switch ((Integer)(var2 = class_2507.method_10629(var1, class_2505.method_53898())).method_10550("Version").orElse(-1)) {
            case 4:
            case 5:
               throw new UnsupportedOperationException("This litematic Version is too old.");
            case 6:
               throw new UnsupportedOperationException("This litematic Version is too old.");
            case 7:
               return new LitematicaSchematic(var2);
            default:
               throw new UnsupportedOperationException("Unsuported Version of a Litematica Schematic");
         }
      }
   };

   private final String a;

   DefaultSchematicFormats(String var3) {
      this.a = var3;
   }

   public boolean isFileType(File var1) {
      return this.a.equalsIgnoreCase(FilenameUtils.getExtension(var1.getAbsolutePath()));
   }

   public List<String> getFileExtensions() {
      return Collections.singletonList(this.a);
   }
}
