package dev.babbaj.pathfinder.xz;

import java.io.InputStream;

final class g extends e {
   long a = 0L;

   public g(InputStream var1) {
      super(var1);
   }

   public final int read() {
      int var1;
      if ((var1 = this.in.read()) != -1 && this.a >= 0L) {
         ++this.a;
      }

      return var1;
   }

   public final int read(byte[] var1, int var2, int var3) {
      int var4;
      if ((var4 = this.in.read(var1, var2, var3)) > 0 && this.a >= 0L) {
         this.a += (long)var4;
      }

      return var4;
   }
}
