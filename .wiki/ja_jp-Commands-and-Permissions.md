# 💻 コマンドと権限

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Keep Gear は `/keepgear` 配下に Brigadier コマンドツリーを展開します。ステータス確認は全プレイヤー利用可能で、設定変更は権限レベル2（OP）が必要です。

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
- [[🛡️ Vanilla Outsider: Keep Gear 公式ウィキ|ja_jp-Home]]
- [[⚙️ 設定ガイド|ja_jp-Configuration]]
- [[💔 耐久度とペナルティ計算|ja_jp-Durability-and-Penalties]]
- [[English Master Commands|Commands-and-Permissions]]
