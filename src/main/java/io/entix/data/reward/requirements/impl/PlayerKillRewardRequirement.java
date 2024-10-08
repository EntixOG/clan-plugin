package io.entix.data.reward.requirements.impl;

import io.entix.ClanPlugin;
import io.entix.data.reward.requirements.AbstractRewardRequirement;
import io.entix.data.reward.requirements.type.RewardRequirementType;
import lombok.NonNull;
import lombok.Setter;

@Setter
public class PlayerKillRewardRequirement extends AbstractRewardRequirement {

    int playerKills;

    public PlayerKillRewardRequirement(ClanPlugin plugin) {
        super(plugin, RewardRequirementType.PLAYER_KILL);
    }

    @Override
    public String encode() {
        return String.valueOf(playerKills);
    }

    @Override
    public void decode(@NonNull String encodedString) {
        int playerKillsFromString;
        try {
            playerKillsFromString = Integer.parseInt(encodedString);

        } catch (NumberFormatException exception)  {
            throw new RuntimeException("Failed to parse encodedString to integer: " + encodedString);
        }
        this.playerKills = playerKillsFromString;
    }

    @Override
    public String description() {
        return " <dark_gray>- <gray>Spieler Kills: <green>" + playerKills;
    }

    @Override
    public boolean isRequirementComplete() {
        return false;
    }
}