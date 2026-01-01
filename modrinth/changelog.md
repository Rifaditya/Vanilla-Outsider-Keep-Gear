# Changelog

## v1.1.0 - The Container Update

### 🎒 Container Integration (Soft Dependency)

Finally, official support for keeping your favorite storage items! No hard dependencies required—it just works.

* **Whitelisted Containers:** By default supports **Traveler's Backpack**, **Eclipse Backpacks**, **Bundles**, and **Shulker Boxes**.
* **Modes:**
  * **Keep All (Default):** Saves the container AND all items inside. This is the "Safe Mode" and acts as a blacklist override for items inside the bag.
  * **Drop Contents:** Saves the empty bag but spills the contents on the ground. Works perfectly for Vanilla containers and Standard modded backpacks.
* **Configurable:** Toggle these features in the new "Item Categories" tab.

### 🐛 Changes

* Moved configuration for containers to "Item Categories" tab for better organization.

### 🔥 Advanced Penalties

* **Cause-Specific Rules:** Added toggles for **Lava**, **Fall**, **Explosion**, and **Void** damage.
  * **Off by Default:** All deaths use the global penalty unless enabled.
  * **Configurable:** Set custom penalty percentages for each cause (e.g. make Explosions deal 10% damage!).
* **Curse Overhaul:** Added **"Drop Curse of Binding"** option. If enabled, cursed items are finally droppable on death!

---

# v1.0.0

* The Release

* **Initial Release** for Minecraft 1.21.11 (Fabric).
* **Core Feature:** Keep items with durability on death, drop everything else.
* **Penalties:** Implemented 1.2% durability loss and 20% XP retention default.
* **Config:** Full Cloth Config & Mod Menu support with Server/Client syncing.
* **Granular Control:** Added ability to toggle keeping for specific categories (Armor, Weapons, Tools, Shields, Elytra, Consumables, Resources).
* **Commands:** Added `/keepgear` admin commands and custom gamerules.
* **Echo Shard Utility:** Perfect "Deep Dark" lore! Consumes 1 Echo Shard on death to negate all durability penalties.
* **Enchantment Weight:** "Heavy" enchantments weigh on the soul. Configurable penalty increase per enchantment level (default +0.1% per level).
* **Compat:** Added "Keep Trinkets" toggle in Integrations menu; supports Trinkets and Trinkets Canary.
