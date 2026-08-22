package baritone.pathing.movement;

import baritone.Baritone;
import baritone.api.IBaritone;
import baritone.api.utils.BetterBlockPos;
import baritone.cache.WorldData;
import baritone.pathing.precompute.PrecomputedData;
import baritone.utils.BlockStateInterface;
import baritone.utils.ToolSet;
import baritone.utils.pathing.BetterWorldBorder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import net.minecraft.class_1304;
import net.minecraft.class_1661;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1887;
import net.minecraft.class_1893;
import net.minecraft.class_1937;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2680;
import net.minecraft.class_5134;
import net.minecraft.class_5321;
import net.minecraft.class_6880;
import net.minecraft.class_746;
import net.minecraft.class_9304;
import net.minecraft.class_9701;
import net.minecraft.class_9720;

public class CalculationContext {
   private static final class_1799 a;
   public final boolean a;
   public final IBaritone a;
   public final class_1937 a;
   public final WorldData a;
   public final BlockStateInterface a;
   public final ToolSet a;
   public final boolean b;
   public final boolean c;
   public final boolean d;
   public final double a;
   public final boolean e;
   public final List<class_2248> a;
   public final boolean f;
   public final boolean g;
   public final boolean h;
   public final boolean i;
   public final boolean j;
   public boolean k;
   public final int a;
   public final boolean l;
   public final boolean m;
   public final boolean n;
   public int b;
   public int c;
   public final int d;
   public final double b;
   public final double c;
   public double d;
   public double e;
   public final double f;
   public final BetterWorldBorder a;
   public final PrecomputedData a;

   public CalculationContext(IBaritone var1) {
      this(var1, false);
   }

   public CalculationContext(IBaritone var1, boolean var2) {
      this.a = new PrecomputedData();
      this.a = (boolean)var2;
      this.a = var1;
      class_746 var3 = var1.getPlayerContext().player();
      this.a = var1.getPlayerContext().world();
      this.a = (WorldData)var1.getPlayerContext().worldData();
      this.a = new BlockStateInterface(var1.getPlayerContext(), (boolean)var2);
      this.a = new ToolSet(var3);
      this.c = (Boolean)Baritone.a().allowPlace.value && ((Baritone)var1).a.a();
      this.b = (Boolean)Baritone.a().allowWaterBucketFall.value && class_1661.method_7380(var3.method_31548().method_7395(a)) && this.a.method_27983() != class_1937.field_25180;
      this.d = (Boolean)Baritone.a().allowSprint.value && var3.method_7344().method_7586() > 6;
      this.a = (Double)Baritone.a().blockPlacementPenalty.value;
      this.e = (Boolean)Baritone.a().allowBreak.value;
      this.a = new ArrayList((Collection)Baritone.a().allowBreakAnyway.value);
      this.f = (Boolean)Baritone.a().allowParkour.value;
      this.g = (Boolean)Baritone.a().allowParkourPlace.value;
      this.h = (Boolean)Baritone.a().allowJumpAtBuildLimit.value;
      this.i = (Boolean)Baritone.a().allowParkourAscend.value;
      this.j = (Boolean)Baritone.a().assumeWalkOnWater.value;
      this.k = false;
      var2 = 0;

      for(class_1304 var6 : var13 = class_1304.values()) {
         class_9304 var7;
         Iterator var8 = (var7 = var1.getPlayerContext().player().method_6118(var6).method_58657()).method_57534().iterator();

         while(var8.hasNext()) {
            class_6880 var9;
            if ((var9 = (class_6880)var8.next()).method_40225(class_1893.field_9122)) {
               var2 = var7.method_57536(var9);
            }
         }
      }

      this.a = var2;
      this.l = (Boolean)Baritone.a().allowDiagonalDescend.value;
      this.m = (Boolean)Baritone.a().allowDiagonalAscend.value;
      this.n = (Boolean)Baritone.a().allowDownward.value;
      this.b = 3;
      this.c = (Integer)Baritone.a().maxFallHeightNoWater.value;
      this.d = (Integer)Baritone.a().maxFallHeightBucket.value;
      float var14 = 1.0F;

      class_1304[] var15;
      label51:
      for(class_1304 var18 : var15 = class_1304.values()) {
         class_9304 var20;
         Iterator var21 = (var20 = var1.getPlayerContext().player().method_6118(var18).method_58657()).method_57534().iterator();

         while(var21.hasNext()) {
            class_6880 var12;
            Iterator var19 = ((class_1887)(var12 = (class_6880)var21.next()).comp_349()).method_60034(class_9701.field_51668).iterator();

            while(var19.hasNext()) {
               class_9720 var10;
               if ((var10 = (class_9720)var19.next()).comp_2718().method_40225((class_5321)class_5134.field_51578.method_40230().get())) {
                  var14 = var10.comp_2719().method_60188(var20.method_57536(var12));
                  break label51;
               }
            }
         }
      }

      this.b = 9.09090909090909 * (double)(1.0F - var14) + 4.63284688441047 * (double)var14;
      this.c = (Double)Baritone.a().blockBreakAdditionalPenalty.value;
      this.d = (Double)Baritone.a().backtrackCostFavoringCoefficient.value;
      this.e = (Double)Baritone.a().jumpPenalty.value;
      this.f = (Double)Baritone.a().walkOnWaterOnePenalty.value;
      this.a = new BetterWorldBorder(this.a.method_8621());
   }

   public final class_2680 a(int var1, int var2, int var3) {
      return this.a.a(var1, var2, var3);
   }

   public final class_2680 a(BetterBlockPos var1) {
      return this.a(((class_2338)var1).method_10263(), ((class_2338)var1).method_10264(), ((class_2338)var1).method_10260());
   }

   public final class_2248 a(int var1, int var2, int var3) {
      return this.a(var1, var2, var3).method_26204();
   }

   public double a(int var1, int var2, int var3, class_2680 var4) {
      if (!this.c) {
         return (double)1000000.0F;
      } else if (!this.a.b(var1, var3)) {
         return (double)1000000.0F;
      } else if (!(Boolean)Baritone.a().allowPlaceInFluidsSource.value && var4.method_26227().method_15771()) {
         return (double)1000000.0F;
      } else {
         return !(Boolean)Baritone.a().allowPlaceInFluidsFlow.value && !var4.method_26227().method_15769() && !var4.method_26227().method_15771() ? (double)1000000.0F : this.a;
      }
   }

   public double b(int var1, int var2, int var3, class_2680 var4) {
      return !this.e && !this.a.contains(var4.method_26204()) ? (double)1000000.0F : (double)1.0F;
   }

   public double a() {
      return this.a;
   }

   static {
      a = new class_1799(class_1802.field_8705);
   }
}
