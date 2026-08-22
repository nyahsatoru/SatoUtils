package dev.babbaj.pathfinder.xz;

public final class bd implements bc {
   private static final boolean[] a = new boolean[]{true, true, true, false, true, false, false, false};
   private static final int[] a = new int[]{0, 1, 2, 2, 3, 3, 3, 3};
   private final boolean a = false;
   private int a;
   private int b = 0;

   private static boolean a(byte var0) {
      int var1;
      return (var1 = var0 & 255) == 0 || var1 == 255;
   }

   public bd(int var1) {
      this.a = var1 + 5;
   }

   public final int a(byte[] var1, int var2, int var3) {
      int var4 = var2 - 1;
      var3 = var2 + var3 - 5;

      int var5;
      for(var5 = var2; var5 <= var3; ++var5) {
         if ((var1[var5] & 254) == 232) {
            label47: {
               if (((var4 = var5 - var4) & -4) != 0) {
                  this.b = 0;
               } else {
                  this.b = this.b << var4 - 1 & 7;
                  if (this.b != 0 && (!a[this.b] || a(var1[var5 + 4 - a[this.b]]))) {
                     var4 = var5;
                     break label47;
                  }
               }

               var4 = var5;
               if (a(var1[var5 + 4])) {
                  int var6 = var1[var5 + 1] & 255 | (var1[var5 + 2] & 255) << 8 | (var1[var5 + 3] & 255) << 16 | (var1[var5 + 4] & 255) << 24;

                  while(true) {
                     boolean var10000 = this.a;
                     var6 -= this.a + var5 - var2;
                     if (this.b == 0) {
                        break;
                     }

                     int var7 = a[this.b] << 3;
                     if (!a((byte)(var6 >>> 24 - var7))) {
                        break;
                     }

                     var6 ^= (1 << 32 - var7) - 1;
                  }

                  var1[var5 + 1] = (byte)var6;
                  var1[var5 + 2] = (byte)(var6 >>> 8);
                  var1[var5 + 3] = (byte)(var6 >>> 16);
                  var1[var5 + 4] = (byte)(~((var6 >>> 24 & 1) - 1));
                  var5 += 4;
                  continue;
               }
            }

            this.b = this.b << 1 | 1;
         }
      }

      var4 = var5 - var4;
      this.b = (var4 & -4) != 0 ? 0 : this.b << var4 - 1;
      var5 -= var2;
      this.a += var5;
      return var5;
   }
}
