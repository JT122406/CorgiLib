package corgitaco.corgilib.client;

import corgitaco.corgilib.config.AnnouncementConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.launch.MixinBootstrap;

import java.util.concurrent.ThreadLocalRandom;

public class AnnouncementInfoClientTicker {

    private static int announcementTimer = ThreadLocalRandom.current().nextInt(1200, 6000);

    public static void checkedAnnouncementTicker(Player eventPlayer) {
        if (eventPlayer == Minecraft.getInstance().player && AnnouncementConfig.INSTANCE.get().announcementDelivery() == AnnouncementConfig.AnnouncementDelivery.CHAT && AnnouncementInfo.getInstance() != null) {
            AnnouncementInfoClientTicker.announcementTicker(eventPlayer);
        }
    }

    public static boolean canRunDismissCommand() {
        return announcementTimer <= 0 && AnnouncementConfig.INSTANCE.get().announcementDelivery() == AnnouncementConfig.AnnouncementDelivery.CHAT;
    }

    public static void announcementTicker(Player player) {
        AnnouncementInfo announcementInfo = AnnouncementInfo.getInstance();
        if (announcementInfo != null) {
            if (announcementTimer > 0) {
                announcementTimer--;
                if (announcementTimer == 0) {
                    player.displayClientMessage(announcementInfo.title(), false);
                    player.displayClientMessage(Component.empty(), false);
                    player.displayClientMessage(announcementInfo.desc(), false);
                    Component component = announcementInfo.actionButtonText();
                    MutableComponent open = ComponentUtils.wrapInSquareBrackets(component.copy().withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, announcementInfo.url())).withBold(true).withColor(ChatFormatting.GREEN)));
                    player.displayClientMessage(Component.empty(), false);
                    MutableComponent dismiss = Component.literal("Dismiss").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY).withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/corgilib_client announcement dismiss")));
                    player.displayClientMessage(Component.literal("").append(open).append(" | ").append(dismiss), false);
                }
            }
        }
    }
}
