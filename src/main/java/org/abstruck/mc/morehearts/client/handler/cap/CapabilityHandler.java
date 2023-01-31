package org.abstruck.mc.morehearts.client.handler.cap;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.abstruck.mc.morehearts.common.capability.ModCapability;
import org.abstruck.mc.morehearts.common.event.ExtraHeartChangeEvent;
import org.abstruck.mc.morehearts.common.event.SynchronousHeartsEvent;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class CapabilityHandler {
    @SubscribeEvent
    public static void onExtraChange(@NotNull ExtraHeartChangeEvent event){
        if (event.isPosterRemote()){
            if (Minecraft.getInstance().player == null) return;
            Minecraft.getInstance().player.getCapability(ModCapability.PLAYER_CAP).ifPresent(cap -> cap.setExtraHearts(event.getToExtraHearts()));
        }
    }

    @SubscribeEvent
    public static void onSynchronous(@NotNull SynchronousHeartsEvent event){
        if (!event.isPosterRemote()) return;
        if (Minecraft.getInstance().player == null) return;
        Minecraft.getInstance().player.getCapability(ModCapability.PLAYER_CAP).ifPresent(cap -> {
            cap.setExtraHearts(event.getExtraHearts());
            cap.setAttachHearts(event.getAttachHearts());
        });
    }
}
