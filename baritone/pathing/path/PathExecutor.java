package baritone.pathing.path;

import baritone.Baritone;
import baritone.api.pathing.calc.IPath;
import baritone.api.pathing.movement.IMovement;
import baritone.api.pathing.movement.MovementStatus;
import baritone.api.pathing.path.IPathExecutor;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.Helper;
import baritone.api.utils.IPlayerContext;
import baritone.api.utils.RotationUtils;
import baritone.api.utils.VecUtils;
import baritone.api.utils.input.Input;
import baritone.behavior.PathingBehavior;
import baritone.pathing.calc.AbstractNodeCostSearch;
import baritone.pathing.movement.CalculationContext;
import baritone.pathing.movement.Movement;
import baritone.pathing.movement.MovementHelper;
import baritone.pathing.movement.movements.MovementAscend;
import baritone.pathing.movement.movements.MovementDescend;
import baritone.pathing.movement.movements.MovementDiagonal;
import baritone.pathing.movement.movements.MovementFall;
import baritone.pathing.movement.movements.MovementParkour;
import baritone.pathing.movement.movements.MovementTraverse;
import baritone.utils.BlockStateInterface;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import net.minecraft.class_2338;
import net.minecraft.class_2382;
import net.minecraft.class_243;
import net.minecraft.class_3545;

public class PathExecutor implements IPathExecutor, Helper {
   public final IPath a;
   public int a;
   private int b;
   private int c;
   private Double a;
   private Integer a;
   public boolean a;
   private boolean c = true;
   public HashSet<class_2338> a = new HashSet();
   public HashSet<class_2338> b = new HashSet();
   public HashSet<class_2338> c = new HashSet();
   private final PathingBehavior a;
   public final IPlayerContext a;
   public boolean b;

   public PathExecutor(PathingBehavior var1, IPath var2) {
      this.a = var1;
      this.a = var1.a;
      this.a = var2;
      this.a = 0;
   }

