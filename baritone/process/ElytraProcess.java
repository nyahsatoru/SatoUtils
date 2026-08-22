package baritone.process;

import baritone.Baritone;
import baritone.api.Settings;
import baritone.api.event.events.BlockChangeEvent;
import baritone.api.event.events.ChunkEvent;
import baritone.api.event.events.PacketEvent;
import baritone.api.event.events.RenderEvent;
import baritone.api.event.events.TickEvent;
import baritone.api.event.events.WorldEvent;
import baritone.api.event.events.type.EventState;
import baritone.api.event.listener.AbstractGameEventListener;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalBlock;
import baritone.api.pathing.goals.GoalXZ;
import baritone.api.pathing.goals.GoalYLevel;
import baritone.api.pathing.movement.IMovement;
import baritone.api.process.IBaritoneProcess;
import baritone.api.process.IElytraProcess;
import baritone.api.process.PathingCommand;
import baritone.api.process.PathingCommandType;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.Pair;
import baritone.api.utils.Rotation;
import baritone.api.utils.RotationUtils;
import baritone.api.utils.input.Input;
import baritone.pathing.movement.CalculationContext;
import baritone.pathing.movement.movements.MovementFall;
import baritone.pathing.path.PathExecutor;
import baritone.process.elytra.ElytraBehavior;
import baritone.process.elytra.NetherPath;
import baritone.process.elytra.NetherPathfinderContext;
import baritone.process.elytra.NullElytraProcess;
import baritone.utils.BaritoneProcessHelper;
import baritone.utils.BlockStateInterface;
import baritone.utils.IRenderer;
import baritone.utils.PathRenderer;
import baritone.utils.PathingCommandContext;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import java.awt.Color;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.Executor;
import net.minecraft.class_1304;
import net.minecraft.class_1713;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1937;
import net.minecraft.class_2189;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2371;
import net.minecraft.class_243;
import net.minecraft.class_2561;
import net.minecraft.class_2680;
import net.minecraft.class_2818;
import net.minecraft.class_287;
import net.minecraft.class_638;

public class ElytraProcess extends BaritoneProcessHelper implements AbstractGameEventListener, IBaritoneProcess, IElytraProcess {
   public State a;
   private boolean a;
   private BetterBlockPos a;
   private boolean b;
   private GoalYLevel a;
   private ElytraBehavior a;
   private boolean c;
   private Set<BetterBlockPos> a = new HashSet();

   public void onLostControl() {
      this.a = ElytraProcess.State.d;
      this.a = false;
      this.a = null;
      this.b = false;
      this.a = null;
      this.a();
   }

   private ElytraProcess(Baritone var1) {
      super(var1);
      var1.getGameEventHandler().registerEventListener(this);
   }

   public static IElytraProcess a(Baritone var0) {
      return (IElytraProcess)(NetherPathfinderContext.a() ? new ElytraProcess(var0) : new NullElytraProcess(var0));
   }

   public boolean isActive() {
      return this.a != null;
   }

   public void resetState() {
      class_2338 var1 = this.currentDestination();
      this.onLostControl();
      if (var1 != null) {
         this.pathTo(var1);
         this.repackChunks();
      }

   }

