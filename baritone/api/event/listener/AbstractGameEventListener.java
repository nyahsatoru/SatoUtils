package baritone.api.event.listener;

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

public interface AbstractGameEventListener extends IGameEventListener {
   default void onTick(TickEvent var1) {
   }

   default void onPostTick(TickEvent var1) {
   }

   default void onPlayerUpdate(PlayerUpdateEvent var1) {
   }

   default void onSendChatMessage(ChatEvent var1) {
   }

   default void onPreTabComplete(TabCompleteEvent var1) {
   }

   default void onChunkEvent(ChunkEvent var1) {
   }

   default void onBlockChange(BlockChangeEvent var1) {
   }

   default void onRenderPass(RenderEvent var1) {
   }

   default void onWorldEvent(WorldEvent var1) {
   }

   default void onSendPacket(PacketEvent var1) {
   }

   default void onReceivePacket(PacketEvent var1) {
   }

   default void onPlayerRotationMove(RotationMoveEvent var1) {
   }

   default void onPlayerSprintState(SprintStateEvent var1) {
   }

   default void onBlockInteract(BlockInteractEvent var1) {
   }

   default void onPlayerDeath() {
   }

   default void onPathEvent(PathEvent var1) {
   }
}
