package org.abstruck.mc.morehearts.common.capability.player;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.abstruck.mc.morehearts.common.capability.ModCapability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundNBT> {
    IPlayerCapability cap = new PlayerCapability();

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == ModCapability.PLAYER_CAP ? LazyOptional.of(this::get).cast() : LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        return get().serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        get().deserializeNBT(nbt);
    }

    public IPlayerCapability get(){
        return cap;
    }

    public void set(IPlayerCapability cap){
        this.cap = cap;
    }

}
