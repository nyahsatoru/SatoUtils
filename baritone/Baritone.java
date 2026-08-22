package baritone;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.Settings;
import baritone.api.behavior.IBehavior;
import baritone.api.event.listener.IEventBus;
import baritone.api.process.IBaritoneProcess;
import baritone.api.process.IElytraProcess;
import baritone.api.utils.IPlayerContext;
import baritone.behavior.InventoryBehavior;
import baritone.behavior.LookBehavior;
import baritone.behavior.PathingBehavior;
import baritone.behavior.WaypointBehavior;
import baritone.cache.WorldProvider;
import baritone.command.manager.CommandManager;
import baritone.event.GameEventHandler;
import baritone.process.BackfillProcess;
import baritone.process.BuilderProcess;
import baritone.process.CustomGoalProcess;
import baritone.process.ElytraProcess;
import baritone.process.ExploreProcess;
import baritone.process.FarmProcess;
import baritone.process.FollowProcess;
import baritone.process.GetToBlockProcess;
import baritone.process.InventoryPauserProcess;
import baritone.process.MineProcess;
import baritone.selection.SelectionManager;
import baritone.utils.BlockStateInterface;
import baritone.utils.GuiClick;
import baritone.utils.InputOverrideHandler;
import baritone.utils.PathingControlManager;
import baritone.utils.player.BaritonePlayerContext;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import net.minecraft.class_310;

public class Baritone implements IBaritone {
   private static final ThreadPoolExecutor a;
   private final class_310 a;
   public final Path a;
   private final GameEventHandler a;
   public final PathingBehavior a;
   public final LookBehavior a;
   public final InventoryBehavior a;
   public final InputOverrideHandler a;
   private final FollowProcess a;
   private final MineProcess a;
   private final GetToBlockProcess a;
   private final CustomGoalProcess a;
   public final BuilderProcess a;
   private final ExploreProcess a;
   private final FarmProcess a;
   public final InventoryPauserProcess a;
   private final IElytraProcess a;
   public final PathingControlManager a;
   public final SelectionManager a;
   public final CommandManager a;
   private final BaritonePlayerContext a;
   public final WorldProvider a;
   public BlockStateInterface a;

   Baritone(class_310 var1) {
      this.a = var1;
      this.a = new GameEventHandler(this);
      this.a = var1.field_1697.toPath().resolve("baritone");
      if (!Files.exists(this.a, new LinkOption[0])) {
         try {
            Files.createDirectories(this.a);
         } catch (IOException var2) {
         }
      }

      this.a = new BaritonePlayerContext(this, var1);
      this.a = (LookBehavior)this.a(LookBehavior::new);
      this.a = (PathingBehavior)this.a(PathingBehavior::new);
      this.a = (InventoryBehavior)this.a(InventoryBehavior::new);
      this.a = (InputOverrideHandler)this.a(InputOverrideHandler::new);
      this.a(WaypointBehavior::new);
      this.a = new PathingControlManager(this);
      this.a = (FollowProcess)this.a(FollowProcess::new);
      this.a = (MineProcess)this.a(MineProcess::new);
      this.a = (CustomGoalProcess)this.a(CustomGoalProcess::new);
      this.a = (GetToBlockProcess)this.a(GetToBlockProcess::new);
      this.a = (BuilderProcess)this.a(BuilderProcess::new);
      this.a = (ExploreProcess)this.a(ExploreProcess::new);
      this.a = (FarmProcess)this.a(FarmProcess::new);
      this.a = (InventoryPauserProcess)this.a(InventoryPauserProcess::new);
      this.a = (IElytraProcess)this.a(ElytraProcess::a);
      this.a(BackfillProcess::new);
      this.a = new WorldProvider(this);
      this.a = new SelectionManager(this);
      this.a = new CommandManager(this);
   }

   private void a(IBehavior var1) {
      this.a.registerEventListener(var1);
   }

   public final <T extends IBehavior> T a(Function<Baritone, T> var1) {
      IBehavior var2 = (IBehavior)var1.apply(this);
      this.a(var2);
      return (T)var2;
   }

   private <T extends IBaritoneProcess> T a(Function<Baritone, T> var1) {
      IBaritoneProcess var2 = (IBaritoneProcess)var1.apply(this);
      this.a.registerProcess(var2);
      return (T)var2;
   }

   public IPlayerContext getPlayerContext() {
      return this.a;
   }

   public IEventBus getGameEventHandler() {
      return this.a;
   }

   public IElytraProcess getElytraProcess() {
      return this.a;
   }

   public void openClick() {
      (new Thread(() -> {
         try {
            Thread.sleep(100L);
            this.a.execute(() -> this.a.method_1507(new GuiClick()));
         } catch (Exception var1) {
         }
      })).start();
   }

   public static Settings a() {
      return BaritoneAPI.getSettings();
   }

   public static Executor a() {
      return a;
   }

   static {
      a = new ThreadPoolExecutor(4, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue());
   }
}
