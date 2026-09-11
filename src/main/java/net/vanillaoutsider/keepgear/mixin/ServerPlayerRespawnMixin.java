// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.keepgear.mixin;

import java.util.Map;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetExperiencePacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.vanillaoutsider.keepgear.KeepGear;
import net.vanillaoutsider.keepgear.duck.KeepGearPlayerBridge;
import net.vanillaoutsider.keepgear.engine.PreservedInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin into ServerPlayer to implement KeepGearPlayerBridge and restore items after respawn.
 */
@Mixin(ServerPlayer.class)
public abstract class ServerPlayerRespawnMixin implements KeepGearPlayerBridge {


    @Unique
    private PreservedInventory keepgear$preservedInventory = PreservedInventory.EMPTY;

    @Override
    public PreservedInventory getPreservedInventory() {
        return this.keepgear$preservedInventory;
    }

    @Override
    public void setPreservedInventory(PreservedInventory preservedInventory) {
        this.keepgear$preservedInventory = preservedInventory;
    }


    @Inject(method = "restoreFrom", at = @At("TAIL"))
    private void keepgear_onRestoreFrom(ServerPlayer oldPlayer, boolean restoreAll, CallbackInfo ci) {
        if (restoreAll) { // dimension transition rather than respawn from death
            return;
        }


        ServerPlayer newPlayer = (ServerPlayer) (Object) this;

        if (!(oldPlayer instanceof KeepGearPlayerBridge oldBridge)) {
            return;
        }

        PreservedInventory preserved = oldBridge.getPreservedInventory();
        if (preserved == null || preserved.isEmpty()) {
            return;
        }


        Inventory inventory = newPlayer.getInventory();

        // 1. Restore main inventory items to their exact slots
        for (Map.Entry<Integer, ItemStack> entry : preserved.mainItems().entrySet()) {
            int slot = entry.getKey();
            ItemStack stack = entry.getValue();
            if (slot >= 0 && slot < inventory.getContainerSize()) {
                ItemStack existing = inventory.getItem(slot);
                if (existing.isEmpty()) {
                    inventory.setItem(slot, stack);
                } else {
                    if (!inventory.add(stack)) {
                        newPlayer.drop(stack, false);
                    }
                }
            } else {
                if (!inventory.add(stack)) {
                    newPlayer.drop(stack, false);
                }
            }
        }


        // 2. Restore equipment slots
        for (Map.Entry<EquipmentSlot, ItemStack> entry : preserved.equipmentItems().entrySet()) {
            EquipmentSlot slot = entry.getKey();
            ItemStack stack = entry.getValue();
            ItemStack existing = newPlayer.getItemBySlot(slot);
            if (existing.isEmpty()) {
                newPlayer.setItemSlot(slot, stack);
            } else {
                // Safe fallback per Grill-Me: if slot occupied, place in main inventory or drop at feet
                if (!inventory.add(stack)) {
                    newPlayer.drop(stack, false);
                }
            }
        }


        // 3. Restore experience & dispatch HUD sync packet
        if (preserved.savedXp() > 0) {
            newPlayer.giveExperiencePoints(preserved.savedXp());
            if (newPlayer.connection != null) {
                newPlayer.connection.send(new ClientboundSetExperiencePacket(
                    newPlayer.experienceProgress,
                    newPlayer.totalExperience,
                    newPlayer.experienceLevel
                ));
            }
        }


        // 4. Send player feedback (chat + sound que)
        newPlayer.sendSystemMessage(
            Component.translatable("message.vanilla-outsider-keep-gear.preserved").withStyle(ChatFormatting.GREEN)
        );

        if (newPlayer.connection != null) {
            if (preserved.echoResonanceTriggered()) {
                newPlayer.sendSystemMessage(
                    Component.translatable("message.vanilla-outsider-keep-gear.echo_resonance").withStyle(ChatFormatting.AQUA)
                );
                newPlayer.connection.send(new ClientboundSoundPacket(
                    SoundEvents.RESPAWN_ANCHOR_DEPLETE,
                    SoundSource.PLAYERS,
                    newPlayer.getX(), newPlayer.getY(), newPlayer.getZ(),
                    0.8F, 1.2F,
                    newPlayer.level().getRandom().nextLong()
                ));
            } else {
                newPlayer.connection.send(new ClientboundSoundPacket(
                    SoundEvents.ARMOR_EQUIP_GENERIC,
                    SoundSource.PLAYERS,
                    newPlayer.getX(), newPlayer.getY(), newPlayer.getZ(),
                    0.8F, 1.0F,
                    newPlayer.level().getRandom().nextLong()
                ));
            }
        }


        // 5. Container menu sync
        newPlayer.containerMenu.broadcastChanges();
        newPlayer.inventoryMenu.broadcastChanges();
    }
}
