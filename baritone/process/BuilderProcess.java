package baritone.process;

import baritone.Baritone;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalBlock;
import baritone.api.pathing.goals.GoalComposite;
import baritone.api.pathing.goals.GoalGetToBlock;
import baritone.api.process.IBuilderProcess;
import baritone.api.process.PathingCommand;
import baritone.api.process.PathingCommandType;
import baritone.api.schematic.FillSchematic;
import baritone.api.schematic.ISchematic;
import baritone.api.schematic.IStaticSchematic;
import baritone.api.schematic.MaskSchematic;
import baritone.api.schematic.MirroredSchematic;
import baritone.api.schematic.RotatedSchematic;
import baritone.api.schematic.SubstituteSchematic;
import baritone.api.schematic.format.ISchematicFormat;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.IPlayerContext;
import baritone.api.utils.RayTraceUtils;
import baritone.api.utils.Rotation;
import baritone.api.utils.RotationUtils;
import baritone.api.utils.SettingsUtil;
import baritone.api.utils.input.Input;
import baritone.behavior.InventoryBehavior;
import baritone.pathing.movement.CalculationContext;
import baritone.pathing.movement.Movement;
import baritone.pathing.movement.MovementHelper;
import baritone.utils.BaritoneProcessHelper;
import baritone.utils.BlockStateInterface;
import baritone.utils.PathingCommandContext;
import baritone.utils.schematic.MapArtSchematic;
import baritone.utils.schematic.SchematicSystem;
import baritone.utils.schematic.SelectionSchematic;
import baritone.utils.schematic.litematica.LitematicaHelper;
import baritone.utils.schematic.schematica.SchematicaHelper;
import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1747;
import net.minecraft.class_1750;
import net.minecraft.class_1799;
import net.minecraft.class_1838;
import net.minecraft.class_2189;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_238;
import net.minecraft.class_2382;
import net.minecraft.class_2383;
import net.minecraft.class_239;
import net.minecraft.class_2404;
import net.minecraft.class_2415;
import net.minecraft.class_2429;
import net.minecraft.class_243;
import net.minecraft.class_2465;
import net.minecraft.class_2470;
import net.minecraft.class_2510;
import net.minecraft.class_2533;
import net.minecraft.class_265;
import net.minecraft.class_2680;
import net.minecraft.class_2769;
import net.minecraft.class_3545;
import net.minecraft.class_3965;
import net.minecraft.class_239.class_240;

public final class BuilderProcess extends BaritoneProcessHelper implements IBuilderProcess {
   private static final Set<class_2769<?>> a;
   private HashSet<BetterBlockPos> a;
   private LongOpenHashSet a;
   private String a;
   private ISchematic b;
   public ISchematic a;
   public class_2382 a;
   private int a;
   private boolean a;
   private int b;
   private int c;
   public List<class_2680> a;
   private int d = 0;

   public BuilderProcess(Baritone var1) {
      super(var1);
   }

   public final void build(String var1, ISchematic var2, class_2382 var3) {
      this.a = var1;
      this.a = var2;
      this.b = null;
      boolean var6 = var2 instanceof SelectionSchematic;
      if (!((Map)Baritone.a().buildSubstitutes.value).isEmpty()) {
         this.a = new SubstituteSchematic(this.a, (Map)Baritone.a().buildSubstitutes.value);
      }

      if (Baritone.a().buildSchematicMirror.value != class_2415.field_11302) {
         this.a = new MirroredSchematic(this.a, (class_2415)Baritone.a().buildSchematicMirror.value);
      }

      if (Baritone.a().buildSchematicRotation.value != class_2470.field_11467) {
         this.a = new RotatedSchematic(this.a, (class_2470)Baritone.a().buildSchematicRotation.value);
      }

      this.a = new MaskSchematic(this.a) {
         public boolean partOfMask(int var1, int var2, int var3, class_2680 var4) {
            return !((List)Baritone.a().buildSkipBlocks.value).contains(((MaskSchematic)this).desiredState(var1, var2, var3, var4, Collections.emptyList()).method_26204());
         }
      };
      int var4 = var3.method_10263();
      int var5 = var3.method_10264();
      int var8 = var3.method_10260();
      if ((Boolean)Baritone.a().schematicOrientationX.value) {
         var4 += var2.widthX();
      }

      if ((Boolean)Baritone.a().schematicOrientationY.value) {
         var5 += var2.heightY();
      }

      if ((Boolean)Baritone.a().schematicOrientationZ.value) {
         var8 += var2.lengthZ();
      }

      this.a = new class_2382(var4, var5, var8);
      this.a = false;
      this.b = (Integer)Baritone.a().startAtLayer.value;
      this.d = var2.heightY();
      if ((Boolean)Baritone.a().buildOnlySelection.value && var6) {
         if (super.a.a.getSelections().length == 0) {
            this.logDirect("Poor little kitten forgot to set a selection while BuildOnlySelection is true");
            this.d = 0;
         } else if ((Boolean)Baritone.a().buildInLayers.value) {
            OptionalInt var7 = Stream.of(super.a.a.getSelections()).mapToInt((var0) -> var0.min().y).min();
            OptionalInt var9 = Stream.of(super.a.a.getSelections()).mapToInt((var0) -> var0.max().y).max();
            if (var7.isPresent() && var9.isPresent()) {
               var4 = (Boolean)Baritone.a().layerOrder.value ? var5 + var2.heightY() - var9.getAsInt() : var7.getAsInt() - var5;
               this.d = ((Boolean)Baritone.a().layerOrder.value ? var5 + var2.heightY() - var7.getAsInt() : var9.getAsInt() - var5) + 1;
               this.b = Math.max(this.b, var4 / (Integer)Baritone.a().layerHeight.value);
               this.logDebug(String.format("Schematic starts at y=%s with height %s", var5, var2.heightY()));
               this.logDebug(String.format("Selection starts at y=%s and ends at y=%s", var7.getAsInt(), var9.getAsInt()));
               this.logDebug(String.format("Considering relevant height %s - %s", var4, this.d));
            }
         }
      }

      this.c = 0;
      this.a = new LongOpenHashSet();
      this.a = null;
   }

