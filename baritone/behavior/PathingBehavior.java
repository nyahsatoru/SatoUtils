package baritone.behavior;

import baritone.Baritone;
import baritone.api.behavior.IPathingBehavior;
import baritone.api.event.events.PathEvent;
import baritone.api.event.events.PlayerUpdateEvent;
import baritone.api.event.events.RenderEvent;
import baritone.api.event.events.SprintStateEvent;
import baritone.api.event.events.TickEvent;
import baritone.api.pathing.calc.IPath;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalXZ;
import baritone.api.process.PathingCommand;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.Helper;
import baritone.api.utils.PathCalculationResult;
import baritone.api.utils.interfaces.IGoalRenderPos;
import baritone.pathing.calc.AStarPathFinder;
import baritone.pathing.calc.AbstractNodeCostSearch;
import baritone.pathing.movement.CalculationContext;
import baritone.pathing.movement.MovementHelper;
import baritone.pathing.path.PathExecutor;
import baritone.utils.PathRenderer;
import baritone.utils.PathingCommandContext;
import baritone.utils.pathing.Favoring;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2561;
import net.minecraft.class_638;

public final class PathingBehavior extends Behavior implements IPathingBehavior, Helper {
   public PathExecutor a;
   public PathExecutor b;
   public Goal a;
   public CalculationContext a;
   private int a;
   private BetterBlockPos a;
   private boolean c;
   public boolean a;
   private boolean d;
   private boolean e;
   private boolean f;
   public boolean b;
   private volatile AStarPathFinder a;
   private final Object a = new Object();
   private final Object b = new Object();
   private boolean g;
   private BetterBlockPos b;
   private final LinkedBlockingQueue<PathEvent> a = new LinkedBlockingQueue();

   public PathingBehavior(Baritone var1) {
      super(var1);
   }

   private void a(PathEvent var1) {
      this.a.add(var1);
   }

   private void c() {
      ArrayList var1 = new ArrayList();
      this.a.drainTo(var1);
      this.b = var1.contains(PathEvent.CALC_FAILED);

      for(PathEvent var2 : var1) {
         super.a.getGameEventHandler().onPathEvent(var2);
      }

   }

