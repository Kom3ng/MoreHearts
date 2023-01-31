package org.abstruck.mc.morehearts.common.event;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public interface ISynchronousEvent extends INBTSerializable<CompoundNBT>{
    boolean isPosterRemote();
    void setPosterRemote(boolean flag);
}
