package dev.babbaj.pathfinder.xz;

import java.io.IOException;
import java.io.InputStream;

class r extends InputStream {
   private InputStream a;
   private final bc a;
   private final byte[] a = new byte[4096];
   private int a = 0;
   private int b = 0;
   private int c = 0;
   private boolean a = false;
   private IOException a = null;
   private final byte[] b = new byte[1];
   // $FF: synthetic field
   private static boolean b = !r.class.desiredAssertionStatus();

   static int a() {
      return 5;
   }

   r(InputStream var1, bc var2) {
      if (var1 == null) {
         throw new NullPointerException();
      } else if (!b && var2 == null) {
         throw new AssertionError();
      } else {
         this.a = var1;
         this.a = var2;
      }
   }

   public int read() {
      return this.read(this.b, 0, 1) == -1 ? -1 : this.b[0] & 255;
   }

   public int read(byte[] var1, int var2, int var3) {
      if (var2 >= 0 && var3 >= 0 && var2 + var3 >= 0 && var2 + var3 <= var1.length) {
         if (var3 == 0) {
            return 0;
         } else if (this.a == null) {
            throw new w("Stream closed");
         } else if (this.a != null) {
            throw this.a;
         } else {
            try {
               int var4 = 0;

               while(true) {
                  int var5 = Math.min(this.b, var3);
                  System.arraycopy(this.a, this.a, var1, var2, var5);
                  this.a += var5;
                  this.b -= var5;
                  var2 += var5;
                  var3 -= var5;
                  var4 += var5;
                  if (this.a + this.b + this.c == 4096) {
                     System.arraycopy(this.a, this.a, this.a, 0, this.b + this.c);
                     this.a = 0;
                  }

                  if (var3 == 0 || this.a) {
                     return var4 > 0 ? var4 : -1;
                  }

                  if (!b && this.b != 0) {
                     throw new AssertionError();
                  }

                  var5 = 4096 - (this.a + this.b + this.c);
                  if ((var5 = this.a.read(this.a, this.a + this.b + this.c, var5)) == -1) {
                     this.a = true;
                     this.b = this.c;
                     this.c = 0;
                  } else {
                     this.c += var5;
                     this.b = this.a.a(this.a, this.a, this.c);
                     if (!b && this.b > this.c) {
                        throw new AssertionError();
                     }

                     this.c -= this.b;
                  }
               }
            } catch (IOException var6) {
               this.a = var6;
               throw var6;
            }
         }
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public int available() {
      if (this.a == null) {
         throw new w("Stream closed");
      } else if (this.a != null) {
         throw this.a;
      } else {
         return this.b;
      }
   }

   public void close() {
      if (this.a != null) {
         try {
            this.a.close();
         } finally {
            this.a = null;
         }

      }
   }
}