   public PathingCommand onTick(boolean var1, boolean var2) {
      if ((Long)Baritone.a().elytraNetherSeed.value != this.a.a.b) {
         this.logDirect("Nether seed changed, recalculating path");
         this.resetState();
      }

      if (this.c != (Boolean)Baritone.a().elytraPredictTerrain.value) {
         this.logDirect("elytraPredictTerrain setting changed, recalculating path");
         this.c = (Boolean)Baritone.a().elytraPredictTerrain.value;
         this.resetState();
      }

      ElytraBehavior var4;
      synchronized((var4 = this.a).a.a) {
         ElytraBehavior var3 = var4;
         var4.a = null;
         if (var4.a != null) {
            try {
               var3.a = (ElytraBehavior.Solution)var3.a.get();
            } catch (Exception var18) {
            } finally {
               var4.a = null;
            }
         }

         Runnable var7;
         if (var4.d <= 0 && (var7 = (Runnable)var4.a.poll()) != null) {
            var7.run();
            var4.d = (Integer)Baritone.a().ticksBetweenInventoryMoves.value;
         }

         if (var4.d > 0) {
            --var4.d;
         }

         if (var4.a > 0) {
            --var4.a;
         }

         if (var4.b > 0) {
            --var4.b;
         }

         if (!var4.a().isPresent()) {
            var4.c = 0;
         }

         var4.a.clear();
         var4.b.clear();
         var4.d = null;
         var4.c = null;
         var4.a = null;
         NetherPath var5;
         if (!(var5 = var4.a.a).isEmpty()) {
            if (var4.b == null) {
               var4.a.a();
            } else {
               var4.a = new BlockStateInterface(var4.a);
               ElytraBehavior.PathManager var6;
               (var6 = var4.a).c();
               int var41 = var6.a;
               var6.a = Math.max(var6.a, var6.c);
               if (var6.a == var41 && var6.a.a.player().method_6128()) {
                  ++var6.b;
               } else {
                  var6.b = 0;
               }

               var6.b();
               if (!var6.b) {
                  var41 = var6.a.size() - 1;
                  if (!var6.a && var6.a.a.world().method_8477(var6.a.a(var41))) {
                     var6.a(var41);
                  }
               }

               int var38 = var4.a.c;
               var4.d = var5.subList(Math.max(var38 - 30, 0), Math.min(var38 + 100, var5.size()));
            }
         }
      }

      long var11;
      if (((var11 = System.currentTimeMillis()) - var4.a) / 1000L > (Long)Baritone.a().elytraTimeBetweenCacheCullSecs.value) {
         var4.a.a(var4.a.player().method_31476().field_9181, var4.a.player().method_31476().field_9180, (Integer)Baritone.a().elytraCacheCullDistance.value, var4.a);
         var4.a = var11;
      }

      if (var1) {
         this.onLostControl();
         this.logDirect("Failed to compute a walking path to a spot to jump off from. Consider starting from a higher location, near an overhang. Or, you can disable elytraAutoJump and just manually begin gliding.");
         return new PathingCommand((Goal)null, PathingCommandType.CANCEL_AND_SET_GOAL);
      } else {
         var1 = false;
         if (super.a.player().method_6128() && this.a()) {
            if ((Boolean)Baritone.a().elytraAllowEmergencyLand.value) {
               this.logDirect("Emergency landing - almost out of elytra durability or fireworks");
               var1 = true;
            } else {
               this.logDirect("almost out of elytra durability or fireworks, but I'm going to continue since elytraAllowEmergencyLand is false");
            }
         }

         if (super.a.player().method_6128() && this.a != ElytraProcess.State.f && (this.a.a.a || var1)) {
            BetterBlockPos var28;
            if ((var28 = this.a.a.a.a()) != null && (super.a.player().method_73189().method_1025(var28.method_46558()) < (double)2304.0F || var1) && (!this.a || var1 && this.a == null)) {
               this.logDirect("Path complete, picking a nearby safe landing spot...");
               BetterBlockPos var51 = super.a.playerFeet();
               ElytraProcess var32 = this;
               PriorityQueue var12 = new PriorityQueue(Comparator.comparingInt((var1x) -> (var1x.x - var51.x) * (var1x.x - var51.x) + (var1x.z - var51.z) * (var1x.z - var51.z)).thenComparingInt((var0) -> -var0.y));
               HashSet var22 = new HashSet();
               LongOpenHashSet var37 = new LongOpenHashSet();
               var12.add(var51);

               BetterBlockPos var63;
               while(true) {
                  if (var12.isEmpty()) {
                     var63 = null;
                     break;
                  }

                  BetterBlockPos var39 = (BetterBlockPos)var12.poll();
                  if (var32.a.world().method_8477(var39) && ((class_2338)var39).method_10264() >= 0 && ((class_2338)var39).method_10264() < 128 && var32.a.world().method_8320(var39).method_26204() == class_2246.field_10124) {
                     LongOpenHashSet var10 = var37;
                     ElytraProcess var8 = var32;
                     class_2338.class_2339 var52 = new class_2338.class_2339(((class_2338)var39).method_10263(), ((class_2338)var39).method_10264(), ((class_2338)var39).method_10260());

                     while(true) {
                        if (var52.method_10264() >= 0 && !var10.contains(var52.method_10063())) {
                           var10.add(var52.method_10063());
                           class_2248 var13;
                           if (a(var13 = var8.a.world().method_8320(var52).method_26204())) {
                              var63 = var8.a(((class_2338)var52).method_10095()) && var8.a(((class_2338)var52).method_10072()) && var8.a(((class_2338)var52).method_10078()) && var8.a(((class_2338)var52).method_10067()) && var8.a(((class_2338)var52).method_10095().method_10067()) && var8.a(((class_2338)var52).method_10095().method_10078()) && var8.a(((class_2338)var52).method_10072().method_10067()) && var8.a(((class_2338)var52).method_10072().method_10078()) ? new BetterBlockPos(var52) : null;
                              break;
                           }

                           if (var13 == class_2246.field_10124) {
                              var52.method_10103(var52.method_10263(), var52.method_10264() - 1, var52.method_10260());
                              continue;
                           }
                        }

                        var63 = null;
                        break;
                     }

                     BetterBlockPos var43 = var63;
                     if (var63 != null) {
                        var8 = var32;
                        int var58 = (var52 = new class_2338.class_2339(((class_2338)var43).method_10263(), ((class_2338)var43).method_10264(), ((class_2338)var43).method_10260())).method_10264() + 15;
                        int var48 = var52.method_10264() + 1;

                        while(true) {
                           if (var48 > var58) {
                              var61 = true;
                              break;
                           }

                           var52.method_10103(var52.method_10263(), var48, var52.method_10260());
                           if (!(var8.a.world().method_8320(var52).method_26204() instanceof class_2189)) {
                              var61 = false;
                              break;
                           }

                           ++var48;
                        }

                        if (var61) {
                           BetterBlockPos var9 = var43.above(15);
                           var8 = var32;
                           var52 = new class_2338.class_2339();
                           var58 = -4;

                           label937:
                           while(true) {
                              if (var58 > 4) {
                                 var62 = true;
                                 break;
                              }

                              for(int var49 = -4; var49 <= 4; ++var49) {
                                 for(int var14 = -4; var14 <= 4; ++var14) {
                                    var52.method_10103(((class_2338)var9).method_10263() + var58, ((class_2338)var9).method_10264() + var49, ((class_2338)var9).method_10260() + var14);
                                    if (!(var8.a.world().method_8320(var52).method_26204() instanceof class_2189)) {
                                       var62 = false;
                                       break label937;
                                    }
                                 }
                              }

                              ++var58;
                           }

                           if (var62 && !var32.a.contains(var43.above(15))) {
                              var63 = var43.above(15);
                              break;
                           }
                        }
                     }

                     if (var22.add(var39.north())) {
                        var12.add(var39.north());
                     }

                     if (var22.add(var39.east())) {
                        var12.add(var39.east());
                     }

                     if (var22.add(var39.south())) {
                        var12.add(var39.south());
                     }

                     if (var22.add(var39.west())) {
                        var12.add(var39.west());
                     }

                     if (var22.add(var39.above())) {
                        var12.add(var39.above());
                     }

                     if (var22.add(var39.below())) {
                        var12.add(var39.below());
                     }
                  }
               }

               BetterBlockPos var23 = var63;
               if (var63 != null) {
                  this.a(var23, true);
                  this.a = var23;
               }

               this.a = true;
            }

            if (var28 != null && super.a.player().method_73189().method_1025(var28.method_46558()) < (double)1.0F) {
               if ((Boolean)Baritone.a().notificationOnPathComplete.value && !this.b) {
                  this.logNotification("Pathing complete", false);
               }

               if ((Boolean)Baritone.a().disconnectOnArrival.value && !this.b) {
                  this.onLostControl();
                  class_1937 var36;
                  if ((var36 = super.a.world()) instanceof class_638) {
                     ((class_638)var36).method_8525(class_2561.method_43470("[Baritone] Arrived at goal!"));
                  }

                  return new PathingCommand((Goal)null, PathingCommandType.CANCEL_AND_SET_GOAL);
               }

               this.b = true;
               if (this.a) {
                  this.a = ElytraProcess.State.f;
                  this.logDirect("Above the landing spot, landing...");
               }
            }
         }

         if (this.a == ElytraProcess.State.f) {
            BetterBlockPos var29 = this.a != null ? this.a : this.a.a.a.a();
            if (super.a.player().method_6128() && var29 != null) {
               class_243 var24 = super.a.player().method_73189();
               class_243 var33 = new class_243((double)var29.x + (double)0.5F, var24.field_1351, (double)var29.z + (double)0.5F);
               Rotation var25 = RotationUtils.calcRotationFromVec3d(var24, var33, super.a.playerRotations());
               super.a.a.updateTarget(new Rotation(var25.getYaw(), 0.0F), false);
               if (super.a.player().method_73189().field_1351 < (double)(var29.y - 15)) {
                  this.logDirect("bad landing spot, trying again...");
                  this.a(var29);
               }
            }
         }

         if (super.a.player().method_6128()) {
            this.a.a = this.a == ElytraProcess.State.f;
            this.a = null;
            super.a.a.clearAllKeys();
            if (!(var4 = this.a).a.a.isEmpty()) {
               class_1799 var40;
               if ((Boolean)Baritone.a().elytraAutoSwap.value && var4.a.isEmpty() && (var40 = var4.a.player().method_6118(class_1304.field_6174)).method_7909() == class_1802.field_8833 && var40.method_7936() - var40.method_7919() <= (Integer)Baritone.a().elytraMinimumDurability.value) {
                  class_2371 var55 = var4.a.player().method_31548().method_67533();
                  int var60 = 0;

                  int var64;
                  while(true) {
                     if (var60 >= var55.size()) {
                        var64 = -1;
                        break;
                     }

                     class_1799 var50;
                     if ((var50 = (class_1799)var55.get(var60)).method_7909() == class_1802.field_8833 && var50.method_7936() - var50.method_7919() > (Integer)Baritone.a().elytraMinimumDurability.value) {
                        var64 = var60;
                        break;
                     }

                     ++var60;
                  }

                  int var44 = var64;
                  if (var64 != -1) {
                     int var47 = var44 < 9 ? var44 + 36 : var44;
                     var4.a(var4.a.player().field_7498.field_7763, var47, class_1713.field_7790);
                     var4.a(var4.a.player().field_7498.field_7763, 6, class_1713.field_7790);
                     var4.a(var4.a.player().field_7498.field_7763, var47, class_1713.field_7790);
                  }
               }

               if (var4.a.player().field_5976) {
                  var4.a("hbonk");
               }

               if (var4.a.player().field_5992) {
                  var4.a("vbonk");
               }

               ElytraBehavior.SolverContext var56 = var4.new SolverContext(false);
               var4.d = true;
               ElytraBehavior.Solution var57;
               if (var4.a != null && var4.a.a.equals(var56)) {
                  var57 = var4.a;
               } else {
                  var57 = var4.a(var56);
               }

               if (var4.b) {
                  int var10002 = var4.a[var56.a.a() ? 1 : 0]++;
                  var4.b = false;
               }

               if (var1 = var4.a.player().method_5771()) {
                  var4.a.a.setInputForceState(Input.JUMP, true);
               }

               if (var57 == null) {
                  var4.a("no solution");
               } else {
                  var4.a.a.updateTarget(var57.a, false);
                  if (!var57.a) {
                     var4.a("no pitch solution, probably gonna crash in a few ticks LOL!!!");
                  } else {
                     var4.a = new BetterBlockPos(var57.a.field_1352, var57.a.field_1351, var57.a.field_1350);
                     var4.a(var57.a.a, var57.a, var57.a.a.a(), var57.b || var1);
                  }
               }
            }

            return new PathingCommand((Goal)null, PathingCommandType.CANCEL_AND_SET_GOAL);
         } else if (this.a == ElytraProcess.State.f) {
            if (super.a.playerMotion().method_18805((double)1.0F, (double)0.0F, (double)1.0F).method_1033() > 0.001) {
               this.logDirect("Landed, but still moving, waiting for velocity to die down... ");
               super.a.a.setInputForceState(Input.SNEAK, true);
               return new PathingCommand((Goal)null, PathingCommandType.REQUEST_PAUSE);
            } else {
               this.logDirect("Done :)");
               super.a.a.clearAllKeys();
               this.onLostControl();
               return new PathingCommand((Goal)null, PathingCommandType.REQUEST_PAUSE);
            }
         } else {
            if (this.a == ElytraProcess.State.e || this.a == ElytraProcess.State.d) {
               this.a = super.a.player().method_24828() && (Boolean)Baritone.a().elytraAutoJump.value ? ElytraProcess.State.a : ElytraProcess.State.d;
            }

            if (this.a == ElytraProcess.State.a) {
               if (this.a()) {
                  this.logDirect("Not taking off, because elytra durability or fireworks are so low that I would immediately emergency land anyway.");
                  this.onLostControl();
                  return new PathingCommand((Goal)null, PathingCommandType.CANCEL_AND_SET_GOAL);
               } else {
                  if (this.a == null) {
                     this.a = new GoalYLevel(31);
                  }

                  PathExecutor var31;
                  if ((var31 = super.a.a.a) != null && var31.getPath().getGoal() == this.a) {
                     IMovement var26;
                     if ((var26 = (IMovement)var31.getPath().movements().stream().filter((var0) -> var0 instanceof MovementFall).findFirst().orElse((Object)null)) == null) {
                        this.onLostControl();
                        this.logDirect("Failed to compute a walking path to a spot to jump off from. Consider starting from a higher location, near an overhang. Or, you can disable elytraAutoJump and just manually begin gliding.");
                        return new PathingCommand((Goal)null, PathingCommandType.CANCEL_AND_SET_GOAL);
                     }

                     BetterBlockPos var34 = new BetterBlockPos((var26.getSrc().x + var26.getDest().x) / 2, (var26.getSrc().y + var26.getDest().y) / 2, (var26.getSrc().z + var26.getDest().z) / 2);
                     this.a.a.a(var34).whenComplete((var1x, var2x) -> {
                        if (var2x == null) {
                           this.a = ElytraProcess.State.c;
                        } else {
                           this.onLostControl();
                        }
                     });
                     this.a = ElytraProcess.State.b;
                  }

                  return new PathingCommandContext(this.a, PathingCommandType.SET_GOAL_AND_PAUSE, new WalkOffCalculationContext(super.a));
               }
            } else if (this.a == ElytraProcess.State.b) {
               return new PathingCommand((Goal)null, PathingCommandType.REQUEST_PAUSE);
            } else {
               if (this.a == ElytraProcess.State.c) {
                  PathExecutor var30 = super.a.a.a;
                  if (!(super.a.player().method_18798().field_1351 < -0.377) || var2 || var30 == null || !(var30.getPath().movements().get(var30.getPosition()) instanceof MovementFall)) {
                     return new PathingCommand((Goal)null, PathingCommandType.SET_GOAL_AND_PATH);
                  }

                  this.a = ElytraProcess.State.d;
               }

               if (this.a == ElytraProcess.State.d) {
                  if (!var2) {
                     super.a.a.b();
                  }

                  super.a.a.clearAllKeys();
                  if (super.a.player().method_18798().field_1351 < -0.377) {
                     super.a.a.setInputForceState(Input.JUMP, true);
                  }
               }

               return new PathingCommand((Goal)null, PathingCommandType.CANCEL_AND_SET_GOAL);
            }
         }
      }
   }

