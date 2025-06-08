package com.alpsbte.essentials;

import com.alpsbte.alpslib.io.YamlFileFactory;
import com.alpsbte.alpslib.io.config.ConfigNotImplementedException;
import com.alpsbte.essentials.commands.utility.CommandLauncher;
import com.alpsbte.essentials.utils.ChatUtils;
import com.alpsbte.essentials.utils.Server;
import com.alpsbte.essentials.utils.io.ConfigPaths;
import com.alpsbte.essentials.utils.io.ConfigUtil;
import com.alpsbte.essentials.utils.io.LangUtil;
import com.google.common.io.ByteStreams;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class AlpsEssentials extends JavaPlugin implements PluginMessageListener {
    public static final String PLUGIN_CHANNEL = "BungeeCord";

    @Override
    public void onEnable() {
        // Init Config
        try {
            YamlFileFactory.registerPlugin(this);
            ConfigUtil.init();
        } catch (ConfigNotImplementedException ex) {
            this.getComponentLogger().warn(Component.text("Could not load configuration file."));
            Bukkit.getConsoleSender().sendMessage(Component.text("The config file must be configured!", NamedTextColor.YELLOW));
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }
        reloadConfig();

        // Register the language library
        LangUtil.init();

        // Register bungeecord messaging channels
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, PLUGIN_CHANNEL);
        this.getServer().getMessenger().registerIncomingPluginChannel(this, PLUGIN_CHANNEL, this);

        // Register Commands
        CommandLauncher.register();

        // Register event listener
        getServer().getPluginManager().registerEvents(new EventListener(), this);
    }

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, byte[] message) {
        if (!channel.equals(PLUGIN_CHANNEL)) return;
        Server.setPlayerData(ByteStreams.newDataInput(message));
    }

    public static AlpsEssentials getPlugin() {
        return getPlugin(AlpsEssentials.class);
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
        Server.setConfigData();
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
