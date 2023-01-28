package org.abstruck.mc.morehearts.common.capability.heart;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.jetbrains.annotations.NotNull;

import javax.swing.text.html.parser.Entity;

public class BoneHeart extends AttachHeart{
    private static int uOffSet;
    private static int vOffSet;

    @Override
    public @NotNull String getRegistryName() {
        return "bone_heart";
    }

    public BoneHeart(){}

    @Override
    public int getUOffSet(){
        return uOffSet;
    }

    @Override
    public int getVOffSet(){
        return vOffSet;
    }

    @Override
    public float getDecelerationMagnification() {
        return 1.0F;
    }

    @Override
    public void activate(@NotNull LivingHurtEvent event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity)) return;
        if (!(event.getEntityLiving() instanceof PlayerEntity)) return;
        event.getEntity().hurt(DamageSource.playerAttack((PlayerEntity) event.getEntityLiving()),event.getAmount());
    }
}
