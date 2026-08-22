package dev.babbaj.pathfinder.xz;

public final class ba implements bc {
   private final boolean a = false;
   private int a;

   public ba(int var1) {
      this.a = var1;
   }

   public final int a(byte[] var1, int var2, int var3) {
      var3 = var2 + var3 - 4;

      int var4;
      for(var4 = var2; var4 <= var3; var4 += 4) {
         if ((var1[var4] & 252) == 72 && (var1[var4 + 3] & 3) == 1) {
            int var5 = (var1[var4] & 3) << 24 | (var1[var4 + 1] & 255) << 16 | (var1[var4 + 2] & 255) << 8 | var1[var4 + 3] & 252;
            boolean var10000 = this.a;
            var5 -= this.a + var4 - var2;
            var1[var4] = (byte)(72 | var5 >>> 24 & 3);
            var1[var4 + 1] = (byte)(var5 >>> 16);
            var1[var4 + 2] = (byte)(var5 >>> 8);
            var1[var4 + 3] = (byte)(var1[var4 + 3] & 3 | var5);
         }
      }

      var4 -= var2;
      this.a += var4;
      return var4;
   }
}
