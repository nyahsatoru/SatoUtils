package baritone.api.process;

import baritone.api.utils.BlockOptionalMeta;
import baritone.api.utils.BlockOptionalMetaLookup;
import java.util.stream.Stream;
import net.minecraft.class_2248;

public interface IMineProcess extends IBaritoneProcess {
   void mineByName(int var1, String... var2);

   void mine(int var1, BlockOptionalMetaLookup var2);

   default void mine(BlockOptionalMetaLookup var1) {
      this.mine(0, (BlockOptionalMetaLookup)var1);
   }

   default void mineByName(String... var1) {
      this.mineByName(0, var1);
   }

   default void mine(int var1, BlockOptionalMeta... var2) {
      this.mine(var1, new BlockOptionalMetaLookup(var2));
   }

   default void mine(BlockOptionalMeta... var1) {
      this.mine(0, (BlockOptionalMeta[])var1);
   }

   default void mine(int var1, class_2248... var2) {
      this.mine(var1, new BlockOptionalMetaLookup((BlockOptionalMeta[])Stream.of(var2).map(BlockOptionalMeta::new).toArray((var0) -> new BlockOptionalMeta[var0])));
   }

   default void mine(class_2248... var1) {
      this.mine(0, (class_2248[])var1);
   }

   default void cancel() {
      this.onLostControl();
   }
}
