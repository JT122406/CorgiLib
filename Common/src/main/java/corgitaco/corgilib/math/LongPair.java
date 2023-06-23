package corgitaco.corgilib.math;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record LongPair(long val1, long val2) {

    public boolean isInBetween(long l) {
        return l >= this.val1 && l <= this.val2;
    }

    public static Codec<LongPair> createLongPairCodec(String val1Name, String val2Name) {
        return RecordCodecBuilder.create(builder -> builder.group(Codec.LONG.fieldOf(val1Name).forGetter(longPair -> longPair.val1), Codec.LONG.fieldOf(val2Name).forGetter(longPair -> longPair.val2)).apply(builder, LongPair::new));
    }

    @Override
    public String toString() {
        return val1 + " - " + val2;
    }
}
