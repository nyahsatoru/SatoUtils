package baritone.launch.mixins;

import baritone.utils.accessor.IPlayerControllerMP;
import net.minecraft.class_2338;
import net.minecraft.class_636;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({class_636.class})
public abstract class MixinPlayerController implements IPlayerControllerMP {
   @Accessor("isDestroying")
   public abstract void setIsHittingBlock(boolean var1);

   @Accessor("isDestroying")
   public abstract boolean isHittingBlock();

   @Accessor("destroyBlockPos")
   public abstract class_2338 getCurrentBlock();

   @Invoker("ensureHasSentCarriedItem")
   public abstract void callSyncCurrentPlayItem();

   @Accessor("destroyDelay")
   public abstract void setDestroyDelay(int var1);
}
