package baritone.command.defaults;

import baritone.Baritone;
import baritone.api.command.Command;
import baritone.api.command.IBaritoneChatControl;
import baritone.api.command.ICommand;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.command.exception.CommandNotFoundException;
import baritone.api.command.helpers.Paginator;
import baritone.api.command.helpers.TabCompleteHelper;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.class_124;
import net.minecraft.class_2558;
import net.minecraft.class_2561;
import net.minecraft.class_2568;
import net.minecraft.class_5250;

public class HelpCommand extends Command {
   public HelpCommand(Baritone var1) {
      super(var1, "help", "?");
   }

   public void execute(String var1, IArgConsumer var2) {
      var2.requireMax(1);
      if (var2.hasAny() && !var2.is(Integer.class)) {
         String var4 = var2.getString().toLowerCase();
         ICommand var3;
         if ((var3 = super.baritone.getCommandManager().getCommand(var4)) == null) {
            throw new CommandNotFoundException(var4);
         } else {
            this.logDirect(String.format("%s - %s", String.join(" / ", var3.getNames()), var3.getShortDesc()));
            this.logDirect("");
            var3.getLongDesc().forEach(this::logDirect);
            this.logDirect("");
            class_5250 var5;
            class_5250 var10000 = var5 = class_2561.method_43470("Click to return to the help menu");
            var10000.method_10862(var10000.method_10866().method_10958(new class_2558.class_10609(IBaritoneChatControl.FORCE_COMMAND_PREFIX + var1)));
            this.logDirect(new class_2561[]{var5});
         }
      } else {
         Paginator.paginate(var2, new Paginator((List)super.baritone.getCommandManager().getRegistry().descendingStream().filter((var0) -> !var0.hiddenFromHelp()).collect(Collectors.toList())), () -> this.logDirect("All Baritone commands (clickable):"), (var1x) -> {
            String var2 = String.join("/", var1x.getNames());
            String var3 = (String)var1x.getNames().get(0);
            class_5250 var4;
            class_5250 var10000 = var4 = class_2561.method_43470(" - " + var1x.getShortDesc());
            var10000.method_10862(var10000.method_10866().method_10977(class_124.field_1063));
            class_5250 var8;
            var10000 = var8 = class_2561.method_43470(var2);
            var10000.method_10862(var10000.method_10866().method_10977(class_124.field_1068));
            class_5250 var5;
            var10000 = var5 = class_2561.method_43470("");
            var10000.method_10862(var10000.method_10866().method_10977(class_124.field_1080));
            var5.method_10852(var8);
            var5.method_27693("\n" + var1x.getShortDesc());
            var5.method_27693("\n\nClick to view full help");
            String var11 = IBaritoneChatControl.FORCE_COMMAND_PREFIX;
            var1 = var11 + String.format("%s %s", var1, var1x.getNames().get(0));
            class_5250 var7;
            class_5250 var12 = var7 = class_2561.method_43470(var3);
            var12.method_10862(var12.method_10866().method_10977(class_124.field_1080));
            var7.method_10852(var4);
            var7.method_10862(var7.method_10866().method_10949(new class_2568.class_10613(var5)).method_10958(new class_2558.class_10609(var1)));
            return var7;
         }, IBaritoneChatControl.FORCE_COMMAND_PREFIX + var1);
      }
   }

   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return var2.hasExactlyOne() ? (new TabCompleteHelper()).addCommands(super.baritone.getCommandManager()).filterPrefix(var2.getString()).stream() : Stream.empty();
   }

   public String getShortDesc() {
      return "View all commands or help on specific ones";
   }

   public List<String> getLongDesc() {
      return Arrays.asList("Using this command, you can view detailed help information on how to use certain commands of Baritone.", "", "Usage:", "> help - Lists all commands and their short descriptions.", "> help <command> - Displays help information on a specific command.");
   }
}
