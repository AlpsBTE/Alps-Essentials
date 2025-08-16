package com.alpsbte.essentials.utils;

import com.alpsbte.essentials.AlpsEssentials;
import com.alpsbte.essentials.config.ConfigUtil;
import com.alpsbte.essentials.config.section.ServerSection;
import com.alpsbte.essentials.utils.io.LangPaths;
import com.alpsbte.essentials.utils.io.LangUtil;
import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.GREEN;
import static net.kyori.adventure.text.format.NamedTextColor.RED;
import static net.kyori.adventure.text.format.TextDecoration.BOLD;

public class ServerUtils {
    private ServerUtils() {}

    private static final List<Server> CONFIGURED_SERVERS = new ArrayList<>();

    public static void configureServers() {
        List<ServerSection> servers = ConfigUtil.getMainConfig().getServerSections();
        for (ServerSection s : servers) {
            CONFIGURED_SERVERS.add(new Server(s.name(), s.address(), s.port()));
        }
        AlpsEssentials.getPlugin().getComponentLogger().info("Configured {} servers.", servers.size());
    }

    public static Optional<Server> getServerByName(String name) {
        return CONFIGURED_SERVERS.stream().filter(s -> s.getName().equals(name)).findAny();
    }

    public static List<Server> getAllServers() {
        return CONFIGURED_SERVERS;
    }

    public static void setPlayerData(ByteArrayDataInput in) {
        String subChannel = in.readUTF();
        if (!subChannel.equals("PlayerCount")) return;

        String serverName = in.readUTF().toUpperCase();
        Optional<Server> server = getServerByName(serverName);
        if (server.isEmpty()) {
            AlpsEssentials.getPlugin().getComponentLogger().info("Could not set player data for server {}! Server not found.", serverName);
            return;
        }

        server.get().setOnlinePlayers(in.readInt());
    }

    public static boolean isOnline(Server server) {
        return checkForConnection(server);
    }

    public static boolean checkForConnection(Server server) {
        try (Socket s = new Socket()) {
            s.connect(new InetSocketAddress(server.getAddress(), server.getPort()), 30);
            return true;
        } catch (IOException ignore) {
            AlpsEssentials.getPlugin().getComponentLogger().warn(text("Could not connect to server (" + server.getName() +
                    " - " + server.getAddress() + ":" + server.getPort() + ")"));
        }
        return false;
    }

    @SuppressWarnings("unused")
    public static void updatePlayerCount() {
        Bukkit.getScheduler().runTaskAsynchronously(AlpsEssentials.getPlugin(), () -> {
            Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
            for (Server server : CONFIGURED_SERVERS) {
                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("PlayerCount");
                out.writeUTF(server.getName());
                player.sendPluginMessage(AlpsEssentials.getPlugin(), AlpsEssentials.PLUGIN_CHANNEL, out.toByteArray());
            }
        });
    }

    @SuppressWarnings("unused")
    public static @NotNull Component getServerStatusComponent(boolean isOnline, Player player) {
        if (isOnline) {
            return text("→ ", GREEN, BOLD)
                    .append(text(LangUtil.getInstance().get(player, LangPaths.CONNECT_TO_SERVER)));
        } else {
            return text("✕ ", RED, BOLD)
                    .append(text(LangUtil.getInstance().get(player, LangPaths.SERVER_IS_OFFLINE)));
        }
    }

    public static void connectToServer(Server server, Player player) {
        if (!isOnline(server)) {
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1f, 1f);
            player.sendMessage(ChatUtils.getAlertMessageFormat(LangUtil.getInstance().get(player, LangPaths.SERVER_IS_OFFLINE)));
            return;
        }

        player.sendMessage(ChatUtils.getInfoMessageFormat(LangUtil.getInstance().get(player, LangPaths.CONNECTING_TO_SERVER)));
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("ConnectOther");
        out.writeUTF(player.getName());
        out.writeUTF(server.getName());
        player.sendPluginMessage(AlpsEssentials.getPlugin(), AlpsEssentials.PLUGIN_CHANNEL, out.toByteArray());
    }
}
