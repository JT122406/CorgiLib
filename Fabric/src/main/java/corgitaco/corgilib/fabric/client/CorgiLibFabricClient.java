package corgitaco.corgilib.fabric.client;

import corgitaco.corgilib.client.AnnouncementInfo;
import corgitaco.corgilib.client.screen.widget.AnnouncementWidget;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.network.chat.Component;

public class CorgiLibFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (AnnouncementInfo.INSTANCE.getNow(null) != null) {
                screen.addRenderableWidget(new AnnouncementWidget(scaledWidth, scaledHeight, 25, 25, Component.literal("")));
            }
        });
    }
}
