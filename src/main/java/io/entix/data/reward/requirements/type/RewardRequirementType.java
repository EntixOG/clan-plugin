package io.entix.data.reward.requirements.type;

import io.entix.data.reward.requirements.AbstractRewardRequirement;
import io.entix.data.reward.requirements.impl.MobRewardRequirement;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.bukkit.Material;
import org.jetbrains.annotations.Nullable;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum RewardRequirementType {

    MONSTER_KILL("PvE (Player vs Entity)", Material.DIAMOND_SWORD, MobRewardRequirement.class),
    PLAYER_KILL("PvP (Player vs Player)", Material.WOODEN_SWORD, MobRewardRequirement.class);

    String requirementName;
    Material requirementIcon;
    Class<? extends AbstractRewardRequirement> rewardRequirement;

    public static @Nullable RewardRequirementType findRewardByName(@NonNull String typeName) {
        for (RewardRequirementType requirementType : values()) {
            if (typeName.equalsIgnoreCase(requirementType.name())) {
                return requirementType;
            }
        }
        return null;
    }
}