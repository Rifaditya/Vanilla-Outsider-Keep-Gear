# Commands & Permissions

Manage and debug **Keep Gear** directly from the chat console.

> [!IMPORTANT]
> These commands are intended for Server Admins.

## 💻 Commands

### General

* `/keepgear` - Shows the current status of the mod (Enabled/Disabled, lists sizes, etc).
* `/keepgear help` - Displays a list of available commands.
* `/keepgear reload` - Reloads the configuration file from disk. Useful for applying changes made to `vanilla-outsider-keep-gear.json5` without restarting the server.
* `/keepgear status` - Alias for `/keepgear`.

### Lists Management

You can modify the Blacklist and Whitelist in-game. Changes are saved to config immediately.

#### Blacklist (Items never kept)

* `/keepgear blacklist list` - Show all blacklisted items.
* `/keepgear blacklist add <item_id>` - Add an item (e.g. `minecraft:cobblestone`).
* `/keepgear blacklist remove <item_id>` - Remove an item.
* `/keepgear blacklist clear` - Remove ALL items from blacklist.

#### Whitelist (Items always kept)

* `/keepgear whitelist list` - Show all whitelisted items.
* `/keepgear whitelist add <item_id>` - Add an item.
* `/keepgear whitelist remove <item_id>` - Remove an item.
* `/keepgear whitelist clear` - Clear the whitelist.

### Dimension Control

Disable the mod entirely in specific dimensions (e.g., make the End truly dangerous).

* `/keepgear dimension list` - Show dimensions where mod is DISABLED.
* `/keepgear dimension disable <dimension_id>` - Disable mod in a dimension (e.g. `minecraft:the_end`).
* `/keepgear dimension enable <dimension_id>` - Re-enable mod in a dimension.

## 🔐 Permissions

The mod uses standard Minecraft command permissions.

* **Default Requirement:** OP Level 2 (Command Block / Cheat level) or higher is usually required for management commands (`reload`, `add`, `remove`).
* **Status/Help:** Accessible to all players (depending on server config).
