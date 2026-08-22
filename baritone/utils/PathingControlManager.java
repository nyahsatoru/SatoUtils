package baritone.utils;

import baritone.Baritone;
import baritone.api.event.events.TickEvent;
import baritone.api.event.listener.AbstractGameEventListener;
import baritone.api.pathing.calc.IPathingControlManager;
import baritone.api.pathing.goals.Goal;
import baritone.api.process.IBaritoneProcess;
import baritone.api.process.PathingCommand;
import baritone.api.process.PathingCommandType;
import baritone.api.utils.BetterBlockPos;
import baritone.behavior.PathingBehavior;
import baritone.pathing.path.PathExecutor;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PathingControlManager implements IPathingControlManager {
   final Baritone a;
   private final HashSet<IBaritoneProcess> a;
   private final List<IBaritoneProcess> a;
   private IBaritoneProcess a;
   private IBaritoneProcess b;
   PathingCommand a;

   public PathingControlManager(Baritone var1) {
      this.a = var1;
      this.a = new HashSet();
      this.a = new ArrayList();
      var1.getGameEventHandler().registerEventListener(new AbstractGameEventListener() {
         public void onTick(TickEvent var1) {
            PathingControlManager var5;
            if (var1.getType() == TickEvent.Type.IN && (var5 = PathingControlManager.this).a != null) {
               PathingBehavior var2 = var5.a.a;
               switch (var5.a.commandType) {
                  case FORCE_REVALIDATE_GOAL_AND_PATH:
                     label34: {
                        if (var5.a.goal != null) {
                           Goal var4 = var5.a.goal;
                           PathExecutor var3;
                           if (!((var3 = var5.a.a.a) != null && !var4.isInGoal(var3.getPath().getDest()) ? !var4.equals(var3.getPath().getGoal()) : false) && !var5.a(var5.a.goal)) {
                              break label34;
                           }
                        }

                        var2.a();
                     }

                     var2.a(var5.a);
                     return;
                  case REVALIDATE_GOAL_AND_PATH:
                     if ((Boolean)Baritone.a().cancelOnGoalInvalidation.value && (var5.a.goal == null || var5.a(var5.a.goal))) {
                        var2.a();
                     }

                     var2.a(var5.a);
               }
            }

         }
      });
   }

   public void registerProcess(IBaritoneProcess var1) {
      var1.onLostControl();
      this.a.add(var1);
   }

   public final void a() {
      this.a = null;
      this.b = null;
      this.a = null;
      this.a.clear();
      Iterator var1 = this.a.iterator();

      while(var1.hasNext()) {
         IBaritoneProcess var2;
         (var2 = (IBaritoneProcess)var1.next()).onLostControl();
         if (var2.isActive() && !var2.isTemporary()) {
            throw new IllegalStateException(var2.displayName() + " stayed active after being cancelled");
         }
      }

   }

   public Optional<IBaritoneProcess> mostRecentInControl() {
      return Optional.ofNullable(this.b);
   }

   public Optional<PathingCommand> mostRecentCommand() {
      return Optional.ofNullable(this.a);
   }

   public final void b() {
      this.a = this.b;
      this.b = null;
      PathingBehavior var1 = this.a.a;
      PathingControlManager var2 = this;
      Iterator var3 = this.a.iterator();

      while(var3.hasNext()) {
         IBaritoneProcess var4;
         if ((var4 = (IBaritoneProcess)var3.next()).isActive()) {
            if (!var2.a.contains(var4)) {
               var2.a.add(0, var4);
            }
         } else {
            var2.a.remove(var4);
         }
      }

      var2.a.sort(Comparator.comparingDouble(IBaritoneProcess::priority).reversed());
      var3 = var2.a.iterator();

      PathingCommand var8;
      while(true) {
         if (!var3.hasNext()) {
            var8 = null;
            break;
         }

         IBaritoneProcess var7;
         IBaritoneProcess var10001 = var7 = (IBaritoneProcess)var3.next();
         PathingCommand var5;
         if ((var5 = var10001.onTick(Objects.equals(var10001, var2.a) && var2.a.a.b, var2.a.a.a())) == null) {
            if (var7.isActive()) {
               throw new IllegalStateException(var7.displayName() + " actively returned null PathingCommand");
            }
         } else if (var5.commandType != PathingCommandType.DEFER) {
            var2.b = var7;
            if (!var7.isTemporary()) {
               var3.forEachRemaining(IBaritoneProcess::onLostControl);
            }

            var8 = var5;
            break;
         }
      }

      this.a = var8;
      if (this.a == null) {
         var1.b();
         var1.a = null;
      } else {
         if (!Objects.equals(this.b, this.a) && this.a.commandType != PathingCommandType.REQUEST_PAUSE && this.a != null && !this.a.isTemporary()) {
            var1.b();
         }

         switch (this.a.commandType) {
            case SET_GOAL_AND_PAUSE:
               var1.a(this.a);
            case REQUEST_PAUSE:
               var1.a = true;
               return;
            case CANCEL_AND_SET_GOAL:
               var1.a = this.a.goal;
               var1.b();
               return;
            case FORCE_REVALIDATE_GOAL_AND_PATH:
            case REVALIDATE_GOAL_AND_PATH:
               if (!var1.isPathing() && !var1.getInProgress().isPresent()) {
                  var1.a(this.a);
                  return;
               }
               break;
            case SET_GOAL_AND_PATH:
               if (this.a.goal != null) {
                  var1.a(this.a);
                  return;
               }
               break;
            default:
               throw new IllegalStateException("Unexpected command type " + String.valueOf(this.a.commandType));
         }

      }
   }

   public final boolean a(Goal var1) {
      PathExecutor var2;
      if ((var2 = this.a.a.a) != null) {
         Goal var3 = var2.getPath().getGoal();
         BetterBlockPos var4 = var2.getPath().getDest();
         if (var3.isInGoal(var4) && !var1.isInGoal(var4)) {
            return true;
         }
      }

      return false;
   }
}
