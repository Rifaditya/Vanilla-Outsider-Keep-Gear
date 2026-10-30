// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.keepgear.engine;

import java.util.Map;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

/**
 * Immutable container representing the items and experience preserved across a player's death.
 *
 * @param mainItems              Map of inventory slot index (0..35) to preserved ItemStack
 * @param equipmentItems         Map of EquipmentSlot (HEAD, CHEST, LEGS, FEET, OFFHAND, BODY) to preserved ItemStack
 * @param savedXp                The exact amount of player total experience points preserved
 * @param echoResonanceTriggered Whether Echo Shard resonance prevented durability wear during this death
 */
public record PreservedInventory(
    Map<Integer, ItemStack> mainItems,
    Map<EquipmentSlot, ItemStack> equipmentItems,
    int savedXp,
    boolean echoResonanceTriggered
) {
    public static final PreservedInventory EMPTY = new PreservedInventory(Map.of(), Map.of(), 0, false);

    public boolean isEmpty() {
        return mainItems.isEmpty() && equipmentItems.isEmpty() && savedXp == 0;
    }
}
