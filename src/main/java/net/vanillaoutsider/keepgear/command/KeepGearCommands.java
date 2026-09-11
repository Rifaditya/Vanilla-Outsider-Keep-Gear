// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.keepgear.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.vanillaoutsider.keepgear.KeepGear;
import net.vanillaoutsider.keepgear.config.ConfigManager;
import net.vanillaoutsider.keepgear.config.KeepGearConfig;

public final class KeepGearCommands {

    private KeepGearCommands() {}

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("keepgear")
            .executes(ctx -> sendStatus(ctx.getSource()))
            .then(Commands.literal("status")
                .executes(ctx -> sendStatus(ctx.getSource())))
            .then(Commands.literal("help")
                .executes(ctx -> sendHelp(ctx.getSource())))
            .then(Commands.literal("reload")
                .requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
                .executes(ctx -> reloadConfig(ctx.getSource())))
            .then(Commands.literal("reset")
                .requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
                .executes(ctx -> resetConfig(ctx.getSource())))
            .then(Commands.literal("set")
                .requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
                .then(Commands.literal("enabled")
                    .then(Commands.argument("value", BoolArgumentType.bool())
                        .executes(ctx -> {
                            KeepGear.getConfig().enabled = BoolArgumentType.getBool(ctx, "value");
                            ConfigManager.save();
                            ctx.getSource().sendSuccess(() -> Component.literal("KeepGear enabled set to: " + KeepGear.getConfig().enabled), true);
                            return 1;
                        })))
                .then(Commands.literal("keepArmor")
                    .then(Commands.argument("value", BoolArgumentType.bool())
                        .executes(ctx -> {
                            KeepGear.getConfig().keepArmor = BoolArgumentType.getBool(ctx, "value");
                            ConfigManager.save();
                            ctx.getSource().sendSuccess(() -> Component.literal("KeepGear keepArmor set to: " + KeepGear.getConfig().keepArmor), true);
                            return 1;
                        })))
                .then(Commands.literal("keepWeapons")
                    .then(Commands.argument("value", BoolArgumentType.bool())
                        .executes(ctx -> {
                            KeepGear.getConfig().keepWeapons = BoolArgumentType.getBool(ctx, "value");
                            ConfigManager.save();
                            ctx.getSource().sendSuccess(() -> Component.literal("KeepGear keepWeapons set to: " + KeepGear.getConfig().keepWeapons), true);
                            return 1;
                        })))
                .then(Commands.literal("keepTools")
                    .then(Commands.argument("value", BoolArgumentType.bool())
                        .executes(ctx -> {
                            KeepGear.getConfig().keepTools = BoolArgumentType.getBool(ctx, "value");
                            ConfigManager.save();
                            ctx.getSource().sendSuccess(() -> Component.literal("KeepGear keepTools set to: " + KeepGear.getConfig().keepTools), true);
                            return 1;
                        })))
                .then(Commands.literal("keepContainers")
                    .then(Commands.argument("value", BoolArgumentType.bool())
                        .executes(ctx -> {
                            KeepGear.getConfig().keepContainers = BoolArgumentType.getBool(ctx, "value");
                            ConfigManager.save();
                            ctx.getSource().sendSuccess(() -> Component.literal("KeepGear keepContainers set to: " + KeepGear.getConfig().keepContainers), true);
                            return 1;
                        })))
                .then(Commands.literal("penaltyPercent")
                    .then(Commands.argument("value", DoubleArgumentType.doubleArg(0.0, 100.0))
                        .executes(ctx -> {
                            KeepGear.getConfig().penaltyPercent = DoubleArgumentType.getDouble(ctx, "value");
                            ConfigManager.save();
                            ctx.getSource().sendSuccess(() -> Component.literal("KeepGear penaltyPercent set to: " + KeepGear.getConfig().penaltyPercent + "%"), true);
                            return 1;
                        })))
                .then(Commands.literal("useEchoShard")
                    .then(Commands.argument("value", BoolArgumentType.bool())
                        .executes(ctx -> {
                            KeepGear.getConfig().useEchoShard = BoolArgumentType.getBool(ctx, "value");
                            ConfigManager.save();
                            ctx.getSource().sendSuccess(() -> Component.literal("KeepGear useEchoShard set to: " + KeepGear.getConfig().useEchoShard), true);
                            return 1;
                        })))
            )
        );
    }

    private static int sendStatus(CommandSourceStack source) {
        KeepGearConfig c = KeepGear.getConfig();
        source.sendSuccess(() -> Component.literal("[Vanilla Outsider: Keep Gear Status]").withStyle(ChatFormatting.GOLD), false);
        source.sendSuccess(() -> Component.literal("  Master Enabled: " + (c.enabled ? "true" : "false")), false);
        source.sendSuccess(() -> Component.literal("  Armor: " + c.keepArmor + " | Weapons: " + c.keepWeapons + " | Tools: " + c.keepTools), false);
        source.sendSuccess(() -> Component.literal("  Shields: " + c.keepShields + " | Elytra: " + c.keepElytra + " | Containers: " + c.keepContainers), false);
        source.sendSuccess(() -> Component.literal("  Penalty: " + (c.penaltyEnabled ? (c.penaltyPercent + "%") : "Disabled") + " | Echo Shard: " + c.useEchoShard), false);
        return 1;
    }

    private static int sendHelp(CommandSourceStack source) {
        source.sendSuccess(() -> Component.literal("[Keep Gear Commands]").withStyle(ChatFormatting.GOLD), false);
        source.sendSuccess(() -> Component.literal("/keepgear status - View current settings"), false);
        source.sendSuccess(() -> Component.literal("/keepgear set <property> <value> - Update setting"), false);
        source.sendSuccess(() -> Component.literal("/keepgear reset - Restore defaults"), false);
        source.sendSuccess(() -> Component.literal("/keepgear reload - Reload config from disk"), false);
        return 1;
    }

    private static int reloadConfig(CommandSourceStack source) {
        ConfigManager.reload();
        source.sendSuccess(() -> Component.literal("Keep Gear configuration reloaded from disk."), true);
        return 1;
    }

    private static int resetConfig(CommandSourceStack source) {
        ConfigManager.resetToDefaults();
        source.sendSuccess(() -> Component.literal("Keep Gear configuration reset to defaults."), true);
        return 1;
    }
}
