package com.satomine.core;

import com.satomine.b.e;
import java.util.List;
import net.minecraft.class_2248;
import net.minecraft.class_2338;

public final class SatoMineCoordinator {
   private final SatoMineContext c = new SatoMineContext();
   private final e b = new e();

   public SatoMineContext getContext() {
      return this.c;
   }

   public e getNavigationService() {
      return this.b;
   }

   public boolean startMining(class_2248 target) {
      if (target == null) {
         return false;
      } else {
         MiningSession session = new MiningSession();
         session.setTarget(target);
         session.setTargetBlocks(List.of(target));
         session.setStrategy(MiningStrategy.FREE);
         if (!this.b.c(List.of(target))) {
            return false;
         } else {
            session.setStatus(MiningSession.Status.RUNNING);
            this.c.setMiningSession(session);
            this.c.setCurrentTask(SatoMineTask.MINING);
            this.c.setResumeMiningAfterLoot(false);
            this.c.setSellPending(false);
            this.c.setResumeMiningAfterSell(false);
            return true;
         }
      }
   }

   public boolean startAreaSession(List<class_2248> targets, MiningRegion region, MiningTraversal traversal) {
      if (targets != null && region != null && traversal != null) {
         MiningSession session = new MiningSession();
         session.setTargetBlocks(targets);
         session.setStrategy(MiningStrategy.AREA);
         session.setTraversal(traversal);
         session.setRegion(region);
         session.setStatus(MiningSession.Status.RUNNING);
         this.c.setMiningSession(session);
         this.c.setCurrentTask(SatoMineTask.MINING);
         this.c.setResumeMiningAfterLoot(false);
         this.c.setSellPending(false);
         this.c.setResumeMiningAfterSell(false);
         return true;
      } else {
         return false;
      }
   }

   public boolean navigateTo(class_2338 target) {
      MiningSession session = this.c.getMiningSession();
      if (session != null && this.c.getCurrentTask() == SatoMineTask.MINING && session.getStrategy() == MiningStrategy.AREA && target != null) {
         if (!this.b.c(target)) {
            return false;
         } else {
            session.setCurrentTarget(target);
            return true;
         }
      } else {
         return false;
      }
   }

   public boolean navigateToExact(class_2338 target) {
      MiningSession session = this.c.getMiningSession();
      if (session != null && this.c.getCurrentTask() == SatoMineTask.MINING && session.getStrategy() == MiningStrategy.AREA && target != null) {
         if (!this.b.b(target)) {
            return false;
         } else {
            session.setCurrentTarget(target);
            return true;
         }
      } else {
         return false;
      }
   }

   public void completeMining() {
      this.b.h();
      MiningSession session = this.c.getMiningSession();
      if (session != null) {
         session.setCurrentTarget((class_2338)null);
         session.setStatus(MiningSession.Status.COMPLETED);
      }

      this.c.setCurrentTask(SatoMineTask.IDLE);
      this.c.setResumeMiningAfterLoot(false);
      this.c.setSellPending(false);
      this.c.setResumeMiningAfterSell(false);
   }

   public boolean pauseMining() {
      MiningSession session = this.c.getMiningSession();
      if (session != null && this.c.getCurrentTask() == SatoMineTask.MINING && session.isRunning() && this.b.f()) {
         session.setStatus(MiningSession.Status.PAUSED);
         return true;
      } else {
         return false;
      }
   }

   public boolean resumeMining() {
      MiningSession session = this.c.getMiningSession();
      if (session != null && this.c.getCurrentTask() == SatoMineTask.MINING && session.isPaused() && this.b.b()) {
         session.setStatus(MiningSession.Status.RUNNING);
         return true;
      } else {
         return false;
      }
   }

   public boolean cancelMining() {
      MiningSession session = this.c.getMiningSession();
      boolean stopped = this.b.h();
      if (session != null) {
         session.setStatus(MiningSession.Status.IDLE);
      }

      this.c.setMiningSession((MiningSession)null);
      this.c.setCurrentTask(SatoMineTask.IDLE);
      this.c.setResumeMiningAfterLoot(false);
      this.c.setSellPending(false);
      this.c.setResumeMiningAfterSell(false);
      return stopped || session != null;
   }

   public boolean beginLoot() {
      MiningSession session = this.c.getMiningSession();
      if (!this.c.isSellPending() && session != null && this.c.getCurrentTask() == SatoMineTask.MINING && session.isRunning()) {
         this.b.h();
         this.b.c();
         session.setStatus(MiningSession.Status.PAUSED);
         this.c.setCurrentTask(SatoMineTask.LOOT);
         this.c.setResumeMiningAfterLoot(true);
         return true;
      } else {
         return false;
      }
   }

