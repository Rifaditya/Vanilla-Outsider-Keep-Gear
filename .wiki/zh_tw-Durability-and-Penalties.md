# 💔 耐久磨損與懲罰機制

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

玩家死亡時，保留的裝備將根據基礎懲罰比例與附魔等級承受耐久磨損。若損耗值超過物品最大耐久度，該裝備將徹底損毀。

---

## 🧮 Wear Penalty Formula

$$\text{Total Wear \%} = \text{Base Penalty (10.0\%)} + (\sum \text{Enchantment Levels} \times 0.1\%)$$

$$\text{Damage Added} = \max\left(1, \text{round}\left(\frac{\text{Total Wear \%}}{100} \times \text{Max Durability}\right)\right)$$

> ⚠️ **Lethal Wear**: If the calculated damage exceeds the item's remaining durability, the item breaks completely and disappears.

---

## 🔮 Echo Shard Insurance

當物品欄含有回響碎片（Echo Shard）時，死亡將消耗 1 枚碎片以完全抵消所有裝備耐久磨損，並播放專屬共鳴音效。

* Consumes exactly 1 `minecraft:echo_shard` from main inventory.
* Protects all items across equipment and inventory slots.
* Displays custom HUD notification and chime sound cue upon respawn.

---

### 🔗 Related Documents
- [[🛡️ 保持裝備 (Keep Gear) 百科|zh_tw-Home]]
- [[⚙️ 設定指南|zh_tw-Configuration]]
- [[💻 指令與權限|zh_tw-Commands-and-Permissions]]
- [[English Master Durability|Durability-and-Penalties]]
