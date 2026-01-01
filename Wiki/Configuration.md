# Configuration Guide

**Vanilla Outsider: Keep Gear** is highly configurable. You can adjust almost every aspect of the mod to fit your modpack or playstyle.

## 🛠️ Accessing Config

* **In-Game (Recommended):** Install **Mod Menu** and **Cloth Config**. Click `Mods` -> `Vanilla Outsider: Keep Gear` -> `Config`.
* **File:** Edit `config/vanilla-outsider-keep-gear.json5` in your instance folder.

---

## 📋 General Settings

| Setting | Type | Default | Description |
| :--- | :--- | :--- | :--- |
| **Enabled** | Boolean | `true` | Master switch for the mod. Set to `false` to disable all functionality. |

---

## 🎒 Item Categories

Decide exactly *what* counts as "Gear".

| Setting | Default | Description |
| :--- | :--- | :--- |
| **Keep Armor** | `true` | Helmets, Chestplates, Leggings, Boots. |
| **Keep Weapons** | `true` | Swords, Bows, Crossbows, Tridents, Maces. |
| **Keep Tools** | `true` | Pickaxes, Axes, Shovels, Hoes, Shears, Fishing Rods. |
| **Keep Shields** | `true` | Shields. |
| **Keep Elytra** | `true` | Elytra (and chestplate equivalents). |
| **Keep Consumables**| `false`| Food, Potions, Stews. (Items with `food` component or "potion", "stew" in name). |
| **Keep Resources** | `false`| **Everything else** that doesn't have durability (Blocks, Diamonds, etc). **Warning:** Setting this to `true` essentially enables full `keepInventory`. |

---

## 📦 Container Management

How the mod handles Backpacks, Shulker Boxes, and Bundles.

| Setting | Default | Description |
| :--- | :--- | :--- |
| **Keep Containers** | `false` | Enable/Disable special handling for container items. |
| **Drop Contents** | `false` | **OFF (Safe Mode):** You keep the bag AND all items inside it. <br> **ON (Spill Mode):** You keep the *bag item* itself, but the *contents* are spilled onto the ground at your death location. Useful for balance! |
| **Whitelist** | `List` | List of Resource IDs (e.g., `minecraft:shulker_box`) to treat as containers. Supports wildcards `modid:*`. |

**Default Whitelist:**

* `minecraft:shulker_box` (all colors)
* `minecraft:bundle` (all colors)
* `travelersbackpack:*`
* `backpacks:backpack*`

---

## 💔 Durability Penalties

The cost of cheating death.

| Setting | Default | Description |
| :--- | :--- | :--- |
| **Penalty Enabled** | `true` | If OFF, items are kept with 0 damage (Free). |
| **Penalty Percent** | `1.2%` | Percentage of **Max Durability** removed from the item. <br> *Example:* A Netherite Pic (2031 durability) loses ~24 durability per death. |
| **Enchantment Penalty** | `true` | Toggle the "Enchantment Weight" mechanic. |
| **Weight per Level** | `0.1%` | Additional penalty % per level of enchantment. <br> *Example:* A Sword with Sharpness V, Unbreaking III (Level 8) adds `0.8%` to the base penalty. |
| **Use Echo Shard** | `true` | If you have an `echo_shard` in your inventory, it is **consumed** on death and your items take **0** damage. |

### 🧮 Penalty Math

Durability penalties are **additive**. They increase the existing damage on your item.

* **Formula:** `New Damage = Current Damage + (Max Durability * Penalty%)`
* **Example:**
  * Item has `90/100` durability (Current Damage = 10).
  * Penalty is `10%` (Damage +10).
  * **Result:** `10 + 10 = 20` Damage. Item drops to `80/100` durability.

---

## ✨ Experience (XP)

| Setting | Default | Description |
| :--- | :--- | :--- |
| **XP Enabled** | `true` | Toggle XP saving mechanic. |
| **XP Percent Kept** | `20%` | Percentage of total XP points preserved. (Vanilla is 0%, `keepInventory` is 100%). |
| **Note** | - | This Global setting is overridden by [[Advanced Penalties|Advanced-Penalties]] if a specific rule matches. |

---

## 🔊 Feedback

| Setting | Default | Description |
| :--- | :--- | :--- |
| **Show Message** | `true` | Shows "Your gear has been saved!" in chat on respawn. |
| **Message Text** | `Text` | Customize the message color and text. |
| **Play Sound** | `true` | Plays `entity.player.levelup` sound on respawn as confirmation. |
