package baritone.launch.mixins;

import baritone.utils.accessor.IRenderPipelines;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.class_10799;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({class_10799.class})
public class MixinRenderPipelines implements IRenderPipelines {
   @Final
   @Shadow
   private static RenderPipeline.Snippet field_56859;

   public RenderPipeline.Snippet getLinesSnippet() {
      return field_56859;
   }
}
