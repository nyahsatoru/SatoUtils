package dev.babbaj.pathfinder.xz;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class p extends InputStream {
   private final a a;
   private DataInputStream a;
   private ak a;
   private aw a;
   private ap a;
   private int a = 0;
   private boolean a = false;
   private boolean b = true;
   private boolean c = true;
   private boolean d = false;
   private IOException a = null;
   private final byte[] a = new byte[1];

   public static int a(int var0) {
      return 104 + b(var0) / 1024;
   }

   private static int b(int var0) {
      if (var0 >= 4096 && var0 <= 2147483632) {
         return var0 + 15 & -16;
      } else {
         throw new IllegalArgumentException("Unsupported dictionary size ".concat(String.valueOf(var0)));
      }
   }

   p(InputStream var1, int var2, byte[] var3, a var4) {
      if (var1 == null) {
         throw new NullPointerException();
      } else {
         this.a = var4;
         this.a = new DataInputStream(var1);
         this.a = new aw();
         this.a = new ak(b(var2), var3);
         if (var3 != null && var3.length > 0) {
            this.b = false;
         }

      }
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
         } else if (this.d) {
            return -1;
         } else {
            try {
               int var4 = 0;

               while(var3 > 0) {
                  if (this.a == 0) {
                     int var6;
                     if ((var6 = this.a.readUnsignedByte()) == 0) {
                        this.d = true;
                        this.a();
                     } else {
                        if (var6 < 224 && var6 != 1) {
                           if (this.b) {
                              throw new f();
                           }
                        } else {
                           this.c = true;
                           this.b = false;
                           ak var8;
                           (var8 = this.a).b = 0;
                           var8.c = 0;
                           var8.d = 0;
                           var8.e = 0;
                           var8.a[var8.a - 1] = 0;
                        }

                        if (var6 >= 128) {
                           this.a = true;
                           this.a = (var6 & 31) << 16;
                           this.a += this.a.readUnsignedShort() + 1;
                           int var7 = this.a.readUnsignedShort() + 1;
                           if (var6 >= 192) {
                              this.c = false;
                              if ((var6 = this.a.readUnsignedByte()) > 224) {
                                 throw new f();
                              }

                              int var9 = var6 / 45;
                              int var20;
                              int var10 = (var20 = var6 - var9 * 9 * 5) / 9;
                              if ((var6 = var20 - var10 * 9) + var10 > 4) {
                                 throw new f();
                              }

                              this.a = new ap(this.a, this.a, var6, var10, var9);
                           } else {
                              if (this.c) {
                                 throw new f();
                              }

                              if (var6 >= 160) {
                                 this.a.a();
                              }
                           }

                           DataInputStream var22 = this.a;
                           aw var39 = this.a;
                           if (var7 < 5) {
                              throw new f();
                           }

                           if (var22.readUnsignedByte() != 0) {
                              throw new f();
                           }

                           var39.b = var22.readInt();
                           var39.a = -1;
                           int var45 = var7 - 5;
                           var39.c = var39.a.length - var45;
                           var22.readFully(var39.a, var39.c, var45);
                        } else {
                           if (var6 > 2) {
                              throw new f();
                           }

                           this.a = false;
                           this.a = this.a.readUnsignedShort() + 1;
                        }
                     }

                     if (this.d) {
                        if (var4 == 0) {
                           return -1;
                        }

                        return var4;
                     }
                  }

                  int var5 = Math.min(this.a, var3);
                  if (!this.a) {
                     ak var65 = this.a;
                     DataInputStream var32 = this.a;
                     ak var16 = var65;
                     int var43 = Math.min(var65.a - var16.c, var5);
                     var32.readFully(var16.a, var16.c, var43);
                     var16.c += var43;
                     if (var16.d < var16.c) {
                        var16.d = var16.c;
                     }
                  } else {
                     ak var14;
                     if ((var14 = this.a).a - var14.c <= var5) {
                        var14.e = var14.a;
                     } else {
                        var14.e = var14.c + var5;
                     }

                     ap var15;
                     ak var40;
                     if ((var40 = (var15 = this.a).a).f > 0) {
                        var40.a(var40.g, var40.f);
                     }

                     while((var40 = var15.a).c < var40.e) {
                        int var24 = var15.a.c & var15.a;
                        if (var15.a.a(var15.a[var15.a.a], var24) == 0) {
                           ar var42;
                           ar var63 = var42 = var15.a;
                           int var10001 = var63.a.a.a(0);
                           var24 = var42.a.a.c;
                           int var51 = var10001;
                           ar var48 = var63;
                           int var35 = var51 >> 8 - var48.a;
                           int var53 = (var24 & var48.b) << var48.a;
                           var24 = var35 + var53;
                           as var49 = var42.a[var24];
                           var51 = 1;
                           if (var49.a.a.a.a < 7) {
                              while((var51 = var51 << 1 | var49.a.a.a.a(var49.a, var51)) < 256) {
                              }
                           } else {
                              var24 = var49.a.a.a.a(var49.a.a.a[0]);
                              var35 = 256;

                              do {
                                 var53 = (var24 <<= 1) & var35;
                                 int var55 = var49.a.a.a.a(var49.a, var35 + var53 + var51);
                                 var51 = var51 << 1 | var55;
                                 var35 &= 0 - var55 ^ ~var53;
                              } while(var51 < 256);
                           }

                           ak var64 = var49.a.a.a;
                           var35 = (byte)var51;
                           ak var30 = var64;
                           var64.a[var30.c++] = (byte)var35;
                           if (var30.d < var30.c) {
                              var30.d = var30.c;
                           }

                           at var31;
                           if ((var31 = var49.a.a.a).a <= 3) {
                              var31.a = 0;
                           } else if (var31.a <= 9) {
                              var31.a -= 3;
                           } else {
                              var31.a -= 6;
                           }
                        } else {
                           int var60;
                           if (var15.a.a(var15.a, var15.a.a) == 0) {
                              at var56 = var15.a;
                              var56.a = var56.a < 7 ? 7 : 10;
                              var15.a[3] = var15.a[2];
                              var15.a[2] = var15.a[1];
                              var15.a[1] = var15.a[0];
                              int var46 = var15.a.a(var24);
                              int var50;
                              if ((var50 = var15.a.a(var15.c[var46 < 6 ? var46 - 2 : 3])) < 4) {
                                 var15.a[0] = var50;
                              } else {
                                 var24 = (var50 >> 1) - 1;
                                 var15.a[0] = (2 | var50 & 1) << var24;
                                 if (var50 < 14) {
                                    int[] var59 = var15.a;
                                    var59[0] |= var15.a.b(var15.d[var50 - 4]);
                                 } else {
                                    int[] var57 = var15.a;
                                    int var10002 = var57[0];
                                    int var11 = var24 - 4;
                                    av var33 = var15.a;
                                    int var12 = 0;

                                    do {
                                       var33.a();
                                       var33.a >>>= 1;
                                       var24 = var33.b - var33.a >>> 31;
                                       var33.b -= var33.a & var24 - 1;
                                       var12 = var12 << 1 | 1 - var24;
                                       --var11;
                                    } while(var11 != 0);

                                    var57[0] = var10002 | var12 << 4;
                                    var57 = var15.a;
                                    var57[0] |= var15.a.b(var15.e);
                                 }
                              }

                              var60 = var46;
                           } else {
                              label276: {
                                 if (var15.a.a(var15.b, var15.a.a) == 0) {
                                    if (var15.a.a(var15.b[var15.a.a], var24) == 0) {
                                       at var61 = var15.a;
                                       var61.a = var61.a < 7 ? 9 : 11;
                                       var60 = 1;
                                       break label276;
                                    }
                                 } else {
                                    int var47;
                                    if (var15.a.a(var15.c, var15.a.a) == 0) {
                                       var47 = var15.a[1];
                                    } else {
                                       if (var15.a.a(var15.d, var15.a.a) == 0) {
                                          var47 = var15.a[2];
                                       } else {
                                          var47 = var15.a[3];
                                          var15.a[3] = var15.a[2];
                                       }

                                       var15.a[2] = var15.a[1];
                                    }

                                    var15.a[1] = var15.a[0];
                                    var15.a[0] = var47;
                                 }

                                 at var62 = var15.a;
                                 var62.a = var62.a < 7 ? 8 : 11;
                                 var60 = var15.b.a(var24);
                              }
                           }

                           int var34 = var60;
                           var15.a.a(var15.a[0], var34);
                        }
                     }

                     var15.a.a();
                  }

                  ak var17;
                  int var44 = (var17 = this.a).c - var17.b;
                  if (var17.c == var17.a) {
                     var17.c = 0;
                  }

                  System.arraycopy(var17.a, var17.b, var1, var2, var44);
                  var17.b = var17.c;
                  var2 += var44;
                  var3 -= var44;
                  var4 += var44;
                  this.a -= var44;
                  aw var18;
                  if (this.a == 0 && ((var18 = this.a).c != var18.a.length || var18.b != 0 || this.a.f > 0)) {
                     throw new f();
                  }
               }

               return var4;
            } catch (IOException var13) {
               this.a = var13;
               throw var13;
            }
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
         return this.a ? this.a : Math.min(this.a, this.a.available());
      }
   }

   private void a() {
      if (this.a != null) {
         ak var10000 = this.a;
         a var1 = this.a;
         this.a = null;
         aw var2 = this.a;
         a var3 = this.a;
         this.a = null;
      }

   }

   public final void close() {
      if (this.a != null) {
         this.a();

         try {
            this.a.close();
         } finally {
            this.a = null;
         }

      }
   }
}
