package io.entix.data.reward;

import eu.koboo.en2do.repository.entity.Id;
import io.entix.data.reward.content.AbstractRewardContent;
import io.entix.data.reward.requirements.AbstractRewardRequirement;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClanReward {

    @Id
    UUID rewardId;

    int rewardLevel;

    List<AbstractRewardRequirement> rewardRequirements = new ArrayList<>();
    List<AbstractRewardContent> contents = new ArrayList<>();

    public boolean hasRequirements() {
        return rewardRequirements.isEmpty();
    }


}
