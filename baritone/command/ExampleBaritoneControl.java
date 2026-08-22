package baritone.command;

import baritone.Baritone;
import baritone.api.BaritoneAPI;
import baritone.api.Settings;
import baritone.api.command.IBaritoneChatControl;
import baritone.api.command.ICommand;
import baritone.api.command.exception.CommandNotEnoughArgumentsException;
import baritone.api.command.exception.CommandNotFoundException;
import baritone.api.command.helpers.TabCompleteHelper;
import baritone.api.event.events.ChatEvent;
import baritone.api.event.events.TabCompleteEvent;
import baritone.api.event.events.type.Cancellable;
import baritone.api.utils.Helper;
import baritone.api.utils.SettingsUtil;
import baritone.behavior.Behavior;
import baritone.command.argument.ArgConsumer;
import baritone.command.argument.CommandArguments;
import baritone.command.manager.CommandManager;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;
import net.minecraft.class_124;
import net.minecraft.class_156;
import net.minecraft.class_2558;
import net.minecraft.class_2561;
import net.minecraft.class_2568;
import net.minecraft.class_3545;
import net.minecraft.class_5250;

public class ExampleBaritoneControl extends Behavior implements Helper {
   private static final Settings a = BaritoneAPI.getSettings();
   private final CommandManager a;

   public ExampleBaritoneControl(Baritone var1) {
      super(var1);
      this.a = var1.a;
   }

   public void onSendChatMessage(ChatEvent var1) {
      String var2 = var1.getMessage();
      String var3 = (String)a.prefix.value;
      boolean var4 = var2.startsWith(IBaritoneChatControl.FORCE_COMMAND_PREFIX);
      if ((!(Boolean)a.prefixControl.value || !var2.startsWith(var3)) && !var4) {
         if (((Boolean)a.chatControl.value || (Boolean)a.chatControlAnyway.value) && this.a(var2)) {
            ((Cancellable)var1).cancel();
         }

      } else {
         ((Cancellable)var1).cancel();
         String var5 = var2.substring(var4 ? IBaritoneChatControl.FORCE_COMMAND_PREFIX.length() : var3.length());
         if (!this.a(var5) && !var5.trim().isEmpty()) {
            (new CommandNotFoundException((String)CommandManager.a(var5).method_15442())).handle((ICommand)null, (List)null);
         }

      }
   }

   private void a(String var1, String var2) {
      if ((Boolean)a.echoCommands.value) {
         var2 = var1 + var2;
         var1 = (Boolean)a.censorRanCommands.value ? var1 + " ..." : var2;
         class_5250 var4;
         class_5250 var10000 = var4 = class_2561.method_43470(String.format("> %s", var1));
         var10000.method_10862(var10000.method_10866().method_10977(class_124.field_1068).method_10949(new class_2568.class_10613(class_2561.method_43470("Click to rerun command"))).method_10958(new class_2558.class_10609(IBaritoneChatControl.FORCE_COMMAND_PREFIX + var2)));
         this.logDirect(new class_2561[]{var4});
      }

   }

   private boolean a(String var1) {
      while(!var1.trim().equalsIgnoreCase("damn")) {
         if (var1.trim().equalsIgnoreCase("orderpizza")) {
            try {
               class_156.method_668().method_670("https://www.dominos.com/en/pages/order/");
            } catch (Exception var7) {
            }

            return false;
         }

         if (!var1.isEmpty()) {
            class_3545 var2;
            String var3 = (String)(var2 = CommandManager.a(var1)).method_15442();
            var1 = var1.substring(((String)var2.method_15442()).length());
            ArgConsumer var4;
            if (!(var4 = new ArgConsumer(this.a, (List)var2.method_15441())).hasAny()) {
               Settings.Setting var5;
               if ((var5 = (Settings.Setting)a.byLowerName.get(var3.toLowerCase(Locale.US))) != null) {
                  this.a(var3, var1);
                  if (var5.getValueClass() == Boolean.class) {
                     this.a.execute(String.format("set toggle %s", var5.getName()));
                  } else {
                     this.a.execute(String.format("set %s", var5.getName()));
                  }

                  return true;
               }
            } else if (var4.hasExactlyOne()) {
               Iterator var10 = a.allSettings.iterator();

               while(var10.hasNext()) {
                  Settings.Setting var6;
                  if (!(var6 = (Settings.Setting)var10.next()).isJavaOnly() && var6.getName().equalsIgnoreCase((String)var2.method_15442())) {
                     this.a(var3, var1);

                     try {
                        this.a.execute(String.format("set %s %s", var6.getName(), var4.getString()));
                     } catch (CommandNotEnoughArgumentsException var8) {
                     }

                     return true;
                  }
               }
            }

            if (this.a.getCommand((String)var2.method_15442()) != null) {
               this.a(var3, var1);
            }

            return this.a.execute(var2);
         }

         var1 = "help";
         this = this;
      }

      this.logDirect("daniel");
      return false;
   }

   public void onPreTabComplete(TabCompleteEvent var1) {
      if ((Boolean)a.prefixControl.value) {
         String var2 = var1.prefix;
         String var3 = (String)a.prefix.value;
         if (var2.startsWith(var3)) {
            String var5;
            List var4 = CommandArguments.a(var5 = var2.substring(var3.length()), true);
            Stream var6 = this.a(var5);
            if (var4.size() == 1) {
               var6 = var6.map((var1x) -> var3 + var1x);
            }

            var1.completions = (String[])var6.toArray((var0) -> new String[var0]);
         }
      }
   }

   private Stream<String> a(String var1) {
      try {
         List var2 = CommandArguments.a(var1, true);
         ArgConsumer var6;
         if ((var6 = new ArgConsumer(this.a, var2)).hasAtMost(2)) {
            if (var6.hasExactly(1)) {
               return (new TabCompleteHelper()).addCommands(this.a).addSettings().filterPrefix(var6.getString()).stream();
            }

            Settings.Setting var3;
            if ((var3 = (Settings.Setting)a.byLowerName.get(var6.getString().toLowerCase(Locale.US))) != null && !var3.isJavaOnly()) {
               if (var3.getValueClass() == Boolean.class) {
                  TabCompleteHelper var5 = new TabCompleteHelper();
                  if ((Boolean)var3.value) {
                     var5.append("true", "false");
                  } else {
                     var5.append("false", "true");
                  }

                  return var5.filterPrefix(var6.getString()).stream();
               }

               return Stream.of(SettingsUtil.settingValueToString(var3));
            }
         }

         return this.a.tabComplete(var1);
      } catch (CommandNotEnoughArgumentsException var4) {
         return Stream.empty();
      }
   }
}
