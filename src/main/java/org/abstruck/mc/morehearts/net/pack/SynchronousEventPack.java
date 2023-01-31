package org.abstruck.mc.morehearts.net.pack;

import com.mojang.brigadier.StringReader;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import org.abstruck.mc.morehearts.common.event.BaseSynchronousEvent;
import org.abstruck.mc.morehearts.common.event.ModEventBus;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.function.Supplier;

public class SynchronousEventPack<E extends BaseSynchronousEvent> {
    private static Logger logger = LogManager.getLogger();
    private E event;
    public SynchronousEventPack(@NotNull E event){
        this.event = event;
    }

    @Contract("_ -> new")
    public static <E extends BaseSynchronousEvent> @NotNull SynchronousEventPack<E> decode(@NotNull PacketBuffer buffer) {
        CompoundNBT nbt = buffer.readAnySizeNbt();

        Class<?> eventClass = null;
        try {
            eventClass = Class.forName(nbt.getString("class_name"));
        } catch (ClassNotFoundException | ClassCastException e) {
            logger.log(Level.ERROR,e);
        }
        E eventInstance = null;
        try {
            eventInstance = (E) Objects.requireNonNull(eventClass).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            logger.log(Level.ERROR,e);
        }
        try {
            Objects.requireNonNull(eventClass).getMethod("deserializeNBT", INBT.class).invoke(eventInstance,nbt.getCompound("nbt"));
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.log(Level.ERROR,e);
        }

        return new SynchronousEventPack<>(Objects.requireNonNull(eventInstance));
    }

    public void encode(@NotNull PacketBuffer buffer){
        CompoundNBT value = new CompoundNBT();

        String className = event.getClass().getName();
        CompoundNBT nbt = event.serializeNBT();

        value.putString("class_name",className);
        value.put("nbt", nbt);

        buffer.writeNbt(value);
    }

    public void handle(@NotNull Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
            ModEventBus.post(event);
        });
        ctx.get().setPacketHandled(true);
    }
}
