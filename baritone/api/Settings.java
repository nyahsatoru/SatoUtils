package baritone.api;

import baritone.api.utils.Helper;
import baritone.api.utils.NotificationHelper;
import baritone.api.utils.SettingsUtil;
import baritone.api.utils.TypeUtils;
import baritone.api.utils.gui.BaritoneToast;
import java.awt.Color;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.minecraft.class_1792;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2382;
import net.minecraft.class_2415;
import net.minecraft.class_2470;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_7469;
import net.minecraft.class_7591;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Settings {
   private static final Logger LOGGER = LoggerFactory.getLogger("Baritone");
   public final Setting<Boolean> allowBreak;
   public final Setting<List<class_2248>> allowBreakAnyway;
   public final Setting<Boolean> allowSprint;
   public final Setting<Boolean> allowPlace;
   public final Setting<Boolean> allowPlaceInFluidsSource;
   public final Setting<Boolean> allowPlaceInFluidsFlow;
   public final Setting<Boolean> allowInventory;
   public final Setting<Integer> ticksBetweenInventoryMoves;
   public final Setting<Boolean> inventoryMoveOnlyIfStationary;
   public final Setting<Boolean> assumeExternalAutoTool;
   public final Setting<Boolean> autoTool;
   public final Setting<Double> blockPlacementPenalty;
   public final Setting<Double> blockBreakAdditionalPenalty;
   public final Setting<Double> jumpPenalty;
   public final Setting<Double> walkOnWaterOnePenalty;
   public final Setting<Boolean> strictLiquidCheck;
   public final Setting<Boolean> allowWaterBucketFall;
   public final Setting<Boolean> assumeWalkOnWater;
   public final Setting<Boolean> assumeWalkOnLava;
   public final Setting<Boolean> assumeStep;
   public final Setting<Boolean> assumeSafeWalk;
   public final Setting<Boolean> allowJumpAtBuildLimit;
   @Deprecated
   @Settings.JavaOnly
   public final Setting<Boolean> allowJumpAt256;
   public final Setting<Boolean> allowParkourAscend;
   public final Setting<Boolean> allowDiagonalDescend;
   public final Setting<Boolean> allowDiagonalAscend;
   public final Setting<Boolean> allowDownward;
   public final Setting<List<class_1792>> acceptableThrowawayItems;
   public final Setting<List<class_2248>> blocksToAvoid;
   public final Setting<List<class_2248>> blocksToDisallowBreaking;
   public final Setting<List<class_2248>> blocksToAvoidBreaking;
   public final Setting<Double> avoidBreakingMultiplier;
   public final Setting<List<class_2248>> buildIgnoreBlocks;
   public final Setting<List<class_2248>> buildSkipBlocks;
   public final Setting<Map<class_2248, List<class_2248>>> buildValidSubstitutes;
   public final Setting<Map<class_2248, List<class_2248>>> buildSubstitutes;
   public final Setting<List<class_2248>> okIfAir;
   public final Setting<Boolean> buildIgnoreExisting;
   public final Setting<Boolean> buildIgnoreDirection;
   public final Setting<List<String>> buildIgnoreProperties;
   public final Setting<Boolean> avoidUpdatingFallingBlocks;
   public final Setting<Boolean> allowVines;
   public final Setting<Boolean> allowWalkOnBottomSlab;
   public final Setting<Boolean> allowParkour;
   public final Setting<Boolean> allowParkourPlace;
   public final Setting<Boolean> considerPotionEffects;
   public final Setting<Boolean> sprintAscends;
   public final Setting<Boolean> overshootTraverse;
   public final Setting<Boolean> pauseMiningForFallingBlocks;
   public final Setting<Integer> rightClickSpeed;
   public final Setting<Double> randomLooking113;
   public final Setting<Float> blockReachDistance;
   public final Setting<Integer> blockBreakSpeed;
   public final Setting<Double> randomLooking;
   public final Setting<Double> costHeuristic;
   public final Setting<Integer> pathingMaxChunkBorderFetch;
   public final Setting<Double> backtrackCostFavoringCoefficient;
   public final Setting<Boolean> avoidance;
   public final Setting<Double> mobSpawnerAvoidanceCoefficient;
   public final Setting<Integer> mobSpawnerAvoidanceRadius;
   public final Setting<Double> mobAvoidanceCoefficient;
   public final Setting<Integer> mobAvoidanceRadius;
   public final Setting<Boolean> rightClickContainerOnArrival;
   public final Setting<Boolean> enterPortal;
   public final Setting<Boolean> minimumImprovementRepropagation;
   public final Setting<Boolean> cutoffAtLoadBoundary;
   public final Setting<Double> maxCostIncrease;
   public final Setting<Integer> costVerificationLookahead;
   public final Setting<Double> pathCutoffFactor;
   public final Setting<Integer> pathCutoffMinimumLength;
   public final Setting<Integer> planningTickLookahead;
   public final Setting<Integer> pathingMapDefaultSize;
   public final Setting<Float> pathingMapLoadFactor;
   public final Setting<Integer> maxFallHeightNoWater;
   public final Setting<Integer> maxFallHeightBucket;
   public final Setting<Boolean> allowOvershootDiagonalDescend;
   public final Setting<Boolean> simplifyUnloadedYCoord;
   public final Setting<Boolean> repackOnAnyBlockChange;
   public final Setting<Integer> movementTimeoutTicks;
   public final Setting<Long> primaryTimeoutMS;
   public final Setting<Long> failureTimeoutMS;
   public final Setting<Long> planAheadPrimaryTimeoutMS;
   public final Setting<Long> planAheadFailureTimeoutMS;
   public final Setting<Boolean> slowPath;
   public final Setting<Long> slowPathTimeDelayMS;
   public final Setting<Long> slowPathTimeoutMS;
   public final Setting<Boolean> doBedWaypoints;
   public final Setting<Boolean> doDeathWaypoints;
   public final Setting<Boolean> chunkCaching;
   public final Setting<Boolean> pruneRegionsFromRAM;
   public final Setting<Integer> chunkPackerQueueMaxSize;
   public final Setting<Boolean> backfill;
   public final Setting<Boolean> logAsToast;
   public final Setting<Boolean> chatDebug;
   public final Setting<Boolean> chatControl;
   public final Setting<Boolean> chatControlAnyway;
   public final Setting<Boolean> renderPath;
   public final Setting<Boolean> renderPathAsLine;
   public final Setting<Boolean> renderGoal;
   public final Setting<Boolean> renderGoalAnimated;
   public final Setting<Boolean> renderSelectionBoxes;
   public final Setting<Boolean> renderGoalIgnoreDepth;
   public final Setting<Boolean> renderGoalXZBeacon;
   public final Setting<Boolean> renderSelectionBoxesIgnoreDepth;
   public final Setting<Boolean> renderPathIgnoreDepth;
   public final Setting<Float> pathRenderLineWidthPixels;
   public final Setting<Float> goalRenderLineWidthPixels;
   public final Setting<Boolean> fadePath;
   public final Setting<Boolean> freeLook;
   public final Setting<Boolean> blockFreeLook;
   public final Setting<Boolean> elytraFreeLook;
   public final Setting<Boolean> smoothLook;
   public final Setting<Boolean> elytraSmoothLook;
   public final Setting<Integer> smoothLookTicks;
   public final Setting<Boolean> remainWithExistingLookDirection;
   public final Setting<Boolean> antiCheatCompatibility;
   public final Setting<Boolean> pathThroughCachedOnly;
   public final Setting<Boolean> sprintInWater;
   public final Setting<Boolean> blacklistClosestOnFailure;
   public final Setting<Boolean> renderCachedChunks;
   public final Setting<Float> cachedChunksOpacity;
   public final Setting<Boolean> prefixControl;
   public final Setting<String> prefix;
   public final Setting<Boolean> shortBaritonePrefix;
   public final Setting<Boolean> useMessageTag;
   public final Setting<Boolean> echoCommands;
   public final Setting<Boolean> censorCoordinates;
   public final Setting<Boolean> censorRanCommands;
   public final Setting<Boolean> itemSaver;
   public final Setting<Integer> itemSaverThreshold;
   public final Setting<Boolean> preferSilkTouch;
   public final Setting<Boolean> walkWhileBreaking;
   public final Setting<Boolean> splicePath;
   public final Setting<Integer> maxPathHistoryLength;
   public final Setting<Integer> pathHistoryCutoffAmount;
   public final Setting<Integer> mineGoalUpdateInterval;
   public final Setting<Integer> maxCachedWorldScanCount;
   public final Setting<Integer> mineMaxOreLocationsCount;
   public final Setting<Integer> minYLevelWhileMining;
   public final Setting<Integer> maxYLevelWhileMining;
   public final Setting<Boolean> allowOnlyExposedOres;
   public final Setting<Integer> allowOnlyExposedOresDistance;
   public final Setting<Boolean> exploreForBlocks;
   public final Setting<Integer> worldExploringChunkOffset;
   public final Setting<Integer> exploreChunkSetMinimumSize;
   public final Setting<Integer> exploreMaintainY;
   public final Setting<Boolean> replantCrops;
   public final Setting<Boolean> replantNetherWart;
   public final Setting<Integer> farmMaxScanSize;
   public final Setting<Boolean> extendCacheOnThreshold;
   public final Setting<Boolean> buildInLayers;
   public final Setting<Boolean> layerOrder;
   public final Setting<Integer> layerHeight;
   public final Setting<Integer> startAtLayer;
   public final Setting<Boolean> skipFailedLayers;
   public final Setting<Boolean> buildOnlySelection;
   public final Setting<class_2382> buildRepeat;
   public final Setting<Integer> buildRepeatCount;
   public final Setting<Boolean> buildRepeatSneaky;
   public final Setting<Boolean> breakFromAbove;
   public final Setting<Boolean> goalBreakFromAbove;
   public final Setting<Boolean> mapArtMode;
   public final Setting<Boolean> okIfWater;
   public final Setting<Integer> incorrectSize;
   public final Setting<Double> breakCorrectBlockPenaltyMultiplier;
   public final Setting<Double> placeIncorrectBlockPenaltyMultiplier;
   public final Setting<Boolean> schematicOrientationX;
   public final Setting<Boolean> schematicOrientationY;
   public final Setting<Boolean> schematicOrientationZ;
   public final Setting<class_2470> buildSchematicRotation;
   public final Setting<class_2415> buildSchematicMirror;
   public final Setting<String> schematicFallbackExtension;
   public final Setting<Integer> builderTickScanRadius;
   public final Setting<Boolean> mineScanDroppedItems;
   public final Setting<Long> mineDropLoiterDurationMSThanksLouca;
   public final Setting<Boolean> distanceTrim;
   public final Setting<Boolean> cancelOnGoalInvalidation;
   public final Setting<Integer> axisHeight;
   public final Setting<Boolean> disconnectOnArrival;
   public final Setting<Boolean> legitMine;
   public final Setting<Integer> legitMineYLevel;
   public final Setting<Boolean> legitMineIncludeDiagonals;
   public final Setting<Boolean> forceInternalMining;
   public final Setting<Boolean> internalMiningAirException;
   public final Setting<Double> followOffsetDistance;
   public final Setting<Float> followOffsetDirection;
   public final Setting<Integer> followRadius;
   public final Setting<Integer> followTargetMaxDistance;
   public final Setting<Boolean> disableCompletionCheck;
   public final Setting<Long> cachedChunksExpirySeconds;
   @Settings.JavaOnly
   public final Setting<Consumer<class_2561>> logger;
   @Settings.JavaOnly
   public final Setting<BiConsumer<String, Boolean>> notifier;
   @Settings.JavaOnly
   public final Setting<BiConsumer<class_2561, class_2561>> toaster;
   public final Setting<Boolean> verboseCommandExceptions;
   public final Setting<Double> yLevelBoxSize;
   public final Setting<Color> colorCurrentPath;
   public final Setting<Color> colorNextPath;
   public final Setting<Color> colorBlocksToBreak;
   public final Setting<Color> colorBlocksToPlace;
   public final Setting<Color> colorBlocksToWalkInto;
   public final Setting<Color> colorBestPathSoFar;
   public final Setting<Color> colorMostRecentConsidered;
   public final Setting<Color> colorGoalBox;
   public final Setting<Color> colorInvertedGoalBox;
   public final Setting<Color> colorSelection;
   public final Setting<Color> colorSelectionPos1;
   public final Setting<Color> colorSelectionPos2;
   public final Setting<Float> selectionOpacity;
   public final Setting<Float> selectionLineWidth;
   public final Setting<Boolean> renderSelection;
   public final Setting<Boolean> renderSelectionIgnoreDepth;
   public final Setting<Boolean> renderSelectionCorners;
   public final Setting<Boolean> useSwordToMine;
   public final Setting<Boolean> desktopNotifications;
   public final Setting<Boolean> notificationOnPathComplete;
   public final Setting<Boolean> notificationOnFarmFail;
   public final Setting<Boolean> notificationOnBuildFinished;
   public final Setting<Boolean> notificationOnExploreFinished;
   public final Setting<Boolean> notificationOnMineFail;
   public final Setting<Integer> elytraSimulationTicks;
   public final Setting<Integer> elytraPitchRange;
   public final Setting<Double> elytraFireworkSpeed;
   public final Setting<Integer> elytraFireworkSetbackUseDelay;
   public final Setting<Double> elytraMinimumAvoidance;
   public final Setting<Boolean> elytraConserveFireworks;
   public final Setting<Boolean> elytraRenderRaytraces;
   public final Setting<Boolean> elytraRenderHitboxRaytraces;
   public final Setting<Boolean> elytraRenderSimulation;
   public final Setting<Boolean> elytraAutoJump;
   public final Setting<Long> elytraNetherSeed;
   public final Setting<Boolean> elytraPredictTerrain;
   public final Setting<Boolean> elytraAutoSwap;
   public final Setting<Integer> elytraMinimumDurability;
   public final Setting<Integer> elytraMinFireworksBeforeLanding;
   public final Setting<Boolean> elytraAllowEmergencyLand;
   public final Setting<Long> elytraTimeBetweenCacheCullSecs;
   public final Setting<Integer> elytraCacheCullDistance;
   public final Setting<Boolean> elytraAllowLandOnNetherFortress;
   public final Setting<Boolean> elytraTermsAccepted;
   public final Setting<Boolean> elytraChatSpam;
   public final Map<String, Setting<?>> byLowerName;
   public final List<Setting<?>> allSettings;
   public final Map<Setting<?>, Type> settingTypes;

   Settings() {
      this.allowBreak = new Setting<Boolean>(Boolean.TRUE);
      this.allowBreakAnyway = new Setting<List<class_2248>>(new ArrayList());
      this.allowSprint = new Setting<Boolean>(Boolean.TRUE);
      this.allowPlace = new Setting<Boolean>(Boolean.TRUE);
      this.allowPlaceInFluidsSource = new Setting<Boolean>(Boolean.TRUE);
      this.allowPlaceInFluidsFlow = new Setting<Boolean>(Boolean.TRUE);
      this.allowInventory = new Setting<Boolean>(Boolean.FALSE);
      this.ticksBetweenInventoryMoves = new Setting<Integer>(1);
      this.inventoryMoveOnlyIfStationary = new Setting<Boolean>(Boolean.FALSE);
      this.assumeExternalAutoTool = new Setting<Boolean>(Boolean.FALSE);
      this.autoTool = new Setting<Boolean>(Boolean.TRUE);
      this.blockPlacementPenalty = new Setting<Double>((double)20.0F);
      this.blockBreakAdditionalPenalty = new Setting<Double>((double)2.0F);
      this.jumpPenalty = new Setting<Double>((double)2.0F);
      this.walkOnWaterOnePenalty = new Setting<Double>((double)3.0F);
      this.strictLiquidCheck = new Setting<Boolean>(Boolean.FALSE);
      this.allowWaterBucketFall = new Setting<Boolean>(Boolean.TRUE);
      this.assumeWalkOnWater = new Setting<Boolean>(Boolean.FALSE);
      this.assumeWalkOnLava = new Setting<Boolean>(Boolean.FALSE);
      this.assumeStep = new Setting<Boolean>(Boolean.FALSE);
      this.assumeSafeWalk = new Setting<Boolean>(Boolean.FALSE);
      this.allowJumpAtBuildLimit = new Setting<Boolean>(Boolean.FALSE);
      this.allowJumpAt256 = new Setting<Boolean>(Boolean.FALSE);
      this.allowParkourAscend = new Setting<Boolean>(Boolean.TRUE);
      this.allowDiagonalDescend = new Setting<Boolean>(Boolean.FALSE);
      this.allowDiagonalAscend = new Setting<Boolean>(Boolean.FALSE);
      this.allowDownward = new Setting<Boolean>(Boolean.TRUE);
      this.acceptableThrowawayItems = new Setting<List<class_1792>>(new ArrayList(Arrays.asList(class_2246.field_10566.method_8389(), class_2246.field_10445.method_8389(), class_2246.field_10515.method_8389(), class_2246.field_10340.method_8389())));
      this.blocksToAvoid = new Setting<List<class_2248>>(new ArrayList());
      this.blocksToDisallowBreaking = new Setting<List<class_2248>>(new ArrayList());
      this.blocksToAvoidBreaking = new Setting<List<class_2248>>(new ArrayList(Arrays.asList(class_2246.field_9980, class_2246.field_10181, class_2246.field_10034, class_2246.field_10380)));
      this.avoidBreakingMultiplier = new Setting<Double>(0.1);
      this.buildIgnoreBlocks = new Setting<List<class_2248>>(new ArrayList(Arrays.asList()));
      this.buildSkipBlocks = new Setting<List<class_2248>>(new ArrayList(Arrays.asList()));
      this.buildValidSubstitutes = new Setting<Map<class_2248, List<class_2248>>>(new HashMap());
      this.buildSubstitutes = new Setting<Map<class_2248, List<class_2248>>>(new HashMap());
      this.okIfAir = new Setting<List<class_2248>>(new ArrayList(Arrays.asList()));
      this.buildIgnoreExisting = new Setting<Boolean>(Boolean.FALSE);
      this.buildIgnoreDirection = new Setting<Boolean>(Boolean.FALSE);
      this.buildIgnoreProperties = new Setting<List<String>>(new ArrayList(Arrays.asList()));
      this.avoidUpdatingFallingBlocks = new Setting<Boolean>(Boolean.TRUE);
      this.allowVines = new Setting<Boolean>(Boolean.FALSE);
      this.allowWalkOnBottomSlab = new Setting<Boolean>(Boolean.TRUE);
      this.allowParkour = new Setting<Boolean>(Boolean.FALSE);
      this.allowParkourPlace = new Setting<Boolean>(Boolean.FALSE);
      this.considerPotionEffects = new Setting<Boolean>(Boolean.TRUE);
      this.sprintAscends = new Setting<Boolean>(Boolean.TRUE);
      this.overshootTraverse = new Setting<Boolean>(Boolean.TRUE);
      this.pauseMiningForFallingBlocks = new Setting<Boolean>(Boolean.TRUE);
      this.rightClickSpeed = new Setting<Integer>(4);
      this.randomLooking113 = new Setting<Double>((double)2.0F);
      this.blockReachDistance = new Setting<Float>(4.5F);
      this.blockBreakSpeed = new Setting<Integer>(6);
      this.randomLooking = new Setting<Double>(0.01);
      this.costHeuristic = new Setting<Double>(3.563);
      this.pathingMaxChunkBorderFetch = new Setting<Integer>(50);
      this.backtrackCostFavoringCoefficient = new Setting<Double>((double)0.5F);
      this.avoidance = new Setting<Boolean>(Boolean.FALSE);
      this.mobSpawnerAvoidanceCoefficient = new Setting<Double>((double)2.0F);
      this.mobSpawnerAvoidanceRadius = new Setting<Integer>(16);
      this.mobAvoidanceCoefficient = new Setting<Double>((double)1.5F);
      this.mobAvoidanceRadius = new Setting<Integer>(8);
      this.rightClickContainerOnArrival = new Setting<Boolean>(Boolean.TRUE);
      this.enterPortal = new Setting<Boolean>(Boolean.TRUE);
      this.minimumImprovementRepropagation = new Setting<Boolean>(Boolean.TRUE);
      this.cutoffAtLoadBoundary = new Setting<Boolean>(Boolean.FALSE);
      this.maxCostIncrease = new Setting<Double>((double)10.0F);
      this.costVerificationLookahead = new Setting<Integer>(5);
      this.pathCutoffFactor = new Setting<Double>(0.9);
      this.pathCutoffMinimumLength = new Setting<Integer>(30);
      this.planningTickLookahead = new Setting<Integer>(150);
      this.pathingMapDefaultSize = new Setting<Integer>(1024);
      this.pathingMapLoadFactor = new Setting<Float>(0.75F);
      this.maxFallHeightNoWater = new Setting<Integer>(3);
      this.maxFallHeightBucket = new Setting<Integer>(20);
      this.allowOvershootDiagonalDescend = new Setting<Boolean>(Boolean.TRUE);
      this.simplifyUnloadedYCoord = new Setting<Boolean>(Boolean.TRUE);
      this.repackOnAnyBlockChange = new Setting<Boolean>(Boolean.TRUE);
      this.movementTimeoutTicks = new Setting<Integer>(100);
      this.primaryTimeoutMS = new Setting<Long>(500L);
      this.failureTimeoutMS = new Setting<Long>(2000L);
      this.planAheadPrimaryTimeoutMS = new Setting<Long>(4000L);
      this.planAheadFailureTimeoutMS = new Setting<Long>(5000L);
      this.slowPath = new Setting<Boolean>(Boolean.FALSE);
      this.slowPathTimeDelayMS = new Setting<Long>(100L);
      this.slowPathTimeoutMS = new Setting<Long>(40000L);
      this.doBedWaypoints = new Setting<Boolean>(Boolean.TRUE);
      this.doDeathWaypoints = new Setting<Boolean>(Boolean.TRUE);
      this.chunkCaching = new Setting<Boolean>(Boolean.TRUE);
      this.pruneRegionsFromRAM = new Setting<Boolean>(Boolean.TRUE);
      this.chunkPackerQueueMaxSize = new Setting<Integer>(2000);
      this.backfill = new Setting<Boolean>(Boolean.FALSE);
      this.logAsToast = new Setting<Boolean>(Boolean.FALSE);
      this.chatDebug = new Setting<Boolean>(Boolean.FALSE);
      this.chatControl = new Setting<Boolean>(Boolean.FALSE);
      this.chatControlAnyway = new Setting<Boolean>(Boolean.FALSE);
      this.renderPath = new Setting<Boolean>(Boolean.TRUE);
      this.renderPathAsLine = new Setting<Boolean>(Boolean.FALSE);
      this.renderGoal = new Setting<Boolean>(Boolean.TRUE);
      this.renderGoalAnimated = new Setting<Boolean>(Boolean.TRUE);
      this.renderSelectionBoxes = new Setting<Boolean>(Boolean.TRUE);
      this.renderGoalIgnoreDepth = new Setting<Boolean>(Boolean.TRUE);
      this.renderGoalXZBeacon = new Setting<Boolean>(Boolean.FALSE);
      this.renderSelectionBoxesIgnoreDepth = new Setting<Boolean>(Boolean.TRUE);
      this.renderPathIgnoreDepth = new Setting<Boolean>(Boolean.TRUE);
      this.pathRenderLineWidthPixels = new Setting<Float>(5.0F);
      this.goalRenderLineWidthPixels = new Setting<Float>(3.0F);
      this.fadePath = new Setting<Boolean>(Boolean.FALSE);
      this.freeLook = new Setting<Boolean>(Boolean.TRUE);
      this.blockFreeLook = new Setting<Boolean>(Boolean.FALSE);
      this.elytraFreeLook = new Setting<Boolean>(Boolean.TRUE);
      this.smoothLook = new Setting<Boolean>(Boolean.FALSE);
      this.elytraSmoothLook = new Setting<Boolean>(Boolean.FALSE);
      this.smoothLookTicks = new Setting<Integer>(5);
      this.remainWithExistingLookDirection = new Setting<Boolean>(Boolean.TRUE);
      this.antiCheatCompatibility = new Setting<Boolean>(Boolean.TRUE);
      this.pathThroughCachedOnly = new Setting<Boolean>(Boolean.FALSE);
      this.sprintInWater = new Setting<Boolean>(Boolean.TRUE);
      this.blacklistClosestOnFailure = new Setting<Boolean>(Boolean.TRUE);
      this.renderCachedChunks = new Setting<Boolean>(Boolean.FALSE);
      this.cachedChunksOpacity = new Setting<Float>(0.5F);
      this.prefixControl = new Setting<Boolean>(Boolean.TRUE);
      this.prefix = new Setting<String>("#");
      this.shortBaritonePrefix = new Setting<Boolean>(Boolean.FALSE);
      this.useMessageTag = new Setting<Boolean>(Boolean.FALSE);
      this.echoCommands = new Setting<Boolean>(Boolean.TRUE);
      this.censorCoordinates = new Setting<Boolean>(Boolean.FALSE);
      this.censorRanCommands = new Setting<Boolean>(Boolean.FALSE);
      this.itemSaver = new Setting<Boolean>(Boolean.FALSE);
      this.itemSaverThreshold = new Setting<Integer>(10);
      this.preferSilkTouch = new Setting<Boolean>(Boolean.FALSE);
      this.walkWhileBreaking = new Setting<Boolean>(Boolean.TRUE);
      this.splicePath = new Setting<Boolean>(Boolean.TRUE);
      this.maxPathHistoryLength = new Setting<Integer>(300);
      this.pathHistoryCutoffAmount = new Setting<Integer>(50);
      this.mineGoalUpdateInterval = new Setting<Integer>(5);
      this.maxCachedWorldScanCount = new Setting<Integer>(10);
      this.mineMaxOreLocationsCount = new Setting<Integer>(64);
      this.minYLevelWhileMining = new Setting<Integer>(0);
      this.maxYLevelWhileMining = new Setting<Integer>(2031);
      this.allowOnlyExposedOres = new Setting<Boolean>(Boolean.FALSE);
      this.allowOnlyExposedOresDistance = new Setting<Integer>(1);
      this.exploreForBlocks = new Setting<Boolean>(Boolean.TRUE);
      this.worldExploringChunkOffset = new Setting<Integer>(0);
      this.exploreChunkSetMinimumSize = new Setting<Integer>(10);
      this.exploreMaintainY = new Setting<Integer>(64);
      this.replantCrops = new Setting<Boolean>(Boolean.TRUE);
      this.replantNetherWart = new Setting<Boolean>(Boolean.FALSE);
      this.farmMaxScanSize = new Setting<Integer>(256);
      this.extendCacheOnThreshold = new Setting<Boolean>(Boolean.FALSE);
      this.buildInLayers = new Setting<Boolean>(Boolean.FALSE);
      this.layerOrder = new Setting<Boolean>(Boolean.FALSE);
      this.layerHeight = new Setting<Integer>(1);
      this.startAtLayer = new Setting<Integer>(0);
      this.skipFailedLayers = new Setting<Boolean>(Boolean.FALSE);
      this.buildOnlySelection = new Setting<Boolean>(Boolean.FALSE);
      this.buildRepeat = new Setting<class_2382>(new class_2382(0, 0, 0));
      this.buildRepeatCount = new Setting<Integer>(-1);
      this.buildRepeatSneaky = new Setting<Boolean>(Boolean.TRUE);
      this.breakFromAbove = new Setting<Boolean>(Boolean.FALSE);
      this.goalBreakFromAbove = new Setting<Boolean>(Boolean.FALSE);
      this.mapArtMode = new Setting<Boolean>(Boolean.FALSE);
      this.okIfWater = new Setting<Boolean>(Boolean.FALSE);
      this.incorrectSize = new Setting<Integer>(100);
      this.breakCorrectBlockPenaltyMultiplier = new Setting<Double>((double)10.0F);
      this.placeIncorrectBlockPenaltyMultiplier = new Setting<Double>((double)2.0F);
      this.schematicOrientationX = new Setting<Boolean>(Boolean.FALSE);
      this.schematicOrientationY = new Setting<Boolean>(Boolean.FALSE);
      this.schematicOrientationZ = new Setting<Boolean>(Boolean.FALSE);
      this.buildSchematicRotation = new Setting<class_2470>(class_2470.field_11467);
      this.buildSchematicMirror = new Setting<class_2415>(class_2415.field_11302);
      this.schematicFallbackExtension = new Setting<String>("schematic");
      this.builderTickScanRadius = new Setting<Integer>(5);
      this.mineScanDroppedItems = new Setting<Boolean>(Boolean.TRUE);
      this.mineDropLoiterDurationMSThanksLouca = new Setting<Long>(250L);
      this.distanceTrim = new Setting<Boolean>(Boolean.TRUE);
      this.cancelOnGoalInvalidation = new Setting<Boolean>(Boolean.TRUE);
      this.axisHeight = new Setting<Integer>(120);
      this.disconnectOnArrival = new Setting<Boolean>(Boolean.FALSE);
      this.legitMine = new Setting<Boolean>(Boolean.FALSE);
      this.legitMineYLevel = new Setting<Integer>(-59);
      this.legitMineIncludeDiagonals = new Setting<Boolean>(Boolean.FALSE);
      this.forceInternalMining = new Setting<Boolean>(Boolean.TRUE);
      this.internalMiningAirException = new Setting<Boolean>(Boolean.TRUE);
      this.followOffsetDistance = new Setting<Double>((double)0.0F);
      this.followOffsetDirection = new Setting<Float>(0.0F);
      this.followRadius = new Setting<Integer>(3);
      this.followTargetMaxDistance = new Setting<Integer>(0);
      this.disableCompletionCheck = new Setting<Boolean>(Boolean.FALSE);
      this.cachedChunksExpirySeconds = new Setting<Long>(-1L);
      this.logger = new Setting<Consumer<class_2561>>((Consumer)(var1x) -> {
         try {
            class_7591 var2 = (Boolean)this.useMessageTag.value ? Helper.MESSAGE_TAG : null;
            class_310.method_1551().field_1705.method_1743().method_44811(var1x, (class_7469)null, var2);
         } catch (Throwable var3) {
            LOGGER.warn("Failed to log message to chat: " + var1x.getString(), var3);
         }
      });
      this.notifier = new Setting<BiConsumer<String, Boolean>>(NotificationHelper::notify);
      this.toaster = new Setting<BiConsumer<class_2561, class_2561>>(BaritoneToast::addOrUpdate);
      this.verboseCommandExceptions = new Setting<Boolean>(Boolean.FALSE);
      this.yLevelBoxSize = new Setting<Double>((double)15.0F);
      this.colorCurrentPath = new Setting<Color>(Color.RED);
      this.colorNextPath = new Setting<Color>(Color.MAGENTA);
      this.colorBlocksToBreak = new Setting<Color>(Color.RED);
      this.colorBlocksToPlace = new Setting<Color>(Color.GREEN);
      this.colorBlocksToWalkInto = new Setting<Color>(Color.MAGENTA);
      this.colorBestPathSoFar = new Setting<Color>(Color.BLUE);
      this.colorMostRecentConsidered = new Setting<Color>(Color.CYAN);
      this.colorGoalBox = new Setting<Color>(Color.GREEN);
      this.colorInvertedGoalBox = new Setting<Color>(Color.RED);
      this.colorSelection = new Setting<Color>(Color.CYAN);
      this.colorSelectionPos1 = new Setting<Color>(Color.BLACK);
      this.colorSelectionPos2 = new Setting<Color>(Color.ORANGE);
      this.selectionOpacity = new Setting<Float>(0.5F);
      this.selectionLineWidth = new Setting<Float>(2.0F);
      this.renderSelection = new Setting<Boolean>(Boolean.TRUE);
      this.renderSelectionIgnoreDepth = new Setting<Boolean>(Boolean.TRUE);
      this.renderSelectionCorners = new Setting<Boolean>(Boolean.TRUE);
      this.useSwordToMine = new Setting<Boolean>(Boolean.TRUE);
      this.desktopNotifications = new Setting<Boolean>(Boolean.FALSE);
      this.notificationOnPathComplete = new Setting<Boolean>(Boolean.TRUE);
      this.notificationOnFarmFail = new Setting<Boolean>(Boolean.TRUE);
      this.notificationOnBuildFinished = new Setting<Boolean>(Boolean.TRUE);
      this.notificationOnExploreFinished = new Setting<Boolean>(Boolean.TRUE);
      this.notificationOnMineFail = new Setting<Boolean>(Boolean.TRUE);
      this.elytraSimulationTicks = new Setting<Integer>(20);
      this.elytraPitchRange = new Setting<Integer>(25);
      this.elytraFireworkSpeed = new Setting<Double>(1.2);
      this.elytraFireworkSetbackUseDelay = new Setting<Integer>(15);
      this.elytraMinimumAvoidance = new Setting<Double>(0.2);
      this.elytraConserveFireworks = new Setting<Boolean>(Boolean.FALSE);
      this.elytraRenderRaytraces = new Setting<Boolean>(Boolean.FALSE);
      this.elytraRenderHitboxRaytraces = new Setting<Boolean>(Boolean.FALSE);
      this.elytraRenderSimulation = new Setting<Boolean>(Boolean.TRUE);
      this.elytraAutoJump = new Setting<Boolean>(Boolean.FALSE);
      this.elytraNetherSeed = new Setting<Long>(146008555100680L);
      this.elytraPredictTerrain = new Setting<Boolean>(Boolean.FALSE);
      this.elytraAutoSwap = new Setting<Boolean>(Boolean.TRUE);
      this.elytraMinimumDurability = new Setting<Integer>(5);
      this.elytraMinFireworksBeforeLanding = new Setting<Integer>(5);
      this.elytraAllowEmergencyLand = new Setting<Boolean>(Boolean.TRUE);
      this.elytraTimeBetweenCacheCullSecs = new Setting<Long>(TimeUnit.MINUTES.toSeconds(3L));
      this.elytraCacheCullDistance = new Setting<Integer>(5000);
      this.elytraAllowLandOnNetherFortress = new Setting<Boolean>(Boolean.FALSE);
      this.elytraTermsAccepted = new Setting<Boolean>(Boolean.FALSE);
      this.elytraChatSpam = new Setting<Boolean>(Boolean.FALSE);
      Field[] var1 = this.getClass().getFields();
      HashMap var2 = new HashMap();
      ArrayList var3 = new ArrayList();
      HashMap var4 = new HashMap();

      try {
         int var5 = var1.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Field var7;
            if ((var7 = var1[var6]).getType().equals(Setting.class)) {
               Setting var8 = (Setting)var7.get(this);
               String var9 = var7.getName();
               var8.name = var9;
               var8.javaOnly = var7.isAnnotationPresent(JavaOnly.class);
               var9 = var9.toLowerCase();
               if (var2.containsKey(var9)) {
                  throw new IllegalStateException("Duplicate setting name");
               }

               var2.put(var9, var8);
               var3.add(var8);
               var4.put(var8, ((ParameterizedType)var7.getGenericType()).getActualTypeArguments()[0]);
            }
         }
      } catch (IllegalAccessException var10) {
         throw new IllegalStateException(var10);
      }

      this.byLowerName = Collections.unmodifiableMap(var2);
      this.allSettings = Collections.unmodifiableList(var3);
      this.settingTypes = Collections.unmodifiableMap(var4);
   }

   public final <T> List<Setting<T>> getAllValuesByType(Class<T> var1) {
      ArrayList var2 = new ArrayList();
      Iterator var3 = this.allSettings.iterator();

      while(var3.hasNext()) {
         Setting var4;
         if ((var4 = (Setting)var3.next()).getValueClass().equals(var1)) {
            var2.add(var4);
         }
      }

      return var2;
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target({ElementType.FIELD})
   @interface JavaOnly {
   }

   public final class Setting<T> {
      public T value;
      public final T defaultValue;
      String name;
      boolean javaOnly;

      Setting(T var2) {
         if (var2 == null) {
            throw new IllegalArgumentException("Cannot determine value type class from null");
         } else {
            this.value = var2;
            this.defaultValue = var2;
            this.javaOnly = false;
         }
      }

      @Deprecated
      public final T get() {
         return this.value;
      }

      public final String getName() {
         return this.name;
      }

      public final Class<T> getValueClass() {
         return TypeUtils.resolveBaseClass(this.getType());
      }

      public final String toString() {
         return SettingsUtil.settingToString(this);
      }

      public final void reset() {
         this.value = this.defaultValue;
      }

      public final Type getType() {
         return (Type)Settings.this.settingTypes.get(this);
      }

      public final boolean isJavaOnly() {
         return this.javaOnly;
      }
   }
}
