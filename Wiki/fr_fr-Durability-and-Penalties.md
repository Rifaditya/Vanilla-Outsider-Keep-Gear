# 💔 Durabilité et Pénalités

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

À la mort, l'équipement conservé subit une usure : Pénalité de base (10%) + Poids d'enchantement (0.1% par niveau). Si les dégâts dépassent le maximum, l'objet est détruit.

---

## 🧮 Wear Penalty Formula

$$\text{Total Wear \%} = \text{Base Penalty (10.0\%)} + (\sum \text{Enchantment Levels} \times 0.1\%)$$

$$\text{Damage Added} = \max\left(1, \text{round}\left(\frac{\text{Total Wear \%}}{100} \times \text{Max Durability}\right)\right)$$

> ⚠️ **Lethal Wear**: If the calculated damage exceeds the item's remaining durability, the item breaks completely and disappears.

---

## 🔮 Echo Shard Insurance

Posséder un éclat d'écho (Echo Shard) dans l'inventaire consomme 1 éclat à la mort et annule l'intégralité de l'usure de durabilité.

* Consumes exactly 1 `minecraft:echo_shard` from main inventory.
* Protects all items across equipment and inventory slots.
* Displays custom HUD notification and chime sound cue upon respawn.

---

### 🔗 Related Documents
- [[🛡️ Wiki Vanilla Outsider: Keep Gear|fr_fr-Home]]
- [[⚙️ Guide de Configuration|fr_fr-Configuration]]
- [[💻 Commandes et Permissions|fr_fr-Commands-and-Permissions]]
- [[English Master Durability|Durability-and-Penalties]]
