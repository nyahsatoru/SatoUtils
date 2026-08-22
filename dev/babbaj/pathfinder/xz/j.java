package dev.babbaj.pathfinder.xz;

import java.io.IOException;
import java.io.InputStream;

public final class j extends InputStream {
   private InputStream a;
   private final ah a;
   private IOException a = null;
   private final byte[] a = new byte[1];

   public j(InputStream var1, int var2) {
      if (var1 == null) {
         throw new NullPointerException();
      } else {
         this.a = var1;
         this.a = new ah(var2);
      }
   }

   public final int read() {
      return this.read(this.a, 0, 1) == -1 ? -1 : this.a[0] & 255;
   }

   public final int read(byte[] var1, int var2, int var3) {
      if (var3 == 0) {
         return 0;
      } else if (this.a == null) {
         throw new w("Stream closed");
      } else if (this.a != null) {
         throw this.a;
      } else {
         try {
            var3 = this.a.read(var1, var2, var3);
         } catch (IOException var6) {
            this.a = var6;
            throw var6;
         }

         if (var3 == -1) {
            return -1;
         } else {
            int var4 = var2;
            byte[] var8 = var1;
            ah var7 = this.a;

            for(int var5 = var2 + var3; var4 < var5; ++var4) {
               var8[var4] += var7.a[var7.a + var7.b & 255];
               var7.a[var7.b-- & 255] = var8[var4];
            }

            return var3;
         }
      }
   }

   public final int available() {
      if (this.a == null) {
         throw new w("Stream closed");
      } else if (this.a != null) {
         throw this.a;
      } else {
         return this.a.available();
      }
   }

   public final void close() {
      if (this.a != null) {
         try {
            this.a.close();
         } finally {
            this.a = null;
         }

      }
   }
}
