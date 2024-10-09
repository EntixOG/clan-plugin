package io.entix.data.reward.requirements.impl;

import io.entix.ClanPlugin;
import io.entix.data.reward.requirements.AbstractRewardRequirement;
import io.entix.data.reward.requirements.type.RewardRequirementType;
import lombok.NonNull;
import lombok.Setter;

@Setter
public class ClanMemberAmountRequirement extends AbstractRewardRequirement {

    int memberAmount;

    public ClanMemberAmountRequirement(ClanPlugin plugin) {
        super(plugin, RewardRequirementType.PLAYER_KILL);
    }

    @Override
    public String encode() {
        return String.valueOf(memberAmount);
    }

    @Override
    public void decode(@NonNull String encodedString) {
        int memberAmountFromString;
        try {
            memberAmountFromString = Integer.parseInt(encodedString);

        } catch (NumberFormatException exception) {
            throw new RuntimeException("Failed to parse encodedString to integer: " + encodedString);
        }
        this.memberAmount = memberAmountFromString;
    }

    @Override
    public String description() {
        return " <dark_gray>- <gray>Clan Mitglieder: <green>" + memberAmount;
    }

    @Override
    public boolean isRequirementComplete() {
        return false;
    }
}