package org.abstruck.mc.morehearts.common.handler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;
import org.abstruck.mc.morehearts.common.capability.ModCapability;
import org.abstruck.mc.morehearts.common.capability.player.IPlayerCapability;
import org.abstruck.mc.morehearts.common.capability.player.PlayerCapabilityProvider;
import org.abstruck.mc.morehearts.common.event.ModEventBus;
import org.abstruck.mc.morehearts.common.event.SynchronousHeartsEvent;
import org.abstruck.mc.morehearts.utils.ModUtil;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber
public class CapabilityHandler {
    @SubscribeEvent
    public static void onAttachCapability(@NotNull AttachCapabilitiesEvent<Entity> event){
        if (!(event.getObject() instanceof PlayerEntity)) return;

        event.addCapability(new ResourceLocation(ModUtil.MOD_ID,"player_cap"),new PlayerCapabilityProvider());
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.@NotNull Clone event){
        PlayerEntity player = event.getPlayer();
        World level = player.getCommandSenderWorld();

        if (!level.getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY) && event.isWasDeath()) return;
        if (level.isClientSide) return;

        LazyOptional<IPlayerCapability> oldCap = event.getOriginal().getCapability(ModCapability.PLAYER_CAP);
        LazyOptional<IPlayerCapability> newCap = event.getPlayer().getCapability(ModCapability.PLAYER_CAP);

        oldCap.ifPresent(oc -> {
            newCap.ifPresent(nc -> {
                nc.deserializeNBT(oc.serializeNBT());
            });
        });
    }
}
