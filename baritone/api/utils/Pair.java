package baritone.api.utils;

import java.util.Objects;

public final class Pair<A, B> {
   private final A a;
   private final B b;

   public Pair(A var1, B var2) {
      this.a = var1;
      this.b = var2;
   }

   public final A first() {
      return this.a;
   }

   public final B second() {
      return this.b;
   }

   public final boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && var1.getClass() == Pair.class) {
         Pair var2 = (Pair)var1;
         return Objects.equals(this.a, var2.a) && Objects.equals(this.b, var2.b);
      } else {
         return false;
      }
   }

   public final int hashCode() {
      return 31 * Objects.hashCode(this.a) + Objects.hashCode(this.b);
   }
}
