package baritone.behavior;

import baritone.Baritone;
import baritone.api.Settings;
import baritone.api.behavior.ILookBehavior;
import baritone.api.behavior.look.IAimProcessor;
import baritone.api.behavior.look.ITickableAimProcessor;
import baritone.api.event.events.PacketEvent;
import baritone.api.event.events.PlayerUpdateEvent;
import baritone.api.event.events.RotationMoveEvent;
import baritone.api.event.events.TickEvent;
import baritone.api.event.events.WorldEvent;
import baritone.api.utils.IPlayerContext;
import baritone.api.utils.Rotation;
import baritone.behavior.look.ForkableRandom;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import net.minecraft.class_2828;

public final class LookBehavior extends Behavior implements ILookBehavior {
   public Target a;
   public Rotation a;
   private Rotation b;
   public final AimProcessor a;
   private final Deque<Float> a;
   private final Deque<Float> b;

   public LookBehavior(Baritone var1) {
      super(var1);
      this.a = new AimProcessor(var1.getPlayerContext());
      this.a = new ArrayDeque();
      this.b = new ArrayDeque();
   }

   public final void updateTarget(Rotation var1, boolean var2) {
      this.a = new Target(var1, LookBehavior.Target.Mode.a(super.a, var2));
   }

   public final IAimProcessor getAimProcessor() {
      return this.a;
   }

   public final void onTick(TickEvent var1) {
      if (var1.getType() == TickEvent.Type.IN) {
         this.a.tick();
      }

   }

   public final void onPlayerUpdate(PlayerUpdateEvent var1) {
      if (this.a != null) {
         switch (var1.getState()) {
            case PRE:
               if (this.a.a == LookBehavior.Target.Mode.b) {
                  return;
               }

               this.b = new Rotation(super.a.player().method_36454(), super.a.player().method_36455());
               Rotation var2 = this.a.peekRotation(this.a.a);
               super.a.player().method_36456(var2.getYaw());
               super.a.player().method_36457(var2.getPitch());
               return;
            case POST:
               if (this.b != null) {
                  this.a.addLast(this.a.a.getYaw());

                  while(this.a.size() > (Integer)Baritone.a().smoothLookTicks.value) {
                     this.a.removeFirst();
                  }

                  this.b.addLast(this.a.a.getPitch());

                  while(this.b.size() > (Integer)Baritone.a().smoothLookTicks.value) {
                     this.b.removeFirst();
                  }

                  if (this.a.a == LookBehavior.Target.Mode.a) {
                     super.a.player().method_36456(this.b.getYaw());
                     super.a.player().method_36457(this.b.getPitch());
                  } else if (super.a.player().method_6128() ? (Boolean)Baritone.a().elytraSmoothLook.value : (Boolean)Baritone.a().smoothLook.value) {
                     super.a.player().method_36456((float)this.a.stream().mapToDouble((var0) -> (double)var0).average().orElse((double)this.b.getYaw()));
                     if (super.a.player().method_6128()) {
                        super.a.player().method_36457((float)this.b.stream().mapToDouble((var0) -> (double)var0).average().orElse((double)this.b.getPitch()));
                     }
                  }

                  this.b = null;
               }

               this.a = null;
            default:
         }
      }
   }

   public final void onSendPacket(PacketEvent var1) {
      if (var1.getPacket() instanceof class_2828) {
         class_2828 var2;
         if ((var2 = (class_2828)var1.getPacket()) instanceof class_2828.class_2831 || var2 instanceof class_2828.class_2830) {
            this.a = new Rotation(var2.method_12271(0.0F), var2.method_12270(0.0F));
         }

      }
   }

   public final void onWorldEvent(WorldEvent var1) {
      this.a = null;
      this.a = null;
   }

   public final void onPlayerRotationMove(RotationMoveEvent var1) {
      if (this.a != null) {
         Rotation var2 = this.a.peekRotation(this.a.a);
         var1.setYaw(var2.getYaw());
         var1.setPitch(var2.getPitch());
      }

   }

