package io.entix.data.clan.repository;

import eu.koboo.en2do.repository.Collection;
import eu.koboo.en2do.repository.Repository;
import io.entix.data.clan.Clan;

import java.util.UUID;

@Collection("clan")
public interface ClanRepository extends Repository<Clan, UUID> {
}
