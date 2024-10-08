package io.entix.data.reward.content.impl;

import io.entix.data.reward.content.AbstractRewardContent;
import io.entix.data.reward.content.type.RewardContentType;
import io.entix.utility.ItemStackConverter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Setter
public class CustomItemContent extends AbstractRewardContent {

    ItemStack customItem;

    public CustomItemContent() {
        super(RewardContentType.CUSTOM_ITEM);
    }

    @Override
    public String encode() {
        if (customItem == null) {
            throw new RuntimeException("Failed to encode custom item, because is null");
        }
        return ItemStackConverter.encode(customItem);
    }

    @Override
    public void decode(@NonNull String encodedString) {
        ItemStack customItem = ItemStackConverter.decode(encodedString);
        if (customItem == null) {
            throw new RuntimeException("Failed to parse encodedString to itemstack: " + encodedString);
        }

        this.customItem = customItem;
    }

    @Override
    public String contentAsString() {
        return "<dark_gray>- <gray>Custom Item: <yellow>" + (customItem.getItemMeta() != null ? customItem.getItemMeta().getDisplayName() : "<red>Kein Displayname");
    }

    @Override
    public void collect(@NonNull Player player) {
    }
}