package baritone.api.event.events;

import baritone.api.event.events.type.EventState;
import java.util.function.BiFunction;

public final class TickEvent {
   private static int overallTickCount;
   private final EventState state;
   private final Type type;
   private final int count;

   public TickEvent(EventState var1, Type var2, int var3) {
      this.state = var1;
      this.type = var2;
      this.count = var3;
   }

   public final int getCount() {
      return this.count;
   }

   public final Type getType() {
      return this.type;
   }

   public final EventState getState() {
      return this.state;
   }

   public static synchronized BiFunction<EventState, Type, TickEvent> createNextProvider() {
      return (var1, var2) -> new TickEvent(var1, var2, var0);
   }

   public static enum Type {
      IN,
      OUT;

      // $FF: synthetic method
      private static Type[] $values() {
         return new Type[]{IN, OUT};
      }
   }
}