   public final void onTick(TickEvent var1) {
      this.c();
      if (var1.getType() == TickEvent.Type.OUT) {
         this.b();
         super.a.a.a();
      } else {
         this.b = this.a();
         super.a.a.b();
         PathingBehavior var10 = this;
         this.e = false;
         if (this.a && this.c) {
            this.a = false;
            if (this.d) {
               super.a.a.clearAllKeys();
               super.a.a.a.a();
            }

            this.d = false;
            this.e = true;
         } else {
            this.d = true;
            if (this.f) {
               this.f = false;
               super.a.a.clearAllKeys();
            }

            synchronized(this.b) {
               synchronized(var10.a) {
                  if (var10.a != null) {
                     BetterBlockPos var4 = var10.a.a();
                     Optional var5 = var10.a.bestPathSoFar();
                     if ((var10.a == null || !var10.a.getPath().getDest().equals(var4)) && !var4.equals(var10.a.playerFeet()) && !var4.equals(var10.b) && (!var5.isPresent() || !((IPath)var5.get()).positions().contains(var10.a.playerFeet()) && !((IPath)var5.get()).positions().contains(var10.b))) {
                        var10.a.a();
                     }
                  }
               }

               if (var10.a != null) {
                  var10.c = var10.a.a();
                  if (!var10.a.a && !var10.a.b()) {
                     label219: {
                        if (var10.c && var10.b != null) {
                           PathExecutor var3;
                           boolean var10000;
                           if (!(var3 = var10.b).a.player().method_24828() && var3.a.world().method_8316(var3.a.playerFeet()).method_15769()) {
                              var10000 = false;
                           } else if (var3.a.player().method_18798().field_1351 < -0.1) {
                              var10000 = false;
                           } else {
                              int var12;
                              if ((var12 = var3.a.positions().indexOf(var3.a.playerFeet())) == -1) {
                                 var10000 = false;
                              } else {
                                 var3.a = var12;
                                 var3.a();
                                 var10000 = true;
                              }
                           }

                           if (var10000) {
                              var10.logDebug("Splicing into planned next path early...");
                              var10.a(PathEvent.SPLICING_ONTO_NEXT_EARLY);
                              var10.a = var10.b;
                              var10.b = null;
                              var10.a.a();
                              break label219;
                           }
                        }

                        if ((Boolean)Baritone.a().splicePath.value) {
                           var10.a = var10.a.a(var10.b);
                        }

                        if (var10.b != null && var10.a.getPath().getDest().equals(var10.b.getPath().getDest())) {
                           var10.b = null;
                        }

                        synchronized(var10.a) {
                           if (var10.a == null) {
                              if (var10.b == null) {
                                 if (var10.a != null && !var10.a.isInGoal(var10.a.getPath().getDest()) && (Double)var10.ticksRemainingInSegment(false).get() < (double)(Integer)Baritone.a().planningTickLookahead.value) {
                                    var10.logDebug("Path almost over. Planning ahead...");
                                    var10.a(PathEvent.NEXT_SEGMENT_CALC_STARTED);
                                    var10.a(var10.a.getPath().getDest(), false, var10.a);
                                 }
                              }
                           }
                        }
                     }
                  } else {
                     var10.a = null;
                     if (var10.a != null && !var10.a.isInGoal(var10.a.playerFeet())) {
                        if (var10.b != null && !var10.b.getPath().positions().contains(var10.a.playerFeet()) && !var10.b.getPath().positions().contains(var10.b)) {
                           var10.logDebug("Discarding next path as it does not contain current position");
                           var10.a(PathEvent.DISCARD_NEXT);
                           var10.b = null;
                        }

                        if (var10.b != null) {
                           var10.logDebug("Continuing on to planned next path");
                           var10.a(PathEvent.CONTINUING_ONTO_PLANNED_NEXT);
                           var10.a = var10.b;
                           var10.b = null;
                           var10.a.a();
                        } else {
                           synchronized(var10.a) {
                              if (var10.a != null) {
                                 var10.a(PathEvent.PATH_FINISHED_NEXT_STILL_CALCULATING);
                              } else {
                                 var10.a(PathEvent.CALC_STARTED);
                                 var10.a(var10.b, true, var10.a);
                              }
                           }
                        }
                     } else {
                        var10.logDebug("All done. At " + String.valueOf(var10.a));
                        var10.a(PathEvent.AT_GOAL);
                        var10.b = null;
                        class_1937 var11;
                        if ((Boolean)Baritone.a().disconnectOnArrival.value && (var11 = var10.a.world()) instanceof class_638) {
                           ((class_638)var11).method_8525(class_2561.method_43470("[Baritone] Arrived at goal!"));
                        }
                     }
                  }
               }
            }
         }

         ++this.a;
         this.c();
      }
   }

   public final void onPlayerSprintState(SprintStateEvent var1) {
      if (this.isPathing()) {
         var1.setState(this.a.b);
      }

   }

   public final void onPlayerUpdate(PlayerUpdateEvent var1) {
      if (this.a != null) {
         switch (var1.getState()) {
            case PRE:
               this.g = (Boolean)super.a.minecraft().field_1690.method_42423().method_41753();
               super.a.minecraft().field_1690.method_42423().method_41748(Boolean.FALSE);
               return;
            case POST:
               super.a.minecraft().field_1690.method_42423().method_41748(this.g);
         }
      }

   }

   public final boolean a(PathingCommand var1) {
      this.a = var1.goal;
      if (var1 instanceof PathingCommandContext) {
         this.a = ((PathingCommandContext)var1).a;
      } else {
         this.a = new CalculationContext(super.a, true);
      }

      if (this.a == null) {
         return false;
      } else if (this.a.isInGoal(super.a.playerFeet())) {
         return false;
      } else {
         synchronized(this.b) {
            if (this.a != null) {
               return false;
            } else {
               boolean var10000;
               synchronized(this.a) {
                  if (this.a != null) {
                     var10000 = false;
                     return var10000;
                  }

                  this.a(PathEvent.CALC_STARTED);
                  this.a(this.b, true, this.a);
                  var10000 = true;
               }

               return var10000;
            }
         }
      }
   }

   public final Goal getGoal() {
      return this.a;
   }

   public final boolean isPathing() {
      return this.hasPath() && !this.e;
   }

   public final Optional<AbstractNodeCostSearch> getInProgress() {
      return Optional.ofNullable(this.a);
   }