   public final void resume() {
      this.a = false;
   }

   public final void pause() {
      this.a = true;
   }

   public final boolean isPaused() {
      return this.a;
   }

   public final boolean build(String var1, File var2, class_2382 var3) {
      Optional var4;
      if (!(var4 = SchematicSystem.a.getByFile(var2)).isPresent()) {
         return false;
      } else {
         try {
            var6 = ((ISchematicFormat)var4.get()).parse(new FileInputStream(var2));
         } catch (Exception var5) {
            var5.printStackTrace();
            return false;
         }

         ISchematic var7 = this.a(var3, var6);
         this.build(var1, var7, var3);
         return true;
      }
   }

   private ISchematic a(class_2382 var1, IStaticSchematic var2) {
      Object var3 = var2;
      if ((Boolean)Baritone.a().mapArtMode.value) {
         var3 = new MapArtSchematic(var2);
      }

      if ((Boolean)Baritone.a().buildOnlySelection.value) {
         var3 = new SelectionSchematic((ISchematic)var3, var1, super.a.a.getSelections());
      }

      return (ISchematic)var3;
   }

   public final void buildOpenSchematic() {
      if (SchematicaHelper.a()) {
         Optional var1;
         if ((var1 = SchematicaHelper.a()).isPresent()) {
            IStaticSchematic var2 = (IStaticSchematic)((class_3545)var1.get()).method_15442();
            class_2338 var4 = (class_2338)((class_3545)var1.get()).method_15441();
            ISchematic var3 = this.a((class_2382)var4, (IStaticSchematic)var2);
            this.build(var2.toString(), (ISchematic)var3, var4);
         } else {
            this.logDirect("No schematic currently open");
         }
      } else {
         this.logDirect("Schematica is not present");
      }
   }

   public final void buildOpenLitematic(int var1) {
      if (LitematicaHelper.a()) {
         if (LitematicaHelper.a(var1)) {
            class_3545 var4;
            class_2382 var2 = (class_2382)(var4 = LitematicaHelper.a(var1)).method_15441();
            ISchematic var3 = this.a(var2, (IStaticSchematic)var4.method_15442());
            this.build(((IStaticSchematic)var4.method_15442()).toString(), var3, var2);
         } else {
            this.logDirect(String.format("List of placements has no entry %s", var1 + 1));
         }
      } else {
         this.logDirect("Litematica is not present");
      }
   }

   public final void clearArea(class_2338 var1, class_2338 var2) {
      class_2338 var3 = new class_2338(Math.min(var1.method_10263(), var2.method_10263()), Math.min(var1.method_10264(), var2.method_10264()), Math.min(var1.method_10260(), var2.method_10260()));
      int var4 = Math.abs(var1.method_10263() - var2.method_10263()) + 1;
      int var5 = Math.abs(var1.method_10264() - var2.method_10264()) + 1;
      int var6 = Math.abs(var1.method_10260() - var2.method_10260()) + 1;
      this.build("clear area", (ISchematic)(new FillSchematic(var4, var5, var6, class_2246.field_10124.method_9564())), var3);
   }

   public final List<class_2680> getApproxPlaceable() {
      return new ArrayList(this.a);
   }

   public final boolean isActive() {
      return this.a != null;
   }

   public final boolean a(class_2338 var1, class_2680 var2) {
      class_265 var3;
      return (var3 = var2.method_26220(super.a.world(), var1)).method_1110() || super.a.world().method_8611((class_1297)null, var3.method_1096((double)var1.method_10263(), (double)var1.method_10264(), (double)var1.method_10260()));
   }

