package io.entix.data.clan;

import eu.koboo.en2do.repository.entity.Id;
import eu.koboo.en2do.repository.entity.Transient;
import io.entix.data.clan.achievements.ClanAchievement;
import io.entix.data.clan.achievements.ClanUnlockedAchievement;
import io.entix.data.clan.member.ClanMember;
import io.entix.data.clan.member.rank.ClanRank;
import io.entix.data.clan.reward.AvailableReward;
import lombok.*;
import lombok.experimental.FieldDefaults;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
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

    Map<EntityType, Integer> killedEntities = new HashMap<>();

    List<AvailableReward> availableRewards = new ArrayList<>();
    List<ClanUnlockedAchievement> unlockedAchievements = new ArrayList<>();

    @Transient
    Map<UUID, ClanMember> clanMembers = new HashMap<>();

    @Transient
    public void sendClanMessage(@NonNull String message) {
        for (ClanMember clanMember : clanMembers.values()) {
            Player player = Bukkit.getPlayer(clanMember.getMemberId());
            if (player == null || !player.isOnline()) continue;
            player.sendMessage(Component.text(message));
        }
    }

    @Transient
    public boolean unlockAchievement(@NonNull ClanAchievement achievement) {
        ClanUnlockedAchievement unlockedAchievement = unlockedAchievements.stream()
                .filter(clanUnlockedAchievement -> clanUnlockedAchievement.getAchievement().equals(achievement))
                .findFirst()
                .orElse(null);

        if (unlockedAchievement != null) return false;

        unlockedAchievement = new ClanUnlockedAchievement(achievement, System.currentTimeMillis());
        unlockedAchievements.add(unlockedAchievement);

        setExperience(experience + achievement.getExperience());
        return true;
    }

    @Transient
    public @Nullable ClanMember addMember(@NonNull UUID uniqueId, @NonNull ClanRank clanRank) {
        if (clanMembers.containsKey(uniqueId)) return null;

        ClanMember clanMember = new ClanMember();
        clanMember.setClanName(clanName);
        clanMember.setMemberId(uniqueId);
        clanMember.setClanRank(clanRank);

        clanMembers.put(uniqueId, clanMember);
        return clanMember;
    }

    @Transient
    public void addKill(@NonNull EntityType entityType) {
        Integer killsOfEntity = killedEntities.getOrDefault(entityType, 0);
        killsOfEntity++;
        killedEntities.put(entityType, killsOfEntity);
    }

    @Transient
    public @Nullable ClanMember findClanMemberById(@NonNull UUID memberId) {
        return clanMembers.get(memberId);
    }
}
