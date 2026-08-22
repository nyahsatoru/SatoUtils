package baritone.utils;

import baritone.Baritone;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.class_1294;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1887;
import net.minecraft.class_1893;
import net.minecraft.class_1922;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2680;
import net.minecraft.class_3489;
import net.minecraft.class_5134;
import net.minecraft.class_5321;
import net.minecraft.class_6862;
import net.minecraft.class_6880;
import net.minecraft.class_746;
import net.minecraft.class_9304;
import net.minecraft.class_9334;
import net.minecraft.class_9701;
import net.minecraft.class_9720;

public class ToolSet {
   public final Map<class_2248, Double> a = new HashMap();
   public final Function<class_2248, Double> a;
   private final class_746 a;
   private static final List<class_6862<class_1792>> a;

   public ToolSet(class_746 var1) {
      this.a = var1;
      if ((Boolean)Baritone.a().considerPotionEffects.value) {
         double var2 = (double)1.0F;
         if (this.a.method_6059(class_1294.field_5917)) {
            var2 = (double)1.0F * ((double)1.0F + (double)(this.a.method_6112(class_1294.field_5917).method_5578() + 1) * 0.2);
         }

         if (this.a.method_6059(class_1294.field_5901)) {
            switch (this.a.method_6112(class_1294.field_5901).method_5578()) {
               case 0 -> var2 *= 0.3;
               case 1 -> var2 *= 0.09;
               case 2 -> var2 *= 0.0027;
               default -> var2 *= 8.1E-4;
            }
         }

         Function var4 = (var2x) -> var2 * var2x;
         this.a = var4.compose(this::a);
      } else {
         this.a = this::a;
      }
   }

   private static int a(class_1799 var0) {
      for(int var1 = 0; var1 < a.size(); ++var1) {
         class_6862 var2 = (class_6862)a.get(var1);
         if (var0.method_31573(var2)) {
            return var1;
         }
      }

      return -1;
   }

   private static boolean a(class_1799 var0) {
      class_9304 var3;
      Iterator var1 = (var3 = var0.method_58657()).method_57534().iterator();

      while(var1.hasNext()) {
         class_6880 var2;
         if ((var2 = (class_6880)var1.next()).method_40225(class_1893.field_9099) && var3.method_57536(var2) > 0) {
            return true;
         }
      }

      return false;
   }

   public final int a(class_2248 var1, boolean var2, boolean var3) {
      if (!(Boolean)Baritone.a().autoTool.value && var3) {
         return this.a.method_31548().method_67532();
      } else {
         var3 = 0;
         double var5 = Double.NEGATIVE_INFINITY;
         int var4 = Integer.MIN_VALUE;
         boolean var7 = false;
         class_2680 var13 = var1.method_9564();

         for(int var8 = 0; var8 < 9; ++var8) {
            class_1799 var9 = this.a.method_31548().method_5438(var8);
            if (((Boolean)Baritone.a().useSwordToMine.value || !var9.method_7909().method_57347().method_57832(class_9334.field_55878)) && (!(Boolean)Baritone.a().itemSaver.value || var9.method_7919() + (Integer)Baritone.a().itemSaverThreshold.value < var9.method_7936() || var9.method_7936() <= 1)) {
               double var11 = a(var9, var13);
               boolean var10 = a(var9);
               if (var11 > var5) {
                  var5 = var11;
                  var3 = var8;
                  var4 = a(var9);
                  var7 = var10;
               } else {
                  int var15;
                  if (var11 == var5 && ((var15 = a(var9)) < var4 && (var10 || !var7) || var2 && !var7 && var10)) {
                     var5 = var11;
                     var3 = var8;
                     var4 = var15;
                     var7 = var10;
                  }
               }
            }
         }

         return var3;
      }
   }

   private double a(class_2248 var1) {
      return a(this.a.method_31548().method_5438(this.a(var1, false, true)), var1.method_9564()) * (((List)Baritone.a().blocksToAvoidBreaking.value).contains(var1) ? (Double)Baritone.a().avoidBreakingMultiplier.value : (double)1.0F);
   }

   public static double a(class_1799 var0, class_2680 var1) {
      float var2;
      try {
         var2 = var1.method_26214((class_1922)null, (class_2338)null);
      } catch (NullPointerException var9) {
         return (double)-1.0F;
      }

      if (var2 < 0.0F) {
         return (double)-1.0F;
      } else {
         float var3;
         if ((var3 = var0.method_7924(var1)) > 1.0F) {
            class_9304 var4;
            Iterator var5 = (var4 = var0.method_58657()).method_57534().iterator();

            label45:
            while(var5.hasNext()) {
               class_6880 var6;
               Iterator var7 = ((class_1887)(var6 = (class_6880)var5.next()).comp_349()).method_60034(class_9701.field_51668).iterator();

               while(var7.hasNext()) {
                  class_9720 var8;
                  if ((var8 = (class_9720)var7.next()).comp_2718().method_40225((class_5321)class_5134.field_51581.method_40230().get())) {
                     var3 += var8.comp_2719().method_60188(var4.method_57536(var6));
                     break label45;
                  }
               }
            }
         }

         var3 /= var2;
         return var1.method_29291() && (var0.method_7960() || !var0.method_7951(var1)) ? (double)(var3 / 100.0F) : (double)(var3 / 30.0F);
      }
   }

   static {
      a = List.of(class_3489.field_52381, class_3489.field_23802, class_3489.field_52382, class_3489.field_52385, class_3489.field_52386, class_3489.field_52387);
   }
}
