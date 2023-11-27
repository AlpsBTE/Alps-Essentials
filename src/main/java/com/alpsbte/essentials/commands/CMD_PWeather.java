package com.alpsbte.essentials.commands;

import com.alpsbte.essentials.utils.ChatUtils;
import com.alpsbte.essentials.utils.io.LangPaths;
import com.alpsbte.essentials.utils.io.LangUtil;
import org.bukkit.WeatherType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.TabExecutor;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CMD_PWeather extends BukkitCommand implements TabCompleter {
    private static final List<String> TAB_COMPLETE_ARGS = Arrays.asList("rain", "clear", "reset");

    public CMD_PWeather(@NotNull String name) {
        super(name);
        this.description = "Sets the weather for the player.";
        this.usageMessage = "/ptime <rain/clear/reset>";
        this.setPermission("alpsbte.pweather");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player)sender;

        if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "rain":
                    player.setPlayerWeather(WeatherType.DOWNFALL);
                    player.sendMessage(ChatUtils.getInfoMessageFormat(LangUtil.getInstance().get(sender,
                            LangPaths.SET_PLAYER_WEATHER, "<gold>rain</gold>")));
                    return true;
                case "clear":
                    player.setPlayerWeather(WeatherType.CLEAR);
                    player.sendMessage(ChatUtils.getInfoMessageFormat(LangUtil.getInstance().get(sender,
                            LangPaths.SET_PLAYER_TIME, "<gold>clear</gold>")));
                    return true;
                case "reset":
                    player.resetPlayerWeather();
                    player.sendMessage(ChatUtils.getInfoMessageFormat(LangUtil.getInstance().get(sender,
                            LangPaths.SET_PLAYER_WEATHER_SYNCHRONIZED)));
                    return true;
            }
        }

        player.sendMessage(ChatUtils.getAlertMessageFormat("/pweather <rain/clear/reset>"));
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
