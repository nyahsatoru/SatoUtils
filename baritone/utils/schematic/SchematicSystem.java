package baritone.utils.schematic;

import baritone.api.command.registry.Registry;
import baritone.api.schematic.ISchematicSystem;
import baritone.api.schematic.format.ISchematicFormat;
import baritone.utils.schematic.format.DefaultSchematicFormats;
import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public enum SchematicSystem implements ISchematicSystem {
   a;

   private final Registry<ISchematicFormat> a = new Registry<ISchematicFormat>();

   private SchematicSystem() {
      Stream var10000 = Arrays.stream(DefaultSchematicFormats.values());
      Registry var10001 = this.a;
      Objects.requireNonNull(var10001);
      var10000.forEach(var10001::register);
   }

   public final Registry<ISchematicFormat> getRegistry() {
      return this.a;
   }

   public final Optional<ISchematicFormat> getByFile(File var1) {
      return this.a.stream().filter((var1x) -> var1x.isFileType(var1)).findFirst();
   }

   public final List<String> getFileExtensions() {
      return this.a.stream().map(ISchematicFormat::getFileExtensions).flatMap(Collection::stream).toList();
   }
}
