package io.entix.data;

import io.entix.ClanPlugin;
import io.entix.data.clan.Clan;
import io.entix.data.clan.member.ClanMember;
import io.entix.data.clan.member.rank.ClanRank;
import io.entix.data.repository.ClanMemberRepository;
import io.entix.data.repository.ClanRepository;
import io.entix.data.repository.ClanRewardRepository;
import io.entix.data.reward.ClanReward;
import io.entix.plugin.menu.ClanDefaultMenu;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClanService {

    ClanPlugin plugin;
    ClanRepository clanRepository;
    ClanMemberRepository clanMemberRepository;
    ClanRewardRepository clanRewardRepository;

    Map<String, Clan> loadedClans;
    Map<UUID, ClanReward> loadedClanRewards;

    List<EntityType> availableEntities;
    ClanDefaultMenu clanDefaultMenu;

    public ClanService(ClanPlugin plugin) {
        this.plugin = plugin;
        this.clanRepository = plugin.getMongoManager().create(ClanRepository.class);
        this.clanMemberRepository = plugin.getMongoManager().create(ClanMemberRepository.class);
        this.clanRewardRepository = plugin.getMongoManager().create(ClanRewardRepository.class);
        this.loadedClans = new ConcurrentHashMap<>();
        this.loadedClanRewards = new ConcurrentHashMap<>();
        this.availableEntities = List.of(EntityType.ZOMBIE, EntityType.HORSE);
        this.clanDefaultMenu = new ClanDefaultMenu(plugin);
        onStart();
    }

    public void onStart() {
        //Load all rewards into runtime cache
        for (ClanReward reward : clanRewardRepository.findAll()) {
            UUID rewardId = reward.getRewardId();
            loadedClanRewards.putIfAbsent(rewardId, reward);
        }

        if (clanRepository == null) return;
        plugin.getExecutorService().execute(() -> {
            for (Clan clan : clanRepository.findAll()) {
                List<ClanMember> membersByClan = clanMemberRepository.findManyByClanName(clan.getClanName());
                membersByClan.forEach(clanMember -> clan.getClanMembers().put(clanMember.getMemberId(), clanMember));
                loadedClans.put(clan.getClanName(), clan);
            }
        });
    }

    public void onStop() {
    }

    public void createClan(@NonNull String clanName, @NonNull String clanTag, @NonNull Player clanCreator) {
        Clan clan = findClanByName(clanName);
        if (clan != null) return;

        clan = new Clan();
        clan.setClanId(UUID.randomUUID());
        clan.setClanName(clanName);
        clan.setClanTag(clanTag);
        clan.setClanCreator(clanCreator.getUniqueId());

        ClanMember clanMember = clan.addMember(clanCreator.getUniqueId(), ClanRank.OWNER);
        if (clanMember == null) throw new RuntimeException("ClanMember could not be created.");
        clanMember.setLastSeenName(clanCreator.getName());
        clanMember.setOnline(true);

        saveOrUpdateClan(clan);
    }

    public boolean deleteClan(@NonNull Clan clan) {
        if (loadedClans.containsKey(clan.getClanName())) return false;

        plugin.getExecutorService().execute(() -> {
            for (ClanMember clanMember : clan.getClanMembers().values()) {
                clanMemberRepository.delete(clanMember);
            }

            clanRepository.delete(clan);
        });

        loadedClans.remove(clan.getClanName());
        return true;
    }

    public void saveOrUpdateClan(@NonNull Clan clan) {
        plugin.getExecutorService().execute(() -> {
            loadedClans.put(clan.getClanName(), clan);
            clanRepository.save(clan);
            for (ClanMember clanMember : clan.getClanMembers().values()) {
                clanMemberRepository.save(clanMember);
            }
        });
    }

    public @Nullable Clan findClanByName(@NonNull String clanName) {
        return loadedClans.get(clanName);
    }

    public @Nullable Clan findClanByTag(@NonNull String clanTag) {
        return loadedClans.values()
                .stream()
                .filter(clan -> clan.getClanTag().equals(clanTag))
                .findFirst()
                .orElse(null);
    }

    public @Nullable Clan findClanByUniqueId(@NonNull UUID uniqueId) {
        return loadedClans.values().stream()
                .filter(clan -> clan.getClanMembers().containsKey(uniqueId))
                .findFirst()
                .orElse(null);
    }

    public @Nullable ClanReward findClanRewardById(@NonNull UUID rewardId) {
        return loadedClanRewards.get(rewardId);
    }


}
