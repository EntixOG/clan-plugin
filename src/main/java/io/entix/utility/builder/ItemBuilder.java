package io.entix.utility.builder;

import com.destroystokyo.paper.profile.PlayerProfile;
import lombok.*;
import lombok.experimental.FieldDefaults;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerTextures;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnegative;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemBuilder {

    ItemStack itemStack;

    Component displayName;
    Material material;
    int amount = 1;
    ItemMeta itemMeta;

    OfflinePlayer skullOwner;
    String skullUrl;

    List<ItemFlag> flags = new ArrayList<>();
    List<Component> lore = new ArrayList<>();
    Map<Enchantment, Integer> enchantments = new HashMap<>();
    boolean unbreakable;

    MiniMessage miniMessage = MiniMessage.miniMessage();

    private ItemBuilder(@NonNull ItemStack itemStack) {
        this.itemStack = itemStack;
        this.material = itemStack.getType();
        this.amount = itemStack.getAmount();
        this.itemMeta = itemStack.getItemMeta();

        if (this.itemMeta == null)
            this.itemMeta = Bukkit.getItemFactory().getItemMeta(this.material);

        if (this.itemMeta != null && this.itemMeta.hasLore())
            this.lore = this.itemMeta.lore();

        if (this.itemMeta != null && this.itemMeta.hasEnchants())
            this.enchantments = this.itemMeta.getEnchants();

        if (this.itemMeta != null && this.itemMeta.hasDisplayName())
            this.displayName = this.itemMeta.displayName();

        if (this.itemMeta != null && !this.itemMeta.getItemFlags().isEmpty())
            this.flags = Arrays.asList(this.itemMeta.getItemFlags().toArray(new ItemFlag[0]));

        if (this.itemMeta instanceof SkullMeta)
            this.skullOwner = ((SkullMeta) this.itemMeta).getOwningPlayer();
    }

    private ItemBuilder(@NonNull Material material) {
        this.material = material;
    }

    private ItemBuilder(@NonNull Material material, @NonNull Component displayName) {
        this.material = material;
        this.displayName = displayName;
    }

    @Contract("_ -> new")
    public static @NonNull ItemBuilder of(@NonNull Material material) {
        return new ItemBuilder(material);
    }

    public static @NonNull ItemBuilder of(@NonNull Material material, @NonNull Component component) {
        return new ItemBuilder(material, component);
    }

    @Contract("_ -> new")
    public static @NonNull ItemBuilder of(@NonNull ItemStack itemStack) {
        return new ItemBuilder(itemStack);
    }

    public ItemBuilder unbreakable() {
        this.unbreakable = true;
        return this;
    }

    public ItemBuilder material(@NonNull Material material) {
        this.material = material;
        return this;
    }

    public ItemBuilder skullUrl(@NonNull String url) {
        this.skullUrl = url;
        return this;
    }

    public ItemBuilder amount(@Nonnegative int amount) {
        if (amount > this.material.getMaxStackSize() || amount > 64) {
            amount = this.material.getMaxStackSize();
        }
        if (amount < 0) {
            amount = 1;
        }

        this.amount = amount;
        return this;
    }

    public ItemBuilder displayName(@NonNull String displayName) {
        if (displayName.contains("§")) {
            this.displayName = Component.text(displayName);
            return this;
        }
        this.displayName = miniMessage.deserialize(displayName);
        return this;
    }

    public ItemBuilder clearLore() {
        if (this.lore == null || this.lore.isEmpty()) return this;
        this.lore.clear();
        this.itemMeta.lore(this.lore);
        return this;
    }

    public ItemBuilder itemFlag(@NonNull ItemFlag itemFlag) {
        this.flags.add(itemFlag);
        return this;
    }

    public ItemBuilder itemFlags(ItemFlag @NonNull ... itemFlag) {
        this.flags.addAll(Arrays.asList(itemFlag));
        return this;
    }

    public ItemBuilder allItemFlags() {
        this.flags.addAll(Arrays.asList(ItemFlag.values()));
        return this;
    }

    public ItemBuilder enchantment(@NonNull Enchantment enchantment, @Nonnegative int value) {
        this.enchantments.put(enchantment, value);
        return this;
    }

    public ItemBuilder lore(@NonNull String line) {
        if (line.contains("§")) {
            this.lore.add(Component.text(line));
            return this;
        }
        this.lore.add(miniMessage.deserialize(line));
        return this;
    }

    public ItemBuilder lore(String @NonNull ... lines) {
        List<Component> lineComponents = new ArrayList<>();
        for (String line : lines) {
            Component component = miniMessage.deserialize(line);
            if (line.contains("§")) {
                component = Component.text(line);
            }
            lineComponents.add(component);
        }
        this.lore = lineComponents;
        return this;
    }

    public ItemBuilder lore(@Nullable List<String> loreStrings) {
        if (loreStrings == null) return this;
        List<Component> components = new ArrayList<>();
        for (String line : loreStrings) {
            Component component = Component.text(line);
            if (!line.contains("§")) {
                component = miniMessage.deserialize(line);
            }
            components.add(component.decoration(TextDecoration.ITALIC, false));
        }
        this.lore.addAll(components);
        return this;
    }

    public ItemBuilder skullOwner(@NonNull String string) {
        this.skullOwner = Bukkit.getOfflinePlayer(string);
        return this;
    }

    public ItemBuilder skullOwner(@NonNull UUID uuid) {
        this.skullOwner = Bukkit.getOfflinePlayer(uuid);
        return this;
    }

    public @NonNull ItemStack build() {
        if (itemStack == null)
            itemStack = new ItemStack(material, amount);

        if (itemMeta == null)
            itemMeta = itemStack.getItemMeta();

        itemMeta.setUnbreakable(unbreakable);

        if (displayName != null && itemMeta != null)
            itemMeta.displayName(displayName);

        if (!lore.isEmpty() && itemMeta != null)
            itemMeta.lore(lore);

        if (!flags.isEmpty())
            flags.forEach(itemFlag -> itemMeta.addItemFlags(itemFlag));

        if (!enchantments.isEmpty())
            enchantments.forEach((enchantment, level) -> itemMeta.addEnchant(enchantment, level, true));

        if (skullOwner != null && itemMeta instanceof SkullMeta skullMeta) {
            itemStack = itemStack.withType(Material.PLAYER_HEAD);
            skullMeta.setOwningPlayer(this.skullOwner);
            itemMeta = skullMeta;
        }

        itemStack.setAmount(this.amount);

        if (skullUrl != null && itemMeta instanceof SkullMeta skullMeta) {
            itemStack = itemStack.withType(Material.PLAYER_HEAD);
            PlayerProfile profile = Bukkit.createProfile(UUID.fromString("c68e6926-d111-421a-989e-7caa0197b17d"));
            PlayerTextures textures = profile.getTextures();
            URL urlObject;
            try {
                urlObject = new URL(skullUrl);
            } catch (MalformedURLException exception) {
                System.out.println("Invalid URL" + exception);
                itemStack.setItemMeta(itemMeta);
                return itemStack;
            }
            textures.setSkin(urlObject);
            profile.setTextures(textures);
            skullMeta.setPlayerProfile(profile);

            itemMeta = skullMeta;
        }

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
