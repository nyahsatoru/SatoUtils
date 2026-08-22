package baritone.api.utils;

import baritone.api.pathing.calc.IPath;
import java.util.Objects;
import java.util.Optional;

public class PathCalculationResult {
   private final IPath path;
   private final Type type;

   public PathCalculationResult(Type var1) {
      this(var1, (IPath)null);
   }

   public PathCalculationResult(Type var1, IPath var2) {
      Objects.requireNonNull(var1);
      this.path = var2;
      this.type = var1;
   }

   public final Optional<IPath> getPath() {
      return Optional.ofNullable(this.path);
   }

   public final Type getType() {
      return this.type;
   }

   public static enum Type {
      SUCCESS_TO_GOAL,
      SUCCESS_SEGMENT,
      FAILURE,
      CANCELLATION,
      EXCEPTION;

      // $FF: synthetic method
      private static Type[] $values() {
         return new Type[]{SUCCESS_TO_GOAL, SUCCESS_SEGMENT, FAILURE, CANCELLATION, EXCEPTION};
      }
   }
}
