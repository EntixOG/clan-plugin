package io.entix;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.bukkit.LiteBukkitMessages;
import eu.koboo.en2do.Credentials;
import eu.koboo.en2do.MongoManager;
import io.entix.clan.ClanService;
import io.entix.clan.command.ClanCommand;
import io.entix.mongo.LocationMongoCodec;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClanPlugin extends JavaPlugin {

    ClanPlugin plugin;
    MongoManager mongoManager;

    LiteCommands<CommandSender> liteCommands;

    ExecutorService executorService;
    ClanService clanService;

    @Override
    public void onLoad() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        if (plugin == null) return;
        saveDefaultConfig();

        String connectionString = getConfig().getString("mongo.url");
        String database = getConfig().getString("mongo.database");

        if (connectionString == null || connectionString.isEmpty() || connectionString.equals("~")) {
            getLogger().severe("Der Connection String ist in der config.yml nicht gesetzt!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if (database == null || database.isEmpty() || database.equals("~")) {
            getLogger().severe("Es ist keine Datenbank in der config.yml nicht gesetzt!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        mongoManager = new MongoManager(Credentials.of(connectionString, database));
        mongoManager.registerCodec(new LocationMongoCodec());

        liteCommands = LiteBukkitFactory.builder("clans", this)
                .message(LiteBukkitMessages.PLAYER_ONLY, "Dieser Befehl kann nur Ingame verwendet werden.")
                .message(LiteBukkitMessages.MISSING_PERMISSIONS, "Für diese Aktion besitzt du keine Rechte.")
                .commands(new ClanCommand(this)).build();

        executorService = Executors.newFixedThreadPool(4);
        clanService = new ClanService(this);
        clanService.onStart();
    }

    @Override
    public void onDisable() {
        if (plugin == null) return;
        if (clanService != null) clanService.onStop();
        if (mongoManager != null) mongoManager.close();
    }
}
