# 💔 耐久损耗与惩罚机制

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

玩家死亡时，保留的装备将根据基础惩罚百分比和附魔权重承受耐久度磨损。若磨损值达到或超过物品最大耐久度，该物品将彻底损坏碎裂。

---

## 🧮 Wear Penalty Formula

$$\text{Total Wear \%} = \text{Base Penalty (10.0\%)} + (\sum \text{Enchantment Levels} \times 0.1\%)$$

$$\text{Damage Added} = \max\left(1, \text{round}\left(\frac{\text{Total Wear \%}}{100} \times \text{Max Durability}\right)\right)$$

> ⚠️ **Lethal Wear**: If the calculated damage exceeds the item's remaining durability, the item breaks completely and disappears.

---

## 🔮 Echo Shard Insurance

携带回响碎片（Echo Shard）时，死亡将消耗 1 枚回响碎片，完全免除所有装备的耐久度磨损，并播放专属音效提示。

* Consumes exactly 1 `minecraft:echo_shard` from main inventory.
* Protects all items across equipment and inventory slots.
* Displays custom HUD notification and chime sound cue upon respawn.

---

### 🔗 Related Documents
- [[🛡️ 保持装备 (Keep Gear) 百科|zh_cn-Home]]
- [[⚙️ 配置指南|zh_cn-Configuration]]
- [[💻 指令与权限|zh_cn-Commands-and-Permissions]]
- [[English Master Durability|Durability-and-Penalties]]
