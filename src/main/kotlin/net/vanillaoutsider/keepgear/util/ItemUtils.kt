package net.vanillaoutsider.keepgear.util

import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.damagesource.DamageTypes
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.component.ItemContainerContents
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.tags.ItemTags
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

    fun isContainer(stack: ItemStack): Boolean {
        if (stack.isEmpty) return false
        val config = ConfigManager.config
        val itemId = getItemId(stack)

        for (entry in config.containerWhitelist) {
            // fast path exact match
            if (entry == itemId) return true
            
            // Wildcard support (e.g. "modid:*")
            if (entry.endsWith("*")) {
                val prefix = entry.substring(0, entry.length - 1)
                if (itemId.startsWith(prefix)) return true
            }
        }
        return false
    }
    
    fun shouldKeepOnDeath(stack: ItemStack): Boolean {
        if (stack.isEmpty) return false
        
        if (isBlacklisted(stack)) return false
        if (isWhitelisted(stack)) return true
        
        val config = ConfigManager.config
        
        // Container Check (Keep Containers setting)
        if (isContainer(stack)) {
            return config.keepContainers
        }
        
        if (isContainer(stack)) {
            return config.keepContainers
        }
        
        // Curse of Binding Helper (drop if enabled)
        if (config.dropBindingCurse) {
             val enchantments = stack.get(DataComponents.ENCHANTMENTS)
             if (enchantments != null) {
                 // Check if any enchantment is Binding Curse
                 for (entry in enchantments.entrySet()) {
                     if (entry.key.`is`(Enchantments.BINDING_CURSE)) {
                         return false // Drop it to get rid of it
                     }
                 }
             }
        }
        
        val id = getItemId(stack) // e.g. "minecraft:diamond_sword"
        val path = id.split(":").lastOrNull() ?: id

        // Category Logic (String Matching fallback)
        
        // Armor
        if (stack.`is`(ItemTags.HEAD_ARMOR) || stack.`is`(ItemTags.CHEST_ARMOR) || 
            stack.`is`(ItemTags.LEG_ARMOR) || stack.`is`(ItemTags.FOOT_ARMOR) ||
            path.contains("helmet") || path.contains("chestplate") || 
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
        if (stack.`is`(ItemTags.SWORDS) || 
            path.contains("sword") || path.contains("bow") || 
            path.contains("trident") || path.contains("mace")) {
            return config.keepWeapons
        }
        
        // Tools
        if (stack.`is`(ItemTags.PICKAXES) || stack.`is`(ItemTags.AXES) || 
            stack.`is`(ItemTags.SHOVELS) || stack.`is`(ItemTags.HOES) || 
            path.contains("pickaxe") || path.contains("axe") || 
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
     * Now supports Cause-Specific Overrides (Lava, Fall, etc.)
     */
    fun calculateTotalPenalty(stack: ItemStack, config: KeepGearConfig, source: DamageSource? = null): Double {
        if (!config.penaltyEnabled) return 0.0
        
        var penalty = config.penaltyPercent
        
        // Cause-Specific Overrides
        if (source != null) {
            val msgId = source.msgId
            
            // 1. Check Custom Map "id:durability:xp"
            for (rule in config.customDeathPenalties) {
                val parts = rule.split(":")
                if (parts.isNotEmpty() && parts[0] == msgId) {
                    // Match found!
                    if (parts.size >= 2) {
                        return parts[1].toDoubleOrNull() ?: config.penaltyPercent
                    }
                }
            }
            
            // 2. Explicit Fields
            if (config.lavaPenaltyEnabled && source.`is`(DamageTypes.LAVA)) {
                penalty = config.lavaPenaltyValue
            } else if (config.fallPenaltyEnabled && source.`is`(DamageTypes.FALL)) {
                penalty = config.fallPenaltyValue
            } else if (config.explosionPenaltyEnabled && (source.`is`(DamageTypes.EXPLOSION) || source.`is`(DamageTypes.PLAYER_EXPLOSION))) {
                penalty = config.explosionPenaltyValue
            } else if (config.voidPenaltyEnabled && source.`is`(DamageTypes.FELL_OUT_OF_WORLD)) {
                penalty = config.voidPenaltyValue
            }
        }
        
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

    /**
     * Calculates the XP retention percentage based on death cause.
     * Priority: Custom Map -> Explicit Fields -> Global Config.
     */
    fun calculateXpRetention(config: KeepGearConfig, source: DamageSource?): Double {
        var xpRetention = config.xpPercent.toDouble()
        
        if (source != null) {
             val msgId = source.msgId
            
            // 1. Check Custom Map "id:durability:xp"
            for (rule in config.customDeathPenalties) {
                val parts = rule.split(":")
                if (parts.isNotEmpty() && parts[0] == msgId) {
                    // Match found!
                    if (parts.size >= 3) {
                        return parts[2].toDoubleOrNull() ?: xpRetention
                    }
                }
            }
            
            // 2. Explicit Fields
            if (config.lavaPenaltyEnabled && source.`is`(DamageTypes.LAVA)) {
                return config.lavaXpRetention
            } else if (config.fallPenaltyEnabled && source.`is`(DamageTypes.FALL)) {
                return config.fallXpRetention
            } else if (config.explosionPenaltyEnabled && (source.`is`(DamageTypes.EXPLOSION) || source.`is`(DamageTypes.PLAYER_EXPLOSION))) {
                return config.explosionXpRetention
            } else if (config.voidPenaltyEnabled && source.`is`(DamageTypes.FELL_OUT_OF_WORLD)) {
                return config.voidXpRetention
            }
        }
        
        return xpRetention
    }

    /**
     * Extracts contents from a container and clears it.
     * Returns a list of ItemStacks that were inside.
     * Supports Vanilla Bundles/Shulkers via DataComponents.CONTAINER.
     * Safe Fallback: If no components found, returns empty list (effectively Keeping All).
     */
    fun processContainerDrop(stack: ItemStack): List<ItemStack> {
        val contents = stack.get(DataComponents.CONTAINER) ?: return emptyList()
        
        // Convert to list to drop
        val drops = contents.stream().toList()
        
        // Clear the container
        stack.set(DataComponents.CONTAINER, ItemContainerContents.EMPTY)
        
        return drops
    }

    fun getItemId(stack: ItemStack): String {
        return BuiltInRegistries.ITEM.getKey(stack.item).toString()
    }
}
