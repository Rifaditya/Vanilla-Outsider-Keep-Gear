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
    var keepTrinkets: Boolean = true, // Integration setting
    var useEchoShard: Boolean = true, // Echo Shard utility
    
    // Category Settings (What to keep)
    var keepArmor: Boolean = true,
    var keepWeapons: Boolean = true, // Swords, Bows, Crossbows, Tridents
    var keepTools: Boolean = true,   // Pickaxes, Axes, Shovels, Hoes, Shears, Fishing Rods
    var keepShields: Boolean = true,
    var keepElytra: Boolean = true,
    var keepConsumables: Boolean = false, // Food, Potions
    var keepResources: Boolean = false,   // Everything else (no durability)
    
    // Container Integration (Backpacks, Shulkers, Bundles)
    var keepContainers: Boolean = false,
    var containerDropContents: Boolean = false, // false = Keep All (Safe), true = Drop Contents
    var containerWhitelist: MutableList<String> = mutableListOf(
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
        "minecraft:*_bundle" // Future proofing 1.21.2+
    ),
    
    // Durability Penalty
    var penaltyEnabled: Boolean = true,
    var penaltyPercent: Double = 1.2,
    var enchantmentPenaltyEnabled: Boolean = true,
    var enchantmentPenaltyValue: Double = 0.1, // Percent per level

    // Advanced Penalties
    var dropBindingCurse: Boolean = false,
    var customDeathPenalties: MutableList<String> = mutableListOf(), // "id:durability:xp"
    
    // Cause-Specific
    var lavaPenaltyEnabled: Boolean = false,
    var lavaPenaltyValue: Double = 5.0,
    var lavaXpRetention: Double = 0.0,
    
    var fallPenaltyEnabled: Boolean = false,
    var fallPenaltyValue: Double = 5.0,
    var fallXpRetention: Double = 20.0,
    
    var explosionPenaltyEnabled: Boolean = false,
    var explosionPenaltyValue: Double = 10.0,
    var explosionXpRetention: Double = 0.0,
    
    var voidPenaltyEnabled: Boolean = false,
    var voidPenaltyValue: Double = 100.0,
    var voidXpRetention: Double = 0.0,
    
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
