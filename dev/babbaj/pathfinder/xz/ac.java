package dev.babbaj.pathfinder.xz;

import java.security.MessageDigest;

public final class ac extends aa {
   private final MessageDigest a;

   public ac() {
      super.a = 32;
      super.a = "SHA-256";
      this.a = MessageDigest.getInstance("SHA-256");
   }

   public final void a(byte[] var1, int var2, int var3) {
      this.a.update(var1, var2, var3);
   }

   public final byte[] a() {
      byte[] var1 = this.a.digest();
      this.a.reset();
      return var1;
   }
}
