package io.entix.data.codec;

import io.entix.ClanPlugin;
import io.entix.data.reward.requirements.AbstractRewardRequirement;
import io.entix.data.reward.requirements.type.RewardRequirementType;
import lombok.NonNull;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.lang.reflect.Constructor;

public record RewardRequirementCodec(ClanPlugin plugin) implements Codec<AbstractRewardRequirement> {

    @Override
    public AbstractRewardRequirement decode(BsonReader reader, DecoderContext decoderContext) {
        reader.readStartDocument();

        String rewardTypeName = reader.readString("rewardType");
        RewardRequirementType rewardType = RewardRequirementType.findRewardByName(rewardTypeName);
        if (rewardType == null) {
            throw new RuntimeException("Failed to parse reward type from String: " + rewardTypeName);
        }

        AbstractRewardRequirement rewardRequirement = instantiateRewardContent(rewardType);
        reader.readName("content");
        rewardRequirement.decode(reader.readString("content"));
        reader.readEndDocument();
        return rewardRequirement;
    }

    @Override
    public void encode(BsonWriter writer, AbstractRewardRequirement rewardRequirement, EncoderContext encoderContext) {
        writer.writeStartDocument();
        writer.writeString("rewardType", rewardRequirement.getRequirementType().name());
        writer.writeString("content", rewardRequirement.encode());

        writer.writeEndDocument();
    }

    @Override
    public Class<AbstractRewardRequirement> getEncoderClass() {
        return AbstractRewardRequirement.class;
    }

    private AbstractRewardRequirement instantiateRewardContent(@NonNull RewardRequirementType requirementType) {
        try {
            Constructor<? extends AbstractRewardRequirement> constructorOfRequirement = requirementType
                    .getRewardRequirement()
                    .getConstructor(ClanPlugin.class);

            return constructorOfRequirement.newInstance(plugin);
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate reward content for type: " + requirementType, e);
        }
    }
}