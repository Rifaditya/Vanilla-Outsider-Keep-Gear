# Vanilla Outsider: Keep Gear - Design Reference (Minecraft 1.21.11 Java Edition, Fabric, Kotlin)

---

# 💀 DEATH MECHANICS OVERVIEW

This mod changes what happens when a player dies. **Items with durability are kept**, everything else is **dropped**.

---

# 🎯 CORE MECHANIC: DURABILITY-BASED CLASSIFICATION

## The Rule

> **Simple:** If an item has a durability bar → **KEEP IT**
>
> If an item has no durability → **DROP IT**

### 🔌 Automatic Mod Compatibility

Because we check for the **durability DataComponent** (not a hardcoded list), this mod automatically works with **ALL modded gear** — any mod that adds tools, weapons, or armor with durability will be protected on death. No config needed!

---

## ✅ Items That Will Be KEPT (Have Durability)

| Category | Items |
|----------|-------|
| **Armor** | Helmet, Chestplate, Leggings, Boots (all materials) |
| **Melee Weapons** | Sword, Axe, Mace |
| **Ranged Weapons** | Bow, Crossbow, Trident, Spear (1.21.11) |
| **Tools** | Pickaxe, Shovel, Hoe |
| **Shields** | Shield |
| **Special** | Elytra, Fishing Rod, Shears, Flint & Steel, Carrot on a Stick, Warped Fungus on a Stick, Brush |

---

## ❌ Items That Will Be DROPPED (No Durability)

| Category | Items |
|----------|-------|
| **Resources** | Ores, Ingots, Gems, Raw materials |
| **Blocks** | Cobblestone, Wood, Dirt, etc. |
| **Food** | All food items |
| **Mob Drops** | Leather, Bones, String, Ender Pearls, etc. |
| **Consumables** | Potions, Arrows, Fireworks, Totems |
| **Misc** | Books, Maps, Compasses (no durability), Buckets |

---

# ⚗️ ENCHANTMENT INTERACTIONS

| Enchantment | Behavior |
|-------------|----------|
| **Curse of Vanishing** | ❌ Item vanishes (respects vanilla behavior) |
| **Curse of Binding** | ✅ Item kept (has durability — curse prevents dropping, not losing on death) |
| **Mending** | ✅ Item kept (has durability) |
| **Unbreaking** | ✅ Item kept (has durability) |

> **Clarification:** Enchantments don't change the keep/drop logic — only **durability** matters. Curse of Vanishing is the exception (vanilla mechanic).

---

# ⭐ EXPERIENCE HANDLING

Players keep **20% of their XP** by default on death. Fully configurable.

| Setting | Default | Configurable |
|---------|---------|--------------|
| **Keep XP on death?** | `true` | ✅ Yes |
| **XP kept percentage** | `20%` | ✅ Yes (0-100%) |
| **Drop remaining XP?** | `true` | ✅ Yes |

### How It Works

- Player has 1000 XP → dies → respawns with **200 XP**
- Remaining **800 XP** drops at death location (can be recovered or lost)
- If `dropRemainingXp: false`, the 80% is simply lost

### Example Config

```json
{
  "experience": {
    "keepXpOnDeath": true,
    "keepPercentage": 20,
    // Drop the remaining XP at death location?
    // true = drops as orbs, false = lost forever
    "dropRemainingXp": true
  }
}
```

---

# ⚙️ CONFIG OPTIONS

## General Settings

| Setting | Default | Description |
|---------|---------|-------------|
| **Mod Enabled** | `true` | Master toggle to enable/disable the entire mod |

## ⚔️ Durability Penalty System

When a player dies and respawns with their gear, each kept item takes a **durability penalty** as a cost for cheating death.

| Setting | Default | Description |
|---------|---------|-------------|
| **Penalty Enabled** | `true` | Toggle durability penalty on/off (on by default) |
| **Penalty Amount** | `1.2%` | Percentage of max durability lost on death (by default) |
| **Configurable** | `true` | Modpack creators can adjust this value |

### How It Works

- On death, each kept item loses **1.2% of its maximum durability**
- Example: Diamond Pickaxe (1561 durability) loses ~19 durability per death
- Example: Netherite Sword (2031 durability) loses ~24 durability per death
- Items at 0 durability will **break** (not kept if already broken)

### Example Config