   public final PathingCommand onTick(boolean var1, boolean var2) {
      int var3 = 0;
      var2 = var2;
      final BuilderProcess var44 = this;

      while(var3 <= 100) {
         var44.a = var44.a(36);
         if (var44.a.a.isInputForcedDown(Input.CLICK_LEFT)) {
            var44.a = 5;
         } else {
            --var44.a;
         }

         var44.a.a.clearAllKeys();
         if (var44.a) {
            return new PathingCommand((Goal)null, PathingCommandType.CANCEL_AND_SET_GOAL);
         }

         if ((Boolean)Baritone.a().buildInLayers.value) {
            if (var44.b == null) {
               var44.b = var44.a;
            }

            final ISchematic var4 = var44.b;
            final int var5;
            final int var6;
            if ((Boolean)Baritone.a().layerOrder.value) {
               var6 = var4.heightY() - 1;
               var5 = var4.heightY() - var44.b * (Integer)Baritone.a().layerHeight.value;
            } else {
               var6 = var44.b * (Integer)Baritone.a().layerHeight.value - 1;
               var5 = 0;
            }

            var44.a = new ISchematic() {
               public class_2680 desiredState(int var1, int var2, int var3, class_2680 var4x, List<class_2680> var5x) {
                  return var4.desiredState(var1, var2, var3, var4x, var44.a);
               }

               public boolean inSchematic(int var1, int var2, int var3, class_2680 var4x) {
                  return ISchematic.super.inSchematic(var1, var2, var3, var4x) && var2 >= var5 && var2 <= var6 && var4.inSchematic(var1, var2, var3, var4x);
               }

               public void reset() {
                  var4.reset();
               }

               public int widthX() {
                  return var4.widthX();
               }

               public int heightY() {
                  return var4.heightY();
               }

               public int lengthZ() {
                  return var4.lengthZ();
               }
            };
         }

         BuilderCalculationContext var46;
         boolean var10000;
         label398: {
            label410: {
               var46 = var44.new BuilderCalculationContext();
               if (var44.a == null) {
                  var44.a = new HashSet();
                  var44.a(var46);
                  if (var44.a.isEmpty()) {
                     break label410;
                  }
               }

               BuilderCalculationContext var9 = var46;
               BuilderProcess var8 = var44;
               BetterBlockPos var10 = var44.a.playerFeet();

               int var11;
               for(int var12 = -(var11 = (Integer)Baritone.a().builderTickScanRadius.value); var12 <= var11; ++var12) {
                  for(int var13 = -var11; var13 <= var11; ++var13) {
                     for(int var14 = -var11; var14 <= var11; ++var14) {
                        int var15 = var10.x + var12;
                        int var16 = var10.y + var13;
                        int var17 = var10.z + var14;
                        class_2680 var18;
                        if ((var18 = var9.a(var15, var16, var17, var9.a.a(var15, var16, var17))) != null) {
                           BetterBlockPos var19 = new BetterBlockPos(var15, var16, var17);
                           if (a(var9.a.a(var15, var16, var17), var18, false)) {
                              var8.a.remove(var19);
                              var8.a.add(BetterBlockPos.longHash(var19));
                           } else {
                              var8.a.add(var19);
                              var8.a.remove(BetterBlockPos.longHash(var19));
                           }
                        }
                     }
                  }
               }

               if (var44.a.isEmpty()) {
                  var44.a(var46);
               }

               if (!var44.a.isEmpty()) {
                  var10000 = true;
                  break label398;
               }
            }

            var10000 = false;
         }

         if (!var10000) {
            if ((Boolean)Baritone.a().buildInLayers.value && var44.b * (Integer)Baritone.a().layerHeight.value < var44.d) {
               var44.logDirect("Starting layer " + var44.b);
               ++var44.b;
               ++var3;
               var2 = var2;
               var44 = var44;
            } else {
               class_2382 var53 = (class_2382)Baritone.a().buildRepeat.value;
               int var58 = (Integer)Baritone.a().buildRepeatCount.value;
               ++var44.c;
               if (var53.equals(new class_2382(0, 0, 0)) || var58 != -1 && var44.c >= var58) {
                  var44.logDirect("Done building");
                  if ((Boolean)Baritone.a().notificationOnBuildFinished.value) {
                     var44.logNotification("Done building", false);
                  }

                  var44.onLostControl();
                  return null;
               }

               var44.b = 0;
               var44.a = (new class_2338(var44.a)).method_10081(var53);
               if (!(Boolean)Baritone.a().buildRepeatSneaky.value) {
                  var44.a.reset();
               }

               String var114 = String.valueOf(var53);
               var44.logDirect("Repeating build in vector " + var114 + ", new origin is " + String.valueOf(var44.a));
               ++var3;
               var2 = var2;
               var44 = var44;
            }
         } else {
            if ((Boolean)Baritone.a().distanceTrim.value) {
               HashSet var7;
               (var7 = new HashSet(var44.a)).removeIf((var1x) -> var1x.method_10262(super.a.player().method_24515()) > (double)200.0F);
               if (!var7.isEmpty()) {
                  var44.a = var7;
               }
            }

            BuilderCalculationContext var59 = var46;
            BuilderProcess var47 = var44;
            BetterBlockPos var64 = var44.a.playerFeet();
            BetterBlockPos var68 = var44.a.a.a();
            int var72 = -5;

            label370:
            while(true) {
               if (var72 > 5) {
                  var107 = Optional.empty();
                  break;
               }

               for(int var74 = (Boolean)Baritone.a().breakFromAbove.value ? -1 : 0; var74 <= 5; ++var74) {
                  for(int var76 = -5; var76 <= 5; ++var76) {
                     int var78 = var64.x + var72;
                     int var83 = var64.y + var74;
                     int var87 = var64.z + var76;
                     class_2680 var90;
                     class_2680 var93;
                     if ((var74 != -1 || var78 != var68.x || var87 != var68.z) && (var90 = var59.a(var78, var83, var87, var59.a.a(var78, var83, var87))) != null && !((var93 = var59.a.a(var78, var83, var87)).method_26204() instanceof class_2189) && var93.method_26204() != class_2246.field_10382 && var93.method_26204() != class_2246.field_10164 && !a(var93, var90, false)) {
                        BetterBlockPos var79 = new BetterBlockPos(var78, var83, var87);
                        Optional var84;
                        if ((var84 = RotationUtils.reachable((IPlayerContext)var47.a, var79, var47.a.playerController().getBlockReachDistance())).isPresent()) {
                           var107 = Optional.of(new class_3545(var79, (Rotation)var84.get()));
                           break label370;
                        }
                     }
                  }
               }

               ++var72;
            }

            Optional var48 = var107;
            if (var107.isPresent() && var2 && var44.a.player().method_24828()) {
               Rotation var57 = (Rotation)((class_3545)var48.get()).method_15441();
               BetterBlockPos var52 = (BetterBlockPos)((class_3545)var48.get()).method_15442();
               var44.a.a.updateTarget(var57, true);
               MovementHelper.a(var44.a, ((CalculationContext)var46).a(var52));
               if (var44.a.player().method_18276()) {
                  var44.a.a.setInputForceState(Input.SNEAK, true);
               }

               if (var44.a.isLookingAt(var52) || var44.a.playerRotations().isReallyCloseTo(var57)) {
                  var44.a.a.setInputForceState(Input.CLICK_LEFT, true);
               }

               return new PathingCommand((Goal)null, PathingCommandType.CANCEL_AND_SET_GOAL);
            }

            ArrayList var54 = new ArrayList();
            ArrayList var65 = var54;
            var59 = var46;
            BuilderProcess var49 = var44;
            var68 = var44.a.playerFeet();
            var72 = -5;

            label341:
            while(true) {
               if (var72 > 5) {
                  var111 = Optional.empty();
                  break;
               }

               for(int var75 = -5; var75 <= 1; ++var75) {
                  for(int var77 = -5; var77 <= 5; ++var77) {
                     int var80 = var68.x + var72;
                     int var85 = var68.y + var75;
                     int var88 = var68.z + var77;
                     class_2680 var91;
                     if ((var91 = var59.a(var80, var85, var88, var59.a.a(var80, var85, var88))) != null) {
                        class_2680 var94 = var59.a.a(var80, var85, var88);
                        if (MovementHelper.a(var80, var88, var94, var59.a) && !a(var94, var91, false) && (var75 != 1 || !(var59.a.a(var80, var85 + 1, var88).method_26204() instanceof class_2189))) {
                           var65.add(var91);
                           BlockStateInterface var96 = var59.a;
                           int var95 = var88;
                           int var92 = var85;
                           var88 = var80;
                           class_2680 var86 = var91;
                           BuilderProcess var81 = var49;
                           class_2350[] var97;
                           int var20 = (var97 = class_2350.values()).length;
                           int var21 = 0;

                           label332:
                           while(true) {
                              if (var21 >= var20) {
                                 var110 = Optional.empty();
                                 break;
                              }

                              class_2350 var22 = var97[var21];
                              BetterBlockPos var23 = (new BetterBlockPos(var88, var92, var95)).relative(var22);
                              class_2680 var24 = var96.a(var23);
                              int var113 = var23.y;
                              class_265 var98;
                              if (!MovementHelper.a(var23.x, var23.z, var24, var96) && var86.method_26184(var81.a.world(), new BetterBlockPos(var88, var92, var95)) && var81.a((class_2338)(new BetterBlockPos(var88, var92, var95)), (class_2680)var86) && !(var98 = var24.method_26218(var81.a.world(), var23)).method_1110()) {
                                 class_238 var99 = var98.method_1107();
                                 class_243[] var108;
                                 switch (var22) {
                                    case field_11036:
                                       var108 = new class_243[]{new class_243((double)0.5F, (double)1.0F, (double)0.5F), new class_243(0.1, (double)1.0F, (double)0.5F), new class_243(0.9, (double)1.0F, (double)0.5F), new class_243((double)0.5F, (double)1.0F, 0.1), new class_243((double)0.5F, (double)1.0F, 0.9)};
                                       break;
                                    case field_11033:
                                       var108 = new class_243[]{new class_243((double)0.5F, (double)0.0F, (double)0.5F), new class_243(0.1, (double)0.0F, (double)0.5F), new class_243(0.9, (double)0.0F, (double)0.5F), new class_243((double)0.5F, (double)0.0F, 0.1), new class_243((double)0.5F, (double)0.0F, 0.9)};
                                       break;
                                    case field_11043:
                                    case field_11035:
                                    case field_11034:
                                    case field_11039:
                                       double var40 = var22.method_10148() == 0 ? (double)0.5F : (double)(1 + var22.method_10148()) / (double)2.0F;
                                       double var42 = var22.method_10165() == 0 ? (double)0.5F : (double)(1 + var22.method_10165()) / (double)2.0F;
                                       var108 = new class_243[]{new class_243(var40, (double)0.25F, var42), new class_243(var40, (double)0.75F, var42)};
                                       break;
                                    default:
                                       throw new IllegalStateException("Unexpected side " + String.valueOf(var22));
                                 }

                                 for(class_243 var28 : var108) {
                                    double var34 = (double)var23.x + var99.field_1323 * var28.field_1352 + var99.field_1320 * ((double)1.0F - var28.field_1352);
                                    double var36 = (double)var23.y + var99.field_1322 * var28.field_1351 + var99.field_1325 * ((double)1.0F - var28.field_1351);
                                    double var38 = (double)var23.z + var99.field_1321 * var28.field_1350 + var99.field_1324 * ((double)1.0F - var28.field_1350);
                                    Rotation var100 = RotationUtils.calcRotationFromVec3d(RayTraceUtils.inferSneakingEyePosition(var81.a.player()), new class_243(var34, var36, var38), var81.a.playerRotations());
                                    Rotation var29 = var81.a.a.getAimProcessor().peekRotation(var100);
                                    class_239 var30;
                                    if ((var30 = RayTraceUtils.rayTraceTowards(var81.a.player(), var29, var81.a.playerController().getBlockReachDistance(), true)) != null && var30.method_17783() == class_240.field_1332 && ((class_3965)var30).method_17777().equals(var23) && ((class_3965)var30).method_17780() == var22.method_10153()) {
                                       Rotation var106 = var29;
                                       class_239 var41 = var30;
                                       class_2680 var105 = var86;
                                       BuilderProcess var101 = var81;
                                       int var43 = 0;

                                       while(true) {
                                          if (var43 >= 9) {
                                             var109 = OptionalInt.empty();
                                             break;
                                          }

                                          class_1799 var103;
                                          if (!(var103 = (class_1799)var101.a.player().method_31548().method_67533().get(var43)).method_7960() && var103.method_7909() instanceof class_1747) {
                                             float var31 = var101.a.player().method_36454();
                                             float var32 = var101.a.player().method_36455();
                                             var101.a.player().method_36456(var106.getYaw());
                                             var101.a.player().method_36457(var106.getPitch());
                                             class_1750 var33 = new class_1750(new class_1838(var101.a.world(), var101.a.player(), class_1268.field_5808, var103, (class_3965)var41) {
                                             });
                                             class_2680 var104 = ((class_1747)var103.method_7909()).method_7711().method_9605(var33);
                                             var101.a.player().method_36456(var31);
                                             var101.a.player().method_36457(var32);
                                             if (var104 != null && var33.method_7716() && a(var104, var105, true)) {
                                                var109 = OptionalInt.of(var43);
                                                break;
                                             }
                                          }

                                          ++var43;
                                       }

                                       OptionalInt var102 = var109;
                                       if (var109.isPresent()) {
                                          var110 = Optional.of(new Placement(var102.getAsInt(), var23, var22.method_10153(), var100));
                                          break label332;
                                       }
                                    }
                                 }
                              }

                              ++var21;
                           }

                           Optional var82 = var110;
                           if (var110.isPresent()) {
                              var111 = var82;
                              break label341;
                           }
                        }
                     }
                  }
               }

               ++var72;
            }

            Optional var50 = var111;
            if (var111.isPresent() && var2 && var44.a.player().method_24828() && var44.a <= 0) {
               Rotation var63 = ((Placement)var50.get()).a;
               var44.a.a.updateTarget(var63, true);
               var44.a.player().method_31548().method_61496(((Placement)var50.get()).a);
               var44.a.a.setInputForceState(Input.SNEAK, true);
               if (var44.a.isLookingAt(((Placement)var50.get()).a) && ((class_3965)var44.a.objectMouseOver()).method_17780().equals(((Placement)var50.get()).a) || var44.a.playerRotations().isReallyCloseTo(var63)) {
                  var44.a.a.setInputForceState(Input.CLICK_RIGHT, true);
               }

               return new PathingCommand((Goal)null, PathingCommandType.CANCEL_AND_SET_GOAL);
            }

            if ((Boolean)Baritone.a().allowInventory.value) {
               ArrayList var61 = new ArrayList();
               ArrayList var51 = new ArrayList();

               label271:
               for(class_2680 var66 : var54) {
                  for(int var70 = 0; var70 < 9; ++var70) {
                     if (a((class_2680)var44.a.get(var70), var66, true)) {
                        var61.add(var70);
                        continue label271;
                     }
                  }

                  var51.add(var66);
               }

               label257:
               for(int var56 = 9; var56 < 36; ++var56) {
                  for(class_2680 var71 : var51) {
                     if (a((class_2680)var44.a.get(var56), var71, true)) {
                        InventoryBehavior var112 = var44.a.a;
                        Objects.requireNonNull(var61);
                        if (!var112.a(var56, var61::contains)) {
                           return new PathingCommand((Goal)null, PathingCommandType.REQUEST_PAUSE);
                        }
                        break label257;
                     }
                  }
               }
            }

            Goal var62;
            if ((var62 = var44.a(var46, var44.a.subList(0, 9), false)) == null && (var62 = var44.a(var46, var44.a, true)) == null) {
               if ((Boolean)Baritone.a().skipFailedLayers.value && (Boolean)Baritone.a().buildInLayers.value && var44.b * (Integer)Baritone.a().layerHeight.value < var44.b.heightY()) {
                  var44.logDirect("Skipping layer that I cannot construct! Layer #" + var44.b);
                  ++var44.b;
                  ++var3;
                  var2 = var2;
                  var44 = var44;
                  continue;
               }

               var44.logDirect("Unable to do it. Pausing. resume to resume, cancel to cancel");
               var44.a = true;
               return new PathingCommand((Goal)null, PathingCommandType.REQUEST_PAUSE);
            }

            return new PathingCommandContext(var62, PathingCommandType.FORCE_REVALIDATE_GOAL_AND_PATH, var46);
         }
      }

      return new PathingCommand((Goal)null, PathingCommandType.SET_GOAL_AND_PATH);
   }

