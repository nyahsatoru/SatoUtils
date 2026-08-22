package dev.babbaj.pathfinder.xz;

public final class az implements bc {
   private static final int[] a = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 4, 6, 6, 0, 0, 7, 7, 4, 4, 0, 0, 4, 4, 0, 0};
   private final boolean a = false;
   private int a;

   public az(int var1) {
      this.a = var1;
   }

   public final int a(byte[] var1, int var2, int var3) {
      var3 = var2 + var3 - 16;

      int var4;
      for(var4 = var2; var4 <= var3; var4 += 16) {
         int var5 = var1[var4] & 31;
         var5 = a[var5];
         int var6 = 0;

         for(int var7 = 5; var6 < 3; var7 += 41) {
            if ((var5 >>> var6 & 1) != 0) {
               int var8 = var7 >>> 3;
               int var9 = var7 & 7;
               long var11 = 0L;

               for(int var13 = 0; var13 < 6; ++var13) {
                  var11 |= ((long)var1[var4 + var8 + var13] & 255L) << (var13 << 3);
               }

               long var21;
               if (((var21 = var11 >>> var9) >>> 37 & 15L) == 5L && (var21 >>> 9 & 7L) == 0L) {
                  int var10 = ((int)(var21 >>> 13 & 1048575L) | ((int)(var21 >>> 36) & 1) << 20) << 4;
                  boolean var10000 = this.a;
                  var10 = var10 - (this.a + var4 - var2) >>> 4;
                  var21 = var21 & -77309403137L | ((long)var10 & 1048575L) << 13 | ((long)var10 & 1048576L) << 16;
                  var11 = var11 & (long)((1 << var9) - 1) | var21 << var9;

                  for(int var18 = 0; var18 < 6; ++var18) {
                     var1[var4 + var8 + var18] = (byte)((int)(var11 >>> (var18 << 3)));
                  }
               }
            }

            ++var6;
         }
      }

      var4 -= var2;
      this.a += var4;
      return var4;
   }
}