   public final boolean a() {
      if (this.a == this.a.length() - 1) {
         ++this.a;
      }

      if (this.a >= this.a.length()) {
         return true;
      } else {
         Movement var1 = (Movement)this.a.movements().get(this.a);
         BetterBlockPos var2 = this.a.playerFeet();
         if (!var1.b().contains(var2)) {
            for(int var3 = 0; var3 < this.a && var3 < this.a.length(); ++var3) {
               if (((Movement)this.a.movements().get(var3)).b().contains(var2)) {
                  int var16 = this.a;
                  this.a = var3;

                  for(int var19 = this.a; var19 <= var16; ++var19) {
                     ((IMovement)this.a.movements().get(var19)).reset();
                  }

                  this.b();
                  this.a();
                  return false;
               }
            }

            for(int var20 = this.a + 3; var20 < this.a.length() - 1; ++var20) {
               if (((Movement)this.a.movements().get(var20)).b().contains(var2)) {
                  if (var20 - this.a > 2) {
                     int var10001 = var20 - this.a;
                     this.logDebug("Skipping forward " + var10001 + " steps, to " + var20);
                  }

                  this.a = var20 - 1;
                  this.b();
                  this.a();
                  return false;
               }
            }
         }

         IPath var4 = this.a;
         PathExecutor var17 = this;
         double var8 = (double)-1.0F;
         BetterBlockPos var5 = null;
         Iterator var26 = var4.movements().iterator();

         while(var26.hasNext()) {
            for(class_2338 var10 : ((Movement)((IMovement)var26.next())).b()) {
               double var11;
               if ((var11 = VecUtils.entityDistanceToCenter(var17.a.player(), var10)) < var8 || var8 == (double)-1.0F) {
                  var8 = var11;
                  var5 = var10;
               }
            }
         }

         class_3545 var21 = new class_3545(var8, var5);
         if (this.a(var21, (double)2.0F)) {
            ++this.b;
            int var59 = this.b;
            System.out.println("FAR AWAY FROM PATH FOR " + var59 + " TICKS. Current distance: " + String.valueOf(var21.method_15442()) + ". Threshold: 2.0");
            if ((double)this.b > (double)200.0F) {
               this.logDebug("Too far away from path for too long, cancelling path");
               this.c();
               return false;
            }
         } else {
            this.b = 0;
         }

         if (this.a(var21, (double)3.0F)) {
            this.logDebug("too far from path");
            this.c();
            return false;
         } else {
            BlockStateInterface var18 = new BlockStateInterface(this.a);

            for(int var22 = this.a - 10; var22 < this.a + 10; ++var22) {
               if (var22 >= 0 && var22 < this.a.movements().size()) {
                  Movement var6;
                  List var44 = (var6 = (Movement)this.a.movements().get(var22)).a(var18);
                  List var27 = var6.b(var18);
                  List var33 = var6.c(var18);
                  var6.resetBlockCache();
                  if (!var44.equals(var6.a(var18))) {
                     this.c = true;
                  }

                  if (!var27.equals(var6.b(var18))) {
                     this.c = true;
                  }

                  if (!var33.equals(var6.c(var18))) {
                     this.c = true;
                  }
               }
            }

            if (this.c) {
               HashSet var23 = new HashSet();
               HashSet var39 = new HashSet();
               HashSet var45 = new HashSet();

               for(int var28 = this.a; var28 < this.a.movements().size(); ++var28) {
                  Movement var34 = (Movement)this.a.movements().get(var28);
                  var23.addAll(var34.a(var18));
                  var39.addAll(var34.b(var18));
                  var45.addAll(var34.c(var18));
               }

               this.a = var23;
               this.b = var39;
               this.c = var45;
               this.c = false;
            }

            if (this.a < this.a.movements().size() - 1) {
               IMovement var24 = (IMovement)this.a.movements().get(this.a + 1);
               if (!this.a.a.a.a(var24.getDest().x, var24.getDest().z)) {
                  this.logDebug("Pausing since destination is at edge of loaded chunks");
                  this.a();
                  return true;
               }
            }

            boolean var25 = var1.safeToCancel();
            if (this.a == null || this.a != this.a) {
               this.a = this.a;
               this.a = var1.getCost();

               for(int var40 = 1; var40 < (Integer)Baritone.a().costVerificationLookahead.value && this.a + var40 < this.a.length() - 1; ++var40) {
                  if (((Movement)this.a.movements().get(this.a + var40)).a(this.a.a) >= (double)1000000.0F && var25) {
                     this.logDebug("Something has changed in the world and a future movement has become impossible. Cancelling.");
                     this.c();
                     return true;
                  }
               }
            }

            CalculationContext var29 = this.a.a;
            var1.a = null;
            if (var1.a == null) {
               var1.a = var1.a(var29);
            }

            double var41;
            if ((var41 = var1.a) >= (double)1000000.0F && var25) {
               this.logDebug("Something has changed in the world and this movement has become impossible. Cancelling.");
               this.c();
               return true;
            } else if (!var1.calculatedWhileLoaded() && var41 - this.a > (Double)Baritone.a().maxCostIncrease.value && var25) {
               this.logDebug("Original cost " + this.a + " current cost " + var41 + ". Cancelling.");
               this.c();
               return true;
            } else {
               List var9;
               Optional var30;
               Optional var47;
               if (!(var30 = this.a.getInProgress()).isPresent() ? false : (!this.a.player().method_24828() ? false : (!MovementHelper.b(this.a, this.a.playerFeet().below()) ? false : (MovementHelper.a(this.a, this.a.playerFeet()) && MovementHelper.a(this.a, this.a.playerFeet().above()) ? (!((IMovement)this.a.movements().get(this.a)).safeToCancel() ? false : (!(var47 = ((AbstractNodeCostSearch)var30.get()).bestPathSoFar()).isPresent() ? false : ((var9 = ((IPath)var47.get()).positions()).size() < 3 ? false : var9.subList(1, var9.size()).contains(this.a.playerFeet())))) : false)))) {
                  this.logDebug("Pausing since current best path is a backtrack");
                  this.a();
                  return true;
               } else {
                  MovementStatus var31;
                  if ((var31 = var1.update()) != MovementStatus.UNREACHABLE && var31 != MovementStatus.FAILED) {
                     if (var31 == MovementStatus.SUCCESS) {
                        ++this.a;
                        this.b();
                        this.a();
                        return true;
                     } else {
                        boolean var62;
                        label441: {
                           boolean var32 = this.a.a.a.isInputForcedDown(Input.SPRINT);
                           this.a.a.a.setInputForceState(Input.SPRINT, false);
                           if ((new CalculationContext(this.a.a, false)).d) {
                              label436: {
                                 IMovement var48;
                                 IMovement var49;
                                 if ((var48 = (IMovement)this.a.movements().get(this.a)) instanceof MovementTraverse && this.a < this.a.length() - 3 && (var49 = (IMovement)this.a.movements().get(this.a + 1)) instanceof MovementAscend && a(this.a, (MovementTraverse)var48, (MovementAscend)var49, (IMovement)this.a.movements().get(this.a + 2))) {
                                    label328: {
                                       IPlayerContext var42 = this.a;
                                       if (!(Math.abs((double)var48.getDirection().method_10263() * ((double)var48.getSrc().z + (double)0.5F - var42.player().method_73189().field_1350)) + Math.abs((double)var48.getDirection().method_10260() * ((double)var48.getSrc().x + (double)0.5F - var42.player().method_73189().field_1352)) > 0.1)) {
                                          class_2338 var12 = var48.getSrc().method_10059(var48.getDirection()).method_10086(2);
                                          if (MovementHelper.a(var42, var12)) {
                                             var62 = true;
                                             break label328;
                                          }

                                          if (Math.abs((double)var48.getDirection().method_10263() * ((double)var12.method_10263() + (double)0.5F - var42.player().method_73189().field_1352)) + Math.abs((double)var48.getDirection().method_10260() * ((double)var12.method_10260() + (double)0.5F - var42.player().method_73189().field_1350)) > 0.8) {
                                             var62 = true;
                                             break label328;
                                          }
                                       }

                                       var62 = false;
                                    }

                                    if (var62) {
                                       this.logDebug("Skipping traverse to straight ascend");
                                       ++this.a;
                                       this.b();
                                       this.a();
                                       this.a.a.a.setInputForceState(Input.JUMP, true);
                                       var62 = true;
                                       break label441;
                                    }

                                    this.logDebug("Too far to the side to safely sprint ascend");
                                 }

                                 if (var32) {
                                    var62 = true;
                                    break label441;
                                 }

                                 if (var48 instanceof MovementDescend) {
                                    if (this.a < this.a.length() - 2) {
                                       var49 = (IMovement)this.a.movements().get(this.a + 1);
                                       if (MovementHelper.c(this.a, var49.getDest().below()) && (var49 instanceof MovementTraverse || var49 instanceof MovementParkour)) {
                                          boolean var35 = (Boolean)Baritone.a().allowPlace.value && this.a.a.a.a() && var49 instanceof MovementParkour;
                                          if (!var48.getDirection().method_10084().method_10081(var49.getDirection()).equals(class_2338.field_10980) && var48.getDirection().method_10084().method_10075(var49.getDirection()).equals(class_2338.field_10980) && !var35) {
                                             ((MovementDescend)var48).a = true;
                                          }
                                       }
                                    }

                                    if (((MovementDescend)var48).b() && !((MovementDescend)var48).c()) {
                                       this.logDebug("Sprinting would be unsafe");
                                       break label436;
                                    }

                                    if (this.a < this.a.length() - 2) {
                                       if ((var49 = (IMovement)this.a.movements().get(this.a + 1)) instanceof MovementAscend && var48.getDirection().method_10084().equals(var49.getDirection().method_10074())) {
                                          ++this.a;
                                          this.b();
                                          this.a();
                                          this.logDebug("Skipping descend to straight ascend");
                                          var62 = true;
                                          break label441;
                                       }

                                       if (a(this.a, var48, var49)) {
                                          IMovement var38;
                                          if (var49 instanceof MovementDescend && this.a < this.a.length() - 3 && (var38 = (IMovement)this.a.movements().get(this.a + 2)) instanceof MovementDescend && !a(this.a, var49, var38)) {
                                             break label436;
                                          }

                                          if (this.a.playerFeet().equals(var48.getDest())) {
                                             ++this.a;
                                             this.b();
                                             this.a();
                                          }

                                          var62 = true;
                                          break label441;
                                       }
                                    }
                                 }

                                 if (var48 instanceof MovementAscend && this.a != 0) {
                                    if ((var49 = (IMovement)this.a.movements().get(this.a - 1)) instanceof MovementDescend && var49.getDirection().method_10084().equals(var48.getDirection().method_10074())) {
                                       var5 = var48.getSrc().above();
                                       if (this.a.player().method_73189().field_1351 >= (double)((class_2338)var5).method_10264() - 0.07) {
                                          this.a.a.a.setInputForceState(Input.JUMP, false);
                                          var62 = true;
                                          break label441;
                                       }
                                    }

                                    if (this.a < this.a.length() - 2 && var49 instanceof MovementTraverse && a(this.a, (MovementTraverse)var49, (MovementAscend)var48, (IMovement)this.a.movements().get(this.a + 1))) {
                                       var62 = true;
                                       break label441;
                                    }
                                 }

                                 if (var48 instanceof MovementFall) {
                                    MovementFall var46 = (MovementFall)var48;
                                    PathExecutor var43 = this;
                                    class_2338 var54;
                                    class_3545 var61;
                                    if ((var54 = ((Movement)var46).getDirection()).method_10264() < -3) {
                                       var61 = null;
                                    } else if (!var46.a.isEmpty()) {
                                       var61 = null;
                                    } else {
                                       class_2382 var55 = new class_2382(((class_2382)var54).method_10263(), 0, ((class_2382)var54).method_10260());

                                       IMovement var13;
                                       int var56;
                                       label280:
                                       for(var56 = this.a + 1; var56 < var43.a.length() - 1 && var56 < var43.a + 3 && (var13 = (IMovement)var43.a.movements().get(var56)) instanceof MovementTraverse && var55.equals(var13.getDirection()); ++var56) {
                                          for(int var14 = var13.getDest().y; var14 <= ((Movement)var46).getSrc().y + 1; ++var14) {
                                             class_2338 var15 = new class_2338(var13.getDest().x, var14, var13.getDest().z);
                                             if (!MovementHelper.a(var43.a, var15)) {
                                                break label280;
                                             }
                                          }

                                          if (!MovementHelper.b(var43.a, var13.getDest().below())) {
                                             break;
                                          }
                                       }

                                       --var56;
                                       if (var56 == var43.a) {
                                          var61 = null;
                                       } else {
                                          double var58 = (double)(var56 - var43.a) - 0.4;
                                          var61 = new class_3545(new class_243((double)var55.method_10263() * var58 + (double)((Movement)var46).getDest().x + (double)0.5F, (double)((Movement)var46).getDest().y, (double)var55.method_10260() * var58 + (double)((Movement)var46).getDest().z + (double)0.5F), ((Movement)var46).getDest().method_10069(var55.method_10263() * (var56 - var43.a), 0, var55.method_10260() * (var56 - var43.a)));
                                       }
                                    }

                                    class_3545 var53 = var61;
                                    if (var61 != null) {
                                       var5 = new BetterBlockPos((class_2338)var53.method_15441());
                                       if (!this.a.positions().contains(var5)) {
                                          throw new IllegalStateException(String.format("Fall override at %s %s %s returned illegal destination %s %s %s", var48.getSrc(), var5));
                                       }

                                       if (this.a.playerFeet().equals(var5)) {
                                          this.a = this.a.positions().indexOf(var5);
                                          this.b();
                                          this.a();
                                          var62 = true;
                                       } else {
                                          this.a();
                                          this.a.a.a.updateTarget(RotationUtils.calcRotationFromVec3d(this.a.playerHead(), (class_243)var53.method_15442(), this.a.playerRotations()), false);
                                          this.a.a.a.setInputForceState(Input.MOVE_FORWARD, true);
                                          var62 = true;
                                       }
                                       break label441;
                                    }
                                 }
                              }
                           }

                           var62 = false;
                        }

                        this.b = var62;
                        if (!this.b) {
                           this.a.player().method_5728(false);
                        }

                        ++this.c;
                        if ((double)this.c > this.a + (double)(Integer)Baritone.a().movementTimeoutTicks.value) {
                           this.logDebug("This movement has taken too long (" + this.c + " ticks, expected " + this.a + "). Cancelling.");
                           this.c();
                           return true;
                        } else {
                           return var25;
                        }
                     }
                  } else {
                     this.logDebug("Movement returns status " + String.valueOf(var31));
                     this.c();
                     return true;
                  }
               }
            }
         }
      }
   }

