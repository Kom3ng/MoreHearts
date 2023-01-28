package org.abstruck.mc.morehearts.common.capability.heart;

import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface IHeart {
    @Contract(pure = true)
    @NotNull String getRegistryName();

    int getUOffSet();

    int getVOffSet();

    float getDecelerationMagnification();

    abstract void activate(LivingHurtEvent event);
}
