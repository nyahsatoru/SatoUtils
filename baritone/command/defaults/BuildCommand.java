package baritone.command.defaults;

import baritone.Baritone;
import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.command.datatypes.RelativeBlockPos;
import baritone.api.command.datatypes.RelativeFile;
import baritone.api.command.exception.CommandInvalidStateException;
import baritone.api.utils.BetterBlockPos;
import baritone.utils.schematic.SchematicSystem;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Stream;
import org.apache.commons.io.FilenameUtils;

public class BuildCommand extends Command {
   private final File a;

   public BuildCommand(Baritone var1) {
      super(var1, "build");
      this.a = new File(var1.getPlayerContext().minecraft().field_1697, "schematics");
   }

   public void execute(String var1, IArgConsumer var2) {
      File var3;
      File var4;
      if (FilenameUtils.getExtension((var3 = var4 = ((File)var2.getDatatypePost(RelativeFile.INSTANCE, this.a)).getAbsoluteFile()).getAbsolutePath()).isEmpty()) {
         String var10002 = var3.getAbsolutePath();
         var3 = new File(var10002 + "." + (String)Baritone.a().schematicFallbackExtension.value);
      }

      if (!var3.exists()) {
         if (var4.exists()) {
            throw new CommandInvalidStateException(String.format("Cannot load %s because I do not know which schematic format that is. Please rename the file to include the correct file extension.", var3));
         } else {
            throw new CommandInvalidStateException("Cannot find " + String.valueOf(var3));
         }
      } else if (!SchematicSystem.a.getByFile(var3).isPresent()) {
         StringJoiner var6 = new StringJoiner(", ");
         List var10000 = SchematicSystem.a.getFileExtensions();
         Objects.requireNonNull(var6);
         var10000.forEach(var6::add);
         throw new CommandInvalidStateException(String.format("Unsupported schematic format. Reckognized file extensions are: %s", var6));
      } else {
         BetterBlockPos var5 = super.ctx.playerFeet();
         if (var2.hasAny()) {
            var2.requireMax(3);
            var5 = (BetterBlockPos)var2.getDatatypePost(RelativeBlockPos.INSTANCE, var5);
         } else {
            var2.requireMax(0);
         }

         if (!super.baritone.getBuilderProcess().build(var3.getName(), (File)var3, var5)) {
            throw new CommandInvalidStateException("Couldn't load the schematic. Either your schematic is corrupt or this is a bug.");
         } else {
            this.logDirect(String.format("Successfully loaded schematic for building\nOrigin: %s", var5));
         }
      }
   }

   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      if (var2.hasExactlyOne()) {
         return RelativeFile.tabComplete(var2, this.a);
      } else if (var2.has(2)) {
         var2.get();
         return var2.tabCompleteDatatype(RelativeBlockPos.INSTANCE);
      } else {
         return Stream.empty();
      }
   }

   public String getShortDesc() {
      return "Build a schematic";
   }

   public List<String> getLongDesc() {
      return Arrays.asList("Build a schematic from a file.", "", "Usage:", "> build <filename> - Loads and builds '<filename>.schematic'", "> build <filename> <x> <y> <z> - Custom position");
   }
}
