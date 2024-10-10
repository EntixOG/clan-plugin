package io.entix.plugin.menu;

import io.entix.ClanPlugin;
import io.entix.data.clan.Clan;
import io.entix.data.clan.member.ClanMember;
import io.entix.data.clan.member.permission.ClanPermission;
import io.entix.utility.MenuUtility;
import io.entix.utility.builder.ItemBuilder;
import io.github.rysefoxx.inventory.plugin.content.IntelligentItem;
import io.github.rysefoxx.inventory.plugin.content.InventoryContents;
import io.github.rysefoxx.inventory.plugin.content.InventoryProvider;
import io.github.rysefoxx.inventory.plugin.pagination.Pagination;
import io.github.rysefoxx.inventory.plugin.pagination.RyseInventory;
import io.github.rysefoxx.inventory.plugin.pagination.SlotIterator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClanMemberOverview implements InventoryProvider {

    ClanPlugin plugin;

    Clan clan;
    ClanMember clanMember;

    SimpleDateFormat simpleDateFormat;
    RyseInventory ryseInventory;

    public ClanMemberOverview(ClanPlugin plugin, Clan clan, ClanMember clanMember) {
        this.plugin = plugin;
        this.clan = clan;
        this.clanMember = clanMember;
        this.simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        this.ryseInventory = RyseInventory.builder()
                .title("Clan | Mitglieder")
                .rows(6)
                .provider(this)
                .disableUpdateTask()
                .build(plugin);
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        MenuUtility.defaultInventory(contents, 6);

        SlotIterator slotIterator = SlotIterator.builder()
                .startPosition(2, 2)
                .blackList(25, 26, 27, 28)
                .type(SlotIterator.SlotIteratorType.HORIZONTAL)
                .build();

        Pagination pagination = contents.pagination();
        pagination.setItemsPerPage(10);
        pagination.iterator(slotIterator);

        List<IntelligentItem> clickableItems = new ArrayList<>();
        for (ClanMember clanMember : clan.getClanMembers().values()) {
            clickableItems.add(IntelligentItem.of(ItemBuilder.of(Material.PLAYER_HEAD)
                    .skullOwner(clanMember.getMemberId())
                    .displayName("§8» §7" + clanMember.getLastSeenName() + " §8(" + (clanMember.isOnline() ? "§aOnline" : "§cOffline") + "§8)")
                    .lore("",
                            " §8› §7Rang: §b" + clanMember.getClanRank().getRankName(),
                            " §8› §7Beitritt: §b" + simpleDateFormat.format(clanMember.getJoiningDate())).build(), event -> {

            }));
        }

        int emptySlots = 0;
        while ((clickableItems.size() % 10) != 0 || clickableItems.isEmpty()) {
            if (emptySlots < clan.getMaxClanMembers()) {
                clickableItems.add(IntelligentItem.empty(ItemBuilder.of(Material.IRON_BARS).displayName("§cKein Mitglied gefunden.").build()));
            } else {
                clickableItems.add(IntelligentItem.empty(ItemBuilder.of(Material.IRON_BARS).displayName("§cNicht Verfügbar").lore("§7§oSchalte diesen Slot bei Upgrades frei.").build()));
            }
            emptySlots++;
        }

        pagination.setItems(clickableItems);

        contents.set(2, 8, MenuUtility.nextButton(pagination));
        contents.set(2, 0, MenuUtility.previousButton(pagination));
        contents.set(5, 4, MenuUtility.backButton(new ClanMenu(plugin, clanMember, clan).getRyseInventory()));

        if (!clanMember.hasPermission(ClanPermission.INVITE)) return;
        contents.set(1, 4, IntelligentItem.of(ItemBuilder.of(Material.SLIME_BALL)
                .displayName("§8➥ §6Spieler Einladen")
                .lore("§aKlicke§7, um einen Spieler einzuladen.").build(), event -> {

        }));
    }
}
