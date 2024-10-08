package io.entix;

import eu.koboo.en2do.Credentials;
import eu.koboo.en2do.MongoManager;
import io.entix.clan.ClanService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.bson.codecs.jsr310.LocalDateCodec;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClanPlugin extends JavaPlugin {

    ClanPlugin plugin;
    MongoManager mongoManager;

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
        mongoManager.registerCodec(new LocalDateCodec());

        executorService = Executors.newFixedThreadPool(4);
        clanService = new ClanService(this);
        clanService.onStart();
    }

    @Override
    public void onDisable() {
        if (plugin == null) return;
        if (mongoManager != null) mongoManager.close();
        if (clanService != null) clanService.onStop();
    }
}
