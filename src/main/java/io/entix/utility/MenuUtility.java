package io.entix.utility;

import io.entix.utility.builder.ItemBuilder;
import io.github.rysefoxx.inventory.plugin.content.IntelligentItem;
import io.github.rysefoxx.inventory.plugin.content.InventoryContents;
import io.github.rysefoxx.inventory.plugin.pagination.Pagination;
import io.github.rysefoxx.inventory.plugin.pagination.RyseInventory;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnegative;
import java.util.List;

@UtilityClass
public class MenuUtility {

    public ItemStack blackDisplayPane = ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE).displayName("§r").build();
    public ItemStack coloredDisplayPane = ItemBuilder.of(Material.ORANGE_STAINED_GLASS_PANE).displayName("§r").build();

    public void defaultInventory(@NotNull InventoryContents contents, @Nonnegative int rows) {
        if (rows > 0 && rows <= 2) {
            return;
        }

        List.of(0, 8).forEach(position -> contents.set(position, blackDisplayPane));
        List.of(1, 7, 9, 17).forEach(position -> contents.set(position, coloredDisplayPane));

        switch (rows) {
            case 6 -> {
                List.of(45, 53).forEach(position -> contents.set(position, blackDisplayPane));
                List.of(36, 44, 46, 52).forEach(position -> contents.set(position, coloredDisplayPane));
            }
            case 5 -> {
                List.of(36, 44).forEach(position -> contents.set(position, blackDisplayPane));
                List.of(27, 35, 37, 43).forEach(position -> contents.set(position, coloredDisplayPane));
            }
            case 4 -> {
                List.of(27, 35).forEach(position -> contents.set(position, blackDisplayPane));
                List.of(18, 26, 28, 34).forEach(position -> contents.set(position, coloredDisplayPane));
            }
            case 3 -> {
                List.of(18, 26).forEach(position -> contents.set(position, blackDisplayPane));
                List.of(19, 25).forEach(position -> contents.set(position, coloredDisplayPane));
            }
        }
    }

    public @NotNull IntelligentItem previousButton(@NonNull Pagination pagination) {
        return IntelligentItem.of(ItemBuilder.of(Material.ARROW).displayName("§8« §7Vorherige Seite").build(), event -> {
            if (!(event.getWhoClicked() instanceof Player player)) return;

            if (pagination.isFirst()) {
                player.sendMessage(Component.text("§cDu befindest dich bereits auf der ersten Seite."));
                return;
            }

            RyseInventory currentInventory = pagination.inventory();
            currentInventory.open(player, pagination.previous().page());
        });
    }

    public @NotNull IntelligentItem nextButton(@NonNull Pagination pagination) {
        return IntelligentItem.of(ItemBuilder.of(Material.ARROW).displayName("§7Nächste Seite §8»").build(), event -> {
            if (!(event.getWhoClicked() instanceof Player player)) return;


            if (pagination.isLast()) {
                player.sendMessage(Component.text("§cDu befindest dich bereits auf der letzten Seite."));
                return;
            }

            RyseInventory currentInventory = pagination.inventory();
            currentInventory.open(player, pagination.next().page());
        });
    }

    public @NotNull IntelligentItem backButton(@NonNull RyseInventory inventory) {
        return IntelligentItem.of(ItemBuilder.of(Material.OAK_DOOR)
                .displayName("§cZurück")
                .lore("§aKlicke§7, um zum vorigen Menü zu gelangen.").build(), event -> inventory.open((Player) event.getWhoClicked()));
    }
}
