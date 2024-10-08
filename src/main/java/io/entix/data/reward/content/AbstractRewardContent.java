package io.entix.data.reward.content;

import io.entix.data.reward.content.type.RewardContentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.entity.Player;

@Getter
@AllArgsConstructor
public abstract class AbstractRewardContent {

    RewardContentType rewardContentType;

    public abstract String encode();

    public abstract void decode(@NonNull String encodedString);

    public abstract void collect(@NonNull Player player);

    public abstract String contentAsString();
}