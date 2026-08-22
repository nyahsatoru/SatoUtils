package baritone.api.process;

public enum PathingCommandType {
   SET_GOAL_AND_PATH,
   REQUEST_PAUSE,
   CANCEL_AND_SET_GOAL,
   REVALIDATE_GOAL_AND_PATH,
   FORCE_REVALIDATE_GOAL_AND_PATH,
   DEFER,
   SET_GOAL_AND_PAUSE;

   // $FF: synthetic method
   private static PathingCommandType[] $values() {
      return new PathingCommandType[]{SET_GOAL_AND_PATH, REQUEST_PAUSE, CANCEL_AND_SET_GOAL, REVALIDATE_GOAL_AND_PATH, FORCE_REVALIDATE_GOAL_AND_PATH, DEFER, SET_GOAL_AND_PAUSE};
   }
}
