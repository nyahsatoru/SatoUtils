package dev.babbaj.pathfinder.xz;

public final class z extends aa {
   private static final long[][] a = new long[4][256];
   private long a = -1L;

   public z() {
      super.a = 8;
      super.a = "CRC64";
   }

   public final void a(byte[] var1, int var2, int var3) {
      for(int var4 = (var3 = var2 + var3) - 3; var2 < var4; var2 += 4) {
         int var5 = (int)this.a;
         this.a = a[3][var5 & 255 ^ var1[var2] & 255] ^ a[2][var5 >>> 8 & 255 ^ var1[var2 + 1] & 255] ^ this.a >>> 32 ^ a[1][var5 >>> 16 & 255 ^ var1[var2 + 2] & 255] ^ a[0][var5 >>> 24 ^ var1[var2 + 3] & 255];
      }

      while(var2 < var3) {
         this.a = a[0][var1[var2++] & 255 ^ (int)this.a & 255] ^ this.a >>> 8;
      }

   }

   public final byte[] a() {
      long var1 = ~this.a;
      this.a = -1L;
      byte[] var3 = new byte[8];

      for(int var4 = 0; var4 < var3.length; ++var4) {
         var3[var4] = (byte)((int)(var1 >> (var4 << 3)));
      }

      return var3;
   }

   static {
      for(int var0 = 0; var0 < 4; ++var0) {
         for(int var1 = 0; var1 < 256; ++var1) {
            long var2 = var0 == 0 ? (long)var1 : a[var0 - 1][var1];

            for(int var4 = 0; var4 < 8; ++var4) {
               if ((var2 & 1L) == 1L) {
                  var2 = var2 >>> 1 ^ -3932672073523589310L;
               } else {
                  var2 >>>= 1;
               }
            }

            a[var0][var1] = var2;
         }
      }

   }
}
