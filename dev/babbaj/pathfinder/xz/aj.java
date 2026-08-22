package dev.babbaj.pathfinder.xz;

import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;

public final class aj extends ai {
   public aa a;

   public aj() {
      super(new f());

      try {
         this.a = new ac();
      } catch (NoSuchAlgorithmException var1) {
         this.a = new y();
      }
   }

   public final void a(long var1, long var3) {
      super.a(var1, var3);
      ByteBuffer var5;
      (var5 = ByteBuffer.allocate(16)).putLong(var1);
      var5.putLong(var3);
      byte[] var2;
      this.a.a(var2 = var5.array(), 0, var2.length);
   }
}
