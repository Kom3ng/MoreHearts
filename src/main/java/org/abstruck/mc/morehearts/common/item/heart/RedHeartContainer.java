package org.abstruck.mc.morehearts.common.item.heart;

import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.abstruck.mc.morehearts.common.item.group.ModGroups;
import org.jetbrains.annotations.NotNull;

public class RedHeartContainer extends Item {
    public RedHeartContainer() {
        super(new Properties().tab(ModGroups.HEART_GROUP));
    }

    @Override
    public @NotNull ActionResult<ItemStack> use(@NotNull World level, @NotNull PlayerEntity player, @NotNull Hand hand) {
        ItemStack itemStackInHand = player.getItemInHand(hand);
        ActionResult<ItemStack> SUCCESS = ActionResult.success(itemStackInHand);
        ActionResult<ItemStack> PASS = ActionResult.pass(itemStackInHand);
        if (hand != Hand.MAIN_HAND) return PASS;
        if (player.isSecondaryUseActive()) {
            addHealthMax(player,itemStackInHand,itemStackInHand.getCount());
            return SUCCESS;
        }
        addHealthMax(player,itemStackInHand,1);
        return SUCCESS;
    }

    private void addHealthMax(PlayerEntity player, @NotNull ItemStack itemStack, int num){
        if (!(itemStack.getItem() instanceof RedHeartContainer)) return;
        ModifiableAttributeInstance attributeInstance = player.getAttributes().getInstance(Attributes.MAX_HEALTH);
        if (attributeInstance == null) return;
        attributeInstance.setBaseValue(attributeInstance.getValue()+2.0D*num);
        itemStack.shrink(num);
    }
}
