package baritone;

import baritone.api.IBaritone;
import baritone.api.IBaritoneProvider;
import baritone.api.cache.IWorldScanner;
import baritone.api.command.ICommandSystem;
import baritone.api.schematic.ISchematicSystem;
import baritone.cache.FasterWorldScanner;
import baritone.command.CommandSystem;
import baritone.command.ExampleBaritoneControl;
import baritone.utils.schematic.SchematicSystem;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.class_310;

public final class BaritoneProvider implements IBaritoneProvider {
   private final List<IBaritone> a = new CopyOnWriteArrayList();
   private final List<IBaritone> b;

   public BaritoneProvider() {
      this.b = Collections.unmodifiableList(this.a);
      ((Baritone)this.createBaritone(class_310.method_1551())).a(ExampleBaritoneControl::new);
   }

   public final IBaritone getPrimaryBaritone() {
      return (IBaritone)this.a.get(0);
   }

   public final List<IBaritone> getAllBaritones() {
      return this.b;
   }

   public final synchronized IBaritone createBaritone(class_310 var1) {
      Object var2;
      if ((var2 = this.getBaritoneForMinecraft(var1)) == null) {
         this.a.add(var2 = new Baritone(var1));
      }

      return (IBaritone)var2;
   }

   public final synchronized boolean destroyBaritone(IBaritone var1) {
      return var1 != this.getPrimaryBaritone() && this.a.remove(var1);
   }

   public final IWorldScanner getWorldScanner() {
      return FasterWorldScanner.a;
   }

   public final ICommandSystem getCommandSystem() {
      return CommandSystem.a;
   }

   public final ISchematicSystem getSchematicSystem() {
      return SchematicSystem.a;
   }
}
