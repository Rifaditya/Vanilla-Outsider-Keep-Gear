// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.keepgear.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.vanillaoutsider.keepgear.KeepGear;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public final class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File(
        FabricLoader.getInstance().getConfigDir().toFile(),
        "vanilla-outsider-keep-gear.json"
    );

    private static KeepGearConfig config = new KeepGearConfig();

    private ConfigManager() {}

    public static KeepGearConfig getConfig() {
        return config;
    }

    public static void load() {
        if (!CONFIG_FILE.exists()) {
            config = new KeepGearConfig();
            save();
            return;
        }

        try (FileReader reader = new FileReader(CONFIG_FILE, StandardCharsets.UTF_8)) {
            KeepGearConfig loaded = GSON.fromJson(reader, KeepGearConfig.class);
            if (loaded != null) {
                config = loaded;
                KeepGear.LOGGER.info("Successfully loaded Keep Gear configuration.");
            } else {
                config = new KeepGearConfig();
                save();
            }
        } catch (Exception e) {
            KeepGear.LOGGER.error("Failed to load Keep Gear config, falling back to defaults: {}", e.getMessage());
            config = new KeepGearConfig();
        }
    }

    public static void save() {
        try {
            File parent = CONFIG_FILE.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            try (FileWriter writer = new FileWriter(CONFIG_FILE, StandardCharsets.UTF_8)) {
                GSON.toJson(config, writer);
            }
        } catch (IOException e) {
            KeepGear.LOGGER.error("Failed to save Keep Gear configuration: {}", e.getMessage());
        }
    }

    public static void reload() {
        load();
    }

    public static void resetToDefaults() {
        config = new KeepGearConfig();
        save();
    }
}