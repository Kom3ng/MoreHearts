package org.abstruck.mc.morehearts.common.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import org.abstruck.mc.morehearts.common.capability.player.IPlayerCapability;

public class ModCapability {
    @CapabilityInject(IPlayerCapability.class)
    public static Capability<IPlayerCapability> PLAYER_CAP;
}
