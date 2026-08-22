package com.nyahsatoru.satoaddon.b;

import java.util.LinkedHashSet;
import java.util.List;
import net.minecraft.class_1747;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_2480;
import net.minecraft.class_746;
import net.minecraft.class_7923;
import net.minecraft.class_9334;

public final class b {
   private static final int b = 36;

   public int b(class_746 player) {
      if (player == null) {
         return 0;
      } else {
         int free = 0;

         for(int slot = 0; slot < 36; ++slot) {
            if (player.method_31548().method_5438(slot).method_7960()) {
               ++free;
            }
         }

         return free;
      }
   }

   public List<String> b(class_746 var1, List<class_1792> var2, List<class_1792> var3, boolean var4, boolean var5) {
      if (var1 == null) {
         return List.of();
      } else {
         LinkedHashSet var6 = new LinkedHashSet();

         for(Object var8 : var3) {
            var6.add(class_7923.field_41178.method_10221(var8).toString());
         }

         return List.copyOf(var6);
      }
   }

   private boolean b(List<class_1792> items, class_1792 item) {
      return items != null && items.contains(item);
   }

   private boolean b(class_1799 stack) {
      if (stack.method_7963()) {
         return true;
      } else if (!stack.method_31574(class_1802.field_8849) && !stack.method_31574(class_1802.field_8466)) {
         if (!stack.method_31574(class_1802.field_27023) && !stack.method_31574(class_1802.field_8598)) {
            class_1792 var3 = stack.method_7909();
            if (var3 instanceof class_1747) {
               class_1747 blockItem = (class_1747)var3;
               if (blockItem.method_7711() instanceof class_2480) {
                  return true;
               }
            }

            return !stack.method_57826(class_9334.field_49631) && !stack.method_57826(class_9334.field_49628) ? stack.method_7942() : true;
         } else {
            return true;
         }
      } else {
         return true;
      }
   }
}
