// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.keepgear;

import net.fabricmc.api.ModInitializer;
import net.vanillaoutsider.keepgear.config.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class KeepGear implements ModInitializer {
    public static final String MOD_ID = "vanilla-outsider-keep-gear";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Vanilla Outsider: Keep Gear 26.3 initializing...");
        ConfigManager.load();
        LOGGER.info("Vanilla Outsider: Keep Gear 26.3 initialized!");
    }

    public static net.vanillaoutsider.keepgear.config.KeepGearConfig getConfig() {
        return ConfigManager.getConfig();
    }
}