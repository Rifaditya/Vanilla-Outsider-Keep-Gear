# ⚙️ Panduan Konfigurasi

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Berkas konfigurasi disimpan dalam format JSON di `.minecraft/config/vanilla-outsider-keep-gear.json`. Mendukung pemuatan ulang instan tanpa memulai ulang server.

---

## 📋 🎒 Kategori Penyimpanan Item

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

## 💔 💔 Penalti Keausan Durabilitas

| Option | Type | Default | Formula Impact |
| :--- | :--- | :--- | :--- |
| `penaltyEnabled` | `boolean` | `true` | Enable/Disable durability wear |
| `penaltyPercent` | `double` | `10.0` | Base wear percentage |
| `enchantmentPenaltyEnabled` | `boolean` | `true` | Enable enchantment weight |
| `enchantmentPenaltyValue` | `double` | `0.1` | Weight per total enchantment level (%) |

---

## ✨ ✨ Penyimpanan Pengalaman (XP)

| Option | Type | Default | Description |
| :--- | :--- | :--- | :--- |
| `xpEnabled` | `boolean` | `true` | Retain experience upon death |
| `xpPercent` | `int` | `20` | Percentage of XP preserved (0-100) |
| `xpDropRemaining` | `boolean` | `true` | Drop remaining XP as orbs |
| `useEchoShard` | `boolean` | `true` | Consume 1 Echo Shard to negate wear |

---

### 🔗 Related Documents
- [[🛡️ Dokumentasi Vanilla Outsider: Keep Gear|id_id-Home]]
- [[💻 Perintah & Perizinan|id_id-Commands-and-Permissions]]
- [[💔 Durabilitas & Penalti Kematian|id_id-Durability-and-Penalties]]
- [[English Master Configuration|Configuration]]
