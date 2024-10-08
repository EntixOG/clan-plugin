package io.entix.data.repository;

import eu.koboo.en2do.repository.Collection;
import eu.koboo.en2do.repository.Repository;
import io.entix.data.reward.ClanReward;

import java.util.UUID;

@Collection("rewards")
public interface ClanRewardRepository extends Repository<ClanReward, UUID> {
}
