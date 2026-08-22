package baritone.api.utils;

import baritone.api.BaritoneAPI;
import java.util.Optional;
import net.minecraft.class_1297;
import net.minecraft.class_2338;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_259;
import net.minecraft.class_265;
import net.minecraft.class_3532;
import net.minecraft.class_3965;
import net.minecraft.class_4770;
import net.minecraft.class_746;
import net.minecraft.class_2350.class_2351;
import net.minecraft.class_239.class_240;

public final class RotationUtils {
   public static final double DEG_TO_RAD = (Math.PI / 180D);
   public static final float DEG_TO_RAD_F = ((float)Math.PI / 180F);
   public static final double RAD_TO_DEG = (180D / Math.PI);
   public static final float RAD_TO_DEG_F = 57.29578F;
   private static final class_243[] BLOCK_SIDE_MULTIPLIERS = new class_243[]{new class_243((double)0.5F, (double)0.0F, (double)0.5F), new class_243((double)0.5F, (double)1.0F, (double)0.5F), new class_243((double)0.5F, (double)0.5F, (double)0.0F), new class_243((double)0.5F, (double)0.5F, (double)1.0F), new class_243((double)0.0F, (double)0.5F, (double)0.5F), new class_243((double)1.0F, (double)0.5F, (double)0.5F)};

   private RotationUtils() {
   }

   public static Rotation calcRotationFromCoords(class_2338 var0, class_2338 var1) {
      return calcRotationFromVec3d(new class_243((double)var0.method_10263(), (double)var0.method_10264(), (double)var0.method_10260()), new class_243((double)var1.method_10263(), (double)var1.method_10264(), (double)var1.method_10260()));
   }

   public static Rotation wrapAnglesToRelative(Rotation var0, Rotation var1) {
      return var0.yawIsReallyClose(var1) ? new Rotation(var0.getYaw(), var1.getPitch()) : var1.subtract(var0).normalize().add(var0);
   }

   public static Rotation calcRotationFromVec3d(class_243 var0, class_243 var1, Rotation var2) {
      return wrapAnglesToRelative(var2, calcRotationFromVec3d(var0, var1));
   }

   private static Rotation calcRotationFromVec3d(class_243 var0, class_243 var1) {
      double[] var8;
      double var2 = class_3532.method_15349((var8 = new double[]{var0.field_1352 - var1.field_1352, var0.field_1351 - var1.field_1351, var0.field_1350 - var1.field_1350})[0], -var8[2]);
      double var4 = Math.sqrt(var8[0] * var8[0] + var8[2] * var8[2]);
      double var6 = class_3532.method_15349(var8[1], var4);
      return new Rotation((float)(var2 * (180D / Math.PI)), (float)(var6 * (180D / Math.PI)));
   }

   public static class_243 calcLookDirectionFromRotation(Rotation var0) {
      float var1 = class_3532.method_15362((double)(-var0.getYaw() * ((float)Math.PI / 180F) - (float)Math.PI));
      float var2 = class_3532.method_15374((double)(-var0.getYaw() * ((float)Math.PI / 180F) - (float)Math.PI));
      float var3 = -class_3532.method_15362((double)(-var0.getPitch() * ((float)Math.PI / 180F)));
      float var4 = class_3532.method_15374((double)(-var0.getPitch() * ((float)Math.PI / 180F)));
      return new class_243((double)(var2 * var3), (double)var4, (double)(var1 * var3));
   }

   @Deprecated
   public static class_243 calcVec3dFromRotation(Rotation var0) {
      return calcLookDirectionFromRotation(var0);
   }

   public static Optional<Rotation> reachable(IPlayerContext var0, class_2338 var1) {
      return reachable(var0, var1, false);
   }

   public static Optional<Rotation> reachable(IPlayerContext var0, class_2338 var1, boolean var2) {
      return reachable(var0, var1, var0.playerController().getBlockReachDistance(), var2);
   }

   public static Optional<Rotation> reachable(IPlayerContext var0, class_2338 var1, double var2) {
      return reachable(var0, var1, var2, false);
   }

