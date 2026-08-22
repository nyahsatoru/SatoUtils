package com.nyahsatoru.satoaddon.modules;

import com.nyahsatoru.satoaddon.b;
import com.nyahsatoru.satoaddon.b.d;
import com.nyahsatoru.satoaddon.core.MiningRegion;
import com.nyahsatoru.satoaddon.core.MiningSession;
import com.nyahsatoru.satoaddon.core.MiningStrategy;
import com.nyahsatoru.satoaddon.core.MiningToolPattern;
import com.nyahsatoru.satoaddon.core.MiningTraversal;
import com.nyahsatoru.satoaddon.core.SatoMineContext;
import com.nyahsatoru.satoaddon.core.SatoMineCoordinator;
import com.nyahsatoru.satoaddon.core.SatoMineTask;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.BlockListSetting;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.EnumSetting;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.FindItemResult;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.meteorclient.utils.player.Rotations;
import meteordevelopment.meteorclient.utils.world.BlockUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1540;
import net.minecraft.class_1747;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_2680;
import net.minecraft.class_3959;
import net.minecraft.class_3965;
import net.minecraft.class_239.class_240;
import net.minecraft.class_3959.class_242;
import net.minecraft.class_3959.class_3960;

public final class SatoMineMining extends Module {
   private static final int n = 256;
   private static final int qb = 1200;
   private static final int l = 20;
   private static final int cb = 3;
   private static final double v = (double)20.25F;
   private static final int zb = 40;
   private static final int x = 3;
   private static final int o = 16;
   private static final int h = 40;
   private static final int jb = 6;
   private static final double lc = 0.04;
   private final SettingGroup bc;
   private final SettingGroup xb;
   private final Setting<List<class_2248>> i;
   private final Setting<MiningStrategy> ub;
   private final Setting<Boolean> d;
   private final Setting<MiningTraversal> ob;
   private final Setting<MiningToolPattern> mb;
   private final Setting<Integer> vb;
   private final Setting<Boolean> kb;
   private final Setting<Boolean> m;
   private final Setting<Boolean> tc;
   private final Setting<List<class_2248>> hc;
   private final Setting<Boolean> w;
   private final SatoMineCoordinator gc;
   private final ArrayDeque<class_2338> dc;
   private final List<class_2338> f;
   private final ArrayDeque<class_2338> ac;
   private final ArrayDeque<class_2338> s;
   private boolean nb;
   private boolean pc;
   private boolean e;
   private long q;
   private int k;
   private int db;
   private int hb;
   private boolean gb;
   private boolean j;
   private class_2338 fc;
   private int ec;
   private boolean r;
   private class_2338 kc;
   private FaceMiningState wb;
   private int sb;
   private int yb;
   private int fb;
   private int ib;
   private int rb;
   private int t;
   private int qc;
   private int tb;
   private int eb;
   private boolean oc;
   private boolean cc;
   private boolean z;
   private boolean lb;
   private boolean uc;
   private class_2338 c;
   private class_2338 ic;
   private class_2338 pb;
   private class_2338 nc;
   private class_243 ab;
   private class_2680 rc;
   private int u;
   private int bb;
   private class_2338 y;
   private class_2680[] jc;
   private boolean b;
   private int p;
   private boolean g;
   private MiningSession.Status sc;