```json
{
  "enabled": true,
  "durabilityPenalty": {
    "enabled": true,
    "percentage": 1.2
  }
}
```

## 🚫 Item Blacklist (For Modpack Creators)

Modpack creators can add items to a blacklist — these items will **DROP on death** even if they have durability.

| Setting | Default | Description |
|---------|---------|-------------|
| **Blacklist enabled** | `true` | Enable/disable blacklist system |
| **Blacklist format** | Item IDs | `minecraft:elytra`, `somemod:item` |
| **Support item tags** | `true` | `#c:overpowered_gear` |

### Example Blacklist Config

```json
{
  "blacklist": [
    // Items with durability that should DROP on death
    "minecraft:elytra",
    "somemod:op_sword",
    "#c:modpack_drops_on_death"
  ]
}
```

### Blacklist Use Cases

- Exclude Elytra from being kept (balance)
- Exclude OP modded weapons/armor
- Use custom tags for modpack-specific rules

---

## ✅ Item Whitelist (For Modpack Creators)

Modpack creators can add items to a whitelist — these items will be **KEPT on death** even if they **don't have durability**.

| Setting | Default | Description |
|---------|---------|-------------|
| **Whitelist enabled** | `true` | Enable/disable whitelist system |
| **Whitelist format** | Item IDs | `minecraft:totem_of_undying`, `somemod:item` |
| **Support item tags** | `true` | `#c:soulbound_items` |

### Example Whitelist Config

```json
{
  "whitelist": [
    // Items WITHOUT durability that should be KEPT on death
    "minecraft:totem_of_undying",
    "minecraft:ender_pearl",
    "somemod:special_artifact",
    "#c:soulbound_items"
  ]
}
```

### Whitelist Use Cases

- Keep Totem of Undying (save your backup totem!)
- Keep special modded artifacts/quest items
- Keep Ender Pearls for emergency escapes
- Use custom tags for soulbound mechanics

## 🎮 Gamerule Interaction

| Gamerule State | Behavior | Description |
|----------------|----------|-------------|
| `keepInventory=true` | Vanilla behavior | Keep everything (mod does nothing) |
| `keepInventory=false` | Mod mechanics | Use durability-based keep system |

### Force Disable Option

| Setting | Default | Description |
|---------|---------|-------------|
| **Force Mod Off** | `false` | When `true`, disables mod even if `keepInventory=false` |

### Mod Menu Integration

This mod integrates with **Mod Menu** for in-game configuration:

- All settings accessible via Mod Menu screen
- Changes apply immediately (no restart required)
- **Server config overrides client config** (server is authoritative)

---

# 🖥️ SERVER / CLIENT CONFIG

| Priority | Description |
|----------|-------------|
| **Server** | Server config is authoritative — overrides client settings |
| **Client** | Client settings only apply in singleplayer |

> **Multiplayer:** When joining a server, the server's Keep Gear config applies. Clients cannot override.

---

# ☠️ SPECIAL DEATH TYPES

Different death causes can have different rules. By default, all follow the mod's normal behavior.

## Void Death

| Setting | Default | Options |
|---------|---------|---------|
| **Void Death Behavior** | `follow_mod` | `follow_mod`, `drop_all` |

## PvP Death (Killed by Player)

| Setting | Default | Options |
|---------|---------|---------|
| **PvP Death Behavior** | `follow_mod` | `follow_mod`, `drop_all` |

## Totem of Undying

