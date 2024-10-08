package io.entix.data.reward.content.impl;

import io.entix.data.reward.content.AbstractRewardContent;
import io.entix.data.reward.content.type.RewardContentType;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.entity.Player;

@Setter
public class MoneyRewardContent extends AbstractRewardContent {

    long money;

    public MoneyRewardContent() {
        super(RewardContentType.MONEY);
    }

    @Override
    public String encode() {
        return String.valueOf(this.money);
    }

    @Override
    public void decode(@NonNull String encodedString) {
        long parsedMoney;
        try {
            parsedMoney = Long.parseLong(encodedString);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Failed to parse encodedString to long: " + encodedString);
        }
        this.money = parsedMoney;
    }

    @Override
    public String contentAsString() {
        return "<dark_gray>- <gray>Money: <yellow>" + money + "$";
    }

    @Override
    public void collect(@NonNull Player player) {

    }
}