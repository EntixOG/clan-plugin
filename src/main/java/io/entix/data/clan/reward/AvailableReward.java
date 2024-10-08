package io.entix.data.clan.reward;

import io.entix.ClanPlugin;
import io.entix.data.reward.ClanReward;
import io.entix.data.reward.requirements.AbstractRewardRequirement;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AvailableReward {

    UUID rewardId;
    List<UUID> collector = new ArrayList<>();

    public boolean collectReward(@NonNull ClanPlugin plugin, @NonNull Player player) {
        if (collector.contains(player.getUniqueId())) return false;

        ClanReward clanReward = plugin.getClanService().findClanRewardById(rewardId);
        if (clanReward == null) return false;

        boolean isFullComplete = clanReward.getRewardRequirements().stream().allMatch(AbstractRewardRequirement::isRequirementComplete);
        if (!isFullComplete) return false;

        clanReward.getContents().forEach(content -> content.collect(player));
        collector.add(player.getUniqueId());
        return true;
    }

    public boolean hasCollected(@NonNull Player player) {
        return collector.contains(player.getUniqueId());
    }
}