   public final void a(BetterBlockPos var1) {
      this.a.add(var1);
      this.a = false;
      this.a = null;
      this.a = ElytraProcess.State.e;
   }

   private void a() {
      ElytraBehavior var1;
      if ((var1 = this.a) != null) {
         this.a = null;
         Executor var10000 = Baritone.a();
         Objects.requireNonNull(var1);
         var10000.execute(var1::b);
      }

   }

   public double priority() {
      return (double)0.0F;
   }

   public String displayName0() {
      return "Elytra - " + this.a.a;
   }

   public void repackChunks() {
      if (this.a != null) {
         this.a.c();
      }

   }

   public class_2338 currentDestination() {
      return this.a != null ? this.a.b : null;
   }

   public void pathTo(class_2338 var1) {
      this.a(var1, false);
   }

   private void a(class_2338 var1, boolean var2) {
      if (super.a.player() != null && super.a.player().method_73183().method_27983() == class_1937.field_25180) {
         this.onLostControl();
         this.c = (Boolean)Baritone.a().elytraPredictTerrain.value;
         this.a = new ElytraBehavior(super.a, this, var1, var2);
         if (super.a.world() != null) {
            this.a.c();
         }

         this.a.a();
      }
   }

   public void pathTo(Goal var1) {
      int var2;
      int var4;
      int var5;
      if (var1 instanceof GoalXZ) {
         GoalXZ var3;
         var4 = (var3 = (GoalXZ)var1).getX();
         var2 = 64;
         var5 = var3.getZ();
      } else {
         if (!(var1 instanceof GoalBlock)) {
            throw new IllegalArgumentException("The goal must be a GoalXZ or GoalBlock");
         }

         GoalBlock var6;
         var4 = (var6 = (GoalBlock)var1).x;
         var2 = var6.y;
         var5 = var6.z;
      }

      if (var2 > 0 && var2 < 128) {
         this.pathTo(new class_2338(var4, var2, var5));
      } else {
         throw new IllegalArgumentException("The y of the goal is not between 0 and 128");
      }
   }

