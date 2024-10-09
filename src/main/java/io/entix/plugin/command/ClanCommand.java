package io.entix.plugin.command;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import io.entix.ClanPlugin;
import io.entix.data.clan.Clan;
import io.entix.validator.impl.ClanNameValidator;
import io.entix.validator.impl.ClanTagValidator;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

@Command(name = "clan", aliases = {"clans"})
public record ClanCommand(ClanPlugin plugin) {

    @Execute(name = "create")
    public void onClanCreate(@Context Player player, @Arg("clanName") String clanName, @Arg("clanTag") String clanTag) {
        Clan clan = plugin.getClanService().findClanByUniqueId(player.getUniqueId());
        if (clan != null) {
            player.sendMessage(Component.text("<red>Du bist schon Mitglied in einem Clan."));
            return;
        }

        clan = plugin.getClanService().findClanByName(clanName);
        if (clan == null) {
            player.sendMessage(Component.text("<red>Es wurde ein Clan mit diesem Namen bereits erstellt."));
            return;
        }

        ClanNameValidator nameValidator = new ClanNameValidator(clanName);
        if (!nameValidator.isValid()) {
            player.sendMessage(Component.text("<red>Der Clan Name ist nicht gültig."));
            return;
        }

        ClanTagValidator tagValidator = new ClanTagValidator(clanName);
        if (!tagValidator.isValid()) {
            player.sendMessage(Component.text("<red>Der Clan Tag ist nicht gültig."));
            return;
        }

        //TODO

    }
}
