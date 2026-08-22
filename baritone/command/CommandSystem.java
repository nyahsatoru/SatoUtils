package baritone.command;

import baritone.api.command.ICommandSystem;
import baritone.api.command.argparser.IArgParserManager;
import baritone.command.argparser.ArgParserManager;

public enum CommandSystem implements ICommandSystem {
   a;

   public final IArgParserManager getParserManager() {
      return ArgParserManager.a;
   }
}
