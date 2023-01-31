package org.abstruck.mc.morehearts.common.capability.heart;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.jetbrains.annotations.NotNull;

public class PigHeart extends AttachHeart{
    @Override
    public @NotNull String getRegistryName() {
        return "pig_heart";
    }

    @Override
    public int getUOffSet() {
        return 18;
    }

    @Override
    public int getVOffSet() {
        return 0;
    }

    @Override
    public float getDecelerationMagnification() {
        return 0.7F;
    }

    @Override
    public void activate(LivingHurtEvent event) {
        PlayerEntity player = (PlayerEntity) event.getEntityLiving();
        player.level.playSound(player,player.getX(),player.getY(),player.getZ(), SoundEvents.PIG_AMBIENT, SoundCategory.AMBIENT,1.0F,1.0F);
    }
}
