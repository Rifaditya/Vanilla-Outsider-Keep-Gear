package net.vanillaoutsider.keepgear.config

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import net.fabricmc.loader.api.FabricLoader
import net.vanillaoutsider.keepgear.KeepGear
import java.io.File

/**
 * Configuration data class for Keep Gear mod.
 * All settings are stored in JSON format.
 */
data class KeepGearConfig(
    // General
    var enabled: Boolean = true,
    
    // Durability Penalty
    var penaltyEnabled: Boolean = true,
    var penaltyPercent: Double = 1.2,
    
    // XP Handling
    var xpEnabled: Boolean = true,
    var xpPercent: Int = 20,
    var xpDropRemaining: Boolean = true,
    
    // Special Death Types
    var voidDeath: String = "follow_mod", // "follow_mod" or "drop_all"
    var pvpDeath: String = "follow_mod",  // "follow_mod" or "drop_all"
    
    // Visual Feedback
    var showMessage: Boolean = false,
    var messageText: String = "§6Your gear has been preserved!",
    var showParticles: Boolean = false,
    var playSound: Boolean = false,
    var soundId: String = "minecraft:entity.player.levelup",
    
    // Blacklist - items WITH durability that should DROP
    var blacklist: MutableList<String> = mutableListOf(),
    
    // Whitelist - items WITHOUT durability that should be KEPT
    var whitelist: MutableList<String> = mutableListOf(),
    
    // Dimension Blacklist - dimensions where mod is DISABLED
    var dimensionBlacklist: MutableList<String> = mutableListOf()
)

object ConfigManager {
    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()
    private val configFile: File = FabricLoader.getInstance()
        .configDir
        .resolve("${KeepGear.MOD_ID}.json")
        .toFile()
    
    var config: KeepGearConfig = KeepGearConfig()
        private set
    
    fun load() {
        try {
            if (configFile.exists()) {
                val json = configFile.readText()
                config = gson.fromJson(json, KeepGearConfig::class.java) ?: KeepGearConfig()
                KeepGear.logger.info("Config loaded from ${configFile.absolutePath}")
            } else {
                save() // Create default config
                KeepGear.logger.info("Created default config at ${configFile.absolutePath}")
            }
        } catch (e: Exception) {
            KeepGear.logger.error("Failed to load config, using defaults", e)
            config = KeepGearConfig()
        }
    }
    
    fun save() {
        try {
            configFile.parentFile?.mkdirs()
            configFile.writeText(gson.toJson(config))
        } catch (e: Exception) {
            KeepGear.logger.error("Failed to save config", e)
        }
    }
    
    fun reload() {
        load()
        KeepGear.logger.info("Config reloaded")
    }
}
