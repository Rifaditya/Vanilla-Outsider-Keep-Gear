# 💻 指令与权限

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Keep Gear 提供完整的 Brigadier 指令树，注册于 `/keepgear`。信息查询指令向所有玩家开放，而配置修改与重载指令需要 2 级操作员权限（GameMaster）。

---

## 📜 Commands Reference

| Command | Level | Description |
| :--- | :--- | :--- |
| `/keepgear` | `0` (All) | Display current mod status and preservation settings |
| `/keepgear status` | `0` (All) | Alias for `/keepgear` |
| `/keepgear help` | `0` (All) | Display command usage guide |
| `/keepgear reload` | `2` (OP) | Reload configuration from disk |
| `/keepgear reset` | `2` (OP) | Reset configuration to factory defaults |
| `/keepgear set enabled <true\|false>` | `2` (OP) | Toggle master switch |
| `/keepgear set penaltyPercent <value>` | `2` (OP) | Set base durability wear percentage |
| `/keepgear set keepArmor <true\|false>` | `2` (OP) | Toggle armor preservation |
| `/keepgear set keepWeapons <true\|false>` | `2` (OP) | Toggle weapons preservation |
| `/keepgear set keepTools <true\|false>` | `2` (OP) | Toggle tools preservation |
| `/keepgear set keepContainers <true\|false>`| `2` (OP) | Toggle container preservation |
| `/keepgear set useEchoShard <true\|false>` | `2` (OP) | Toggle Echo Shard insurance |

---

### 🔗 Related Documents
- [[🛡️ 保持装备 (Keep Gear) 百科|zh_cn-Home]]
- [[⚙️ 配置指南|zh_cn-Configuration]]
- [[💔 耐久损耗与惩罚机制|zh_cn-Durability-and-Penalties]]
- [[English Master Commands|Commands-and-Permissions]]
