package baritone.api.utils.input;

public enum Input {
   MOVE_FORWARD,
   MOVE_BACK,
   MOVE_LEFT,
   MOVE_RIGHT,
   CLICK_LEFT,
   CLICK_RIGHT,
   JUMP,
   SNEAK,
   SPRINT;

   // $FF: synthetic method
   private static Input[] $values() {
      return new Input[]{MOVE_FORWARD, MOVE_BACK, MOVE_LEFT, MOVE_RIGHT, CLICK_LEFT, CLICK_RIGHT, JUMP, SNEAK, SPRINT};
   }
}
