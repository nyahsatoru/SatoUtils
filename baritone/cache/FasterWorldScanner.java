package baritone.cache;

import baritone.api.cache.ICachedWorld;
import baritone.api.cache.IWorldScanner;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.BlockOptionalMeta;
import baritone.api.utils.BlockOptionalMetaLookup;
import baritone.api.utils.IPlayerContext;
import baritone.utils.accessor.IPalettedContainer;
import io.netty.buffer.Unpooled;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.class_1923;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2540;
import net.minecraft.class_2680;
import net.minecraft.class_2802;
import net.minecraft.class_2816;
import net.minecraft.class_2818;
import net.minecraft.class_2826;
import net.minecraft.class_2837;
import net.minecraft.class_2841;
import net.minecraft.class_6490;
import net.minecraft.class_6564;

public enum FasterWorldScanner implements IWorldScanner {
   a;

   private static final class_2680[] a = new class_2680[0];
   // $FF: synthetic field
   private static boolean a = !FasterWorldScanner.class.desiredAssertionStatus();

   public final List<class_2338> scanChunkRadius(IPlayerContext var1, BlockOptionalMetaLookup var2, int var3, int var4, int var5) {
      if (!a && var1.world() == null) {
         throw new AssertionError();
      } else if (var5 < 0) {
         throw new IllegalArgumentException("chunkRange must be >= 0");
      } else {
         int var10003 = var1.playerFeet().x >> 4;
         int var10004 = var1.playerFeet().z >> 4;
         var4 = var5;
         int var9 = var10004;
         int var8 = var10003;
         ArrayList var11;
         (var11 = new ArrayList()).add(new class_1923(var8, var9));

         for(int var6 = 1; var6 < var4; ++var6) {
            for(int var7 = 0; var7 <= var6; ++var7) {
               var11.add(new class_1923(var8 - var7, var9 - var6));
               if (var7 != 0) {
                  var11.add(new class_1923(var8 + var7, var9 - var6));
                  var11.add(new class_1923(var8 - var7, var9 + var6));
               }

               var11.add(new class_1923(var8 + var7, var9 + var6));
               if (var7 != var6) {
                  var11.add(new class_1923(var8 - var6, var9 - var7));
                  var11.add(new class_1923(var8 + var6, var9 - var7));
                  if (var7 != 0) {
                     var11.add(new class_1923(var8 - var6, var9 + var7));
                     var11.add(new class_1923(var8 + var6, var9 + var7));
                  }
               }
            }
         }

         return this.a(var1, var2, var11, var3);
      }
   }

   public final List<class_2338> scanChunk(IPlayerContext var1, BlockOptionalMetaLookup var2, class_1923 var3, int var4, int var5) {
      Stream var6 = a(var1, var2, var3);
      if (var4 >= 0) {
         var6 = var6.limit((long)var4);
      }

      return (List)var6.collect(Collectors.toList());
   }

   public final int repack(IPlayerContext var1) {
      return this.repack(var1, 40);
   }

   public final int repack(IPlayerContext var1, int var2) {
      class_2802 var3 = var1.world().method_8398();
      ICachedWorld var4 = var1.worldData().getCachedWorld();
      BetterBlockPos var10;
      int var5 = (var10 = var1.playerFeet()).method_10263() >> 4;
      int var11 = var10.method_10260() >> 4;
      int var6 = var5 - var2;
      int var7 = var11 - var2;
      var5 += var2;
      var11 += var2;

      for(var2 = 0; var6 <= var5; ++var6) {
         for(int var8 = var7; var8 <= var11; ++var8) {
            class_2818 var9;
            if ((var9 = var3.method_12126(var6, var8, false)) != null && !var9.method_12223()) {
               ++var2;
               var4.queueForPacking(var9);
            }
         }
      }

      return var2;
   }

   private List<class_2338> a(IPlayerContext var1, BlockOptionalMetaLookup var2, List<class_1923> var3, int var4) {
      if (!a && var1.world() == null) {
         throw new AssertionError();
      } else {
         try {
            Stream var6 = var3.parallelStream().flatMap((var3x) -> a(var1, var2, var3x));
            if (var4 >= 0) {
               var6 = var6.limit((long)var4);
            }

            return (List)var6.collect(Collectors.toList());
         } catch (Exception var5) {
            var5.printStackTrace();
            throw var5;
         }
      }
   }

