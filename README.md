# Vanilla Outsider: Keep Gear

A Fabric mod for Minecraft 1.21.11 that **keeps your gear on death**!

## Features

- **Durability-Based Protection**: Items with durability (armor, tools, weapons) are kept on death
- **Materials Drop**: Items without durability (ores, blocks, food) drop as normal
- **Durability Penalty**: 1.2% durability cost per death (configurable)
- **XP Retention**: Keep 20% of your XP on death (configurable)
- **Mod Compatibility**: Automatically works with ALL modded gear that has durability

## Commands

| Command | Description |
|---------|-------------|
| `/keepgear` | Show mod status |
| `/keepgear reload` | Reload config |
| `/keepgear blacklist add <item>` | Add item to blacklist (drops on death) |
| `/keepgear whitelist add <item>` | Add item to whitelist (kept without durability) |
| `/keepgear dimension disable <dim>` | Disable mod in dimension |

## Gamerules

| Gamerule | Default | Description |
|----------|---------|-------------|
| `keepGearEnabled` | true | Master toggle |
| `keepGearPenaltyPercent` | 1 | Durability penalty % |
| `keepGearXpPercent` | 20 | XP kept % |

## Configuration

Config file: `config/vanilla-outsider-keep-gear.json`

## License

GPL-3.0

## Author

Rifaditya
