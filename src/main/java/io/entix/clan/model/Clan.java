package io.entix.clan.model;

import eu.koboo.en2do.repository.entity.Id;
import eu.koboo.en2do.repository.entity.Transient;
import io.entix.clan.model.member.ClanMember;
import io.entix.clan.model.member.rank.ClanRank;
import io.entix.clan.model.reward.AvailableReward;
import lombok.*;
import lombok.experimental.FieldDefaults;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.*;

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
    long creationDate = System.currentTimeMillis();

    double experience = 0.0;
    long money = 0L;

    int maxBases = 3;
    List<Location> clanBases = new ArrayList<>();

    List<AvailableReward> availableRewards = new ArrayList<>();

    @Transient
    Map<UUID, ClanMember> clanMembers = new HashMap<>();

    public void sendClanMessage(@NonNull String message) {
        for (ClanMember clanMember : clanMembers.values()) {
            Player player = Bukkit.getPlayer(clanMember.getMemberId());
            if (player == null || !player.isOnline()) continue;
            player.sendMessage(Component.text(message));
        }
    }

    public ClanMember addMember(@NonNull UUID uniqueId, @NonNull ClanRank clanRank) {
        if (clanMembers.containsKey(uniqueId)) {
            return null;
        }

        ClanMember clanMember = new ClanMember();
        clanMember.setMemberId(uniqueId);
        clanMember.setClanRank(clanRank);
        return clanMember;
    }

    public @Nullable ClanMember findClanMemberById(@NonNull UUID memberId) {
        return clanMembers.get(memberId);
    }
}
