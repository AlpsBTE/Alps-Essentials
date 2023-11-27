package com.alpsbte.essentials.commands;

import com.alpsbte.essentials.utils.Server;
import com.alpsbte.essentials.utils.ServerUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CMD_Hub extends BukkitCommand {
    public CMD_Hub(@NotNull String name) {
        super(name);
        this.description = "Teleports to the hub server.";
        this.usageMessage = "/hub";
        this.setPermission("alpsbte.hub");

        List<String> aliases = new ArrayList<>();
        aliases.add("hub");
        aliases.add("l");
        aliases.add("lobby");
        this.setAliases(aliases);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player)sender;
        ServerUtils.connectToServer(ServerUtils.isOnline(Server.HUB_PLOT), Server.HUB_PLOT, player);
        return true;
    }
}
