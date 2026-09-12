# 🏛️ Architecture & Mixins

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

**Vanilla Outsider: Keep Gear** is engineered with a clean, low-overhead, modular architecture targeting Minecraft 26.3 Java Edition on Fabric Loader.

## 🏗️ Core Architectural Diagram

```text
       Player Death Event
               │
               ▼
   PlayerDropEquipmentMixin (HEAD)
               │
               ▼
   ItemPreservationEngine.processDeathPreservation()
        ├── 1. Evaluate Eligibility (Dimension, Void, PvP)
        ├── 2. Check Echo Shard Insurance
        ├── 3. Filter Main Inventory (0..35)
        ├── 4. Filter Equipment Slots (HEAD, CHEST, LEGS, FEET, OFFHAND)
        ├── 5. Apply Durability Wear Formulas
        └── 6. Extract Preserved XP & Deduct from Player
               │
               ▼
   KeepGearPlayerBridge.setPreservedInventory()
               │
               ▼ [Vanilla dropEquipment() runs without preserved stacks]
               │
       Player Respawn Event
               │
               ▼
   ServerPlayerRespawnMixin (restoreFrom TAIL)
        ├── 1. Retrieve PreservedInventory from Old Player Bridge
        ├── 2. Restore Exact Main Inventory Slots (0..35)
        ├── 3. Restore Equipment Slots (with inventory/ground fallback)
        ├── 4. Restore XP & Send ClientboundSetExperiencePacket
        ├── 5. Send Audio/Visual Feedback Packets
        └── 6. Broadcast Container & Inventory Menu Changes
```

---

## 💉 Mixin Injection Specifications

### 1. `PlayerDropEquipmentMixin`
* **Target**: `net.minecraft.world.entity.player.Player`
* **Injection Point**: `@Inject(method = "dropEquipment", at = @At("HEAD"))`
* **Mechanism**: Intercepts the vanilla item drop sequence before equipment is converted into `ItemEntity` instances in the world. Stacks matching preservation criteria are removed from the player's inventory directly into a temporary `PreservedInventory` record.

### 2. `ServerPlayerRespawnMixin`
* **Target**: `net.minecraft.server.level.ServerPlayer`
* **Interface**: Implements `KeepGearPlayerBridge`
* **Injection Point**: `@Inject(method = "restoreFrom", at = @At("TAIL"))`
* **Mechanism**: When a server player respawns from death (`restoreAll == false`), items stored in the bridge from the old player instance are restored into identical slots on the new player instance.

---

## 🛡️ Equipment Conflict Safety Fallback

If an equipment slot (e.g. helmet) is somehow occupied upon respawn:
1. The engine attempts to place the item into the player's main inventory (`inventory.add(stack)`).
2. If the player's inventory is completely full, the item is dropped safely at the player's respawn feet (`newPlayer.drop(stack, false)`).
3. Under no circumstance is an item deleted or overwritten.

---

## 🌐 Network & Client Synchronization

* **HUD Experience Sync**: Vanilla Minecraft does not automatically re-synchronize experience bar graphics after programmatic XP restoration on respawn. `ServerPlayerRespawnMixin` explicitly transmits `ClientboundSetExperiencePacket` to prevent visual desynchronization.
* **Audio Cues**: Dispatches `ClientboundSoundPacket` for `ARMOR_EQUIP_GENERIC` (or `RESPAWN_ANCHOR_DEPLETE` when Echo Shard is used).
* **Menu Sync**: Calls `broadcastChanges()` on both `containerMenu` and `inventoryMenu`.
