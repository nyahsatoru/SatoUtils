package dev.babbaj.pathfinder.xz;

public final class ay implements bc {
   private final boolean a = false;
   private int a;

   public ay(int var1) {
      this.a = var1 + 4;
   }

   public final int a(byte[] var1, int var2, int var3) {
      var3 = var2 + var3 - 4;

      int var4;
      for(var4 = var2; var4 <= var3; var4 += 2) {
         if ((var1[var4 + 1] & 248) == 240 && (var1[var4 + 3] & 248) == 248) {
            int var5 = ((var1[var4 + 1] & 7) << 19 | (var1[var4] & 255) << 11 | (var1[var4 + 3] & 7) << 8 | var1[var4 + 2] & 255) << 1;
            boolean var10000 = this.a;
            var5 = var5 - (this.a + var4 - var2) >>> 1;
            var1[var4 + 1] = (byte)(240 | var5 >>> 19 & 7);
            var1[var4] = (byte)(var5 >>> 11);
            var1[var4 + 3] = (byte)(248 | var5 >>> 8 & 7);
            var1[var4 + 2] = (byte)var5;
            var4 += 2;
         }
      }

      var4 -= var2;
      this.a += var4;
      return var4;
   }
}
