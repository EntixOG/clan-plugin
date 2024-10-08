package io.entix.data.reward.requirements;

import eu.koboo.en2do.repository.Collection;
import eu.koboo.en2do.repository.Repository;
import io.entix.data.reward.ClanReward;

import java.util.UUID;

@Collection("clan_reward")
public interface ClanRewardRepository extends Repository<ClanReward, UUID> {
}
