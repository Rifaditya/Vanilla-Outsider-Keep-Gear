# 💔 Durabilitas & Penalti Kematian

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Saat mati, perlengkapan yang disimpan mengalami keausan: Penalti Dasar (10%) + tambahan per level sihir (0.1%). Jika total kerusakan mencapai batas maksimal, item akan hancur selamanya.

---

## 🧮 Wear Penalty Formula

$$\text{Total Wear \%} = \text{Base Penalty (10.0\%)} + (\sum \text{Enchantment Levels} \times 0.1\%)$$

$$\text{Damage Added} = \max\left(1, \text{round}\left(\frac{\text{Total Wear \%}}{100} \times \text{Max Durability}\right)\right)$$

> ⚠️ **Lethal Wear**: If the calculated damage exceeds the item's remaining durability, the item breaks completely and disappears.

---

## 🔮 Echo Shard Insurance

Menyimpan Echo Shard di inventaris akan mengonsumsi 1 pecahan saat mati untuk membatalkan seluruh keausan durabilitas secara otomatis.

* Consumes exactly 1 `minecraft:echo_shard` from main inventory.
* Protects all items across equipment and inventory slots.
* Displays custom HUD notification and chime sound cue upon respawn.

---

### 🔗 Related Documents
- [[🛡️ Dokumentasi Vanilla Outsider: Keep Gear|id_id-Home]]
- [[⚙️ Panduan Konfigurasi|id_id-Configuration]]
- [[💻 Perintah & Perizinan|id_id-Commands-and-Permissions]]
- [[English Master Durability|Durability-and-Penalties]]