   private boolean a() {
      class_1799 var1;
      if ((var1 = super.a.player().method_6118(class_1304.field_6174)).method_7909() == class_1802.field_8833 && var1.method_7936() - var1.method_7919() >= (Integer)Baritone.a().elytraMinimumDurability.value) {
         class_2371 var4 = super.a.player().method_31548().method_67533();
         int var2 = 0;

         for(int var3 = 0; var3 < 36; ++var3) {
            if (ElytraBehavior.a((class_1799)var4.get(var3))) {
               var2 += ((class_1799)var4.get(var3)).method_7947();
            }
         }

         if (var2 <= (Integer)Baritone.a().elytraMinFireworksBeforeLanding.value) {
            return true;
         } else {
            return false;
         }
      } else {
         return true;
      }
   }

   public boolean isLoaded() {
      return true;
   }

   public boolean isSafeToCancel() {
      return !this.isActive() || this.a != ElytraProcess.State.e && this.a != ElytraProcess.State.d;
   }

   public void onRenderPass(RenderEvent var1) {
      if (this.a != null) {
         RenderEvent var2 = var1;
         ElytraBehavior var9 = this.a;
         Settings var3 = Baritone.a();
         if (var9.d != null) {
            PathRenderer.a(var1.getModelViewStack(), var9.d, 0, Color.RED, false, 0, 0, (double)0.0F);
         }

         if (var9.a != null) {
            PathRenderer.a(var1.getModelViewStack(), var9.a, new GoalBlock(var9.a), var1.getPartialTicks(), Color.GREEN);
         }

         if (!var9.a.isEmpty() && (Boolean)var3.elytraRenderRaytraces.value) {
            class_287 var4 = IRenderer.a(Color.GREEN);

            for(Pair var6 : var9.a) {
               IRenderer.a(var4, var2.getModelViewStack(), (class_243)var6.first(), (class_243)var6.second(), (Float)var3.pathRenderLineWidthPixels.value);
            }

            IRenderer.a(var4, (Boolean)var3.renderPathIgnoreDepth.value);
         }

         if (!var9.b.isEmpty() && (Boolean)Baritone.a().elytraRenderRaytraces.value) {
            class_287 var10 = IRenderer.a(Color.BLUE);

            for(Pair var14 : var9.b) {
               IRenderer.a(var10, var2.getModelViewStack(), (class_243)var14.first(), (class_243)var14.second(), (Float)var3.pathRenderLineWidthPixels.value);
            }

            IRenderer.a(var10, (Boolean)var3.renderPathIgnoreDepth.value);
         }

         if (var9.c != null && (Boolean)Baritone.a().elytraRenderSimulation.value) {
            class_287 var11 = IRenderer.a(new Color(3591388));
            class_243 var13 = var9.a.player().method_30950(var2.getPartialTicks());

            for(int var15 = 0; var15 < var9.c.size() - 1; ++var15) {
               class_243 var7 = ((class_243)var9.c.get(var15)).method_1019(var13);
               class_243 var8 = ((class_243)var9.c.get(var15 + 1)).method_1019(var13);
               IRenderer.a(var11, var2.getModelViewStack(), var7, var8, (Float)var3.pathRenderLineWidthPixels.value);
            }

            IRenderer.a(var11, (Boolean)var3.renderPathIgnoreDepth.value);
         }
      }

   }

