package dev.babbaj.pathfinder.xz;

public final class aw extends av {
   public final byte[] a = dev.babbaj.pathfinder.xz.a.a(65531);
   public int c;

   public aw() {
      this.c = this.a.length;
   }

   public final void a() {
      if ((super.a & -16777216) == 0) {
         try {
            super.b = super.b << 8 | this.a[this.c++] & 255;
            super.a <<= 8;
         } catch (ArrayIndexOutOfBoundsException var1) {
            throw new f();
         }
      }
   }
}
