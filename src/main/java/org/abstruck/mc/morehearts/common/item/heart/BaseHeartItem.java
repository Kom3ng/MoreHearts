package org.abstruck.mc.morehearts.common.item.heart;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.abstruck.mc.morehearts.common.capability.ModCapability;
import org.abstruck.mc.morehearts.common.capability.heart.HeartRegister;
import org.abstruck.mc.morehearts.common.capability.player.IPlayerCapability;
import org.abstruck.mc.morehearts.common.item.group.ModGroups;
import org.abstruck.mc.morehearts.utils.collection.list.ListUtil;
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
        final ActionResult<ItemStack> FAILED = ActionResult.fail(itemInHand);

        if (hand != Hand.MAIN_HAND) return PASS;

        player.getCapability(ModCapability.PLAYER_CAP).ifPresent(cap -> {
            float healthMax = player.getMaxHealth();
            if (player.isSecondaryUseActive()){
                if (itemInHand.getItem() instanceof BaseExtraHeartItem){
                    addHearts(itemInHand,cap,itemInHand.getCount());
                    return;
                }
                if (itemInHand.getItem() instanceof BaseAttachHeartItem){
                    if (cap.getAttachHearts().size() + itemInHand.getCount() <= MathHelper.ceil(healthMax/2)){
                        addHearts(itemInHand,cap,itemInHand.getCount());
                        return;
                    }
                    addHearts(itemInHand,cap,MathHelper.ceil(healthMax/2) - cap.getAttachHearts().size());
                    return;
                }
            }

            if (cap.getAttachHearts().size() >= MathHelper.ceil(healthMax/2) && itemInHand.getItem() instanceof BaseAttachHeartItem) return;
            addHearts(itemInHand, cap,1);
        });

        return player.getCapability(ModCapability.PLAYER_CAP).isPresent() ? SUCCESS : FAILED;
    }

    private void addHearts(@NotNull ItemStack itemInHand, @NotNull IPlayerCapability cap, int count) {
        ListUtil.loop(count,()->cap.addHeart(HeartRegister.INSTANCE.getRegistryHeart(getBindingHeartRegistryName())));
        itemInHand.shrink(count);
    }
}
