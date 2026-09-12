# 💻 Commands & Permissions

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

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
