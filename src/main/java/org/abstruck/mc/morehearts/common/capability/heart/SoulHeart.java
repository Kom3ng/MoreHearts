package org.abstruck.mc.morehearts.common.capability.heart;

import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.jetbrains.annotations.NotNull;

public class SoulHeart extends ExtraHeart{
    @Override
    public @NotNull String getRegistryName() {
        return "soul_heart";
    }

    @Override
    public int getUOffSet() {
        return 0;
    }

    @Override
    public int getVOffSet() {
        return 9;
    }

    @Override
    public float getDecelerationMagnification() {
        return 1.0F;
    }

    @Override
    public void activate(LivingHurtEvent event) {
        System.out.println("soul heart activate");
    }
}
