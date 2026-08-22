package baritone.launch.mixins;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.command.IBaritoneChatControl;
import baritone.api.event.events.ChatEvent;
import baritone.utils.accessor.IGuiScreen;
import net.minecraft.class_2558;
import net.minecraft.class_310;
import net.minecraft.class_437;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({class_437.class})
public abstract class MixinScreen implements IGuiScreen {
   @Inject(
      method = {"defaultHandleGameClickEvent"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private static void handleCustomClickEvent(class_2558 var0, class_310 var1, class_437 var2, CallbackInfo var3) {
      if (var0 != null) {
         if (var0 instanceof class_2558.class_10609) {
            class_2558.class_10609 var10000 = (class_2558.class_10609)var0;

            try {
               var7 = var10000.comp_3506();
            } catch (Throwable var4) {
               throw new MatchException(var4.toString(), var4);
            }

            String var5 = var7;
            if (var5.startsWith(IBaritoneChatControl.FORCE_COMMAND_PREFIX)) {
               IBaritone var6;
               if ((var6 = BaritoneAPI.getProvider().getPrimaryBaritone()) != null) {
                  var6.getGameEventHandler().onSendChatMessage(new ChatEvent(var5));
               }

               var3.cancel();
            }
         }
      }
   }
}