   private void a(BuilderCalculationContext var1) {
      this.a = new HashSet();

      for(int var2 = 0; var2 < this.a.heightY(); ++var2) {
         for(int var3 = 0; var3 < this.a.lengthZ(); ++var3) {
            for(int var4 = 0; var4 < this.a.widthX(); ++var4) {
               int var5 = var4 + this.a.method_10263();
               int var6 = var2 + this.a.method_10264();
               int var7 = var3 + this.a.method_10260();
               class_2680 var8 = var1.a.a(var5, var6, var7);
               if (this.a.inSchematic(var4, var2, var3, var8)) {
                  if (var1.a.a(var5, var7)) {
                     if (a(var1.a.a(var5, var6, var7), this.a.desiredState(var4, var2, var3, var8, this.a), false)) {
                        this.a.add(BetterBlockPos.longHash(var5, var6, var7));
                     } else {
                        this.a.add(new BetterBlockPos(var5, var6, var7));
                        this.a.remove(BetterBlockPos.longHash(var5, var6, var7));
                        if (this.a.size() > (Integer)Baritone.a().incorrectSize.value) {
                           return;
                        }
                     }
                  } else if (!this.a.contains(BetterBlockPos.longHash(var5, var6, var7))) {
                     this.a.add(new BetterBlockPos(var5, var6, var7));
                     if (this.a.size() > (Integer)Baritone.a().incorrectSize.value) {
                        return;
                     }
                  }
               }
            }
         }
      }

   }

