package baritone.command.defaults;

import baritone.Baritone;
import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.command.exception.CommandInvalidStateException;
import baritone.api.pathing.goals.Goal;
import baritone.api.process.IBaritoneProcess;
import baritone.api.process.PathingCommand;
import baritone.api.process.PathingCommandType;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ExecutionControlCommands {
   <undefinedtype> a;
   <undefinedtype> a;
   <undefinedtype> a;
   <undefinedtype> a;

   public ExecutionControlCommands(final Baritone var1) {
      final boolean[] var2 = new boolean[]{false};
      var1.getPathingControlManager().registerProcess(new IBaritoneProcess() {
         public boolean isActive() {
            return var2[0];
         }

         public PathingCommand onTick(boolean var1x, boolean var2x) {
            var1.getInputOverrideHandler().clearAllKeys();
            return new PathingCommand((Goal)null, PathingCommandType.REQUEST_PAUSE);
         }

         public boolean isTemporary() {
            return true;
         }

         public void onLostControl() {
         }

         public double priority() {
            return (double)0.0F;
         }

         public String displayName0() {
            return "Pause/Resume Commands";
         }
      });
      this.a = new Command(var1, new String[]{"pause", "p", "paws"}) {
         public void execute(String var1, IArgConsumer var2x) {
            var2x.requireMax(0);
            if (var2[0]) {
               throw new CommandInvalidStateException("Already paused");
            } else {
               var2[0] = true;
               this.logDirect("Paused");
            }
         }

         public Stream<String> tabComplete(String var1, IArgConsumer var2x) {
            return Stream.empty();
         }

         public String getShortDesc() {
            return "Pauses Baritone until you use resume";
         }

         public List<String> getLongDesc() {
            return Arrays.asList("The pause command tells Baritone to temporarily stop whatever it's doing.", "", "This can be used to pause pathing, building, following, whatever. A single use of the resume command will start it right back up again!", "", "Usage:", "> pause");
         }
      };
      this.a = new Command(var1, new String[]{"resume", "r", "unpause", "unpaws"}) {
         public void execute(String var1, IArgConsumer var2x) {
            var2x.requireMax(0);
            super.baritone.getBuilderProcess().resume();
            if (!var2[0]) {
               throw new CommandInvalidStateException("Not paused");
            } else {
               var2[0] = false;
               this.logDirect("Resumed");
            }
         }

         public Stream<String> tabComplete(String var1, IArgConsumer var2x) {
            return Stream.empty();
         }

         public String getShortDesc() {
            return "Resumes Baritone after a pause";
         }

         public List<String> getLongDesc() {
            return Arrays.asList("The resume command tells Baritone to resume whatever it was doing when you last used pause.", "", "Usage:", "> resume");
         }
      };
      this.a = new Command(var1, new String[]{"paused"}) {
         public void execute(String var1, IArgConsumer var2x) {
            var2x.requireMax(0);
            this.logDirect(String.format("Baritone is %spaused", var2[0] ? "" : "not "));
         }

         public Stream<String> tabComplete(String var1, IArgConsumer var2x) {
            return Stream.empty();
         }

         public String getShortDesc() {
            return "Tells you if Baritone is paused";
         }

         public List<String> getLongDesc() {
            return Arrays.asList("The paused command tells you if Baritone is currently paused by use of the pause command.", "", "Usage:", "> paused");
         }
      };
      this.a = new Command(var1, new String[]{"cancel", "c", "stop"}) {
         public void execute(String var1, IArgConsumer var2x) {
            var2x.requireMax(0);
            if (var2[0]) {
               var2[0] = false;
            }

            super.baritone.getPathingBehavior().cancelEverything();
            this.logDirect("ok canceled");
         }

         public Stream<String> tabComplete(String var1, IArgConsumer var2x) {
            return Stream.empty();
         }

         public String getShortDesc() {
            return "Cancel what Baritone is currently doing";
         }

         public List<String> getLongDesc() {
            return Arrays.asList("The cancel command tells Baritone to stop whatever it's currently doing.", "", "Usage:", "> cancel");
         }
      };
   }
}
