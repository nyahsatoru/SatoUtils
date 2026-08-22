package baritone.api.utils;

public enum BooleanBinaryOperators implements BooleanBinaryOperator {
   OR((var0, var1) -> var0 || var1),
   AND((var0, var1) -> var0 && var1),
   XOR((var0, var1) -> var0 ^ var1);

   private final BooleanBinaryOperator op;

   private BooleanBinaryOperators(BooleanBinaryOperator var3) {
      this.op = var3;
   }

   public final boolean applyAsBoolean(boolean var1, boolean var2) {
      return this.op.applyAsBoolean(var1, var2);
   }

   // $FF: synthetic method
   private static BooleanBinaryOperators[] $values() {
      return new BooleanBinaryOperators[]{OR, AND, XOR};
   }
}
