package io.entix.data.repository;

import eu.koboo.en2do.repository.Collection;
import eu.koboo.en2do.repository.Repository;
import io.entix.data.clan.Clan;

import java.util.UUID;

@Collection("registry")
public interface ClanRepository extends Repository<Clan, UUID> {
}
