# Architecture & Symbol Index: Vanilla Outsider: Keep Gear

## 1. Mod Metadata & Entrypoint
- **Mod ID**: `vanilla-outsider-keep-gear`
- **Main Entrypoint**: `net.vanillaoutsider.keepgear.KeepGear` (`net.fabricmc.api.ModInitializer`)
- **Client Entrypoint**: `None`

## 2. Bytecode Mixin Target Registry
| Target Vanilla Class | Mixin Class | Purpose |
| :--- | :--- | :--- |
| `Vanilla Class` | `net.vanillaoutsider.keepgear.mixin.PlayerDropEquipmentMixin` | Core mixin hook |
| `Vanilla Class` | `net.vanillaoutsider.keepgear.mixin.ServerPlayerRespawnMixin` | Core mixin hook |

## 3. Core Mechanics & Subsystems
- **Source Root**: `src/main/java/`
- **Resource Root**: `src/main/resources/`

## 4. Dynamic GameRules & Commands
- **GameRules / Commands**: Configured dynamically via namespaced keys (`vanilla-outsider-keep-gear:*`).

## 5. Configuration & Sidedness Isolation
- **Sidedness**: Server-safe logic in main, client isolated in `src/client/java` or client entrypoint.
