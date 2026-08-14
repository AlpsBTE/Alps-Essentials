package com.alpsbte.essentials.commands;

import com.alpsbte.essentials.commands.utility.AlpsCommand;
import com.alpsbte.essentials.config.ConfigUtil;
import com.alpsbte.essentials.utils.ChatUtils;
import com.alpsbte.essentials.utils.io.LangPaths;
import com.alpsbte.essentials.utils.io.LangUtil;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("UnstableApiUsage")
public class ScaleCmd implements AlpsCommand {
    @Override
    public boolean isEnabled() {
        return ConfigUtil.getMainConfig().getCommandSection().enableScale();
    }
    @Override
    public @NotNull LiteralCommandNode<CommandSourceStack> node() {
        return Commands.literal("scale")
                .requires(stack -> this.canUseAndIsPlayer(stack.getSender()))
                .then(Commands.literal("1").executes(ctx -> execute(ctx, 1)))
                .then(Commands.literal("0.75").executes(ctx -> execute(ctx, 0.75)))
                .then(Commands.literal("0.5").executes(ctx -> execute(ctx, 0.5)))
                .then(Commands.literal("0.25").executes(ctx -> execute(ctx, 0.25))).build();
    }

    @Override
    public @Nullable String description() {
        return "Sets the scale of the player.";
    }

    private int execute(CommandContext<CommandSourceStack> ctx, double scale) {
        Player player = ((Player) ctx.getSource().getSender()).getPlayer();
            if (player != null) {
                player.getAttribute(Attribute.SCALE).setBaseValue(scale);
                player.sendMessage(ChatUtils.getInfoMessageFormat(LangUtil.getInstance().get(player,
                        LangPaths.SET_PLAYER_SCALE, "<gold>" + scale + "</gold>")));
            }
        return Command.SINGLE_SUCCESS;
    }

    @Override
    public @Nullable String permission() {
        return PERMISSION_PREFIX + "scale";
    }
}