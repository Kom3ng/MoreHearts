package org.abstruck.mc.morehearts.common.event;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.eventbus.api.Event;

public abstract class BaseSynchronousEvent extends Event implements INBTSerializable<CompoundNBT>, ISynchronousEvent {
    public BaseSynchronousEvent(boolean isPosterRemote){
        this.setPosterRemote(isPosterRemote);
    }
    public BaseSynchronousEvent(){
    }
}
