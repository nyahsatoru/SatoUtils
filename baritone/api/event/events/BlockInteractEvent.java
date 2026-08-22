package baritone.api.event.events;

import net.minecraft.class_2338;

public final class BlockInteractEvent {
   private final class_2338 pos;
   private final Type type;

   public BlockInteractEvent(class_2338 var1, Type var2) {
      this.pos = var1;
      this.type = var2;
   }

   public final class_2338 getPos() {
      return this.pos;
   }

   public final Type getType() {
      return this.type;
   }

   public static enum Type {
      START_BREAK,
      USE;

      // $FF: synthetic method
      private static Type[] $values() {
         return new Type[]{START_BREAK, USE};
      }
   }
}
