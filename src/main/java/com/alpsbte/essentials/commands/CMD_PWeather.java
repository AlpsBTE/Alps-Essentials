package com.alpsbte.essentials.commands;

import com.alpsbte.essentials.utils.ChatUtils;
import com.alpsbte.essentials.utils.io.LangPaths;
import com.alpsbte.essentials.utils.io.LangUtil;
import org.bukkit.WeatherType;
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

public class CMD_PWeather extends BukkitCommand {
    private static final List<String> TAB_COMPLETE_ARGS = Arrays.asList("rain", "clear", "reset");

    public CMD_PWeather(@NotNull String name) {
        super(name);
        this.description = "Sets the weather for the player.";
        this.usageMessage = "/ptime <rain/clear/reset>";
        this.setPermission("alpsbte.pweather");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return true;

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

        player.sendMessage(ChatUtils.getAlertMessageFormat(LangUtil.getInstance().get(sender, LangPaths.COMMAND_USAGE))
                .append(text(": /pweather <rain/clear/reset>", RED)));
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
