package baritone.process.elytra;

import baritone.api.utils.BetterBlockPos;
import java.util.AbstractList;
import java.util.Collections;
import java.util.List;
import net.minecraft.class_243;

public final class NetherPath extends AbstractList<BetterBlockPos> {
   private static final NetherPath a = new NetherPath(Collections.emptyList());
   private final List<BetterBlockPos> a;

   NetherPath(List<BetterBlockPos> var1) {
      this.a = var1;
   }

   public final BetterBlockPos a(int var1) {
      return (BetterBlockPos)this.a.get(var1);
   }

   public final int size() {
      return this.a.size();
   }

   public final BetterBlockPos a() {
      return this.isEmpty() ? null : (BetterBlockPos)this.a.get(this.a.size() - 1);
   }

   public final class_243 a(int var1) {
      BetterBlockPos var2 = this.a(var1);
      return new class_243((double)var2.x, (double)var2.y, (double)var2.z);
   }

   public static NetherPath a() {
      return a;
   }

   // $FF: synthetic method
   public final Object get(int var1) {
      return this.a(var1);
   }

   // $FF: synthetic method
   public final Object getLast() {
      return this.a();
   }
}
