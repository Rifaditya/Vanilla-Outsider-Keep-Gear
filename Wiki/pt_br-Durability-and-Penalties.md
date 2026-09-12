# 💔 Durabilidade e Penalidades

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Ao morrer, os itens preservados sofrem desgaste: Penalidade Base (10%) + Peso por nível de encantamento (0.1%). Se o dano atingir a durabilidade máxima, o item quebra permanentemente.

---

## 🧮 Wear Penalty Formula

$$\text{Total Wear \%} = \text{Base Penalty (10.0\%)} + (\sum \text{Enchantment Levels} \times 0.1\%)$$

$$\text{Damage Added} = \max\left(1, \text{round}\left(\frac{\text{Total Wear \%}}{100} \times \text{Max Durability}\right)\right)$$

> ⚠️ **Lethal Wear**: If the calculated damage exceeds the item's remaining durability, the item breaks completely and disappears.

---

## 🔮 Echo Shard Insurance

Carregar um fragmento de eco (Echo Shard) consome 1 unidade na morte e neutraliza totalmente o desgaste de durabilidade.

* Consumes exactly 1 `minecraft:echo_shard` from main inventory.
* Protects all items across equipment and inventory slots.
* Displays custom HUD notification and chime sound cue upon respawn.

---

### 🔗 Related Documents
- [[🛡️ Wiki do Vanilla Outsider: Keep Gear|pt_br-Home]]
- [[⚙️ Guia de Configuração|pt_br-Configuration]]
- [[💻 Comandos e Permissões|pt_br-Commands-and-Permissions]]
- [[English Master Durability|Durability-and-Penalties]]
