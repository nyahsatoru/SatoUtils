package com.satomine.b;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import meteordevelopment.meteorclient.pathing.BaritoneUtils;
import meteordevelopment.meteorclient.pathing.IPathManager;
import meteordevelopment.meteorclient.pathing.PathManagers;
import net.minecraft.class_1792;
import net.minecraft.class_1802;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_310;

public final class d {
   private Object k;
   private Boolean b;
   private Object d;
   private Boolean h;
   private Object i;
   private Boolean c;
   private Object f;
   private Object e;
   private Object g;
   private Double j;

   public boolean d() {
      IPathManager manager = this.e();
      return BaritoneUtils.IS_AVAILABLE && manager != null && !"none".equalsIgnoreCase(manager.getName());
   }

   public boolean b(List<class_2248> targets) {
      if (this.d() && targets != null && !targets.isEmpty()) {
         try {
            this.e().mine((class_2248[])targets.toArray((x$0) -> new class_2248[x$0]));
            return true;
         } catch (RuntimeException var3) {
            return false;
         }
      } else {
         return false;
      }
   }

   public boolean c(class_2338 target) {
      if (this.d() && target != null) {
         try {
            this.e().moveTo(target);
            return true;
         } catch (RuntimeException var3) {
            return false;
         }
      } else {
         return false;
      }
   }

   public boolean b(class_2338 target) {
      if (this.d() && target != null) {
         try {
            Class<?> apiClass = Class.forName("baritone.api.BaritoneAPI");
            Object provider = apiClass.getMethod("getProvider").invoke((Object)null);
            Object baritone = this.b(provider);
            if (baritone == null) {
               return false;
            } else {
               Class<?> goalClass = Class.forName("baritone.api.pathing.goals.Goal");
               Class<?> goalBlockClass = Class.forName("baritone.api.pathing.goals.GoalBlock");
               Object goal = goalBlockClass.getConstructor(class_2338.class).newInstance(target);
               Object customGoalProcess = baritone.getClass().getMethod("getCustomGoalProcess").invoke(baritone);
               customGoalProcess.getClass().getMethod("setGoalAndPath", goalClass).invoke(customGoalProcess, goal);
               return true;
            }
         } catch (RuntimeException | ReflectiveOperationException var9) {
            return false;
         }
      } else {
         return false;
      }
   }

   public Optional<_b> i() {
      try {
         Class<?> apiClass = Class.forName("baritone.api.BaritoneAPI");
         Object provider = apiClass.getMethod("getProvider").invoke((Object)null);
         Object baritone = this.b(provider);
         if (baritone == null) {
            return Optional.empty();
         } else {
            Object selectionManager = baritone.getClass().getMethod("getSelectionManager").invoke(baritone);
            Object selection = this.c(selectionManager);
            if (selection == null) {
               return Optional.empty();
            } else {
               class_2338 min = this.d(selection.getClass().getMethod("min").invoke(selection));
               class_2338 max = this.d(selection.getClass().getMethod("max").invoke(selection));
               class_2338 pos1 = this.d(selection.getClass().getMethod("pos1").invoke(selection));
               class_2338 pos2 = this.d(selection.getClass().getMethod("pos2").invoke(selection));
               return min != null && max != null && pos1 != null && pos2 != null ? Optional.of(new _b(min, max, pos1, pos2)) : Optional.empty();
            }
         }
      } catch (RuntimeException | ReflectiveOperationException var10) {
         return Optional.empty();
      }
   }

   private Object b(Object provider) throws ReflectiveOperationException {
      class_310 client = class_310.method_1551();

      try {
         return provider.getClass().getMethod("getBaritoneForMinecraft", class_310.class).invoke(provider, client);
      } catch (NoSuchMethodException var4) {
         return provider.getClass().getMethod("getPrimaryBaritone").invoke(provider);
      }
   }

   private Object c(Object selectionManager) throws ReflectiveOperationException {
      Object selections = selectionManager.getClass().getMethod("getSelections").invoke(selectionManager);
      return selections != null && Array.getLength(selections) > 0 ? Array.get(selections, Array.getLength(selections) - 1) : selectionManager.getClass().getMethod("getLastSelection").invoke(selectionManager);
   }

   public boolean l() {
      return this.b(IPathManager::pause);
   }

   public boolean c() {
      return this.b(IPathManager::resume);
   }

   public boolean b() {
      return this.b(IPathManager::stop);
   }

   public boolean h() {
      IPathManager manager = this.e();
      return manager != null && manager.isPathing();
   }

