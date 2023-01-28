package org.abstruck.mc.morehearts.common.capability.heart;

import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.jetbrains.annotations.NotNull;

public class ZombieHeart extends AttachHeart{
    @Override
    public @NotNull String getRegistryName() {
        return "zombie_heart";
    }

    @Override
    public int getUOffSet() {
        return 0;
    }

    @Override
    public int getVOffSet() {
        return 0;
    }

    @Override
    public float getDecelerationMagnification() {
        return 0.5F;
    }

    @Override
    public void activate(@NotNull LivingHurtEvent event) {
    }
}
