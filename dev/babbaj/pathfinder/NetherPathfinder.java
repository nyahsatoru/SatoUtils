package dev.babbaj.pathfinder;

import dev.babbaj.pathfinder.xz.x;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class NetherPathfinder {
   public static int CACHE_MISS_GENERATE = 0;
   public static int CACHE_MISS_AIR = 1;
   public static int CACHE_MISS_SOLID = 2;
   private static final boolean IS_LOADED;

   public static native long newContext(long var0);

   public static native void freeContext(long var0);

   public static native void insertChunkData(long var0, int var2, int var3, boolean[] var4);

   public static native long getOrCreateChunk(long var0, int var2, int var3);

   public static native long getChunkPointer(long var0, int var2, int var3);

   public static native boolean hasChunkFromJava(long var0, int var2, int var3);

   public static native void cullFarChunks(long var0, int var2, int var3, int var4);

   public static native PathSegment pathFind(long var0, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8, boolean var9, int var10, boolean var11);

   private static native void raytrace0(long var0, int var2, int var3, double[] var4, double[] var5, boolean[] var6, double[] var7);

   public static void raytrace(long var0, int var2, int var3, double[] var4, double[] var5, boolean[] var6, double[] var7) {
      if (var4.length >= var3 * 3 && var5.length >= var3 * 3 && var6.length >= var3 && (var7 == null || var7.length >= var3 * 3)) {
         raytrace0(var0, var2, var3, var4, var5, var6, var7);
      } else {
         throw new IllegalArgumentException("Bad array lengths idiot");
      }
   }

   private static native int isVisibleMulti0(long var0, int var2, int var3, double[] var4, double[] var5, boolean var6);

   public static int isVisibleMulti(long var0, int var2, int var3, double[] var4, double[] var5, boolean var6) {
      if (var4.length >= var3 * 3 && var5.length >= var3 * 3) {
         return isVisibleMulti0(var0, var2, var3, var4, var5, var6);
      } else {
         throw new IllegalArgumentException("Bad array lengths idiot");
      }
   }

   public static native boolean isVisible(long var0, int var2, double var3, double var5, double var7, double var9, double var11, double var13);

   public static native boolean cancel(long var0);

   static native long getX2Index();

   public static boolean isThisSystemSupported() {
      return IS_LOADED;
   }

   private static String getNativeLibName() {
      if (Integer.parseInt(System.getProperty("sun.arch.data.model")) != 64) {
         throw new UnsupportedOperationException("Unsupported architecture (64-bit required)");
      } else {
         String var0 = System.getProperty("os.name").toLowerCase();
         String var1;
         if (!(var1 = System.getProperty("os.arch").toLowerCase()).contains("arm") && !var1.contains("aarch64")) {
            if (!var1.equals("x86_64") && !var1.equals("amd64")) {
               throw new UnsupportedOperationException("Unsupported architecture: ".concat(String.valueOf(var1)));
            }

            var1 = "x86_64";
         } else {
            var1 = "aarch64";
         }

         if (var0.contains("linux")) {
            return "libnether_pathfinder-" + var1 + ".so";
         } else if (var0.contains("windows")) {
            return "nether_pathfinder-" + var1 + ".dll";
         } else if (var0.contains("mac")) {
            return "libnether_pathfinder-" + var1 + ".dylib";
         } else {
            throw new UnsupportedOperationException("Unsupported operating system: ".concat(String.valueOf(var0)));
         }
      }
   }

   private static byte[] getNativeLib(String var0) {
      InputStream var1 = NetherPathfinder.class.getClassLoader().getResourceAsStream("natives.zip.xz");
      Throwable var2 = null;

      try {
         x var3 = new x(var1);
         Throwable var4 = null;

         try {
            ZipInputStream var5 = new ZipInputStream(var3);
            Throwable var6 = null;

            try {
               ZipEntry var7;
               while((var7 = var5.getNextEntry()) != null) {
                  if (var7.getName().equals(var0)) {
                     ByteArrayOutputStream var57 = new ByteArrayOutputStream();
                     byte[] var58 = new byte[4096];

                     int var8;
                     while((var8 = var5.read(var58)) != -1) {
                        var57.write(var58, 0, var8);
                     }

                     var0 = var57.toByteArray();
                     return (byte[])var0;
                  }
               }

               throw new NullPointerException("Failed to find pathfinder library: ".concat(String.valueOf(var0)));
            } catch (Throwable var51) {
               var6 = var51;
               throw var51;
            } finally {
               if (var6 != null) {
                  try {
                     var5.close();
                  } catch (Throwable var50) {
                     var6.addSuppressed(var50);
                  }
               } else {
                  var5.close();
               }

            }
         } catch (Throwable var53) {
            var4 = var53;
            throw var53;
         } finally {
            if (var4 != null) {
               try {
                  var3.close();
               } catch (Throwable var49) {
                  var4.addSuppressed(var49);
               }
            } else {
               var3.close();
            }

         }
      } catch (Throwable var55) {
         var2 = var55;
         throw var55;
      } finally {
         if (var1 != null) {
            if (var2 != null) {
               try {
                  var1.close();
               } catch (Throwable var48) {
                  var2.addSuppressed(var48);
               }
            } else {
               var1.close();
            }
         }

      }
   }

   private static void tryLoadLibrary() {
      String var0;
      byte[] var1 = getNativeLib(var0 = getNativeLibName());
      String[] var7;
      Path var8 = Files.createTempFile((var7 = var0.split("\\."))[0], "." + var7[1]);
      System.out.println("[nether-pathfinder] Created temp file at " + var8.toAbsolutePath());

      try {
         Files.write(var8, var1, new OpenOption[0]);
         System.load(var8.toAbsolutePath().toString());
      } finally {
         try {
            Files.delete(var8);
         } catch (IOException var5) {
            System.err.println("[nether-pathfinder] Failed to delete temp file");
         }

         if (!var8.toFile().delete()) {
            var8.toFile().deleteOnExit();
         }

      }

   }

   static {
      boolean var0 = false;

      try {
         tryLoadLibrary();
         System.out.println("[nether-pathfinder] Loaded shared library");
         var0 = true;
      } catch (Throwable var2) {
         System.err.println("[nether-pathfinder] Failed to load shared library");
         var2.printStackTrace();
      }

      IS_LOADED = var0;
   }
}
