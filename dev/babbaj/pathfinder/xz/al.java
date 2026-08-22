package dev.babbaj.pathfinder.xz;

import java.util.Arrays;

public abstract class al {
   public final int a;
   public final int[] a = new int[4];
   public final at a = new at();
   public final short[][] a = new short[12][16];
   public final short[] a = new short[12];
   public final short[] b = new short[12];
   public final short[] c = new short[12];
   public final short[] d = new short[12];
   public final short[][] b = new short[12][16];
   public final short[][] c = new short[4][64];
   public final short[][] d = new short[][]{new short[2], new short[2], new short[4], new short[4], new short[8], new short[8], new short[16], new short[16], new short[32], new short[32]};
   public final short[] e = new short[16];

   al(int var1) {
      this.a = (1 << var1) - 1;
   }

   void a() {
      this.a[0] = 0;
      this.a[1] = 0;
      this.a[2] = 0;
      this.a[3] = 0;
      this.a.a = 0;

      for(int var1 = 0; var1 < this.a.length; ++var1) {
         Arrays.fill(this.a[var1], (short)1024);
      }

      Arrays.fill(this.a, (short)1024);
      Arrays.fill(this.b, (short)1024);
      Arrays.fill(this.c, (short)1024);
      Arrays.fill(this.d, (short)1024);

      for(int var2 = 0; var2 < this.b.length; ++var2) {
         Arrays.fill(this.b[var2], (short)1024);
      }

      for(int var3 = 0; var3 < this.c.length; ++var3) {
         Arrays.fill(this.c[var3], (short)1024);
      }

      for(int var4 = 0; var4 < this.d.length; ++var4) {
         Arrays.fill(this.d[var4], (short)1024);
      }

      Arrays.fill(this.e, (short)1024);
   }
}