   public static Optional<Rotation> reachable(IPlayerContext var0, class_2338 var1, double var2, boolean var4) {
      if ((Boolean)BaritoneAPI.getSettings().remainWithExistingLookDirection.value && var0.isLookingAt(var1)) {
         Rotation var5 = var0.playerRotations().add(new Rotation(0.0F, 1.0E-4F));
         if (!var4) {
            return Optional.of(var5);
         }

         class_239 var6;
         if ((var6 = RayTraceUtils.rayTraceTowards(var0.player(), var5, var2, true)) != null && var6.method_17783() == class_240.field_1332 && ((class_3965)var6).method_17777().equals(var1)) {
            return Optional.of(var5);
         }
      }

      Optional var16;
      if ((var16 = reachableCenter(var0, var1, var2, var4)).isPresent()) {
         return var16;
      } else {
         class_265 var19;
         if ((var19 = var0.world().method_8320(var1).method_26218(var0.world(), var1)).method_1110()) {
            var19 = class_259.method_1077();
         }

         class_243[] var7;
         for(class_243 var17 : var7 = BLOCK_SIDE_MULTIPLIERS) {
            double var10 = var19.method_1091(class_2351.field_11048) * var17.field_1352 + var19.method_1105(class_2351.field_11048) * ((double)1.0F - var17.field_1352);
            double var12 = var19.method_1091(class_2351.field_11052) * var17.field_1351 + var19.method_1105(class_2351.field_11052) * ((double)1.0F - var17.field_1351);
            double var14 = var19.method_1091(class_2351.field_11051) * var17.field_1350 + var19.method_1105(class_2351.field_11051) * ((double)1.0F - var17.field_1350);
            if ((var16 = reachableOffset(var0, var1, (new class_243((double)var1.method_10263(), (double)var1.method_10264(), (double)var1.method_10260())).method_1031(var10, var12, var14), var2, var4)).isPresent()) {
               return var16;
            }
         }

         return Optional.empty();
      }
   }

   public static Optional<Rotation> reachableOffset(IPlayerContext var0, class_2338 var1, class_243 var2, double var3, boolean var5) {
      Rotation var7 = calcRotationFromVec3d(var5 ? RayTraceUtils.inferSneakingEyePosition(var0.player()) : var0.player().method_5836(1.0F), var2, var0.playerRotations());
      Rotation var6 = BaritoneAPI.getProvider().getBaritoneForPlayer(var0.player()).getLookBehavior().getAimProcessor().peekRotation(var7);
      class_239 var8;
      if ((var8 = RayTraceUtils.rayTraceTowards(var0.player(), var6, var3, var5)) != null && var8.method_17783() == class_240.field_1332) {
         if (((class_3965)var8).method_17777().equals(var1)) {
            return Optional.of(var7);
         }

         if (var0.world().method_8320(var1).method_26204() instanceof class_4770 && ((class_3965)var8).method_17777().equals(var1.method_10074())) {
            return Optional.of(var7);
         }
      }

      return Optional.empty();
   }

   public static Optional<Rotation> reachableCenter(IPlayerContext var0, class_2338 var1, double var2, boolean var4) {
      return reachableOffset(var0, var1, VecUtils.calculateBlockCenter(var0.world(), var1), var2, var4);
   }

   @Deprecated
   public static Optional<Rotation> reachable(class_746 var0, class_2338 var1, double var2) {
      return reachable(var0, var1, var2, false);
   }

   @Deprecated
   public static Optional<Rotation> reachable(class_746 var0, class_2338 var1, double var2, boolean var4) {
      return reachable(BaritoneAPI.getProvider().getBaritoneForPlayer(var0).getPlayerContext(), var1, var2, var4);
   }

   @Deprecated
   public static Optional<Rotation> reachableOffset(class_1297 var0, class_2338 var1, class_243 var2, double var3, boolean var5) {
      Rotation var6 = calcRotationFromVec3d(var5 ? RayTraceUtils.inferSneakingEyePosition(var0) : var0.method_5836(1.0F), var2, new Rotation(var0.method_36454(), var0.method_36455()));
      class_239 var7;
      if ((var7 = RayTraceUtils.rayTraceTowards(var0, var6, var3, var5)) != null && var7.method_17783() == class_240.field_1332) {
         if (((class_3965)var7).method_17777().equals(var1)) {
            return Optional.of(var6);
         }

         if (var0.method_73183().method_8320(var1).method_26204() instanceof class_4770 && ((class_3965)var7).method_17777().equals(var1.method_10074())) {
            return Optional.of(var6);
         }
      }

      return Optional.empty();
   }

   @Deprecated
   public static Optional<Rotation> reachableCenter(class_1297 var0, class_2338 var1, double var2, boolean var4) {
      return reachableOffset(var0, var1, VecUtils.calculateBlockCenter(var0.method_73183(), var1), var2, var4);
   }
}
