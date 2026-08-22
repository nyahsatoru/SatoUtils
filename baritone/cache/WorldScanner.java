package baritone.cache;

import baritone.api.cache.ICachedWorld;
import baritone.api.cache.IWorldScanner;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.BlockOptionalMetaLookup;
import baritone.api.utils.IPlayerContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import net.minecraft.class_1923;
import net.minecraft.class_2338;
import net.minecraft.class_2680;
import net.minecraft.class_2802;
import net.minecraft.class_2806;
import net.minecraft.class_2818;
import net.minecraft.class_2826;
import net.minecraft.class_2841;
import net.minecraft.class_631;

public enum WorldScanner implements IWorldScanner {
   a;

   public final List<class_2338> scanChunkRadius(IPlayerContext var1, BlockOptionalMetaLookup var2, int var3, int var4, int var5) {
      ArrayList var6 = new ArrayList();
      if (var2.blocks().isEmpty()) {
         return var6;
      } else {
         class_631 var7 = (class_631)var1.world().method_8398();
         var5 *= var5;
         int var8 = var1.playerFeet().method_10263() >> 4;
         int var9 = var1.playerFeet().method_10260() >> 4;
         int var10;
         int var11 = (var10 = var1.playerFeet().method_10264() - var1.world().method_8597().comp_651()) >> 4;
         int[] var22 = IntStream.range(0, var1.world().method_8597().comp_652() / 16).boxed().sorted(Comparator.comparingInt((var1x) -> Math.abs(var1x - var11))).mapToInt((var0) -> var0).toArray();
         int var12 = 0;
         boolean var13 = false;

         while(true) {
            boolean var14 = true;
            boolean var15 = false;

            for(int var16 = -var12; var16 <= var12; ++var16) {
               for(int var17 = -var12; var17 <= var12; ++var17) {
                  if (var16 * var16 + var17 * var17 == var12) {
                     var15 = true;
                     int var18 = var16 + var8;
                     int var19 = var17 + var9;
                     class_2818 var20;
                     if ((var20 = var7.method_2857(var18, var19, (class_2806)null, false)) != null) {
                        var14 = false;
                        if (a(var18 << 4, var19 << 4, var1.world().method_8597().comp_651(), var20, var2, var6, var3, var4, var10, var22)) {
                           var13 = true;
                        }
                     }
                  }
               }
            }

            if (var14 && var15 || var6.size() >= var3 && (var12 > var5 || var12 > 1 && var13)) {
               return var6;
            }

            ++var12;
         }
      }
   }

   public final List<class_2338> scanChunk(IPlayerContext var1, BlockOptionalMetaLookup var2, class_1923 var3, int var4, int var5) {
      if (var2.blocks().isEmpty()) {
         return Collections.emptyList();
      } else {
         class_2818 var6 = ((class_631)var1.world().method_8398()).method_2857(var3.field_9181, var3.field_9180, (class_2806)null, false);
         int var7 = var1.playerFeet().method_10264();
         if (var6 != null && !var6.method_12223()) {
            ArrayList var8 = new ArrayList();
            a(var3.field_9181 << 4, var3.field_9180 << 4, var1.world().method_8597().comp_651(), var6, var2, var8, var4, var5, var7, IntStream.range(0, var1.world().method_8597().comp_652() / 16).toArray());
            return var8;
         } else {
            return Collections.emptyList();
         }
      }
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

   private static boolean a(int var0, int var1, int var2, class_2818 var3, BlockOptionalMetaLookup var4, Collection<class_2338> var5, int var6, int var7, int var8, int[] var9) {
      class_2826[] var19 = var3.method_12006();
      boolean var10 = false;

      for(int var13 : var9) {
         class_2826 var14;
         if ((var14 = var19[var13]) != null && !var14.method_38292()) {
            var13 <<= 4;
            class_2841 var21 = var14.method_12265();

            for(int var15 = 0; var15 < 16; ++var15) {
               for(int var16 = 0; var16 < 16; ++var16) {
                  for(int var17 = 0; var17 < 16; ++var17) {
                     class_2680 var18 = (class_2680)var21.method_12321(var17, var15, var16);
                     if (var4.has(var18)) {
                        int var22 = var13 | var15;
                        if (var5.size() >= var6) {
                           if (Math.abs(var22 - var8) < var7) {
                              var10 = true;
                           } else if (var10) {
                              return true;
                           }
                        }

                        var5.add(new class_2338(var0 | var17, var22 + var2, var1 | var16));
                     }
                  }
               }
            }
         }
      }

      return var10;
   }
}
