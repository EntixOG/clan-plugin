package io.entix.data.reward.content.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.entix.data.reward.content.AbstractRewardContent;
import io.entix.data.reward.content.type.RewardContentType;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;

@Setter
public class MaterialRewardContent extends AbstractRewardContent {

    Material material;
    int itemAmount;

    public MaterialRewardContent() {
        super(RewardContentType.MATERIAL);
    }

    @Override
    public String encode() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("material", material.name());
        jsonObject.addProperty("value", String.valueOf(itemAmount));
        return jsonObject.toString();
    }

    @Override
    public void decode(@NonNull String encodedString) {
        JsonObject jsonObject = JsonParser.parseString(encodedString).getAsJsonObject();
        if (!jsonObject.has("material") && !jsonObject.has("value")) {
            throw new RuntimeException("Failed to read material from String: " + encodedString);
        }
        String materialAsString = jsonObject.get("material").getAsString();

        this.material = Material.valueOf(materialAsString);
        this.itemAmount = jsonObject.get("value").getAsInt();
    }

    @Override
    public String contentAsString() {
        return "<dark_gray>- <gray>Item: <yellow>" + material.name() + " <dark_gray>(<green>" + itemAmount + "<dark_gray>";
    }

    @Override
    public void collect(@NonNull Player player) {

    }
}