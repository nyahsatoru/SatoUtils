package baritone.event;

import baritone.Baritone;
import baritone.api.event.events.BlockChangeEvent;
import baritone.api.event.events.BlockInteractEvent;
import baritone.api.event.events.ChatEvent;
import baritone.api.event.events.ChunkEvent;
import baritone.api.event.events.PacketEvent;
import baritone.api.event.events.PathEvent;
import baritone.api.event.events.PlayerUpdateEvent;
import baritone.api.event.events.RenderEvent;
import baritone.api.event.events.RotationMoveEvent;
import baritone.api.event.events.SprintStateEvent;
import baritone.api.event.events.TabCompleteEvent;
import baritone.api.event.events.TickEvent;
import baritone.api.event.events.WorldEvent;
import baritone.api.event.events.type.EventState;
import baritone.api.event.listener.IEventBus;
import baritone.api.event.listener.IGameEventListener;
import baritone.api.utils.Helper;
import baritone.api.utils.Pair;
import baritone.cache.CachedChunk;
import baritone.cache.WorldProvider;
import baritone.utils.BlockStateInterface;
import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;
import net.minecraft.class_1923;
import net.minecraft.class_1937;
import net.minecraft.class_2806;
import net.minecraft.class_2818;
import net.minecraft.class_4970;

public final class GameEventHandler implements IEventBus, Helper {
   private final Baritone a;
   private final List<IGameEventListener> a = new CopyOnWriteArrayList();

   public GameEventHandler(Baritone var1) {
      this.a = var1;
   }

   public final void onTick(TickEvent var1) {
      label16: {
         if (var1.getType() == TickEvent.Type.IN) {
            try {
               this.a.a = new BlockStateInterface(this.a.getPlayerContext(), true);
               break label16;
            } catch (Exception var2) {
               var2.printStackTrace();
            }
         }

         this.a.a = null;
      }

      this.a.forEach((var1x) -> var1x.onTick(var1));
   }

   public final void onPostTick(TickEvent var1) {
      this.a.forEach((var1x) -> var1x.onPostTick(var1));
   }

   public final void onPlayerUpdate(PlayerUpdateEvent var1) {
      this.a.forEach((var1x) -> var1x.onPlayerUpdate(var1));
   }

   public final void onSendChatMessage(ChatEvent var1) {
      this.a.forEach((var1x) -> var1x.onSendChatMessage(var1));
   }

   public final void onPreTabComplete(TabCompleteEvent var1) {
      this.a.forEach((var1x) -> var1x.onPreTabComplete(var1));
   }

   public final void onChunkEvent(ChunkEvent var1) {
      EventState var2 = var1.getState();
      ChunkEvent.Type var3 = var1.getType();
      class_1937 var4 = this.a.getPlayerContext().world();
      boolean var5 = var2 == EventState.PRE && var3 == ChunkEvent.Type.UNLOAD && var4.method_8398().method_12121(var1.getX(), var1.getZ(), (class_2806)null, false) != null;
      if (var1.isPostPopulate() || var5) {
         this.a.a.ifWorldLoaded((var2x) -> {
            class_2818 var3 = var4.method_8497(var1.getX(), var1.getZ());
            var2x.getCachedWorld().queueForPacking(var3);
         });
      }

      this.a.forEach((var1x) -> var1x.onChunkEvent(var1));
   }

   public final void onBlockChange(BlockChangeEvent var1) {
      if ((Boolean)Baritone.a().repackOnAnyBlockChange.value) {
         Stream var10000 = var1.getBlocks().stream().map(Pair::second).map(class_4970.class_4971::method_26204);
         ImmutableSet var10001 = CachedChunk.a;
         Objects.requireNonNull(var10001);
         if (var10000.anyMatch(var10001::contains)) {
            this.a.a.ifWorldLoaded((var2) -> {
               class_1937 var3 = this.a.getPlayerContext().world();
               class_1923 var4 = var1.getChunkPos();
               var2.getCachedWorld().queueForPacking(var3.method_8497(var4.field_9181, var4.field_9180));
            });
         }
      }

      this.a.forEach((var1x) -> var1x.onBlockChange(var1));
   }

   public final void onRenderPass(RenderEvent var1) {
      this.a.forEach((var1x) -> var1x.onRenderPass(var1));
   }

   public final void onWorldEvent(WorldEvent var1) {
      WorldProvider var2 = this.a.a;
      if (var1.getState() == EventState.POST) {
         var2.a();
         if (var1.getWorld() != null) {
            var2.a(var1.getWorld());
         }
      }

      this.a.forEach((var1x) -> var1x.onWorldEvent(var1));
   }

   public final void onSendPacket(PacketEvent var1) {
      this.a.forEach((var1x) -> var1x.onSendPacket(var1));
   }

   public final void onReceivePacket(PacketEvent var1) {
      this.a.forEach((var1x) -> var1x.onReceivePacket(var1));
   }

   public final void onPlayerRotationMove(RotationMoveEvent var1) {
      this.a.forEach((var1x) -> var1x.onPlayerRotationMove(var1));
   }

   public final void onPlayerSprintState(SprintStateEvent var1) {
      this.a.forEach((var1x) -> var1x.onPlayerSprintState(var1));
   }

   public final void onBlockInteract(BlockInteractEvent var1) {
      this.a.forEach((var1x) -> var1x.onBlockInteract(var1));
   }

   public final void onPlayerDeath() {
      this.a.forEach(IGameEventListener::onPlayerDeath);
   }

   public final void onPathEvent(PathEvent var1) {
      this.a.forEach((var1x) -> var1x.onPathEvent(var1));
   }

   public final void registerEventListener(IGameEventListener var1) {
      this.a.add(var1);
   }
}
