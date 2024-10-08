package io.entix.clan.repository;

import eu.koboo.en2do.repository.Collection;
import eu.koboo.en2do.repository.Repository;
import io.entix.clan.model.member.ClanMember;

import java.util.UUID;

@Collection("clan_member")
public interface ClanMemberRepository extends Repository<ClanMember, UUID> {
}
