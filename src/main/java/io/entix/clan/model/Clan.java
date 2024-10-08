package io.entix.clan.model;

import eu.koboo.en2do.repository.entity.Id;
import eu.koboo.en2do.repository.entity.Transient;
import io.entix.clan.model.member.ClanMember;
import io.entix.clan.model.member.rank.ClanRank;
import io.entix.clan.repository.ClanMemberRepository;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Clan {

    @Id
    UUID clanId;

    String clanName;
    String clanTag;

    UUID clanCreator;

    double experience = 0.0;
    long money = 0L;

    long creationDate = System.currentTimeMillis();

    @Transient
    Map<UUID, ClanMember> clanMembers = new HashMap<>();

    public ClanMember addMember(@NonNull UUID uniqueId, @NonNull ClanRank clanRank) {
        if (clanMembers.containsKey(uniqueId)) {
            return null;
        }

        ClanMember clanMember = new ClanMember();
        clanMember.setMemberId(uniqueId);
        clanMember.setClanRank(clanRank);
        return clanMember;
    }
}
