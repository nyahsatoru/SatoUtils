package baritone.api.event.events;

import baritone.api.event.events.type.EventState;

public final class ChunkEvent {
   private final EventState state;
   private final Type type;
   private final int x;
   private final int z;

   public ChunkEvent(EventState var1, Type var2, int var3, int var4) {
      this.state = var1;
      this.type = var2;
      this.x = var3;
      this.z = var4;
   }

   public final EventState getState() {
      return this.state;
   }

   public final Type getType() {
      return this.type;
   }

   public final int getX() {
      return this.x;
   }

   public final int getZ() {
      return this.z;
   }

   public final boolean isPostPopulate() {
      return this.state == EventState.POST && this.type.isPopulate();
   }

   public static enum Type {
      LOAD,
      UNLOAD,
      POPULATE_FULL,
      POPULATE_PARTIAL;

      public final boolean isPopulate() {
         return this == POPULATE_FULL || this == POPULATE_PARTIAL;
      }

      // $FF: synthetic method
      private static Type[] $values() {
         return new Type[]{LOAD, UNLOAD, POPULATE_FULL, POPULATE_PARTIAL};
      }
   }
}
