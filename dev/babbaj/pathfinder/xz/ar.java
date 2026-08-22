package dev.babbaj.pathfinder.xz;

public final class ar extends an {
   public final as[] a;
   // $FF: synthetic field
   public final ap a;

   ar(ap var1, int var2, int var3) {
      super(var1, var2, var3);
      this.a = var1;
      this.a = new as[1 << var2 + var3];

      for(int var4 = 0; var4 < this.a.length; ++var4) {
         this.a[var4] = new as(this, (byte)0);
      }

   }

   final void a() {
      for(int var1 = 0; var1 < this.a.length; ++var1) {
         this.a[var1].a();
      }

   }
}
