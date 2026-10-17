# 🎛️ Master Release Queue: Vanilla Outsider — Keep Gear

> **Mod Project Master Ground-Truth Document**  
> *Last Synchronized: 2026-10-11*  
> **Modrinth ID**: `keep-gear` (`keep-gear`) | **CurseForge ID**: *(Unregistered)* (`keep-gear`) | **Lead SemVer**: `1.2.5`

---

## 📊 Multi-Version Release Matrix & Queue Status

| Target MC | Generational Era | Live on Platforms | Next Queued Version | Status & Cadence Action | Feature Highlights / Notes |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **MC 26.3** | Modern Lead | — | `1.2.5+26.3` | 🚀 **Ready for Release** | Complete Keep Gear suite: durability preservation, degradation mechanics, Echo Shard insurance, `/keepgear` Brigadier command suite & optional Trinkets compatibility. |

---

## 🏛️ Project Operating Rules & Architectural Invariants

1. **📜 Subproject Changelog & Queue Segregation Law**:
   - Each version anchor maintains its own dedicated `CHANGELOG.md` and `RELEASE_QUEUE.md` tracking solely that Minecraft version anchor with Strict Version Anchor Exclusivity.

2. **🔢 Multi-Era Anchor Parity Law ("1 Update Across All Anchors")**:
   - Modern Sovereign (`26.3`/`26.x`) baseline leads, followed immediately by era-adapted ports across active anchors.

3. **📅 Daily Update Guard**:
   - Strict maximum of 1 release per day per targeted Minecraft version anchor across Modrinth and CurseForge.

4. **📦 4-Point Distribution Verification**:
   - Local Archive (`Archive Jar of all versions/MC <version>/`)
   - Central Hub Archive (`minecraft-mod-release-hub/archives/Keep Gear/`)
   - External Vault (`D:/Minecraft Mod Archive Jars/Keep Gear/`)
   - Launcher Test Profiles

---

## 🛠️ Dedicated Subproject Release Queues

- [MC 26.3 Release Queue](file:///E:/Minecraft%20Project/Vanilla%20Outsider%20Collections/Keep%20Gear/RELEASE_QUEUE.md)

---

## 🛠️ CLI Publisher Commands for Keep Gear

```powershell
# 1. Check current status across all targeted Minecraft versions
python ".agents/skills/platform-publisher/scripts/platform_publisher.py" --mod "Keep Gear" --status

# 2. Publish next sequential batch across all active versions
python ".agents/skills/platform-publisher/scripts/platform_publisher.py" --mod "Keep Gear" --publish-next --yes
```
