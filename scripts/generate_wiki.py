#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Vanilla Outsider: Keep Gear - 11-Language Global Demographic Wiki Suite Generator
Generates full documentation in .wiki/ and mirrors to Wiki/
"""

import os
import re
import shutil
from pathlib import Path

# Paths
ROOT_DIR = Path(__file__).resolve().parent.parent
WIKI_DIR = ROOT_DIR / ".wiki"
MIRROR_DIR = ROOT_DIR / "Wiki"

SWITCHBOARD_MD = (
    "🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | "
    "[[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | "
    "[[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | "
    "[[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | "
    "[[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | "
    "[[🇰🇷 한국어|ko_kr-Home]]"
)

FOOTER_CONTENT = """<p align="center">
  <hr>
  <strong>Vanilla Outsider: Keep Gear Documentation</strong> &bull; Part of the <strong>Vanilla Outsider Collection</strong><br>
  <em>Author: <strong>Dasik (Rifaditya)</strong> &bull; Licensed under <strong>GNU General Public License v3.0 (GPLv3)</strong></em><br>
  <small>📌 <strong>Repository Source Disclaimer</strong>: The documentation in this Wiki reflects the current source code state in the repository, which may include recent unreleased commits ahead of public release builds on CurseForge/Modrinth.</small>
</p>
"""

PAGES = {}

# ==============================================================================
# 1. ENGLISH SUITE
# ==============================================================================

PAGES["Home.md"] = f"""# 🛡️ Vanilla Outsider: Keep Gear Wiki

{SWITCHBOARD_MD}

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
"""

PAGES["Configuration.md"] = f"""# ⚙️ Configuration Guide

{SWITCHBOARD_MD}

**Vanilla Outsider: Keep Gear** provides an exhaustive, granular configuration system serialized as clean JSON via Google Gson.

## 📁 File Location & Format

* **Disk File**: `.minecraft/config/vanilla-outsider-keep-gear.json`
* **In-Game GUI**: Accessible via **Mod Menu** -> Select `Vanilla Outsider: Keep Gear` -> Click **Configure** (requires YetAnotherConfigLib).
* **Console / In-Game Chat**: Managed dynamically using the [[Commands & Permissions|Commands-and-Permissions]] suite.

---

## 📋 Comprehensive Option Reference

### 1. General Switch
| Key | Type | Default | Description |
| :--- | :--- | :--- | :--- |
| `enabled` | `boolean` | `true` | Master toggle for the entire mod. When `false`, vanilla death logic applies. |

### 2. Equipment Categories (What to Keep)
| Key | Type | Default | Preservation Criteria |
| :--- | :--- | :--- | :--- |
| `keepArmor` | `boolean` | `true` | Items matching `#minecraft:head_armor`, `chest_armor`, `leg_armor`, `foot_armor`, or `Equippable` slot. |
| `keepWeapons` | `boolean` | `true` | Items matching `#minecraft:swords`, bows, crossbows, tridents, maces, or `DataComponents.WEAPON`. |
| `keepTools` | `boolean` | `true` | Pickaxes, axes, shovels, hoes, shears, fishing rods, brushes, flint and steel. |
| `keepShields` | `boolean` | `true` | Standard shields or items having `DataComponents.BLOCKS_ATTACKS`. |
| `keepElytra` | `boolean` | `true` | Vanilla `minecraft:elytra` and equippable chest flight gear with durability. |
| `keepContainers` | `boolean` | `false` | Bundles, shulker boxes, and whitelisted modded backpacks. |
| `containerDropContents`| `boolean` | `false` | When `true`, saves the empty container item but spills contained items to the ground. |
| `keepConsumables` | `boolean` | `false` | Food items (`DataComponents.FOOD`) and potions (`DataComponents.CONSUMABLE`). |
| `keepResources` | `boolean` | `false` | Non-durable materials (diamonds, cobblestone, ingots). When `true`, functions like `keepInventory`. |

### 3. Durability Wear Penalties
| Key | Type | Default | Mathematical Impact |
| :--- | :--- | :--- | :--- |
| `penaltyEnabled` | `boolean` | `true` | Toggles whether preserved equipment takes durability damage on death. |
| `penaltyPercent` | `double` | `10.0` | Base percentage of item's maximum durability deducted on respawn. |
| `enchantmentPenaltyEnabled` | `boolean` | `true` | Toggles enchantment weight penalty modifier. |
| `enchantmentPenaltyValue` | `double` | `0.1` | Additional penalty percentage added per total enchantment level. |

### 4. Insurance & Curses
| Key | Type | Default | Behavioral Logic |
| :--- | :--- | :--- | :--- |
| `useEchoShard` | `boolean` | `true` | Consumes 1 Echo Shard upon death to grant 100% durability wear immunity. |
| `keepBindingCurse` | `boolean` | `true` | When `true`, Curse of Binding items remain equipped. When `false`, they drop on death. |

### 5. Experience (XP) Retention
| Key | Type | Default | Impact |
| :--- | :--- | :--- | :--- |
| `xpEnabled` | `boolean` | `true` | Toggles player experience preservation. |
| `xpPercent` | `int` | `20` | Percentage of total player XP preserved across death (0 to 100). |
| `xpDropRemaining` | `boolean` | `true` | When `true`, the remaining unpreserved XP drops as orbs. When `false`, it vanishes. |

### 6. Special Death Scenarios
| Key | Values | Default | Behavior |
| :--- | :--- | :--- | :--- |
| `voidDeath` | `"follow_mod"` \\| `"drop_all"` | `"follow_mod"` | Out-of-world death behavior. `"drop_all"` forces complete drop on void death. |
| `pvpDeath` | `"follow_mod"` \\| `"drop_all"` | `"follow_mod"` | Behavior when slain by another player. |

### 7. Whitelists & Blacklists
| Key | Type | Description |
| :--- | :--- | :--- |
| `whitelist` | `List<String>` | Item IDs explicitly preserved regardless of durability (e.g. `["minecraft:totem_of_undying"]`). |
| `blacklist` | `List<String>` | Item IDs explicitly dropped regardless of durability (e.g. `["minecraft:wooden_sword"]`). |
| `dimensionBlacklist` | `List<String>` | Dimension IDs where Keep Gear is disabled (e.g. `["minecraft:the_end"]`). |
| `containerWhitelist` | `List<String>` | Container IDs recognized for container preservation rules. |

---

## 📝 Example `vanilla-outsider-keep-gear.json`

