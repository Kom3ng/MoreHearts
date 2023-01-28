package org.abstruck.mc.morehearts.common.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.abstruck.mc.morehearts.common.command.debug.DebugCommand;
import org.abstruck.mc.morehearts.utils.ModUtil;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber
public class CommandRegister {
    @SubscribeEvent
    public static void register(@NotNull RegisterCommandsEvent event){
        CommandDispatcher<CommandSource> dispatcher = event.getDispatcher();
        LiteralCommandNode<CommandSource> debug = dispatcher.register(
                Commands.literal(ModUtil.MOD_ID).then(
                        Commands.literal("debug")
                                .requires(commandSource -> commandSource.hasPermission(0))
                                .executes(new DebugCommand())
                )
        );
    }
}
