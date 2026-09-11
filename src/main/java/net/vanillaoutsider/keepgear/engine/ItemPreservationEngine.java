// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.keepgear.engine;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.equipment.Equippable;
import net.vanillaoutsider.keepgear.config.KeepGearConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Algorithmic preservation engine responsible for evaluating item retention eligibility,
 * calculating durability degradation, handling curses, and checking Echo Shard insurance.
 */
public final class ItemPreservationEngine {
    private static final Logger LOGGER = LoggerFactory.getLogger("vanilla-outsider-keep-gear/Engine");

    private ItemPreservationEngine() {}

    /**
     * Preserves items and experience from a dying player according to the active configuration.
     * Items meeting preservation criteria are removed from the player's inventory so that
     * vanilla's dropEquipment() will not drop them as world entities.
     *
     * @param player dying server player
     * @param config active configuration
     * @param damageSource the cause of death
     * @return an immutable PreservedInventory record containing the saved stacks and XP
     */
    public static PreservedInventory processDeathPreservation(
        ServerPlayer player,
        KeepGearConfig config,
        DamageSource damageSource
    ) {
        if (!shouldPreserveOnDeath(player, config, damageSource)) {
            return PreservedInventory.EMPTY;
        }

        boolean echoResonance = checkAndConsumeEchoShard(player, config);
        Map<Integer, ItemStack> preservedMain = new HashMap<>();
        Map<EquipmentSlot, ItemStack> preservedEquipment = new EnumMap<>(EquipmentSlot.class);

        Inventory inventory = player.getInventory();

        // 1. Process 36 main inventory slots (0..35)
        for (int i = 0; i < 36; i++) {
            ItemStack stack = inventory.getItem(i);
            if (stack.isEmpty()) continue;

            if (shouldKeepItem(stack, config)) {
                ItemStack preservedStack = processPreservedStack(stack, config, echoResonance);
                if (!preservedStack.isEmpty()) {
                    preservedMain.put(i, preservedStack);
                }
                inventory.removeItemNoUpdate(i);
            }
        }

        // 2. Process equipment slots (HEAD, CHEST, LEGS, FEET, OFFHAND)
        EquipmentSlot[] equipmentSlots = {
            EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET, EquipmentSlot.OFFHAND
        };

        for (EquipmentSlot slot : equipmentSlots) {
            ItemStack stack = player.getItemBySlot(slot);
            if (stack.isEmpty()) continue;

            if (shouldKeepItem(stack, config)) {
                ItemStack preservedStack = processPreservedStack(stack, config, echoResonance);
                if (!preservedStack.isEmpty()) {
                    preservedEquipment.put(slot, preservedStack);
                }
                player.setItemSlot(slot, ItemStack.EMPTY);
            }
        }

        // 3. Process experience points
        int preservedXp = 0;
        if (config.xpEnabled && config.xpPercent > 0.0) {
            int totalXp = player.totalExperience;
            preservedXp = (int) Math.round(totalXp * (Math.clamp(config.xpPercent, 0.0, 100.0) / 100.0));
            if (!config.xpDropRemaining) {
                // Clear experience so none drops
                player.experienceLevel = 0;
                player.totalExperience = 0;
                player.experienceProgress = 0.0F;
            } else {
                // Deduct preserved XP so only remaining drops
                player.totalExperience = Math.max(0, totalXp - preservedXp);
            }
        }

        LOGGER.debug(
            "Player {} death processed: preserved {} main items, {} equipment items, {} XP (Echo Resonance: {})",
            player.getScoreboardName(),
            preservedMain.size(),
            preservedEquipment.size(),
            preservedXp,
            echoResonance
        );

        return new PreservedInventory(
            Map.copyOf(preservedMain),
            Map.copyOf(preservedEquipment),
            preservedXp,
            echoResonance
        );
    }

