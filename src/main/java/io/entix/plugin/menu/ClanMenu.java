package io.entix.plugin.menu;

import io.entix.ClanPlugin;
import io.entix.data.clan.Clan;
import io.entix.data.clan.member.ClanMember;
import io.entix.utility.MenuUtility;
import io.entix.utility.builder.ItemBuilder;
import io.github.rysefoxx.inventory.plugin.content.IntelligentItem;
import io.github.rysefoxx.inventory.plugin.content.InventoryContents;
import io.github.rysefoxx.inventory.plugin.content.InventoryProvider;
import io.github.rysefoxx.inventory.plugin.pagination.RyseInventory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.bukkit.Material;
import org.bukkit.entity.Player;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClanMenu implements InventoryProvider {

    ClanPlugin plugin;

    Clan clan;
    ClanMember clanMember;

    RyseInventory ryseInventory;

    String discordHeadUrl = "http://textures.minecraft.net/texture/7da9449cea12119c22ca5b93c28cbe9df31521ca159a5ea5faa5ffb5a1780f57";

    public ClanMenu(ClanPlugin plugin, ClanMember clanMember, Clan clan) {
        this.plugin = plugin;
        this.clanMember = clanMember;
        this.clan = clan;
        this.ryseInventory = RyseInventory.builder()
                .title("Clan | " + clan.getClanName())
                .rows(6)
                .provider(this)
                .disableUpdateTask()
                .build(plugin);
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        MenuUtility.defaultInventory(contents, 6);

        contents.set(0, 4, IntelligentItem.empty(ItemBuilder.of(Material.WRITABLE_BOOK)
                .displayName("§8➥ §f§lClan Information")
                .lore("").build()));

        contents.set(2, 4, IntelligentItem.of(ItemBuilder.of(Material.TOTEM_OF_UNDYING)
                        .displayName("§8➥ §a§lMitglieder")
                        .lore("", "§aKlicke§7, um die Clanmitglieder anzuzeigen.").build(),
                event -> new ClanMemberOverview(plugin, clan, clanMember).getRyseInventory().open(player)));

        contents.set(2, 6, IntelligentItem.of(ItemBuilder.of(Material.SUNFLOWER)
                .displayName("§8➥ §e§lClan Bank")
                .lore("", "§aKlicke§7, um die Clan-Bank zu öffnen.").build(), event -> {

        }));

        contents.set(3, 7, IntelligentItem.of(ItemBuilder.of(Material.EMERALD)
                .displayName("§8➥ §2§lClan Upgrades")
                .lore("", "§aKlicke§7, um die Clan Upgrades zu öffnen.").build(), event -> {

        }));

        contents.set(4, 5, IntelligentItem.of(ItemBuilder.of(Material.PLAYER_HEAD)
                .skullUrl(discordHeadUrl)
                .displayName("§8➥ §9§lClan Discord")
                .lore("", "§aKlicke§7, wenn du dem Channel beitreten möchtest.").build(), event -> {

        }));

        contents.set(4, 3, IntelligentItem.of(ItemBuilder.of(Material.COMMAND_BLOCK)
                .displayName("§8➥ §c§lEinstellungen")
                .lore("", "§aKlicke§7, um die Clan Einstellungen zu öffnen.").build(), event -> {

        }));

        contents.set(3, 1, IntelligentItem.of(ItemBuilder.of(Material.SPYGLASS)
                .displayName("§8➥ §d§lClan Erfolge")
                .lore("", "§aKlicke§7, um die Clan Erfolge zu sehen.").build(), event -> {

        }));

        contents.set(2, 2, IntelligentItem.of(ItemBuilder.of(Material.CHEST)
                .displayName("§8➥ §6§lClan Belohnungen")
                .lore("", "§aKlicke§7, um die Clan Belohnungen zu öffnen.").build(), event -> {

        }));
    }
}
