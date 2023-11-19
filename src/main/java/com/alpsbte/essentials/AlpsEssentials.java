package com.alpsbte.essentials;

import com.alpsbte.alpslib.io.YamlFileFactory;
import com.alpsbte.alpslib.io.config.ConfigNotImplementedException;
import com.alpsbte.essentials.commands.CMD_Reload;
import com.alpsbte.essentials.commands.CMD_Spawn;
import com.alpsbte.essentials.commands.CMD_TPP;
import com.alpsbte.essentials.utils.ChatUtils;
import com.alpsbte.essentials.utils.io.ConfigPaths;
import com.alpsbte.essentials.utils.io.ConfigUtil;
import com.alpsbte.essentials.utils.io.LangUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.logging.Level;

public final class AlpsEssentials extends JavaPlugin {
    private static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;

        // Init Config
        try {
            YamlFileFactory.registerPlugin(this);
            ConfigUtil.init();
        } catch (ConfigNotImplementedException ex) {
            Bukkit.getLogger().log(Level.WARNING, "Could not load configuration file.");
            Bukkit.getConsoleSender().sendMessage(Component.text("The config file must be configured!", NamedTextColor.YELLOW));
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }
        reloadConfig();

        // Register the language library
        li.cinnazeyy.langlibs.core.file.YamlFileFactory.registerPlugin(this);
        LangUtil.init();

        // Register Commands
        CommandMap cmdMap = Bukkit.getServer().getCommandMap();
        cmdMap.register("tpp", new CMD_TPP("tpp"));
        cmdMap.register("spawn", new CMD_Spawn("spawn"));
        cmdMap.register("alpsreload", new CMD_Reload("alpsreload"));
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    @Override
    public @NotNull FileConfiguration getConfig() {
        return ConfigUtil.getInstance().configs[0];
    }

    @Override
    public void reloadConfig() {
        ConfigUtil.getInstance().reloadFiles();
        ConfigUtil.getInstance().saveFiles();
        ChatUtils.setChatFormat(ConfigUtil.getInstance().configs[0].getString(ConfigPaths.CHAT_FORMAT_INFO_PREFIX),
                ConfigUtil.getInstance().configs[0].getString(ConfigPaths.CHAT_FORMAT_ALERT_PREFIX),
                ConfigUtil.getInstance().configs[0].getString(ConfigPaths.CHAT_FORMAT_ARROW));
    }

    public static Location getSpawnLocation() {
        FileConfiguration config = getPlugin().getConfig();
        return new Location(
                Bukkit.getWorld(Objects.requireNonNull(config.getString(ConfigPaths.SPAWN_WORLD))),
                config.getDouble(ConfigPaths.SPAWN_X),
                config.getDouble(ConfigPaths.SPAWN_Y),
                config.getDouble(ConfigPaths.SPAWN_Z),
                (float) config.getDouble(ConfigPaths.SPAWN_YAW),
                (float) config.getDouble(ConfigPaths.SPAWN_PITCH));
    }
}