```json
{{
  "enabled": true,
  "keepArmor": true,
  "keepWeapons": true,
  "keepTools": true,
  "keepShields": true,
  "keepElytra": true,
  "keepContainers": false,
  "containerDropContents": false,
  "keepConsumables": false,
  "keepResources": false,
  "penaltyEnabled": true,
  "penaltyPercent": 10.0,
  "enchantmentPenaltyEnabled": true,
  "enchantmentPenaltyValue": 0.1,
  "useEchoShard": true,
  "keepBindingCurse": true,
  "xpEnabled": true,
  "xpPercent": 20,
  "xpDropRemaining": true,
  "voidDeath": "follow_mod",
  "pvpDeath": "follow_mod",
  "showMessage": true,
  "messageText": "§aYour gear has been preserved!",
  "playSound": true,
  "showParticles": true,
  "blacklist": [],
  "whitelist": [],
  "dimensionBlacklist": [],
  "containerWhitelist": [
    "minecraft:shulker_box",
    "minecraft:bundle",
    "travelersbackpack:*",
    "backpacks:backpack"
  ]
}}
```
"""

PAGES["Commands-and-Permissions.md"] = f"""# 💻 Commands & Permissions

{SWITCHBOARD_MD}

**Vanilla Outsider: Keep Gear** includes a comprehensive Brigadier command tree registered under `/keepgear`.

## 🔐 Permission Level Architecture

The mod adheres to Minecraft's standard 4-tier permission model:
* **Informational Commands** (`/keepgear`, `/keepgear status`, `/keepgear help`): Available to all players by default (`permission level 0`), allowing players to inspect active death rules.
* **Administrative Commands** (`set`, `reload`, `reset`): Strictly gated by `Commands.LEVEL_GAMEMASTERS` (`permission level 2`, command blocks and server operators).

---

## 📜 Command Reference Tree

### 1. `/keepgear` & `/keepgear status`
Prints a formatted, color-coded dashboard of the active mod configuration in chat.
* **Syntax**: `/keepgear` or `/keepgear status`
* **Output**:
  ```text
  [Vanilla Outsider: Keep Gear Status]
    Master Enabled: true
    Armor: true | Weapons: true | Tools: true
    Shields: true | Elytra: true | Containers: false
    Penalty: 10.0% | Echo Shard: true
  ```

### 2. `/keepgear help`
Lists available subcommands with brief syntax hints.
* **Syntax**: `/keepgear help`

### 3. `/keepgear reload`
Reloads the configuration directly from disk (`config/vanilla-outsider-keep-gear.json`).
* **Requirement**: Gamemaster Level 2+
* **Syntax**: `/keepgear reload`
* **Response**: `Keep Gear configuration reloaded from disk.`

### 4. `/keepgear reset`
Resets the configuration to default factory values and immediately persists it to disk.
* **Requirement**: Gamemaster Level 2+
* **Syntax**: `/keepgear reset`
* **Response**: `Keep Gear configuration reset to defaults.`

### 5. `/keepgear set <property> <value>`
Dynamically updates a configuration value at runtime and saves it to disk without server restart.
* **Requirement**: Gamemaster Level 2+
* **Available Properties**:
  * `enabled <true|false>`: Master switch.
  * `keepArmor <true|false>`: Armor category preservation.
  * `keepWeapons <true|false>`: Weapons category preservation.
  * `keepTools <true|false>`: Tools category preservation.
  * `keepContainers <true|false>`: Storage containers toggle.
  * `penaltyPercent <0.0 .. 100.0>`: Base durability wear percentage.
  * `useEchoShard <true|false>`: Echo Shard insurance toggle.

#### Examples:
```mcfunction
/keepgear set enabled true
/keepgear set penaltyPercent 15.0
/keepgear set keepContainers true
/keepgear set useEchoShard false
```
"""

PAGES["Durability-and-Penalties.md"] = f"""# 💔 Durability & Penalties

{SWITCHBOARD_MD}

To strike the balance between vanilla consequence and equipment safety, **Vanilla Outsider: Keep Gear** introduces a deterministic durability wear calculation system.

## 🧮 Durability Degradation Formula

When a player dies, each preserved item undergoes durability evaluation:

$$\\text{{Total Wear \\%}} = \\text{{Base Penalty \\%}} + (\\sum \\text{{Enchantment Levels}} \\times \\text{{Weight Factor \\%}})$$

Where:
* **Base Penalty**: Default `10.0%` (Configurable via `penaltyPercent`).
* **Weight Factor**: Default `0.1%` (Configurable via `enchantmentPenaltyValue`).
* **Sum of Enchantment Levels**: Combined level of all enchantments present on the item stack.

### 📐 Applied Damage Calculation
$$\\text{{Damage Added}} = \\max\\left(1, \\text{{round}}\\left(\\frac{{\\text{{Total Wear \\%}}}}{{100}} \\times \\text{{Max Durability}}\\right)\\right)$$

$$\\text{{New Damage Value}} = \\text{{Current Damage}} + \\text{{Damage Added}}$$

> [!WARNING]
> **Lethal Wear Breakage**: If $\\text{{New Damage Value}} \\ge \\text{{Max Durability}}$, the item **breaks completely** during death and is permanently destroyed (`ItemStack.EMPTY`), preventing ghost item exploits.

---

## 📊 Concrete Calculation Examples

| Equipment Item | Enchantments | Total Levels | Base % | Enchant Weight % | Total Wear % | Max Durability | Damage Added |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **Netherite Pickaxe** | Efficiency V, Unbreaking III, Fortune III, Mending I | $5+3+3+1 = 12$ | $10.0\\%$ | $12 \\times 0.1\\% = 1.2\\%$ | **11.2%** | $2031$ | $+227$ Damage |
| **Diamond Sword** | Sharpness V, Looting III, Unbreaking III | $5+3+3 = 11$ | $10.0\\%$ | $11 \\times 0.1\\% = 1.1\\%$ | **11.1%** | $1561$ | $+173$ Damage |
| **Iron Chestplate** | None | $0$ | $10.0\\%$ | $0.0\\%$ | **10.0%** | $240$ | $+24$ Damage |
| **Netherite Helmet** | Protection IV, Respiration III, Aqua Affinity I, Unbreaking III, Mending I | $4+3+1+3+1 = 12$ | $10.0\\%$ | $1.2\\%$ | **11.2%** | $407$ | $+46$ Damage |

---

## 🔮 Echo Shard Resonance Insurance

Carrying an **Echo Shard** (`minecraft:echo_shard`) functions as insurance against gear degradation:

1. Upon death, `ItemPreservationEngine.checkAndConsumeEchoShard()` scans the player's 36 main inventory slots.
2. If an Echo Shard is found, **exactly 1 shard is consumed** from the stack.
3. The `echoResonance` flag is set to `true`.
4. **All durability degradation is completely bypassed** (0 damage applied to any kept item).
5. Upon respawn, the player receives:
   * Green preservation confirmation.
   * Aqua chat alert: `Echo Shard resonance protected your gear from durability wear!`
   * Custom sound effect: `SoundEvents.RESPAWN_ANCHOR_DEPLETE` (`0.8F` volume, `1.2F` pitch).

---

## ☠️ Curse Interactions

