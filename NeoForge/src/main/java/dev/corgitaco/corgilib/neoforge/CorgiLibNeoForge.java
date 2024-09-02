package dev.corgitaco.corgilib.neoforge;

import corgitaco.corgilib.CorgiLib;
import corgitaco.corgilib.network.Packet;
import dev.corgitaco.corgilib.neoforge.platform.NeoForgePlatform;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.Map;

/**
 * Main class for the mod on the NeoForge platform.
 */
@Mod(CorgiLib.MOD_ID)
public class CorgiLibNeoForge {
    public CorgiLibNeoForge(IEventBus modEventBus) {
        modEventBus.addListener(this::commonSetup);
        CorgiLib.init();
        NeoForgePlatform.CACHED.values().forEach(deferredRegister -> deferredRegister.register(modEventBus));
    }



    private void commonSetup(final FMLCommonSetupEvent event) {
    }

}
