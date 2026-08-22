package baritone.api.command.datatypes;

import baritone.api.command.helpers.TabCompleteHelper;
import java.util.Locale;
import java.util.stream.Stream;
import net.minecraft.class_2350;
import net.minecraft.class_2350.class_2351;

public enum ForAxis implements IDatatypeFor<class_2350.class_2351> {
   INSTANCE;

   public final class_2350.class_2351 get(IDatatypeContext var1) {
      return class_2351.valueOf(var1.getConsumer().getString().toUpperCase(Locale.US));
   }

   public final Stream<String> tabComplete(IDatatypeContext var1) {
      return (new TabCompleteHelper()).append(Stream.of(class_2351.values()).map(class_2350.class_2351::method_10174).map(String::toLowerCase)).filterPrefix(var1.getConsumer().getString()).stream();
   }

   // $FF: synthetic method
   private static ForAxis[] $values() {
      return new ForAxis[]{INSTANCE};
   }
}
