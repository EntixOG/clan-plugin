package io.entix.data.clan.repository;

import eu.koboo.en2do.repository.Collection;
import eu.koboo.en2do.repository.Repository;
import io.entix.data.clan.member.ClanMember;

import java.util.UUID;

@Collection("clan_member")
public interface ClanMemberRepository extends Repository<ClanMember, UUID> {
}
