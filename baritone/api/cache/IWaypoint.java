package baritone.api.cache;

import baritone.api.utils.BetterBlockPos;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public interface IWaypoint {
   String getName();

   Tag getTag();

   long getCreationTimestamp();

   BetterBlockPos getLocation();

   public static enum Tag {
      HOME(new String[]{"home", "base"}),
      DEATH(new String[]{"death"}),
      BED(new String[]{"bed", "spawn"}),
      USER(new String[]{"user"});

      private static final List<Tag> TAG_LIST = Collections.unmodifiableList(Arrays.asList(values()));
      public final String[] names;

      private Tag(String... var3) {
         this.names = var3;
      }

      public final String getName() {
         return this.names[0];
      }

      public static Tag getByName(String var0) {
         Tag[] var1;
         int var2 = (var1 = values()).length;

         for(int var3 = 0; var3 < var2; ++var3) {
            Tag var4;
            String[] var5;
            int var6 = (var5 = (var4 = var1[var3]).names).length;

            for(int var7 = 0; var7 < var6; ++var7) {
               if (var5[var7].equalsIgnoreCase(var0)) {
                  return var4;
               }
            }
         }

         return null;
      }

      public static String[] getAllNames() {
         HashSet var0 = new HashSet();

         Tag[] var1;
         for(Tag var4 : var1 = values()) {
            var0.addAll(Arrays.asList(var4.names));
         }

         return (String[])var0.toArray(new String[0]);
      }

      // $FF: synthetic method
      private static Tag[] $values() {
         return new Tag[]{HOME, DEATH, BED, USER};
      }
   }
}
