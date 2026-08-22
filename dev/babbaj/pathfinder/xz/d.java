package dev.babbaj.pathfinder.xz;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

class d extends InputStream {
   private final DataInputStream a;
   final g a;
   private InputStream a;
   final aa a;
   private final boolean a;
   private long b = -1L;
   private long c = -1L;
   private long d;
   final int a;
   long a = 0L;
   private boolean b = false;
   private final byte[] a = new byte[1];
   // $FF: synthetic field
   private static boolean c = !d.class.desiredAssertionStatus();

   public d(InputStream var1, aa var2, boolean var3, int var4, a var5) {
      this.a = var2;
      this.a = (boolean)var3;
      this.a = new DataInputStream(var1);
      if ((var3 = this.a.readUnsignedByte()) == 0) {
         throw new m();
      } else {
         this.a = 4 * (var3 + 1);
         byte[] var6;
         (var6 = new byte[this.a])[0] = (byte)var3;
         this.a.readFully(var6, 1, this.a - 1);
         if (!ad.a(var6, 0, this.a - 4, this.a - 4)) {
            throw new f("XZ Block Header is corrupt");
         } else if ((var6[1] & 60) != 0) {
            throw new t("Unsupported options in XZ Block Header");
         } else {
            long[] var7 = new long[var3 = (var6[1] & 3) + 1];
            byte[][] var8 = new byte[var3][];
            ByteArrayInputStream var9 = new ByteArrayInputStream(var6, 2, this.a - 6);

            try {
               this.d = 9223372036854775804L - (long)this.a - (long)var2.a;
               if ((var6[1] & 64) != 0) {
                  this.c = ad.a((InputStream)var9);
                  if (this.c == 0L || this.c > this.d) {
                     throw new f();
                  }

                  this.d = this.c;
               }

               if ((var6[1] & 128) != 0) {
                  this.b = ad.a((InputStream)var9);
               }

               for(int var18 = 0; var18 < var3; ++var18) {
                  var7[var18] = ad.a((InputStream)var9);
                  long var10;
                  if ((var10 = ad.a((InputStream)var9)) > (long)var9.available()) {
                     throw new f();
                  }

                  var8[var18] = new byte[(int)var10];
                  var9.read(var8[var18]);
               }
            } catch (IOException var12) {
               throw new f("XZ Block Header is corrupt");
            }

            for(int var19 = var9.available(); var19 > 0; --var19) {
               if (var9.read() != 0) {
                  throw new t("Unsupported options in XZ Block Header");
               }
            }

            if (-1L != -1L) {
               int var20;
               if ((long)(var20 = this.a + var2.a) >= -1L) {
                  throw new f("XZ Index does not match a Block Header");
               }

               long var23;
               if ((var23 = -1L - (long)var20) > this.d || this.c != -1L && this.c != var23) {
                  throw new f("XZ Index does not match a Block Header");
               }

               if (this.b != -1L && this.b != -1L) {
                  throw new f("XZ Index does not match a Block Header");
               }

               this.d = var23;
               this.c = var23;
               this.b = -1L;
            }

            l[] var21 = new l[var7.length];

            for(int var24 = 0; var24 < var21.length; ++var24) {
               if (var7[var24] == 33L) {
                  var21[var24] = new o(var8[var24]);
               } else if (var7[var24] == 3L) {
                  var21[var24] = new i(var8[var24]);
               } else {
                  if (!dev.babbaj.pathfinder.xz.c.a(var7[var24])) {
                     throw new t("Unknown Filter ID " + var7[var24]);
                  }

                  var21[var24] = new c(var7[var24], var8[var24]);
               }
            }

            l[] var13 = var21;

            for(int var16 = 0; var16 < var13.length - 1; ++var16) {
               if (!var13[var16].b()) {
                  throw new t("Unsupported XZ filter chain");
               }
            }

            if (!var13[var13.length - 1].c()) {
               throw new t("Unsupported XZ filter chain");
            } else {
               var3 = 0;

               for(int var22 = 0; var22 < var13.length; ++var22) {
                  if (var13[var22].a()) {
                     ++var3;
                  }
               }

               if (var3 > 3) {
                  throw new t("Unsupported XZ filter chain");
               } else {
                  if (var4 >= 0) {
                     int var25 = 0;

                     for(int var11 = 0; var11 < var21.length; ++var11) {
                        var25 += var21[var11].a();
                     }

                     if (var25 > var4) {
                        throw new q(var25, var4);
                     }
                  }

                  this.a = new g(var1);
                  this.a = this.a;

                  for(int var26 = var21.length - 1; var26 >= 0; --var26) {
                     this.a = var21[var26].a(this.a, var5);
                  }

               }
            }
         }
      }
   }

   public int read() {
      return this.read(this.a, 0, 1) == -1 ? -1 : this.a[0] & 255;
   }

   public int read(byte[] var1, int var2, int var3) {
      if (this.b) {
         return -1;
      } else {
         int var4;
         if ((var4 = this.a.read(var1, var2, var3)) > 0) {
            if (this.a) {
               this.a.a(var1, var2, var4);
            }

            this.a += (long)var4;
            long var5;
            if ((var5 = this.a.a) < 0L || var5 > this.d || this.a < 0L || this.b != -1L && this.a > this.b) {
               throw new f();
            }

            if (var4 < var3 || this.a == this.b) {
               if (this.a.read() != -1) {
                  throw new f();
               }

               this.a();
               this.b = true;
            }
         } else if (var4 == -1) {
            this.a();
            this.b = true;
         }

         return var4;
      }
   }

   private void a() {
      long var1 = this.a.a;
      if ((this.c == -1L || this.c == var1) && (this.b == -1L || this.b == this.a)) {
         while((var1++ & 3L) != 0L) {
            if (this.a.readUnsignedByte() != 0) {
               throw new f();
            }
         }

         byte[] var3 = new byte[this.a.a];
         this.a.readFully(var3);
         if (this.a && !Arrays.equals(this.a.a(), var3)) {
            throw new f("Integrity check (" + this.a.a + ") does not match");
         }
      } else {
         throw new f();
      }
   }

   public int available() {
      return this.a.available();
   }

   public void close() {
      try {
         this.a.close();
      } catch (IOException var1) {
         if (!c) {
            throw new AssertionError();
         }
      }

      this.a = null;
   }
}
