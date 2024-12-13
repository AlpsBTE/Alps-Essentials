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

import java.util.Collection;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class HubCmd implements AlpsCommand {

    @Override
    public @NotNull LiteralCommandNode<CommandSourceStack> node() {
        return Commands.literal("hub")
                .requires(stack -> this.canUseAndIsPlayer(stack.getSender()))
                .executes(this::execute)
                .build();
    }

    private int execute(CommandContext<CommandSourceStack> commandSourceStackCommandContext) {
        if (commandSourceStackCommandContext.getSource().getSender() instanceof Player player) {
            ServerUtils.connectToServer(Server.HUB_PLOT, player);
        }
        return Command.SINGLE_SUCCESS;
    }

    @Override
    public @Nullable String description() {
        return "Teleports to the hub server.";
    }

    @Override
    public @NotNull Collection<String> aliases() {
        return List.of("hub", "l", "lobby");
    }

    @Override
    public @Nullable String permission() {
        return "alpsbte.hub";
    }
}
