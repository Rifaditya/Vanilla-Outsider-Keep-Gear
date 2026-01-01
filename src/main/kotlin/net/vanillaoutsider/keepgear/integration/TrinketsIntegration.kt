package net.vanillaoutsider.keepgear.integration

import dev.emi.trinkets.api.TrinketsApi
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.ItemStack
import net.vanillaoutsider.keepgear.KeepGear
import net.vanillaoutsider.keepgear.config.ConfigManager
import net.vanillaoutsider.keepgear.util.ItemUtils
import java.util.HashMap

/**
 * Handles Trinkets mod integration.
 * Methods in this class should ONLY be called if Trinkets is loaded.
 */
object TrinketsIntegration {
    
    // Map to store saved trinkets: SlotIdentifier -> ItemStack
    // Using a String identifier for simplicity: "group:slot:index"
    private val savedTrinkets = HashMap<String, ItemStack>()

    /**
     * Called when player dies.
     * Scans trinket slots, keeps items with durability (or whitelisted),
     * and clears them from inventory so Trinkets doesn't drop them.
     */
    fun onDeath(player: ServerPlayer, overridePenalty: Double? = null) {
        savedTrinkets.clear()
        
        // Check if integration is enabled in config
        if (!ConfigManager.config.keepTrinkets) return

        val trinketComponent = TrinketsApi.getTrinketComponent(player)
        if (trinketComponent.isEmpty) return
        
        val component = trinketComponent.get()
        val config = ConfigManager.config
        
        component.inventory.forEach { (groupId, groupMap) ->
            groupMap.forEach { (slotId, inventory) ->
                for (i in 0 until inventory.containerSize) {
                    val stack = inventory.getItem(i)
                    if (stack.isEmpty) continue
                 // Check if we should keep this trinket
                if (ItemUtils.shouldKeepOnDeath(stack)) {
                    // Check Curse of Vanishing (not explicitly handled in Mixin but good to have, though vanilla might handle it separately for trinkets? 
                    // Trinkets API usually handles drops, but we are intercepting. 
                    // Let's assume shouldKeepOnDeath handles the decision logic, but applyPenalty modifies it.
                    
                    // Apply Penalty
                    if (ItemUtils.hasDurability(stack)) {
                        val itemPenalty = overridePenalty ?: ItemUtils.calculateTotalPenalty(stack, config)
                        if (itemPenalty > 0) {
                            val survived = ItemUtils.applyPenalty(stack, itemPenalty)
                            if (!survived) {
                                // Item broke, don't save it
                                continue // Item broke, continue to next item
                            }
                        }
                    }

                    // Save logic: Store a copy
                        val key = "$groupId:$slotId:$i"
                        savedTrinkets[key] = stack.copy()
                        
                        // Clear from inventory so Trinkets doesn't drop it
                        inventory.setItem(i, ItemStack.EMPTY)
                    }
                }
            }
        }
        
        if (savedTrinkets.isNotEmpty()) {
            KeepGear.logger.debug("Saved ${savedTrinkets.size} trinkets for ${player.name.string}")
        }
    }

    /**
     * Called when player respawns.
     * Restores saved trinkets to their original slots.
     */
    fun onRespawn(player: ServerPlayer) {
        if (savedTrinkets.isEmpty()) return
        
        val trinketComponent = TrinketsApi.getTrinketComponent(player)
        if (trinketComponent.isEmpty) return
        
        val component = trinketComponent.get()
        
        savedTrinkets.forEach { (key, stack) ->
            val parts = key.split(":")
            if (parts.size == 3) {
                val groupId = parts[0]
                val slotId = parts[1]
                val index = parts[2].toIntOrNull() ?: 0
                
                // Try to put it back exactly where it was
                val group = component.inventory[groupId]
                val slot = group?.get(slotId)
                
                if (slot != null && index < slot.containerSize) {
                    // Check if slot is empty (should be, unless starter items)
                    if (slot.getItem(index).isEmpty) {
                        slot.setItem(index, stack)
                    } else {
                        // If slot occupied, give to player inventory or drop
                        if (!player.inventory.add(stack)) {
                            player.drop(stack, false)
                        }
                    }
                } else {
                    // Slot doesn't exist anymore? Give to player
                    if (!player.inventory.add(stack)) {
                        player.drop(stack, false)
                    }
                }
            }
        }
        
        savedTrinkets.clear()
    }
}