   private Goal a(BuilderCalculationContext var1, List<class_2680> var2, boolean var3) {
      ArrayList var4 = new ArrayList();
      ArrayList var5 = new ArrayList();
      ArrayList var6 = new ArrayList();
      ArrayList var7 = new ArrayList();
      HashMap var8 = new HashMap();
      ArrayList var9 = new ArrayList();
      this.a.forEach((var8x) -> {
         class_2680 var9x;
         if (!((var9x = var1.a.a(var8x)).method_26204() instanceof class_2189)) {
            if (var9x.method_26204() instanceof class_2404) {
               if (!MovementHelper.g(var9x)) {
                  var6.add(var8x);
               } else {
                  var7.add(var8x);
               }
            } else {
               var5.add(var8x);
            }
         } else {
            class_2680 var10;
            if ((var10 = var1.a(var8x.x, var8x.y, var8x.z, var9x)) == null) {
               var9.add(var8x);
            } else {
               List var10000 = var2;
               class_2680 var12 = var10;
               Iterator var11 = var10000.iterator();

               while(true) {
                  if (var11.hasNext()) {
                     if (!a((class_2680)var11.next(), var12)) {
                        continue;
                     }

                     var13 = true;
                     break;
                  }

                  var13 = false;
                  break;
               }

               if (var13) {
                  var4.add(var8x);
               } else {
                  var8.put(var10, 1 + (Integer)var8.getOrDefault(var10, 0));
               }
            }
         }
      });
      this.a.removeAll(var9);
      ArrayList var10 = new ArrayList();
      var5.forEach((var3x) -> {
         BetterBlockPos var10001 = var3x;
         BuilderCalculationContext var5 = var1;
         BetterBlockPos var4 = var10001;
         var10.add((Boolean)Baritone.a().goalBreakFromAbove.value && var5.a.a(((class_2338)var4).method_10084()).method_26204() instanceof class_2189 && var5.a.a(((class_2338)var4).method_10086(2)).method_26204() instanceof class_2189 ? new JankyGoalComposite(new GoalBreak(var4), new GoalGetToBlock(((class_2338)var4).method_10084()) {
            public boolean isInGoal(int var1, int var2, int var3) {
               return var2 <= super.y && (var1 != super.x || var2 != super.y || var3 != super.z) ? super.isInGoal(var1, var2, var3) : false;
            }
         }) : new GoalBreak(var4));
      });
      var5 = new ArrayList();
      var4.forEach((var4x) -> {
         if (!var4.contains(var4x.below()) && !var4.contains(var4x.below(2))) {
            BetterBlockPos var11 = var4x;
            BuilderProcess var10 = this;
            Object var10001;
            if (!(super.a.world().method_8320(var4x).method_26204() instanceof class_2189)) {
               var10001 = new GoalPlace(var4x);
            } else {
               boolean var12 = !(super.a.world().method_8320(((class_2338)var4x).method_10084()).method_26204() instanceof class_2189);
               class_2680 var5x = super.a.world().method_8320(var4x);
               class_2350[] var6;
               int var7 = (var6 = Movement.a).length;
               int var8 = 0;

               while(true) {
                  if (var8 >= var7) {
                     var10001 = new GoalPlace(var11);
                     break;
                  }

                  class_2350 var9 = var6[var8];
                  if (MovementHelper.c(var10.a, ((class_2338)var11).method_10093(var9)) && var10.a((class_2338)var11, (class_2680)var1.a(((class_2338)var11).method_10263(), ((class_2338)var11).method_10264(), ((class_2338)var11).method_10260(), var5x))) {
                     var10001 = new GoalAdjacent(var11, ((class_2338)var11).method_10093(var9), var12);
                     break;
                  }

                  ++var8;
               }
            }

            var5.add(var10001);
         }

      });
      var6.forEach((var1x) -> var5.add(new GoalBlock(var1x.above())));
      if (!var5.isEmpty()) {
         return new JankyGoalComposite(new GoalComposite((Goal[])var5.toArray(new Goal[0])), new GoalComposite((Goal[])var10.toArray(new Goal[0])));
      } else if (var10.isEmpty()) {
         if (var3 && !var8.isEmpty()) {
            this.logDirect("Missing materials for at least:");
            this.logDirect((String)var8.entrySet().stream().map((var0) -> String.format("%sx %s", var0.getValue(), var0.getKey())).collect(Collectors.joining("\n")));
         }

         if (var3 && !var7.isEmpty()) {
            this.logDirect("Unreplaceable liquids at at least:");
            this.logDirect((String)var7.stream().map((var0) -> String.format("%s %s %s", var0.x, var0.y, var0.z)).collect(Collectors.joining("\n")));
         }

         return null;
      } else {
         return new GoalComposite((Goal[])var10.toArray(new Goal[0]));
      }
   }

