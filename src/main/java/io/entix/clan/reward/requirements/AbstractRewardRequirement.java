package io.entix.clan.reward.requirements;

import io.entix.ClanPlugin;
import io.entix.clan.reward.requirements.type.RewardRequirementType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public abstract class AbstractRewardRequirement {

    ClanPlugin plugin;
    RewardRequirementType requirementType;

    public abstract String encode();

    public abstract void decode(@NonNull String encodedString);

    public abstract String description();

    public abstract boolean isRequirementComplete();
}