* **Curse of Vanishing** (`EnchantmentEffectComponents.PREVENT_EQUIPMENT_DROP`): Fully respected. Any item bearing Curse of Vanishing is completely destroyed upon death, bypassing preservation.
* **Curse of Binding** (`EnchantmentEffectComponents.PREVENT_ARMOR_CHANGE`): Controlled by `keepBindingCurse`. When `true` (default), binding items remain equipped across death. When `false`, binding cursed gear is dropped to the ground at the death location.
"""

PAGES["Architecture-and-Mixins.md"] = f"""# 🏛️ Architecture & Mixins

{SWITCHBOARD_MD}

**Vanilla Outsider: Keep Gear** is engineered with a clean, low-overhead, modular architecture targeting Minecraft 26.3 Java Edition on Fabric Loader.

## 🏗️ Core Architectural Diagram

```text
       Player Death Event
               │
               ▼
   PlayerDropEquipmentMixin (HEAD)
               │
               ▼
   ItemPreservationEngine.processDeathPreservation()
        ├── 1. Evaluate Eligibility (Dimension, Void, PvP)
        ├── 2. Check Echo Shard Insurance
        ├── 3. Filter Main Inventory (0..35)
        ├── 4. Filter Equipment Slots (HEAD, CHEST, LEGS, FEET, OFFHAND)
        ├── 5. Apply Durability Wear Formulas
        └── 6. Extract Preserved XP & Deduct from Player
               │
               ▼
   KeepGearPlayerBridge.setPreservedInventory()
               │
               ▼ [Vanilla dropEquipment() runs without preserved stacks]
               │
       Player Respawn Event
               │
               ▼
   ServerPlayerRespawnMixin (restoreFrom TAIL)
        ├── 1. Retrieve PreservedInventory from Old Player Bridge
        ├── 2. Restore Exact Main Inventory Slots (0..35)
        ├── 3. Restore Equipment Slots (with inventory/ground fallback)
        ├── 4. Restore XP & Send ClientboundSetExperiencePacket
        ├── 5. Send Audio/Visual Feedback Packets
        └── 6. Broadcast Container & Inventory Menu Changes
```

---

## 💉 Mixin Injection Specifications

### 1. `PlayerDropEquipmentMixin`
* **Target**: `net.minecraft.world.entity.player.Player`
* **Injection Point**: `@Inject(method = "dropEquipment", at = @At("HEAD"))`
* **Mechanism**: Intercepts the vanilla item drop sequence before equipment is converted into `ItemEntity` instances in the world. Stacks matching preservation criteria are removed from the player's inventory directly into a temporary `PreservedInventory` record.

### 2. `ServerPlayerRespawnMixin`
* **Target**: `net.minecraft.server.level.ServerPlayer`
* **Interface**: Implements `KeepGearPlayerBridge`
* **Injection Point**: `@Inject(method = "restoreFrom", at = @At("TAIL"))`
* **Mechanism**: When a server player respawns from death (`restoreAll == false`), items stored in the bridge from the old player instance are restored into identical slots on the new player instance.

---

## 🛡️ Equipment Conflict Safety Fallback

If an equipment slot (e.g. helmet) is somehow occupied upon respawn:
1. The engine attempts to place the item into the player's main inventory (`inventory.add(stack)`).
2. If the player's inventory is completely full, the item is dropped safely at the player's respawn feet (`newPlayer.drop(stack, false)`).
3. Under no circumstance is an item deleted or overwritten.

---

## 🌐 Network & Client Synchronization

* **HUD Experience Sync**: Vanilla Minecraft does not automatically re-synchronize experience bar graphics after programmatic XP restoration on respawn. `ServerPlayerRespawnMixin` explicitly transmits `ClientboundSetExperiencePacket` to prevent visual desynchronization.
* **Audio Cues**: Dispatches `ClientboundSoundPacket` for `ARMOR_EQUIP_GENERIC` (or `RESPAWN_ANCHOR_DEPLETE` when Echo Shard is used).
* **Menu Sync**: Calls `broadcastChanges()` on both `containerMenu` and `inventoryMenu`.
"""

PAGES["Version-Compatibility.md"] = f"""# 🗺️ Version Compatibility & Lifecycle Matrix

{SWITCHBOARD_MD}

**Vanilla Outsider: Keep Gear** adheres strictly to the **1 JAR 1 Version** development law. Each release is tailored specifically to the targeted Minecraft version's internal mappings and component APIs.

## 📊 Compatibility Matrix

| Minecraft Version | Mod Version | Build Status | Java Runtime | Fabric Loader | Fabric API | Architecture |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **Minecraft 26.3** | `1.2.4+26.3` | 🟢 Active Release | **Java 25+** | `>=0.19.3` | `0.156.1+26.3` | Pure Java 25, Brigadier, DataComponents |
| **Minecraft 1.21.1** | `1.1.0+1.21.1` | 🟡 Legacy / LTS | Java 21+ | `>=0.16.0` | `0.100.0+` | Kotlin / Fabric |
| **Minecraft 1.20.4** | `1.0.0+1.20.4` | 🔴 Deprecated | Java 17+ | `>=0.15.0` | `0.90.0+` | Legacy NBT |

---

## 🧩 Companion & Mod Compatibility

