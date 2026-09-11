# Changelog

All notable changes to **Vanilla Outsider: Keep Gear** will be documented in this file.

## [1.2.1+26.3]
### Added
- Pure Java 25 configuration data model `KeepGearConfig` supporting 100% toggleable mechanics across all categories and death rules.
- Thread-safe `ConfigManager` with Gson JSON serialization in `config/vanilla-outsider-keep-gear.json`.
- Comprehensive `en_us.json` localization covering all category options, tooltips, and in-game feedback messages.
- Automated JUnit 5 test suite `KeepGearConfigTest` verifying default values and JSON serialization roundtrips.

## [1.2.0+26.3]
### Changed
- Migrated project toolchain to Minecraft 26.3 (26.3-snapshot-6) and Fabric Loom 1.15-SNAPSHOT.
- Purged legacy Kotlin plugins and runtime dependencies (abric-language-kotlin).
- Converted project entrypoints to pure Java 25 (JavaLanguageVersion.of(25)).
- Updated optional GUI dependencies from Cloth Config to YetAnotherConfigLib v3 (YACL) + ModMenu (compileOnly with "suggests").
- Registered automated release archiving task rchiveReleaseJar.
