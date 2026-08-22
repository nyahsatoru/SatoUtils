package baritone.command.argparser;

import baritone.api.command.argparser.IArgParser;
import baritone.api.command.argument.ICommandArgument;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class DefaultArgParsers {
   public static final List<IArgParser<?>> a;

   static {
      a = Arrays.asList(DefaultArgParsers.IntArgumentParser.a, DefaultArgParsers.LongArgumentParser.a, DefaultArgParsers.FloatArgumentParser.a, DefaultArgParsers.DoubleArgumentParser.a, DefaultArgParsers.BooleanArgumentParser.a);
   }

   public static class BooleanArgumentParser implements IArgParser.Stateless<Boolean> {
      public static final BooleanArgumentParser a = new BooleanArgumentParser();
      private static List<String> a = Arrays.asList("1", "true", "yes", "t", "y", "on", "enable");
      private static List<String> b = Arrays.asList("0", "false", "no", "f", "n", "off", "disable");

      public Class<Boolean> getTarget() {
         return Boolean.class;
      }

      // $FF: synthetic method
      public Object parseArg(ICommandArgument var1) {
         String var2 = var1.getValue();
         if (a.contains(var2.toLowerCase(Locale.US))) {
            return Boolean.TRUE;
         } else if (b.contains(var2.toLowerCase(Locale.US))) {
            return Boolean.FALSE;
         } else {
            throw new IllegalArgumentException("invalid boolean");
         }
      }
   }

   public static enum DoubleArgumentParser implements IArgParser.Stateless<Double> {
      a;

      public final Class<Double> getTarget() {
         return Double.class;
      }

      // $FF: synthetic method
      public final Object parseArg(ICommandArgument var1) {
         String var2;
         if (!(var2 = var1.getValue()).matches("^([+-]?(?:\\d+(?:\\.\\d*)?|\\.\\d+)|)$")) {
            throw new IllegalArgumentException("failed double format check");
         } else {
            return Double.parseDouble(var2);
         }
      }
   }

   public static enum FloatArgumentParser implements IArgParser.Stateless<Float> {
      a;

      public final Class<Float> getTarget() {
         return Float.class;
      }

      // $FF: synthetic method
      public final Object parseArg(ICommandArgument var1) {
         String var2;
         if (!(var2 = var1.getValue()).matches("^([+-]?(?:\\d+(?:\\.\\d*)?|\\.\\d+)|)$")) {
            throw new IllegalArgumentException("failed float format check");
         } else {
            return Float.parseFloat(var2);
         }
      }
   }

   public static enum IntArgumentParser implements IArgParser.Stateless<Integer> {
      a;

      public final Class<Integer> getTarget() {
         return Integer.class;
      }

      // $FF: synthetic method
      public final Object parseArg(ICommandArgument var1) {
         return Integer.parseInt(var1.getValue());
      }
   }

   public static enum LongArgumentParser implements IArgParser.Stateless<Long> {
      a;

      public final Class<Long> getTarget() {
         return Long.class;
      }

      // $FF: synthetic method
      public final Object parseArg(ICommandArgument var1) {
         return Long.parseLong(var1.getValue());
      }
   }
}
