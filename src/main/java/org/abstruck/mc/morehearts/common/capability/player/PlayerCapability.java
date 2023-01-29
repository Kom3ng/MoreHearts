package org.abstruck.mc.morehearts.common.capability.player;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import org.abstruck.mc.morehearts.common.capability.heart.AttachHeart;
import org.abstruck.mc.morehearts.common.capability.heart.ExtraHeart;
import org.abstruck.mc.morehearts.common.capability.heart.HeartRegister;
import org.abstruck.mc.morehearts.common.capability.heart.IHeart;
import org.abstruck.mc.morehearts.utils.collection.list.ListUtil;
import org.abstruck.mc.morehearts.utils.data.NbtData;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PlayerCapability implements IPlayerCapability{
    NbtData<List<AttachHeart>> attachHearts = new NbtData<>("attach_hearts",new ArrayList<>());
    NbtData<List<ExtraHeart>> extraHearts = new NbtData<>("extra_hearts", new ArrayList<>());

    private NbtData<List<AttachHeart>> getVanillaAttachHearts(){
        return attachHearts;
    }
    private NbtData<List<ExtraHeart>> getVanillaExtraHearts(){
        return extraHearts;
    }

    @Override
    public @NotNull List<IHeart> getHearts() {
        List<IHeart> result = new ArrayList<>(getAttachHearts());
        result.addAll(getExtraHearts());
        return result;
    }

    @Override
    public @NotNull List<AttachHeart> getAttachHearts() {
        return ListUtil.removeNull(getVanillaAttachHearts().getValue());
    }

    @Override
    public @NotNull List<ExtraHeart> getExtraHearts() {
        return ListUtil.removeNull(getVanillaExtraHearts().getValue());
    }

    @Override
    public void setHearts(@NotNull List<IHeart> hearts) {
        List<AttachHeart> newAttachHeartList = new ArrayList<>();
        List<ExtraHeart> newExtraHeartList =  new ArrayList<>();

        hearts.stream()
                .filter(h -> h instanceof AttachHeart)
                .forEach(h -> newAttachHeartList.add((AttachHeart) h));
        hearts.stream()
                .filter(h -> h instanceof ExtraHeart)
                .forEach(h -> newExtraHeartList.add((ExtraHeart) h));

        setAttachHearts(newAttachHeartList);
        setExtraHearts(newExtraHeartList);
    }

    @Override
    public void setAttachHearts(@NotNull List<AttachHeart> hearts) {
        getVanillaAttachHearts().setValue(hearts);
    }

    @Override
    public void setExtraHearts(@NotNull List<ExtraHeart> hearts) {
        getVanillaExtraHearts().setValue(hearts);
    }

    @Override
    public float getAllExtraHealth() {
        final float BASE = 2.0F;
        AtomicReference<Float> result = new AtomicReference<>(0.0F);
        getExtraHearts().forEach(eh -> result.set(result.get() + BASE / eh.getDecelerationMagnification()));
        return result.get();
    }

    @Override
    public void shrinkExtraHeartsFromBehind(int num) {
        setExtraHearts(ListUtil.safeShrinkFromBehind(getExtraHearts(),num));
    }

    @Override
    public void removeExtraHeart(int index) {
        List<ExtraHeart> heartList = getExtraHearts();
        heartList.remove(index);
        setExtraHearts(heartList);
    }

    @Override
    public void addHeart(IHeart heart) {
        if (heart instanceof AttachHeart) {
            getAttachHearts().add((AttachHeart) heart);
            return;
        }
        if (heart instanceof ExtraHeart) {
            getExtraHearts().add((ExtraHeart) heart);
        }
    }


    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT(),
                attachHeartsNbt = new CompoundNBT(),
                extraHeartsNbt = new CompoundNBT();

        ListUtil.forEachWithIndex(getAttachHearts(),(index, element) -> {
            attachHeartsNbt.putString(String.valueOf(index), element.getRegistryName());
        });
        ListUtil.forEachWithIndex(getExtraHearts(),(index, extraHeart) -> {
            extraHeartsNbt.putString(String.valueOf(index), extraHeart.getRegistryName());
        });

        nbt.put(getVanillaAttachHearts().getKey(), attachHeartsNbt);
        nbt.put(getVanillaExtraHearts().getKey(), extraHeartsNbt);

        return nbt;
    }

    @Override
    public void deserializeNBT(@NotNull CompoundNBT nbt) {
        List<AttachHeart> attachHeartList = new ArrayList<>();
        List<ExtraHeart> extraHeartList = new ArrayList<>();

        CompoundNBT attachHeartsNbt = nbt.getCompound(getVanillaAttachHearts().getKey());
        CompoundNBT extraHeartsNbt = nbt.getCompound(getVanillaExtraHearts().getKey());

        int index = 0;
        while (attachHeartsNbt.contains(String.valueOf(index))){
            IHeart heart = HeartRegister.INSTANCE.getRegistryHeart(attachHeartsNbt.getString(String.valueOf(index)));
            if (heart instanceof AttachHeart) attachHeartList.add((AttachHeart) heart);
            index++;
        }

        index = 0;
        while (extraHeartsNbt.contains(String.valueOf(index))){
            IHeart heart = HeartRegister.INSTANCE.getRegistryHeart(extraHeartsNbt.getString(String.valueOf(index)));
            if (heart instanceof ExtraHeart) extraHeartList.add((ExtraHeart) heart);
            index++;
        }

        this.getVanillaAttachHearts().setValue(ListUtil.removeNull(attachHeartList));
        this.getVanillaExtraHearts().setValue(ListUtil.removeNull(extraHeartList));
    }



    //storage below

    @Nullable
    @Override
    public INBT writeNBT(Capability<IPlayerCapability> capability, @NotNull IPlayerCapability instance, Direction side) {
        return instance.serializeNBT();
    }

    @Override
    public void readNBT(Capability<IPlayerCapability> capability, IPlayerCapability instance, Direction side, INBT nbt) {
        if (!(nbt instanceof CompoundNBT)) return;
        instance.deserializeNBT((CompoundNBT) nbt);
    }
}
