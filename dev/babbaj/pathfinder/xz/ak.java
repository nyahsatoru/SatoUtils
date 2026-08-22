package dev.babbaj.pathfinder.xz;

public final class ak {
   public final byte[] a;
   public final int a;
   public int b = 0;
   public int c = 0;
   public int d = 0;
   public int e = 0;
   public int f = 0;
   public int g = 0;
   // $FF: synthetic field
   private static boolean a = !ak.class.desiredAssertionStatus();

   public ak(int var1, byte[] var2) {
      this.a = var1;
      this.a = dev.babbaj.pathfinder.xz.a.a(this.a);
      if (var2 != null) {
         this.c = Math.min(var2.length, var1);
         this.d = this.c;
         this.b = this.c;
         System.arraycopy(var2, var2.length - this.c, this.a, 0, this.c);
      }

   }

   public final int a(int var1) {
      int var2 = this.c - var1 - 1;
      if (var1 >= this.c) {
         var2 += this.a;
      }

      return this.a[var2] & 255;
   }

   public final void a(int var1, int var2) {
      if (var1 >= 0 && var1 < this.d) {
         int var3 = Math.min(this.e - this.c, var2);
         this.f = var2 - var3;
         this.g = var1;
         if ((var2 = this.c - var1 - 1) < 0) {
            if (!a && this.d != this.a) {
               throw new AssertionError();
            }

            var2 += this.a;
            int var4 = Math.min(this.a - var2, var3);
            if (!a && var4 > var1 + 1) {
               throw new AssertionError();
            }

            byte[] var10002 = this.a;
            System.arraycopy(var10002, var2, var10002, this.c, var4);
            this.c += var4;
            var2 = 0;
            if ((var3 -= var4) == 0) {
               return;
            }
         }

         if (!a && var2 >= this.c) {
            throw new AssertionError();
         } else if (!a && var3 <= 0) {
            throw new AssertionError();
         } else {
            int var7;
            do {
               var7 = Math.min(var3, this.c - var2);
               byte[] var8 = this.a;
               System.arraycopy(var8, var2, var8, this.c, var7);
               this.c += var7;
            } while((var3 -= var7) > 0);

            if (this.d < this.c) {
               this.d = this.c;
            }

         }
      } else {
         throw new f();
      }
   }
}
