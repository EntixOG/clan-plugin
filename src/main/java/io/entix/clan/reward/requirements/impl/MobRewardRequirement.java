package io.entix.clan.reward.requirements.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.entix.ClanPlugin;
import io.entix.clan.reward.requirements.AbstractRewardRequirement;
import io.entix.clan.reward.requirements.type.RewardRequirementType;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.entity.EntityType;

@Setter
public class MobRewardRequirement extends AbstractRewardRequirement {

    EntityType entityType;
    int value;

    public MobRewardRequirement(ClanPlugin plugin) {
        super(plugin, RewardRequirementType.MONSTER_KILL);
    }

    @Override
    public String encode() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", entityType.name());
        jsonObject.addProperty("value", String.valueOf(value));
        return jsonObject.toString();
    }

    @Override
    public void decode(@NonNull String encodedString) {
        JsonObject jsonObject = JsonParser.parseString(encodedString).getAsJsonObject();
        if (!jsonObject.has("type") && !jsonObject.has("value")) {
            throw new RuntimeException("Failed to read 'PvERewardRequirement' from String: " + encodedString);
        }
        String entityTypeString = jsonObject.get("type").getAsString();
        EntityType entityType = EntityType.fromName(entityTypeString);
        if (entityType == null) {
            throw new RuntimeException("Failed to find EntityType from String: " + entityTypeString);
        }

        this.entityType = entityType;
        this.value = jsonObject.get("value").getAsInt();
    }

    @Override
    public String description() {
        return " <dark_gray>- <gray>Mob-Kills: <green>" + value + " <dark_gray>❘ <gray>Type: <green>" + entityType.name();
    }

    @Override
    public boolean isRequirementComplete() {
        return false;
    }
}