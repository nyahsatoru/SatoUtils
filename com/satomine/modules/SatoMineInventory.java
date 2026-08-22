package com.satomine.modules;

import com.satomine.b.SatoSellGui;
import com.satomine.b.b;
import com.satomine.b.c;
import com.satomine.core.SatoMineCoordinator;
import com.satomine.core.SatoMineTask;
import java.util.List;
import java.util.UUID;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.EnumSetting;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.ItemListSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1792;

public final class SatoMineInventory extends Module {
   private final SettingGroup e;
   private final SettingGroup p;
   private final SettingGroup b;
   private final Setting<Boolean> i;
   private final Setting<SellMode> j;
   private final Setting<Integer> r;
   private final Setting<Integer> d;
   private final Setting<List<class_1792>> y;
   private final Setting<List<class_1792>> w;
   private final Setting<Boolean> k;
   private final Setting<Boolean> c;
   private final Setting<Integer> q;
   private final Setting<Integer> u;
   private final Setting<Integer> o;
   private final Setting<Boolean> n;
   private final SatoMineCoordinator l;
   private final b v;
   private final c s;
   private State f;
   private c._b g;
   private String h;
   private int t;
   private int m;
   private boolean x;

   public SatoMineInventory(SatoMineCoordinator coordinator) {
      super(com.satomine.b.b, "satomine-inventory", "Automatically sells only the items selected in Items to Sell, then resumes mining.");
      this.e = this.settings.getDefaultGroup();
      this.p = this.settings.createGroup("Policy");
      this.b = this.settings.createGroup("Safety");
      this.i = this.e.add(((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("auto-sell")).description("When inventory is nearly full, open /sellgui, sell the selected items, close the GUI, and continue mining.")).defaultValue(true)).build());
      this.j = this.e.add(((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)(new EnumSetting.Builder()).name("sell-method")).description("SellGUI: open /sellgui, move selected Items to Sell into the sell slots, then close the GUI.")).defaultValue(SatoMineInventory.SellMode.SellGUI)).build());
      this.r = this.e.add(((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder()).name("sell-when-slots-left")).description("Start selling when the main inventory has this many free slots or fewer.")).defaultValue(1)).range(0, 35).sliderRange(0, 10).build());
      this.d = this.e.add(((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder()).name("resume-when-slots-left")).description("Resume mining when the inventory has at least this many free slots.")).defaultValue(10)).range(1, 36).sliderRange(1, 20).build());
      this.y = this.p.add(((ItemListSetting.Builder)((ItemListSetting.Builder)(new ItemListSetting.Builder()).name("items-to-sell")).description("Explicit whitelist for SellGUI. Only matching inventory stacks may be quick-moved.")).build());
      this.w = this.p.add(((ItemListSetting.Builder)((ItemListSetting.Builder)(new ItemListSetting.Builder()).name("keep-items")).description("SellGUI ignores Keep Items completely; only Items to Sell are eligible.")).build());
      this.k = this.p.add(((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("sell-leftovers")).description("SellGUI only processes the explicit Items to Sell whitelist.")).defaultValue(true)).visible(() -> this.j.get() == SatoMineInventory.SellMode.Order)).build());
      this.c = this.p.add(((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("auto-protect-important-items")).description("Protect damageable, enchanted, named, custom-data, container, spawner, and Ender Chest items.")).defaultValue(true)).build());
      this.q = this.b.add(((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder()).name("executor-timeout")).description("Maximum time allowed for one automatic selling run.")).defaultValue(1200)).range(100, 12000).sliderRange(200, 2400).build());
      this.u = this.b.add(((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder()).name("cleanup-grace")).description("Ticks allowed for a managed bot to deactivate after cancellation.")).defaultValue(40)).range(5, 200).sliderRange(5, 100).build());
      this.o = this.b.add(((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder()).name("retry-cooldown")).description("Delay before another automatic selling cycle can start.")).defaultValue(200)).range(20, 2400).sliderRange(20, 600).build());
      this.n = this.b.add(((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("chat-feedback")).description("Show simple selling progress messages in chat.")).defaultValue(true)).build());
      this.v = new b();
      this.s = new c();
      this.f = SatoMineInventory.State.MONITOR;
      this.l = coordinator;
   }

   public void onActivate() {
      this.f();
   }

   public void onDeactivate() {
      this.e();
      this.l.clearSellRequest();
      if (this.l.getCurrentTask() == SatoMineTask.SELL) {
         this.l.finishSell();
      }

      this.f();
   }

   @EventHandler
   private void b(TickEvent.Pre event) {
      SatoSellGui.tick();
      if (this.mc.field_1724 != null && this.mc.field_1687 != null) {
         if (this.f != SatoMineInventory.State.MONITOR && this.f != SatoMineInventory.State.WAIT_LOOT && this.f != SatoMineInventory.State.COOLDOWN && this.l.getCurrentTask() != SatoMineTask.SELL) {
            this.e();
            this.f();
         } else {
            switch (this.f.ordinal()) {
               case 0 -> this.j();
               case 1 -> this.l();
               case 2 -> this.d();
               case 3 -> this.b(c._b.b, SatoMineInventory.State.START_SELL_GUI);
               case 4 -> this.b();
               case 5 -> this.b(c._b.e, SatoMineInventory.State.VERIFY);
               case 6 -> this.k();
               case 7 -> this.i();
               case 8 -> this.h();
            }

         }
      } else {
         this.e();
         this.l.clearSellRequest();
         if (this.l.getCurrentTask() == SatoMineTask.SELL) {
            this.l.finishSell();
         }

         this.f();
      }
   }

   public String getInfoString() {
      int free = this.mc.field_1724 == null ? 0 : this.v.b(this.mc.field_1724);
      String var10000 = this.f.name().toLowerCase();
      return var10000 + " " + free + " free";
   }

   private void j() {
      if ((Boolean)this.i.get() && this.v.b(this.mc.field_1724) <= (Integer)this.r.get()) {
         this.l.requestSell();
         this.f = SatoMineInventory.State.WAIT_LOOT;
         this.t = 0;
         this.b("Inventory has %d free slot(s); starting automatic selling.", this.v.b(this.mc.field_1724));
      }
   }

   private void l() {
      ++this.t;
      if (this.l.getCurrentTask() != SatoMineTask.LOOT) {
         if (this.l.beginSell()) {
            this.f = this.j.get() == SatoMineInventory.SellMode.Order ? SatoMineInventory.State.START_ORDER : SatoMineInventory.State.START_SELL_GUI;
            this.t = 0;
         }
      }
   }

   private void d() {
      if (!this.s.b(c._b.b)) {
         this.c("SatoMine Order unavailable; continuing with Sell GUI cleanup.");
         this.f = SatoMineInventory.State.START_SELL_GUI;
      } else if (this.s.c(c._b.b)) {
         this.c("SatoMine Order is already active and was not started by SatoMine Inventory; Sell cycle aborted safely.");
         this.f = SatoMineInventory.State.VERIFY;
      } else {
         this.b(c._b.b);
         if (!this.s.b(this.h)) {
            this.c("Managed Order could not start: %s", this.s.b());
            this.c();
            this.f = SatoMineInventory.State.START_SELL_GUI;
         } else {
            this.f = SatoMineInventory.State.WAIT_ORDER;
         }
      }
   }

   private void b() {
      List<String> itemIds = this.v.b(this.mc.field_1724, (List)this.w.get(), (List)this.y.get(), this.j.get() != SatoMineInventory.SellMode.Order || (Boolean)this.k.get(), (Boolean)this.c.get());
      if (itemIds.isEmpty()) {
         this.f = SatoMineInventory.State.VERIFY;
      } else if (!this.s.b(c._b.e)) {
         this.c("SatoMine SellGUI unavailable; cleanup cannot continue.");
         this.f = SatoMineInventory.State.VERIFY;
      } else if (this.s.c(c._b.e)) {
         this.c("SatoMine SellGUI is already active and was not started by SatoMine Inventory; Sell cycle aborted safely.");
         this.f = SatoMineInventory.State.VERIFY;
      } else {
         this.b(c._b.e);
         if (!this.s.b(this.h, itemIds)) {
            this.c("SatoMine SellGUI could not start: %s", this.s.b());
            this.c();
            this.f = SatoMineInventory.State.VERIFY;
         } else {
            this.f = SatoMineInventory.State.WAIT_SELL_GUI;
         }
      }
   }

   private void b(c._b executor, State nextState) {
      ++this.t;
      c._c status = this.s.e(executor, this.h);
      boolean active = this.s.c(executor);
      if (status.c() && !active) {
         if (status != c._c.e) {
            this.c("%s ended with %s: %s", executor, status, this.s.d(executor, this.h));
         }

         this.c();
         this.f = nextState;
         this.t = 0;
      } else if (!this.x && this.t >= (Integer)this.q.get()) {
         this.x = true;
         this.t = 0;
         this.s.c(executor, this.h);
         this.c("%s timed out; managed cancellation requested.", executor);
      } else {
         if (this.x && this.t >= (Integer)this.u.get()) {
            this.s.b(executor, this.h);
            this.c();
            this.f = nextState;
            this.t = 0;
         }

      }
   }

   private void k() {
      int free = this.v.b(this.mc.field_1724);
      int target = Math.max((Integer)this.r.get() + 1, (Integer)this.d.get());
      if (free < target) {
         this.c("Selling finished with %d free slot(s); target is %d.", free, target);
      } else {
         this.b("Selling complete: %d free slot(s) available.", free);
      }

      this.f = SatoMineInventory.State.RESUME;
   }

   private void i() {
      boolean resumed = this.l.finishSell();
      if (!resumed) {
         this.c("Selling finished, but mining could not resume automatically.");
      }

      this.f = SatoMineInventory.State.COOLDOWN;
      this.m = (Integer)this.o.get();
   }

   private void h() {
      if (this.m-- <= 0) {
         this.f = SatoMineInventory.State.MONITOR;
      }
   }

   private void b(c._b executor) {
      this.g = executor;
      this.h = "satomine-inventory-" + String.valueOf(UUID.randomUUID());
      this.t = 0;
      this.x = false;
   }

   private void c() {
      this.g = null;
      this.h = null;
      this.x = false;
   }

   private void e() {
      if (this.g != null && this.h != null) {
         this.s.c(this.g, this.h);
         if (this.s.c(this.g)) {
            this.s.b(this.g, this.h);
         }

         this.c();
      }
   }

   private void f() {
      this.f = SatoMineInventory.State.MONITOR;
      this.g = null;
      this.h = null;
      this.t = 0;
      this.m = 0;
      this.x = false;
   }

   private void b(String message, Object... args) {
      if ((Boolean)this.n.get()) {
         this.info(message, args);
      }

   }

   private void c(String message, Object... args) {
      if ((Boolean)this.n.get()) {
         this.warning(message, args);
      }

   }

   public static enum SellMode {
      Order,
      SellGUI;

      // $FF: synthetic method
      private static SellMode[] b() {
         return new SellMode[]{Order, SellGUI};
      }
   }

   private static enum State {
      MONITOR,
      WAIT_LOOT,
      START_ORDER,
      WAIT_ORDER,
      START_SELL_GUI,
      WAIT_SELL_GUI,
      VERIFY,
      RESUME,
      COOLDOWN;

      // $FF: synthetic method
      private static State[] b() {
         return new State[]{MONITOR, WAIT_LOOT, START_ORDER, WAIT_ORDER, START_SELL_GUI, WAIT_SELL_GUI, VERIFY, RESUME, COOLDOWN};
      }
   }
}
