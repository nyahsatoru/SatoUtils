package baritone.command.defaults;

import baritone.Baritone;
import baritone.api.IBaritone;
import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class CommandAlias extends Command {
   private final String a;
   private String b;

   public CommandAlias(IBaritone var1, List<String> var2, String var3, String var4) {
      super(var1, (String[])var2.toArray(new String[0]));
      this.a = var3;
      this.b = var4;
   }

   public CommandAlias(Baritone var1, String var2, String var3, String var4) {
      super(var1, var2);
      this.a = var3;
      this.b = var4;
   }

   public void execute(String var1, IArgConsumer var2) {
      super.baritone.getCommandManager().execute(String.format("%s %s", this.b, var2.rawRest()));
   }

   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      return super.baritone.getCommandManager().tabComplete(String.format("%s %s", this.b, var2.rawRest()));
   }

   public String getShortDesc() {
      return this.a;
   }

   public List<String> getLongDesc() {
      return Collections.singletonList(String.format("This command is an alias, for: %s ...", this.b));
   }
}
