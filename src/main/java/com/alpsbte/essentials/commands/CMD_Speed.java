package com.alpsbte.essentials.commands;

import com.alpsbte.alpslib.utils.AlpsUtils;
import com.alpsbte.essentials.utils.ChatUtils;
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

public class CMD_Speed extends BukkitCommand {
    private static final List<String> TAB_COMPLETE_ARGS = Arrays.asList("1", "2", "3");

    public CMD_Speed(@NotNull String name) {
        super(name);
        this.description = "Sets the flying speed of the player.";
        this.usageMessage = "/speed <1/2/3>";
        this.setPermission("alpsbte.speed");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return true;

        if (args.length == 1 && AlpsUtils.tryParseInt(args[0]) != null) {
            float speed = Float.parseFloat(args[0]) / 10;
            if (speed >= 0.1 && speed <= 0.4) {
                player.setFlySpeed(speed);
                player.sendMessage(ChatUtils.getInfoMessageFormat(LangUtil.getInstance().get(sender,
                        LangPaths.SET_PLAYER_SPEED, "<gold>" + args[0] + "</gold>")));
                return true;
            }
        }

        player.sendMessage(ChatUtils.getAlertMessageFormat(LangUtil.getInstance().get(sender, LangPaths.COMMAND_USAGE))
                .append(text(": /speed <1/2/3>", RED)));
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
