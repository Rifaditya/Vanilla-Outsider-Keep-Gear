package net.vanillaoutsider.keepgear.util

import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.ItemStack
import net.vanillaoutsider.keepgear.config.ConfigManager

/**
 * Utility functions for checking items.
 */
object ItemUtils {
    
    /**
     * Check if an item has durability (can be damaged).
     * Uses the DataComponents system introduced in 1.21.
     */
    fun hasDurability(stack: ItemStack): Boolean {
        if (stack.isEmpty) return false
        // Check if item has max damage component and it's > 0
        val maxDamage = stack.getOrDefault(DataComponents.MAX_DAMAGE, 0)
        return maxDamage > 0
    }
    
    /**
     * Check if an item is in the blacklist (should DROP even with durability).
     */
    fun isBlacklisted(stack: ItemStack): Boolean {
        if (stack.isEmpty) return false
        val config = ConfigManager.config
        val itemId = getItemId(stack)
        
        for (entry in config.blacklist) {
            // Check tag matches (tags start with #)
            if (entry.startsWith("#")) {
                val tagId = entry.substring(1)
                if (TagHelper.matchesTag(stack, tagId)) {
                    return true
                }
            } else {
                // Check direct item ID match
                if (entry == itemId) {
                    return true
                }
            }
        }
        
        return false
    }
    
    /**
     * Check if an item is in the whitelist (should be KEPT even without durability).
     */
    fun isWhitelisted(stack: ItemStack): Boolean {
        if (stack.isEmpty) return false
        val config = ConfigManager.config
        val itemId = getItemId(stack)
        
        for (entry in config.whitelist) {
            // Check tag matches (tags start with #)
            if (entry.startsWith("#")) {
                val tagId = entry.substring(1)
                if (TagHelper.matchesTag(stack, tagId)) {
                    return true
                }
            } else {
                // Check direct item ID match
                if (entry == itemId) {
                    return true
                }
            }
        }
        
        return false
    }
    
    /**
     * Determine if an item should be kept on death.
     * Priority: Blacklist > Whitelist > Durability check
     */
    fun shouldKeepOnDeath(stack: ItemStack): Boolean {
        if (stack.isEmpty) return false
        
        // Blacklist overrides everything - DROP the item
        if (isBlacklisted(stack)) return false
        
        // Whitelist keeps items without durability
        if (isWhitelisted(stack)) return true
        
        // Default: keep if has durability
        return hasDurability(stack)
    }
    
    /**
     * Apply durability penalty to an item.
     * @return true if item is still usable, false if it broke
     */
    fun applyPenalty(stack: ItemStack, penaltyPercent: Double): Boolean {
        if (stack.isEmpty || !hasDurability(stack)) return true
        
        val maxDamage = stack.getOrDefault(DataComponents.MAX_DAMAGE, 0)
        if (maxDamage <= 0) return true
        
        val currentDamage = stack.getOrDefault(DataComponents.DAMAGE, 0)
        val penaltyDamage = (maxDamage * penaltyPercent / 100.0).toInt().coerceAtLeast(1)
        val newDamage = currentDamage + penaltyDamage
        
        // Check if item would break
        if (newDamage >= maxDamage) {
            return false // Item breaks
        }
        
        // Apply damage
        stack.set(DataComponents.DAMAGE, newDamage)
        return true
    }
    
    /**
     * Get the registry ID of an item (e.g., "minecraft:diamond_sword").
     */
    fun getItemId(stack: ItemStack): String {
        return BuiltInRegistries.ITEM.getKey(stack.item).toString()
    }
}