| Mod | Minimum Version | Integration Level | Behavior |
| :--- | :--- | :--- | :--- |
| **Fabric API** | `0.156.1+26.3` | **Required** | Provides command registration and lifecycle hooks. |
| **Trinkets** | `3.11.0+` | **Optional (Reflection-Safe)** | When detected, accessories equipped in Trinkets slots are preserved on death. Zero runtime crash if absent. |
| **YetAnotherConfigLib (YACL)** | `3.9.5+` | **Optional** | Renders advanced in-game configuration menu. |
| **Mod Menu** | `18.0.0-beta.1+` | **Optional** | Provides the config button inside the Mod Menu UI. |
| **Traveler's Backpack** | Latest | **Automatic** | Whitelisted container; supports safe mode and spill mode. |
| **Shulker Box / Bundles** | Vanilla 26.3 | **Native** | Whitelisted container; supports safe mode and spill mode. |
"""

# ==============================================================================
# 2. LOCALIZED SUITE (10 Languages x 4 Core Pages)
# ==============================================================================

LOCALES = {
    "zh_cn": {
        "lang_name": "🇨🇳 简体中文 (Simplified Chinese)",
        "sidebar_home": "🏠 门户 (Home)",
        "sidebar_config": "⚙️ 配置指南 (Configuration)",
        "sidebar_cmds": "💻 指令与权限 (Commands)",
        "sidebar_dur": "💔 耐久与惩罚 (Durability)",
        "Home": {
            "title": "🛡️ 保持装备 (Keep Gear) 百科",
            "desc": "欢迎来到 **Vanilla Outsider: Keep Gear** 官方文档门户。Keep Gear 是一款依据 **Vanilla Outsider** 哲学开发的适用于 Minecraft **26.3**（Java 25，Fabric Loader）的高性能装备保护模组。\n\n本模组并非完全保留所有物品（传统 `keepInventory` 使得游戏过于简单），也不是原版的死亡全掉落，而是**智能保留带有耐久度条的装备**（护甲、武器、工具、盾牌、鞘翅），并施加可配置的耐久度惩罚与经验保留机制。",
            "nav_config": "详细配置指南、物品类别开关及 Mod Menu / YACL",
            "nav_cmds": "游戏内 `/keepgear` 管理指令、权限与重载",
            "nav_penalties": "耐久损耗公式、附魔权重计算、回响碎片保险与诅咒",
            "nav_arch": "Mixin 注入架构、网络数据包同步与崩溃防护"
        },
        "Configuration": {
            "title": "⚙️ 配置指南",
            "desc": "本模组采用 Google Gson 格式持久化存储于 `.minecraft/config/vanilla-outsider-keep-gear.json`，支持客户端与服务端动态热重载。",
            "cat_title": "🎒 物品保留类别",
            "wear_title": "💔 耐久度损耗惩罚",
            "xp_title": "✨ 经验保留设置"
        },
        "Commands": {
            "title": "💻 指令与权限",
            "desc": "Keep Gear 提供完整的 Brigadier 指令树，注册于 `/keepgear`。信息查询指令向所有玩家开放，而配置修改与重载指令需要 2 级操作员权限（GameMaster）。"
        },
        "Durability": {
            "title": "💔 耐久损耗与惩罚机制",
            "desc": "玩家死亡时，保留的装备将根据基础惩罚百分比和附魔权重承受耐久度磨损。若磨损值达到或超过物品最大耐久度，该物品将彻底损坏碎裂。",
            "echo_desc": "携带回响碎片（Echo Shard）时，死亡将消耗 1 枚回响碎片，完全免除所有装备的耐久度磨损，并播放专属音效提示。"
        }
    },
    "zh_tw": {
        "lang_name": "🇭🇰 繁體中文 (Traditional Chinese)",
        "sidebar_home": "🏠 門戶 (Home)",
        "sidebar_config": "⚙️ 設定指南 (Configuration)",
        "sidebar_cmds": "💻 指令與權限 (Commands)",
        "sidebar_dur": "💔 耐久與懲罰 (Durability)",
        "Home": {
            "title": "🛡️ 保持裝備 (Keep Gear) 百科",
            "desc": "歡迎來到 **Vanilla Outsider: Keep Gear** 官方文件門戶。Keep Gear 是一套依據 **Vanilla Outsider** 理念開發的 Minecraft **26.3**（Java 25，Fabric Loader）高效能裝備保護模組。\n\n本模組在原版殘酷掉落與 `keepInventory` 之間取得平衡，**智慧保留具備耐久度條的裝備**（盔甲、武器、工具、盾牌、鞘翅），並施加可配置的耐久度磨損與經驗保留機制。",
            "nav_config": "詳細設定指南、物品類別切換及 Mod Menu / YACL",
            "nav_cmds": "遊戲內 `/keepgear` 管理指令、權限與重新載入",
            "nav_penalties": "耐久磨損公式、附魔權重計算、回響碎片保險與詛咒",
            "nav_arch": "Mixin 注入架構、網絡封包同步與防崩潰機制"
        },
        "Configuration": {
            "title": "⚙️ 設定指南",
            "desc": "本模組透過 Google Gson 儲存於 `.minecraft/config/vanilla-outsider-keep-gear.json`，支援客戶端與伺服器端動態熱重載。",
            "cat_title": "🎒 物品保留類別",
            "wear_title": "💔 耐久度磨損懲罰",
            "xp_title": "✨ 經驗保留設定"
        },
        "Commands": {
            "title": "💻 指令與權限",
            "desc": "Keep Gear 提供完整的 Brigadier 指令樹，註冊於 `/keepgear`。狀態查詢開放給所有玩家，配置修改與重載指令需 2 級管理員權限。"
        },
        "Durability": {
            "title": "💔 耐久磨損與懲罰機制",
            "desc": "玩家死亡時，保留的裝備將根據基礎懲罰比例與附魔等級承受耐久磨損。若損耗值超過物品最大耐久度，該裝備將徹底損毀。",
            "echo_desc": "當物品欄含有回響碎片（Echo Shard）時，死亡將消耗 1 枚碎片以完全抵消所有裝備耐久磨損，並播放專屬共鳴音效。"
        }
    },
    "ru_ru": {
        "lang_name": "🇷🇺 Русский (Russian)",
        "sidebar_home": "🏠 Центральный портал (Home)",
        "sidebar_config": "⚙️ Руководство по конфигурации",
        "sidebar_cmds": "💻 Команды и права доступа",
        "sidebar_dur": "💔 Прочность и штрафы за смерть",
        "Home": {
            "title": "🛡️ Keep Gear — Вики",
            "desc": "Добро пожаловать в официальную документацию **Vanilla Outsider: Keep Gear** для Minecraft **26.3** (Java 25, Fabric Loader). Мод сохраняет предметы с прочностью (броню, оружие, инструменты, щиты, элитры) при смерти, накладывая настраиваемый износ прочности и сохраняя часть опыта.",
            "nav_config": "Полное руководство по настройке конфигурации vanilla-outsider-keep-gear.json",
            "nav_cmds": "Справочник команд /keepgear, права доступа и перезагрузка",
            "nav_penalties": "Формула износа прочности, вес чар, осколок эха и проклятия",
            "nav_arch": "Архитектура миксинов, синхронизация пакетов и безопасность"
        },
        "Configuration": {
            "title": "⚙️ Руководство по конфигурации",
            "desc": "Конфигурация сохраняется в формате JSON в файле `.minecraft/config/vanilla-outsider-keep-gear.json`. Поддерживает горячую перезагрузку без перезапуска сервера.",
            "cat_title": "🎒 Категории сохраняемых предметов",
            "wear_title": "💔 Износ прочности при смерти",
            "xp_title": "✨ Сохранение опыта (XP)"
        },
        "Commands": {
            "title": "💻 Команды и права доступа",
            "desc": "Keep Gear регистрирует дерево команд `/keepgear` на движке Brigadier. Команды статуса доступны всем игрокам, управление — операторам 2 уровня."
        },
        "Durability": {
            "title": "💔 Прочность и штрафы за смерть",
            "desc": "При смерти сохраненные предметы получают износ: Базовый штраф (10%) + дополнительный процент за каждый уровень чар (0.1%). Если износ превышает прочность, предмет ломается навсегда.",
            "echo_desc": "Наличие осколка эха (Echo Shard) поглощает урон прочности: расходуется 1 осколок, предотвращая любой износ снаряжения."
        }
    },
    "es_es": {
        "lang_name": "🇪🇸 Español (Spanish)",
        "sidebar_home": "🏠 Portal central (Home)",
        "sidebar_config": "⚙️ Guía de configuración",
        "sidebar_cmds": "💻 Comandos y permisos",
        "sidebar_dur": "💔 Durabilidad y penalizaciones",
        "Home": {
            "title": "🛡️ Wiki de Vanilla Outsider: Keep Gear",
            "desc": "Bienvenido al portal oficial de documentación de **Keep Gear** para Minecraft **26.3** (Java 25, Fabric). Conserva objetos con durabilidad (armaduras, armas, herramientas, escudos, élitros) al morir, aplicando penalizaciones de desgaste configurables.",
            "nav_config": "Guía completa de configuración de vanilla-outsider-keep-gear.json",
            "nav_cmds": "Referencia de comandos /keepgear y niveles de permisos",
            "nav_penalties": "Fórmulas de desgaste, peso de encantamientos y esquirlas del eco",
            "nav_arch": "Arquitectura interna de mixins y sincronización de red"
        },
        "Configuration": {
            "title": "⚙️ Guía de Configuración",
            "desc": "El archivo de configuración se almacena en `.minecraft/config/vanilla-outsider-keep-gear.json` y es totalmente compatible con Mod Menu y YACL.",
            "cat_title": "🎒 Categorías de Objetos",
            "wear_title": "💔 Penalizaciones de Durabilidad",
            "xp_title": "✨ Retención de Experiencia"
        },
        "Commands": {
            "title": "💻 Comandos y Permisos",
            "desc": "Árbol completo de comandos Brigadier bajo `/keepgear`. Los comandos informativos son públicos; la modificación requiere nivel de operador 2.",
            "echo_desc": "Consumo de esquirlas del eco para anular el desgaste de durabilidad al reaparecer."
        },
        "Durability": {
            "title": "💔 Durabilidad y Penalizaciones",
            "desc": "Al morir, el equipo conservado sufre desgaste: Desgaste Base (10%) + Peso por nivel de encantamiento (0.1%). Si el daño acumulado supera el máximo, el objeto se destruye.",
            "echo_desc": "Tener una esquirla del eco (Echo Shard) en el inventario consume 1 unidad para anular completamente el desgaste de durabilidad."
        }
    },
    "de_de": {
        "lang_name": "🇩🇪 Deutsch (German)",
        "sidebar_home": "🏠 Zentrales Portal (Home)",
        "sidebar_config": "⚙️ Konfigurationshandbuch",
        "sidebar_cmds": "💻 Befehle & Berechtigungen",
        "sidebar_dur": "💔 Haltbarkeit & Strafen",
        "Home": {
            "title": "🛡️ Vanilla Outsider: Keep Gear Wiki",
            "desc": "Willkommen bei der offiziellen Dokumentation für **Keep Gear** (Minecraft **26.3**, Java 25, Fabric). Behalte Ausrüstung mit Haltbarkeitsbalken beim Tod bei konfigurierbarem Haltbarkeitsverlust und Erfahrungserhalt.",
            "nav_config": "Umfassender Konfigurationsleitfaden zu vanilla-outsider-keep-gear.json",
            "nav_cmds": "Befehlsreferenz für /keepgear und Berechtigungsstufen",
            "nav_penalties": "Haltbarkeitsformel, Verzauberungsgewichtung und Echoscherben",
            "nav_arch": "Mixin-Architektur, Netzwerkpakete und Fallbacks"
        },
        "Configuration": {
            "title": "⚙️ Konfigurationshandbuch",
            "desc": "Konfigurationsdatei liegt unter `.minecraft/config/vanilla-outsider-keep-gear.json`. Unterstützt YACL-Menüs und Hot-Reloading.",
            "cat_title": "🎒 Ausrüstungskategorien",
            "wear_title": "💔 Haltbarkeitsabzug beim Tod",
            "xp_title": "✨ Erfahrungspunkte-Erhalt"
        },
        "Commands": {
            "title": "💻 Befehle & Berechtigungen",
            "desc": "Keep Gear registriert den Befehlsbaum `/keepgear`. Statusabfragen sind für alle Spieler zugänglich, administrative Befehle erfordern OP-Stufe 2."
        },
        "Durability": {
            "title": "💔 Haltbarkeit & Strafen",
            "desc": "Beim Tod erleiden behaltene Gegenstände Abnutzung: Basisstrafe (10%) + Zusatzabzug pro Verzauberungsstufe (0.1%). Bei Überschreiten der Maximalhaltbarkeit zerbricht das Item vollständig.",
            "echo_desc": "Eine Echoscherbe (Echo Shard) im Inventar schützt deine Ausrüstung: Beim Tod wird 1 Scherbe verbraucht und jeglicher Haltbarkeitsverlust abgewendet."
        }
    },
    "fr_fr": {
        "lang_name": "🇫🇷 Français (French)",
        "sidebar_home": "🏠 Portail central (Home)",
        "sidebar_config": "⚙️ Guide de configuration",
        "sidebar_cmds": "💻 Commandes et permissions",
        "sidebar_dur": "💔 Durabilité et pénalités",
        "Home": {
            "title": "🛡️ Wiki Vanilla Outsider: Keep Gear",
            "desc": "Bienvenue sur la documentation officielle de **Keep Gear** pour Minecraft **26.3** (Java 25, Fabric). Conservez vos objets à durabilité (armures, armes, outils, boucliers, élytres) lors de la mort avec pénalités de durabilité ajustables.",
            "nav_config": "Guide complet de configuration vanilla-outsider-keep-gear.json",
            "nav_cmds": "Répertoire des commandes /keepgear et permissions",
            "nav_penalties": "Formule d'usure, poids des enchantements et éclats d'écho",
            "nav_arch": "Architecture des Mixins et synchronisation réseau"
        },
        "Configuration": {
            "title": "⚙️ Guide de Configuration",
            "desc": "Le fichier de configuration est situé dans `.minecraft/config/vanilla-outsider-keep-gear.json`. Compatible Mod Menu et YACL.",
            "cat_title": "🎒 Catégories d'Équipement",
            "wear_title": "💔 Pénalités d'Usure de Durabilité",
            "xp_title": "✨ Rétention d'Expérience"
        },
        "Commands": {
            "title": "💻 Commandes et Permissions",
            "desc": "Arborescence Brigadier complète sous `/keepgear`. Les commandes d'état sont ouvertes à tous; la modification requiert le niveau opérateur 2."
        },
        "Durability": {
            "title": "💔 Durabilité et Pénalités",
            "desc": "À la mort, l'équipement conservé subit une usure : Pénalité de base (10%) + Poids d'enchantement (0.1% par niveau). Si les dégâts dépassent le maximum, l'objet est détruit.",
            "echo_desc": "Posséder un éclat d'écho (Echo Shard) dans l'inventaire consomme 1 éclat à la mort et annule l'intégralité de l'usure de durabilité."
        }
    },
    "pt_br": {
        "lang_name": "🇧🇷 Português (Portuguese)",
        "sidebar_home": "🏠 Portal central (Home)",
        "sidebar_config": "⚙️ Guia de configuração",
        "sidebar_cmds": "💻 Comandos e permissões",
        "sidebar_dur": "💔 Durabilidade e penalidades",
        "Home": {
            "title": "🛡️ Wiki do Vanilla Outsider: Keep Gear",
            "desc": "Bem-vindo à documentação oficial do **Keep Gear** para Minecraft **26.3** (Java 25, Fabric). Preserva itens com durabilidade (armaduras, armas, ferramentas, escudos, élitros) ao morrer, aplicando penalidades graduais de desgaste.",
            "nav_config": "Guia de configuração do vanilla-outsider-keep-gear.json",
            "nav_cmds": "Comandos /keepgear e níveis de permissão",
            "nav_penalties": "Fórmulas de desgaste, peso de encantamentos e fragmentos de eco",
            "nav_arch": "Arquitetura interna de mixins e sincronização"
        },
        "Configuration": {
            "title": "⚙️ Guia de Configuração",
            "desc": "Configuração serializada em `.minecraft/config/vanilla-outsider-keep-gear.json`. Suporta Mod Menu e recarregamento sem reinicialização.",
            "cat_title": "🎒 Categorias de Itens",
            "wear_title": "💔 Penalidades de Desgaste",
            "xp_title": "✨ Retenção de Experiência"
        },
        "Commands": {
            "title": "💻 Comandos e Permissões",
            "desc": "Árvore de comandos Brigadier sob `/keepgear`. Comandos de status são públicos; alterações exigem nível de operador 2."
        },
        "Durability": {
            "title": "💔 Durabilidade e Penalidades",
            "desc": "Ao morrer, os itens preservados sofrem desgaste: Penalidade Base (10%) + Peso por nível de encantamento (0.1%). Se o dano atingir a durabilidade máxima, o item quebra permanentemente.",
            "echo_desc": "Carregar um fragmento de eco (Echo Shard) consome 1 unidade na morte e neutraliza totalmente o desgaste de durabilidade."
        }
    },
    "ja_jp": {
        "lang_name": "🇯🇵 日本語 (Japanese)",
        "sidebar_home": "🏠 中央ポータル (Home)",
        "sidebar_config": "⚙️ 設定ガイド (Configuration)",
        "sidebar_cmds": "💻 コマンドと権限 (Commands)",
        "sidebar_dur": "💔 耐久度とペナルティ (Durability)",
        "Home": {
            "title": "🛡️ Vanilla Outsider: Keep Gear 公式ウィキ",
            "desc": "Minecraft **26.3**（Java 25, Fabric Loader）向け装備保持モッド **Keep Gear** の公式ドキュメントポータルへようこそ。死亡時に耐久度ゲージを持つアイテム（防具、武器、道具、盾、エリトラ）を保持し、設定可能な耐久値ペナルティと経験値保持を適用します。",
            "nav_config": "vanilla-outsider-keep-gear.json 設定ガイドとMod Menu連携",
            "nav_cmds": "/keepgear コマンド一覧と管理者権限仕様",
            "nav_penalties": "耐久度消耗計算式、エンチャント加算、残響の欠片保険",
            "nav_arch": "Mixin実装アーキテクチャとパケット同期仕様"
        },
        "Configuration": {
            "title": "⚙️ 設定ガイド",
            "desc": "設定ファイルは `.minecraft/config/vanilla-outsider-keep-gear.json` に保存されます。YACL および Mod Menu を通じてゲーム内設定が可能です。",
            "cat_title": "🎒 アイテム保持カテゴリ",
            "wear_title": "💔 耐久値消耗ペナルティ",
            "xp_title": "✨ 経験値保持設定"
        },
        "Commands": {
            "title": "💻 コマンドと権限",
            "desc": "Keep Gear は `/keepgear` 配下に Brigadier コマンドツリーを展開します。ステータス確認は全プレイヤー利用可能で、設定変更は権限レベル2（OP）が必要です。"
        },
        "Durability": {
            "title": "💔 耐久度とペナルティ計算",
            "desc": "死亡時、保持されたアイテムは基本ペナルティ（10%）＋エンチャント総レベルごとの加算（0.1%）の消耗を受けます。消耗量が最大耐久値に達した場合、アイテムは完全消滅します。",
            "echo_desc": "インベントリに残響の欠片（Echo Shard）を所持している場合、死亡時に1個消費され、すべての耐久度消耗を完全に無効化します。"
        }
    },
    "id_id": {
        "lang_name": "🇮🇩 Bahasa Indonesia (Indonesian)",
        "sidebar_home": "🏠 Portal Pusat (Home)",
        "sidebar_config": "⚙️ Panduan Konfigurasi (Configuration)",
        "sidebar_cmds": "💻 Perintah & Perizinan (Commands)",
        "sidebar_dur": "💔 Durabilitas & Penalti (Durability)",
        "Home": {
            "title": "🛡️ Dokumentasi Vanilla Outsider: Keep Gear",
            "desc": "Selamat datang di portal dokumentasi resmi **Keep Gear** untuk Minecraft **26.3** (Java 25, Fabric). Mod ini menjaga item yang memiliki bilah durabilitas (armor, senjata, alat, perisai, elytra) saat pemain mati, dengan penalti keausan durabilitas dan penyimpanan XP yang dapat disesuaikan.",
            "nav_config": "Panduan konfigurasi berkas vanilla-outsider-keep-gear.json",
            "nav_cmds": "Daftar perintah /keepgear dan perizinan gamemaster",
            "nav_penalties": "Formula keausan durabilitas, bobot sihir, dan asuransi Echo Shard",
            "nav_arch": "Arsitektur Mixin, jembatan pemain, dan sinkronisasi jaringan"
        },
        "Configuration": {
            "title": "⚙️ Panduan Konfigurasi",
            "desc": "Berkas konfigurasi disimpan dalam format JSON di `.minecraft/config/vanilla-outsider-keep-gear.json`. Mendukung pemuatan ulang instan tanpa memulai ulang server.",
            "cat_title": "🎒 Kategori Penyimpanan Item",
            "wear_title": "💔 Penalti Keausan Durabilitas",
            "xp_title": "✨ Penyimpanan Pengalaman (XP)"
        },
        "Commands": {
            "title": "💻 Perintah & Perizinan",
            "desc": "Keep Gear menyediakan rangkaian perintah Brigadier di bawah `/keepgear`. Pengecekan status terbuka untuk semua pemain, sedangkan pengaturan konfigurasi memerlukan izin OP level 2."
        },
        "Durability": {
            "title": "💔 Durabilitas & Penalti Kematian",
            "desc": "Saat mati, perlengkapan yang disimpan mengalami keausan: Penalti Dasar (10%) + tambahan per level sihir (0.1%). Jika total kerusakan mencapai batas maksimal, item akan hancur selamanya.",
            "echo_desc": "Menyimpan Echo Shard di inventaris akan mengonsumsi 1 pecahan saat mati untuk membatalkan seluruh keausan durabilitas secara otomatis."
        }
    },
    "ko_kr": {
        "lang_name": "🇰🇷 한국어 (Korean)",
        "sidebar_home": "🏠 중앙 포털 (Home)",
        "sidebar_config": "⚙️ 구성 가이드 (Configuration)",
        "sidebar_cmds": "💻 명령어 및 권한 (Commands)",
        "sidebar_dur": "💔 내구도 및 페널티 (Durability)",
        "Home": {
            "title": "🛡️ Vanilla Outsider: Keep Gear 위키",
            "desc": "마인크래프트 **26.3**(Java 25, Fabric Loader)용 고성능 장비 보존 모드 **Keep Gear** 공식 문서입니다. 사망 시 내구도 바가 있는 아이템(갑옷, 무기, 도구, 방패, 겉날개)을 보존하며, 설정 가능한 내구도 마모 페널티 및 경험치 보존 기능을 제공합니다.",
            "nav_config": "vanilla-outsider-keep-gear.json 구성 가이드 및 GUI 설정",
            "nav_cmds": "/keepgear 명령어 목록 및 권한 등급 체계",
            "nav_penalties": "내구도 마모 공식, 마법 부여 가중치, 메아리 조각 보험",
            "nav_arch": "Mixin 아키텍처, 네트워크 패킷 동기화 및 안전 폴백"
        },
        "Configuration": {
            "title": "⚙️ 구성 가이드",
            "desc": "설정 파일은 `.minecraft/config/vanilla-outsider-keep-gear.json`에 저장되며, Mod Menu 및 YACL을 통한 인게임 설정 화면을 지원합니다.",
            "cat_title": "🎒 아이템 보존 범주",
            "wear_title": "💔 내구도 마모 페널티",
            "xp_title": "✨ 경험치(XP) 보존 설정"
        },
        "Commands": {
            "title": "💻 명령어 및 권한",
            "desc": "Keep Gear는 `/keepgear` 아래에 완벽한 Brigadier 명령어 트리를 등록합니다. 상태 조회는 모든 플레이어가 가능하며, 설정 변경 및 리로드는 OP 레벨 2가 필요합니다."
        },
        "Durability": {
            "title": "💔 내구도 마모 및 페널티",
            "desc": "사망 시 보존된 장비는 기본 페널티(10%) + 마법 부여 레벨당 가중치(0.1%)의 내구도 마모를 겪습니다. 누적 피해가 최대 내구도를 초과하면 아이템은 완전히 파괴됩니다.",
            "echo_desc": "인벤토리에 메아리 조각(Echo Shard)을 보유한 경우 사망 시 1개가 소모되어 모든 장비의 내구도 마모를 완전히 상쇄합니다."
        }
    }
}

for lang_code, lang_data in LOCALES.items():
    # 1. Localized Home
    PAGES[f"{lang_code}-Home.md"] = f"""# {lang_data['Home']['title']}

