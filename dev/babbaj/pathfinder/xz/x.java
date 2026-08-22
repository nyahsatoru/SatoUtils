package dev.babbaj.pathfinder.xz;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class x extends InputStream {
   private final a a;
   private final int a;
   private InputStream a;
   private s a;
   private final boolean a;
   private boolean b;
   private IOException a;
   private final byte[] a;

   public x(InputStream var1) {
      this(var1, (byte)0);
   }

   private x(InputStream var1, byte var2) {
      this(var1, '\u0000');
   }

   private x(InputStream var1, char var2) {
      this(var1, dev.babbaj.pathfinder.xz.a.a());
   }

   private x(InputStream var1, a var2) {
      this.b = false;
      this.a = null;
      this.a = new byte[1];
      this.a = var2;
      this.a = var1;
      this.a = -1;
      this.a = true;
      this.a = new s(var1, var2);
   }

   public final int read() {
      return this.read(this.a, 0, 1) == -1 ? -1 : this.a[0] & 255;
   }

   public final int read(byte[] var1, int var2, int var3) {
      if (var2 >= 0 && var3 >= 0 && var2 + var3 >= 0 && var2 + var3 <= var1.length) {
         if (var3 == 0) {
            return 0;
         } else if (this.a == null) {
            throw new w("Stream closed");
         } else if (this.a != null) {
            throw this.a;
         } else if (this.b) {
            return -1;
         } else {
            int var4 = 0;

            try {
               while(var3 > 0) {
                  if (this.a == null) {
                     x var5 = this;
                     DataInputStream var6 = new DataInputStream(this.a);
                     byte[] var7 = new byte[12];

                     while(true) {
                        if (var6.read(var7, 0, 1) == -1) {
                           this.b = true;
                           break;
                        }

                        var6.readFully(var7, 1, 3);
                        if (var7[0] != 0 || var7[1] != 0 || var7[2] != 0 || var7[3] != 0) {
                           var6.readFully(var7, 4, 8);

                           try {
                              var5.a = new s(var5.a, var5.a, var5.a, var7, var5.a);
                              break;
                           } catch (v var8) {
                              throw new f("Garbage after a valid XZ Stream");
                           }
                        }
                     }

                     if (this.b) {
                        if (var4 == 0) {
                           return -1;
                        }

                        return var4;
                     }
                  }

                  int var10;
                  if ((var10 = this.a.read(var1, var2, var3)) > 0) {
                     var4 += var10;
                     var2 += var10;
                     var3 -= var10;
                  } else if (var10 == -1) {
                     this.a = null;
                  }
               }
            } catch (IOException var9) {
               this.a = var9;
               if (var4 == 0) {
                  throw var9;
               }
            }

            return var4;
         }
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public final int available() {
      if (this.a == null) {
         throw new w("Stream closed");
      } else if (this.a != null) {
         throw this.a;
      } else {
         return this.a == null ? 0 : this.a.available();
      }
   }

   public final void close() {
      x var1 = this;
      if (this.a != null) {
         if (this.a != null) {
            this.a.a(false);
            this.a = null;
         }

         try {
            var1.a.close();
         } finally {
            this.a = null;
         }

      }
   }
}
