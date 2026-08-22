package dev.babbaj.pathfinder.xz;

import java.io.InputStream;

final class i extends h implements l {
   private final int a;

   i(byte[] var1) {
      if (var1.length != 1) {
         throw new t("Unsupported Delta filter properties");
      } else {
         this.a = (var1[0] & 255) + 1;
      }
   }

   public final int a() {
      return 1;
   }

   public final InputStream a(InputStream var1, a var2) {
      return new j(var1, this.a);
   }
}
