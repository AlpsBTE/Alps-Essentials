package com.alpsbte.essentials.commands;

import com.alpsbte.essentials.commands.utility.AlpsCommand;
import com.alpsbte.essentials.config.ConfigUtil;
import com.alpsbte.essentials.utils.ChatUtils;
import com.alpsbte.essentials.utils.io.LangPaths;
import com.alpsbte.essentials.utils.io.LangUtil;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("UnstableApiUsage")
public class PTimeCmd implements AlpsCommand {
    @Override
    public boolean isEnabled() {
        return ConfigUtil.getMainConfig().getCommandSection().enablePTime();
    }
    @Override
    public @NotNull LiteralCommandNode<CommandSourceStack> node() {
        return Commands.literal("ptime")
                .requires(stack -> this.canUseAndIsPlayer(stack.getSender()))
                .then(Commands.literal("day").executes(this::executeDay))
                .then(Commands.literal("night").executes(this::executeNight))
                .then(Commands.literal("reset").executes(this::executeReset))
                .then(Commands.argument("ticks", IntegerArgumentType.integer(0, 24000))
                        .executes(this::executeTicks)).build();
    }

    @Override
    public @Nullable String description() {
        return "Sets the time for the player.";
    }

    private int executeDay(CommandContext<CommandSourceStack> ctx) {
        Player player = (Player) ctx.getSource().getSender();
        player.setPlayerTime(6000, false);
        ctx.getSource().getSender().sendMessage(ChatUtils.getInfoMessageFormat(LangUtil.getInstance().get(ctx.getSource().getSender(),
                LangPaths.SET_PLAYER_TIME, "<gold>day</gold>")));
        return Command.SINGLE_SUCCESS;
    }

    private int executeNight(CommandContext<CommandSourceStack> ctx) {
        Player player = (Player) ctx.getSource().getSender();
        player.setPlayerTime(18000, false);
        ctx.getSource().getSender().sendMessage(ChatUtils.getInfoMessageFormat(LangUtil.getInstance().get(ctx.getSource().getSender(),
                LangPaths.SET_PLAYER_TIME, "<gold>night</gold>")));
        return Command.SINGLE_SUCCESS;
    }

    private int executeReset(CommandContext<CommandSourceStack> ctx) {
        Player player = (Player) ctx.getSource().getSender();
        player.resetPlayerTime();
        ctx.getSource().getSender().sendMessage(ChatUtils.getInfoMessageFormat(LangUtil.getInstance().get(ctx.getSource().getSender(),
                LangPaths.SET_PLAYER_TIME_SYNCHRONIZED)));
        return Command.SINGLE_SUCCESS;
    }

    private int executeTicks(CommandContext<CommandSourceStack> ctx) {
        int ticks = ctx.getArgument("ticks", Integer.class);
        Player player = (Player) ctx.getSource().getSender();
        if (ticks >= 0 && ticks <= 24000) {
            player.setPlayerTime(ticks, false);
            ctx.getSource().getSender().sendMessage(ChatUtils.getInfoMessageFormat(LangUtil.getInstance().get(ctx.getSource().getSender(),
                    LangPaths.SET_PLAYER_TIME, "<gold>" + ticks + " ticks</gold>")));
        }
        return Command.SINGLE_SUCCESS;
    }

    @Override
    public @Nullable String permission() {
        return PERMISSION_PREFIX + "ptime";
    }
}