| Setting | Behavior |
|---------|----------|
| **Totem Saves Player** | ✅ No durability penalty (player didn't actually die) |

> **Logic:** If the Totem activates, the player never truly died, so no gear penalty applies.

### Example Config

```json
{
  "specialDeaths": {
    // Void death (falling out of world)
    "voidDeath": "follow_mod",  // or "drop_all"
    
    // Killed by another player
    "pvpDeath": "follow_mod",   // or "drop_all"
    
    // Totem saves = no penalty (always true, not configurable)
  }
}
```

---

# 💀 HARDCORE MODE

| Setting | Behavior |
|---------|----------|
| **Hardcore Detected** | ❌ Mod forces OFF — drops everything |

> **Reason:** Hardcore = one life. Mods like **Hardcore Revival** handle death differently (corpse/death state). This mod stays out of the way to avoid conflicts.

---

# 🌍 DIMENSION HANDLING

All dimensions (including modded) follow the mod by default. Use a **blacklist** to disable for specific dimensions.

| Setting | Default | Description |
|---------|---------|-------------|
| **All dimensions enabled** | `true` | Mod works everywhere by default |
| **Dimension Blacklist** | `[]` | List dimensions where mod is disabled |

### Example Config

```json
{
  "dimensions": {
    // All dimensions enabled by default
    // Only add dimensions where you want to DISABLE the mod
    "blacklist": [
      // "minecraft:the_end",  // Uncomment to disable in The End
      // "somemod:dangerous_dimension"
    ]
  }
}
```

> **Modpack Creators:** Only need to blacklist dimensions you want disabled. Everything else works automatically.

---

# 🔧 BROKEN ITEMS (0 Durability)

| Condition | Behavior |
|-----------|----------|
| **Item at 0 durability** | ❌ Item is GONE (not kept) |
| **Item breaks on death** | ❌ Item is GONE |

> **Logic:** If an item is already broken, there's nothing to keep. The durability penalty cannot reduce items below 0.

---

# 🛡️ ADMIN COMMANDS & GAMERULES

## 🎮 Custom Gamerules

This mod adds its own gamerules for easy server management via `/gamerule`.

### Master Toggle

| Gamerule | Type | Default | Description |
|----------|------|---------|-------------|
| `keepGearEnabled` | Boolean | `true` | Master on/off for the mod |

### Durability Penalty

| Gamerule | Type | Default | Description |
|----------|------|---------|-------------|
| `keepGearPenaltyEnabled` | Boolean | `true` | Enable durability penalty |
| `keepGearPenaltyPercent` | Float | `1.2` | Penalty percentage (0-100) |

### XP Handling

| Gamerule | Type | Default | Description |
|----------|------|---------|-------------|
| `keepGearXpEnabled` | Boolean | `true` | Enable XP retention |
| `keepGearXpPercent` | Integer | `20` | % of XP kept (0-100) |
| `keepGearXpDropRemaining` | Boolean | `true` | Drop remaining XP as orbs |

### Special Death Types

| Gamerule | Type | Default | Options |
|----------|------|---------|---------|
| `keepGearVoidDeath` | String | `follow_mod` | `follow_mod`, `drop_all` |
| `keepGearPvpDeath` | String | `follow_mod` | `follow_mod`, `drop_all` |

### Visual Feedback

| Gamerule | Type | Default | Description |
|----------|------|---------|-------------|
| `keepGearShowMessage` | Boolean | `false` | Show death message |
| `keepGearShowParticles` | Boolean | `false` | Show respawn particles |
| `keepGearPlaySound` | Boolean | `false` | Play respawn sound |

### Example Gamerule Commands

```
/gamerule keepGearEnabled true
/gamerule keepGearEnabled false
/gamerule keepGearPenaltyPercent 2.5
/gamerule keepGearXpPercent 50
/gamerule keepGearVoidDeath drop_all
/gamerule keepGearShowMessage true
```

---

## 🛡️ Admin Commands

All commands require OP permission (level 2+).

### Main Commands

| Command | Description |
|---------|-------------|
| `/keepgear` | Show mod status summary |
| `/keepgear help` | Show all available commands |
| `/keepgear reload` | Reload config from file |
| `/keepgear status` | Show detailed mod status |

#### Examples

```
/keepgear
/keepgear help
/keepgear reload
/keepgear status
```

### Player Management

| Command | Description |
|---------|-------------|
| `/keepgear toggle <player>` | Toggle mod for specific player |
| `/keepgear enable <player>` | Enable mod for specific player |
| `/keepgear disable <player>` | Disable mod for specific player |
| `/keepgear reset <player>` | Reset player to server defaults |

#### Examples

```
# Toggle keep gear for a specific player
/keepgear toggle Steve

# Disable keep gear for player (they drop everything on death)
/keepgear disable Alex

# Enable keep gear for player
/keepgear enable Alex

# Reset player to use server default settings
/keepgear reset Steve
```

### Item Blacklist Management

| Command | Description |
|---------|-------------|
| `/keepgear blacklist list` | Show all blacklisted items |
| `/keepgear blacklist add <item>` | Add item to blacklist |
| `/keepgear blacklist remove <item>` | Remove item from blacklist |
| `/keepgear blacklist clear` | Clear entire blacklist |

#### Examples

```
# View all blacklisted items
/keepgear blacklist list

# Add elytra to blacklist (will drop on death)
/keepgear blacklist add minecraft:elytra

# Add a modded item to blacklist
/keepgear blacklist add somemod:overpowered_sword

# Add an item tag to blacklist (all items with this tag)
/keepgear blacklist add #c:drops_on_death

# Remove item from blacklist
/keepgear blacklist remove minecraft:elytra

# Clear entire blacklist
/keepgear blacklist clear
```

### Item Whitelist Management

> **Note:** Whitelist is for items **without durability** that you want to keep on death.

| Command | Description |
|---------|-------------|
| `/keepgear whitelist list` | Show all whitelisted items |
| `/keepgear whitelist add <item>` | Add item to whitelist (kept on death) |
| `/keepgear whitelist remove <item>` | Remove item from whitelist |
| `/keepgear whitelist clear` | Clear entire whitelist |

#### Examples

```
# View all whitelisted items (items without durability that are kept)
/keepgear whitelist list

# Add Totem of Undying to whitelist (backup totems kept on death)
/keepgear whitelist add minecraft:totem_of_undying

# Add Ender Pearls to whitelist
/keepgear whitelist add minecraft:ender_pearl

# Add a modded quest item to whitelist
/keepgear whitelist add questmod:sacred_artifact

# Add an item tag to whitelist (soulbound items)
/keepgear whitelist add #c:soulbound_items

# Remove item from whitelist
/keepgear whitelist remove minecraft:ender_pearl

# Clear entire whitelist
/keepgear whitelist clear
```

### Dimension Management

> **Note:** By default, keep gear is **enabled in ALL dimensions**. Use these commands to **disable** keep gear in specific dimensions.

| Command | Description |
|---------|-------------|
| `/keepgear dimension list` | Show dimensions where keep gear is DISABLED |
| `/keepgear dimension disable <dimension>` | DISABLE keep gear in dimension (items drop) |
| `/keepgear dimension enable <dimension>` | RE-ENABLE keep gear in dimension |

#### Examples

```
# View dimensions where keep gear is DISABLED
/keepgear dimension list

# DISABLE keep gear in The End (everything drops there)
/keepgear dimension disable minecraft:the_end

# DISABLE keep gear in a modded dimension
/keepgear dimension disable aether:the_aether

# RE-ENABLE keep gear in The End (items kept again)
/keepgear dimension enable minecraft:the_end
```

### Debug Commands

| Command | Description |
|---------|-------------|
| `/keepgear debug <player>` | Show player's current settings |
| `/keepgear test` | Test death simulation (no actual death) |

#### Examples

```
# Check a player's current keep gear settings
/keepgear debug Steve

# Test what would happen on death (without actually dying)
/keepgear test
```

---

## 📊 Command & Gamerule Summary

| Category | Count |
|----------|-------|
| **Gamerules** | 11 |
| **Admin Commands** | 16+ |

---

# 🎨 VISUAL & AUDIO FEEDBACK

All visual/audio feedback is **off by default** but fully configurable.

| Feature | Default | Configurable |
|---------|---------|-------------|
| **Death Message** | `false` | ✅ Yes |
| **Respawn Particles** | `false` | ✅ Yes |
| **Respawn Sound** | `false` | ✅ Yes |

### Example Config with Comments

```json
{
  "visualFeedback": {
    // Show a message when gear is saved on death
    // Set to true to display: "Your gear has been preserved!"
    "deathMessage": false,
    
    // Custom message text (only used if deathMessage is true)
    // Supports color codes: §6 = gold, §a = green, etc.
    // Default message: "§6Your gear has been preserved!"
    "deathMessageText": "§6Your gear has been preserved!",
    
    // Show soul-like particles around player on respawn
    // Creates a brief glowing effect on kept items
    "respawnParticles": false,
    
    // Play a sound effect when respawning with saved gear
    // Uses minecraft:entity.player.levelup by default
    "respawnSound": false,
    
    // Custom sound ID (only used if respawnSound is true)
    "respawnSoundId": "minecraft:entity.player.levelup"
  }
}
```

---

# 🔗 MOD COMPATIBILITY

## Trinkets Mod Integration

Trinkets has its own `keepTrinketsOnDeath` setting. This mod uses **bidirectional sync** to keep both settings linked.

### 🔄 Bidirectional Sync Behavior

Both settings act as **one unified toggle** — changing one automatically changes the other:

| Action | Result |
|--------|--------|
| **Game loads** | This mod is ON by default → Forces Trinkets to ON |
| **Player toggles THIS mod OFF** | Trinkets setting automatically turns OFF |
| **Player toggles THIS mod ON** | Trinkets setting automatically turns ON |
| **Player toggles Trinkets OFF** | This mod setting automatically turns OFF |
| **Player toggles Trinkets ON** | This mod setting automatically turns ON |

### Why Bidirectional Sync?

- **Unified experience:** Player only needs to toggle one setting
- **No confusion:** Both mods always behave the same way
- **Works from either Mod Menu:** Change from Keep Gear or Trinkets config, both update

### State Table

| Keep Gear | Trinkets | Result |
|-----------|----------|--------|
| ON | ON (synced) | ✅ All gear + trinkets with durability kept |
| OFF | OFF (synced) | ❌ Everything drops (vanilla behavior) |

> **Note:** The settings cannot be in different states — they are always synced.

## Backpacks Mod Support

| Mod | Behavior |
|-----|----------|
| **Any backpack mod** | If the backpack item has durability → kept |
| **Backpack contents** | Contents inside backpack are also kept (travels with backpack) |

---

# 📝 EXAMPLE SCENARIOS

## Scenario 1: Mining trip death

- **Inventory:** Diamond pickaxe, Iron armor, 64 diamonds, 32 iron ore, 16 coal
- **After respawn:** Diamond pickaxe + Iron armor **kept**, diamonds + ore + coal **dropped**

## Scenario 2: Combat death

- **Inventory:** Netherite sword, Shield, Bow, 64 arrows, Golden apples, Ender pearls
- **After respawn:** Sword + Shield + Bow **kept**, arrows + apples + pearls **dropped**

## Scenario 3: Elytra flight crash

- **Inventory:** Elytra, Firework rockets, Food, Building blocks
- **After respawn:** Elytra **kept**, rockets + food + blocks **dropped**

---

# 💡 ADDITIONAL NOTES

### Your Ideas & Notes
>
> *(Write any additional ideas or clarifications here)*
>
>
>

### Questions for Development
>
> *(Any questions or concerns about implementation)*
>
>
>

---

# 📋 QUICK SUMMARY

## Core Features

| Category | Decision |
|----------|----------|
| **Classification Method** | ✅ Durability-based (any item with durability = kept) |
| **Mod Compatibility** | ✅ Automatic (works with all modded gear) |
| **Broken Items (0 dur)** | ❌ Gone (not kept) |

## Penalties & XP

| Category | Decision |
|----------|----------|
| **Durability Penalty** | ✅ 1.2% per death (configurable) |
| **XP Kept** | ✅ 20% kept, 80% drops (configurable) |
| **Totem of Undying** | ✅ No penalty (didn't die) |

## Config System

| Category | Decision |
|----------|----------|
| **Config Format** | ✅ JSON + Mod Menu integration |
| **Server/Client** | ✅ Server overrides client |
| **Custom Gamerules** | ✅ `/gamerule keepGear*` commands |
| **Admin Commands** | ✅ `/keepgear` for management |

## Special Cases

| Category | Decision |
|----------|----------|
| **Void Death** | ✅ Follow mod (configurable: drop all) |
| **PvP Death** | ✅ Follow mod (configurable: drop all) |
| **Hardcore Mode** | ❌ Mod forced OFF (drops everything) |
| **Dimensions** | ✅ All enabled, blacklist only |

## Mod Compatibility

| Category | Decision |
|----------|----------|
| **Trinkets** | ✅ Bidirectional sync |
| **Backpacks** | ✅ Keep if has durability |
| **Item Blacklist** | ✅ Drop items WITH durability |
| **Item Whitelist** | ✅ Keep items WITHOUT durability |
| **Dimension Blacklist** | ✅ Disable in specific dimensions |

## Feedback & UI

| Category | Decision |
|----------|----------|
| **Visual Feedback** | ✅ All OFF by default (configurable) |
| **Death Message** | ✅ Optional |
| **Particles/Sound** | ✅ Optional |

---

*Last Updated: December 2025 | Minecraft 1.21.11 Java Edition | Fabric + Kotlin*