   public void onWorldEvent(WorldEvent var1) {
      if (var1.getWorld() != null && var1.getState() == EventState.POST) {
         this.a();
      }

   }

   public void onChunkEvent(ChunkEvent var1) {
      if (this.a != null) {
         ChunkEvent var2 = var1;
         ElytraBehavior var3 = this.a;
         if (var2.isPostPopulate() && var3.a != null) {
            class_2818 var4 = var3.a.world().method_8497(var2.getX(), var2.getZ());
            var3.a.a(var4);
         }
      }

   }

   public void onBlockChange(BlockChangeEvent var1) {
      if (this.a != null) {
         this.a.a.a(var1);
      }

   }

   public void onReceivePacket(PacketEvent var1) {
      if (this.a != null) {
         this.a.a(var1);
      }

   }

   public void onPostTick(TickEvent var1) {
      IBaritoneProcess var2 = (IBaritoneProcess)super.a.a.mostRecentInControl().orElse((Object)null);
      if (this.a != null && var2 == this) {
         this.a.a(var1);
      }

   }

   private static boolean a(class_2248 var0) {
      return var0 == class_2246.field_10515 || var0 == class_2246.field_10255 || var0 == class_2246.field_10266 && (Boolean)Baritone.a().elytraAllowLandOnNetherFortress.value;
   }

   private boolean a(class_2338 var1) {
      return a(super.a.world().method_8320(var1).method_26204());
   }

   public static enum State {
      a("Finding spot to jump off"),
      b("Waiting for elytra path"),
      c("Walking to takeoff"),
      d("Begin flying"),
      e("Flying"),
      f("Landing");

      public final String a;

      private State(String var3) {
         this.a = var3;
      }
   }

   public static final class WalkOffCalculationContext extends CalculationContext {
      public WalkOffCalculationContext(Baritone var1) {
         super(var1, true);
         super.k = true;
         super.b = 8;
         super.c = 10000;
      }

      public final double a(int var1, int var2, int var3, class_2680 var4) {
         return (double)1000000.0F;
      }

      public final double b(int var1, int var2, int var3, class_2680 var4) {
         return (double)1000000.0F;
      }

      public final double a() {
         return (double)1000000.0F;
      }
   }
}
