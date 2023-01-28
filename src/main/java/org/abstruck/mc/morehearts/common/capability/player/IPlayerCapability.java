package org.abstruck.mc.morehearts.common.capability.player;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import org.abstruck.mc.morehearts.common.capability.heart.AttachHeart;
import org.abstruck.mc.morehearts.common.capability.heart.ExtraHeart;
import org.abstruck.mc.morehearts.common.capability.heart.IHeart;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IPlayerCapability extends INBTSerializable<CompoundNBT>, Capability.IStorage<IPlayerCapability> {
    @NotNull List<IHeart> getHearts();
    @NotNull List<AttachHeart> getAttachHearts();
    @NotNull List<ExtraHeart> getExtraHearts();

    void setHearts(@NotNull List<IHeart> hearts);
    void setAttachHearts(@NotNull List<AttachHeart> hearts);
    void setExtraHearts(@NotNull List<ExtraHeart> hearts);

    float getAllExtraHealth();

    void shrinkExtraHeartsFromBehind(int num);
    void removeExtraHeart(int index);

    void addHeart(IHeart heart);
}