{SWITCHBOARD_MD}

{lang_data['Home']['desc']}

---

## 🧭 {lang_data['lang_name']}

| Section | Link |
| :--- | :--- |
| **{lang_data['Configuration']['title']}** | [[{lang_data['Configuration']['title']}|{lang_code}-Configuration]] |
| **{lang_data['Commands']['title']}** | [[{lang_data['Commands']['title']}|{lang_code}-Commands-and-Permissions]] |
| **{lang_data['Durability']['title']}** | [[{lang_data['Durability']['title']}|{lang_code}-Durability-and-Penalties]] |

---

### 🌐 Global Navigation
- [[English Documentation Portal|Home]]
- [[Configuration Guide|Configuration]]
- [[Commands & Permissions|Commands-and-Permissions]]
- [[Durability Degradation & Penalties|Durability-and-Penalties]]
- [[Architecture & Mixins|Architecture-and-Mixins]]
- [[Version Compatibility Matrix|Version-Compatibility]]
"""

    # 2. Localized Configuration
    PAGES[f"{lang_code}-Configuration.md"] = f"""# {lang_data['Configuration']['title']}

{SWITCHBOARD_MD}

{lang_data['Configuration']['desc']}

---

## 📋 {lang_data['Configuration']['cat_title']}

| Option | Type | Default | Description |
| :--- | :--- | :--- | :--- |
| `enabled` | `boolean` | `true` | Master switch |
| `keepArmor` | `boolean` | `true` | Helmets, Chestplates, Leggings, Boots |
| `keepWeapons` | `boolean` | `true` | Swords, Bows, Crossbows, Tridents, Maces |
| `keepTools` | `boolean` | `true` | Pickaxes, Axes, Shovels, Hoes, Shears, Fishing Rods |
| `keepShields` | `boolean` | `true` | Shields (`DataComponents.BLOCKS_ATTACKS`) |
| `keepElytra` | `boolean` | `true` | Elytra (`minecraft:elytra`) |
| `keepContainers` | `boolean` | `false` | Shulker Boxes, Bundles, Backpacks |
| `containerDropContents` | `boolean` | `false` | Drop contents, keep empty container |
| `keepConsumables` | `boolean` | `false` | Food, Potions |
| `keepResources` | `boolean` | `false` | Diamonds, Iron, Building Blocks |

