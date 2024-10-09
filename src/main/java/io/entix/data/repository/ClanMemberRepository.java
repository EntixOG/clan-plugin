package io.entix.data.repository;

import eu.koboo.en2do.repository.Collection;
import eu.koboo.en2do.repository.Repository;
import io.entix.data.clan.member.ClanMember;

import java.util.UUID;

@Collection("members")
public interface ClanMemberRepository extends Repository<ClanMember, UUID> {
}
