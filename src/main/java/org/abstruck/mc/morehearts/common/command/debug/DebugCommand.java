package org.abstruck.mc.morehearts.common.command.debug;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.TranslationTextComponent;
import org.abstruck.mc.morehearts.common.capability.ModCapability;
import org.jetbrains.annotations.NotNull;

public class DebugCommand implements Command<CommandSource> {
    @Override
    public int run(@NotNull CommandContext<CommandSource> context) throws CommandSyntaxException {
        context.getSource().getPlayerOrException().getCapability(ModCapability.PLAYER_CAP).ifPresent(cap -> {
            cap.getHearts().forEach(h -> context.getSource().sendSuccess(new TranslationTextComponent(h.getRegistryName()),false));
        });
        return 0;
    }
}
