// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.keepgear.command;

import com.mojang.brigadier.CommandDispatcher;
import java.util.List;
import net.dasik.social.api.command.GameRuleCommandBridge;
import net.dasik.social.api.command.ModCommandDispatcher;
import net.dasik.social.api.command.UniversalCommandRegistry;
import net.minecraft.commands.CommandSourceStack;
import net.vanillaoutsider.keepgear.KeepGear;
import net.vanillaoutsider.keepgear.config.ConfigManager;
import net.vanillaoutsider.keepgear.config.KeepGearConfig;

public final class KeepGearCommands {
    public static final String ROOT_LITERAL = "keepgear";
    public static final String MOD_NAME = "Keep Gear";

    private static volatile GameRuleCommandBridge activeBridge;

    private KeepGearCommands() {}

    public static void init() {
        ModCommandDispatcher.init();
        activeBridge = createBridge();
        ModCommandDispatcher.registerSuite(ROOT_LITERAL, builder -> configureBuilder(builder, activeBridge));
    }

    public static UniversalCommandRegistry.Builder configureBuilder(UniversalCommandRegistry.Builder builder, GameRuleCommandBridge bridge) {
        return builder
            .withRootLiteral(ROOT_LITERAL)
            .withModName(MOD_NAME)
            .withBridge(bridge)
            .withStatusSupplier(level -> {
                KeepGearConfig c = KeepGear.getConfig();
                return List.of(
                    "Master Enabled: " + c.enabled,
                    "Gear Toggles: Armor=" + c.keepArmor + ", Weapons=" + c.keepWeapons + ", Tools=" + c.keepTools + ", Shields=" + c.keepShields + ", Elytra=" + c.keepElytra + ", Containers=" + c.keepContainers,
                    "Durability Penalty: " + (c.penaltyEnabled ? (c.penaltyPercent + "%") : "Disabled"),
                    "Echo Shard Insurance: " + (c.useEchoShard ? "Enabled" : "Disabled")
                );
            })
            .withReloadAction(server -> {
                ConfigManager.reload();
                syncToBridge(bridge);
            });
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        if (activeBridge == null) {
            activeBridge = createBridge();
        }
        configureBuilder(UniversalCommandRegistry.builder(ROOT_LITERAL, dispatcher), activeBridge).register();
    }

    public static GameRuleCommandBridge createBridge() {
        GameRuleCommandBridge bridge = new GameRuleCommandBridge();
        KeepGearConfig config = KeepGear.getConfig();

        bridge.registerBoolean("enabled", null, config.enabled);
        bridge.registerBoolean("keepArmor", null, config.keepArmor);
        bridge.registerBoolean("keepWeapons", null, config.keepWeapons);
        bridge.registerBoolean("keepTools", null, config.keepTools);
        bridge.registerBoolean("keepShields", null, config.keepShields);
        bridge.registerBoolean("keepElytra", null, config.keepElytra);
        bridge.registerBoolean("keepContainers", null, config.keepContainers);
        bridge.registerDouble("penaltyPercent", null, config.penaltyPercent, 0.0, 100.0);
        bridge.registerBoolean("useEchoShard", null, config.useEchoShard);

        bridge.setSaveCallback(server -> {
            syncFromBridge(bridge);
            ConfigManager.save();
        });

        return bridge;
    }

    public static void syncFromBridge(GameRuleCommandBridge bridge) {
        if (bridge == null) return;
        KeepGearConfig config = KeepGear.getConfig();

        Object enabledVal = bridge.getRuleEntry("enabled") != null ? bridge.getRuleEntry("enabled").getCurrentValue() : null;
        if (enabledVal instanceof Boolean b) config.enabled = b;

        Object armorVal = bridge.getRuleEntry("keepArmor") != null ? bridge.getRuleEntry("keepArmor").getCurrentValue() : null;
        if (armorVal instanceof Boolean b) config.keepArmor = b;

        Object weaponsVal = bridge.getRuleEntry("keepWeapons") != null ? bridge.getRuleEntry("keepWeapons").getCurrentValue() : null;
        if (weaponsVal instanceof Boolean b) config.keepWeapons = b;

        Object toolsVal = bridge.getRuleEntry("keepTools") != null ? bridge.getRuleEntry("keepTools").getCurrentValue() : null;
        if (toolsVal instanceof Boolean b) config.keepTools = b;

        Object shieldsVal = bridge.getRuleEntry("keepShields") != null ? bridge.getRuleEntry("keepShields").getCurrentValue() : null;
        if (shieldsVal instanceof Boolean b) config.keepShields = b;

        Object elytraVal = bridge.getRuleEntry("keepElytra") != null ? bridge.getRuleEntry("keepElytra").getCurrentValue() : null;
        if (elytraVal instanceof Boolean b) config.keepElytra = b;

        Object containersVal = bridge.getRuleEntry("keepContainers") != null ? bridge.getRuleEntry("keepContainers").getCurrentValue() : null;
        if (containersVal instanceof Boolean b) config.keepContainers = b;

        Object penaltyVal = bridge.getRuleEntry("penaltyPercent") != null ? bridge.getRuleEntry("penaltyPercent").getCurrentValue() : null;
        if (penaltyVal instanceof Number n) config.penaltyPercent = n.doubleValue();

        Object echoVal = bridge.getRuleEntry("useEchoShard") != null ? bridge.getRuleEntry("useEchoShard").getCurrentValue() : null;
        if (echoVal instanceof Boolean b) config.useEchoShard = b;
    }

    public static void syncToBridge(GameRuleCommandBridge bridge) {
        if (bridge == null) return;
        KeepGearConfig config = KeepGear.getConfig();
        if (bridge.getRuleEntry("enabled") != null) bridge.getRuleEntry("enabled").setCurrentValue(config.enabled);
        if (bridge.getRuleEntry("keepArmor") != null) bridge.getRuleEntry("keepArmor").setCurrentValue(config.keepArmor);
        if (bridge.getRuleEntry("keepWeapons") != null) bridge.getRuleEntry("keepWeapons").setCurrentValue(config.keepWeapons);
        if (bridge.getRuleEntry("keepTools") != null) bridge.getRuleEntry("keepTools").setCurrentValue(config.keepTools);
        if (bridge.getRuleEntry("keepShields") != null) bridge.getRuleEntry("keepShields").setCurrentValue(config.keepShields);
        if (bridge.getRuleEntry("keepElytra") != null) bridge.getRuleEntry("keepElytra").setCurrentValue(config.keepElytra);
        if (bridge.getRuleEntry("keepContainers") != null) bridge.getRuleEntry("keepContainers").setCurrentValue(config.keepContainers);
        if (bridge.getRuleEntry("penaltyPercent") != null) bridge.getRuleEntry("penaltyPercent").setCurrentValue(config.penaltyPercent);
        if (bridge.getRuleEntry("useEchoShard") != null) bridge.getRuleEntry("useEchoShard").setCurrentValue(config.useEchoShard);
    }

    public static GameRuleCommandBridge getBridge() {
        return activeBridge;
    }
}
