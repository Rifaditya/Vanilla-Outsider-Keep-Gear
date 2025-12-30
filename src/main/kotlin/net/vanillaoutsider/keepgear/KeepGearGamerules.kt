package net.vanillaoutsider.keepgear

/**
 * Placeholder for gamerules - using config-based settings instead.
 * Gamerule registration requires Fabric API gamerule module which may need setup.
 * For now, all settings are controlled via config file and /keepgear commands.
 */
object KeepGearGamerules {
    
    fun register() {
        // Gamerules are optional - using config-based approach instead
        // All settings are accessible via /keepgear commands and config file
        KeepGear.logger.info("Using config-based settings (gamerules disabled)")
    }
}
