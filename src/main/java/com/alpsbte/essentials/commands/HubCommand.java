package com.alpsbte.essentials.commands;

import com.alpsbte.essentials.utils.Server;
import com.alpsbte.essentials.utils.ServerUtils;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

import static com.alpsbte.essentials.utils.ServerUtils.PERMISSION_PREFIX;

public class HubCommand implements BasicCommand {

    @Override
    public void execute(CommandSourceStack commandSourceStack, @NotNull String[] strings) {
        if (!(commandSourceStack.getSender() instanceof Player player)) return;
        ServerUtils.connectToServer(Server.HUB_PLOT, player);
    }

    @Override
    public @Nullable String permission() {
        return PERMISSION_PREFIX + "hub";
    }

    public static void register(Commands commands) {
        commands.register("hub", "Teleports to the hub server.", List.of("l", "lobby"),  new HubCommand());
    }
}
