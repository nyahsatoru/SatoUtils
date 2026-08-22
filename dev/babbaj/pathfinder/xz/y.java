package dev.babbaj.pathfinder.xz;

import java.util.zip.CRC32;

public final class y extends aa {
   private final CRC32 a = new CRC32();

   public y() {
      super.a = 4;
      super.a = "CRC32";
   }

   public final void a(byte[] var1, int var2, int var3) {
      this.a.update(var1, var2, var3);
   }

   public final byte[] a() {
      long var1 = this.a.getValue();
      byte[] var3 = new byte[]{(byte)((int)var1), (byte)((int)(var1 >>> 8)), (byte)((int)(var1 >>> 16)), (byte)((int)(var1 >>> 24))};
      this.a.reset();
      return var3;
   }
}
