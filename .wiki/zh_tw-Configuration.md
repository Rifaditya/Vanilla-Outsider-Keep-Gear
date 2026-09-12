# ⚙️ 設定指南

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

本模組透過 Google Gson 儲存於 `.minecraft/config/vanilla-outsider-keep-gear.json`，支援客戶端與伺服器端動態熱重載。

---

## 📋 🎒 物品保留類別

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

## 💔 💔 耐久度磨損懲罰

| Option | Type | Default | Formula Impact |
| :--- | :--- | :--- | :--- |
| `penaltyEnabled` | `boolean` | `true` | Enable/Disable durability wear |
| `penaltyPercent` | `double` | `10.0` | Base wear percentage |
| `enchantmentPenaltyEnabled` | `boolean` | `true` | Enable enchantment weight |
| `enchantmentPenaltyValue` | `double` | `0.1` | Weight per total enchantment level (%) |

---

## ✨ ✨ 經驗保留設定

| Option | Type | Default | Description |
| :--- | :--- | :--- | :--- |
| `xpEnabled` | `boolean` | `true` | Retain experience upon death |
| `xpPercent` | `int` | `20` | Percentage of XP preserved (0-100) |
| `xpDropRemaining` | `boolean` | `true` | Drop remaining XP as orbs |
| `useEchoShard` | `boolean` | `true` | Consume 1 Echo Shard to negate wear |

---

### 🔗 Related Documents
- [[🛡️ 保持裝備 (Keep Gear) 百科|zh_tw-Home]]
- [[💻 指令與權限|zh_tw-Commands-and-Permissions]]
- [[💔 耐久磨損與懲罰機制|zh_tw-Durability-and-Penalties]]
- [[English Master Configuration|Configuration]]
