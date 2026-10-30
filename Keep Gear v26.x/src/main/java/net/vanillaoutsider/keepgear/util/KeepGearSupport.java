// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.keepgear.util;

import net.dasik.social.api.SocialLinks;
import net.dasik.social.api.config.DasikSupportHelper;
import net.minecraft.network.chat.Component;

import java.net.URI;

/**
 * Helper utility consuming Dasik Library APIs to provide community and creator support endpoints.
 */
public final class KeepGearSupport {

    private KeepGearSupport() {}

    public static String getDiscordUrl() {
        return SocialLinks.DISCORD_INVITE_URL;
    }

    public static String getKofiUrl() {
        return DasikSupportHelper.KOFI_URL;
    }

    public static URI getDiscordUri() {
        return SocialLinks.getDiscordUri();
    }

    public static URI getKofiUri() {
        return SocialLinks.getKofiUri();
    }

    public static Component getFooter() {
        return DasikSupportHelper.getCommandFooter();
    }
}
