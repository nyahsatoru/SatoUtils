package baritone.api.process;

public interface IBaritoneProcess {
   double DEFAULT_PRIORITY = (double)-1.0F;

   boolean isActive();

   PathingCommand onTick(boolean var1, boolean var2);

   boolean isTemporary();

   void onLostControl();

   default double priority() {
      return (double)-1.0F;
   }

   default String displayName() {
      return !this.isActive() ? "INACTIVE" : this.displayName0();
   }

   String displayName0();
}
