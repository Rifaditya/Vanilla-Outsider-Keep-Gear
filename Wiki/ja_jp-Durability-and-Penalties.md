# 💔 耐久度とペナルティ計算

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

死亡時、保持されたアイテムは基本ペナルティ（10%）＋エンチャント総レベルごとの加算（0.1%）の消耗を受けます。消耗量が最大耐久値に達した場合、アイテムは完全消滅します。

---

## 🧮 Wear Penalty Formula

$$\text{Total Wear \%} = \text{Base Penalty (10.0\%)} + (\sum \text{Enchantment Levels} \times 0.1\%)$$

$$\text{Damage Added} = \max\left(1, \text{round}\left(\frac{\text{Total Wear \%}}{100} \times \text{Max Durability}\right)\right)$$

> ⚠️ **Lethal Wear**: If the calculated damage exceeds the item's remaining durability, the item breaks completely and disappears.

---

## 🔮 Echo Shard Insurance

インベントリに残響の欠片（Echo Shard）を所持している場合、死亡時に1個消費され、すべての耐久度消耗を完全に無効化します。

* Consumes exactly 1 `minecraft:echo_shard` from main inventory.
* Protects all items across equipment and inventory slots.
* Displays custom HUD notification and chime sound cue upon respawn.

---

### 🔗 Related Documents
- [[🛡️ Vanilla Outsider: Keep Gear 公式ウィキ|ja_jp-Home]]
- [[⚙️ 設定ガイド|ja_jp-Configuration]]
- [[💻 コマンドと権限|ja_jp-Commands-and-Permissions]]
- [[English Master Durability|Durability-and-Penalties]]
