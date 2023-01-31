package org.abstruck.mc.morehearts.common.event;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.BusBuilder;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.IEventListener;
import net.minecraftforge.fml.network.PacketDistributor;
import org.abstruck.mc.morehearts.net.Networking;
import org.abstruck.mc.morehearts.net.pack.SynchronousEventPack;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

public class ModEventBus {
    private static final IEventBus EVENT_BUS = BusBuilder.builder().setExceptionHandler(ModEventBus::handleError).build();
    private static final String handleErrorMessage = "MoreHearts: catch an exception. you can report this to https://github.com/Kom3ng/MoreHearts/issues . or looking for help in our qq group: 454199689";
    private static void handleError(IEventBus iEventBus, Event event, IEventListener[] iEventListeners, int i, Throwable throwable){
        LogManager.getLogger().log(Level.ERROR,handleErrorMessage,throwable);
    }

    public static void post(Event event){
        MinecraftForge.EVENT_BUS.post(event);
    }
    public static void postSynchronousEvent(PacketDistributor.PacketTarget target, BaseSynchronousEvent event){
        Networking.INSTANCE.send(target,new SynchronousEventPack<>(event));
    }

    public static IEventBus getEventBus() {
        return EVENT_BUS;
    }
}
