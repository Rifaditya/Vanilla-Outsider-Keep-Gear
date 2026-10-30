// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.keepgear.duck;

import net.vanillaoutsider.keepgear.engine.PreservedInventory;

public interface KeepGearPlayerBridge {
    PreservedInventory getPreservedInventory();
    void setPreservedInventory(PreservedInventory preservedInventory);
}