   private static Stream<class_2338> a(IPlayerContext var0, BlockOptionalMetaLookup var1, class_1923 var2) {
      class_2802 var3;
      if (!(var3 = var0.world().method_8398()).method_12123(var2.field_9181, var2.field_9180)) {
         return Stream.empty();
      } else {
         long var4 = (long)var2.field_9181 << 4;
         long var6 = (long)var2.field_9180 << 4;
         int var8 = var0.playerFeet().y - var0.world().method_31607() >> 4;
         return a(var1, var3.method_12126(var2.field_9181, var2.field_9180, false), var4, var6, var8).stream();
      }
   }

   private static List<class_2338> a(BlockOptionalMetaLookup var0, class_2818 var1, long var2, long var4, int var6) {
      ArrayList var7 = new ArrayList();
      int var8 = var1.method_31607();
      class_2826[] var11;
      int var9 = (var11 = var1.method_12006()).length;

      for(int var10 = var6 - 1; var10 >= 0 || var6 < var9; --var10) {
         if (var6 < var9) {
            a(var0, var11[var6], var7, var2, var8 + (var6 << 4), var4);
         }

         if (var10 >= 0) {
            a(var0, var11[var10], var7, var2, var8 + (var10 << 4), var4);
         }

         ++var6;
      }

      return var7;
   }

   private static void a(BlockOptionalMetaLookup var0, class_2826 var1, List<class_2338> var2, long var3, int var5, long var6) {
      if (var1 != null && !var1.method_38292()) {
         class_2841 var8;
         if (((IPalettedContainer)(var8 = var1.method_12265())).getStorage() != null) {
            class_2837 var23;
            if ((var23 = ((IPalettedContainer)var8).getPalette()) instanceof class_6564) {
               if (var0.has((class_2680)var23.method_12288(0))) {
                  for(int var19 = 0; var19 < 16; ++var19) {
                     for(int var22 = 0; var22 < 16; ++var22) {
                        for(int var25 = 0; var25 < 16; ++var25) {
                           var2.add(new class_2338((int)var3 + var19, var5 + var22, (int)var6 + var25));
                        }
                     }
                  }
               }

            } else {
               boolean[] var18;
               if ((var18 = a(var0, var23)).length != 0) {
                  class_6490 var20;
                  long[] var24 = (var20 = ((IPalettedContainer)var1.method_12265()).getStorage()).method_15212();
                  int var9 = var20.method_15215();
                  int var21 = var20.method_34896();
                  long var14 = (1L << var21) - 1L;
                  int var10 = 0;

                  for(int var11 = 0; var10 < var24.length && var11 < var9; ++var10) {
                     long var16 = var24[var10];

                     for(int var12 = 0; var12 <= 64 - var21 && var11 < var9; ++var11) {
                        int var13 = (int)(var16 >> var12 & var14);
                        if (var18[var13]) {
                           var2.add(new class_2338((int)var3 + (var11 & 255 & 15), var5 + (var11 >> 8), (int)var6 + ((var11 & 255) >> 4)));
                        }

                        var12 += var21;
                     }
                  }

               }
            }
         }
      }
   }

   private static boolean[] a(BlockOptionalMetaLookup var0, class_2837<class_2680> var1) {
      boolean var2 = false;
      class_2680[] var7;
      if ((var7 = a(var1)) == a) {
         return a(var0);
      } else {
         int var3;
         boolean[] var4 = new boolean[var3 = var7.length];

         for(int var5 = 0; var5 < var3; ++var5) {
            class_2680 var6 = var7[var5];
            if (var0.has(var6)) {
               var4[var5] = true;
               var2 = true;
            } else {
               var4[var5] = false;
            }
         }

         if (!var2) {
            return new boolean[0];
         } else {
            return var4;
         }
      }
   }

   private static boolean[] a(BlockOptionalMetaLookup var0) {
      boolean[] var1 = new boolean[class_2248.field_10651.method_10204()];
      Iterator var4 = var0.blocks().iterator();

      while(var4.hasNext()) {
         for(class_2680 var3 : ((BlockOptionalMeta)var4.next()).getAllBlockStates()) {
            var1[class_2248.field_10651.method_10206(var3)] = true;
         }
      }

      return var1;
   }

   private static class_2680[] a(class_2837<class_2680> var0) {
      if (var0 instanceof class_2816) {
         return a;
      } else {
         class_2540 var1 = new class_2540(Unpooled.buffer());
         var0.method_12287(var1, class_2248.field_10651);
         int var5;
         class_2680[] var2 = new class_2680[var5 = var1.method_10816()];

         for(int var3 = 0; var3 < var5; ++var3) {
            class_2680 var4 = (class_2680)class_2248.field_10651.method_10200(var1.method_10816());
            if (!a && var4 == null) {
               throw new AssertionError();
            }

            var2[var3] = var4;
         }

         return var2;
      }
   }
}
