package corgitaco.corgilib.client.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import corgitaco.corgilib.client.AnnouncementInfo;
import corgitaco.corgilib.client.AnnouncementInfoClientTicker;

public class CorgiLibClientCommands {


    public static void registerClientCommands(CommandDispatcher<?> dispatcher) {
        LiteralArgumentBuilder corgilib = LiteralArgumentBuilder.literal("corgilib_client");

        corgilib.then(LiteralArgumentBuilder.literal("announcement").then(LiteralArgumentBuilder.literal("dismiss").requires(o -> AnnouncementInfoClientTicker.canRunDismissCommand()).executes(context -> {
            if (AnnouncementInfoClientTicker.canRunDismissCommand()) {
                AnnouncementInfo.saveStoredAnnouncementInfo();
                return 1;
            } else {
                return 0;
            }
        })));

        dispatcher.register(corgilib);
    }
}
