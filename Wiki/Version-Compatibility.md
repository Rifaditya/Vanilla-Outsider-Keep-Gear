# 🗺️ Version Compatibility & Lifecycle Matrix

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

**Vanilla Outsider: Keep Gear** adheres strictly to the **1 JAR 1 Version** development law. Each release is tailored specifically to the targeted Minecraft version's internal mappings and component APIs.

## 📊 Compatibility Matrix

| Minecraft Version | Mod Version | Build Status | Java Runtime | Fabric Loader | Fabric API | Architecture |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **Minecraft 26.3** | `1.2.4+26.3` | 🟢 Active Release | **Java 25+** | `>=0.19.3` | `0.156.1+26.3` | Pure Java 25, Brigadier, DataComponents |
| **Minecraft 1.21.1** | `1.1.0+1.21.1` | 🟡 Legacy / LTS | Java 21+ | `>=0.16.0` | `0.100.0+` | Kotlin / Fabric |
| **Minecraft 1.20.4** | `1.0.0+1.20.4` | 🔴 Deprecated | Java 17+ | `>=0.15.0` | `0.90.0+` | Legacy NBT |

---

## 🧩 Companion & Mod Compatibility

| Mod | Minimum Version | Integration Level | Behavior |
| :--- | :--- | :--- | :--- |
| **Fabric API** | `0.156.1+26.3` | **Required** | Provides command registration and lifecycle hooks. |
| **Trinkets** | `3.11.0+` | **Optional (Reflection-Safe)** | When detected, accessories equipped in Trinkets slots are preserved on death. Zero runtime crash if absent. |
| **YetAnotherConfigLib (YACL)** | `3.9.5+` | **Optional** | Renders advanced in-game configuration menu. |
| **Mod Menu** | `18.0.0-beta.1+` | **Optional** | Provides the config button inside the Mod Menu UI. |
| **Traveler's Backpack** | Latest | **Automatic** | Whitelisted container; supports safe mode and spill mode. |
| **Shulker Box / Bundles** | Vanilla 26.3 | **Native** | Whitelisted container; supports safe mode and spill mode. |
