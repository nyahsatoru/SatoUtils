package com.satomine.core;

import java.util.List;
import net.minecraft.class_2248;
import net.minecraft.class_2338;

public final class MiningSession {
   private class_2248 g;
   private List<class_2248> h = List.of();
   private MiningStrategy d;
   private MiningTraversal f;
   private MiningRegion i;
   private class_2338 c;
   private long b;
   private int j;
   private Status e;

   public MiningSession() {
      this.d = MiningStrategy.FREE;
      this.f = MiningTraversal.LAYER_BY_LAYER;
      this.e = MiningSession.Status.IDLE;
   }

   public class_2248 getTarget() {
      return this.g;
   }

   public void setTarget(class_2248 target) {
      this.g = target;
   }

   public List<class_2248> getTargetBlocks() {
      return this.h;
   }

   public void setTargetBlocks(List<class_2248> targetBlocks) {
      this.h = List.copyOf(targetBlocks);
   }

   public MiningStrategy getStrategy() {
      return this.d;
   }

   public void setStrategy(MiningStrategy strategy) {
      this.d = strategy;
   }

   public MiningTraversal getTraversal() {
      return this.f;
   }

   public void setTraversal(MiningTraversal traversal) {
      this.f = traversal;
   }

   public MiningRegion getRegion() {
      return this.i;
   }

   public void setRegion(MiningRegion region) {
      this.i = region;
   }

   public class_2338 getCurrentTarget() {
      return this.c;
   }

   public void setCurrentTarget(class_2338 currentTarget) {
      this.c = currentTarget;
   }

   public long getScannedBlocks() {
      return this.b;
   }

   public void addScannedBlocks(long count) {
      this.b += count;
   }

   public int getRemainingTargets() {
      return this.j;
   }

   public void setRemainingTargets(int remainingTargets) {
      this.j = remainingTargets;
   }

   public Status getStatus() {
      return this.e;
   }

   public void setStatus(Status status) {
      this.e = status;
   }

   public boolean isRunning() {
      return this.e == MiningSession.Status.RUNNING;
   }

   public boolean isPaused() {
      return this.e == MiningSession.Status.PAUSED;
   }

   public static enum Status {
      IDLE,
      RUNNING,
      PAUSED,
      COMPLETED,
      FAILED;

      // $FF: synthetic method
      private static Status[] b() {
         return new Status[]{IDLE, RUNNING, PAUSED, COMPLETED, FAILED};
      }
   }
}