   public final boolean a() {
      if (this.a == null) {
         return !super.a.getElytraProcess().isActive() || super.a.getElytraProcess().isSafeToCancel();
      } else {
         return this.c;
      }
   }

   public final boolean b() {
      if (this.a()) {
         this.b();
         return true;
      } else {
         return false;
      }
   }

   public final boolean cancelEverything() {
      boolean var1;
      if (var1 = this.a()) {
         this.b();
      }

      super.a.a.a();
      return var1;
   }

   public final void a() {
      synchronized(this.b) {
         this.getInProgress().ifPresent(AbstractNodeCostSearch::a);
         if (!this.a()) {
            return;
         }

         this.a = null;
         this.b = null;
      }

      this.f = true;
   }

   public final void b() {
      this.a(PathEvent.CANCELED);
      synchronized(this.b) {
         this.getInProgress().ifPresent(AbstractNodeCostSearch::a);
         if (this.a != null) {
            this.a = null;
            this.b = null;
            super.a.a.clearAllKeys();
            super.a.a.a.a();
         }

      }
   }

   public final void forceCancel() {
      this.cancelEverything();
      this.b();
      synchronized(this.a) {
         this.a = null;
      }
   }

   public final Optional<Double> estimatedTicksToGoal() {
      BetterBlockPos var1 = super.a.playerFeet();
      if (this.a != null && var1 != null && this.a != null) {
         if (this.a.isInGoal(super.a.playerFeet())) {
            this.a(this.b);
            return Optional.of((double)0.0F);
         } else if (this.a == 0) {
            return Optional.empty();
         } else {
            double var2 = this.a.heuristic(var1.x, var1.y, var1.z);
            double var4 = this.a.heuristic(this.a.x, this.a.y, this.a.z);
            return var2 == var4 ? Optional.empty() : Optional.of(Math.abs(var2 - this.a.heuristic()) * (double)this.a / Math.abs(var4 - var2));
         }
      } else {
         return Optional.empty();
      }
   }

   private void a(BetterBlockPos var1) {
      this.a = 0;
      this.a = var1;
   }

   public final BetterBlockPos a() {
      BetterBlockPos var1 = super.a.playerFeet();
      if (!MovementHelper.b(super.a, var1.below())) {
         if (super.a.player().method_24828()) {
            double var2 = super.a.player().method_73189().field_1352;
            double var4 = super.a.player().method_73189().field_1350;
            ArrayList var6 = new ArrayList();

            for(int var7 = -1; var7 <= 1; ++var7) {
               for(int var8 = -1; var8 <= 1; ++var8) {
                  var6.add(new BetterBlockPos(var1.x + var7, var1.y, var1.z + var8));
               }
            }

            var6.sort(Comparator.comparingDouble((var4x) -> ((double)var4x.x + (double)0.5F - var2) * ((double)var4x.x + (double)0.5F - var2) + ((double)var4x.z + (double)0.5F - var4) * ((double)var4x.z + (double)0.5F - var4)));

            for(int var13 = 0; var13 < 4; ++var13) {
               BetterBlockPos var14;
               double var9 = Math.abs((double)(var14 = (BetterBlockPos)var6.get(var13)).x + (double)0.5F - var2);
               double var11 = Math.abs((double)var14.z + (double)0.5F - var4);
               if ((!(var9 > 0.8) || !(var11 > 0.8)) && MovementHelper.b(super.a, var14.below()) && MovementHelper.a(super.a, var14) && MovementHelper.a(super.a, var14.above())) {
                  return var14;
               }
            }
         } else if (MovementHelper.b(super.a, var1.below().below())) {
            return var1.below();
         }
      }

      return var1;
   }

