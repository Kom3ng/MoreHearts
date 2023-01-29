package org.abstruck.mc.morehearts.common.capability.heart;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.jetbrains.annotations.NotNull;

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
        if (event.getEntityLiving().level.isClientSide()) return;
        event.getEntityLiving().addEffect(new EffectInstance(Effects.DAMAGE_BOOST,3,1));
    }
}