    /**
     * Determines whether the death event is eligible for gear preservation.
     */
    public static boolean shouldPreserveOnDeath(Player player, KeepGearConfig config, DamageSource damageSource) {
        if (!config.enabled) return false;

        // Check dimension blacklist
        String dimensionId = player.level().dimension().identifier().toString();
        if (config.dimensionBlacklist != null && config.dimensionBlacklist.contains(dimensionId)) {
            return false;
        }

        // Check void death behavior
        if (damageSource != null && damageSource.is(DamageTypes.FELL_OUT_OF_WORLD)) {
            if ("vanilla".equalsIgnoreCase(config.voidDeath)) return false;
            if ("keep_all".equalsIgnoreCase(config.voidDeath)) return true;
        }

        // Check PvP death behavior
        if (damageSource != null && damageSource.getEntity() instanceof Player && damageSource.getEntity() != player) {
            if ("drop_all".equalsIgnoreCase(config.pvpDeath)) return false;
            if ("keep_all".equalsIgnoreCase(config.pvpDeath)) return true;
        }

        return true;
    }

    /**
     * Evaluates whether a single ItemStack qualifies for preservation.
     */
    public static boolean shouldKeepItem(ItemStack stack, KeepGearConfig config) {
        if (stack.isEmpty()) return false;

        // 1. Vanishing curse check (EnchantmentEffectComponents.PREVENT_EQUIPMENT_DROP)
        // If an item has vanishing curse, it is destroyed and should never be kept.
        if (hasVanishingCurse(stack)) {
            return false;
        }

        // 2. Binding curse check
        if (hasBindingCurse(stack)) {
            return config.keepBindingCurse;
        }

        // 3. Explicit identifier Whitelist / Blacklist
        Identifier itemId = BuiltInRegistries.ITEM.getKey(stack.getItem());
        String itemStr = itemId.toString();

        if (config.whitelist != null && config.whitelist.contains(itemStr)) {
            return true;
        }
        if (config.blacklist != null && config.blacklist.contains(itemStr)) {
            return false;
        }

        // 4. Containers (Shulker boxes, bundles, backpacks)
        if (isContainerItem(stack, config)) {
            return config.keepContainers;
        }

        // 5. Consumables (Food, potions, etc.)
        if (stack.has(DataComponents.CONSUMABLE) || stack.has(DataComponents.FOOD)) {
            return config.keepConsumables;
        }

        // 6. Elytra
        if (isElytra(stack)) {
            return config.keepElytra;
        }

        // 7. Shield
        if (isShield(stack)) {
            return config.keepShields;
        }

        // 8. Armor
        if (isArmor(stack)) {
            return config.keepArmor;
        }

        // 9. Weapons (Swords, bows, crossbows, tridents, maces)
        if (isWeapon(stack)) {
            return config.keepWeapons;
        }

        // 10. Tools (Pickaxes, axes, shovels, hoes, shears, fishing rods)
        if (isTool(stack)) {
            return config.keepTools;
        }

        // 11. Generic items with durability
        if (stack.isDamageableItem()) {
            return config.keepTools;
        }

        // 12. Non-durable resources (Iron ingots, dirt, cobblestone, etc.)
        return config.keepResources;
    }

    /**
     * Applies durability degradation wear on an item if applicable, destroying broken items.
     *
     * @return the surviving stack, or ItemStack.EMPTY if broken and destroyed
     */
    public static ItemStack processPreservedStack(ItemStack stack, KeepGearConfig config, boolean echoResonance) {
        ItemStack copy = stack.copy();
        if (echoResonance || !config.penaltyEnabled || !copy.isDamageableItem()) {
            return copy;
        }

        double wearPercent = calculateDurabilityWear(copy, config);
        if (wearPercent <= 0.0) {
            return copy;
        }

        int maxDamage = copy.getMaxDamage();
        int damageToAdd = (int) Math.round((wearPercent / 100.0) * maxDamage);
        damageToAdd = Math.max(1, damageToAdd);

        int currentDamage = copy.getDamageValue();
        int newDamage = currentDamage + damageToAdd;

        // If wear breaks the item, it is permanently destroyed
        if (newDamage >= maxDamage) {
            return ItemStack.EMPTY;
        }

        copy.setDamageValue(newDamage);
        return copy;
    }

