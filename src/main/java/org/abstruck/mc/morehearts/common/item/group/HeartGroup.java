package org.abstruck.mc.morehearts.common.item.group;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.abstruck.mc.morehearts.init.ItemInit;
import org.jetbrains.annotations.NotNull;

public class HeartGroup extends ItemGroup {
    public HeartGroup() {
        super("hearts");
    }

    @Override
    public @NotNull ItemStack makeIcon() {
        return new ItemStack(ItemInit.ZOMBIE_HEART.get());
    }
}