   private void a(BetterBlockPos var1, boolean var2, CalculationContext var3) {
      if (!Thread.holdsLock(this.a)) {
         throw new IllegalStateException("Must be called with synchronization on pathCalcLock");
      } else if (this.a != null) {
         throw new IllegalStateException("Already doing it");
      } else if (!var3.a) {
         throw new IllegalStateException("Improper context thread safety level");
      } else {
         Goal var4;
         if ((var4 = this.a) == null) {
            this.logDebug("no goal");
         } else {
            long var5;
            long var7;
            if (this.a == null) {
               var5 = (Long)Baritone.a().primaryTimeoutMS.value;
               var7 = (Long)Baritone.a().failureTimeoutMS.value;
            } else {
               var5 = (Long)Baritone.a().planAheadPrimaryTimeoutMS.value;
               var7 = (Long)Baritone.a().planAheadFailureTimeoutMS.value;
            }

            AStarPathFinder var9;
            if (!Objects.equals((var9 = this.a(var1, var4, this.a == null ? null : this.a.getPath(), var3)).getGoal(), var4)) {
               this.logDebug("Simplifying " + String.valueOf(var4.getClass()) + " to GoalXZ due to distance");
            }

            this.a = var9;
            Baritone.a().execute(() -> {
               if (var2) {
                  String var10001 = String.valueOf(var1);
                  this.logDebug("Starting to search for path from " + var10001 + " to " + String.valueOf(var4));
               }

               PathCalculationResult var11 = var9.calculate(var5, var7);
               synchronized(this.b) {
                  Optional var6 = var11.getPath().map((var1x) -> new PathExecutor(this, var1x));
                  if (this.a == null) {
                     if (var6.isPresent()) {
                        if (((PathExecutor)var6.get()).getPath().positions().contains(this.b)) {
                           this.a(PathEvent.CALC_FINISHED_NOW_EXECUTING);
                           this.a = (PathExecutor)var6.get();
                           this.a(new BetterBlockPos(var1));
                        } else {
                           this.logDebug("Warning: discarding orphan path segment with incorrect start");
                        }
                     } else if (var11.getType() != PathCalculationResult.Type.CANCELLATION && var11.getType() != PathCalculationResult.Type.EXCEPTION) {
                        this.a(PathEvent.CALC_FAILED);
                     }
                  } else if (this.b == null) {
                     if (var6.isPresent()) {
                        if (((PathExecutor)var6.get()).getPath().getSrc().equals(this.a.getPath().getDest())) {
                           this.a(PathEvent.NEXT_SEGMENT_CALC_FINISHED);
                           this.b = (PathExecutor)var6.get();
                        } else {
                           this.logDebug("Warning: discarding orphan next segment with incorrect start");
                        }
                     } else {
                        this.a(PathEvent.NEXT_CALC_FAILED);
                     }
                  } else {
                     this.logDirect("Warning: PathingBehaivor illegal state! Discarding invalid path!");
                  }

                  if (var2 && this.a != null && this.a.getPath() != null) {
                     if (var4.isInGoal(this.a.getPath().getDest())) {
                        String var12 = String.valueOf(var1);
                        this.logDebug("Finished finding a path from " + var12 + " to " + String.valueOf(var4) + ". " + this.a.getPath().getNumNodesConsidered() + " nodes considered");
                     } else {
                        String var13 = String.valueOf(var1);
                        this.logDebug("Found path segment from " + var13 + " towards " + String.valueOf(var4) + ". " + this.a.getPath().getNumNodesConsidered() + " nodes considered");
                     }
                  }

                  synchronized(this.a) {
                     this.a = null;
                  }

               }
            });
         }
      }
   }

   private AStarPathFinder a(BetterBlockPos var1, Goal var2, IPath var3, CalculationContext var4) {
      Object var5 = var2;
      if ((Boolean)Baritone.a().simplifyUnloadedYCoord.value && var2 instanceof IGoalRenderPos) {
         class_2338 var8 = ((IGoalRenderPos)var2).getGoalPos();
         if (!var4.a.a(var8.method_10263(), var8.method_10260())) {
            var5 = new GoalXZ(var8.method_10263(), var8.method_10260());
         }
      }

      Favoring var9 = new Favoring(var4.a.getPlayerContext(), var3, var4);
      BetterBlockPos var10 = super.a.playerFeet();
      BetterBlockPos var6 = new BetterBlockPos(var1);
      class_2338 var7 = var10.method_10059(var6);
      if (var10.method_10264() == var6.method_10264() && Math.abs(var7.method_10263()) <= 1 && Math.abs(var7.method_10260()) <= 1) {
         var6 = var10;
      }

      return new AStarPathFinder(var6, ((class_2338)var1).method_10263(), ((class_2338)var1).method_10264(), ((class_2338)var1).method_10260(), (Goal)var5, var9, var4);
   }

   public final void onRenderPass(RenderEvent var1) {
      PathRenderer.a(var1, this);
   }
}
