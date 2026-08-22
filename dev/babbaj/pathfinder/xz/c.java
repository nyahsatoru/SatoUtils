package dev.babbaj.pathfinder.xz;

import java.io.InputStream;

class c extends b implements l {
   private final long a;
   private final int a;
   // $FF: synthetic field
   private static boolean a = !c.class.desiredAssertionStatus();

   c(long var1, byte[] var3) {
      if (!a && !a(var1)) {
         throw new AssertionError();
      } else {
         this.a = var1;
         if (var3.length == 0) {
            this.a = 0;
         } else if (var3.length != 4) {
            throw new t("Unsupported BCJ filter properties");
         } else {
            int var4 = 0;

            for(int var2 = 0; var2 < 4; ++var2) {
               var4 |= (var3[var2] & 255) << (var2 << 3);
            }

            this.a = var4;
         }
      }
   }

   public final int a() {
      return r.a();
   }

   public final InputStream a(InputStream var1, a var2) {
      Object var3 = null;
      if (this.a == 4L) {
         var3 = new bd(this.a);
      } else if (this.a == 5L) {
         var3 = new ba(this.a);
      } else if (this.a == 6L) {
         var3 = new az(this.a);
      } else if (this.a == 7L) {
         var3 = new ax(this.a);
      } else if (this.a == 8L) {
         var3 = new ay(this.a);
      } else if (this.a == 9L) {
         var3 = new bb(this.a);
      } else if (!a) {
         throw new AssertionError();
      }

      return new r(var1, (bc)var3);
   }
}
