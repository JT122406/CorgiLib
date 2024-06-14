package corgitaco.corgilib.platform;

import com.google.auto.service.AutoService;
import corgitaco.corgilib.network.FabricNetworkHandler;
import corgitaco.corgilib.network.Packet;
import net.minecraft.server.level.ServerPlayer;

@AutoService(FabricPlatformNetwork.class)
public class FabricPlatformNetwork implements PlatformNetwork {
    @Override
    public <P extends Packet> void sendToClient(ServerPlayer player, P packet) {
        FabricNetworkHandler.sendToPlayer(player, packet);
    }

    @Override
    public <P extends Packet> void sendToServer(P packet) {
        FabricNetworkHandler.sendToServer(packet);
    }

}