   public final void onLostControl() {
      this.a = null;
      this.a = null;
      this.a = null;
      this.b = null;
      this.b = (Integer)Baritone.a().startAtLayer.value;
      this.c = 0;
      this.a = false;
      this.a = null;
   }

   public final String displayName0() {
      return this.a ? "Builder Paused" : "Building " + this.a;
   }

   public final Optional<Integer> getMinLayer() {
      return (Boolean)Baritone.a().buildInLayers.value ? Optional.of(this.b) : Optional.empty();
   }

   public final Optional<Integer> getMaxLayer() {
      return (Boolean)Baritone.a().buildInLayers.value ? Optional.of(this.d) : Optional.empty();
   }

   final List<class_2680> a(int var1) {
      ArrayList var2 = new ArrayList();

      for(int var3 = 0; var3 < var1; ++var3) {
         class_1799 var4;
         if (!(var4 = (class_1799)super.a.player().method_31548().method_67533().get(var3)).method_7960() && var4.method_7909() instanceof class_1747) {
            class_2680 var5;
            if ((var5 = ((class_1747)var4.method_7909()).method_7711().method_9605(new class_1750(new class_1838(super.a.world(), super.a.player(), class_1268.field_5808, var4, new class_3965(new class_243(super.a.player().method_73189().field_1352, super.a.player().method_73189().field_1351, super.a.player().method_73189().field_1350), class_2350.field_11036, super.a.playerFeet(), false)) {
            }))) != null) {
               var2.add(var5);
            } else {
               var2.add(class_2246.field_10124.method_9564());
            }
         } else {
            var2.add(class_2246.field_10124.method_9564());
         }
      }

      return var2;
   }

