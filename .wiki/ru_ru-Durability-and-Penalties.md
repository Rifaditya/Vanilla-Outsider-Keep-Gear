# 💔 Прочность и штрафы за смерть

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

При смерти сохраненные предметы получают износ: Базовый штраф (10%) + дополнительный процент за каждый уровень чар (0.1%). Если износ превышает прочность, предмет ломается навсегда.

---

## 🧮 Wear Penalty Formula

$$\text{Total Wear \%} = \text{Base Penalty (10.0\%)} + (\sum \text{Enchantment Levels} \times 0.1\%)$$

$$\text{Damage Added} = \max\left(1, \text{round}\left(\frac{\text{Total Wear \%}}{100} \times \text{Max Durability}\right)\right)$$

> ⚠️ **Lethal Wear**: If the calculated damage exceeds the item's remaining durability, the item breaks completely and disappears.

---

## 🔮 Echo Shard Insurance

Наличие осколка эха (Echo Shard) поглощает урон прочности: расходуется 1 осколок, предотвращая любой износ снаряжения.

* Consumes exactly 1 `minecraft:echo_shard` from main inventory.
* Protects all items across equipment and inventory slots.
* Displays custom HUD notification and chime sound cue upon respawn.

---

### 🔗 Related Documents
- [[🛡️ Keep Gear — Вики|ru_ru-Home]]
- [[⚙️ Руководство по конфигурации|ru_ru-Configuration]]
- [[💻 Команды и права доступа|ru_ru-Commands-and-Permissions]]
- [[English Master Durability|Durability-and-Penalties]]
