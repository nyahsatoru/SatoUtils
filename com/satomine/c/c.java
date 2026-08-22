package com.satomine.c;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.class_1792;
import net.minecraft.class_243;

public final class c {
   private final List<d> d;
   private final class_243 b;
   private final long e;
   private final Map<class_1792, Integer> c;

   public c(List<d> candidates, class_243 miningOrigin, long startedTick, Map<class_1792, Integer> inventoryBefore) {
      this.d = List.copyOf(candidates);
      this.b = miningOrigin;
      this.e = startedTick;
      this.c = new HashMap(inventoryBefore);
   }

   public List<d> f() {
      return this.d;
   }

   public class_243 b() {
      return this.b;
   }

   public long e() {
      return this.e;
   }

   public int b(class_1792 item) {
      return (Integer)this.c.getOrDefault(item, 0);
   }

   public boolean c() {
      return this.d.stream().noneMatch(d::d);
   }

   public long d() {
      return this.d.stream().filter((candidate) -> candidate.c() == d._b.e).count();
   }
}
