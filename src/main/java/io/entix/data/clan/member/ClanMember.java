package io.entix.data.clan.member;

import eu.koboo.en2do.repository.entity.Id;
import eu.koboo.en2do.repository.entity.Transient;
import io.entix.data.clan.member.permission.ClanPermission;
import io.entix.data.clan.member.rank.ClanRank;
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
public class ClanMember {

    @Id
    UUID memberId;

    String clanName;

    String lastSeenName;
    long joiningDate = System.currentTimeMillis();

    boolean online = false;

    ClanRank clanRank = ClanRank.MEMBER;
    List<ClanPermission> permissions = new ArrayList<>();

    @Transient
    public boolean hasPermission(@NonNull ClanPermission permission) {
        if (clanRank.hasPermission(permission)) return true;
        return permissions.contains(permission);
    }
}
