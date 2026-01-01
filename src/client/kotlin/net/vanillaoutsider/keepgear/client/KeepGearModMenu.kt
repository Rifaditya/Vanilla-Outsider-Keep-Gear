package net.vanillaoutsider.keepgear.client

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import me.shedaniel.clothconfig2.api.ConfigBuilder
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import net.vanillaoutsider.keepgear.config.ConfigManager

/**
 * Mod Menu integration for config screen.
 */
class KeepGearModMenu : ModMenuApi {
    
    override fun getModConfigScreenFactory(): ConfigScreenFactory<*> {
        return ConfigScreenFactory { parent -> createConfigScreen(parent) }
    }
    
    private fun createConfigScreen(parent: Screen): Screen {
        val config = ConfigManager.config
        
        val builder = ConfigBuilder.create()
            .setParentScreen(parent)
            .setTitle(Component.translatable("config.vanilla-outsider-keep-gear.title"))
            .setSavingRunnable { ConfigManager.save() }
        
        // General category
        val general = builder.getOrCreateCategory(Component.literal("General"))
        val entryBuilder = builder.entryBuilder()
        
        general.addEntry(
            entryBuilder.startBooleanToggle(Component.literal("Enabled"), config.enabled)
                .setDefaultValue(true)
                .setTooltip(Component.literal("Enable or disable the mod"))
                .setSaveConsumer { config.enabled = it }
                .build()
        )

        // Integrations category
        val integrations = builder.getOrCreateCategory(Component.literal("Integrations"))

        integrations.addEntry(
            entryBuilder.startBooleanToggle(Component.translatable("config.vanilla-outsider-keep-gear.keepTrinkets"), config.keepTrinkets)
                .setDefaultValue(true)
                .setTooltip(Component.translatable("config.vanilla-outsider-keep-gear.keepTrinkets.tooltip"))
                .setSaveConsumer { config.keepTrinkets = it }
                .build()
        )

        // Item Categories
        val categories = builder.getOrCreateCategory(Component.literal("Item Categories"))
        
        categories.addEntry(
            entryBuilder.startBooleanToggle(Component.literal("Keep Armor"), config.keepArmor)
                .setDefaultValue(true)
                .setTooltip(Component.literal("Helmets, Chestplates, Leggings, Boots"))
                .setSaveConsumer { config.keepArmor = it }
                .build()
        )
        
        categories.addEntry(
            entryBuilder.startBooleanToggle(Component.literal("Keep Weapons"), config.keepWeapons)
                .setDefaultValue(true)
                .setTooltip(Component.literal("Swords, Bows, Crossbows, Tridents, Maces"))
                .setSaveConsumer { config.keepWeapons = it }
                .build()
        )
        
        categories.addEntry(
            entryBuilder.startBooleanToggle(Component.literal("Keep Tools"), config.keepTools)
                .setDefaultValue(true)
                .setTooltip(Component.literal("Pickaxes, Axes, Shovels, Hoes, Shears, Fishing Rods"))
                .setSaveConsumer { config.keepTools = it }
                .build()
        )
        
        categories.addEntry(
            entryBuilder.startBooleanToggle(Component.literal("Keep Shields"), config.keepShields)
                .setDefaultValue(true)
                .setTooltip(Component.literal("Shields"))
                .setSaveConsumer { config.keepShields = it }
                .build()
        )
        
        categories.addEntry(
            entryBuilder.startBooleanToggle(Component.literal("Keep Elytra"), config.keepElytra)
                .setDefaultValue(true)
                .setTooltip(Component.literal("Elytra"))
                .setSaveConsumer { config.keepElytra = it }
                .build()
        )
        
        categories.addEntry(
            entryBuilder.startBooleanToggle(Component.literal("Keep Consumables"), config.keepConsumables)
                .setDefaultValue(false)
                .setTooltip(Component.literal("Food, Potions (Items with Food component)"))
                .setSaveConsumer { config.keepConsumables = it }
                .build()
        )
        
        categories.addEntry(
            entryBuilder.startBooleanToggle(Component.literal("Keep Resources/Misc"), config.keepResources)
                .setDefaultValue(false)
                .setTooltip(Component.literal("Everything else without durability (Materials, Blocks)"))
                .setSaveConsumer { config.keepResources = it }
                .build()
        )
        
        categories.addEntry(
            entryBuilder.startBooleanToggle(Component.translatable("config.vanilla-outsider-keep-gear.keepContainers"), config.keepContainers)
                .setDefaultValue(false)
                .setTooltip(Component.translatable("config.vanilla-outsider-keep-gear.keepContainers.tooltip"))
                .setSaveConsumer { config.keepContainers = it }
                .build()
        )

        categories.addEntry(
            entryBuilder.startBooleanToggle(Component.translatable("config.vanilla-outsider-keep-gear.containerDropContents"), config.containerDropContents)
                .setDefaultValue(false)
                .setTooltip(Component.translatable("config.vanilla-outsider-keep-gear.containerDropContents.tooltip"))
                .setSaveConsumer { config.containerDropContents = it }
                .build()
        )
        
        categories.addEntry(
            entryBuilder.startStrList(Component.literal("Container Whitelist"), config.containerWhitelist)
                .setDefaultValue(
                    listOf(
                        "travelersbackpack:*",
                        "backpacks:backpack",
                        "backpacks:backpack_tier_*",
                        "minecraft:shulker_box",
                        "minecraft:white_shulker_box",
                        "minecraft:orange_shulker_box",
                        "minecraft:magenta_shulker_box",
                        "minecraft:light_blue_shulker_box",
                        "minecraft:yellow_shulker_box",
                        "minecraft:lime_shulker_box",
                        "minecraft:pink_shulker_box",
                        "minecraft:gray_shulker_box",
                        "minecraft:light_gray_shulker_box",
                        "minecraft:cyan_shulker_box",
                        "minecraft:purple_shulker_box",
                        "minecraft:blue_shulker_box",
                        "minecraft:brown_shulker_box",
                        "minecraft:green_shulker_box",
                        "minecraft:red_shulker_box",
                        "minecraft:black_shulker_box",
                        "minecraft:bundle",
                        "minecraft:*_bundle"
                    )
                )
                .setTooltip(Component.literal("IDs of containers to keep (supports wildcards)"))
                .setSaveConsumer { config.containerWhitelist = it.toMutableList() }
                .build()
        )
        
        // Penalty settings
        val penalty = builder.getOrCreateCategory(Component.literal("Durability Penalty"))
        
        penalty.addEntry(
            entryBuilder.startBooleanToggle(Component.literal("Penalty Enabled"), config.penaltyEnabled)
                .setDefaultValue(true)
                .setTooltip(Component.literal("Apply durability penalty to kept items"))
                .setSaveConsumer { config.penaltyEnabled = it }
                .build()
        )

        penalty.addEntry(
            entryBuilder.startBooleanToggle(Component.translatable("config.vanilla-outsider-keep-gear.useEchoShard"), config.useEchoShard)
                .setDefaultValue(true)
                .setTooltip(Component.translatable("config.vanilla-outsider-keep-gear.useEchoShard.tooltip"))
                .setSaveConsumer { config.useEchoShard = it }
                .build()
        )
        
        penalty.addEntry(
            entryBuilder.startDoubleField(Component.literal("Penalty Percent"), config.penaltyPercent)
                .setDefaultValue(1.2)
                .setMin(0.0)
                .setMax(100.0)
                .setTooltip(Component.literal("Percent of max durability lost on death"))
                .setSaveConsumer { config.penaltyPercent = it }
                .build()
        )

        penalty.addEntry(
            entryBuilder.startBooleanToggle(Component.translatable("config.vanilla-outsider-keep-gear.enchantmentPenaltyEnabled"), config.enchantmentPenaltyEnabled)
                .setDefaultValue(true)
                .setTooltip(Component.translatable("config.vanilla-outsider-keep-gear.enchantmentPenaltyEnabled.tooltip"))
                .setSaveConsumer { config.enchantmentPenaltyEnabled = it }
                .build()
        )

        penalty.addEntry(
            entryBuilder.startDoubleField(Component.translatable("config.vanilla-outsider-keep-gear.enchantmentPenaltyValue"), config.enchantmentPenaltyValue)
                .setDefaultValue(0.1)
                .setMin(0.0)
                .setMax(1.0)
                .setTooltip(Component.translatable("config.vanilla-outsider-keep-gear.enchantmentPenaltyValue.tooltip"))
                .setSaveConsumer { config.enchantmentPenaltyValue = it }
                .build()
        )
        
        // XP settings
        val xp = builder.getOrCreateCategory(Component.literal("Experience"))
        
        xp.addEntry(
            entryBuilder.startBooleanToggle(Component.literal("XP Retention Enabled"), config.xpEnabled)
                .setDefaultValue(true)
                .setTooltip(Component.literal("Keep some XP on death"))
                .setSaveConsumer { config.xpEnabled = it }
                .build()
        )
        
        xp.addEntry(
            entryBuilder.startIntField(Component.literal("XP Percent Kept"), config.xpPercent)
                .setDefaultValue(20)
                .setMin(0)
                .setMax(100)
                .setTooltip(Component.literal("Percent of XP kept on death"))
                .setSaveConsumer { config.xpPercent = it }
                .build()
        )
        
        // Feedback settings
        val feedback = builder.getOrCreateCategory(Component.literal("Feedback"))
        
        feedback.addEntry(
            entryBuilder.startBooleanToggle(Component.literal("Show Message"), config.showMessage)
                .setDefaultValue(true)
                .setTooltip(Component.literal("Show message when items are kept"))
                .setSaveConsumer { config.showMessage = it }
                .build()
        )
        
        feedback.addEntry(
            entryBuilder.startStrField(Component.literal("Message Text"), config.messageText)
                .setDefaultValue("§aYour gear has been saved!")
                .setTooltip(Component.literal("Message shown on respawn"))
                .setSaveConsumer { config.messageText = it }
                .build()
        )
        
        feedback.addEntry(
            entryBuilder.startBooleanToggle(Component.literal("Play Sound"), config.playSound)
                .setDefaultValue(true)
                .setTooltip(Component.literal("Play sound when items are kept"))
                .setSaveConsumer { config.playSound = it }
                .build()
        )
        
        // Death type settings
        val deathTypes = builder.getOrCreateCategory(Component.literal("Special Deaths"))
        
        deathTypes.addEntry(
            entryBuilder.startSelector(Component.literal("Void Death"), arrayOf("keep_gear", "drop_all"), config.voidDeath)
                .setDefaultValue("keep_gear")
                .setTooltip(Component.literal("Behavior when falling into void"))
                .setSaveConsumer { config.voidDeath = it }
                .build()
        )
        
        deathTypes.addEntry(
            entryBuilder.startSelector(Component.literal("PvP Death"), arrayOf("keep_gear", "drop_all"), config.pvpDeath)
                .setDefaultValue("keep_gear")
                .setTooltip(Component.literal("Behavior when killed by another player"))
                .setSaveConsumer { config.pvpDeath = it }
                .build()
        )

        // Advanced Penalties
        val advanced = builder.getOrCreateCategory(Component.translatable("config.vanilla-outsider-keep-gear.advanced"))
        
        advanced.addEntry(
            entryBuilder.startBooleanToggle(Component.translatable("config.vanilla-outsider-keep-gear.dropBindingCurse"), config.dropBindingCurse)
                .setDefaultValue(false)
                .setTooltip(Component.translatable("config.vanilla-outsider-keep-gear.dropBindingCurse.tooltip"))
                .setSaveConsumer { config.dropBindingCurse = it }
                .build()
        )

        advanced.addEntry(
             entryBuilder.startStrList(Component.translatable("config.vanilla-outsider-keep-gear.customMap"), config.customDeathPenalties)
                 .setDefaultValue(listOf())
                 .setTooltip(Component.translatable("config.vanilla-outsider-keep-gear.customMap.tooltip"))
                 .setSaveConsumer { config.customDeathPenalties = it.toMutableList() }
                 .build()
        )
        
        // Lava
        advanced.addEntry(entryBuilder.startTextDescription(Component.translatable("config.vanilla-outsider-keep-gear.lavaPenaltyEnabled")).build())
        advanced.addEntry(
            entryBuilder.startBooleanToggle(Component.literal("Enable Lava Penalty"), config.lavaPenaltyEnabled)
                .setDefaultValue(false)
                .setSaveConsumer { config.lavaPenaltyEnabled = it }
                .build()
        )
        advanced.addEntry(
            entryBuilder.startDoubleField(Component.translatable("config.vanilla-outsider-keep-gear.lavaPenaltyValue"), config.lavaPenaltyValue)
                .setDefaultValue(5.0)
                .setMin(0.0).setMax(100.0)
                .setSaveConsumer { config.lavaPenaltyValue = it }
                .build()
        )
        advanced.addEntry(
            entryBuilder.startDoubleField(Component.translatable("config.vanilla-outsider-keep-gear.lavaXpRetention"), config.lavaXpRetention)
                .setDefaultValue(0.0)
                .setMin(0.0).setMax(100.0)
                .setSaveConsumer { config.lavaXpRetention = it }
                .build()
        )
        
        // Fall
        advanced.addEntry(entryBuilder.startTextDescription(Component.translatable("config.vanilla-outsider-keep-gear.fallPenaltyEnabled")).build())
        advanced.addEntry(
            entryBuilder.startBooleanToggle(Component.literal("Enable Fall Penalty"), config.fallPenaltyEnabled)
                .setDefaultValue(false)
                .setSaveConsumer { config.fallPenaltyEnabled = it }
                .build()
        )
        advanced.addEntry(
            entryBuilder.startDoubleField(Component.translatable("config.vanilla-outsider-keep-gear.fallPenaltyValue"), config.fallPenaltyValue)
                .setDefaultValue(5.0)
                .setMin(0.0).setMax(100.0)
                .setSaveConsumer { config.fallPenaltyValue = it }
                .build()
        )
        advanced.addEntry(
            entryBuilder.startDoubleField(Component.translatable("config.vanilla-outsider-keep-gear.fallXpRetention"), config.fallXpRetention)
                .setDefaultValue(20.0)
                .setMin(0.0).setMax(100.0)
                .setSaveConsumer { config.fallXpRetention = it }
                .build()
        )

        // Explosion
        advanced.addEntry(entryBuilder.startTextDescription(Component.translatable("config.vanilla-outsider-keep-gear.explosionPenaltyEnabled")).build())
        advanced.addEntry(
            entryBuilder.startBooleanToggle(Component.literal("Enable Explosion Penalty"), config.explosionPenaltyEnabled)
                .setDefaultValue(false)
                .setSaveConsumer { config.explosionPenaltyEnabled = it }
                .build()
        )
        advanced.addEntry(
            entryBuilder.startDoubleField(Component.translatable("config.vanilla-outsider-keep-gear.explosionPenaltyValue"), config.explosionPenaltyValue)
                .setDefaultValue(10.0)
                .setMin(0.0).setMax(100.0)
                .setSaveConsumer { config.explosionPenaltyValue = it }
                .build()
        )
        advanced.addEntry(
            entryBuilder.startDoubleField(Component.translatable("config.vanilla-outsider-keep-gear.explosionXpRetention"), config.explosionXpRetention)
                .setDefaultValue(0.0)
                .setMin(0.0).setMax(100.0)
                .setSaveConsumer { config.explosionXpRetention = it }
                .build()
        )
        
        // Void
        advanced.addEntry(entryBuilder.startTextDescription(Component.translatable("config.vanilla-outsider-keep-gear.voidPenaltyEnabled")).build())
        advanced.addEntry(
            entryBuilder.startBooleanToggle(Component.literal("Enable Void Penalty"), config.voidPenaltyEnabled)
                .setDefaultValue(false)
                .setSaveConsumer { config.voidPenaltyEnabled = it }
                .build()
        )
        advanced.addEntry(
            entryBuilder.startDoubleField(Component.translatable("config.vanilla-outsider-keep-gear.voidPenaltyValue"), config.voidPenaltyValue)
                .setDefaultValue(100.0)
                .setMin(0.0).setMax(100.0)
                .setSaveConsumer { config.voidPenaltyValue = it }
                .build()
        )
        advanced.addEntry(
            entryBuilder.startDoubleField(Component.translatable("config.vanilla-outsider-keep-gear.voidXpRetention"), config.voidXpRetention)
                .setDefaultValue(0.0)
                .setMin(0.0).setMax(100.0)
                .setSaveConsumer { config.voidXpRetention = it }
                .build()
        )

        
        return builder.build()
    }
}
