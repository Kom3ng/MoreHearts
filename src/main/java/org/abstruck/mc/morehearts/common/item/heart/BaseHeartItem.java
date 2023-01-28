package org.abstruck.mc.morehearts.common.item.heart;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.abstruck.mc.morehearts.common.capability.ModCapability;
import org.abstruck.mc.morehearts.common.capability.heart.HeartRegister;
import org.abstruck.mc.morehearts.common.item.group.ModGroups;
import org.jetbrains.annotations.NotNull;

public abstract class BaseHeartItem extends Item {
    public BaseHeartItem() {
        super(new Properties().tab(ModGroups.HEART_GROUP));
    }

    abstract String getBindingHeartRegistryName();

    @Override
    public @NotNull ActionResult<ItemStack> use(@NotNull World level, @NotNull PlayerEntity player, @NotNull Hand hand) {
        ItemStack itemInHand = player.getItemInHand(hand);
        final ActionResult<ItemStack> PASS = ActionResult.pass(itemInHand);
        final ActionResult<ItemStack> SUCCESS = ActionResult.success(itemInHand);

        if (hand != Hand.MAIN_HAND) return PASS;

        player.getCapability(ModCapability.PLAYER_CAP).ifPresent(cap -> {
            float healthMax = player.getMaxHealth();
            if ((cap.getAttachHearts().size()+1)*2>healthMax) return;
            cap.addHeart(HeartRegister.INSTANCE.getRegistryHeart(getBindingHeartRegistryName()));
            itemInHand.shrink(1);
        });

        return player.getCapability(ModCapability.PLAYER_CAP).isPresent() ? SUCCESS : PASS;
    }
}
