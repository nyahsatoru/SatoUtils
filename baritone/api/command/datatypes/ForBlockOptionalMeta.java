package baritone.api.command.datatypes;

import baritone.api.command.helpers.TabCompleteHelper;
import baritone.api.utils.BlockOptionalMeta;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.class_2248;
import net.minecraft.class_2769;
import net.minecraft.class_2960;
import net.minecraft.class_7923;

public enum ForBlockOptionalMeta implements IDatatypeFor<BlockOptionalMeta> {
   INSTANCE;

   private static Pattern PATTERN = Pattern.compile("(?:[a-z0-9_.-]+:)?(?:[a-z0-9/_.-]+(?:\\[(?:(?:[a-z0-9_.-]+=[a-z0-9_.-]+,)*(?:[a-z0-9_.-]+(?:=(?:[a-z0-9_.-]+(?:\\])?)?)?)?|\\])?)?)?");

   public final BlockOptionalMeta get(IDatatypeContext var1) {
      return new BlockOptionalMeta(var1.getConsumer().getString());
   }

   public final Stream<String> tabComplete(IDatatypeContext var1) {
      String var2 = var1.getConsumer().peekString();
      if (!PATTERN.matcher(var2).matches()) {
         var1.getConsumer().getString();
         return Stream.empty();
      } else if (var2.endsWith("]")) {
         var1.getConsumer().getString();
         return Stream.empty();
      } else if (!var2.contains("[")) {
         return var1.getConsumer().tabCompleteDatatype(BlockById.INSTANCE);
      } else {
         var1.getConsumer().getString();
         String[] var4;
         String var6 = (var4 = splitLast(var2, '['))[0];
         String var3 = var4[1];
         class_2248 var14;
         if ((var14 = (class_2248)class_7923.field_41175.method_17966(class_2960.method_60654(var6)).orElse((Object)null)) == null) {
            return Stream.empty();
         } else {
            String[] var5;
            var6 = ((Object[])(var5 = splitLast(var3, ',')))[0];
            if (!(var3 = ((Object[])var5)[1]).contains("=")) {
               Set var16 = (Set)Stream.of(var6.split(",")).map((var0) -> var0.split("=")[0]).collect(Collectors.toSet());
               var6 = var2.substring(0, var2.length() - var3.length());
               return (new TabCompleteHelper()).append(var14.method_9595().method_11659().stream().map(class_2769::method_11899)).filter((var1x) -> !var16.contains(var1x)).filterPrefix(var3).sortAlphabetically().map((var1x) -> var6 + var1x).stream();
            } else {
               String[] var12;
               var5 = (var12 = splitLast(var3, '='))[0];
               var6 = var12[1];
               var3 = var2.substring(0, var2.length() - var6.length());
               class_2769 var10;
               return (var10 = var14.method_9595().method_11663(var5)) == null ? Stream.empty() : (new TabCompleteHelper()).append(getValues(var10)).filterPrefix(var6).sortAlphabetically().map((var1x) -> var3 + var1x).stream();
            }
         }
      }
   }

   private static String[] splitLast(String var0, char var1) {
      int var2;
      return (var2 = var0.lastIndexOf(var1)) == -1 ? new String[]{"", var0} : new String[]{var0.substring(0, var2), var0.substring(var2 + 1)};
   }

   private static <T extends Comparable<T>> Stream<String> getValues(class_2769<T> var0) {
      Stream var10000 = var0.method_11898().stream();
      Objects.requireNonNull(var0);
      return var10000.map(var0::method_11901);
   }

   // $FF: synthetic method
   private static ForBlockOptionalMeta[] $values() {
      return new ForBlockOptionalMeta[]{INSTANCE};
   }
}
