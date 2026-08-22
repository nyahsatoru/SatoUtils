package dev.babbaj.pathfinder.xz;

public final class ax implements bc {
   private final boolean a = false;
   private int a;

   public ax(int var1) {
      this.a = var1 + 8;
   }

   public final int a(byte[] var1, int var2, int var3) {
      var3 = var2 + var3 - 4;

      int var4;
      for(var4 = var2; var4 <= var3; var4 += 4) {
         if ((var1[var4 + 3] & 255) == 235) {
            int var5 = ((var1[var4 + 2] & 255) << 16 | (var1[var4 + 1] & 255) << 8 | var1[var4] & 255) << 2;
            boolean var10000 = this.a;
            var5 = var5 - (this.a + var4 - var2) >>> 2;
            var1[var4 + 2] = (byte)(var5 >>> 16);
            var1[var4 + 1] = (byte)(var5 >>> 8);
            var1[var4] = (byte)var5;
         }
      }

      var4 -= var2;
      this.a += var4;
      return var4;
   }
}
