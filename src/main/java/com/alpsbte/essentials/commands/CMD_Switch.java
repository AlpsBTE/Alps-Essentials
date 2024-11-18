package com.alpsbte.essentials.commands;

import com.alpsbte.essentials.commands.utility.AlpsCommand;
import com.alpsbte.essentials.utils.Server;
import com.alpsbte.essentials.utils.ServerUtils;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("UnstableApiUsage")
public class CMD_Switch implements AlpsCommand {
    @Override
    public @NotNull LiteralCommandNode<CommandSourceStack> node() {
        return Commands.literal("switch")
                .requires(stack -> this.canUseAndIsPlayer(stack.getSender()))
                .then(Commands.literal("hub").executes(this::executeHub))
                .then(Commands.literal("terra").executes(this::executeTerra)).build();
    }

    @Override
    public @Nullable String description() {
        return "Switches to another server.";
    }

    private int executeHub(CommandContext<CommandSourceStack> ctx) {
        ServerUtils.connectToServer(Server.HUB_PLOT, (Player) ctx.getSource().getSender());
        return Command.SINGLE_SUCCESS;
    }

    private int executeTerra(CommandContext<CommandSourceStack> ctx) {
        ServerUtils.connectToServer(Server.HUB_PLOT, (Player) ctx.getSource().getSender());
        return Command.SINGLE_SUCCESS;
    }
}
