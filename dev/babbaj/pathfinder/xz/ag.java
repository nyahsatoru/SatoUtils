package dev.babbaj.pathfinder.xz;

public abstract class ag {
   public final int a;
   public final byte[] a = new byte[256];
   public int b = 0;

   ag(int var1) {
      if (var1 > 0 && var1 <= 256) {
         this.a = var1;
      } else {
         throw new IllegalArgumentException();
      }
   }
}
