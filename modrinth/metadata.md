# 📋 Project Metadata Checklist

Use this file to populate the fields on the Modrinth project creation page.

## Part 1: General Information

| Field | Value |
| :--- | :--- |
| **Project Name** | `Vanilla Outsider: Keep Gear` |
| **Slug** | `vanilla-outsider-keep-gear` |
| **Summary** | A balanced death system: Keep gear with durability at a cost scaling with enchantments. Use Echo Shards to negate penalties and retain XP. |
| **Description** | *Copy from `description.md`* |
| **Categories** | `Adventure`, `Utility`, `Magic` |
| **License** | `GNU GPLv3` |
| **Source Link** | `https://github.com/Rifaditya/Vanilla-Outsider-Keep-Gear` |
| **Issues Link** | `https://github.com/Rifaditya/Vanilla-Outsider-Keep-Gear/issues` |

## Part 2: Featured Tags

*Select these tags to help users find your mod:*

1. **Death**
2. **Utility**
3. **Equipment**
4. **Vanilla+**

## Part 3: Environment Settings

| Setting | Status |
| :--- | :--- |
| **Client Side** | Optional (for Mod Menu config) |
| **Server Side** | Required |

## Part 4: Version Upload Checklist

When uploading the file `Vanilla-Outsider-Keep-Gear-1.0.0.jar`:

- [ ] **Name:** `Vanilla Outsider: Keep Gear v1.0.0`
- [ ] **Version Number:** `1.0.0`
- [ ] **Release Type:** `Release` (green)
- [ ] **Game Versions:** `1.21.11`
- [ ] **Loaders:** `Fabric`
- [ ] **Java Version:** `Java 21`

### Dependencies

Add these in the "Dependencies" tab:

1. **Fabric API** (Required)
2. **Fabric Language Kotlin** (Required)
3. **Cloth Config** (Optional, for config screen)
4. **Mod Menu** (Optional, for config screen)
5. **Trinkets** (Optional, for integration)

---

## 🧩 Compatibility Knowledge Base

- **Incompatible with:** Grave mods (usually), as items are kept in inventory.
- **Compatible with:** `Trinkets` (Bidirectional Sync), `Backpacks` (If they have durability).
