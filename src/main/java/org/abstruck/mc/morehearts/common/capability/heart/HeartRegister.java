package org.abstruck.mc.morehearts.common.capability.heart;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class HeartRegister {
    public static HeartRegister INSTANCE = new HeartRegister();
    private HeartRegister(){
        registeredHearts = new HashMap<>();
    }

    private final Map<String, IHeart> registeredHearts;

    public void registerHeartFactory(@NotNull Supplier<IHeart> factory){
        IHeart registryHeart = factory.get();
        getRegisteredHearts().put(registryHeart.getRegistryName(), registryHeart);
    }
    public IHeart getRegistryHeart(String registryName){
        return getRegisteredHearts().get(registryName);
    }

    public Map<String, IHeart> getRegisteredHearts() {
        return registeredHearts;
    }
}
