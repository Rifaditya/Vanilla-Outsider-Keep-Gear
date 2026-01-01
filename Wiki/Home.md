# Welcome to the Vanilla Outsider: Keep Gear Wiki

**Vanilla Outsider: Keep Gear** is a next-generation death penalty mod for Fabric/Quilt (Minecraft 1.21+). It strikes a precise balance between the punishing nature of Vanilla Minecraft and the forgiveness of `keepInventory`.

Instead of losing everything or keeping everything, **Keep Gear** allows you to keep your essential equipment (Armor, Weapons, Tools) while applying durability penalties, encouraging you to maintain your gear without fear of losing your "God Roll" items to a glitch or a lava pit.

## 🌟 Key Features

### 🛡️ Smart Preservation

* **Automatic Detection:** Automatically detects and saves Helmets, Chestplates, Leggings, Boots, Swords, Tools, and Shields.
* **Mod Compatibility:** Works out-of-the-box with items from other mods (e.g., modded armors, hammers, excavators) via keyword matching and whitelist support.
* **Curse Compatibility:** Respects *Curse of Vanishing* (items vanish) and offers a new option to finally drop *Curse of Binding* items!

### 🎒 Container Support

* **Backpack Friendly:** Integrated support for **Traveler's Backpacks**, **Improved Backpacks**, **Shulker Boxes**, and **Bundles**.
* **Flexible Modes:**
  * **Safe Mode (Default):** Keep the container AND its contents secured.
  * **Spill Mode:** Keep the empty container object (so you don't lose the bag itself), but spill the contents on the ground (Vanilla-like punishment).

### ⚡ Balanced Penalties

* **Durability Loss:** Items kept take a configurable durability hit (Default: 12%).
* **Enchantment Weight:** Highly enchanted items are "heavier" on the soul. Each enchantment level adds a small extra penalty (configurable, default 0.1% per level).
* **Echo Shard Recovery:** If you die with an **Echo Shard** in your inventory, it is consumed to **completely negate** the durability penalty (like a Totem of Undying for your gear!).

### ☠️ Granular Death Rules (New in v1.1!)

* **Cause-Specific Penalties:** Configure different punishments for Lava, Void, Explosions, Fall Damage, etc.
* **XP Retention:** Decide exactly how much XP you keep for each death type (e.g., Lava burns 100% XP, but Starvation keeps 100%).
* **Custom Rules Engine:** Create your own rules for ANY death source ID (e.g., `drown`, `wither`, `magic`).

## 📥 Installation

### Requirements

* **Minecraft Java Edition:** 1.21 or higher.
* **Fabric Loader:** Latest version recommended.
* **Fabric API:** Required.

### Recommended Mods

* **Mod Menu:** For in-game configuration UI.
* **Cloth Config API:** Required for the config screen.

### Client vs Server

* **Singleplayer:** Install on Client.
* **Multiplayer:** Install on **BOTH** Client and Server. (Server handles logic, Client handles Mod Menu config syncing).

## 🚀 Getting Started

1. Install the mod.
2. Launch Minecraft.
3. Die (optional, but recommended for testing).
4. Notice your tools are still there, but slightly damaged!
5. Check [[Configuration]] to tweak the numbers.
