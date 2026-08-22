package com.nyahsatoru.satoaddon.modules;

import com.nyahsatoru.satoaddon.b;
import com.nyahsatoru.satoaddon.c.c;
import com.nyahsatoru.satoaddon.c.d;
import com.nyahsatoru.satoaddon.core.MiningSession;
import com.nyahsatoru.satoaddon.core.SatoMineCoordinator;
import com.nyahsatoru.satoaddon.core.SatoMineTask;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.DoubleSetting;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.ItemListSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1297;
import net.minecraft.class_1542;
import net.minecraft.class_1792;
import net.minecraft.class_1802;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_243;

public final class SatoMineLoot extends Module {
   private static final int hb = 5;
   private static final int g = 2;
   private static final int e = 20;
   private static final int m = 3;
   private static final int y = 5;
   private static final double w = (double)1.0F;
   private final SettingGroup mb;
   private final SettingGroup o;
   private final SettingGroup z;
   private final SettingGroup rb;
   private final Setting<Boolean> p;
   private final Setting<List<class_1792>> gb;
   private final Setting<List<class_1792>> fb;
   private final Setting<List<class_1792>> db;
   private final Setting<Double> sb;
   private final Setting<Double> cb;
   private final Setting<Double> h;
   private final Setting<Integer> l;
   private final Setting<Integer> ab;
   private final Setting<Integer> q;
   private final Setting<Integer> kb;
   private final Setting<Integer> jb;
   private final Setting<Boolean> bb;
   private final Setting<Boolean> ib;
   private final Setting<Double> d;
   private final Setting<Integer> lb;
   private final Setting<Integer> f;
   private final Setting<Integer> k;
   private final SatoMineCoordinator nb;
   private final Map<UUID, d> ob;
   private final Set<UUID> s;
   private State ub;
   private c qb;
   private d tb;
   private class_2338 u;
   private boolean x;
   private long b;
   private int eb;
   private int pb;
   private int n;
   private int t;
   private boolean r;
   private boolean v;
   private boolean c;
   private class_243 i;
   private int j;

