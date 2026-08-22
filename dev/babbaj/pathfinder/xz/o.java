package dev.babbaj.pathfinder.xz;

import java.io.InputStream;

final class o extends n implements l {
   private int a;

   o(byte[] var1) {
      if (var1.length == 1 && (var1[0] & 255) <= 37) {
         this.a = 2 | var1[0] & 1;
         this.a <<= (var1[0] >>> 1) + 11;
      } else {
         throw new t("Unsupported LZMA2 properties");
      }
   }

   public final int a() {
      return p.a(this.a);
   }

   public final InputStream a(InputStream var1, a var2) {
      return new p(var1, this.a, (byte[])null, var2);
   }
}
