package org.abstruck.mc.morehearts.common.event;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.abstruck.mc.morehearts.common.capability.heart.ExtraHeart;
import org.abstruck.mc.morehearts.common.capability.heart.HeartRegister;
import org.abstruck.mc.morehearts.utils.collection.list.ListUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ExtraHeartChangeEvent extends BaseSynchronousEvent {
    private List<ExtraHeart> toExtraHearts;
    private boolean isPosterRemote;
    public ExtraHeartChangeEvent(boolean isPosterRemote,List<ExtraHeart> toExtraHearts) {
        this.toExtraHearts = toExtraHearts;
        this.isPosterRemote = isPosterRemote;
    }

    public ExtraHeartChangeEvent(){
        this.toExtraHearts = new ArrayList<>();
    }

    public List<ExtraHeart> getToExtraHearts() {
        return toExtraHearts;
    }

    @Override
    public boolean isPosterRemote() {
        return isPosterRemote;
    }

    @Override
    public void setPosterRemote(boolean flag) {
        this.isPosterRemote = flag;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putBoolean("is_poster_remote",isPosterRemote);
        ListUtil.forEachWithIndex(toExtraHearts,(index, extraHeart) -> nbt.putString(String.valueOf(index), extraHeart.getRegistryName()));
        return nbt;
    }

    @Override
    public void deserializeNBT(@NotNull CompoundNBT nbt) {
        int index = 0;
        while (nbt.contains(String.valueOf(index))) {
            String indexStr = String.valueOf(index);
            String heartRegistryName = nbt.getString(indexStr);
            ExtraHeart extraHeart = (ExtraHeart) HeartRegister.INSTANCE.getRegistryHeart(heartRegistryName);
            getToExtraHearts().add(extraHeart);
            index++;
        }
        this.setPosterRemote(nbt.getBoolean("is_poster_remote"));
    }
}
