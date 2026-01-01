package net.vanillaoutsider.keepgear.util

import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.ItemStack
import net.vanillaoutsider.keepgear.config.ConfigManager
import net.vanillaoutsider.keepgear.config.KeepGearConfig

/**
 * Utility functions for checking items.
 * Uses Registry ID string matching to avoid class import issues.
 */
object ItemUtils {
    
    /**
     * Check if an item has durability (can be damaged).
     * Uses the DataComponents system introduced in 1.21.
     */
    fun hasDurability(stack: ItemStack): Boolean {
        if (stack.isEmpty) return false
        val maxDamage = stack.getOrDefault(DataComponents.MAX_DAMAGE, 0)
        return maxDamage > 0
    }
    
    fun isBlacklisted(stack: ItemStack): Boolean {
        if (stack.isEmpty) return false
        val config = ConfigManager.config
        val itemId = getItemId(stack)
        
        for (entry in config.blacklist) {
            if (entry.startsWith("#")) {
                val tagId = entry.substring(1)
                if (TagHelper.matchesTag(stack, tagId)) {
                    return true
                }
            } else {
                if (entry == itemId) {
                    return true
                }
            }
        }
        return false
    }
    
    fun isWhitelisted(stack: ItemStack): Boolean {
        if (stack.isEmpty) return false
        val config = ConfigManager.config
        val itemId = getItemId(stack)
        
        for (entry in config.whitelist) {
            if (entry.startsWith("#")) {
                val tagId = entry.substring(1)
                if (TagHelper.matchesTag(stack, tagId)) {
                    return true
                }
            } else {
                if (entry == itemId) {
                    return true
                }
            }
        }
        return false
    }
    
    fun shouldKeepOnDeath(stack: ItemStack): Boolean {
        if (stack.isEmpty) return false
        
        if (isBlacklisted(stack)) return false
        if (isWhitelisted(stack)) return true
        
        val config = ConfigManager.config
        val id = getItemId(stack) // e.g. "minecraft:diamond_sword"
        val path = id.split(":").lastOrNull() ?: id

        // Category Logic (String Matching fallback)
        
        // Armor
        if (path.contains("helmet") || path.contains("chestplate") || 
            path.contains("leggings") || path.contains("boots")) {
            return config.keepArmor
        }
        
        // Shields
        if (path.contains("shield")) {
            return config.keepShields
        }
        
        // Elytra
        if (path.contains("elytra")) {
            return config.keepElytra
        }
        
        // Weapons
        if (path.contains("sword") || path.contains("bow") || 
            path.contains("trident") || path.contains("mace")) {
            return config.keepWeapons
        }
        
        // Tools
        if (path.contains("pickaxe") || path.contains("axe") || 
            path.contains("shovel") || path.contains("hoe") || 
            path.contains("shears") || path.contains("fishing_rod")) {
            return config.keepTools
        }

        // Consumables
        // Check Food component OR potion in name/id
        if (stack.has(DataComponents.FOOD) || path.contains("potion") || path.contains("bottle") || path.contains("stew") || path.contains("soup")) {
            return config.keepConsumables
        }

        // General Durability Fallback
        if (hasDurability(stack)) {
             // Treat generic durability items as Tools
             return config.keepTools 
        }

        // Resources/Misc
        return config.keepResources
    }
    
    fun applyPenalty(stack: ItemStack, penaltyPercent: Double): Boolean {
        if (stack.isEmpty || !hasDurability(stack)) return true
        
        val maxDamage = stack.getOrDefault(DataComponents.MAX_DAMAGE, 0)
        if (maxDamage <= 0) return true
        
        val currentDamage = stack.getOrDefault(DataComponents.DAMAGE, 0)
        val penaltyDamage = (maxDamage * penaltyPercent / 100.0).toInt().coerceAtLeast(1)
        val newDamage = currentDamage + penaltyDamage
        
        if (newDamage >= maxDamage) {
            return false // Item breaks
        }
        
        stack.set(DataComponents.DAMAGE, newDamage)
        return true
    }
    
    /**
     * Calculates the total durability penalty percentage for an item.
     * Includes base penalty + (total enchantment levels * weight penalty).
     */
    fun calculateTotalPenalty(stack: ItemStack, config: KeepGearConfig): Double {
        if (!config.penaltyEnabled) return 0.0
        
        var penalty = config.penaltyPercent
        
        if (config.enchantmentPenaltyEnabled) {
            val enchantments = stack.get(DataComponents.ENCHANTMENTS)
            if (enchantments != null) {
                var totalLevels = 0
                for (entry in enchantments.entrySet()) {
                    totalLevels += entry.intValue
                }
                
                if (totalLevels > 0) {
                    penalty += (totalLevels * config.enchantmentPenaltyValue)
                }
            }
        }
        
        return penalty
    }

    fun getItemId(stack: ItemStack): String {
        return BuiltInRegistries.ITEM.getKey(stack.item).toString()
    }
}
