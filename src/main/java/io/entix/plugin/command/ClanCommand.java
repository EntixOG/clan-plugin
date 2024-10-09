package io.entix.plugin.command;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import io.entix.ClanPlugin;
import io.entix.data.clan.Clan;
import io.entix.utility.validator.impl.ClanNameValidator;
import io.entix.utility.validator.impl.ClanTagValidator;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

@Command(name = "clan", aliases = {"clans"})
public record ClanCommand(ClanPlugin plugin) {

    @Execute(name = "einladen", aliases = {"create"})
    public void onClanCreate(@Context Player player, @Arg("clanName") String clanName, @Arg("clanTag") String clanTag) {
        Clan clan = plugin.getClanService().findClanByUniqueId(player.getUniqueId());
        if (clan != null) {
            player.sendMessage(Component.text("§cDu bist schon Mitglied in einem Clan."));
            return;
        }

        clan = plugin.getClanService().findClanByName(clanName);
        if (clan != null) {
            player.sendMessage(Component.text("§cEs wurde ein Clan mit diesem Namen bereits erstellt."));
            return;
        }

        ClanNameValidator nameValidator = new ClanNameValidator(clanName);
        if (!nameValidator.isValid()) {
            player.sendMessage(Component.text("§cDer Clan Name ist nicht gültig."));
            return;
        }

        ClanTagValidator tagValidator = new ClanTagValidator(clanName);
        if (!tagValidator.isValid()) {
            player.sendMessage(Component.text("§cDer Clan Tag ist nicht gültig."));
            return;
        }

        plugin.getClanService().createClan(clanName, clanTag, player);
        player.sendMessage(Component.text("§aDu hast einen Clan erstellt §8(§6" + clanName + "§8)"));
    }
}
