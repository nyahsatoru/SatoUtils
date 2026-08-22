package baritone.selection;

import baritone.Baritone;
import baritone.api.event.events.RenderEvent;
import baritone.api.event.listener.AbstractGameEventListener;
import baritone.api.selection.ISelection;
import baritone.utils.IRenderer;
import java.awt.Color;
import net.minecraft.class_238;
import net.minecraft.class_287;
import net.minecraft.class_4587;

public class SelectionRenderer implements AbstractGameEventListener, IRenderer {
   private final SelectionManager a;

   SelectionRenderer(Baritone var1, SelectionManager var2) {
      this.a = var2;
      var1.getGameEventHandler().registerEventListener(this);
   }

   public void onRenderPass(RenderEvent var1) {
      class_4587 var10000 = var1.getModelViewStack();
      ISelection[] var2 = this.a.getSelections();
      class_4587 var11 = var10000;
      float var3 = (Float)a.selectionOpacity.value;
      boolean var4 = (Boolean)a.renderSelectionIgnoreDepth.value;
      float var5 = (Float)a.selectionLineWidth.value;
      if ((Boolean)a.renderSelection.value && var2.length != 0) {
         class_287 var6 = IRenderer.a((Color)a.colorSelection.value, var3);

         for(ISelection var10 : var2) {
            IRenderer.a(var6, var11, var10.aabb(), 0.005, var5);
         }

         if ((Boolean)a.renderSelectionCorners.value) {
            IRenderer.a((Color)a.colorSelectionPos1.value, var3);

            for(ISelection var18 : var2) {
               IRenderer.a(var6, var11, new class_238(var18.pos1()), var5);
            }

            IRenderer.a((Color)a.colorSelectionPos2.value, var3);

            for(ISelection var19 : var2) {
               IRenderer.a(var6, var11, new class_238(var19.pos2()), var5);
            }
         }

         IRenderer.a(var6, var4);
      }
   }
}
