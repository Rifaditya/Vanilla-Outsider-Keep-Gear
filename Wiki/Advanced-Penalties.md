# Advanced Penalties & Custom Rules

For modpack creators and power users, **Keep Gear** (v1.1+) offers a powerful granular penalty system. You can define different durability losses and XP retention rates based on **how** the player died.

---

## ⚠️ Explicit Causes (Convenience Config)

These common death types have their own dedicated sliders in the Mod Menu config under **"Advanced Penalties"**.

| Cause | Durability Default | XP Default | Rationale |
| :--- | :--- | :--- | :--- |
| **Lava** | `5.0%` | `0.0%` | Lava is destructive. You might save your gear, but your XP burns away. |
| **Fall** | `5.0%` | `20.0%` | Gravity hurts the body (armor), but not the soul (XP). |
| **Explosion**| `10.0%`| `0.0%` | Explosions shatter equipment heavily. |
| **Void** | `100.0%`| `0.0%` | **The Void consumes all.** By default, items BREAK (100% damage) and XP is lost. |

> [!NOTE]
> By default, these toggles are **OFF**. When OFF, they use the Global Settings (usually 1.2% durability, 20% XP). You must enable them individually to activate these overrides.

---

## 🛠️ Custom Penalty Map (Power Mode)

The **Custom Penalty Rules** list allows you to define rules for **ANY** death message ID in the game, including those from other mods (e.g., `magic`, `wither`, `radiation`, `dehydration`).

### Syntax

The format for each entry string is:
`damage_source_id:durability_loss_percent:xp_retention_percent`

* **damage_source_id:** The registry ID of the damage source (see below).
* **durability_loss_percent:** 0.0 to 100.0.
* **xp_retention_percent:** 0.0 to 100.0.

### Examples

| Entry String | Explanation |
| :--- | :--- |
| `drown:20.0:50.0` | **Drowning:** Gear takes a heavy **20%** hit (water damage?), but you keep **50%** of your XP. |
| `starve:5.0:100.0` | **Starvation:** Gear takes minimal **5%** damage, and you keep **100%** XP (starving shouldn't make you forget things!). |
| `wither:50.0:0.0` | **Wither:** The decay rots your gear (**50%** loss) and destroys your soul (**0%** XP). |
| `sweetBerryBush:0.0:100.0` | **Berries:** Embarrassing, but harmless. No penalty. |

### Finding Damage IDs

To find the ID of a death source:

1. Die to it.
2. Look at the console/logs or enable debug mode.
3. Common Vanilla IDs: `inFire`, `lightningBolt`, `onFire`, `lava`, `hotFloor`, `inWall`, `cramming`, `drown`, `starve`, `cactus`, `fall`, `flyIntoWall`, `outOfWorld` (Void), `generic`, `magic`, `wither`, `dragonBreath`, `dryout`, `sweetBerryBush`, `freeze`, `stalagmite`.

---

## 🧮 Priority Logic

When a player dies, the mod calculates the penalty in this order:

1. **Custom Map Rule:** Does the death ID match an entry in your custom list?
    * **YES:** Use the values defined there. **STOP.**
    * **NO:** Continue.
2. **Explicit Toggle:** Is it Lava, Fall, Explosion, or Void, AND is that specific toggle enabled?
    * **YES:** Use the sliders for that cause. **STOP.**
    * **NO:** Continue.
3. **Global Settings:** Use the base `Penalty Percent` and `XP Percent Kept`.

---

## 🔮 Curses

### Curse of Binding

Normally, items with *Curse of Binding* cannot be removed from armor slots. Even on death, standard keep-inventory mods keep them stuck to you.
**Keep Gear** adds a **"Drop Curse of Binding"** option.

* **If Enabled:** Any item with Curse of Binding is **DROPPED** on the ground instead of kept. This is the only way to get rid of them without breaking them!

### Curse of Vanishing

**Keep Gear** respects Vanilla logic. If an item has *Curse of Vanishing*, it will disappear on death, regardless of your settings.
