# 📌 Keep Gear Backlog

This file tracks planned features, technical refinements, performance optimizations, and deferred bug fixes for **Keep Gear**.

---

## 📊 Backlog Summary

| ID | Category | Title | Priority | Target Version | Status |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `[BL-KG-001]` | `[TECH_DEBT]` | Update Keep Gear MC 26.x to use Dasik Library MC 26.x | `[MEDIUM]` | `26.x` | `✅ RESOLVED` |

---

## 🏷 Legend & Status Tags
- **Categories**: `[FEATURE]`, `[REFINEMENT]`, `[BUGFIX]`, `[PERF]`, `[TECH_DEBT]`
- **Priorities**: `[HIGH]` (Important logic fix/enhancement), `[MEDIUM]` (Quality of life), `[LOW]` (Minor polish)
- **Statuses**: `📌 DEFERRED` (Queued for future work), `🚧 IN_PROGRESS` (Active development), `✅ RESOLVED` (Implemented and verified)

---

## 📝 Detailed Backlog Entries

### [BL-KG-001] Update Keep Gear MC 26.x to use Dasik Library MC 26.x
- **Category**: `[TECH_DEBT]`
- **Priority**: `[MEDIUM]`
- **Status**: `✅ RESOLVED`
- **Target Component(s)**: `build.gradle`, `fabric.mod.json`, dynamic gamerules, creator support
- **Date Added**: 2026-09-24

#### ❓ Problem / Context
Keep Gear does not declare `dasik-library` as a runtime dependency.

#### 💡 Proposed Solution & Technical Specifications
- Add `dasik-library` dependency in `build.gradle` and `fabric.mod.json`.
- Wire `net.dasik.social.api.*` for dynamic gamerules and creator support links.

#### 🧪 Verification & Acceptance Criteria
- [x] `./gradlew check` / test suite passes.
- [x] Mod compiles cleanly with `./gradlew build`.
- [x] Built JAR is triple-archived (4-point distribution).