---

## 💔 {lang_data['Configuration']['wear_title']}

| Option | Type | Default | Formula Impact |
| :--- | :--- | :--- | :--- |
| `penaltyEnabled` | `boolean` | `true` | Enable/Disable durability wear |
| `penaltyPercent` | `double` | `10.0` | Base wear percentage |
| `enchantmentPenaltyEnabled` | `boolean` | `true` | Enable enchantment weight |
| `enchantmentPenaltyValue` | `double` | `0.1` | Weight per total enchantment level (%) |

---

## ✨ {lang_data['Configuration']['xp_title']}

| Option | Type | Default | Description |
| :--- | :--- | :--- | :--- |
| `xpEnabled` | `boolean` | `true` | Retain experience upon death |
| `xpPercent` | `int` | `20` | Percentage of XP preserved (0-100) |
| `xpDropRemaining` | `boolean` | `true` | Drop remaining XP as orbs |
| `useEchoShard` | `boolean` | `true` | Consume 1 Echo Shard to negate wear |

---

### 🔗 Related Documents
- [[{lang_data['Home']['title']}|{lang_code}-Home]]
- [[{lang_data['Commands']['title']}|{lang_code}-Commands-and-Permissions]]
- [[{lang_data['Durability']['title']}|{lang_code}-Durability-and-Penalties]]
- [[English Master Configuration|Configuration]]
"""

    # 3. Localized Commands
    PAGES[f"{lang_code}-Commands-and-Permissions.md"] = f"""# {lang_data['Commands']['title']}