   public abstract static class AbstractAimProcessor implements ITickableAimProcessor {
      protected final IPlayerContext a;
      private final ForkableRandom a;
      private double a;
      private double b;

      public AbstractAimProcessor(IPlayerContext var1) {
         this.a = var1;
         this.a = new ForkableRandom();
      }

      AbstractAimProcessor(AbstractAimProcessor var1) {
         this.a = var1.a;
         ForkableRandom var2 = var1.a;
         this.a = new ForkableRandom(Arrays.copyOf(var2.a, 4));
         this.a = var1.a;
         this.b = var1.b;
      }

      public final Rotation peekRotation(Rotation var1) {
         Rotation var2 = this.a();
         float var3 = var1.getYaw();
         float var4;
         if ((var4 = var1.getPitch()) == var2.getPitch()) {
            var4 = var4 < -20.0F ? var4 + 1.0F : (var4 > 10.0F ? var4 - 1.0F : var4);
         }

         var3 = (float)((double)var3 + this.a);
         var4 = (float)((double)var4 + this.b);
         return (new Rotation(this.a(var2.getYaw(), var3), this.a(var2.getPitch(), var4))).clamp();
      }

      public final void tick() {
         this.a = (this.a.a() - (double)0.5F) * (Double)Baritone.a().randomLooking.value;
         this.b = (this.a.a() - (double)0.5F) * (Double)Baritone.a().randomLooking.value;
         double var1;
         if (Math.abs(var1 = this.a.a() - (double)0.5F) < 0.1) {
            var1 *= (double)4.0F;
         }

         this.a += var1 * (Double)Baritone.a().randomLooking113.value;
      }

      public final void advance(int var1) {
         for(int var2 = 0; var2 < var1; ++var2) {
            this.tick();
         }

      }

      public Rotation nextRotation(Rotation var1) {
         var1 = this.peekRotation(var1);
         this.tick();
         return var1;
      }

      public final ITickableAimProcessor fork() {
         return new AbstractAimProcessor(this) {
            private Rotation a;
            // $FF: synthetic field
            private LookBehavior.AbstractAimProcessor a;

            {
               this.a = var1;
               super(var2);
               this.a = this.a.a();
            }

            public Rotation nextRotation(Rotation var1) {
               return this.a = super.nextRotation(var1);
            }

            protected final Rotation a() {
               return this.a;
            }
         };
      }

      protected abstract Rotation a();

      private float a(float var1, float var2) {
         var2 -= var1;
         double var3 = this.a(var2);
         return var1 + this.a(var3);
      }

      private double a(float var1) {
         float var2 = this.a((double)1.0F);
         return (double)Math.round(var1 / var2);
      }

      private float a(double var1) {
         double var3 = (Double)this.a.minecraft().field_1690.method_42495().method_41753() * (double)0.6F + (double)0.2F;
         return (float)(var1 * var3 * var3 * var3 * (double)8.0F) * 0.15F;
      }
   }

   public static final class AimProcessor extends AbstractAimProcessor {
      public AimProcessor(IPlayerContext var1) {
         super(var1);
      }

      protected final Rotation a() {
         return super.a.playerRotations();
      }
   }

   public static class Target {
      public final Rotation a;
      public final Mode a;

      public Target(Rotation var1, Mode var2) {
         this.a = var1;
         this.a = var2;
      }

      static enum Mode {
         c,
         a,
         b;

         static Mode a(IPlayerContext var0, boolean var1) {
            Settings var2;
            boolean var3 = (Boolean)(var2 = Baritone.a()).antiCheatCompatibility.value;
            boolean var4 = (Boolean)var2.blockFreeLook.value;
            if (var0.player().method_6128()) {
               return (Boolean)var2.elytraFreeLook.value ? a : c;
            } else if ((Boolean)var2.freeLook.value) {
               if (var1) {
                  return var4 ? a : c;
               } else {
                  return var3 ? a : b;
               }
            } else {
               return c;
            }
         }
      }
   }
}
