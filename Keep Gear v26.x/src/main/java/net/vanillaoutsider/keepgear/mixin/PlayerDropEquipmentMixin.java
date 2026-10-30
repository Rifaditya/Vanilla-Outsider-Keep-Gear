// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.keepgear.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.vanillaoutsider.keepgear.KeepGear;
import net.vanillaoutsider.keepgear.duck.KeepGearPlayerBridge;
import net.vanillaoutsider.keepgear.engine.ItemPreservationEngine;
import net.vanillaoutsider.keepgear.engine.PreservedInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin into Player to intercept dropEquipment before vanilla drops items on death.
 */
@Mixin(Player.class)
public abstract class PlayerDropEquipmentMixin {

    @Inject(method = "dropEquipment", at = @At("HEAD"))
    private void keepgear_onDropEquipment(ServerLevel level, CallbackInfo ci) {
        Player player = (Player) (Object) this;
        if (player instanceof ServerPlayer serverPlayer) {
            PreservedInventory preserved = ItemPreservationEngine.processDeathPreservation(
                serverPlayer,
                KeepGear.getConfig(),
                serverPlayer.getLastDamageSource()
            );

            if (serverPlayer instanceof KeepGearPlayerBridge bridge) {
                bridge.setPreservedInventory(preserved);
            }
        }
    }
}
