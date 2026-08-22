package baritone.api.pathing.movement;

public enum MovementStatus {
   PREPPING(false),
   WAITING(false),
   RUNNING(false),
   SUCCESS(true),
   UNREACHABLE(true),
   FAILED(true),
   CANCELED(true);

   private final boolean complete;

   private MovementStatus(boolean var3) {
      this.complete = var3;
   }

   public final boolean isComplete() {
      return this.complete;
   }

   // $FF: synthetic method
   private static MovementStatus[] $values() {
      return new MovementStatus[]{PREPPING, WAITING, RUNNING, SUCCESS, UNREACHABLE, FAILED, CANCELED};
   }
}
