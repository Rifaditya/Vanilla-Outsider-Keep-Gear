# ⚙️ Configuration Guide

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

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
| `voidDeath` | `"follow_mod"` \| `"drop_all"` | `"follow_mod"` | Out-of-world death behavior. `"drop_all"` forces complete drop on void death. |
| `pvpDeath` | `"follow_mod"` \| `"drop_all"` | `"follow_mod"` | Behavior when slain by another player. |

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
{
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
}
```
