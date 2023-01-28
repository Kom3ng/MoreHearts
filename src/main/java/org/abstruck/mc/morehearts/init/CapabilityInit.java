package org.abstruck.mc.morehearts.init;

import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.abstruck.mc.morehearts.common.capability.player.IPlayerCapability;
import org.abstruck.mc.morehearts.common.capability.player.PlayerCapability;
import org.jetbrains.annotations.NotNull;

public class CapabilityInit {
    public static void register(@NotNull FMLCommonSetupEvent event){
        event.enqueueWork(() -> {
            CapabilityManager.INSTANCE.register(IPlayerCapability.class,new PlayerCapability(),PlayerCapability::new);
        });
    }
}