   private boolean a(class_3545<Double, class_2338> var1, double var2) {
      if ((Double)var1.method_15442() > var2) {
         if (this.a.movements().get(this.a) instanceof MovementFall) {
            class_2338 var4 = (class_2338)this.a.positions().get(this.a + 1);
            return VecUtils.entityFlatDistanceToCenter(this.a.player(), var4) >= var2;
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   private static boolean a(IPlayerContext var0, MovementTraverse var1, MovementAscend var2, IMovement var3) {
      if (!(Boolean)Baritone.a().sprintAscends.value) {
         return false;
      } else if (!((Movement)var1).getDirection().equals(((Movement)var2).getDirection().method_10074())) {
         return false;
      } else if (var3.getDirection().method_10263() == ((Movement)var2).getDirection().method_10263() && var3.getDirection().method_10260() == ((Movement)var2).getDirection().method_10260()) {
         if (!MovementHelper.b(var0, ((Movement)var1).getDest().below())) {
            return false;
         } else if (!MovementHelper.b(var0, ((Movement)var2).getDest().below())) {
            return false;
         } else if (!var2.a.isEmpty()) {
            return false;
         } else {
            for(int var6 = 0; var6 < 2; ++var6) {
               for(int var4 = 0; var4 < 3; ++var4) {
                  Object var5 = ((Movement)var1).getSrc().above(var4);
                  if (var6 == 1) {
                     var5 = ((class_2338)var5).method_10081(((Movement)var1).getDirection());
                  }

                  if (!MovementHelper.a((IPlayerContext)var0, (class_2338)var5)) {
                     return false;
                  }
               }
            }

            if (MovementHelper.b(var0.world().method_8320(((Movement)var1).getSrc().above(3)))) {
               return false;
            } else if (!MovementHelper.b(var0.world().method_8320(((Movement)var2).getDest().above(2)))) {
               return true;
            } else {
               return false;
            }
         }
      } else {
         return false;
      }
   }

   private static boolean a(IPlayerContext var0, IMovement var1, IMovement var2) {
      if (var2 instanceof MovementDescend && var2.getDirection().equals(var1.getDirection())) {
         return true;
      } else if (!MovementHelper.b(var0, var1.getDest().method_10081(var1.getDirection()))) {
         return false;
      } else if (var2 instanceof MovementTraverse && var2.getDirection().equals(var1.getDirection())) {
         return true;
      } else {
         return var2 instanceof MovementDiagonal && (Boolean)Baritone.a().allowOvershootDiagonalDescend.value;
      }
   }

   private void b() {
      this.a();
      this.c = 0;
   }

   public final void a() {
      this.a.a.a.clearAllKeys();
   }

   private void c() {
      this.a();
      this.a.a.a.a.a();
      this.a = this.a.length() + 3;
      this.a = true;
   }

   public int getPosition() {
      return this.a;
   }

   public final PathExecutor a(PathExecutor var1) {
      return var1 == null ? this.a() : (PathExecutor)SplicedPath.a(this.a, var1.a).map((var2) -> {
         if (!var2.getDest().equals(var1.getPath().getDest())) {
            throw new IllegalStateException(String.format("Path has end %s instead of %s after splicing", var2.getDest(), var1.getPath().getDest()));
         } else {
            (var1 = new PathExecutor(this.a, var2)).a = this.a;
            var1.a = this.a;
            var1.a = this.a;
            var1.c = this.c;
            return var1;
         }
      }).orElseGet(this::a);
   }

   private PathExecutor a() {
      if (this.a > (Integer)Baritone.a().maxPathHistoryLength.value) {
         int var1 = (Integer)Baritone.a().pathHistoryCutoffAmount.value;
         IPath var10004 = this.a;
         CutoffPath var2;
         if (!(var2 = new CutoffPath(var10004, var1, var10004.length() - 1)).getDest().equals(this.a.getDest())) {
            throw new IllegalStateException(String.format("Path has end %s instead of %s after trimming its start", var2.getDest(), this.a.getDest()));
         } else {
            int var10001 = this.a.length();
            this.logDebug("Discarding earliest segment movements, length cut from " + var10001 + " to " + var2.length());
            PathExecutor var3;
            (var3 = new PathExecutor(this.a, var2)).a = this.a - var1;
            var3.a = this.a;
            if (this.a != null) {
               var3.a = this.a - var1;
            }

            var3.c = this.c;
            return var3;
         }
      } else {
         return this;
      }
   }

   public IPath getPath() {
      return this.a;
   }

   public final boolean b() {
      return this.a >= this.a.length();
   }
}
