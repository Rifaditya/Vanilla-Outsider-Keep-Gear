<div align="center">

![Vanilla Outsider: Keep Gear Banner](http://PLACEHOLDER_LINK_HERE)

</div>
<p align="center">
    <a href="https://modrinth.com/mod/fabric-api"><img src="https://img.shields.io/badge/Requires-Fabric_API-blue?style=for-the-badge&logo=fabric" alt="Requires Fabric API"></a>
    <a href="https://modrinth.com/mod/fabric-language-kotlin"><img src="https://img.shields.io/badge/Language-Kotlin-purple?style=for-the-badge&logo=kotlin" alt="Kotlin"></a>
    <a href="https://modrinth.com/mod/cloth-config"><img src="https://img.shields.io/badge/Config-Cloth_Config-orange?style=for-the-badge" alt="Cloth Config"></a>
    <img src="https://img.shields.io/badge/License-GPLv3-green?style=for-the-badge" alt="License">
</p>

# ⚔️ A Balanced Interpretation of "Keep Inventory"

Do you hate losing your enchanted Netherite sword in lava, but feel like `/gamerule keepInventory true` removes all the challenge? **Vanilla Outsider: Keep Gear** offers the perfect middle ground.

**The Rule is Simple:** If it has durability, you **keep it**. If it doesn't, you **drop it**.

---

## ✨ Key Features

### 🛡️ Durability-Based Logic

The mod automatically scans your inventory on death. No manual whitelists needed—it just works, even with modded items!

| Icon | Category | Action (Default) | Examples |
| :---: | :--- | :--- | :--- |
| 🛡️ | **Armor** | **KEPT** | Helmets, Chestplates, Leggings, Boots |
| ⚔️ | **Weapons** | **KEPT** | Swords, Bows, Crossbows, Tridents, Mace |
| ⚒️ | **Tools** | **KEPT** | Pickaxes, Axes, Shovels, Hoes, Shears, Fishing Rods |
| 🛡️ | **Shields** | **KEPT** | All Shields |
| 🕊️ | **Elytra** | **KEPT** | Elytra |
| 💎 | **Resources** | **DROPPED** | Diamonds, Wood, Cobblestone, Mob Drops |
| 🧪 | **Consumables** | **DROPPED** | Food, Potions, Stews |

> [!NOTE]
> **Mod Compatibility:** Since we check for the `durability` component, this automatically works for copper armor, emerald tools, or any other modded gear!

### ⚖️ Death Penalties (Fair Play)

Dying shouldn't be free. To balance the convenience of keeping your gear, you pay a price:

* **Durability Cost:** All preserved items lose **1.2%** (configurable) of their max durability.
* **Enchantment Weight:** Heavy magic comes with a cost. Each enchantment level adds **0.1%** (configurable) to the penalty.
* **Echo Shard Utility:** Carrying an **Echo Shard** consumes it on death to **completely bypass** the durability penalty.
* **XP Tax:** You keep **20%** (configurable) of your XP. The rest drops as orbs or is lost, depending on your settings.

### 🔌 Intelligent Integrations

We've built native compatibility with popular mods to ensure a seamless experience.

* **Trinkets:** Optional integration! Works with official Trinkets and forks (Trinkets Canary). Toggle "Keep Trinkets" in the Integrations menu to preserve your equipped gear. Also respects Echo Shard protection!
* **Backpacks:** Safely kept if they have durability.
* **Mod Menu:** Full in-game configuration UI with granular category toggles.

---

## ⚙️ Configuration

You can customize almost everything via **Mod Menu** or the config file.

* **Category Toggles:** Don't want to keep Elytra? Want to keep Food? You can now toggle keeping for Armor, Weapons, Tools, Shields, Elytra, Consumables, and Resources independently.
* **Whitelist/Blacklist:** Need even more precision? Use specific item IDs or tags (`#c:soulbound_items`) to override the category defaults.
* **Dimension Control:** Disable the mod in specific dimensions (like The End) for an extra challenge.
* **Void & PvP:** Choose whether to keep gear or drop everything when dying to the Void or other Players.

---

## 📦 Installation

1. Download **[Fabric Loader](https://fabricmc.net/)** for Minecraft 1.21.11.
2. Install **[Fabric API](https://modrinth.com/mod/fabric-api)** and **[Fabric Language Kotlin](https://modrinth.com/mod/fabric-language-kotlin)**.
3. Install **[Cloth Config](https://modrinth.com/mod/cloth-config)** (Required for config screen).
4. Install **[Mod Menu](https://modrinth.com/mod/modmenu)** (Recommended).
5. Download `Vanilla-Outsider-Keep-Gear.jar` and place it in your `mods` folder.

---

## 📜 Legal & Credits

| Role | Author |
| :--- | :--- |
| **Creator** | DasikIgaijin |
| **Collection** | Vanilla Outsider |
| **License** | GNU GPLv3 |

> [!IMPORTANT]
> This mod is part of the **Vanilla Outsider** collection. You are free to use it in modpacks, videos, and servers.

---

<div align="center">

**Made with ❤️ for the Minecraft community**

*Part of the Vanilla Outsider Collection*

</div>
