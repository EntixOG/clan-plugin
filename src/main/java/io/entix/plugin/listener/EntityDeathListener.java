package io.entix.plugin.listener;

import io.entix.ClanPlugin;
import io.entix.data.clan.Clan;
import io.entix.data.clan.achievements.ClanAchievement;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public record EntityDeathListener(ClanPlugin plugin) implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        EntityType entityType = event.getEntityType();
        if (!plugin.getClanService().getAvailableEntities().contains(entityType)) return;

        Player killer = event.getEntity().getKiller();
        if (killer == null) return;

        Clan clan = plugin.getClanService().findClanByUniqueId(killer.getUniqueId());
        if (clan == null) return;

        clan.addKill(entityType);

        ClanAchievement firstMobKillAchievement = ClanAchievement.FIRST_MOB_KILL;
        if (!clan.unlockAchievement(firstMobKillAchievement)) return;
        clan.sendClanMessage("Neues Achievement freigeschaltet -> " + firstMobKillAchievement.getTranslation());
    }
}
