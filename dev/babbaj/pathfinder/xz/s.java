package dev.babbaj.pathfinder.xz;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

public final class s extends InputStream {
   private InputStream a;
   private final a a;
   private final int a;
   private final ae a;
   private final aa a;
   private final boolean a;
   private d a;
   private final aj a;
   private boolean b;
   private IOException a;
   private final byte[] a;

   public s(InputStream var1, a var2) {
      byte[] var3 = new byte[12];
      (new DataInputStream(var1)).readFully(var3);
      this(var1, -1, true, var3, var2);
   }

   s(InputStream var1, int var2, boolean var3, byte[] var4, a var5) {
      this.a = null;
      this.a = new aj();
      this.b = false;
      this.a = null;
      this.a = new byte[1];
      this.a = var5;
      this.a = var1;
      this.a = var2;
      this.a = var3;
      this.a = ad.a(var4);
      this.a = aa.a(this.a.a);
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
                     try {
                        this.a = new d(this.a, this.a, this.a, this.a, this.a);
                     } catch (m var13) {
                        InputStream var15 = this.a;
                        aj var5 = this.a;
                        CRC32 var18;
                        (var18 = new CRC32()).update(0);
                        CheckedInputStream var16;
                        if (ad.a((InputStream)(var16 = new CheckedInputStream(var15, var18))) != var5.d) {
                           throw new f("XZ Block Header or the start of XZ Index is corrupt");
                        }

                        aj var20 = new aj();
                        long var6 = 0L;

                        while(true) {
                           if (var6 < var5.d) {
                              long var8 = ad.a((InputStream)var16);
                              long var10 = ad.a((InputStream)var16);

                              try {
                                 var20.a(var8, var10);
                              } catch (w var12) {
                                 throw new f("XZ Index is corrupt");
                              }

                              if (var20.a <= var5.a && var20.b <= var5.b && var20.c <= var5.c) {
                                 ++var6;
                                 continue;
                              }

                              throw new f("XZ Index is corrupt");
                           }

                           if (var20.a == var5.a && var20.b == var5.b && var20.c == var5.c && Arrays.equals(var20.a.a(), var5.a.a())) {
                              DataInputStream var23 = new DataInputStream(var16);

                              for(int var7 = (int)(4L - ((ai)var5).a() & 3L); var7 > 0; --var7) {
                                 if (var23.readUnsignedByte() != 0) {
                                    throw new f("XZ Index is corrupt");
                                 }
                              }

                              long var24 = var18.getValue();

                              for(int var9 = 0; var9 < 4; ++var9) {
                                 if ((var24 >>> (var9 << 3) & 255L) != (long)var23.readUnsignedByte()) {
                                    throw new f("XZ Index is corrupt");
                                 }
                              }

                              byte[] var17 = new byte[12];
                              (new DataInputStream(this.a)).readFully(var17);
                              ae var19 = ad.b(var17);
                              if (this.a.a == var19.a && this.a.b() == var19.a) {
                                 this.b = true;
                                 if (var4 > 0) {
                                    return var4;
                                 }

                                 return -1;
                              }

                              throw new f("XZ Stream Footer does not match Stream Header");
                           }

                           throw new f("XZ Index is corrupt");
                        }
                     }
                  }

                  int var21;
                  if ((var21 = this.a.read(var1, var2, var3)) > 0) {
                     var4 += var21;
                     var2 += var21;
                     var3 -= var21;
                  } else if (var21 == -1) {
                     d var22;
                     this.a.a((long)(var22 = this.a).a + var22.a.a + (long)var22.a.a, this.a.a);
                     this.a = null;
                  }
               }
            } catch (IOException var14) {
               this.a = var14;
               if (var4 == 0) {
                  throw var14;
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
      this.a(true);
   }

   public final void a(boolean var1) {
      if (this.a != null) {
         if (this.a != null) {
            this.a.close();
            this.a = null;
         }

         try {
            if (var1) {
               this.a.close();
            }
         } finally {
            this.a = null;
         }

      }
   }
}
