package org.abstruck.mc.morehearts.utils.heart;

import net.minecraft.util.math.MathHelper;
import org.abstruck.mc.morehearts.common.capability.heart.AttachHeart;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HeartUtil {
    public static @Nullable AttachHeart healthLiesInWitchHeart(float health, @NotNull List<AttachHeart> attachHearts){
        int index = MathHelper.ceil(health / 2);
        return attachHearts.size() > index ? attachHearts.get(index) : null;
    }

    public static int indexOfHeartLiesInWitchHeart(float health, @NotNull List<AttachHeart> attachHearts){
        int heartQuantity = MathHelper.ceil(health / 2);
        return attachHearts.size() > heartQuantity ? heartQuantity - 1 : attachHearts.size() - heartQuantity;
    }
}
