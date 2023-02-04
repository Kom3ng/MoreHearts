package org.abstruck.mc.morehearts.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.abstruck.mc.morehearts.client.gui.HealthGui;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class RendHeartHandler {
    @SubscribeEvent
    public static void onRendHeart(@NotNull RenderGameOverlayEvent.Post event){
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) return;
        if (Minecraft.getInstance().gameMode == null || !Minecraft.getInstance().gameMode.canHurtPlayer()) return;
        if (!ForgeIngameGui.renderHealth) return;
        HealthGui.INSTANCE.render(event.getMatrixStack());
    }

    @SubscribeEvent
    public static void setRandom(RenderGameOverlayEvent.@NotNull Pre event){
        if (event.getType() != RenderGameOverlayEvent.ElementType.HEALTH) return;
        if (Minecraft.getInstance().gameMode == null || !Minecraft.getInstance().gameMode.canHurtPlayer()) return;
        if (!ForgeIngameGui.renderHealth) return;
        HealthGui.INSTANCE.random = new Random(Minecraft.getInstance().gui.getGuiTicks() * 312871L);
    }
}
