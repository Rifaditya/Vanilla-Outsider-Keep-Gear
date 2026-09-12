# 💔 Haltbarkeit & Strafen

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Beim Tod erleiden behaltene Gegenstände Abnutzung: Basisstrafe (10%) + Zusatzabzug pro Verzauberungsstufe (0.1%). Bei Überschreiten der Maximalhaltbarkeit zerbricht das Item vollständig.

---

## 🧮 Wear Penalty Formula

$$\text{Total Wear \%} = \text{Base Penalty (10.0\%)} + (\sum \text{Enchantment Levels} \times 0.1\%)$$

$$\text{Damage Added} = \max\left(1, \text{round}\left(\frac{\text{Total Wear \%}}{100} \times \text{Max Durability}\right)\right)$$

> ⚠️ **Lethal Wear**: If the calculated damage exceeds the item's remaining durability, the item breaks completely and disappears.

---

## 🔮 Echo Shard Insurance

Eine Echoscherbe (Echo Shard) im Inventar schützt deine Ausrüstung: Beim Tod wird 1 Scherbe verbraucht und jeglicher Haltbarkeitsverlust abgewendet.

* Consumes exactly 1 `minecraft:echo_shard` from main inventory.
* Protects all items across equipment and inventory slots.
* Displays custom HUD notification and chime sound cue upon respawn.

---

### 🔗 Related Documents
- [[🛡️ Vanilla Outsider: Keep Gear Wiki|de_de-Home]]
- [[⚙️ Konfigurationshandbuch|de_de-Configuration]]
- [[💻 Befehle & Berechtigungen|de_de-Commands-and-Permissions]]
- [[English Master Durability|Durability-and-Penalties]]