   public boolean beginStandaloneLoot() {
      if (!this.c.isSellPending() && this.c.getCurrentTask() == SatoMineTask.IDLE) {
         this.b.h();
         this.b.c();
         this.c.setCurrentTask(SatoMineTask.LOOT);
         this.c.setResumeMiningAfterLoot(false);
         return true;
      } else {
         return false;
      }
   }

   public boolean navigateForLoot(class_2338 target, boolean exact) {
      if (this.c.getCurrentTask() == SatoMineTask.LOOT && target != null) {
         return exact ? this.b.b(target) : this.b.c(target);
      } else {
         return false;
      }
   }

   public boolean finishLoot() {
      if (this.c.getCurrentTask() != SatoMineTask.LOOT) {
         return false;
      } else {
         this.b.h();
         if (!this.c.shouldResumeMiningAfterLoot()) {
            this.c.setCurrentTask(SatoMineTask.IDLE);
            this.c.setResumeMiningAfterLoot(false);
            return true;
         } else {
            MiningSession session = this.c.getMiningSession();
            if (session == null) {
               this.c.setCurrentTask(SatoMineTask.IDLE);
               this.c.setResumeMiningAfterLoot(false);
               return false;
            } else {
               session.setStatus(MiningSession.Status.RUNNING);
               this.c.setCurrentTask(SatoMineTask.MINING);
               this.c.setResumeMiningAfterLoot(false);
               if (session.getStrategy() == MiningStrategy.FREE && !this.b.c(session.getTargetBlocks())) {
                  session.setStatus(MiningSession.Status.FAILED);
                  this.c.setCurrentTask(SatoMineTask.IDLE);
                  return false;
               } else {
                  return true;
               }
            }
         }
      }
   }

   public void requestSell() {
      if (this.c.getCurrentTask() != SatoMineTask.SELL) {
         this.c.setSellPending(true);
      }

   }

   public void clearSellRequest() {
      this.c.setSellPending(false);
   }

   public boolean beginSell() {
      if (!this.c.isSellPending()) {
         return false;
      } else {
         SatoMineTask currentTask = this.c.getCurrentTask();
         MiningSession session = this.c.getMiningSession();
         if (currentTask == SatoMineTask.MINING) {
            if (session == null || !session.isRunning()) {
               return false;
            }

            this.b.h();
            this.b.c();
            session.setStatus(MiningSession.Status.PAUSED);
            this.c.setResumeMiningAfterSell(true);
         } else {
            if (currentTask != SatoMineTask.IDLE) {
               return false;
            }

            this.b.h();
            this.b.c();
            this.c.setResumeMiningAfterSell(false);
         }

         this.c.setCurrentTask(SatoMineTask.SELL);
         this.c.setSellPending(false);
         return true;
      }
   }

   public boolean finishSell() {
      if (this.c.getCurrentTask() != SatoMineTask.SELL) {
         return false;
      } else {
         this.b.h();
         if (!this.c.shouldResumeMiningAfterSell()) {
            this.c.setCurrentTask(SatoMineTask.IDLE);
            this.c.setResumeMiningAfterSell(false);
            this.c.setSellPending(false);
            return true;
         } else {
            MiningSession session = this.c.getMiningSession();
            if (session == null) {
               this.c.setCurrentTask(SatoMineTask.IDLE);
               this.c.setResumeMiningAfterSell(false);
               this.c.setSellPending(false);
               return false;
            } else {
               session.setStatus(MiningSession.Status.RUNNING);
               this.c.setCurrentTask(SatoMineTask.MINING);
               this.c.setResumeMiningAfterSell(false);
               this.c.setSellPending(false);
               if (session.getStrategy() == MiningStrategy.FREE && !this.b.c(session.getTargetBlocks())) {
                  session.setStatus(MiningSession.Status.FAILED);
                  this.c.setCurrentTask(SatoMineTask.IDLE);
                  return false;
               } else {
                  return true;
               }
            }
         }
      }
   }

   public SatoMineTask getCurrentTask() {
      return this.c.getCurrentTask();
   }

   public MiningSession.Status getStatus() {
      MiningSession session = this.c.getMiningSession();
      return session == null ? MiningSession.Status.IDLE : session.getStatus();
   }
}
