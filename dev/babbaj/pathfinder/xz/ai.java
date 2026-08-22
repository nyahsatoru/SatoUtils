package dev.babbaj.pathfinder.xz;

public abstract class ai {
   private final w a;
   public long a = 0L;
   public long b = 0L;
   public long c = 0L;
   public long d = 0L;

   ai(f var1) {
      this.a = var1;
   }

   public final long a() {
      return (long)(1 + af.a(this.d)) + this.c + 4L;
   }

   public long b() {
      return this.a() + 3L & -4L;
   }

   public long c() {
      return 12L + this.a + this.b() + 12L;
   }

   void a(long var1, long var3) {
      this.a += var1 + 3L & -4L;
      this.b += var3;
      this.c += (long)(af.a(var1) + af.a(var3));
      ++this.d;
      if (this.a < 0L || this.b < 0L || this.b() > 17179869184L || this.c() < 0L) {
         throw this.a;
      }
   }
}
