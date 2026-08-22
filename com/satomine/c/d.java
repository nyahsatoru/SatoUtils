package com.satomine.c;

import java.util.UUID;
import net.minecraft.class_1542;
import net.minecraft.class_1792;
import net.minecraft.class_243;

public final class d {
   private final UUID d;
   private final class_1792 j;
   private final long i;
   private b g;
   private class_243 e;
   private int f;
   private long h;
   private int b;
   private _b c;

   public d(class_1542 entity, b policy, long tick) {
      this.c = d._b.d;
      this.d = entity.method_5667();
      this.j = entity.method_6983().method_7909();
      this.i = tick;
      this.b(entity, policy, tick);
   }

   public void b(class_1542 entity, b policy, long tick) {
      this.g = policy;
      this.e = new class_243(entity.method_23317(), entity.method_23318(), entity.method_23321());
      this.f = entity.method_6983().method_7947();
      this.h = tick;
      this.b = 0;
   }

   public UUID k() {
      return this.d;
   }

   public class_1792 h() {
      return this.j;
   }

   public b j() {
      return this.g;
   }

   public class_243 e() {
      return this.e;
   }

   public int f() {
      return this.f;
   }

   public long i() {
      return this.i;
   }

   public long b() {
      return this.h;
   }

   public int g() {
      return ++this.b;
   }

   public _b c() {
      return this.c;
   }

   public boolean d() {
      return this.c == d._b.d;
   }

   public void b(_b resolution) {
      this.c = resolution;
   }

   public static enum _b {
      d,
      e,
      c,
      f;

      // $FF: synthetic method
      private static _b[] b() {
         return new _b[]{d, e, c, f};
      }
   }
}
