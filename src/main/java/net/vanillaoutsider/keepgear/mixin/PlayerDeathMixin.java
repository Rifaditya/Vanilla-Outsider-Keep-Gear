package net.vanillaoutsider.keepgear.mixin;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.network.chat.Component;
import net.vanillaoutsider.keepgear.KeepGear;
import net.vanillaoutsider.keepgear.config.ConfigManager;
import net.vanillaoutsider.keepgear.util.DeathTypeUtils;
import net.vanillaoutsider.keepgear.util.ItemUtils;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;
import net.fabricmc.loader.api.FabricLoader;
import net.vanillaoutsider.keepgear.integration.TrinketsIntegration;

/**
 * Mixin to intercept player death and handle inventory preservation.
 * Keeps items with durability, drops items without.
 */
@Mixin(ServerPlayer.class)
public abstract class PlayerDeathMixin {

    /**
     * Map of slot index -> ItemStack to preserve slot positions.
     * This ensures armor stays in armor slots, offhand stays in offhand, etc.
     */
    @Unique
    private Map<Integer, ItemStack> keepgear$savedItems = new HashMap<>();

    @Unique
    private int keepgear$savedXp = 0;

    /**
     * Before player dies, save items that should be kept WITH their slot positions.
     */
    @Inject(method = "die", at = @At("HEAD"))
    private void keepgear$onDeathStart(DamageSource source, CallbackInfo ci) {
        ServerPlayer player = (ServerPlayer) (Object) this;
        keepgear$savedItems.clear();
        keepgear$savedXp = 0;

        // Check if mod should handle this death
        if (!DeathTypeUtils.INSTANCE.shouldModHandleDeath(player, source)) {
            return;
        }

        var config = ConfigManager.INSTANCE.getConfig();
        Inventory inventory = player.getInventory();
        boolean isPenaltyNegated = false;

        // --- ECHO SHARD LOGIC ---
        // If player has Echo Shard, consume it to negate penalty
        if (config.getUseEchoShard()) {
            for (int i = 0; i < inventory.getContainerSize(); i++) {
                ItemStack stack = inventory.getItem(i);
                if (!stack.isEmpty() && stack.is(Items.ECHO_SHARD)) {
                    stack.shrink(1);
                    isPenaltyNegated = true;
                    KeepGear.INSTANCE.getLogger().info(
                            "Echo Shard resonance detected! Preserving gear durability for {}",
                            player.getName().getString());
                    break;
                }
            }
        }
        // ------------------------

        // Process all inventory slots (including armor and offhand)
        // Slot layout: 0-8 hotbar, 9-35 main inv, 36-39 armor, 40 offhand
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (stack.isEmpty())
                continue;

            if (ItemUtils.INSTANCE.shouldKeepOnDeath(stack)) {
                // Check Curse of Vanishing
                if (hasVanishingCurse(stack)) {
                    continue; // Let it vanish
                }

                // Apply durability penalty (Dynamic per item)
                double itemPenalty = 0.0;
                if (!isPenaltyNegated) {
                    itemPenalty = ItemUtils.INSTANCE.calculateTotalPenalty(stack, config);
                }

                if (itemPenalty > 0 && ItemUtils.INSTANCE.hasDurability(stack)) {
                    boolean survived = ItemUtils.INSTANCE.applyPenalty(stack, itemPenalty);
                    if (!survived) {
                        continue; // Item broke from penalty
                    }
                }

                // Save item WITH its slot index to restore to same position
                keepgear$savedItems.put(i, stack.copy());
                inventory.setItem(i, ItemStack.EMPTY);
            }
        }

        // Handle XP retention
        if (config.getXpEnabled()) {
            int totalXp = player.totalExperience;
            int keepPercent = config.getXpPercent();
            keepgear$savedXp = (int) (totalXp * keepPercent / 100.0);
        }

        KeepGear.INSTANCE.getLogger().debug("Saved {} items and {} XP for {}",
                keepgear$savedItems.size(), keepgear$savedXp, player.getName().getString());

        // Handle Trinkets integration
        // If penalty is negated, pass 0.0. If not, pass null to let integration
        // calculate dynamic penalty.
        if (FabricLoader.getInstance().isModLoaded("trinkets")) {
            TrinketsIntegration.INSTANCE.onDeath(player, isPenaltyNegated ? 0.0 : null);
        }
    }

    /**
     * After player respawns, restore saved items to their ORIGINAL slots.
     */
    @Inject(method = "restoreFrom", at = @At("TAIL"))
    private void keepgear$onRespawn(ServerPlayer oldPlayer, boolean alive, CallbackInfo ci) {
        ServerPlayer player = (ServerPlayer) (Object) this;

        // Transfer saved data from old player instance
        PlayerDeathMixin oldMixin = (PlayerDeathMixin) (Object) oldPlayer;
        Map<Integer, ItemStack> savedItems = oldMixin.keepgear$savedItems;
        int savedXp = oldMixin.keepgear$savedXp;

        if (savedItems.isEmpty() && savedXp == 0) {
            return;
        }

        var config = ConfigManager.INSTANCE.getConfig();

        // Restore items to their ORIGINAL slots
        Inventory inventory = player.getInventory();
        for (Map.Entry<Integer, ItemStack> entry : savedItems.entrySet()) {
            int slotIndex = entry.getKey();
            ItemStack stack = entry.getValue();

            // Put item back in its original slot
            // This preserves armor in armor slots (36-39), offhand in slot 40, etc.
            inventory.setItem(slotIndex, stack);
        }

        // Restore XP
        if (savedXp > 0) {
            player.giveExperiencePoints(savedXp);
        }

        // Show message if enabled
        if (config.getShowMessage()) {
            player.displayClientMessage(Component.literal(config.getMessageText()), true);
        }

        // Sound feedback
        if (config.getPlaySound()) {
            try {
                player.playSound(net.minecraft.sounds.SoundEvents.PLAYER_LEVELUP, 1.0f, 1.0f);
            } catch (Exception e) {
                KeepGear.INSTANCE.getLogger().debug("Could not play sound");
            }
        }

        KeepGear.INSTANCE.getLogger().debug("Restored {} items and {} XP for {}",
                savedItems.size(), savedXp, player.getName().getString());

        // Handle Trinkets integration
        if (FabricLoader.getInstance().isModLoaded("trinkets")) {
            TrinketsIntegration.INSTANCE.onRespawn(player);
        }

        // Clear saved data
        savedItems.clear();
    }

    @Unique
    private boolean hasVanishingCurse(ItemStack stack) {
        return stack.has(DataComponents.ENCHANTMENTS) &&
                stack.getEnchantments().keySet().stream()
                        .anyMatch(holder -> holder.is(Enchantments.VANISHING_CURSE));
    }
}
