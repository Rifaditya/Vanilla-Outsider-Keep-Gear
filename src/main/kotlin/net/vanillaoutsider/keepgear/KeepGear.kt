package net.vanillaoutsider.keepgear

import net.fabricmc.api.ModInitializer
import net.vanillaoutsider.keepgear.config.ConfigManager
import org.slf4j.LoggerFactory

object KeepGear : ModInitializer {
    const val MOD_ID = "vanilla-outsider-keep-gear"
    val logger = LoggerFactory.getLogger(MOD_ID)

    override fun onInitialize() {
        logger.info("Vanilla Outsider: Keep Gear initializing...")
        
        // Load config
        ConfigManager.load()
        
        // Register gamerules
        KeepGearGamerules.register()
        
        // Register commands
        KeepGearCommands.register()
        
        logger.info("Keep Gear initialized! Your gear is now protected.")
    }
}
