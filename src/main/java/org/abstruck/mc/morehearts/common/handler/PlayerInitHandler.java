package org.abstruck.mc.morehearts.common.handler;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;
import org.abstruck.mc.morehearts.common.capability.ModCapability;
import org.abstruck.mc.morehearts.common.event.SynchronousHeartsEvent;
import org.abstruck.mc.morehearts.common.event.ModEventBus;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber
public class PlayerInitHandler {
    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.@NotNull PlayerLoggedInEvent event){
        ModEventBus.postSynchronousEvent(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()), new SynchronousHeartsEvent(event.getPlayer(), true));
    }
    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.@NotNull PlayerRespawnEvent event){
        ModEventBus.postSynchronousEvent(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()), new SynchronousHeartsEvent(event.getPlayer(), true));
    }
    @SubscribeEvent
    public static void onPlayerChangeWorld(PlayerEvent.@NotNull PlayerChangedDimensionEvent event){
        ModEventBus.postSynchronousEvent(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()), new SynchronousHeartsEvent(event.getPlayer(), true));
    }
}
