# 💔 내구도 마모 및 페널티

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

사망 시 보존된 장비는 기본 페널티(10%) + 마법 부여 레벨당 가중치(0.1%)의 내구도 마모를 겪습니다. 누적 피해가 최대 내구도를 초과하면 아이템은 완전히 파괴됩니다.

---

## 🧮 Wear Penalty Formula

$$\text{Total Wear \%} = \text{Base Penalty (10.0\%)} + (\sum \text{Enchantment Levels} \times 0.1\%)$$

$$\text{Damage Added} = \max\left(1, \text{round}\left(\frac{\text{Total Wear \%}}{100} \times \text{Max Durability}\right)\right)$$

> ⚠️ **Lethal Wear**: If the calculated damage exceeds the item's remaining durability, the item breaks completely and disappears.

---

## 🔮 Echo Shard Insurance

인벤토리에 메아리 조각(Echo Shard)을 보유한 경우 사망 시 1개가 소모되어 모든 장비의 내구도 마모를 완전히 상쇄합니다.

* Consumes exactly 1 `minecraft:echo_shard` from main inventory.
* Protects all items across equipment and inventory slots.
* Displays custom HUD notification and chime sound cue upon respawn.

---

### 🔗 Related Documents
- [[🛡️ Vanilla Outsider: Keep Gear 위키|ko_kr-Home]]
- [[⚙️ 구성 가이드|ko_kr-Configuration]]
- [[💻 명령어 및 권한|ko_kr-Commands-and-Permissions]]
- [[English Master Durability|Durability-and-Penalties]]