   private static boolean a(class_2680 var0, class_2680 var1) {
      if (var0.method_26204() != var1.method_26204()) {
         return false;
      } else {
         boolean var2 = (Boolean)Baritone.a().buildIgnoreDirection.value;
         List var3 = (List)Baritone.a().buildIgnoreProperties.value;
         if (!var2 && var3.isEmpty()) {
            return var0.equals(var1);
         } else {
            Map var6 = var0.method_11656();
            Map var7 = var1.method_11656();

            for(class_2769 var5 : var6.keySet()) {
               if (var6.get(var5) != var7.get(var5) && (!var2 || !a.contains(var5)) && !var3.contains(var5.method_11899())) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   static boolean a(class_2680 var0, class_2680 var1, boolean var2) {
      if (var1 == null) {
         return true;
      } else if (var0.method_26204() instanceof class_2404 && (Boolean)Baritone.a().okIfWater.value) {
         return true;
      } else if (var0.method_26204() instanceof class_2189 && var1.method_26204() instanceof class_2189) {
         return true;
      } else if (var0.method_26204() instanceof class_2189 && ((List)Baritone.a().okIfAir.value).contains(var1.method_26204())) {
         return true;
      } else if (var1.method_26204() instanceof class_2189 && ((List)Baritone.a().buildIgnoreBlocks.value).contains(var0.method_26204())) {
         return true;
      } else if (!(var0.method_26204() instanceof class_2189) && (Boolean)Baritone.a().buildIgnoreExisting.value && !var2) {
         return true;
      } else if (((List)((Map)Baritone.a().buildValidSubstitutes.value).getOrDefault(var1.method_26204(), Collections.emptyList())).contains(var0.method_26204()) && !var2) {
         return true;
      } else {
         return var0.equals(var1) ? true : a(var0, var1);
      }
   }

   // $FF: synthetic method
   static Baritone a(BuilderProcess var0) {
      return var0.a;
   }

   static {
      a = ImmutableSet.of(class_2465.field_11459, class_2383.field_11177, class_2510.field_11571, class_2510.field_11572, class_2510.field_11565, class_2429.field_11332, new class_2769[]{class_2429.field_11335, class_2429.field_11331, class_2429.field_11328, class_2429.field_11327, class_2533.field_11631, class_2533.field_11625});
   }

   public class BuilderCalculationContext extends CalculationContext {
      private final List<class_2680> b = BuilderProcess.this.a(9);
      private final ISchematic a;
      private final int e;
      private final int f;
      private final int g;

      public BuilderCalculationContext() {
         super(BuilderProcess.a((BuilderProcess)BuilderProcess.this), true);
         this.a = BuilderProcess.this.a;
         this.e = BuilderProcess.this.a.method_10263();
         this.f = BuilderProcess.this.a.method_10264();
         this.g = BuilderProcess.this.a.method_10260();
         super.e += (double)10.0F;
         super.d = (double)1.0F;
      }

      final class_2680 a(int var1, int var2, int var3, class_2680 var4) {
         return this.a.inSchematic(var1 - this.e, var2 - this.f, var3 - this.g, var4) ? this.a.desiredState(var1 - this.e, var2 - this.f, var3 - this.g, var4, BuilderProcess.this.a) : null;
      }

      public final double a(int var1, int var2, int var3, class_2680 var4) {
         if (!super.a.b(var1, var3)) {
            return (double)1000000.0F;
         } else {
            class_2680 var5;
            if ((var5 = this.a(var1, var2, var3, var4)) != null) {
               if (var5.method_26204() instanceof class_2189) {
                  return super.a * (Double)Baritone.a().placeIncorrectBlockPenaltyMultiplier.value;
               } else if (this.b.contains(var5)) {
                  return (double)0.0F;
               } else {
                  return !super.c ? (double)1000000.0F : super.a * (double)1.5F * (Double)Baritone.a().placeIncorrectBlockPenaltyMultiplier.value;
               }
            } else {
               return super.c ? super.a : (double)1000000.0F;
            }
         }
      }

      public final double b(int var1, int var2, int var3, class_2680 var4) {
         if (!super.e && !super.a.contains(var4.method_26204())) {
            return (double)1000000.0F;
         } else if ((var4 = this.a(var1, var2, var3, var4)) != null) {
            if (var4.method_26204() instanceof class_2189) {
               return (double)1.0F;
            } else {
               return BuilderProcess.a(super.a.a(var1, var2, var3), var4, false) ? (Double)Baritone.a().breakCorrectBlockPenaltyMultiplier.value : (double)1.0F;
            }
         } else {
            return (double)1.0F;
         }
      }
   }

   public static class GoalAdjacent extends GoalGetToBlock {
      private boolean a;
      private class_2338 a;

      public GoalAdjacent(BetterBlockPos var1, class_2338 var2, boolean var3) {
         super(var1);
         this.a = var2;
         this.a = var3;
      }

      public boolean isInGoal(int var1, int var2, int var3) {
         if (var1 == super.x && var2 == super.y && var3 == super.z) {
            return false;
         } else if (var1 == this.a.method_10263() && var2 == this.a.method_10264() && var3 == this.a.method_10260()) {
            return false;
         } else if (!this.a && var2 == super.y - 1) {
            return false;
         } else {
            return var2 < super.y - 1 ? false : super.isInGoal(var1, var2, var3);
         }
      }

      public double heuristic(int var1, int var2, int var3) {
         return (double)(super.y * 100) + super.heuristic(var1, var2, var3);
      }

      public boolean equals(Object var1) {
         if (!super.equals(var1)) {
            return false;
         } else {
            GoalAdjacent var2 = (GoalAdjacent)var1;
            return this.a == var2.a && Objects.equals(this.a, var2.a);
         }
      }

      public int hashCode() {
         return ((-2112107180 + super.hashCode()) * 1730799370 + (int)BetterBlockPos.longHash(this.a.method_10263(), this.a.method_10264(), this.a.method_10260())) * 260592149 + (this.a ? -1314802005 : 1565710265);
      }

      public String toString() {
         return String.format("GoalAdjacent{x=%s,y=%s,z=%s}", SettingsUtil.maybeCensor(super.x), SettingsUtil.maybeCensor(super.y), SettingsUtil.maybeCensor(super.z));
      }
   }

   public static class GoalBreak extends GoalGetToBlock {
      public GoalBreak(class_2338 var1) {
         super(var1);
      }

      public boolean isInGoal(int var1, int var2, int var3) {
         return var2 > super.y ? false : super.isInGoal(var1, var2, var3);
      }

      public String toString() {
         return String.format("GoalBreak{x=%s,y=%s,z=%s}", SettingsUtil.maybeCensor(super.x), SettingsUtil.maybeCensor(super.y), SettingsUtil.maybeCensor(super.z));
      }

      public int hashCode() {
         return super.hashCode() * 1636324008;
      }
   }

   public static class GoalPlace extends GoalBlock {
      public GoalPlace(BetterBlockPos var1) {
         super(((class_2338)var1).method_10084());
      }

      public double heuristic(int var1, int var2, int var3) {
         return (double)(super.y * 100) + super.heuristic(var1, var2, var3);
      }

      public int hashCode() {
         return super.hashCode() * 1910811835;
      }

      public String toString() {
         return String.format("GoalPlace{x=%s,y=%s,z=%s}", SettingsUtil.maybeCensor(super.x), SettingsUtil.maybeCensor(super.y), SettingsUtil.maybeCensor(super.z));
      }
   }

   public static class JankyGoalComposite implements Goal {
      private final Goal a;
      private final Goal b;

      public JankyGoalComposite(Goal var1, Goal var2) {
         this.a = var1;
         this.b = var2;
      }

      public boolean isInGoal(int var1, int var2, int var3) {
         return this.a.isInGoal(var1, var2, var3) || this.b.isInGoal(var1, var2, var3);
      }

      public double heuristic(int var1, int var2, int var3) {
         return this.a.heuristic(var1, var2, var3);
      }

      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (var1 != null && this.getClass() == var1.getClass()) {
            JankyGoalComposite var2 = (JankyGoalComposite)var1;
            return Objects.equals(this.a, var2.a) && Objects.equals(this.b, var2.b);
         } else {
            return false;
         }
      }

      public int hashCode() {
         return (1544707182 + this.a.hashCode()) * -80327868 + this.b.hashCode();
      }

      public String toString() {
         String var10000 = String.valueOf(this.a);
         return "JankyComposite Primary: " + var10000 + " Fallback: " + String.valueOf(this.b);
      }
   }

   public static class Placement {
      final int a;
      final BetterBlockPos a;
      final class_2350 a;
      final Rotation a;

      public Placement(int var1, BetterBlockPos var2, class_2350 var3, Rotation var4) {
         this.a = var1;
         this.a = var2;
         this.a = var3;
         this.a = var4;
      }
   }
}
