package com.alpsbte.essentials;

import com.alpsbte.alpslib.io.YamlFileFactory;
import com.alpsbte.essentials.commands.utility.CommandLauncher;
import com.alpsbte.essentials.config.section.SpawnSection;
import com.alpsbte.essentials.utils.ChatUtils;
import com.alpsbte.essentials.config.ConfigUtil;
import com.alpsbte.essentials.utils.ServerUtils;
import com.alpsbte.essentials.utils.io.LangUtil;
import com.google.common.io.ByteStreams;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurateException;

import java.util.Objects;

public final class AlpsEssentials extends JavaPlugin implements PluginMessageListener {
    public static final String PLUGIN_CHANNEL = "BungeeCord";

    @Override
    public void onEnable() {
        // Init Config
        try {
            YamlFileFactory.registerPlugin(this);
            ConfigUtil.init();
        } catch (ConfigurateException ex) {
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
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, byte @NotNull [] message) {
        if (!channel.equals(PLUGIN_CHANNEL)) return;
        ServerUtils.setPlayerData(ByteStreams.newDataInput(message));
    }

    public static AlpsEssentials getPlugin() {
        return getPlugin(AlpsEssentials.class);
    }

    @Override
    public void reloadConfig() {
        try {
            ConfigUtil.reloadAllConfigs();
        } catch (ConfigurateException e) {
            getComponentLogger().error("Could not reload config files!", e);
        }
        ChatUtils.updateChatFormat();
        ServerUtils.configureServers();
    }

    public static Location getSpawnLocation() {
        SpawnSection spawnConfig = ConfigUtil.getMainConfig().getSpawnSection();
        return new Location(
                Bukkit.getWorld(Objects.requireNonNull(spawnConfig.world())),
                spawnConfig.x(),
                spawnConfig.y(),
                spawnConfig.z(),
                (float) spawnConfig.yaw(),
                (float) spawnConfig.pitch());
    }
}
