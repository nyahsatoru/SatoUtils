package baritone.command.argparser;

import baritone.api.command.argparser.IArgParser;
import baritone.api.command.argparser.IArgParserManager;
import baritone.api.command.argument.ICommandArgument;
import baritone.api.command.exception.CommandInvalidTypeException;
import baritone.api.command.exception.CommandNoParserForTypeException;
import baritone.api.command.registry.Registry;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public enum ArgParserManager implements IArgParserManager {
   a;

   private Registry<IArgParser> a = new Registry<IArgParser>();

   private ArgParserManager() {
      List var10000 = DefaultArgParsers.a;
      Registry var10001 = this.a;
      Objects.requireNonNull(var10001);
      var10000.forEach(var10001::register);
   }

   public final <T> IArgParser.Stateless<T> getParserStateless(Class<T> var1) {
      Stream var10000 = this.a.descendingStream();
      Objects.requireNonNull(IArgParser.Stateless.class);
      var10000 = var10000.filter(IArgParser.Stateless.class::isInstance);
      Objects.requireNonNull(IArgParser.Stateless.class);
      return (IArgParser.Stateless)var10000.map(IArgParser.Stateless.class::cast).filter((var1x) -> var1x.getTarget().isAssignableFrom(var1)).findFirst().orElse((Object)null);
   }

   public final <T, S> IArgParser.Stated<T, S> getParserStated(Class<T> var1, Class<S> var2) {
      Stream var10000 = this.a.descendingStream();
      Objects.requireNonNull(IArgParser.Stated.class);
      var10000 = var10000.filter(IArgParser.Stated.class::isInstance);
      Objects.requireNonNull(IArgParser.Stated.class);
      var10000 = var10000.map(IArgParser.Stated.class::cast).filter((var1x) -> var1x.getTarget().isAssignableFrom(var1)).filter((var1x) -> var1x.getStateType().isAssignableFrom(var2));
      Objects.requireNonNull(IArgParser.Stated.class);
      return (IArgParser.Stated)var10000.map(IArgParser.Stated.class::cast).findFirst().orElse((Object)null);
   }

   public final <T> T parseStateless(Class<T> var1, ICommandArgument var2) {
      IArgParser.Stateless var3;
      if ((var3 = this.getParserStateless(var1)) == null) {
         throw new CommandNoParserForTypeException(var1);
      } else {
         try {
            return (T)var3.parseArg(var2);
         } catch (Exception var4) {
            throw new CommandInvalidTypeException(var2, var1.getSimpleName());
         }
      }
   }

   public final <T, S> T parseStated(Class<T> var1, Class<S> var2, ICommandArgument var3, S var4) {
      IArgParser.Stated var6;
      if ((var6 = this.getParserStated(var1, var2)) == null) {
         throw new CommandNoParserForTypeException(var1);
      } else {
         try {
            return (T)var6.parseArg(var3, var4);
         } catch (Exception var5) {
            throw new CommandInvalidTypeException(var3, var1.getSimpleName());
         }
      }
   }

   public final Registry<IArgParser> getRegistry() {
      return this.a;
   }
}
