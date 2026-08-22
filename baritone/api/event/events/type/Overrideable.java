package baritone.api.event.events.type;

public class Overrideable<T> {
   private T value;
   private boolean modified;

   public Overrideable(T var1) {
      this.value = var1;
   }

   public T get() {
      return this.value;
   }

   public void set(T var1) {
      this.value = var1;
      this.modified = true;
   }

   public boolean wasModified() {
      return this.modified;
   }

   public String toString() {
      return String.format("Overrideable{modified=%b,value=%s}", this.modified, this.value.toString());
   }
}
