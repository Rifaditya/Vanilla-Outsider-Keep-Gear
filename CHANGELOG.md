# Changelog

All notable changes to **Vanilla Outsider: Keep Gear** will be documented in this file.

## [1.2.5+26.3]
### Added
- Integrated universal **Dasik Library** (`>=1.9.0`) runtime dependency.
- Integrated official Dasik community Discord and Ko-fi creator support links in `/keepgear status` and `/keepgear help` command footers via `DasikSupportHelper`.
- Added startup log reporting official community and creator support endpoints.
- Added `KeepGearSupport` utility providing streamlined access to Dasik Library social endpoints.
- Configured ModMenu links for donation and Discord community in `fabric.mod.json`.
- Comprehensive automated JUnit 5 test coverage for Dasik Library integration and endpoint resolution.

## [1.2.4+26.3]
### Added
- Complete in-game Brigadier command suite `/keepgear` (`status`, `help`, `set <property> <value>`, `reset`, `reload`) with permission gating (`Commands.LEVEL_GAMEMASTERS`).
- Reflection-safe optional Trinkets compatibility module (`TrinketsCompat`) preserving equipped accessories across deaths without compile-time hard dependencies.
- Registered `/keepgear` command tree to `CommandRegistrationCallback`.

## [1.2.3+26.3]
### Added
- Player death item interception via `PlayerDropEquipmentMixin` cancelling vanilla drop pipeline for preserved equipment.
- Sided respawn equipment restoration via `ServerPlayerRespawnMixin` with equipment conflict safety fallback (equipped -> inventory -> safe ground drop).
- Instant post-respawn XP HUD synchronization packet dispatch (`ClientboundSetExperiencePacket`).
- Visual and audio respawn feedback with configurable particle effects and chime audio cues.

## [1.2.2+26.3]
### Added
- High-fidelity `ItemPreservationEngine` with 100% granular classification (armor, weapons, tools, shields, elytra, consumables, resources, containers).
- Minecraft 26.3 DataComponents durability degradation penalty (`DataComponents.DAMAGE` & `DataComponents.MAX_DAMAGE`) with permanent breakage on lethal wear.
- Curse of Vanishing / Curse of Binding enforcement.
- Echo Shard insurance mechanic consuming shard from inventory to grant 100% item safety and bypass degradation.

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
