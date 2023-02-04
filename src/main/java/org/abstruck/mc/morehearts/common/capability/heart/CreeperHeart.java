package org.abstruck.mc.morehearts.common.capability.heart;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.Explosion;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.jetbrains.annotations.NotNull;

public class CreeperHeart extends ExtraHeart{
    @Override
    public @NotNull String getRegistryName() {
        return "creeper_heart";
    }

    @Override
    public int getUOffSet() {
        return 18;
    }

    @Override
    public int getVOffSet() {
        return 9;
    }

    @Override
    public float getDecelerationMagnification() {
        return 0.9F;
    }

    @Override
    public void activate(@NotNull LivingHurtEvent event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity)) return;
        Entity attackEntity = event.getSource().getEntity();
        attackEntity.level.explode(event.getEntity(),attackEntity.getX(),attackEntity.getY(),attackEntity.getZ(),4,true, Explosion.Mode.BREAK);
    }
}
