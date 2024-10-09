package io.entix.plugin.listener;

import io.entix.ClanPlugin;
import io.entix.data.clan.Clan;
import io.entix.data.clan.member.ClanMember;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public record PlayerJoinListener(ClanPlugin plugin) implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        plugin.getExecutorService().execute(() -> {
            Clan clan = plugin.getClanService().findClanByUniqueId(player.getUniqueId());
            if (clan == null) return;
            clan.sendClanMessage("<gray>Das Mitglied <gold>" + player.getName() + " <gray>ist nun <green>online<gray>.");
            ClanMember clanMember = clan.findClanMemberById(player.getUniqueId());
            if (clanMember == null) return;
            clanMember.setOnline(true);
        });
    }
}
