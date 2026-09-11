// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.keepgear.config;

import java.util.ArrayList;
import java.util.List;

public final class KeepGearConfig {
    // Master Switch
    public boolean enabled = true;

    // Equipment Categories (What to keep)
    public boolean keepArmor = true;
    public boolean keepWeapons = true;
    public boolean keepTools = true;
    public boolean keepShields = true;
    public boolean keepElytra = true;

    // Storage / Containers (100% Toggleable)
    public boolean keepContainers = false;
    public boolean containerDropContents = false;

    // Extra Categories
    public boolean keepConsumables = false;
    public boolean keepResources = false;

    // Durability Penalty Mechanics
    public boolean penaltyEnabled = true;
    public double penaltyPercent = 10.0;
    public boolean enchantmentPenaltyEnabled = true;
    public double enchantmentPenaltyValue = 0.1;

    // Insurance & Curses
    public boolean useEchoShard = true;
    public boolean keepBindingCurse = true;

    // Experience Handling
    public boolean xpEnabled = true;
    public int xpPercent = 20;
    public boolean xpDropRemaining = true;

    // Special Death Scenarios
    public String voidDeath = "follow_mod"; // "follow_mod" or "drop_all"
    public String pvpDeath = "follow_mod";  // "follow_mod" or "drop_all"

    // Visual & Sound Feedback
    public boolean showMessage = true;
    public String messageText = "§aYour gear has been preserved!";
    public boolean playSound = true;
    public boolean showParticles = true;

    // Whitelists & Blacklists
    public List<String> blacklist = new ArrayList<>();
    public List<String> whitelist = new ArrayList<>();
    public List<String> dimensionBlacklist = new ArrayList<>();
    public List<String> containerWhitelist = new ArrayList<>(List.of(
        "minecraft:shulker_box",
        "minecraft:white_shulker_box",
        "minecraft:orange_shulker_box",
        "minecraft:magenta_shulker_box",
        "minecraft:light_blue_shulker_box",
        "minecraft:yellow_shulker_box",
        "minecraft:lime_shulker_box",
        "minecraft:pink_shulker_box",
        "minecraft:gray_shulker_box",
        "minecraft:light_gray_shulker_box",
        "minecraft:cyan_shulker_box",
        "minecraft:purple_shulker_box",
        "minecraft:blue_shulker_box",
        "minecraft:brown_shulker_box",
        "minecraft:green_shulker_box",
        "minecraft:red_shulker_box",
        "minecraft:black_shulker_box",
        "minecraft:bundle",
        "minecraft:*_bundle",
        "travelersbackpack:*",
        "backpacks:backpack",
        "backpacks:backpack_tier_*"
    ));
}