   public SatoMineMining(SatoMineCoordinator coordinator) {
      super(com.nyahsatoru.satoaddon.b.b, "sato-mining", "SatoMine mining orchestration.");
      this.bc = this.settings.getDefaultGroup();
      this.xb = this.settings.createGroup("Area");
      this.i = this.bc.add(((BlockListSetting.Builder)((BlockListSetting.Builder)(new BlockListSetting.Builder()).name("target-blocks")).description("Blocks used by the selected mining strategy.")).defaultValue(new class_2248[]{class_2246.field_10340}).build());
      this.ub = this.bc.add(((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)(new EnumSetting.Builder()).name("strategy")).description("Free mode mines the selected block target automatically.")).defaultValue(MiningStrategy.FREE)).build());
      this.d = this.bc.add(((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("pause")).description("Pause or resume the current mining session.")).defaultValue(false)).build());
      this.ob = this.xb.add(((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)(new EnumSetting.Builder()).name("traversal")).description("Order mode controls the order used to scan selected targets.")).defaultValue(MiningTraversal.ZIG_ZAG)).visible(() -> this.ub.get() == MiningStrategy.AREA)).build());
      this.mb = this.xb.add(((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)(new EnumSetting.Builder()).name("tool-pattern")).description("One-by-one mining or a server tool that breaks one vertical 3x3 face per center block.")).defaultValue(MiningToolPattern.ONE_BY_ONE)).visible(() -> this.ub.get() == MiningStrategy.AREA)).build());
      this.vb = this.xb.add(((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder()).name("settle-ticks")).description("Ticks to wait after a successful 3x3 center break. Lower values mine rows faster; zero is the most aggressive.")).defaultValue(2)).range(0, 10).sliderRange(0, 10).visible(() -> this.ub.get() == MiningStrategy.AREA && this.mb.get() == MiningToolPattern.THREE_BY_THREE)).build());
      this.kb = this.xb.add(((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("continuous-row")).description("Continuous Row follows the planned row and mines blocks automatically.")).defaultValue(false)).visible(() -> this.ub.get() == MiningStrategy.AREA && this.mb.get() == MiningToolPattern.THREE_BY_THREE)).build());
      this.m = this.xb.add(((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("sprint-continuous-row")).description("Allow faster movement while Continuous Row is active.")).defaultValue(false)).visible(() -> this.ub.get() == MiningStrategy.AREA && this.mb.get() == MiningToolPattern.THREE_BY_THREE && (Boolean)this.kb.get())).build());
      this.tc = this.xb.add(((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("place-support-blocks")).description("Let Continuous Row place blocks to bridge holes and climb back onto the exact row axis.")).defaultValue(true)).visible(() -> this.ub.get() == MiningStrategy.AREA && this.mb.get() == MiningToolPattern.THREE_BY_THREE && (Boolean)this.kb.get())).build());
      this.hc = this.xb.add(((BlockListSetting.Builder)((BlockListSetting.Builder)((BlockListSetting.Builder)(new BlockListSetting.Builder()).name("support-blocks")).description("Blocks allowed for bridging and row support.")).defaultValue(new class_2248[]{class_2246.field_10445, class_2246.field_10566, class_2246.field_10515}).visible(() -> this.ub.get() == MiningStrategy.AREA && this.mb.get() == MiningToolPattern.THREE_BY_THREE && (Boolean)this.kb.get() && (Boolean)this.tc.get())).build());
      this.w = this.xb.add(((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("clear-obstacles")).description("Use a hotbar pickaxe to clear a direct obstacle inside the selected region.")).defaultValue(true)).visible(() -> this.ub.get() == MiningStrategy.AREA && this.mb.get() == MiningToolPattern.ONE_BY_ONE)).build());
      this.dc = new ArrayDeque();
      this.f = new ArrayList();
      this.ac = new ArrayDeque();
      this.s = new ArrayDeque();
      this.ec = -1;
      this.wb = SatoMineMining.FaceMiningState.PREPARE;
      this.sc = MiningSession.Status.IDLE;
      this.gc = coordinator;
   }

   public void onActivate() {
      this.nb = true;
      this.pc = false;
      this.d.set(false);
      this.sc = MiningSession.Status.IDLE;
      this.e();
   }

   public void onDeactivate() {
      this.fb();
      this.gc.cancelMining();
      this.nb = false;
      this.d.set(false);
      this.e();
      this.b(MiningSession.Status.IDLE, false);
   }

   @EventHandler
   private void b(TickEvent.Pre event) {
      if (this.mc.field_1724 != null && this.mc.field_1687 != null && this.mc.field_1761 != null) {
         if (!(Boolean)this.kb.get() || !(Boolean)this.tc.get()) {
            this.gc.getNavigationService().d();
         }

         if (this.nb) {
            this.nb = false;
            this.db();
            if (!this.isActive()) {
               return;
            }
         }

         MiningSession.Status status = this.gc.getStatus();
         if (this.gc.getCurrentTask() != SatoMineTask.LOOT && this.gc.getCurrentTask() != SatoMineTask.SELL) {
            if (this.b) {
               this.s();
            }

            if ((Boolean)this.d.get() && status == MiningSession.Status.RUNNING) {
               if (this.gc.pauseMining()) {
                  this.gc.getNavigationService().c();
                  this.gc.getNavigationService().d();
                  this.b(MiningSession.Status.PAUSED, true);
               }

            } else if (!(Boolean)this.d.get() && status == MiningSession.Status.PAUSED) {
               if (this.gc.resumeMining()) {
                  this.b(MiningSession.Status.RUNNING, true);
               }

            } else if (this.gc.getStatus() == MiningSession.Status.RUNNING) {
               if (this.ub.get() == MiningStrategy.AREA) {
                  this.eb();
               }

            }
         } else {
            if (!this.b) {
               this.r();
            }

         }
      } else {
         this.gc.getNavigationService().c();
         this.gc.getNavigationService().d();
         this.fb();
         if (this.gc.getStatus() != MiningSession.Status.IDLE) {
            this.gc.cancelMining();
         }

      }
   }

   public String getInfoString() {
      MiningSession session = this.b();
      if (session == null) {
         return this.gc.getStatus().name().toLowerCase();
      } else if (this.mb.get() == MiningToolPattern.THREE_BY_THREE && session.getRegion() != null) {
         String var3 = session.getStatus().name().toLowerCase();
         return var3 + " 3x3 band " + (this.sb + 1) + " lane " + (this.yb + 1) + " depth " + (this.fb + 1) + " " + this.wb.name().toLowerCase();
      } else if (session.getCurrentTarget() != null) {
         String var2 = session.getStatus().name().toLowerCase();
         return var2 + " " + session.getCurrentTarget().method_23854();
      } else if (session.getRegion() != null) {
         String var10000 = session.getStatus().name().toLowerCase();
         return var10000 + " scan " + session.getScannedBlocks() + "/" + session.getRegion().volume();
      } else {
         return session.getStatus().name().toLowerCase();
      }
   }

   private void db() {
      if (this.ub.get() == MiningStrategy.FREE && ((List)this.i.get()).isEmpty()) {
         this.b("No target blocks configured.");
      } else if (!this.gc.getNavigationService().k().d()) {
         if (!this.pc) {
            this.b("SatoMine path manager is unavailable.");
            this.pc = true;
         }

      } else if (this.ub.get() == MiningStrategy.FREE) {
         if (!this.gc.startMining((class_2248)((List)this.i.get()).get(0))) {
            this.b("Could not start free mining with " + this.gc.getNavigationService().g() + ".");
         } else {
            this.b(MiningSession.Status.RUNNING, true);
            this.info("Strategy: free; target: %s", new Object[]{((class_2248)((List)this.i.get()).get(0)).method_9518().getString()});
         }
      } else {
         Optional<d._b> selection = this.gc.getNavigationService().j();
         if (selection.isEmpty()) {
            this.b("No valid SatoMine Selection found.");
         } else {
            d._b bounds = (d._b)selection.get();
            MiningRegion region = new MiningRegion(bounds.d(), bounds.c());
            if (!this.gc.startAreaSession((List)this.i.get(), region, (MiningTraversal)this.ob.get())) {
               this.b("Could not create Area mining session.");
            } else {
               if (this.mb.get() == MiningToolPattern.THREE_BY_THREE) {
                  if (this.ob.get() != MiningTraversal.ZIG_ZAG) {
                     this.b("3x3 currently requires Zig-Zag traversal.");
                     return;
                  }

                  this.c(region, bounds.e());
               } else {
                  this.b(region);
               }

               this.b(MiningSession.Status.RUNNING, true);
               this.info("Strategy: area; region: %s; traversal: %s; tool: %s; targets: %s", new Object[]{region.describe(), ((MiningTraversal)this.ob.get()).name().toLowerCase(), ((MiningToolPattern)this.mb.get()).name().toLowerCase(), ((List)this.i.get()).isEmpty() ? "all breakable blocks" : Integer.toString(((List)this.i.get()).size())});
            }
         }
      }
   }

   private void eb() {
      MiningSession session = this.b();
      if (session != null && session.getRegion() != null) {
         if (this.mb.get() == MiningToolPattern.THREE_BY_THREE) {
            this.d(session);
         } else {
            class_2338 current = session.getCurrentTarget();
            if (current != null) {
               if (session.getRegion().contains(current) && this.p(current)) {
                  ++this.k;
                  if (this.k > 1200) {
                     this.warning("Target timed out inside region: %s", new Object[]{current.method_23854()});
                     this.b(session, "target timeout");
                  } else if (this.fc != null) {
                     this.b(session, current);
                  } else if (this.mc.field_1724.method_5707(current.method_46558()) > (double)20.25F) {
                     if (!this.gb) {
                        this.kc = this.b(session.getRegion(), current);
                        if (this.gc.navigateTo(this.kc)) {
                           this.gb = true;
                           this.db = 0;
                        } else {
                           ++this.hb;
                           if (this.hb >= 3 && !this.k(current)) {
                              this.b(session, "navigation start failed");
                           }
                        }

                     } else if (this.gc.getNavigationService().e()) {
                        this.db = 0;
                     } else {
                        ++this.db;
                        if (this.db >= 20) {
                           this.db = 0;
                           this.gb = false;
                           this.kc = null;
                           ++this.hb;
                           if (this.hb >= 3 && !this.k(current)) {
                              this.b(session, "navigation stopped before reaching target");
                           }
                        }

                     }
                  } else {
                     this.gc.getNavigationService().h();
                     this.gb = false;
                     this.db = 0;
                     this.hb = 0;
                     BlockUtils.breakBlock(current, true);
                  }
               } else {
                  this.fb();
                  session.setCurrentTarget((class_2338)null);
                  this.k = 0;
               }
            } else {
               this.u();
               class_2338 next = this.w();
               if (next != null) {
                  this.k = 0;
                  this.db = 0;
                  this.hb = 0;
                  this.gb = false;
                  this.b().setCurrentTarget(next);
               } else {
                  if (this.e && this.dc.isEmpty()) {
                     if (!this.j) {
                        if (this.b().getTargetBlocks().isEmpty()) {
                           this.info("No breakable blocks remain inside the selected region.", new Object[0]);
                        } else {
                           this.warning("No target blocks found inside the selected region.", new Object[0]);
                        }

                        this.j = true;
                     }

                     this.gc.completeMining();
                     this.b(MiningSession.Status.COMPLETED, true);
                  }

               }
            }
         }
      } else {
         this.b("Area session has no MiningRegion.");
      }
   }

   private void u() {
      MiningSession session = this.b();
      MiningRegion region = session.getRegion();
      int scanned = 0;

      while(!this.e && scanned < 256) {
         class_2338 pos = this.b(region, (long)(this.q++));
         this.e = this.q >= region.volume();
         ++scanned;
         session.addScannedBlocks(1L);
         if (this.p(pos)) {
            this.dc.addLast(pos);
         }
      }

   }

   private class_2338 b(MiningRegion region, long index) {
      long xLength = (long)region.getMax().method_10263() - (long)region.getMin().method_10263() + 1L;
      long zLength = (long)region.getMax().method_10260() - (long)region.getMin().method_10260() + 1L;
      long layerSize = xLength * zLength;
      long yIndex = index / layerSize;
      long rowIndex = index % layerSize;
      long zIndex = rowIndex / xLength;
      long xIndex = rowIndex % xLength;
      if (this.b().getTraversal() == MiningTraversal.ZIG_ZAG) {
         if ((yIndex & 1L) == 1L) {
            zIndex = zLength - 1L - zIndex;
         }

         if ((yIndex + zIndex & 1L) == 1L) {
            xIndex = xLength - 1L - xIndex;
         }
      }

      return new class_2338(Math.toIntExact((long)region.getMin().method_10263() + xIndex), Math.toIntExact((long)region.getMin().method_10264() + yIndex), Math.toIntExact((long)region.getMin().method_10260() + zIndex));
   }

   private class_2338 w() {
      if (this.dc.isEmpty()) {
         return null;
      } else if (this.b().getTraversal() != MiningTraversal.ZIG_ZAG && this.b().getTraversal() != MiningTraversal.LAYER_BY_LAYER) {
         class_2338 nearest = null;
         double nearestDistance = Double.MAX_VALUE;

         for(class_2338 candidate : this.dc) {
            double distance = this.mc.field_1724.method_5707(candidate.method_46558());
            if (distance < nearestDistance) {
               nearest = candidate;
               nearestDistance = distance;
            }
         }

         this.dc.remove(nearest);
         return nearest;
      } else {
         return (class_2338)this.dc.removeFirst();
      }
   }

   private boolean p(class_2338 pos) {
      MiningSession session = this.b();
      if (session == null) {
         return false;
      } else {
         class_2680 state = this.mc.field_1687.method_8320(pos);
         if (!session.getTargetBlocks().isEmpty()) {
            return session.getTargetBlocks().contains(state.method_26204());
         } else {
            return !state.method_26215() && state.method_26227().method_15769() && BlockUtils.canBreak(pos, state);
         }
      }
   }

   private class_2338 b(MiningRegion region, class_2338 target) {
      class_2338 best = target;
      double bestDistance = this.mc.field_1724.method_5707(target.method_46558());

      for(class_2338 candidate : new class_2338[]{target.method_10095(), target.method_10072(), target.method_10078(), target.method_10067(), target.method_10084(), target.method_10074()}) {
         if (this.mc.field_1687.method_8320(candidate).method_26215() && (region.contains(candidate) || this.b(target, region))) {
            double distance = this.mc.field_1724.method_5707(candidate.method_46558());
            if (distance < bestDistance) {
               best = candidate;
               bestDistance = distance;
            }
         }
      }

      return best;
   }

   private boolean b(class_2338 pos, MiningRegion region) {
      return pos.method_10263() == region.getMin().method_10263() || pos.method_10263() == region.getMax().method_10263() || pos.method_10264() == region.getMin().method_10264() || pos.method_10264() == region.getMax().method_10264() || pos.method_10260() == region.getMin().method_10260() || pos.method_10260() == region.getMax().method_10260();
   }

   private void b(MiningRegion region) {
      this.q = 0L;
      this.e = false;
      this.dc.clear();
      this.j = false;
   }

   private void c(MiningRegion region, class_2338 selectionPos2) {
      this.wb = SatoMineMining.FaceMiningState.PREPARE;
      this.sb = 0;
      this.yb = 0;
      this.fb = 0;
      this.oc = selectionPos2.method_10263() == region.getMax().method_10263();
      this.cc = selectionPos2.method_10260() == region.getMin().method_10260();
      this.f.clear();
      this.i();
   }

   private void d(MiningSession session) {
      MiningRegion region = session.getRegion();
      switch (this.wb.ordinal()) {
         case 0:
            this.b(session, region);
            break;
         case 1:
            this.m();
            break;
         case 2:
            this.h(session);
            break;
         case 3:
            this.c(session);
            break;
         case 4:
            this.f(session);
            break;
         case 5:
            this.j(session);
            break;
         case 6:
            this.t();
            break;
         case 7:
            this.b(session);
            break;
         case 8:
            this.e(session);
            break;
         case 9:
            this.gc.getNavigationService().d();
            this.gc.completeMining();
            this.b(MiningSession.Status.COMPLETED, true);
      }

   }

   private void b(MiningSession session, MiningRegion region) {
      int xLength = region.getMax().method_10263() - region.getMin().method_10263() + 1;
      int laneCount = (xLength + 2) / 3;
      int yLength = region.getMax().method_10264() - region.getMin().method_10264() + 1;
      int bandCount = (yLength + 2) / 3;
      int depthCount = region.getMax().method_10260() - region.getMin().method_10260() + 1;
      if (this.sb >= bandCount) {
         this.wb = SatoMineMining.FaceMiningState.COMPLETE;
      } else {
         if (this.f.isEmpty()) {
            this.f(region, depthCount);
         }

         if (this.fb >= this.f.size()) {
            this.b("3x3 planner produced an invalid row target index.");
         } else {
            this.c = (class_2338)this.f.get(this.fb);
            if (this.fb == 0 && this.pb != null) {
               this.ic = this.pb;
               this.pb = null;
            } else {
               this.ic = this.f(this.c);
            }

            session.setCurrentTarget(this.c);
            this.i();
            if (this.fb < depthCount && this.yb < laneCount) {
               if (!this.p(this.c)) {
                  this.k(session);
               } else {
                  this.wb = SatoMineMining.FaceMiningState.MOVE_TO_FACE;
               }
            } else {
               this.b("3x3 planner produced an invalid face index.");
            }
         }
      }
   }

   private void j(MiningSession session) {
      if (!this.p(this.c)) {
         if ((Boolean)this.kb.get() && this.c(this.c)) {
            this.gc.getNavigationService().h();
            this.z = false;
            this.bb = 6;
            this.ab();
         } else {
            this.k(session);
         }
      } else if ((Boolean)this.kb.get()) {
         this.i(session);
      } else {
         if (this.z) {
            this.gc.getNavigationService().h();
            this.v();
         }

         if (this.n(this.ic)) {
            this.gc.getNavigationService().h();
            this.gb = false;
            this.db = 0;
            this.hb = 0;
            this.wb = SatoMineMining.FaceMiningState.ROTATE;
         } else if (!this.gb) {
            if (this.gc.navigateTo(this.ic)) {
               this.gb = true;
               this.db = 0;
            } else if (++this.hb >= 3) {
               this.b("Could not reach the required 3x3 face approach at " + this.ic.method_23854() + ".");
            }

         } else if (this.gc.getNavigationService().e()) {
            this.db = 0;
         } else {
            if (++this.db >= 20) {
               this.db = 0;
               this.gb = false;
               if (++this.hb >= 3) {
                  this.b("Path stopped before the required 3x3 face approach at " + this.ic.method_23854() + ".");
               }
            }

         }
      }
   }

   private void i(MiningSession session) {
      if (this.z) {
         this.z();
      }

      if (this.nc == null) {
         this.nc = this.d();
      }

      if (this.g(this.nc)) {
         this.gc.getNavigationService().h();
         this.v();
         this.k(session);
      } else if (this.bb > 0) {
         --this.bb;
      } else {
         if (this.z) {
            if (this.gc.getNavigationService().e()) {
               this.tb = 0;
               if (!this.bb()) {
                  this.gc.getNavigationService().h();
                  this.z = false;
                  this.bb = 6;
                  this.ab();
               }

               return;
            }

            if (++this.tb < 20) {
               return;
            }

            this.tb = 0;
            this.z = false;
            if (++this.eb >= 3) {
               this.b("Continuous 3x3 row path stopped before goal " + this.nc.method_23854() + ".");
               return;
            }
         }

         this.gc.getNavigationService().h();
         this.x();
         if (!this.o(this.nc)) {
            this.b(this.nc);
            this.z();
            if (!this.gc.navigateToExact(this.nc)) {
               if (++this.eb >= 3) {
                  this.b("Could not start continuous 3x3 row goal " + this.nc.method_23854() + ".");
               }

            } else {
               session.setCurrentTarget(this.c);
               this.z = true;
               this.tb = 0;
               this.ab();
            }
         }
      }
   }

   private class_2338 d() {
      return this.c;
   }

   private void m() {
      class_2338 waypoint = (class_2338)this.ac.peekFirst();
      if (waypoint == null) {
         if (!this.s.isEmpty()) {
            this.jb();
         } else {
            this.wb = SatoMineMining.FaceMiningState.PREPARE;
         }

      } else {
         boolean reachedWaypoint = (Boolean)this.kb.get() ? this.g(waypoint) : this.n(waypoint);
         if (reachedWaypoint) {
            this.gc.getNavigationService().h();
            this.ac.removeFirst();
            this.x();
            if (this.ac.isEmpty()) {
               if (!this.s.isEmpty()) {
                  this.jb();
               } else {
                  this.wb = SatoMineMining.FaceMiningState.PREPARE;
               }
            }

         } else if (!this.gb) {
            if ((Boolean)this.kb.get()) {
               if (this.o(waypoint)) {
                  return;
               }

               this.b(waypoint);
            }

            boolean pathStarted = (Boolean)this.kb.get() ? this.gc.navigateToExact(waypoint) : this.gc.navigateTo(waypoint);
            if (pathStarted) {
               this.gb = true;
               this.db = 0;
            } else if (++this.hb >= 3) {
               this.b("Could not follow the required 3x3 row transition through " + waypoint.method_23854() + ".");
            }

         } else if (this.gc.getNavigationService().e()) {
            this.db = 0;
         } else {
            if (++this.db >= 20) {
               this.db = 0;
               this.gb = false;
               if (++this.hb >= 3) {
                  this.b("Path stopped during the required 3x3 row transition through " + waypoint.method_23854() + ".");
               }
            }

         }
      }
   }

   private void jb() {
      this.c = (class_2338)this.s.peekFirst();
      if (this.c == null) {
         this.gb();
      } else {
         this.i();
         if (!this.p(this.c)) {
            this.s.removeFirst();
            this.jb();
         } else {
            this.wb = SatoMineMining.FaceMiningState.TURN_ROTATE;
         }
      }
   }

   private void h(MiningSession session) {
      if (!this.p(this.c)) {
         this.ib();
      } else if (this.lb) {
         this.lb = false;
         this.uc = false;
         this.jc = this.e(this.c);
         this.ib = 0;
         session.setCurrentTarget(this.c);
         this.wb = SatoMineMining.FaceMiningState.TURN_BREAK;
      } else {
         if (!this.uc) {
            this.uc = true;
            this.b((class_2338)this.c, (Runnable)(() -> this.lb = true));
         }

      }
   }

   private void c(MiningSession session) {
      if (!this.b(this.c, this.jc) && this.p(this.c)) {
         if (++this.ib > 40) {
            if (++this.t >= 3) {
               this.b("3x3 row connector did not update at " + this.c.method_23854() + ".");
               return;
            }

            this.jc = this.e(this.c);
            this.ib = 0;
         }

         this.l(this.c);
         this.b((class_2338)this.c, (Runnable)null);
         BlockUtils.breakBlock(this.c, true);
         session.setCurrentTarget(this.c);
      } else {
         this.rb = (Integer)this.vb.get();
         this.wb = SatoMineMining.FaceMiningState.TURN_SETTLE;
      }
   }

   private void f(MiningSession session) {
      if (this.rb-- <= 0) {
         if (this.p(this.c)) {
            if (++this.qc > 16) {
               this.b("3x3 row connector kept refilling at " + this.c.method_23854() + ".");
            } else {
               this.jc = this.e(this.c);
               this.ib = 0;
               this.t = 0;
               this.wb = SatoMineMining.FaceMiningState.TURN_BREAK;
            }
         } else {
            this.ib();
         }
      }
   }

   private void ib() {
      if (!this.s.isEmpty()) {
         this.s.removeFirst();
      }

      this.c = null;
      this.jc = null;
      if (!this.s.isEmpty()) {
         this.jb();
      } else {
         this.gb();
      }

   }

   private void gb() {
      this.i();
      if (this.y != null) {
         this.ac.addLast(this.y);
         this.y = null;
         this.wb = SatoMineMining.FaceMiningState.MOVE_TRANSITION;
      } else {
         this.wb = SatoMineMining.FaceMiningState.PREPARE;
      }

   }

   private void t() {
      if (!this.p(this.c)) {
         this.wb = SatoMineMining.FaceMiningState.PREPARE;
      } else if (this.lb) {
         this.lb = false;
         this.uc = false;
         this.jc = this.d(this.c);
         this.ib = 0;
         this.wb = SatoMineMining.FaceMiningState.BREAK_FACE;
      } else {
         if (!this.uc) {
            this.uc = true;
            this.b((class_2338)this.c, (Runnable)(() -> this.lb = true));
         }

      }
   }

   private void b(MiningSession session) {
      if (!this.c(this.c, this.jc) && this.p(this.c)) {
         if (++this.ib > 40) {
            if (++this.t >= 3) {
               this.b("3x3 face did not update at " + this.c.method_23854() + ".");
               return;
            }

            this.jc = this.d(this.c);
            this.ib = 0;
         }

         this.l(this.c);
         this.b((class_2338)this.c, (Runnable)null);
         BlockUtils.breakBlock(this.c, true);
         session.setCurrentTarget(this.c);
      } else {
         this.rb = (Integer)this.vb.get();
         this.wb = SatoMineMining.FaceMiningState.WAIT_SETTLE;
      }
   }

   private void e(MiningSession session) {
      if (this.rb-- <= 0) {
         if (this.p(this.c)) {
            if (++this.qc > 16) {
               this.b("3x3 face kept refilling after gravity settled at " + this.c.method_23854() + ".");
            } else {
               this.jc = this.d(this.c);
               this.ib = 0;
               this.t = 0;
               this.wb = SatoMineMining.FaceMiningState.BREAK_FACE;
            }
         } else if ((Boolean)this.kb.get() && this.nc != null && !this.g(this.nc)) {
            if (!this.j(this.nc)) {
               if (!this.o(this.nc)) {
                  if (this.b(this.nc)) {
                     this.gc.getNavigationService().h();
                     this.z = this.gc.navigateToExact(this.nc);
                     this.tb = 0;
                     if (!this.z && ++this.eb >= 3) {
                        this.g(session);
                     }

                  } else {
                     this.g(session);
                  }
               }
            } else if (this.gc.getNavigationService().e()) {
               this.tb = 0;
            } else if (++this.tb >= 20) {
               this.tb = 0;
               this.z = false;
               if (++this.eb >= 3) {
                  this.b("Continuous 3x3 row step stopped before reaching " + this.nc.method_23854() + ".");
               } else {
                  if (this.gc.navigateToExact(this.nc)) {
                     this.z = true;
                  }

               }
            }
         } else {
            this.k(session);
         }
      }
   }

   private void g(MiningSession session) {
      MiningRegion region = session.getRegion();
      int depthCount = region.getMax().method_10260() - region.getMin().method_10260() + 1;
      this.warning("No configured support block can bridge %s; asking SatoMine to find a fallback route.", new Object[]{this.nc.method_23854()});
      this.gc.getNavigationService().h();
      this.gc.getNavigationService().d();
      this.v();
      boolean hasNextCenter = this.fb + 1 < depthCount;
      class_2338 nextCenter = hasNextCenter ? (class_2338)this.f.get(this.fb + 1) : null;
      this.k(session);
      if (nextCenter != null) {
         this.b(nextCenter, "continuous row hole");
         this.wb = SatoMineMining.FaceMiningState.MOVE_TRANSITION;
      }
   }

   private void k(MiningSession session) {
      MiningRegion region = session.getRegion();
      int depthCount = region.getMax().method_10260() - region.getMin().method_10260() + 1;
      int xLength = region.getMax().method_10263() - region.getMin().method_10263() + 1;
      int laneCount = (xLength + 2) / 3;
      int yLength = region.getMax().method_10264() - region.getMin().method_10264() + 1;
      int bandCount = (yLength + 2) / 3;
      boolean completedLane = this.fb + 1 >= depthCount;
      boolean completedBand = completedLane && this.yb + 1 >= laneCount;
      boolean completedLaneForward = this.cb();
      class_2338 completedFaceCenter = this.c;
      if (this.z) {
         this.gc.getNavigationService().h();
      }

      if ((Boolean)this.kb.get()) {
         this.v();
      }

      ++this.fb;
      if (this.fb >= depthCount) {
         this.fb = 0;
         ++this.yb;
         this.f.clear();
         if (this.yb >= laneCount) {
            this.yb = 0;
            ++this.sb;
            this.oc = completedFaceCenter.method_10263() > region.getMin().method_10263() + (xLength - 1) / 2;
            this.cc = completedFaceCenter.method_10260() == region.getMin().method_10260();
         }
      }

      this.ac.clear();
      this.s.clear();
      this.y = null;
      if (completedLane && !completedBand) {
         int feetY = completedFaceCenter.method_10264() - 1;
         int transitionZ = completedFaceCenter.method_10260() + (completedLaneForward ? 1 : -1);
         class_2338 transitionWaypoint = new class_2338(completedFaceCenter.method_10263(), feetY, transitionZ);
         class_2338 nextRowCenter = new class_2338(this.b(region, this.yb), completedFaceCenter.method_10264(), this.cb() ? region.getMin().method_10260() : region.getMax().method_10260());
         if ((Boolean)this.kb.get()) {
            class_2338 crossLaneWaypoint = new class_2338(nextRowCenter.method_10263(), feetY, transitionZ);
            class_2338 nextRowApproach = this.f(nextRowCenter);
            if (this.i(transitionWaypoint) && this.i(crossLaneWaypoint) && this.i(nextRowApproach)) {
               this.ac.addLast(transitionWaypoint);
               this.ac.addLast(crossLaneWaypoint);
               this.ac.addLast(nextRowApproach);
            } else {
               this.b(nextRowCenter, "continuous U-turn");
            }
         } else if (this.i(transitionWaypoint)) {
            int nextLaneMinX = this.c(region, this.yb);
            int nextLaneMaxX = this.e(region, this.yb);
            this.ac.addLast(transitionWaypoint);

            for(int x = nextLaneMinX; x <= nextLaneMaxX; ++x) {
               this.s.addLast(new class_2338(x, completedFaceCenter.method_10264(), transitionZ));
            }

            this.y = new class_2338(nextRowCenter.method_10263(), feetY, transitionZ);
         } else {
            this.b(nextRowCenter, "row transition");
         }
      } else if (completedBand && this.sb < bandCount) {
         int nextCenterY = this.d(region, this.sb);
         int nextStartZ = this.cc ? region.getMin().method_10260() : region.getMax().method_10260();
         class_2338 nextBandStart = new class_2338(this.b((MiningRegion)region, 0), nextCenterY, nextStartZ);
         class_2338 preferredApproach = this.f(nextBandStart);
         if (this.i(preferredApproach)) {
            this.ac.addLast(preferredApproach);
         } else {
            this.b(nextBandStart, "band transition");
         }
      }

      session.setCurrentTarget((class_2338)null);
      this.c = null;
      this.ic = null;
      this.jc = null;
      this.i();
      if (!completedLane && !this.f.isEmpty()) {
         this.c = (class_2338)this.f.get(this.fb);
         this.ic = this.f(this.c);
         session.setCurrentTarget(this.c);
         this.wb = this.p(this.c) ? SatoMineMining.FaceMiningState.MOVE_TO_FACE : SatoMineMining.FaceMiningState.PREPARE;
      } else {
         this.wb = this.ac.isEmpty() ? SatoMineMining.FaceMiningState.PREPARE : SatoMineMining.FaceMiningState.MOVE_TRANSITION;
      }

   }

   private boolean cb() {
      return (this.yb & 1) == 0 ? this.cc : !this.cc;
   }

   private void f(MiningRegion region, int depthCount) {
      boolean forward = this.cb();
      int centerX = this.b(region, this.yb);
      int centerY = this.d(region, this.sb);

      for(int depth = 0; depth < depthCount; ++depth) {
         int centerZ = forward ? region.getMin().method_10260() + depth : region.getMax().method_10260() - depth;
         this.f.add(new class_2338(centerX, centerY, centerZ));
      }

   }

   private int d(MiningRegion region, int band) {
      int bandMaxY = region.getMax().method_10264() - band * 3;
      int remainingHeight = bandMaxY - region.getMin().method_10264() + 1;
      return bandMaxY - (remainingHeight >= 3 ? 1 : 0);
   }

   private class_2338 f(class_2338 center) {
      return this.b(center, center.method_10260() + (this.cb() ? -2 : 2));
   }

   private class_2338 b(class_2338 rowCenter, int z) {
      return new class_2338(rowCenter.method_10263(), rowCenter.method_10264() - 1, z);
   }

   private void b(class_2338 nextCenter, String transitionName) {
      class_2338 preferred = this.f(nextCenter);
      class_2338 safeGoal = this.c(nextCenter, preferred);
      if (safeGoal == null) {
         this.warning("No safe standing position found near %s; SatoMine will try the preferred %s goal.", new Object[]{nextCenter.method_23854(), transitionName});
         safeGoal = preferred;
      } else {
         this.warning("Hole detected during %s; asking SatoMine to recover through %s.", new Object[]{transitionName, safeGoal.method_23854()});
      }

      this.ac.addLast(safeGoal);
      this.pb = safeGoal;
   }

   private class_2338 c(class_2338 center, class_2338 preferred) {
      class_2338 best = null;
      int bestScore = Integer.MAX_VALUE;

      for(int yOffset = -2; yOffset <= 2; ++yOffset) {
         for(int xOffset = -4; xOffset <= 4; ++xOffset) {
            for(int zOffset = -4; zOffset <= 4; ++zOffset) {
               class_2338 candidate = preferred.method_10069(xOffset, yOffset, zOffset);
               if (this.m(candidate) && this.b(center, candidate)) {
                  int score = Math.abs(xOffset) + Math.abs(zOffset) + Math.abs(yOffset) * 3;
                  if (score < bestScore) {
                     best = candidate;
                     bestScore = score;
                  }
               }
            }
         }
      }

      return best;
   }

   private boolean m(class_2338 feet) {
      return this.mc.field_1687.method_8320(feet).method_26215() && this.mc.field_1687.method_8320(feet.method_10084()).method_26215() ? this.j(feet) : false;
   }

   private boolean j(class_2338 feet) {
      class_2338 floor = feet.method_10074();
      class_2680 floorState = this.mc.field_1687.method_8320(floor);
      return floorState.method_26227().method_15769() && floorState.method_26212(this.mc.field_1687, floor);
   }

   private boolean o(class_2338 feet) {
      if (feet != null && !this.j(feet) && (Boolean)this.tc.get()) {
         if (this.p > 0) {
            --this.p;
            return true;
         } else {
            FindItemResult support = this.hb();
            if (!support.found()) {
               return false;
            } else {
               class_2338 floor = feet.method_10074();
               if (!(this.mc.field_1724.method_5707(floor.method_46558()) > (double)20.25F) && BlockUtils.canPlace(floor, true)) {
                  if (!BlockUtils.place(floor, support, true, 100, true, true)) {
                     return false;
                  } else {
                     this.p = 2;
                     return true;
                  }
               } else {
                  return false;
               }
            }
         }
      } else {
         this.p = 0;
         return false;
      }
   }

   private boolean b(class_2338 feet) {
      if (feet != null && !this.j(feet) && (Boolean)this.tc.get()) {
         FindItemResult support = this.hb();
         if (!support.found()) {
            if (!this.g) {
               this.warning("Continuous Row needs a support block in the hotbar to bridge or climb onto %s.", new Object[]{feet.method_23854()});
               this.g = true;
            }

            this.gc.getNavigationService().d();
            return false;
         } else {
            this.g = false;
            return this.gc.getNavigationService().b((List)this.hc.get());
         }
      } else {
         return false;
      }
   }

   private FindItemResult hb() {
      return InvUtils.findInHotbar((stack) -> {
         class_1792 patt0$temp = stack.method_7909();
         if (patt0$temp instanceof class_1747 blockItem) {
            return ((List)this.hc.get()).contains(blockItem.method_7711());
         } else {
            return false;
         }
      });
   }

   private boolean i(class_2338 feet) {
      class_2338 floor = feet.method_10074();
      class_2680 floorState = this.mc.field_1687.method_8320(floor);
      boolean supported = floorState.method_26227().method_15769() && floorState.method_26212(this.mc.field_1687, floor);
      boolean canBuildSupport = (Boolean)this.tc.get() && floorState.method_26227().method_15769() && floorState.method_45474() && this.hb().found();
      if (!supported && !canBuildSupport) {
         return false;
      } else {
         class_2680 feetState = this.mc.field_1687.method_8320(feet);
         class_2680 headState = this.mc.field_1687.method_8320(feet.method_10084());
         return (feetState.method_26215() || BlockUtils.canBreak(feet)) && (headState.method_26215() || BlockUtils.canBreak(feet.method_10084()));
      }
   }

   private boolean b(class_2338 center, class_2338 feet) {
      double eyeX = (double)feet.method_10263() + (double)0.5F;
      double eyeY = (double)((float)feet.method_10264() + this.mc.field_1724.method_5751());
      double eyeZ = (double)feet.method_10260() + (double)0.5F;
      return center.method_46558().method_1028(eyeX, eyeY, eyeZ) <= (double)20.25F;
   }

   private boolean h(class_2338 center) {
      if (this.mc.field_1724.method_5707(center.method_46558()) > (double)20.25F) {
         return false;
      } else {
         class_3965 hit = this.mc.field_1687.method_17742(new class_3959(this.mc.field_1724.method_33571(), center.method_46558(), class_3960.field_17559, class_242.field_1348, this.mc.field_1724));
         return hit.method_17783() == class_240.field_1332 && hit.method_17777().equals(center);
      }
   }

   private void v() {
      this.gc.getNavigationService().c();
      this.z = false;
      this.nc = null;
      this.tb = 0;
      this.eb = 0;
      this.bb = 0;
      this.ab();
      this.p = 0;
   }

   private void z() {
      if ((Boolean)this.m.get()) {
         this.gc.getNavigationService().i();
      } else {
         this.gc.getNavigationService().c();
      }

   }

   private void b(class_2338 center, Runnable callback) {
      double yaw = Rotations.getYaw(center);
      double pitch = Rotations.getPitch(center);
      this.mc.field_1724.method_36456((float)yaw);
      this.mc.field_1724.method_36457((float)pitch);
      this.mc.field_1724.method_5847((float)yaw);
      Rotations.rotate(yaw, pitch, 100, callback);
   }

   private void l(class_2338 center) {
      int slot = InvUtils.findFastestTool(this.mc.field_1687.method_8320(center)).slot();
      if (slot >= 0 && slot != this.mc.field_1724.method_31548().method_67532()) {
         InvUtils.swap(slot, false);
      }

   }

   private int b(MiningRegion region, int lane) {
      int laneMinX = this.c(region, lane);
      int laneMaxX = this.e(region, lane);
      int width = laneMaxX - laneMinX + 1;
      return this.oc ? laneMaxX - (width >= 3 ? 1 : 0) : laneMinX + (width >= 3 ? 1 : 0);
   }

   private int c(MiningRegion region, int lane) {
      return this.oc ? Math.max(region.getMin().method_10263(), region.getMax().method_10263() - lane * 3 - 2) : region.getMin().method_10263() + lane * 3;
   }

   private int e(MiningRegion region, int lane) {
      return this.oc ? region.getMax().method_10263() - lane * 3 : Math.min(region.getMax().method_10263(), region.getMin().method_10263() + lane * 3 + 2);
   }

   private boolean n(class_2338 goal) {
      class_2338 playerPos = this.mc.field_1724.method_24515();
      return Math.abs(playerPos.method_10263() - goal.method_10263()) <= 1 && Math.abs(playerPos.method_10264() - goal.method_10264()) <= 1 && Math.abs(playerPos.method_10260() - goal.method_10260()) <= 1;
   }

   private boolean g(class_2338 goal) {
      return goal != null && this.mc.field_1724.method_24515().equals(goal);
   }

   private boolean bb() {
      class_243 currentPosition = this.c();
      class_2680 currentCenterState = this.mc.field_1687.method_8320(this.c);
      boolean moved = this.ab == null || currentPosition.method_1025(this.ab) >= 0.04;
      boolean centerUpdated = this.rc == null || !currentCenterState.equals(this.rc);
      if (!moved && !centerUpdated) {
         return ++this.u < 40;
      } else {
         this.ab = currentPosition;
         this.rc = currentCenterState;
         this.u = 0;
         return true;
      }
   }

   private boolean c(class_2338 center) {
      class_238 gravityColumn = new class_238((double)(center.method_10263() - 1), (double)(center.method_10264() - 1), (double)(center.method_10260() - 1), (double)(center.method_10263() + 2), (double)(center.method_10264() + 8), (double)(center.method_10260() + 2));
      return !this.mc.field_1687.method_8390(class_1540.class, gravityColumn, (entity) -> true).isEmpty();
   }

   private void ab() {
      this.ab = this.mc.field_1724 == null ? null : this.c();
      this.rc = this.mc.field_1687 != null && this.c != null ? this.mc.field_1687.method_8320(this.c) : null;
      this.u = 0;
   }

   private class_243 c() {
      return new class_243(this.mc.field_1724.method_23317(), this.mc.field_1724.method_23318(), this.mc.field_1724.method_23321());
   }

   private class_2680[] d(class_2338 center) {
      class_2680[] snapshot = new class_2680[9];
      int index = 0;

      for(int y = center.method_10264() - 1; y <= center.method_10264() + 1; ++y) {
         for(int x = center.method_10263() - 1; x <= center.method_10263() + 1; ++x) {
            snapshot[index++] = this.mc.field_1687.method_8320(new class_2338(x, y, center.method_10260()));
         }
      }

      return snapshot;
   }

   private class_2680[] e(class_2338 center) {
      class_2680[] snapshot = new class_2680[9];
      int index = 0;

      for(int y = center.method_10264() - 1; y <= center.method_10264() + 1; ++y) {
         for(int z = center.method_10260() - 1; z <= center.method_10260() + 1; ++z) {
            snapshot[index++] = this.mc.field_1687.method_8320(new class_2338(center.method_10263(), y, z));
         }
      }

      return snapshot;
   }

   private boolean c(class_2338 center, class_2680[] snapshot) {
      if (snapshot == null) {
         return false;
      } else {
         int index = 0;

         for(int y = center.method_10264() - 1; y <= center.method_10264() + 1; ++y) {
            for(int x = center.method_10263() - 1; x <= center.method_10263() + 1; ++x) {
               if (!this.mc.field_1687.method_8320(new class_2338(x, y, center.method_10260())).equals(snapshot[index++])) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   private boolean b(class_2338 center, class_2680[] snapshot) {
      if (snapshot == null) {
         return false;
      } else {
         int index = 0;

         for(int y = center.method_10264() - 1; y <= center.method_10264() + 1; ++y) {
            for(int z = center.method_10260() - 1; z <= center.method_10260() + 1; ++z) {
               if (!this.mc.field_1687.method_8320(new class_2338(center.method_10263(), y, z)).equals(snapshot[index++])) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   private void i() {
      this.ib = 0;
      this.rb = 0;
      this.t = 0;
      this.qc = 0;
      this.lb = false;
      this.uc = false;
      this.x();
   }

   private void x() {
      this.gb = false;
      this.kc = null;
      this.db = 0;
      this.hb = 0;
   }

   private MiningSession b() {
      SatoMineContext context = this.gc.getContext();
      return context.getMiningSession();
   }

   private void r() {
      this.b = true;
      this.fb();
      this.n();
      this.x();
      this.lb = false;
      this.uc = false;
   }

   private void s() {
      this.b = false;
      this.x();
      this.z = false;
      this.tb = this.nc == null ? 0 : 19;
      this.eb = 0;
      this.lb = false;
      this.uc = false;
   }

   private void n() {
      this.gc.getNavigationService().c();
      this.gc.getNavigationService().d();
      this.z = false;
      this.tb = 0;
      this.eb = 0;
   }

   private void e() {
      this.gc.getNavigationService().d();
      this.dc.clear();
      this.e = false;
      this.q = 0L;
      this.k = 0;
      this.db = 0;
      this.hb = 0;
      this.gb = false;
      this.j = false;
      this.wb = SatoMineMining.FaceMiningState.PREPARE;
      this.sb = 0;
      this.yb = 0;
      this.fb = 0;
      this.oc = false;
      this.cc = true;
      this.v();
      this.c = null;
      this.ic = null;
      this.pb = null;
      this.jc = null;
      this.f.clear();
      this.ac.clear();
      this.s.clear();
      this.y = null;
      this.b = false;
      this.p = 0;
      this.g = false;
      this.i();
      this.fb();
   }

   private void b(MiningSession session, String reason) {
      this.warning("Skipping target %s: %s", new Object[]{session.getCurrentTarget().method_23854(), reason});
      this.fb();
      this.gc.getNavigationService().h();
      session.setCurrentTarget((class_2338)null);
      this.k = 0;
      this.db = 0;
      this.hb = 0;
      this.gb = false;
   }

   private void b(String message) {
      this.error(message, new Object[0]);
      this.fb();
      this.gc.cancelMining();
      this.e();
   }

   private boolean k(class_2338 target) {
      if ((Boolean)this.w.get() && this.fc == null && this.mc.field_1724 != null && this.mc.field_1687 != null) {
         MiningSession session = this.b();
         if (session != null && session.getRegion() != null) {
            class_3965 hit = this.mc.field_1687.method_17742(new class_3959(this.mc.field_1724.method_33571(), target.method_46558(), class_3960.field_17559, class_242.field_1348, this.mc.field_1724));
            if (hit.method_17783() != class_240.field_1332) {
               return false;
            } else {
               class_2338 blocked = hit.method_17777();
               if (!blocked.equals(target) && session.getRegion().contains(blocked) && !this.p(blocked) && BlockUtils.canBreak(blocked)) {
                  int pickaxeSlot = this.b(this.mc.field_1687.method_8320(blocked));
                  if (pickaxeSlot < 0) {
                     this.warning("Obstacle %s blocks target, but no pickaxe is available in the hotbar.", new Object[]{blocked.method_23854()});
                     return false;
                  } else {
                     this.ec = this.mc.field_1724.method_31548().method_67532();
                     InvUtils.swap(pickaxeSlot, false);
                     this.fc = blocked.method_10062();
                     this.r = true;
                     this.gb = false;
                     this.db = 0;
                     this.hb = 0;
                     this.info("Clearing obstacle %s with pickaxe.", new Object[]{blocked.method_23854()});
                     return true;
                  }
               } else {
                  return false;
               }
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private void b(MiningSession session, class_2338 originalTarget) {
      if (this.fc != null) {
         if (session.getRegion().contains(this.fc) && !this.p(this.fc) && !this.mc.field_1687.method_8320(this.fc).method_26215()) {
            if (this.mc.field_1724.method_5707(this.fc.method_46558()) > (double)20.25F) {
               if (!this.gb) {
                  if (this.gc.navigateTo(this.fc)) {
                     this.gb = true;
                     this.db = 0;
                     return;
                  }

                  ++this.hb;
               } else {
                  if (this.gc.getNavigationService().e()) {
                     this.db = 0;
                     return;
                  }

                  ++this.db;
                  if (this.db < 20) {
                     return;
                  }

                  this.db = 0;
                  this.gb = false;
                  ++this.hb;
               }

               if (this.hb >= 3) {
                  this.warning("Could not reach obstacle %s; skipping target %s.", new Object[]{this.fc.method_23854(), originalTarget.method_23854()});
                  this.fb();
                  this.b(session, "obstacle unreachable");
               }

            } else {
               this.gc.getNavigationService().h();
               this.gb = false;
               this.db = 0;
               BlockUtils.breakBlock(this.fc, true);
            }
         } else {
            this.fb();
         }
      }
   }

   private int b(class_2680 state) {
      int bestSlot = -1;
      float bestSpeed = 1.0F;

      for(int slot = 0; slot < 9; ++slot) {
         class_1799 stack = this.mc.field_1724.method_31548().method_5438(slot);
         if (this.b(stack)) {
            float speed = stack.method_7924(state);
            if (speed > bestSpeed) {
               bestSpeed = speed;
               bestSlot = slot;
            }
         }
      }

      return bestSlot;
   }

   private boolean b(class_1799 stack) {
      return stack.method_31574(class_1802.field_8647) || stack.method_31574(class_1802.field_8387) || stack.method_31574(class_1802.field_8403) || stack.method_31574(class_1802.field_8335) || stack.method_31574(class_1802.field_8377) || stack.method_31574(class_1802.field_22024);
   }

   private void fb() {
      this.kb();
      this.fc = null;
      this.ec = -1;
      this.gb = false;
      this.db = 0;
      this.hb = 0;
   }

   private void kb() {
      if (this.r && this.ec >= 0 && this.ec < 9 && this.mc.field_1724 != null) {
         InvUtils.swap(this.ec, false);
         this.mc.field_1724.method_31548().method_61496(this.ec);
      }

      this.r = false;
   }

   private void b(MiningSession.Status status, boolean chat) {
      if (status != this.sc) {
         this.sc = status;
         if (chat) {
            this.info("Mining status: %s", new Object[]{status.name().toLowerCase()});
         }

      }
   }

   private static enum FaceMiningState {
      PREPARE,
      MOVE_TRANSITION,
      TURN_ROTATE,
      TURN_BREAK,
      TURN_SETTLE,
      MOVE_TO_FACE,
      ROTATE,
      BREAK_FACE,
      WAIT_SETTLE,
      COMPLETE;

      // $FF: synthetic method
      private static FaceMiningState[] b() {
         return new FaceMiningState[]{PREPARE, MOVE_TRANSITION, TURN_ROTATE, TURN_BREAK, TURN_SETTLE, MOVE_TO_FACE, ROTATE, BREAK_FACE, WAIT_SETTLE, COMPLETE};
      }
   }
}
