package dev.babbaj.pathfinder.xz;

import java.util.Arrays;

abstract class am {
   final short[] a;
   final short[][] a;
   final short[][] b;
   final short[] b;
   // $FF: synthetic field
   private al a;

   am(ap var1) {
      this.a = var1;
      super();
      this.a = new short[2];
      this.a = new short[16][8];
      this.b = new short[16][8];
      this.b = new short[256];
   }

   final void a() {
      Arrays.fill(this.a, (short)1024);

      for(int var1 = 0; var1 < this.a.length; ++var1) {
         Arrays.fill(this.a[var1], (short)1024);
      }

      for(int var2 = 0; var2 < this.a.length; ++var2) {
         Arrays.fill(this.b[var2], (short)1024);
      }

      Arrays.fill(this.b, (short)1024);
   }
}
