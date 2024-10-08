package io.entix;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClanPlugin extends JavaPlugin {

    ClanPlugin plugin;

    @Override
    public void onLoad() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        if (plugin == null) return;

    }

    @Override
    public void onDisable() {
        if (plugin == null) return;

    }
}
