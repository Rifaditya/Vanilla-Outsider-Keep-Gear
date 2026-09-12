# 💔 Durability & Penalties

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

To strike the balance between vanilla consequence and equipment safety, **Vanilla Outsider: Keep Gear** introduces a deterministic durability wear calculation system.

## 🧮 Durability Degradation Formula

When a player dies, each preserved item undergoes durability evaluation:

$$\text{Total Wear \%} = \text{Base Penalty \%} + (\sum \text{Enchantment Levels} \times \text{Weight Factor \%})$$

Where:
* **Base Penalty**: Default `10.0%` (Configurable via `penaltyPercent`).
* **Weight Factor**: Default `0.1%` (Configurable via `enchantmentPenaltyValue`).
* **Sum of Enchantment Levels**: Combined level of all enchantments present on the item stack.

### 📐 Applied Damage Calculation
$$\text{Damage Added} = \max\left(1, \text{round}\left(\frac{\text{Total Wear \%}}{100} \times \text{Max Durability}\right)\right)$$

$$\text{New Damage Value} = \text{Current Damage} + \text{Damage Added}$$

> [!WARNING]
> **Lethal Wear Breakage**: If $\text{New Damage Value} \ge \text{Max Durability}$, the item **breaks completely** during death and is permanently destroyed (`ItemStack.EMPTY`), preventing ghost item exploits.

---

## 📊 Concrete Calculation Examples

| Equipment Item | Enchantments | Total Levels | Base % | Enchant Weight % | Total Wear % | Max Durability | Damage Added |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **Netherite Pickaxe** | Efficiency V, Unbreaking III, Fortune III, Mending I | $5+3+3+1 = 12$ | $10.0\%$ | $12 \times 0.1\% = 1.2\%$ | **11.2%** | $2031$ | $+227$ Damage |
| **Diamond Sword** | Sharpness V, Looting III, Unbreaking III | $5+3+3 = 11$ | $10.0\%$ | $11 \times 0.1\% = 1.1\%$ | **11.1%** | $1561$ | $+173$ Damage |
| **Iron Chestplate** | None | $0$ | $10.0\%$ | $0.0\%$ | **10.0%** | $240$ | $+24$ Damage |
| **Netherite Helmet** | Protection IV, Respiration III, Aqua Affinity I, Unbreaking III, Mending I | $4+3+1+3+1 = 12$ | $10.0\%$ | $1.2\%$ | **11.2%** | $407$ | $+46$ Damage |

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
