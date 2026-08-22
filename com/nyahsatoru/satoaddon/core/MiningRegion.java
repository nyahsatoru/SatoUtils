package com.nyahsatoru.satoaddon.core;

import net.minecraft.class_2338;

public final class MiningRegion {
   private final class_2338 c;
   private final class_2338 b;

   public MiningRegion(class_2338 first, class_2338 second) {
      this.c = new class_2338(Math.min(first.method_10263(), second.method_10263()), Math.min(first.method_10264(), second.method_10264()), Math.min(first.method_10260(), second.method_10260()));
      this.b = new class_2338(Math.max(first.method_10263(), second.method_10263()), Math.max(first.method_10264(), second.method_10264()), Math.max(first.method_10260(), second.method_10260()));
   }

   public class_2338 getMin() {
      return this.c;
   }

   public class_2338 getMax() {
      return this.b;
   }

   public boolean contains(class_2338 pos) {
      return pos.method_10263() >= this.c.method_10263() && pos.method_10263() <= this.b.method_10263() && pos.method_10264() >= this.c.method_10264() && pos.method_10264() <= this.b.method_10264() && pos.method_10260() >= this.c.method_10260() && pos.method_10260() <= this.b.method_10260();
   }

   public long volume() {
      return ((long)this.b.method_10263() - (long)this.c.method_10263() + 1L) * ((long)this.b.method_10264() - (long)this.c.method_10264() + 1L) * ((long)this.b.method_10260() - (long)this.c.method_10260() + 1L);
   }

   public String describe() {
      String var10000 = this.c.method_23854();
      return var10000 + " -> " + this.b.method_23854();
   }
}