   public String g() {
      IPathManager manager = this.e();
      return manager == null ? "unavailable" : manager.getName();
   }

   public boolean j() {
      try {
         Object settings = Class.forName("baritone.api.BaritoneAPI").getMethod("getSettings").invoke((Object)null);
         Object setting = settings.getClass().getField("allowSprint").get(settings);
         Field valueField = setting.getClass().getField("value");
         if (this.b == null) {
            this.b = (Boolean)valueField.get(setting);
            this.k = setting;
         }

         valueField.set(setting, true);
         return true;
      } catch (RuntimeException | ReflectiveOperationException var4) {
         return false;
      }
   }

   public void f() {
      if (this.k != null && this.b != null) {
         try {
            this.k.getClass().getField("value").set(this.k, this.b);
         } catch (RuntimeException | ReflectiveOperationException var5) {
         } finally {
            this.k = null;
            this.b = null;
         }

      }
   }

   public boolean c(List<class_2248> supportBlocks) {
      if (supportBlocks != null && !supportBlocks.isEmpty()) {
         List<class_1792> supportItems = new ArrayList();

         for(class_2248 block : supportBlocks) {
            if (block != null && block.method_8389() != class_1802.field_8162 && !supportItems.contains(block.method_8389())) {
               supportItems.add(block.method_8389());
            }
         }

         if (supportItems.isEmpty()) {
            return false;
         } else {
            try {
               Object settings = Class.forName("baritone.api.BaritoneAPI").getMethod("getSettings").invoke((Object)null);
               this.d = this.b(settings, "allowPlace", this.d);
               this.i = this.b(settings, "allowParkourPlace", this.i);
               this.f = this.b(settings, "acceptableThrowawayItems", this.f);
               this.g = this.b(settings, "blockPlacementPenalty", this.g);
               Field allowPlaceValue = this.d.getClass().getField("value");
               Field parkourPlaceValue = this.i.getClass().getField("value");
               Field throwawayValue = this.f.getClass().getField("value");
               Field penaltyValue = this.g.getClass().getField("value");
               if (this.h == null) {
                  this.h = (Boolean)allowPlaceValue.get(this.d);
               }

               if (this.c == null) {
                  this.c = (Boolean)parkourPlaceValue.get(this.i);
               }

               if (this.e == null) {
                  this.e = throwawayValue.get(this.f);
               }

               if (this.j == null) {
                  this.j = (Double)penaltyValue.get(this.g);
               }

               allowPlaceValue.set(this.d, true);
               parkourPlaceValue.set(this.i, true);
               throwawayValue.set(this.f, List.copyOf(supportItems));
               penaltyValue.set(this.g, (double)0.0F);
               return true;
            } catch (RuntimeException | ReflectiveOperationException var8) {
               this.k();
               return false;
            }
         }
      } else {
         return false;
      }
   }

   public void k() {
      this.b(this.d, this.h);
      this.b(this.i, this.c);
      this.b(this.f, this.e);
      this.b(this.g, this.j);
      this.d = null;
      this.h = null;
      this.i = null;
      this.c = null;
      this.f = null;
      this.e = null;
      this.g = null;
      this.j = null;
   }

   private Object b(Object settings, String fieldName, Object current) throws ReflectiveOperationException {
      return current != null ? current : settings.getClass().getField(fieldName).get(settings);
   }

   private void b(Object setting, Object value) {
      if (setting != null && value != null) {
         try {
            setting.getClass().getField("value").set(setting, value);
         } catch (RuntimeException | ReflectiveOperationException var4) {
         }

      }
   }

   private class_2338 d(Object value) {
      class_2338 var10000;
      if (value instanceof class_2338 pos) {
         var10000 = pos;
      } else {
         var10000 = null;
      }

      return var10000;
   }

   private boolean b(Consumer<IPathManager> action) {
      IPathManager manager = this.e();
      if (!this.d()) {
         return false;
      } else {
         try {
            action.accept(manager);
            return true;
         } catch (RuntimeException var4) {
            return false;
         }
      }
   }

   private IPathManager e() {
      try {
         return PathManagers.get();
      } catch (RuntimeException var2) {
         return null;
      }
   }

   public static record _b(class_2338 d, class_2338 b, class_2338 e, class_2338 c) {
      public class_2338 c() {
         return this.b;
      }

      public class_2338 b() {
         return this.e;
      }

      public class_2338 e() {
         return this.c;
      }
   }
}
