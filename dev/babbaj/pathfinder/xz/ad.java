package dev.babbaj.pathfinder.xz;

import java.io.EOFException;
import java.io.InputStream;
import java.util.zip.CRC32;

public final class ad extends af {
   public static boolean a(byte[] var0, int var1, int var2, int var3) {
      CRC32 var4;
      (var4 = new CRC32()).update(var0, var1, var2);
      long var5 = var4.getValue();

      for(int var7 = 0; var7 < 4; ++var7) {
         if ((byte)((int)(var5 >>> (var7 << 3))) != var0[var3 + var7]) {
            return false;
         }
      }

      return true;
   }

   public static ae a(byte[] var0) {
      for(int var1 = 0; var1 < u.a.length; ++var1) {
         if (var0[var1] != u.a[var1]) {
            throw new v();
         }
      }

      if (!a(var0, u.a.length, 2, u.a.length + 2)) {
         throw new f("XZ Stream Header is corrupt");
      } else {
         try {
            return a(var0, u.a.length);
         } catch (t var2) {
            throw new t("Unsupported options in XZ Stream Header");
         }
      }
   }

   public static ae b(byte[] var0) {
      if (var0[10] == u.b[0] && var0[11] == u.b[1]) {
         if (!a(var0, 4, 6, 0)) {
            throw new f("XZ Stream Footer is corrupt");
         } else {
            ae var1;
            try {
               var1 = a(var0, 8);
            } catch (t var3) {
               throw new t("Unsupported options in XZ Stream Footer");
            }

            var1.a = 0L;

            for(int var2 = 0; var2 < 4; ++var2) {
               var1.a |= (long)((var0[var2 + 4] & 255) << (var2 << 3));
            }

            var1.a = var1.a + 1L << 2;
            return var1;
         }
      } else {
         throw new f("XZ Stream Footer is corrupt");
      }
   }

   private static ae a(byte[] var0, int var1) {
      if (var0[var1] == 0 && (var0[var1 + 1] & 255) < 16) {
         ae var2;
         (var2 = new ae()).a = var0[var1 + 1];
         return var2;
      } else {
         throw new t();
      }
   }

   public static long a(InputStream var0) {
      int var1;
      if ((var1 = var0.read()) == -1) {
         throw new EOFException();
      } else {
         long var2 = (long)(var1 & 127);

         for(int var4 = 0; (var1 & 128) != 0; var2 |= (long)(var1 & 127) << var4 * 7) {
            ++var4;
            if (var4 >= 9) {
               throw new f();
            }

            if ((var1 = var0.read()) == -1) {
               throw new EOFException();
            }

            if (var1 == 0) {
               throw new f();
            }
         }

         return var2;
      }
   }
}