{SWITCHBOARD_MD}

{lang_data['Commands']['desc']}

---

## 📜 Commands Reference

| Command | Level | Description |
| :--- | :--- | :--- |
| `/keepgear` | `0` (All) | Display current mod status and preservation settings |
| `/keepgear status` | `0` (All) | Alias for `/keepgear` |
| `/keepgear help` | `0` (All) | Display command usage guide |
| `/keepgear reload` | `2` (OP) | Reload configuration from disk |
| `/keepgear reset` | `2` (OP) | Reset configuration to factory defaults |
| `/keepgear set enabled <true\\|false>` | `2` (OP) | Toggle master switch |
| `/keepgear set penaltyPercent <value>` | `2` (OP) | Set base durability wear percentage |
| `/keepgear set keepArmor <true\\|false>` | `2` (OP) | Toggle armor preservation |
| `/keepgear set keepWeapons <true\\|false>` | `2` (OP) | Toggle weapons preservation |
| `/keepgear set keepTools <true\\|false>` | `2` (OP) | Toggle tools preservation |
| `/keepgear set keepContainers <true\\|false>`| `2` (OP) | Toggle container preservation |
| `/keepgear set useEchoShard <true\\|false>` | `2` (OP) | Toggle Echo Shard insurance |

---

### 🔗 Related Documents
- [[{lang_data['Home']['title']}|{lang_code}-Home]]
- [[{lang_data['Configuration']['title']}|{lang_code}-Configuration]]
- [[{lang_data['Durability']['title']}|{lang_code}-Durability-and-Penalties]]
- [[English Master Commands|Commands-and-Permissions]]
"""

    # 4. Localized Durability
    PAGES[f"{lang_code}-Durability-and-Penalties.md"] = f"""# {lang_data['Durability']['title']}

