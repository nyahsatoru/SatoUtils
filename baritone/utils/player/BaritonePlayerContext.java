package baritone.utils.player;

import baritone.Baritone;
import baritone.api.cache.IWorldData;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.IPlayerContext;
import baritone.api.utils.IPlayerController;
import baritone.api.utils.RayTraceUtils;
import baritone.api.utils.Rotation;
import baritone.behavior.LookBehavior;
import java.util.Optional;
import net.minecraft.class_1297;
import net.minecraft.class_1937;
import net.minecraft.class_239;
import net.minecraft.class_310;
import net.minecraft.class_746;

public final class BaritonePlayerContext implements IPlayerContext {
   private final Baritone a;
   private final class_310 a;
   private final BaritonePlayerController a;

   public BaritonePlayerContext(Baritone var1, class_310 var2) {
      this.a = var1;
      this.a = var2;
      this.a = new BaritonePlayerController(var2);
   }

   public final class_310 minecraft() {
      return this.a;
   }

   public final class_746 player() {
      return this.a.field_1724;
   }

   public final IPlayerController playerController() {
      return this.a;
   }

   public final class_1937 world() {
      return this.a.field_1687;
   }

   public final IWorldData worldData() {
      return this.a.a.a();
   }

   public final BetterBlockPos viewerPos() {
      class_1297 var1;
      return (var1 = this.a.method_1560()) == null ? this.playerFeet() : BetterBlockPos.from(var1.method_24515());
   }

   public final Rotation playerRotations() {
      LookBehavior var1 = this.a.a;
      return (Rotation)((Boolean)Baritone.a().freeLook.value ? Optional.ofNullable(var1.a) : Optional.empty()).orElseGet(() -> IPlayerContext.super.playerRotations());
   }

   public final class_239 objectMouseOver() {
      return RayTraceUtils.rayTraceTowards(this.player(), this.playerRotations(), this.playerController().getBlockReachDistance());
   }
}
