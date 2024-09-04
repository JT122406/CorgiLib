package corgitaco.corgilib.entity.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public record AnyConditionPassesCondition(List<Condition> filters) implements Condition {

    public static final Codec<AnyConditionPassesCondition> CODEC = RecordCodecBuilder.create(builder ->
            builder.group(
                    Condition.CODEC.listOf().fieldOf("filters").forGetter(AnyConditionPassesCondition::filters)
            ).apply(builder, AnyConditionPassesCondition::new)
    );

    @Override
    public Codec<? extends Condition> codec() {
        return CODEC;
    }

    @Override
    public boolean passes(ConditionContext conditionContext) {
        for (Condition filter : filters) {
            if (filter.passes(conditionContext)) {
                return true;
            }
        }
        return false;
    }
}
