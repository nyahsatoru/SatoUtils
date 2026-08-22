package baritone.pathing.calc.openset;

import baritone.pathing.calc.PathNode;
import java.util.Arrays;

public final class BinaryHeapOpenSet implements IOpenSet {
   public PathNode[] a;
   public int a;

   public BinaryHeapOpenSet() {
      this((byte)0);
   }

   private BinaryHeapOpenSet(byte var1) {
      this.a = 0;
      this.a = new PathNode[1024];
   }

   public final void a(PathNode var1) {
      if (this.a >= this.a.length - 1) {
         this.a = (PathNode[])Arrays.copyOf(this.a, this.a.length << 1);
      }

      ++this.a;
      var1.d = this.a;
      this.a[this.a] = var1;
      this.b(var1);
   }

   public final void b(PathNode var1) {
      int var2;
      int var3 = (var2 = var1.d) >>> 1;
      double var4 = var1.c;

      for(PathNode var6 = this.a[var3]; var2 > 1 && var6.c > var4; var6 = this.a[var3]) {
         this.a[var2] = var6;
         this.a[var3] = var1;
         var1.d = var3;
         var6.d = var2;
         var2 = var3;
         var3 >>>= 1;
      }

   }
}
