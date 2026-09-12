# 🛡️ Vanilla Outsider: Keep Gear Wiki

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Welcome to the official **Vanilla Outsider: Keep Gear** documentation portal. **Keep Gear** is an intelligent, high-precision equipment preservation mod for Minecraft **26.3** (Java 25, Fabric Loader) developed under the **Vanilla Outsider** design philosophy.

Instead of losing everything on death or trivializing survival with standard `keepInventory`, **Keep Gear** preserves items with durability bars (armor, weapons, tools, shields, elytra) while applying configurable durability degradation penalties and experience retention rules.

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository** (`v1.2.4+26.3`), which includes the pure Java 25 architecture, Brigadier command tree `/keepgear`, reflection-safe Trinkets accessory integration, and modern DataComponents preservation.

---

## 🌟 Key Features

* **Intelligent Item Classification**: Automatically identifies gear via native Minecraft tags (`#swords`, `#pickaxes`, `#head_armor`), component inspection (`DataComponents.EQUIPPABLE`, `DataComponents.BLOCKS_ATTACKS`), and durability check (`ItemStack.isDamageableItem()`).
* **Durability Penalty System**: Preserved equipment absorbs wear upon death (Default: `10.0%` max durability base wear + `0.1%` per total enchantment level).
* **Echo Shard Insurance**: Carrying an Echo Shard in your inventory absorbs death trauma—consuming 1 Echo Shard upon respawn to negate 100% of durability damage with custom audio-visual feedback.
* **Granular Death Scenarios**: Configurable behavior for Void falls (`voidDeath = "follow_mod" | "drop_all"`) and PvP encounters (`pvpDeath = "follow_mod" | "drop_all"`).
* **Experience Preservation**: Configurable XP retention (Default: `20%` preserved, `80%` dropped as orbs).
* **Container & Bag Safety**: Granular handling for Shulker Boxes, Bundles, and modded backpacks with optional spill-contents mode.
* **Trinkets Compatibility**: Seamless, reflection-safe compatibility with Trinkets accessories across death cycles.
* **Brigadier Command Engine**: Complete in-game `/keepgear` management tree with permission gating (`Commands.LEVEL_GAMEMASTERS`).

---

## 🧭 Documentation Portal Navigation

| Section | Description | Link |
| :--- | :--- | :--- |
| **Configuration** | Complete guide to `vanilla-outsider-keep-gear.json`, category toggles, and Mod Menu / YACL | [[Configuration]] |
| **Commands & Permissions** | In-game command reference for `/keepgear status`, `set`, `reload`, and `reset` | [[Commands-and-Permissions]] |
| **Durability & Penalties** | Mathematical formulas, enchantment weights, Echo Shard insurance, and curses | [[Durability-and-Penalties]] |
| **Architecture & Mixins** | Technical deep dive into injection mixins, player bridges, and packet sync | [[Architecture-and-Mixins]] |
| **Version Compatibility** | Support lifecycle across Minecraft 26.3, Fabric Loader, Java 25, and optional mods | [[Version-Compatibility]] |

---

## 📦 Quick Installation

1. Install **Minecraft 26.3** with **Fabric Loader** (`>=0.19.3`).
2. Ensure **Fabric API** is installed in your `mods/` directory.
3. Place `vanilla-outsider-keep-gear-1.2.4+26.3.jar` into `mods/`.
4. *(Optional)* Install **Mod Menu** and **Yet Another Config Lib (YACL)** for in-game configuration screens.
5. Launch the game, verify with `/keepgear status`.
