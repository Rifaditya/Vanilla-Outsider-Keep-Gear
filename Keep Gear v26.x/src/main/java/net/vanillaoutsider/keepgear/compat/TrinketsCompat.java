// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.keepgear.compat;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.level.ServerPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reflection-safe Trinkets compatibility helper.
 * Guarantees zero ClassNotFoundException on servers without Trinkets installed.
 */
public final class TrinketsCompat {
    private static final Logger LOGGER = LoggerFactory.getLogger("vanilla-outsider-keep-gear/TrinketsCompat");
    private static boolean initialized = false;
    private static boolean trinketsLoaded = false;

    private TrinketsCompat() {}

    public static boolean isLoaded() {
        if (!initialized) {
            trinketsLoaded = FabricLoader.getInstance().isModLoaded("trinkets");
            initialized = true;
            if (trinketsLoaded) {
                LOGGER.info("Trinkets detected - activating optional Trinkets gear preservation");
            }
        }
        return trinketsLoaded;
    }
}