   public SatoMineLoot(SatoMineCoordinator coordinator) {
      super(com.nyahsatoru.satoaddon.b.b, "sato-autoloot", "Collects important mining drops in bounded batches.");
      this.mb = this.settings.getDefaultGroup();
      this.o = this.settings.createGroup("Batch");
      this.z = this.settings.createGroup("Timing");
      this.rb = this.settings.createGroup("Fast Loot");
      this.p = this.mb.add(((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("standalone-loot")).description("Find and collect configured drops whenever SatoMine Loot is enabled, even without a mining session.")).defaultValue(false)).build());
      this.gb = this.mb.add(((ItemListSetting.Builder)((ItemListSetting.Builder)(new ItemListSetting.Builder()).name("must-pickup-items")).description("Items that always interrupt mining for pickup. Mining target drops are included automatically.")).build());
      this.fb = this.mb.add(((ItemListSetting.Builder)((ItemListSetting.Builder)(new ItemListSetting.Builder()).name("optional-items")).description("Items collected when they are close enough to the active mining route.")).build());
      this.db = this.mb.add(((ItemListSetting.Builder)((ItemListSetting.Builder)(new ItemListSetting.Builder()).name("ignored-items")).description("Items that are never collected. Ignore overrides all other policies.")).build());
      this.sb = this.o.add(((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder()).name("loot-radius")).description("Radius around the player used to discover dropped items.")).defaultValue((double)8.0F).range((double)1.0F, (double)32.0F).sliderRange((double)1.0F, (double)16.0F).build());
      this.cb = this.o.add(((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder()).name("batch-radius")).description("Maximum distance from the seed item for entities in the same loot batch.")).defaultValue((double)3.5F).range((double)1.0F, (double)12.0F).sliderRange((double)1.0F, (double)8.0F).build());
      this.h = this.o.add(((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder()).name("max-detour")).description("Maximum distance from the mining interruption point to a loot candidate.")).defaultValue((double)12.0F).range((double)2.0F, (double)48.0F).sliderRange((double)2.0F, (double)24.0F).build());
      this.l = this.o.add(((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder()).name("batch-size")).description("Maximum number of item entities in one batch.")).defaultValue(16)).range(1, 64).sliderRange(1, 32).build());
      this.ab = this.z.add(((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder()).name("settle-delay")).description("Ticks an item must exist before it can enter a batch.")).defaultValue(10)).range(0, 60).sliderRange(0, 40).build());
      this.q = this.z.add(((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder()).name("loot-timeout")).description("Maximum ticks spent processing one batch.")).defaultValue(200)).range(40, 1200).sliderRange(40, 600).build());
      this.kb = this.z.add(((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder()).name("pickup-wait")).description("Ticks to wait near a batch before selecting another unresolved item.")).defaultValue(15)).range(2, 100).sliderRange(2, 60).build());
      this.jb = this.z.add(((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder()).name("resume-delay")).description("Ticks to wait after a batch before Mining resumes or standalone Loot releases ownership.")).defaultValue(5)).range(0, 40).sliderRange(0, 20).build());
      this.bb = this.rb.add(((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("fast-batch-loot")).description("Sweep through a loot batch with fewer paths and without waiting at every item entity.")).defaultValue(true)).build());
      SettingGroup var10001 = this.rb;
      BoolSetting.Builder var10002 = (BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("sprint-loot")).description("Allow faster movement while collecting loot.")).defaultValue(true);
      Setting var10003 = this.bb;
      Objects.requireNonNull(var10003);
      this.ib = var10001.add(((BoolSetting.Builder)var10002.visible(var10003::get)).build());
      var10001 = this.rb;
      DoubleSetting.Builder var6 = ((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder()).name("pickup-radius")).description("Distance used to enter the short pickup verification state in Fast Batch Loot.")).defaultValue((double)1.5F).range((double)1.0F, (double)2.5F).sliderRange((double)1.0F, (double)2.0F);
      var10003 = this.bb;
      Objects.requireNonNull(var10003);
      this.d = var10001.add(((DoubleSetting.Builder)var6.visible(var10003::get)).build());
      var10001 = this.rb;
      IntSetting.Builder var7 = ((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder()).name("fast-settle-delay")).description("Maximum item settle delay used while Fast Batch Loot is enabled.")).defaultValue(3)).range(0, 20).sliderRange(0, 10);
      var10003 = this.bb;
      Objects.requireNonNull(var10003);
      this.lb = var10001.add(((IntSetting.Builder)var7.visible(var10003::get)).build());
      var10001 = this.rb;
      var7 = ((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder()).name("fast-pickup-wait")).description("Ticks to verify pickup after a fast route reaches an unresolved item.")).defaultValue(3)).range(1, 20).sliderRange(1, 10);
      var10003 = this.bb;
      Objects.requireNonNull(var10003);
      this.f = var10001.add(((IntSetting.Builder)var7.visible(var10003::get)).build());
      var10001 = this.rb;
      var7 = ((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder()).name("stuck-timeout")).description("Ticks without player movement before the current fast loot path is cancelled.")).defaultValue(60)).range(20, 200).sliderRange(20, 120);
      var10003 = this.bb;
      Objects.requireNonNull(var10003);
      this.k = var10001.add(((IntSetting.Builder)var7.visible(var10003::get)).build());
      this.ob = new HashMap();
      this.s = new HashSet();
      this.ub = SatoMineLoot.State.OBSERVING;
      this.nb = coordinator;
   }

   public void onActivate() {
      this.b(false);
   }

   public void onDeactivate() {
      this.b(true);
   }

   @EventHandler
   private void b(TickEvent.Pre event) {
      ++this.b;
      if (this.mc.field_1724 != null && this.mc.field_1687 != null) {
         if (this.qb != null && this.ub != SatoMineLoot.State.PAUSE_MINING && this.nb.getCurrentTask() != SatoMineTask.LOOT) {
            this.p();
         } else {
            if (this.qb != null && !this.v && this.b - this.qb.e() >= (long)(Integer)this.q.get()) {
               this.v = true;
               this.b(d._b.f);
               this.j();
               this.ub = SatoMineLoot.State.RESUME_MINING;
               this.pb = 0;
            }

            switch (this.ub.ordinal()) {
               case 0 -> this.c();
               case 1 -> this.k();
               case 2 -> this.m();
               case 3 -> this.i();
               case 4 -> this.d();
               case 5 -> this.f();
            }

         }
      } else {
         if (this.nb.getCurrentTask() == SatoMineTask.LOOT) {
            this.nb.cancelMining();
         }

         this.b(false);
      }
   }

   public String getInfoString() {
      if (this.qb == null) {
         return this.ub.name().toLowerCase();
      } else {
         long pending = this.qb.f().stream().filter(d::d).count();
         return this.ub.name().toLowerCase() + " " + pending + "/" + this.qb.f().size();
      }
   }

   private void c() {
      if (this.nb.getContext().isSellPending()) {
         this.ob.clear();
      } else {
         SatoMineTask currentTask = this.nb.getCurrentTask();
         boolean miningAvailable = currentTask == SatoMineTask.MINING && this.nb.getStatus() == MiningSession.Status.RUNNING;
         boolean standaloneAvailable = (Boolean)this.p.get() && currentTask == SatoMineTask.IDLE;
         if (!miningAvailable && !standaloneAvailable) {
            this.ob.clear();
         } else if (this.eb-- <= 0) {
            this.eb = (Boolean)this.bb.get() ? 2 : 5;
            this.l();
            c batch = this.e();
            if (batch != null) {
               this.c = standaloneAvailable;
               this.qb = batch;
               this.s.clear();
               this.v = false;
               this.ub = SatoMineLoot.State.PAUSE_MINING;
               this.pb = 0;
            }
         }
      }
   }

   private void l() {
      double radius = (Double)this.sb.get();
      List<class_1542> entities = this.mc.field_1687.method_8390(class_1542.class, this.mc.field_1724.method_5829().method_1014(radius), (entityx) -> entityx.method_5805() && !entityx.method_31481() && !entityx.method_6983().method_7960());
      Set<UUID> seen = new HashSet();

      for(class_1542 entity : entities) {
         com.nyahsatoru.satoaddon.c.b policy = this.c(entity.method_6983().method_7909());
         UUID id = entity.method_5667();
         seen.add(id);
         if (policy == com.nyahsatoru.satoaddon.c.b.e) {
            this.ob.remove(id);
         } else {
            this.ob.compute(id, (uuid, current) -> {
               if (current == null) {
                  return new d(entity, policy, this.b);
               } else {
                  current.b(entity, policy, this.b);
                  return current;
               }
            });
         }
      }

      this.ob.entrySet().removeIf((entry) -> !seen.contains(entry.getKey()) && this.b - ((d)entry.getValue()).b() > 10L);
   }

   private c e() {
      class_243 origin = this.n();
      int effectiveSettleDelay = (Boolean)this.bb.get() ? Math.min((Integer)this.ab.get(), (Integer)this.lb.get()) : (Integer)this.ab.get();
      List<d> eligible = this.ob.values().stream().filter(d::d).filter((candidatex) -> this.b - candidatex.i() >= (long)effectiveSettleDelay).filter((candidatex) -> candidatex.e().method_1025(origin) <= this.b((Double)this.h.get())).sorted(Comparator.comparingInt((candidatex) -> candidatex.j() == com.nyahsatoru.satoaddon.c.b.c ? 0 : 1).thenComparingDouble((candidatex) -> candidatex.e().method_1025(origin))).toList();
      if (eligible.isEmpty()) {
         return null;
      } else {
         d seed = (d)eligible.get(0);
         double radiusSquared = this.b((Double)this.cb.get());
         List<d> batchCandidates = eligible.stream().filter((candidatex) -> candidatex.e().method_1025(seed.e()) <= radiusSquared).limit((long)(Integer)this.l.get()).toList();
         if (batchCandidates.isEmpty()) {
            return null;
         } else {
            Map<class_1792, Integer> inventoryBefore = new HashMap();

            for(d candidate : batchCandidates) {
               inventoryBefore.putIfAbsent(candidate.h(), this.b(candidate.h()));
            }

            return new c(batchCandidates, origin, this.b, inventoryBefore);
         }
      }
   }

   private void k() {
      boolean started = this.c ? this.nb.beginStandaloneLoot() : this.nb.beginLoot();
      if (!started) {
         this.p();
      } else {
         this.info("%s loot batch started: %d item entities.", new Object[]{this.c ? "Standalone" : "Mining", this.qb.f().size()});
         this.ub = SatoMineLoot.State.SELECT_GOAL;
         this.pb = 0;
      }
   }

   private void m() {
      this.h();
      if (this.qb.c()) {
         this.j();
         this.ub = SatoMineLoot.State.RESUME_MINING;
         this.pb = 0;
      } else {
         Comparator<d> distanceOrder = Comparator.comparingDouble((candidate) -> candidate.e().method_1025(this.n()));
         if ((Boolean)this.bb.get()) {
            distanceOrder = distanceOrder.reversed();
         }

         this.tb = (d)this.qb.f().stream().filter(d::d).filter((candidate) -> !this.s.contains(candidate.k())).filter((candidate) -> candidate.e().method_1025(this.qb.b()) <= this.b((Double)this.h.get())).min(distanceOrder).orElse((Object)null);
         if (this.tb == null) {
            this.b(d._b.f);
            this.ub = SatoMineLoot.State.RESUME_MINING;
            this.pb = 0;
         } else {
            this.u = class_2338.method_49638(this.tb.e());
            if (!this.mc.field_1687.method_8316(this.u).method_15769()) {
               this.tb.b(d._b.f);
               this.ub = SatoMineLoot.State.SELECT_GOAL;
            } else {
               this.x = !(Boolean)this.bb.get() && this.b(this.u);
               this.s.add(this.tb.k());
               this.r = false;
               this.n = 0;
               this.t = 0;
               this.i = this.n();
               this.j = 0;
               this.ub = SatoMineLoot.State.NAVIGATE;
            }
         }
      }
   }

   private void i() {
      this.h();
      if (this.qb.c()) {
         this.j();
         this.ub = SatoMineLoot.State.RESUME_MINING;
         this.pb = 0;
      } else if (this.tb != null && !this.tb.d()) {
         this.j();
         this.ub = SatoMineLoot.State.SELECT_GOAL;
         this.pb = 0;
      } else if (!(Boolean)this.bb.get() && this.g()) {
         this.j();
         this.r = false;
         this.ub = SatoMineLoot.State.WAIT_PICKUP;
         this.pb = 0;
      } else if (!this.r) {
         if ((Boolean)this.bb.get() && (Boolean)this.ib.get()) {
            this.nb.getNavigationService().i();
         }

         if (this.nb.navigateForLoot(this.u, this.x)) {
            this.r = true;
            this.n = 0;
            this.i = this.n();
            this.j = 0;
         } else if (++this.t >= 3) {
            this.tb.b(d._b.f);
            this.j();
            this.ub = SatoMineLoot.State.SELECT_GOAL;
         }

      } else if (this.nb.getNavigationService().e()) {
         this.n = 0;
         if ((Boolean)this.bb.get() && !this.b()) {
            this.j();
            this.r = false;
            if (++this.t >= 3) {
               this.tb.b(d._b.f);
               this.ub = SatoMineLoot.State.SELECT_GOAL;
            }
         }

      } else if ((Boolean)this.bb.get() && this.g()) {
         this.j();
         this.ub = SatoMineLoot.State.WAIT_PICKUP;
         this.pb = 0;
      } else {
         int retryDelay = (Boolean)this.bb.get() ? 5 : 20;
         if (++this.n >= retryDelay) {
            this.n = 0;
            this.r = false;
            if (++this.t >= 3) {
               this.tb.b(d._b.f);
               this.j();
               this.ub = SatoMineLoot.State.SELECT_GOAL;
            }

         }
      }
   }

   private void d() {
      this.h();
      if (this.qb.c()) {
         this.ub = SatoMineLoot.State.RESUME_MINING;
         this.pb = 0;
      } else {
         int wait = (Boolean)this.bb.get() ? Math.min((Integer)this.kb.get(), (Integer)this.f.get()) : (Integer)this.kb.get();
         if (++this.pb >= wait) {
            this.pb = 0;
            this.ub = SatoMineLoot.State.SELECT_GOAL;
         }
      }
   }

   private void h() {
      Map<UUID, class_1542> liveEntities = this.o();
      Map<class_1792, Integer> availableInventoryDelta = new HashMap();

      for(d candidate : this.qb.f()) {
         availableInventoryDelta.putIfAbsent(candidate.h(), Math.max(0, this.b(candidate.h()) - this.qb.b(candidate.h())));
      }

      for(d candidate : this.qb.f()) {
         if (candidate.c() == d._b.e) {
            availableInventoryDelta.computeIfPresent(candidate.h(), (item, availablex) -> Math.max(0, availablex - candidate.f()));
         }
      }

      for(d candidate : this.qb.f()) {
         if (candidate.d()) {
            int available = (Integer)availableInventoryDelta.getOrDefault(candidate.h(), 0);
            if (available >= candidate.f()) {
               candidate.b(d._b.e);
               availableInventoryDelta.put(candidate.h(), Math.max(0, available - candidate.f()));
            } else {
               class_1542 entity = (class_1542)liveEntities.get(candidate.k());
               if (entity != null) {
                  candidate.b(entity, candidate.j(), this.b);
               } else if (candidate.g() >= 5) {
                  candidate.b(d._b.c);
               }
            }
         }
      }

   }

   private Map<UUID, class_1542> o() {
      Map<UUID, class_1542> entities = new HashMap();

      for(class_1297 entity : this.mc.field_1687.method_18112()) {
         if (entity instanceof class_1542 itemEntity) {
            if (itemEntity.method_5805() && !itemEntity.method_31481()) {
               entities.put(itemEntity.method_5667(), itemEntity);
            }
         }
      }

      return entities;
   }

   private boolean g() {
      class_243 playerPos = this.n();
      double rangeSquared = (Boolean)this.bb.get() ? this.b((Double)this.d.get()) : (double)1.0F;
      return this.qb.f().stream().filter(d::d).anyMatch((candidate) -> candidate.e().method_1025(playerPos) <= rangeSquared);
   }

   private boolean b() {
      class_243 current = this.n();
      if (this.i != null && !(current.method_1025(this.i) >= 0.04)) {
         return ++this.j < (Integer)this.k.get();
      } else {
         this.i = current;
         this.j = 0;
         return true;
      }
   }

   private void j() {
      this.nb.getNavigationService().h();
      this.nb.getNavigationService().c();
      this.r = false;
      this.i = null;
      this.j = 0;
   }

   private void f() {
      if (this.pb++ >= (Integer)this.jb.get()) {
         long pickedUp = this.qb == null ? 0L : this.qb.d();
         if (this.nb.getCurrentTask() == SatoMineTask.LOOT) {
            boolean resumed = this.nb.finishLoot();
            if (!resumed) {
               this.warning("Loot finished, but task ownership could not be released.", new Object[0]);
            }
         }

         if (this.v) {
            this.warning("Loot batch timed out; resolved %d confirmed pickups.", new Object[]{pickedUp});
         } else {
            this.info("Loot batch finished: %d confirmed pickups.", new Object[]{pickedUp});
         }

         this.p();
      }
   }

   private com.nyahsatoru.satoaddon.c.b c(class_1792 item) {
      if (((List)this.db.get()).contains(item)) {
         return com.nyahsatoru.satoaddon.c.b.e;
      } else if (!((List)this.gb.get()).contains(item) && !this.d(item)) {
         return ((List)this.fb.get()).contains(item) ? com.nyahsatoru.satoaddon.c.b.b : com.nyahsatoru.satoaddon.c.b.e;
      } else {
         return com.nyahsatoru.satoaddon.c.b.c;
      }
   }

   private boolean d(class_1792 item) {
      MiningSession session = this.nb.getContext().getMiningSession();
      if (session == null) {
         return false;
      } else {
         for(class_2248 block : session.getTargetBlocks()) {
            class_1792 drop = block.method_8389();
            if (drop != class_1802.field_8162 && drop == item) {
               return true;
            }
         }

         return false;
      }
   }

   private int b(class_1792 item) {
      int count = 0;

      for(int slot = 0; slot < this.mc.field_1724.method_31548().method_5439(); ++slot) {
         if (this.mc.field_1724.method_31548().method_5438(slot).method_31574(item)) {
            count += this.mc.field_1724.method_31548().method_5438(slot).method_7947();
         }
      }

      return count;
   }

   private boolean b(class_2338 feet) {
      if (this.mc.field_1687.method_8320(feet).method_26215() && this.mc.field_1687.method_8320(feet.method_10084()).method_26215()) {
         class_2338 floor = feet.method_10074();
         return this.mc.field_1687.method_8316(floor).method_15769() && this.mc.field_1687.method_8320(floor).method_26212(this.mc.field_1687, floor);
      } else {
         return false;
      }
   }

   private void b(d._b resolution) {
      if (this.qb != null) {
         for(d candidate : this.qb.f()) {
            if (candidate.d()) {
               candidate.b(resolution);
            }
         }

      }
   }

   private void b(boolean resumeMining) {
      if (resumeMining && this.nb.getCurrentTask() == SatoMineTask.LOOT) {
         this.nb.finishLoot();
      }

      this.ob.clear();
      this.b = 0L;
      this.eb = 0;
      this.p();
   }

   private void p() {
      if (this.nb.getCurrentTask() == SatoMineTask.LOOT) {
         this.j();
      } else {
         this.nb.getNavigationService().c();
      }

      this.qb = null;
      this.tb = null;
      this.u = null;
      this.x = false;
      this.s.clear();
      this.ub = SatoMineLoot.State.OBSERVING;
      this.pb = 0;
      this.n = 0;
      this.t = 0;
      this.r = false;
      this.v = false;
      this.c = false;
      this.i = null;
      this.j = 0;
   }

   private double b(double value) {
      return value * value;
   }

   private class_243 n() {
      return new class_243(this.mc.field_1724.method_23317(), this.mc.field_1724.method_23318(), this.mc.field_1724.method_23321());
   }

   private static enum State {
      OBSERVING,
      PAUSE_MINING,
      SELECT_GOAL,
      NAVIGATE,
      WAIT_PICKUP,
      RESUME_MINING;

      // $FF: synthetic method
      private static State[] b() {
         return new State[]{OBSERVING, PAUSE_MINING, SELECT_GOAL, NAVIGATE, WAIT_PICKUP, RESUME_MINING};
      }
   }
}
