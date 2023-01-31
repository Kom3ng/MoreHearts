package org.abstruck.mc.morehearts.common.capability.heart;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.jetbrains.annotations.NotNull;

public class EvilHeart extends ExtraHeart{
    @Override
    public @NotNull String getRegistryName() {
        return "evil_heart";
    }

    @Override
    public int getUOffSet() {
        return 9;
    }

    @Override
    public int getVOffSet() {
        return 9;
    }

    @Override
    public float getDecelerationMagnification() {
        return 1.1F;
    }

    @Override
    public void activate(@NotNull LivingHurtEvent event) {
        if (event.getSource().getEntity() == null) return;
        event.getSource().getEntity().hurt(DamageSource.playerAttack((PlayerEntity) event.getEntityLiving()),event.getAmount()*3);
    }
}
