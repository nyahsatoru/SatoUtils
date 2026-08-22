package baritone.cache;

import baritone.Baritone;
import baritone.api.cache.IWorldData;
import baritone.api.cache.IWorldProvider;
import baritone.api.utils.IPlayerContext;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.class_1937;
import net.minecraft.class_2960;
import net.minecraft.class_3545;
import net.minecraft.class_5218;
import net.minecraft.class_642;
import org.apache.commons.lang3.SystemUtils;

public class WorldProvider implements IWorldProvider {
   private static final Map<Path, WorldData> a = new HashMap();
   private final Baritone a;
   private final IPlayerContext a;
   private WorldData a;
   private class_1937 a;

   public WorldProvider(Baritone var1) {
      this.a = var1;
      this.a = var1.getPlayerContext();
   }

   public final WorldData a() {
      this.b();
      return this.a;
   }

   public final void a(class_1937 var1) {
      Optional var10000;
      label29: {
         Path var2;
         Path var4;
         if (this.a.minecraft().method_1496()) {
            if ((var4 = this.a.minecraft().method_1576().method_27050(class_5218.field_24188)).relativize(this.a.minecraft().field_1697.toPath()).getNameCount() != 2) {
               var4 = var4.getParent();
            }

            var2 = var4 = var4.resolve("baritone");
         } else {
            class_642 var5;
            if ((var5 = this.a.minecraft().method_1558()) == null) {
               System.out.println("World seems to be a replay. Not loading Baritone cache.");
               this.a = null;
               this.a = this.a.world();
               var10000 = Optional.empty();
               break label29;
            }

            String var6 = var5.method_52811() ? "realms" : var5.field_3761;
            if (SystemUtils.IS_OS_WINDOWS) {
               var6 = var6.replace(":", "_");
            }

            var4 = this.a.a.resolve(var6);
            var2 = this.a.a;
         }

         var10000 = Optional.of(new class_3545(var4, var2));
      }

      var10000.ifPresent((var2x) -> {
         Path var3 = (Path)var2x.method_15442();
         Path var8 = (Path)var2x.method_15441();

         try {
            Files.createDirectories(var8);
            Files.write(var8.resolve("readme.txt"), "https://github.com/cabaletta/baritone\n".getBytes(StandardCharsets.US_ASCII), new OpenOption[0]);
         } catch (IOException var7) {
         }

         var8 = var3;
         class_2960 var4 = var1.method_27983().method_29177();
         int var11 = var1.method_8597().comp_653();
         Path var10000 = var8.resolve(var4.method_12836());
         String var10001 = var4.method_12832();
         var8 = var10000.resolve(var10001 + "_" + var11);

         try {
            Files.createDirectories(var8);
         } catch (IOException var6) {
         }

         System.out.println("Baritone world data dir: " + String.valueOf(var8));
         synchronized(a) {
            this.a = (WorldData)a.computeIfAbsent(var8, (var1x) -> new WorldData(var1x, var1.method_8597(), var1.method_27983()));
         }

         this.a = this.a.world();
      });
   }

   public final void a() {
      WorldData var1 = this.a;
      this.a = null;
      this.a = null;
      if (var1 != null) {
         var1.a();
      }
   }

   private void b() {
      if (this.a != this.a.world()) {
         if (this.a != null) {
            System.out.println("mc.world unloaded unnoticed! Unloading Baritone cache now.");
            this.a();
         }

         if (this.a.world() != null) {
            System.out.println("mc.world loaded unnoticed! Loading Baritone cache now.");
            this.a(this.a.world());
            return;
         }
      } else if (this.a == null && this.a.world() != null && (this.a.minecraft().method_1496() || this.a.minecraft().method_1558() != null)) {
         System.out.println("Retrying to load Baritone cache");
         this.a(this.a.world());
      }

   }

   // $FF: synthetic method
   public IWorldData getCurrentWorld() {
      return this.a();
   }
}
