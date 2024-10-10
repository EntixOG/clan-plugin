package io.entix.plugin.menu;

import io.entix.ClanPlugin;
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
public class ClanDefaultMenu implements InventoryProvider {

    ClanPlugin plugin;
    RyseInventory ryseInventory;

    public ClanDefaultMenu(ClanPlugin plugin) {
        this.plugin = plugin;
        this.ryseInventory = RyseInventory.builder()
                .title("Clans")
                .rows(5)
                .provider(this)
                .disableUpdateTask()
                .build(plugin);
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        MenuUtility.defaultInventory(contents, 3);

        contents.set(2, 2, IntelligentItem.of(ItemBuilder.of(Material.WRITABLE_BOOK)
                .displayName("§8➥ §9§lClan suchen")
                .lore("§7§o§7Hier hast du die Möglichkeit, nach", "§7§oeinem Clan zu suchen.").build(), event -> {

        }));

        contents.set(2, 4, IntelligentItem.of(ItemBuilder.of(Material.TURTLE_HELMET)
                .displayName("§8➥ §2§lClan Erstellen")
                .lore("§7§oGründe hier deinen eigenen Clan und baue", "§7§ogemeinsam mit deinen Freunden ein Imperium auf.").build(), event -> {

        }));

        contents.set(2, 6, IntelligentItem.of(ItemBuilder.of(Material.ARMOR_STAND)
                .displayName("§8➥ §6§lClan Ranking")
                .lore("§7§oSchaue hier dir die besten 10 Clans an.").build(), event -> {

        }));

        contents.updateOrSet(8, IntelligentItem.empty(ItemBuilder.of(Material.PAPER)
                .displayName("§8➥ §f§lClans Information")
                .lore("§7§oClans auf diesem Server sind dafür da, um mit",
                        "§7§oFreunden eine Gemeinschaft zu bilden, zusammen zu",
                        "§7§owachsen und gemeinsam an Events oder Kämpfen",
                        "§7§oteilzunehmen. Als Clan könnt ihr Ressourcen teilen",
                        "§7§ound euch gegen andere Clans behaupten").build()));

    }
}
