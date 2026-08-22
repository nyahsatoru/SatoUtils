package dev.babbaj.pathfinder.xz;

import java.security.NoSuchAlgorithmException;

public abstract class aa {
   public int a;
   public String a;

   public abstract void a(byte[] var1, int var2, int var3);

   public abstract byte[] a();

   public static aa a(int var0) {
      switch (var0) {
         case 0:
            return new ab();
         case 1:
            return new y();
         case 4:
            return new z();
         case 10:
            try {
               return new ac();
            } catch (NoSuchAlgorithmException var1) {
            }
         default:
            throw new t("Unsupported Check ID ".concat(String.valueOf(var0)));
      }
   }
}
