package com.alpsbte.essentials.commands;

import com.alpsbte.essentials.commands.utility.AlpsCommand;
import com.alpsbte.essentials.config.ConfigUtil;
import com.alpsbte.essentials.utils.Server;
import com.alpsbte.essentials.utils.ServerUtils;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class SwitchCmd implements AlpsCommand {
    @Override
    public boolean isEnabled() {
        return ConfigUtil.getMainConfig().getCommandSection().enableSwitch();
    }

    @Override
    public @NotNull LiteralCommandNode<CommandSourceStack> node() {
        LiteralArgumentBuilder<CommandSourceStack> builder = Commands.literal("switch")
                .requires(stack -> this.canUseAndIsPlayer(stack.getSender()));

        List<Server> servers = ServerUtils.getAllServers();
        for (Server s: servers) {
            builder = builder.then(Commands.literal(s.getName()).executes(ctx -> execute(ctx, s)));
        }
        return builder.build();
    }

    @Override
    public @Nullable String description() {
        return "Switches to another server.";
    }

    private int execute(CommandContext<CommandSourceStack> ctx, Server server) {
        ServerUtils.connectToServer(server, (Player) ctx.getSource().getSender());
        return Command.SINGLE_SUCCESS;
    }

    @Override
    public @Nullable String permission() {
        return PERMISSION_PREFIX + "switch";
    }
}
