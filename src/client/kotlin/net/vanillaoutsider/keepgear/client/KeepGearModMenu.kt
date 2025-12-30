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
            entryBuilder.startDoubleField(Component.literal("Penalty Percent"), config.penaltyPercent)
                .setDefaultValue(1.2)
                .setMin(0.0)
                .setMax(100.0)
                .setTooltip(Component.literal("Percent of max durability lost on death"))
                .setSaveConsumer { config.penaltyPercent = it }
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
        
        return builder.build()
    }
}