    /**
     * Calculates the total durability wear percentage, adding enchantment penalties if configured.
     */
    public static double calculateDurabilityWear(ItemStack stack, KeepGearConfig config) {
        if (!config.penaltyEnabled) return 0.0;

        double wear = Math.max(0.0, config.penaltyPercent);

        if (config.enchantmentPenaltyEnabled && config.enchantmentPenaltyValue > 0.0) {
            ItemEnchantments enchantments = stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
            int totalLevels = 0;
            for (var entry : enchantments.entrySet()) {
                totalLevels += entry.getIntValue();
            }
            wear += totalLevels * config.enchantmentPenaltyValue;
        }

        return wear;
    }

    /**
     * Checks if player has an Echo Shard and consumes 1 if insurance is enabled.
     */
    public static boolean checkAndConsumeEchoShard(Player player, KeepGearConfig config) {
        if (!config.useEchoShard) return false;

        Inventory inventory = player.getInventory();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty() && stack.is(Items.ECHO_SHARD)) {
                stack.shrink(1);
                if (stack.isEmpty()) {
                    inventory.removeItemNoUpdate(i);
                }
                return true;
            }
        }
        return false;
    }

    public static boolean hasVanishingCurse(ItemStack stack) {
        return !stack.isEmpty() && EnchantmentHelper.has(stack, EnchantmentEffectComponents.PREVENT_EQUIPMENT_DROP);
    }

    public static boolean hasBindingCurse(ItemStack stack) {
        return !stack.isEmpty() && EnchantmentHelper.has(stack, EnchantmentEffectComponents.PREVENT_ARMOR_CHANGE);
    }

    public static boolean isContainerItem(ItemStack stack, KeepGearConfig config) {
        if (stack.has(DataComponents.CONTAINER) || stack.has(DataComponents.BUNDLE_CONTENTS)) {
            return true;
        }
        Identifier id = BuiltInRegistries.ITEM.getKey(stack.getItem());
        if (config.containerWhitelist != null && config.containerWhitelist.contains(id.toString())) {
            return true;
        }
        return stack.is(ItemTags.BUNDLES);
    }

    public static boolean isArmor(ItemStack stack) {
        if (stack.is(ItemTags.HEAD_ARMOR) || stack.is(ItemTags.CHEST_ARMOR) ||
            stack.is(ItemTags.LEG_ARMOR) || stack.is(ItemTags.FOOT_ARMOR)) {
            return true;
        }
        Equippable equippable = stack.get(DataComponents.EQUIPPABLE);
        if (equippable != null) {
            EquipmentSlot slot = equippable.slot();
            return slot == EquipmentSlot.HEAD || slot == EquipmentSlot.CHEST ||
                   slot == EquipmentSlot.LEGS || slot == EquipmentSlot.FEET ||
                   slot == EquipmentSlot.BODY;
        }
        return false;
    }

    public static boolean isElytra(ItemStack stack) {
        if (stack.is(Items.ELYTRA)) return true;
        Equippable equippable = stack.get(DataComponents.EQUIPPABLE);
        return equippable != null && equippable.slot() == EquipmentSlot.CHEST && stack.is(ItemTags.DURABILITY_ENCHANTABLE) && !isArmor(stack);
    }

    public static boolean isShield(ItemStack stack) {
        if (stack.is(Items.SHIELD)) return true;
        return stack.has(DataComponents.BLOCKS_ATTACKS);
    }

    public static boolean isWeapon(ItemStack stack) {
        if (stack.is(ItemTags.SWORDS) || stack.is(ItemTags.BOW_ENCHANTABLE) ||
            stack.is(ItemTags.CROSSBOW_ENCHANTABLE) || stack.is(ItemTags.TRIDENT_ENCHANTABLE) ||
            stack.is(ItemTags.MACE_ENCHANTABLE) || stack.has(DataComponents.WEAPON)) {
            return true;
        }
        return stack.is(Items.BOW) || stack.is(Items.CROSSBOW) || stack.is(Items.TRIDENT) || stack.is(Items.MACE);
    }

    public static boolean isTool(ItemStack stack) {
        return stack.is(ItemTags.PICKAXES) || stack.is(ItemTags.AXES) ||
               stack.is(ItemTags.SHOVELS) || stack.is(ItemTags.HOES) ||
               stack.is(Items.SHEARS) || stack.is(Items.FISHING_ROD) ||
               stack.is(Items.FLINT_AND_STEEL) || stack.is(Items.BRUSH) ||
               stack.is(Items.SPYGLASS);
    }
}
