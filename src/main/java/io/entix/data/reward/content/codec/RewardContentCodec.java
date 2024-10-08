package io.entix.data.reward.content.codec;

import io.entix.data.reward.content.AbstractRewardContent;
import io.entix.data.reward.content.type.RewardContentType;
import lombok.NonNull;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class RewardContentCodec implements Codec<AbstractRewardContent> {

    @Override
    public AbstractRewardContent decode(BsonReader reader, DecoderContext decoderContext) {
        reader.readStartDocument();

        String rewardTypeName = reader.readString("rewardType");
        RewardContentType rewardContentType = RewardContentType.findRewardByName(rewardTypeName);
        if (rewardContentType == null) {
            throw new RuntimeException("Failed to parse reward type from String: " + rewardTypeName);
        }

        AbstractRewardContent rewardContent = instantiateRewardContent(rewardContentType);
        reader.readName("content");
        rewardContent.decode(reader.readString("content"));
        reader.readEndDocument();
        return rewardContent;
    }

    @Override
    public void encode(BsonWriter writer, AbstractRewardContent rewardContent, EncoderContext encoderContext) {
        writer.writeStartDocument();
        writer.writeString("rewardType", rewardContent.getRewardContentType().name());
        writer.writeString("content", rewardContent.encode());

        writer.writeEndDocument();
    }

    @Override
    public Class<AbstractRewardContent> getEncoderClass() {
        return AbstractRewardContent.class;
    }

    private AbstractRewardContent instantiateRewardContent(@NonNull RewardContentType rewardContentType) {
        try {
            return rewardContentType.getRewardContent().getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate reward content for type: " + rewardContentType, e);
        }
    }
}
