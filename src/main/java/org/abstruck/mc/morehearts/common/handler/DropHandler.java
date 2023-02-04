package org.abstruck.mc.morehearts.common.handler;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.abstruck.mc.morehearts.init.ItemInit;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

@Mod.EventBusSubscriber
public class DropHandler {
    private static final Random random = new Random();
    @SubscribeEvent
    public static void onDrop(@NotNull LivingDropsEvent event){
        if (random.nextInt(100)>5) return;
        if (!(event.getSource().getEntity() instanceof PlayerEntity)) return;
        if (event.getEntity() instanceof ZombieEntity){
            spawnOneHeart(ItemInit.ZOMBIE_HEART.get(), event);
            return;
        }
        if (event.getEntity() instanceof PigEntity){
            spawnOneHeart(ItemInit.PIG_HEART.get(), event);
            return;
        }
        if (event.getEntity() instanceof SkeletonEntity){
            spawnOneHeart(ItemInit.BONE_HEART.get(), event);
            return;
        }
        if (event.getEntity() instanceof GhastEntity){
            spawnOneHeart(ItemInit.SOUL_HEART.get(), event);
            return;
        }
        if (event.getEntity() instanceof CreeperEntity){
            spawnOneHeart(ItemInit.CREEPER_HEART.get(), event);
            return;
        }
        if (event.getEntity() instanceof WitherSkeletonEntity){
            spawnOneHeart(ItemInit.EVIL_HEART.get(), event);
            return;
        }
        if (event.getEntity() instanceof CreatureEntity){
            if (random.nextInt(100)==0){
                event.getEntity().spawnAtLocation(new ItemStack(ItemInit.RED_HEART_CONTAINER.get()));
            }
        }
    }

    private static void spawnOneHeart(IItemProvider itemProvider, @NotNull LivingDropsEvent event){
        event.getEntity().spawnAtLocation(new ItemStack(itemProvider));
    }
}
