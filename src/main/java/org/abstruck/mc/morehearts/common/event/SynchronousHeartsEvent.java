package org.abstruck.mc.morehearts.common.event;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import org.abstruck.mc.morehearts.common.capability.ModCapability;
import org.abstruck.mc.morehearts.common.capability.heart.AttachHeart;
import org.abstruck.mc.morehearts.common.capability.heart.ExtraHeart;
import org.abstruck.mc.morehearts.common.capability.heart.HeartRegister;
import org.abstruck.mc.morehearts.utils.collection.list.ListUtil;
import org.abstruck.mc.morehearts.utils.data.NbtData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SynchronousHeartsEvent extends BaseSynchronousEvent{
    private NbtData<Boolean> isPosterRemote = new NbtData<>("is_poster_remote",true);
    private NbtData<List<AttachHeart>> attachHearts;
    private NbtData<List<ExtraHeart>> extraHearts;

    public SynchronousHeartsEvent(@NotNull PlayerEntity player, boolean isPosterRemote){
        player.getCapability(ModCapability.PLAYER_CAP).ifPresent(cap -> {
            attachHearts = new NbtData<>("attach_hearts",cap.getAttachHearts());
            extraHearts = new NbtData<>("extra_hearts",cap.getExtraHearts());
        });
        setPosterRemote(isPosterRemote);
    }

    public SynchronousHeartsEvent(){
        attachHearts = new NbtData<>("attach_hearts",new ArrayList<>());
        extraHearts = new NbtData<>("extra_heart",new ArrayList<>());
    }

    @Override
    public void setPosterRemote(boolean flag) {
        this.isPosterRemote.setValue(flag);
    }

    @Override
    public boolean isPosterRemote() {
        return isPosterRemote.getValue();
    }

    public List<AttachHeart> getAttachHearts(){
        return attachHearts.getValue();
    }

    public List<ExtraHeart> getExtraHearts(){
        return extraHearts.getValue();
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT result = new CompoundNBT();

        CompoundNBT attachNbt = new CompoundNBT();
        CompoundNBT extraNbt = new CompoundNBT();

        result.putBoolean(isPosterRemote.getKey(), isPosterRemote.getValue());

        ListUtil.forEachWithIndex(attachHearts.getValue(), (index, attachHeart) -> attachNbt.putString(String.valueOf(index),attachHeart.getRegistryName()));
        ListUtil.forEachWithIndex(extraHearts.getValue(), (index, extraHeart) -> extraNbt.putString(String.valueOf(index),extraHeart.getRegistryName()));

        result.put(attachHearts.getKey(), attachNbt);
        result.put(extraHearts.getKey(), extraNbt);

        return result;
    }

    @Override
    public void deserializeNBT(@NotNull CompoundNBT nbt) {
        setPosterRemote(nbt.getBoolean(isPosterRemote.getKey()));

        CompoundNBT attachNbt = nbt.getCompound(attachHearts.getKey());
        CompoundNBT extraNbt = nbt.getCompound(extraHearts.getKey());

        int index = 0;
        while (attachNbt.contains(String.valueOf(index))){
            attachHearts.getValue().add((AttachHeart) HeartRegister.INSTANCE.getRegistryHeart(attachNbt.getString(String.valueOf(index))));
            index++;
        }
        index = 0;
        while (extraNbt.contains(String.valueOf(index))){
            extraHearts.getValue().add((ExtraHeart) HeartRegister.INSTANCE.getRegistryHeart(extraNbt.getString(String.valueOf(index))));
            index++;
        }
    }
}
