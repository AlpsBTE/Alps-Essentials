package com.alpsbte.essentials.commands;

import com.alpsbte.essentials.utils.ChatUtils;
import com.alpsbte.essentials.utils.Server;
import com.alpsbte.essentials.utils.ServerUtils;
import com.alpsbte.essentials.utils.io.LangPaths;
import com.alpsbte.essentials.utils.io.LangUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.RED;

public class CMD_Switch extends BukkitCommand {
    private static final List<String> TAB_COMPLETE_ARGS = Arrays.asList("hub", "terra");

    public CMD_Switch(@NotNull String name) {
        super(name);
        this.description = "Switches to another server.";
        this.usageMessage = "/switch <hub/terra>";
        this.setPermission("alpsbte.switch");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return true;

        if (args.length == 1) {
            String input = args[0].toLowerCase();
            if (input.equalsIgnoreCase(TAB_COMPLETE_ARGS.get(0))) {
                ServerUtils.connectToServer(Server.HUB_PLOT, player);
                return true;
            } else if (input.equalsIgnoreCase(TAB_COMPLETE_ARGS.get(1))) {
                ServerUtils.connectToServer(Server.TERRA, player);
                return true;
            }
        }

        player.sendMessage(ChatUtils.getAlertMessageFormat(LangUtil.getInstance().get(sender, LangPaths.COMMAND_USAGE))
                .append(text(": /switch <hub/terra>", RED)));
        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        final List<String> completions = new ArrayList<>();
        StringUtil.copyPartialMatches(args[0], TAB_COMPLETE_ARGS, completions);
        Collections.sort(completions);
        return completions;
    }
}
