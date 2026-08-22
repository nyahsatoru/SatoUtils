package baritone.utils.schematic.format.defaults;

import baritone.api.schematic.CompositeSchematic;
import baritone.api.schematic.IStaticSchematic;
import baritone.utils.schematic.StaticSchematic;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2382;
import net.minecraft.class_2487;
import net.minecraft.class_2499;
import net.minecraft.class_2680;
import net.minecraft.class_2769;
import net.minecraft.class_2960;
import net.minecraft.class_6880;
import net.minecraft.class_7923;
import org.apache.commons.lang3.Validate;

public final class LitematicaSchematic extends CompositeSchematic implements IStaticSchematic {
   public LitematicaSchematic(class_2487 var1) {
      super(0, 0, 0);
      LitematicaSchematic var30 = this;
      class_2382 var3 = new class_2382(b(var1, "x"), b(var1, "y"), b(var1, "z"));
      class_2487[] var31;
      int var4 = (var31 = a(var1)).length;

      for(int var5 = 0; var5 < var4; ++var5) {
         class_2487 var6;
         class_2499 var7;
         class_2499 var9;
         class_2680[] var10 = new class_2680[(var9 = var7 = (var6 = var31[var5]).method_68569("BlockStatePalette")).size()];

         for(int var8 = 0; var8 < var9.size(); ++var8) {
            class_2487 var11;
            class_2960 var12;
            class_2248 var13 = (var12 = class_2960.method_12829((String)(var11 = (class_2487)var9.method_10534(var8)).method_10558("Name").orElse(""))) == null ? class_2246.field_10124 : (class_2248)class_7923.field_41175.method_10223(var12).map(class_6880.class_6883::comp_349).orElse(class_2246.field_10124);
            class_2487 var14 = (class_2487)var11.method_10562("Properties").orElse(new class_2487());
            class_2487 var16 = var14;
            class_2248 var15 = var13;
            class_2680 var17 = var13.method_9564();

            for(String var19 : var14.method_10541()) {
               class_2769 var20 = var15.method_9595().method_11663(var19);
               String var21 = (String)var16.method_10558(var19).orElse((Object)null);
               if (var20 != null) {
                  Optional var25;
                  if (!(var25 = var20.method_11900(var21)).isPresent()) {
                     throw new IllegalArgumentException("Invalid value for property " + String.valueOf(var20));
                  }

                  var17 = (class_2680)var17.method_11657(var20, (Comparable)var25.get());
               }
            }

            var10[var8] = var17;
         }

         int var38 = var7.size();
         int var33 = (int)Math.max((double)2.0F, Math.ceil(Math.log((double)var38) / Math.log((double)2.0F)));
         class_2487 var40;
         long var43 = (long)Math.abs((Integer)(var40 = (class_2487)var6.method_10562("Size").orElse(new class_2487())).method_10550("x").orElse(0) * (Integer)var40.method_10550("y").orElse(0) * (Integer)var40.method_10550("z").orElse(0));
         long[] var39 = (long[])var6.method_10565("BlockStates").orElse(new long[0]);
         LitematicaBitArray var34 = new LitematicaBitArray(var33, var43, var39);
         LitematicaBitArray var44 = var34;
         class_2680[] var42 = var10;
         int var45 = a(var6, "x") - var3.method_10263();
         int var46 = a(var6, "y") - var3.method_10264();
         int var47 = a(var6, "z") - var3.method_10260();
         class_2487 var48;
         int var49 = Math.abs((Integer)(var48 = (class_2487)var6.method_10562("Size").orElse(new class_2487())).method_10550("x").orElse(0));
         int var50 = Math.abs((Integer)var48.method_10550("y").orElse(0));
         int var51 = Math.abs((Integer)var48.method_10550("z").orElse(0));
         class_2680[][][] var52 = new class_2680[var49][var51][var50];
         int var53 = 0;

         for(int var22 = 0; var22 < var50; ++var22) {
            for(int var23 = 0; var23 < var51; ++var23) {
               for(int var24 = 0; var24 < var49; ++var24) {
                  class_2680[] var10000 = var52[var24][var23];
                  long var26 = (long)var53;
                  Validate.inclusiveBetween(0L, var44.b - 1L, var26);
                  long var28;
                  int var32 = (int)((var28 = var26 * (long)var44.a) >> 6);
                  int var35 = (int)((var26 + 1L) * (long)var44.a - 1L >> 6);
                  int var37 = (int)(var28 & 63L);
                  int var10003;
                  if (var32 == var35) {
                     var10003 = (int)(var44.a[var32] >>> var37 & var44.a);
                  } else {
                     int var41 = 64 - var37;
                     var10003 = (int)((var44.a[var32] >>> var37 | var44.a[var35] << var41) & var44.a);
                  }

                  var10000[var22] = var42[var10003];
                  ++var53;
               }
            }
         }

         ((CompositeSchematic)var30).put(new StaticSchematic(var52), var45, var46, var47);
      }

   }

   private static class_2487[] a(class_2487 var0) {
      return (class_2487[])var0.method_10562("Regions").map(class_2487::method_68567).map((var0x) -> {
         Stream var10000 = var0x.stream().filter((var0) -> var0 instanceof class_2487);
         Objects.requireNonNull(class_2487.class);
         return (class_2487[])var10000.map(class_2487.class::cast).toArray((var0) -> new class_2487[var0]);
      }).orElse(new class_2487[0]);
   }

   private static int a(class_2487 var0, String var1) {
      int var2 = (Integer)var0.method_10562("Position").flatMap((var1x) -> var1x.method_10550(var1)).orElse(0);
      int var3 = (Integer)var0.method_10562("Size").flatMap((var1x) -> var1x.method_10550(var1)).orElse(0);
      return Math.min(var2, var2 + var3 + 1);
   }

   private static int b(class_2487 var0, String var1) {
      int var2 = Integer.MAX_VALUE;

      for(class_2487 var5 : var0 = a((class_2487)var0)) {
         var2 = Math.min(var2, a(var5, var1));
      }

      return var2;
   }

   public final class_2680 getDirect(int var1, int var2, int var3) {
      return ((CompositeSchematic)this).desiredState(var1, var2, var3, (class_2680)null, Collections.emptyList());
   }

   static class LitematicaBitArray {
      final long[] a;
      final int a;
      final long a;
      final long b;

      public LitematicaBitArray(int var1, long var2, @Nullable long[] var4) {
         Validate.inclusiveBetween(1L, 32L, (long)var1);
         this.b = var2;
         this.a = var1;
         this.a = (1L << var1) - 1L;
         if (var4 != null) {
            this.a = var4;
         } else {
            long var5 = var2 * (long)var1;
            var1 = 1;
            long var10001;
            if (64L == 0L) {
               var10001 = 0L;
            } else if (var5 == 0L) {
               var10001 = 64L;
            } else {
               if (var5 < 0L) {
                  var1 = -1;
               }

               long var7;
               var10001 = (var7 = var5 % (64L * (long)var1)) == 0L ? var5 : var5 + 64L * (long)var1 - var7;
            }

            this.a = new long[(int)(var10001 / 64L)];
         }
      }
   }
}
