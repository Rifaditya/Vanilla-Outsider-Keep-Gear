package net.vanillaoutsider.keepgear.util

import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.damagesource.DamageTypes
import net.minecraft.world.level.Level
import net.vanillaoutsider.keepgear.config.ConfigManager

/**
 * Utility functions for detecting death types and dimension checks.
 */
object DeathTypeUtils {
    
    /**
     * Check if death was caused by falling into the void.
     */
    fun isVoidDeath(source: DamageSource): Boolean {
        return source.`is`(DamageTypes.FELL_OUT_OF_WORLD)
    }
    
    /**
     * Check if death was caused by another player (PvP).
     */
    fun isPvpDeath(source: DamageSource): Boolean {
        return source.entity is ServerPlayer
    }
    
    /**
     * Check if mod is disabled in the current dimension.
     */
    fun isDimensionBlacklisted(level: Level): Boolean {
        // Get dimension id as string using the ResourceKey's toString
        val dimKey = level.dimension()
        val dimId = dimKey.toString()
        return ConfigManager.config.dimensionBlacklist.any { 
            dimId.contains(it) 
        }
    }
    
    /**
     * Get the dimension ID as a string.
     */
    fun getDimensionId(level: Level): String {
        return level.dimension().toString()
    }
    
    /**
     * Check if the world is in hardcore mode.
     */
    fun isHardcore(level: Level): Boolean {
        return level.levelData.isHardcore
    }
    
    /**
     * Determine if the mod should be active for this death.
     * @return true if mod should handle death, false if vanilla behavior
     */
    fun shouldModHandleDeath(player: ServerPlayer, source: DamageSource): Boolean {
        val config = ConfigManager.config
        val level = player.level()
        
        // Master toggle
        if (!config.enabled) return false
        
        // Hardcore mode - always use vanilla (drop all)
        if (isHardcore(level)) return false
        
        // Dimension blacklist
        if (isDimensionBlacklisted(level)) return false
        
        // Check special death types
        if (isVoidDeath(source) && config.voidDeath == "drop_all") return false
        if (isPvpDeath(source) && config.pvpDeath == "drop_all") return false
        
        return true
    }
}
