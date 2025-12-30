package net.vanillaoutsider.keepgear

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component
import net.vanillaoutsider.keepgear.config.ConfigManager

/**
 * Registers admin commands for Keep Gear mod.
 */
object KeepGearCommands {
    
    fun register() {
        CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
            registerCommands(dispatcher)
        }
        KeepGear.logger.info("Registered admin commands")
    }
    
    private fun registerCommands(dispatcher: CommandDispatcher<CommandSourceStack>) {
        dispatcher.register(
            Commands.literal("keepgear")
                .executes { ctx -> showStatus(ctx) }
                .then(Commands.literal("help").executes { ctx -> showHelp(ctx) })
                .then(Commands.literal("reload").executes { ctx -> reload(ctx) })
                .then(Commands.literal("status").executes { ctx -> showStatus(ctx) })
                // Blacklist commands
                .then(Commands.literal("blacklist")
                    .then(Commands.literal("list").executes { ctx -> listBlacklist(ctx) })
                    .then(Commands.literal("add")
                        .then(Commands.argument("item", StringArgumentType.greedyString())
                            .executes { ctx -> addBlacklist(ctx, StringArgumentType.getString(ctx, "item")) }))
                    .then(Commands.literal("remove")
                        .then(Commands.argument("item", StringArgumentType.greedyString())
                            .executes { ctx -> removeBlacklist(ctx, StringArgumentType.getString(ctx, "item")) }))
                    .then(Commands.literal("clear").executes { ctx -> clearBlacklist(ctx) }))
                // Whitelist commands
                .then(Commands.literal("whitelist")
                    .then(Commands.literal("list").executes { ctx -> listWhitelist(ctx) })
                    .then(Commands.literal("add")
                        .then(Commands.argument("item", StringArgumentType.greedyString())
                            .executes { ctx -> addWhitelist(ctx, StringArgumentType.getString(ctx, "item")) }))
                    .then(Commands.literal("remove")
                        .then(Commands.argument("item", StringArgumentType.greedyString())
                            .executes { ctx -> removeWhitelist(ctx, StringArgumentType.getString(ctx, "item")) }))
                    .then(Commands.literal("clear").executes { ctx -> clearWhitelist(ctx) }))
                // Dimension commands
                .then(Commands.literal("dimension")
                    .then(Commands.literal("list").executes { ctx -> listDimensions(ctx) })
                    .then(Commands.literal("disable")
                        .then(Commands.argument("dimension", StringArgumentType.greedyString())
                            .executes { ctx -> disableDimension(ctx, StringArgumentType.getString(ctx, "dimension")) }))
                    .then(Commands.literal("enable")
                        .then(Commands.argument("dimension", StringArgumentType.greedyString())
                            .executes { ctx -> enableDimension(ctx, StringArgumentType.getString(ctx, "dimension")) })))
        )
    }
    
    // === Main Commands ===
    
    private fun showHelp(ctx: CommandContext<CommandSourceStack>): Int {
        val source = ctx.source
        source.sendSuccess({ Component.literal("§6=== Keep Gear Commands ===") }, false)
        source.sendSuccess({ Component.literal("§7/keepgear §f- Show status") }, false)
        source.sendSuccess({ Component.literal("§7/keepgear reload §f- Reload config") }, false)
        source.sendSuccess({ Component.literal("§7/keepgear blacklist list/add/remove/clear") }, false)
        source.sendSuccess({ Component.literal("§7/keepgear whitelist list/add/remove/clear") }, false)
        source.sendSuccess({ Component.literal("§7/keepgear dimension list/disable/enable") }, false)
        return 1
    }
    
    private fun reload(ctx: CommandContext<CommandSourceStack>): Int {
        ConfigManager.reload()
        ctx.source.sendSuccess({ Component.literal("§aConfig reloaded!") }, true)
        return 1
    }
    
    private fun showStatus(ctx: CommandContext<CommandSourceStack>): Int {
        val config = ConfigManager.config
        val source = ctx.source
        source.sendSuccess({ Component.literal("§6=== Keep Gear Status ===") }, false)
        source.sendSuccess({ Component.literal("§7Enabled: §f${config.enabled}") }, false)
        source.sendSuccess({ Component.literal("§7Penalty: §f${config.penaltyPercent}%") }, false)
        source.sendSuccess({ Component.literal("§7XP Kept: §f${config.xpPercent}%") }, false)
        source.sendSuccess({ Component.literal("§7Blacklist: §f${config.blacklist.size} items") }, false)
        source.sendSuccess({ Component.literal("§7Whitelist: §f${config.whitelist.size} items") }, false)
        source.sendSuccess({ Component.literal("§7Disabled Dimensions: §f${config.dimensionBlacklist.size}") }, false)
        return 1
    }
    
    // === Blacklist Commands ===
    
    private fun listBlacklist(ctx: CommandContext<CommandSourceStack>): Int {
        val list = ConfigManager.config.blacklist
        if (list.isEmpty()) {
            ctx.source.sendSuccess({ Component.literal("§7Blacklist is empty") }, false)
        } else {
            ctx.source.sendSuccess({ Component.literal("§6Blacklisted items (${list.size}):") }, false)
            list.forEach { item ->
                ctx.source.sendSuccess({ Component.literal("§7- $item") }, false)
            }
        }
        return 1
    }
    
    private fun addBlacklist(ctx: CommandContext<CommandSourceStack>, item: String): Int {
        val list = ConfigManager.config.blacklist
        if (list.contains(item)) {
            ctx.source.sendFailure(Component.literal("§c$item is already blacklisted"))
            return 0
        }
        list.add(item)
        ConfigManager.save()
        ctx.source.sendSuccess({ Component.literal("§aAdded $item to blacklist") }, true)
        return 1
    }
    
    private fun removeBlacklist(ctx: CommandContext<CommandSourceStack>, item: String): Int {
        val list = ConfigManager.config.blacklist
        if (!list.contains(item)) {
            ctx.source.sendFailure(Component.literal("§c$item is not in blacklist"))
            return 0
        }
        list.remove(item)
        ConfigManager.save()
        ctx.source.sendSuccess({ Component.literal("§aRemoved $item from blacklist") }, true)
        return 1
    }
    
    private fun clearBlacklist(ctx: CommandContext<CommandSourceStack>): Int {
        ConfigManager.config.blacklist.clear()
        ConfigManager.save()
        ctx.source.sendSuccess({ Component.literal("§aBlacklist cleared") }, true)
        return 1
    }
    
    // === Whitelist Commands ===
    
    private fun listWhitelist(ctx: CommandContext<CommandSourceStack>): Int {
        val list = ConfigManager.config.whitelist
        if (list.isEmpty()) {
            ctx.source.sendSuccess({ Component.literal("§7Whitelist is empty") }, false)
        } else {
            ctx.source.sendSuccess({ Component.literal("§6Whitelisted items (${list.size}):") }, false)
            list.forEach { item ->
                ctx.source.sendSuccess({ Component.literal("§7- $item") }, false)
            }
        }
        return 1
    }
    
    private fun addWhitelist(ctx: CommandContext<CommandSourceStack>, item: String): Int {
        val list = ConfigManager.config.whitelist
        if (list.contains(item)) {
            ctx.source.sendFailure(Component.literal("§c$item is already whitelisted"))
            return 0
        }
        list.add(item)
        ConfigManager.save()
        ctx.source.sendSuccess({ Component.literal("§aAdded $item to whitelist") }, true)
        return 1
    }

    private fun removeWhitelist(ctx: CommandContext<CommandSourceStack>, item: String): Int {
        val list = ConfigManager.config.whitelist
        if (!list.contains(item)) {
            ctx.source.sendFailure(Component.literal("§c$item is not in whitelist"))
            return 0
        }
        list.remove(item)
        ConfigManager.save()
        ctx.source.sendSuccess({ Component.literal("§aRemoved $item from whitelist") }, true)
        return 1
    }

    private fun clearWhitelist(ctx: CommandContext<CommandSourceStack>): Int {
        ConfigManager.config.whitelist.clear()
        ConfigManager.save()
        ctx.source.sendSuccess({ Component.literal("§aWhitelist cleared") }, true)
        return 1
    }

    // === Dimension Commands ===

    private fun listDimensions(ctx: CommandContext<CommandSourceStack>): Int {
        val list = ConfigManager.config.dimensionBlacklist
        if (list.isEmpty()) {
            ctx.source.sendSuccess({ Component.literal("§aKeep Gear is enabled in ALL dimensions") }, false)
        } else {
            ctx.source.sendSuccess({ Component.literal("§6Keep Gear DISABLED in (${list.size} dimensions):") }, false)
            list.forEach { dim ->
                ctx.source.sendSuccess({ Component.literal("§7- $dim") }, false)
            }
        }
        return 1
    }

    private fun disableDimension(ctx: CommandContext<CommandSourceStack>, dimension: String): Int {
        val list = ConfigManager.config.dimensionBlacklist
        if (list.contains(dimension)) {
            ctx.source.sendFailure(Component.literal("§cKeep Gear is already disabled in $dimension"))
            return 0
        }
        list.add(dimension)
        ConfigManager.save()
        ctx.source.sendSuccess({ Component.literal("§aDisabled Keep Gear in $dimension") }, true)
        return 1
    }

    private fun enableDimension(ctx: CommandContext<CommandSourceStack>, dimension: String): Int {
        val list = ConfigManager.config.dimensionBlacklist
        if (!list.contains(dimension)) {
            ctx.source.sendFailure(Component.literal("§cKeep Gear is already enabled in $dimension"))
            return 0
        }
        list.remove(dimension)
        ConfigManager.save()
        ctx.source.sendSuccess({ Component.literal("§aEnabled Keep Gear in $dimension") }, true)
        return 1
    }
}
