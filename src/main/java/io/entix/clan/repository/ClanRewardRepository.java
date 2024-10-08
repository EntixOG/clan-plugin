package io.entix.clan.repository;

import eu.koboo.en2do.repository.Collection;
import eu.koboo.en2do.repository.Repository;
import io.entix.clan.reward.ClanReward;

import java.util.UUID;

@Collection("clan_reward")
public interface ClanRewardRepository extends Repository<ClanReward, UUID> {
}