{SWITCHBOARD_MD}

{lang_data['Durability']['desc']}

---

## 🧮 Wear Penalty Formula

$$\\text{{Total Wear \\%}} = \\text{{Base Penalty (10.0\\%)}} + (\\sum \\text{{Enchantment Levels}} \\times 0.1\\%)$$

$$\\text{{Damage Added}} = \\max\\left(1, \\text{{round}}\\left(\\frac{{\\text{{Total Wear \\%}}}}{{100}} \\times \\text{{Max Durability}}\\right)\\right)$$

> ⚠️ **Lethal Wear**: If the calculated damage exceeds the item's remaining durability, the item breaks completely and disappears.

---

## 🔮 Echo Shard Insurance

{lang_data['Durability']['echo_desc']}

* Consumes exactly 1 `minecraft:echo_shard` from main inventory.
* Protects all items across equipment and inventory slots.
* Displays custom HUD notification and chime sound cue upon respawn.

---

### 🔗 Related Documents
- [[{lang_data['Home']['title']}|{lang_code}-Home]]
- [[{lang_data['Configuration']['title']}|{lang_code}-Configuration]]
- [[{lang_data['Commands']['title']}|{lang_code}-Commands-and-Permissions]]
- [[English Master Durability|Durability-and-Penalties]]
"""

# ==============================================================================
# 3. SIDEBAR GENERATION
# ==============================================================================

sidebar_sections = [
    "### 🛡️ [[Keep Gear Portal|Home]]",
    "- [[Configuration Guide|Configuration]]",
    "- [[Commands & Permissions|Commands-and-Permissions]]",
    "- [[Durability & Penalties|Durability-and-Penalties]]",
    "- [[Architecture & Mixins|Architecture-and-Mixins]]",
    "- [[Version Compatibility|Version-Compatibility]]",
    "",
    "---",
    "",
    "### 🌐 Global Demographic Languages",
    ""
]

for code, data in LOCALES.items():
    sidebar_sections.append(f"<details>")
    sidebar_sections.append(f"<summary><b>{data['lang_name']}</b></summary>")
    sidebar_sections.append("")
    sidebar_sections.append(f"- [[{data['sidebar_home']}|{code}-Home]]")
    sidebar_sections.append(f"- [[{data['sidebar_config']}|{code}-Configuration]]")
    sidebar_sections.append(f"- [[{data['sidebar_cmds']}|{code}-Commands-and-Permissions]]")
    sidebar_sections.append(f"- [[{data['sidebar_dur']}|{code}-Durability-and-Penalties]]")
    sidebar_sections.append("")
    sidebar_sections.append("</details>")
    sidebar_sections.append("")

PAGES["_Sidebar.md"] = "\n".join(sidebar_sections)
PAGES["_Footer.md"] = FOOTER_CONTENT

# ==============================================================================
# 4. WRITE, VALIDATE, AND MIRROR
# ==============================================================================

def write_and_verify():
    print(f"[INFO] Writing {len(PAGES)} wiki pages to {WIKI_DIR}...")
    WIKI_DIR.mkdir(parents=True, exist_ok=True)
    MIRROR_DIR.mkdir(parents=True, exist_ok=True)

    # Clean existing non-hidden files in WIKI_DIR
    for item in WIKI_DIR.iterdir():
        if item.is_file() and item.name != ".git":
            item.unlink()

    for filename, content in PAGES.items():
        filepath = WIKI_DIR / filename
        filepath.write_text(content.strip() + "\n", encoding="utf-8")

    print(f"[SUCCESS] Wrote {len(PAGES)} files to .wiki/")

    # Link verification
    print("[INFO] Running wikilink integrity audit...")
    existing_pages = {f.stem for f in WIKI_DIR.glob("*.md")}
    broken_links = []

    link_pattern = re.compile(r'\[\[([^\]\|]+)(?:\|([^\]]+))?\]\]')

    for filepath in WIKI_DIR.glob("*.md"):
        text = filepath.read_text(encoding="utf-8")
        matches = link_pattern.findall(text)
        for target, link_alias in matches:
            # GitHub wikilink syntax: [[Target]] or [[Title|Target]]
            target_page = link_alias.strip() if link_alias else target.strip()
            if target_page not in existing_pages and target.strip() in existing_pages:
                target_page = target.strip()

            if target_page not in existing_pages:
                broken_links.append((filepath.name, target, link_alias, target_page))

    if broken_links:
        print(f"[ERROR] Found {len(broken_links)} broken wikilinks:")
        for file, target, alias, resolved in broken_links:
            print(f"  In {file}: [[{target}|{alias}]] -> '{resolved}' not found")
        raise RuntimeError("Wikilink validation failed!")
    else:
        print(f"[SUCCESS] Zero broken wikilinks across {len(PAGES)} files!")

    # Mirroring to Wiki/
    print(f"[INFO] Mirroring files to {MIRROR_DIR}...")
    for item in MIRROR_DIR.iterdir():
        if item.is_file() and item.name not in ["KEEP_GEAR_REFERENCE.md", "brainstroming.md", "Advanced-Penalties.md"]:
            item.unlink()

    for filename, content in PAGES.items():
        dest = MIRROR_DIR / filename
        dest.write_text(content.strip() + "\n", encoding="utf-8")

    print(f"[SUCCESS] Mirrored {len(PAGES)} files to Wiki/")

if __name__ == "__main__":
    write_and_verify()
