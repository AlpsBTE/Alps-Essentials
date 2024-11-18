package com.alpsbte.essentials.commands;

import com.alpsbte.essentials.commands.utility.AlpsCommand;
import com.alpsbte.essentials.utils.ChatUtils;
import com.alpsbte.essentials.utils.io.LangPaths;
import com.alpsbte.essentials.utils.io.LangUtil;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.WeatherType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("UnstableApiUsage")
public class PWeather implements AlpsCommand {
    @Override
    public @NotNull LiteralCommandNode<CommandSourceStack> node() {
        return Commands.literal("pweather")
                .requires(stack -> this.canUseAndIsPlayer(stack.getSender()))
                .then(Commands.literal("rain").executes(this::executeRain))
                .then(Commands.literal("clear").executes(this::executeClear))
                .then(Commands.literal("reset").executes(this::executeReset)).build();
    }

    @Override
    public @Nullable String description() {
        return "Sets the weather for the player.";
    }

    private int executeRain(CommandContext<CommandSourceStack> ctx) {
        ((Player) ctx.getSource().getSender()).setPlayerWeather(WeatherType.DOWNFALL);
        ctx.getSource().getSender().sendMessage(ChatUtils.getInfoMessageFormat(LangUtil.getInstance().get(ctx.getSource().getSender(),
                LangPaths.SET_PLAYER_WEATHER, "<gold>rain</gold>")));
        return Command.SINGLE_SUCCESS;
    }

    private int executeClear(CommandContext<CommandSourceStack> ctx) {
        ((Player) ctx.getSource().getSender()).setPlayerWeather(WeatherType.CLEAR);
        ctx.getSource().getSender().sendMessage(ChatUtils.getInfoMessageFormat(LangUtil.getInstance().get(ctx.getSource().getSender(),
                LangPaths.SET_PLAYER_WEATHER, "<gold>clear</gold>")));
        return Command.SINGLE_SUCCESS;
    }

    private int executeReset(CommandContext<CommandSourceStack> ctx) {
        ((Player) ctx.getSource().getSender()).resetPlayerWeather();
        ctx.getSource().getSender().sendMessage(ChatUtils.getInfoMessageFormat(LangUtil.getInstance().get(ctx.getSource().getSender(),
                LangPaths.SET_PLAYER_WEATHER_SYNCHRONIZED)));
        return Command.SINGLE_SUCCESS;
    }
}
