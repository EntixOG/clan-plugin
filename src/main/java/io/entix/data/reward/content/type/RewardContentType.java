package io.entix.data.reward.content.type;

import com.mongodb.lang.Nullable;
import io.entix.data.reward.content.AbstractRewardContent;
import io.entix.data.reward.content.impl.CustomItemContent;
import io.entix.data.reward.content.impl.MaterialRewardContent;
import io.entix.data.reward.content.impl.MoneyRewardContent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.bukkit.Material;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum RewardContentType {

    MONEY("Money", Material.SUNFLOWER, MoneyRewardContent.class),
    MATERIAL("Material", Material.SHULKER_BOX, MaterialRewardContent.class),
    CUSTOM_ITEM("Custom Item", Material.NETHER_STAR, CustomItemContent.class);

    String rewardName;
    Material contentIcon;
    Class<? extends AbstractRewardContent> rewardContent;

    public static @Nullable RewardContentType findRewardByName(@NonNull String typeName) {
        for (RewardContentType rewardContentType : values()) {
            if (typeName.equalsIgnoreCase(rewardContentType.name())) {
                return rewardContentType;
            }
        }
        return null;
    }
}