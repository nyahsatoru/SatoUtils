package baritone.process;

import baritone.Baritone;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalBlock;
import baritone.api.pathing.goals.GoalComposite;
import baritone.api.pathing.goals.GoalNear;
import baritone.api.pathing.goals.GoalXZ;
import baritone.api.process.IFollowProcess;
import baritone.api.process.PathingCommand;
import baritone.api.process.PathingCommandType;
import baritone.api.utils.BetterBlockPos;
import baritone.utils.BaritoneProcessHelper;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.class_1297;
import net.minecraft.class_1542;
import net.minecraft.class_1799;
import net.minecraft.class_2338;

public final class FollowProcess extends BaritoneProcessHelper implements IFollowProcess {
   private Predicate<class_1297> a;
   private List<class_1297> a;
   private boolean a;

   public FollowProcess(Baritone var1) {
      super(var1);
   }

   public final PathingCommand onTick(boolean var1, boolean var2) {
      this.a();
      GoalComposite var3 = new GoalComposite((Goal[])this.a.stream().map(this::a).toArray((var0) -> new Goal[var0]));
      return new PathingCommand(var3, PathingCommandType.REVALIDATE_GOAL_AND_PATH);
   }

   private Goal a(class_1297 var1) {
      Object var3;
      if ((Double)Baritone.a().followOffsetDistance.value != (double)0.0F && !this.a) {
         GoalXZ var2 = GoalXZ.fromDirection(var1.method_73189(), (Float)Baritone.a().followOffsetDirection.value, (Double)Baritone.a().followOffsetDistance.value);
         var3 = new BetterBlockPos((double)var2.getX(), var1.method_73189().field_1351, (double)var2.getZ());
      } else {
         var3 = var1.method_24515();
      }

      return (Goal)(this.a ? new GoalBlock((class_2338)var3) : new GoalNear((class_2338)var3, (Integer)Baritone.a().followRadius.value));
   }

   private boolean a(class_1297 var1) {
      if (var1 == null) {
         return false;
      } else if (!var1.method_5805()) {
         return false;
      } else if (var1.equals(super.a.player())) {
         return false;
      } else {
         int var2;
         if ((var2 = (Integer)Baritone.a().followTargetMaxDistance.value) != 0 && var1.method_5858(super.a.player()) > (double)(var2 * var2)) {
            return false;
         } else {
            Stream var10000 = super.a.entitiesStream();
            Objects.requireNonNull(var1);
            return var10000.anyMatch(var1::equals);
         }
      }
   }

   private void a() {
      this.a = (List)super.a.entitiesStream().filter(this::a).filter(this.a).distinct().collect(Collectors.toList());
   }

   public final boolean isActive() {
      if (this.a == null) {
         return false;
      } else {
         this.a();
         return !this.a.isEmpty();
      }
   }

   public final void onLostControl() {
      this.a = null;
      this.a = null;
   }

   public final String displayName0() {
      return "Following " + String.valueOf(this.a);
   }

   public final void follow(Predicate<class_1297> var1) {
      this.a = var1;
      this.a = false;
   }

   public final void pickup(Predicate<class_1799> var1) {
      this.a = (var1x) -> var1x instanceof class_1542 && var1.test(((class_1542)var1x).method_6983());
      this.a = true;
   }

   public final List<class_1297> following() {
      return this.a;
   }

   public final Predicate<class_1297> currentFilter() {
      return this.a;
   }
}
