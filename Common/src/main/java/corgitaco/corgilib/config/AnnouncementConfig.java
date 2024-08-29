package corgitaco.corgilib.config;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonElement;
import blue.endless.jankson.JsonGrammar;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.api.SyntaxError;
import com.google.common.base.Suppliers;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import corgitaco.corgilib.CorgiLib;
import corgitaco.corgilib.platform.ModPlatform;
import corgitaco.corgilib.serialization.codec.CommentedCodec;
import corgitaco.corgilib.serialization.jankson.JanksonJsonOps;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Supplier;

public record AnnouncementConfig(AnnouncementDelivery announcementDelivery) {

    public static final Path PATH = ModPlatform.PLATFORM.modConfigDir().resolve("announcement_config.json5");

    public static final Codec<AnnouncementDelivery> DELIVERY_CODEC = Codec.STRING.xmap(s -> AnnouncementDelivery.valueOf(s.toUpperCase()), AnnouncementDelivery::name);

    public static final String COMMENT = """
            This has 2 acceptable values: [CHAT, WIDGET]
            * CHAT - Will post a message within 5 minutes to a user's chat once they've been in game for 5 minutes. Will no longer show after the user presses dismiss.
            * WIDGET - Displays a widget on screen with the announcement on all screens until the widget is dismissed.
            """;

    public static final Codec<AnnouncementConfig> CODEC = RecordCodecBuilder.create(announcementConfigInstance ->
            announcementConfigInstance.group(
                    CommentedCodec.of(DELIVERY_CODEC, "announcement_delivery", COMMENT).forGetter(AnnouncementConfig::announcementDelivery)
            ).apply(announcementConfigInstance, AnnouncementConfig::new)
    );

    public static final Supplier<AnnouncementConfig> INSTANCE = Suppliers.memoize(() -> {
        if (!PATH.toFile().exists()) {
            DataResult<JsonElement> jsonElementDataResult = CODEC.encodeStart(JanksonJsonOps.INSTANCE, new AnnouncementConfig(AnnouncementDelivery.WIDGET));

            if (jsonElementDataResult.error().isPresent()) {
                CorgiLib.LOGGER.error("Ignoring config %s due to errors: %s".formatted(PATH.toAbsolutePath(), jsonElementDataResult.error().orElseThrow().toString()));
                return new AnnouncementConfig(AnnouncementDelivery.WIDGET);
            }

            try {
                Files.createDirectories(PATH.getParent());
                Files.writeString(PATH, jsonElementDataResult.result().get().toJson(new JsonGrammar.Builder().withComments(true).build()));
            } catch (IOException e) {
                e.printStackTrace();
                CorgiLib.LOGGER.error("Ignoring config %s due to errors: %s".formatted(PATH.toAbsolutePath(), e.getLocalizedMessage()));
                return new AnnouncementConfig(AnnouncementDelivery.WIDGET);
            }
        } else {
            try {
                JsonObject load = Jankson.builder().build().load(PATH.toFile());
                DataResult<Pair<AnnouncementConfig, JsonElement>> decode = CODEC.decode(JanksonJsonOps.INSTANCE, load);

                if (decode.error().isPresent()) {
                    CorgiLib.LOGGER.error("Ignoring config %s due to errors: %s".formatted(PATH.toAbsolutePath(), decode.error().orElseThrow().toString()));
                    return new AnnouncementConfig(AnnouncementDelivery.WIDGET);
                }
                if (decode.result().isPresent()) {
                    return decode.result().orElseThrow().getFirst();
                }
            } catch (IOException | SyntaxError e) {
                e.printStackTrace();
                CorgiLib.LOGGER.error("Ignoring config %s due to errors: %s".formatted(PATH.toAbsolutePath(), e.getLocalizedMessage()));
                return new AnnouncementConfig(AnnouncementDelivery.WIDGET);
            }
        }

        return new AnnouncementConfig(AnnouncementDelivery.WIDGET);
    });


    public enum AnnouncementDelivery {
        CHAT,
        WIDGET
    }
}
