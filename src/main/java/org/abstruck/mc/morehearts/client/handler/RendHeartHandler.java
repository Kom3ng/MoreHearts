package org.abstruck.mc.morehearts.client.handler;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.abstruck.mc.morehearts.client.gui.HealthGui;
import org.apache.logging.log4j.LogManager;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber
public class RendHeartHandler {
    @SubscribeEvent
    public static void onRendHeart(@NotNull RenderGameOverlayEvent event){
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) return;

        HealthGui.INSTANCE.render(new MatrixStack());
    }
}
