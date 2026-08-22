package baritone.command.manager;

import baritone.Baritone;
import baritone.api.IBaritone;
import baritone.api.command.ICommand;
import baritone.api.command.argument.ICommandArgument;
import baritone.api.command.exception.CommandException;
import baritone.api.command.exception.CommandUnhandledException;
import baritone.api.command.exception.ICommandException;
import baritone.api.command.helpers.TabCompleteHelper;
import baritone.api.command.manager.ICommandManager;
import baritone.api.command.registry.Registry;
import baritone.command.argument.ArgConsumer;
import baritone.command.argument.CommandArguments;
import baritone.command.defaults.DefaultCommands;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Stream;
import net.minecraft.class_3545;

public class CommandManager implements ICommandManager {
   private final Registry<ICommand> a = new Registry<ICommand>();
   private final Baritone a;

   public CommandManager(Baritone var1) {
      this.a = var1;
      List var10000 = DefaultCommands.a(var1);
      Registry var10001 = this.a;
      Objects.requireNonNull(var10001);
      var10000.forEach(var10001::register);
   }

   public IBaritone getBaritone() {
      return this.a;
   }

   public Registry<ICommand> getRegistry() {
      return this.a;
   }

   public ICommand getCommand(String var1) {
      Iterator var2 = this.a.entries.iterator();

      while(var2.hasNext()) {
         ICommand var3;
         if ((var3 = (ICommand)var2.next()).getNames().contains(var1.toLowerCase(Locale.US))) {
            return var3;
         }
      }

      return null;
   }

   public boolean execute(String var1) {
      return this.execute(a(var1, false));
   }

   public boolean execute(class_3545<String, List<ICommandArgument>> var1) {
      ExecutionWrapper var5;
      if ((var5 = this.a(var1)) != null) {
         ExecutionWrapper var2 = var5;

         try {
            var2.a.execute(var2.a, var2.a);
         } catch (Throwable var4) {
            ((ICommandException)(var4 instanceof ICommandException ? (ICommandException)var4 : new CommandUnhandledException(var4))).handle(var5.a, var5.a.getArgs());
         }
      }

      return var5 != null;
   }

   public Stream<String> tabComplete(class_3545<String, List<ICommandArgument>> var1) {
      ExecutionWrapper var2;
      return (var2 = this.a(var1)) == null ? Stream.empty() : var2.a();
   }

   public Stream<String> tabComplete(String var1) {
      class_3545 var3;
      String var2 = (String)(var3 = a(var1, true)).method_15442();
      return ((List)var3.method_15441()).isEmpty() ? (new TabCompleteHelper()).addCommands(this.a.a).filterPrefix(var2).stream() : this.tabComplete(var3);
   }

   private ExecutionWrapper a(class_3545<String, List<ICommandArgument>> var1) {
      String var2 = (String)var1.method_15442();
      ArgConsumer var4 = new ArgConsumer(this, (List)var1.method_15441());
      ICommand var3;
      return (var3 = this.getCommand(var2)) == null ? null : new ExecutionWrapper(var3, var2, var4);
   }

   private static class_3545<String, List<ICommandArgument>> a(String var0, boolean var1) {
      String var2 = var0.split("\\s", 2)[0];
      List var3 = CommandArguments.a(var0.substring(var2.length()), var1);
      return new class_3545(var2, var3);
   }

   public static class_3545<String, List<ICommandArgument>> a(String var0) {
      return a(var0, false);
   }

   static final class ExecutionWrapper {
      ICommand a;
      String a;
      ArgConsumer a;

      ExecutionWrapper(ICommand var1, String var2, ArgConsumer var3) {
         this.a = var1;
         this.a = var2;
         this.a = var3;
      }

      final Stream<String> a() {
         try {
            return this.a.tabComplete(this.a, this.a);
         } catch (CommandException var1) {
         } catch (Throwable var2) {
            var2.printStackTrace();
         }

         return Stream.empty();
      }
   }
}
