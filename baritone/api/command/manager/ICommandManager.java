package baritone.api.command.manager;

import baritone.api.IBaritone;
import baritone.api.command.ICommand;
import baritone.api.command.argument.ICommandArgument;
import baritone.api.command.registry.Registry;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.class_3545;

public interface ICommandManager {
   IBaritone getBaritone();

   Registry<ICommand> getRegistry();

   ICommand getCommand(String var1);

   boolean execute(String var1);

   boolean execute(class_3545<String, List<ICommandArgument>> var1);

   Stream<String> tabComplete(class_3545<String, List<ICommandArgument>> var1);

   Stream<String> tabComplete(String var1);
}
