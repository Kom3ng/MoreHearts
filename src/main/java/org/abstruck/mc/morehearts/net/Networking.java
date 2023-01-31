package org.abstruck.mc.morehearts.net;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.abstruck.mc.morehearts.common.event.BaseSynchronousEvent;
import org.abstruck.mc.morehearts.net.pack.SynchronousEventPack;
import org.abstruck.mc.morehearts.utils.ModUtil;

public class Networking extends BaseNetworking{
    public static SimpleChannel INSTANCE;
    public static final String VERSION = "1.0";

    public static <E extends BaseSynchronousEvent> void registerMessage(){
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(ModUtil.MOD_ID,"event_channel"),
                () -> VERSION,
                (version) -> version.equals(VERSION),
                (version) -> version.equals(VERSION)
        );

        INSTANCE.messageBuilder(SynchronousEventPack.class,nextId())
                .decoder(SynchronousEventPack::decode)
                .encoder(SynchronousEventPack<E>::encode)
                .consumer(SynchronousEventPack<E>::handle)
                .add();
    }
}
