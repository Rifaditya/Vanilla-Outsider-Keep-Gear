# 🛡️ Vanilla Outsider: Keep Gear

A Fabric mod for Minecraft 1.21.11 that keeps items with durability upon death while dropping other items.

> **Created by DasikIgaijin** — Made with AI assistance from Claude Opus 4.5

---

## 📖 Overview

In vanilla Minecraft, you lose everything when you die. **Vanilla Outsider: Keep Gear** changes this by letting you keep items that have durability (armor, tools, weapons) while still dropping materials and consumables.

This mod is part of the **Vanilla Outsider** collection — mods that enhance vanilla Minecraft using only vanilla items, bringing an outsider's perspective to pure vanilla gameplay.

---

## ✨ Features

### 🛡️ Smart Item Keeping

| Item Type | On Death |
|-----------|----------|
| **Armor** (helmet, chest, legs, boots) | ✅ Kept |
| **Tools** (pickaxe, axe, shovel, hoe) | ✅ Kept |
| **Weapons** (sword, bow, crossbow, trident) | ✅ Kept |
| **Materials** (cobblestone, diamonds, etc.) | ❌ Dropped |
| **Consumables** (food, potions) | ❌ Dropped |

### ⚙️ Durability Penalty

- Items kept on death lose **1.2%** of their max durability
- Prevents death from being consequence-free
- Configurable percentage

### 📊 XP Retention

- Keep **20%** of your XP on death
- Remaining **80%** drops as XP orbs
- Configurable percentage

### 📋 Blacklist & Whitelist

| List | Purpose |
|------|---------|
| **Blacklist** | Force items with durability to DROP (e.g., elytra) |
| **Whitelist** | Force items without durability to be KEPT (e.g., diamonds) |

### 🌍 Special Death Types

| Death Type | Default | Options |
|------------|---------|---------|
| **Void Death** | Keep gear | `keep_gear` or `drop_all` |
| **PvP Death** | Keep gear | `keep_gear` or `drop_all` |
| **Hardcore Mode** | Drop all | Always uses vanilla behavior |

### 🌌 Dimension Control

Disable the mod in specific dimensions (e.g., force vanilla behavior in The End).

---

## 🎮 Commands

All commands require operator permissions (level 2).

| Command | Description |
|---------|-------------|
| `/keepgear` | Show mod status |
| `/keepgear reload` | Reload config |
| `/keepgear blacklist add <item>` | Add item to blacklist |
| `/keepgear whitelist add <item>` | Add item to whitelist |
| `/keepgear dimension disable <dim>` | Disable mod in dimension |

**Examples:**

```
/keepgear whitelist add minecraft:diamond
/keepgear blacklist add minecraft:elytra
/keepgear dimension disable minecraft:the_end
```

---

## 📦 Installation

### Requirements

- **Minecraft**: 1.21.11
- **Fabric Loader**: 0.18.4 or higher
- **Fabric API**: Latest version
- **Fabric Language Kotlin**: Required

### Steps

1. Install [Fabric Loader](https://fabricmc.net/use/) for Minecraft 1.21.11
2. Download and install [Fabric API](https://modrinth.com/mod/fabric-api)
3. Download and install [Fabric Language Kotlin](https://modrinth.com/mod/fabric-language-kotlin)
4. Download **Vanilla Outsider: Keep Gear**
5. Place the mod `.jar` in your `mods` folder
6. Launch Minecraft

---

## 🤝 Compatibility

- ✅ Compatible with most other Fabric mods
- ✅ Multiplayer compatible
- ✅ Respects vanilla `keepInventory` gamerule
- ✅ Works with Curse of Vanishing

---

## 📜 License

This project is licensed under the **GPL-3.0 License**.

---

## 👤 Credits

- **DasikIgaijin** - Mod Creator & Designer
- **Claude Opus 4.5** - AI Development Assistant

---

## 🌟 Vanilla Outsider Collection

Other mods in this collection:

- [Vanilla Outsider: Better Dogs](https://github.com/Rifaditya/Vanilla-Outsider-Better-Dogs) - Enhanced wolf AI with personalities
- [Vanilla Outsider: Gold Progression](https://github.com/Rifaditya/Vanilla-Outsider-Gold-Progression) - Rebalanced gold equipment progression
- [Vanilla Outsider: More Animal Drops](https://github.com/Rifaditya/Vanilla-Outsider-More-Animal-Drops) - Enhanced animal loot drops

---

## 🔗 Links

- [GitHub Repository](https://github.com/Rifaditya/Vanilla-Outsider-Keep-Gear)
- [Issue Tracker](https://github.com/Rifaditya/Vanilla-Outsider-Keep-Gear/issues)

---

<div align="center">

**Made with ❤️ for the Minecraft community**

*Part of the Vanilla Outsider Collection*

</div>
