package corgitaco.corgilib.forge.client;

import corgitaco.corgilib.CorgiLib;
import corgitaco.corgilib.client.AnnouncementInfo;
import corgitaco.corgilib.client.screen.widget.AnnouncementWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CorgiLib.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class CorgiLibForgeClientEvents {



    @SubscribeEvent
    public static void screenInit(ScreenEvent.Init.Post event) {
        Screen screen = event.getScreen();

        if (AnnouncementInfo.INSTANCE != null) {
            screen.addRenderableWidget(new AnnouncementWidget(screen.width, screen.height, 25, 25, Component.literal("")));
        }
    }
}
