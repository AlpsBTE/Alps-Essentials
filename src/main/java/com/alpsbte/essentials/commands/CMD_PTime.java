package com.alpsbte.essentials.commands;

import com.alpsbte.alpslib.utils.AlpsUtils;
import com.alpsbte.essentials.utils.ChatUtils;
import com.alpsbte.essentials.utils.io.LangPaths;
import com.alpsbte.essentials.utils.io.LangUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CMD_PTime extends BukkitCommand implements TabCompleter {
    private static final List<String> TAB_COMPLETE_ARGS = Arrays.asList("day", "night", "reset");

    public CMD_PTime(@NotNull String name) {
        super(name);
        this.description = "Sets the time for the player.";
        this.usageMessage = "/ptime <day/night/reset>";
        this.setPermission("alpsbte.ptime");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player)sender;

        if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "day":
                    player.setPlayerTime(6000, false);
                    player.sendMessage(ChatUtils.getInfoMessageFormat(LangUtil.getInstance().get(sender,
                            LangPaths.SET_PLAYER_TIME, "<gold>day</gold>")));
                    return true;
                case "night":
                    player.setPlayerTime(18000, false);
                    player.sendMessage(ChatUtils.getInfoMessageFormat(LangUtil.getInstance().get(sender,
                            LangPaths.SET_PLAYER_TIME, "<gold>night</gold>")));
                    return true;
                case "reset":
                    player.resetPlayerTime();
                    player.sendMessage(ChatUtils.getInfoMessageFormat(LangUtil.getInstance().get(sender,
                            LangPaths.SET_PLAYER_TIME_SYNCHRONIZED)));
                    return true;
                default:
                    if (AlpsUtils.tryParseInt(args[0]) != null) {
                        int ticks = Integer.parseInt(args[0]);
                        if (ticks >= 0 && ticks <= 24000) {
                            player.setPlayerTime(ticks, false);
                            player.sendMessage(ChatUtils.getInfoMessageFormat(LangUtil.getInstance().get(sender,
                                    LangPaths.SET_PLAYER_TIME, "<gold>" + ticks + " ticks</gold>")));
                            return true;
                        }
                    }
                    break;
            }
        }

        player.sendMessage(ChatUtils.getAlertMessageFormat("/ptime <day/night/reset>"));
        return true;
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        final List<String> completions = new ArrayList<>();
        StringUtil.copyPartialMatches(args[0], TAB_COMPLETE_ARGS, completions);
        Collections.sort(completions);
        return completions;
    }
}
