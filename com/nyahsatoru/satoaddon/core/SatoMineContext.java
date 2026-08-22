package com.nyahsatoru.satoaddon.core;

public final class SatoMineContext {
   private MiningSession c;
   private SatoMineTask b;
   private boolean f;
   private boolean d;
   private boolean e;

   public SatoMineContext() {
      this.b = SatoMineTask.IDLE;
   }

   public MiningSession getMiningSession() {
      return this.c;
   }

   public void setMiningSession(MiningSession miningSession) {
      this.c = miningSession;
   }

   public SatoMineTask getCurrentTask() {
      return this.b;
   }

   public void setCurrentTask(SatoMineTask currentTask) {
      this.b = currentTask;
   }

   public boolean shouldResumeMiningAfterLoot() {
      return this.f;
   }

   public void setResumeMiningAfterLoot(boolean resumeMiningAfterLoot) {
      this.f = resumeMiningAfterLoot;
   }

   public boolean isSellPending() {
      return this.d;
   }

   public void setSellPending(boolean sellPending) {
      this.d = sellPending;
   }

   public boolean shouldResumeMiningAfterSell() {
      return this.e;
   }

   public void setResumeMiningAfterSell(boolean resumeMiningAfterSell) {
      this.e = resumeMiningAfterSell;
   }
}
