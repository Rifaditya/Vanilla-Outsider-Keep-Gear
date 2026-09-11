# VO: Keep Gear (Smart Preservation)

## Philosophy Fit
**"Death is separate from Loss."**

This mod adheres to the "1:1 Input/Output" rule by exchanging **Durability/Cost** for **Preservation**. It preserves the "Death Loop" (you still lose resources/consumables and have to run back) but enhances it by removing the specific frustration of losing grind-heavy items.

## Mechanics

1. **Smart Preservation (The Filter)**
   - **Description**: Identifies items to keep based on strict categories aligned with **Vanilla Creative Tabs**.
   - **Implementation**: On `PlayerDropAllItemsEvent` (or Mixin into `dropAllDeathLoot`), iterate inventory. Check item's assigned Creative Mode Tab or specific Tags. Move valid items to "Saved Inventory".

2. **Configurable Categories (GameRules)**
   Covering the entire spectrum of Vanilla items.
   - **Equipment**:
     - `voKeepGearTools`: Pickaxes, Shovels, Axes, Hoes, Shears, Fishing Rods (`#c:tools`).
     - `voKeepGearCombat`: Swords, Bows, Crossbows, Tridents, Shields (`#c:swords`, `#minecraft:arrows`).
     - `voKeepGearArmor`: Helmets, Chestplates, Leggings, Boots (`#c:armors`).
   - **Storage**:
     - `voKeepGearStorage`: Shulker Boxes, Bundles, Chests, Barrels (`#c:shulker_boxes`, `#c:bundles`).
   - **Blocks**:
     - `voKeepGearBuilding`: Bricks, Stone, Concrete, Glass.
     - `voKeepGearColored`: Wool, Carpet, Terracotta, Stained Glass.
     - `voKeepGearNatural`: Logs, Leaves, Saplings, Flowers, Crops (`#minecraft:logs`, etc).
     - `voKeepGearFunctional`: Crafting Tables, Furnaces, Anvils, Beacons.
     - `voKeepGearRedstone`: Redstone Dust, Pistons, Rails, Minecarts.
   - **Materials**:
     - `voKeepGearFood`: Apples, Bread, Golden Carrots (`item.isEdible()`).
     - `voKeepGearIngredients`: Sticks, Iron Ingots, Diamonds, Lapis (`#c:raw_materials`).
     - `voKeepGearRare`: Enchanted Books, Nether Stars, Totems of Undying.
     - **voKeepGearSpawnEggs**: Mob Spawn Eggs.

3. **The Cost: Durability Penalty**
   - **Base Rule**: Every kept item (that has durability) loses `X%` durability on death.
   - **Risk**: Items *CAN* break if penalty exceeds current durability.
   - **Config**: `voKeepGearPenaltyPercent` (default 10%).

4. **The Insurance: Echo Resonance**
   - **Item**: `Echo Shard`.
   - **Effect**: If present in inventory, one shard is consumed on death.
   - **Reward**: Negates ALL durability penalties for that death.
   - **Configurable**: Can be toggled on/off via GameRule `voKeepGearEchoInsurance`.

## Configuration (GameRules)
**All must appear in "Player" category of Game Rules screen.**

### Equipment Defaults
- `voKeepGearTools` (bool, default true)
- `voKeepGearCombat` (bool, default true)
- `voKeepGearArmor` (bool, default true)

### Critical Defaults
- `voKeepGearStorage` (bool, default true)
- `voKeepGearRare` (bool, default false)

### Bulk Categories Defaults
- `voKeepGearBuilding` (bool, default false)
- `voKeepGearColored` (bool, default false)
- `voKeepGearNatural` (bool, default false)
- `voKeepGearFunctional` (bool, default false)
- `voKeepGearRedstone` (bool, default false)
- `voKeepGearFood` (bool, default false)
- `voKeepGearIngredients` (bool, default false)
- `voKeepGearSpawnEggs` (bool, default false)

### Mechanics
- `voKeepGearPenaltyPercent` (int, default 10, min 0, max 100)
- `voKeepGearEchoInsurance` (bool, default true)

## Assets Needed
- **Sound**: `block.sculk_shrieker.shriek` (Low pitch on Echo Shard use).
- **Visuals**: `Soul Smoke` particles on respawn.

## Implementation Checklist
- [ ] **Core Logic**: Mixin into `dropAllDeathLoot`.
- [ ] **Category Filter**: Implement robust checking against `CreativeModeTabs` and `TagKeys`.
- [ ] **Persistence**: Save kept items to NBT/Component on player.
- [ ] **Restoration**: Restore items in `respawn` method.
- [ ] **Durability Logic**: Apply damage calculation.
- [ ] **Echo Logic**: Consume shard if present & enabled.
- [ ] **Config**: Register ALL GameRules.
- [ ] **Verification**: Die with mixed inventory covering all categories.
