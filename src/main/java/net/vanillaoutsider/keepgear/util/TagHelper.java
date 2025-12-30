package net.vanillaoutsider.keepgear.util;

import net.minecraft.world.item.ItemStack;

/**
 * Java helper class for tag matching.
 * Uses getTags() approach to check item tags.
 */
public class TagHelper {

    /**
     * Check if an item matches a tag.
     * 
     * @param stack The item stack to check
     * @param tagId The tag ID (e.g., "c:soulbound" or "minecraft:tools")
     * @return true if the item has the tag
     */
    public static boolean matchesTag(ItemStack stack, String tagId) {
        if (stack.isEmpty() || tagId == null || tagId.isEmpty()) {
            return false;
        }

        try {
            // Get all tags the item has and check if any match
            var tags = stack.getTags();
            final String searchTag = tagId;
            return tags.anyMatch(tag -> {
                String tagString = tag.location().toString();
                // Match exact ID or just the path part
                return tagString.equals(searchTag) ||
                        tagString.endsWith(":" + searchTag) ||
                        tagString.equals("minecraft:" + searchTag);
            });
        } catch (Exception e) {
            return false;
        }
    }
}
