// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.keepgear;

import net.vanillaoutsider.keepgear.config.KeepGearConfig;
import net.vanillaoutsider.keepgear.engine.ItemPreservationEngine;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItemPreservationEngineTest {

    @Test
    void testDurabilityWearCalculation() {
        KeepGearConfig config = new KeepGearConfig();
        config.penaltyEnabled = true;
        config.penaltyPercent = 10.0;
        config.enchantmentPenaltyEnabled = false;

        double wear = ItemPreservationEngine.calculateDurabilityWear(null, config);
        assertEquals(10.0, wear, 0.001);
    }

    @Test
    void testDurabilityWearCalculationDisabled() {
        KeepGearConfig config = new KeepGearConfig();
        config.penaltyEnabled = false;

        double wear = ItemPreservationEngine.calculateDurabilityWear(null, config);
        assertEquals(0.0, wear, 0.001);
    }
}
