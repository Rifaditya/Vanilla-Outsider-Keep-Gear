// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.keepgear;

import net.dasik.social.api.SocialLinks;
import net.dasik.social.api.config.DasikSupportHelper;
import net.fabricmc.api.ModInitializer;
import net.vanillaoutsider.keepgear.command.KeepGearCommands;
import net.vanillaoutsider.keepgear.compat.TrinketsCompat;
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

        // Check optional Trinkets compatibility
        TrinketsCompat.isLoaded();

        // Register Brigadier command suite via Universal Command Framework
        KeepGearCommands.init();

        LOGGER.info("Keep Gear initialized! Community: {}, Support: {}", SocialLinks.DISCORD_INVITE_URL, DasikSupportHelper.KOFI_URL);
    }

    public static net.vanillaoutsider.keepgear.config.KeepGearConfig getConfig() {
        return ConfigManager.getConfig();
    }
}