// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.keepgear;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.vanillaoutsider.keepgear.config.KeepGearConfig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class KeepGearConfigTest {

    @Test
    void testDefaultValues() {
        KeepGearConfig config = new KeepGearConfig();

        assertTrue(config.enabled, "Mod should be enabled by default");
        assertTrue(config.keepArmor, "Armor should be kept by default");
        assertTrue(config.keepWeapons, "Weapons should be kept by default");
        assertTrue(config.keepTools, "Tools should be kept by default");
        assertTrue(config.keepShields, "Shields should be kept by default");
        assertTrue(config.keepElytra, "Elytra should be kept by default");

        assertFalse(config.keepContainers, "Containers should drop by default per agreed design");
        assertFalse(config.containerDropContents, "Container drop contents should default to false");
        assertFalse(config.keepConsumables, "Consumables should drop by default");
        assertFalse(config.keepResources, "Resources should drop by default");

        assertTrue(config.penaltyEnabled, "Penalty should be enabled by default");
        assertEquals(10.0, config.penaltyPercent, 0.001, "Penalty should default to 10%");
        assertTrue(config.useEchoShard, "Echo Shard insurance should be enabled by default");
        assertTrue(config.keepBindingCurse, "Curse of Binding should be kept by default");

        assertTrue(config.xpEnabled, "XP retention should be enabled by default");
        assertEquals(20, config.xpPercent, "XP retention should default to 20%");

        assertEquals("follow_mod", config.voidDeath);
        assertEquals("follow_mod", config.pvpDeath);

        assertNotNull(config.containerWhitelist);
        assertTrue(config.containerWhitelist.contains("minecraft:shulker_box"));
        assertTrue(config.containerWhitelist.contains("minecraft:bundle"));
    }

    @Test
    void testGsonSerializationRoundtrip() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        KeepGearConfig original = new KeepGearConfig();
        original.penaltyPercent = 15.5;
        original.keepContainers = true;
        original.blacklist.add("minecraft:wooden_sword");

        String json = gson.toJson(original);
        assertNotNull(json);

        KeepGearConfig parsed = gson.fromJson(json, KeepGearConfig.class);
        assertNotNull(parsed);
        assertEquals(15.5, parsed.penaltyPercent, 0.001);
        assertTrue(parsed.keepContainers);
        assertTrue(parsed.blacklist.contains("minecraft:wooden_sword"));
    }
}