package com.alpsbte.essentials.utils;

import com.alpsbte.essentials.AlpsEssentials;
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

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.GREEN;
import static net.kyori.adventure.text.format.NamedTextColor.RED;
import static net.kyori.adventure.text.format.TextDecoration.BOLD;

public class ServerUtils {
    public static boolean isOnline(Server server) {
        return checkForConnection(server);
    }

    public static void updatePlayerCount() {
        Bukkit.getScheduler().runTaskAsynchronously(AlpsEssentials.getPlugin(), () -> {
            Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
            for (Server server : Server.values()) {
                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("PlayerCount");
                out.writeUTF(server.getName());
                player.sendPluginMessage(AlpsEssentials.getPlugin(), "BungeeCord", out.toByteArray());
            }
        });
    }

    public static boolean checkForConnection(Server server) {
        if (!Server.isConfigDataSet()) return false;
        try (Socket s = new Socket()) {
            s.connect(new InetSocketAddress(server.getIP(), server.getPort()), 30);
            return true;
        } catch (IOException ignore) {
            Bukkit.getLogger().log(Level.WARNING, "Could not connect to server (" + server.getName() +
                    " - " + server.getIP() + ":" + server.getPort() + ")");
        }
        return false;
    }

    public static Component getServerStatusComponent(boolean isOnline, Player player) {
        if (isOnline) {
            return text("→ ", GREEN, BOLD)
                    .append(text(LangUtil.getInstance().get(player, LangPaths.CONNECT_TO_SERVER)));
        } else {
            return text("✕ ", RED, BOLD)
                    .append(text(LangUtil.getInstance().get(player, LangPaths.SERVER_IS_OFFLINE)));
        }
    }

    public static void connectToServer(Server server, Player player) {
        if (isOnline(server)) {
            player.sendMessage(ChatUtils.getInfoMessageFormat(LangUtil.getInstance().get(player, LangPaths.CONNECTING_TO_SERVER)));
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);

            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("ConnectOther");
            out.writeUTF(player.getName());
            out.writeUTF(server.getName());
            player.sendPluginMessage(AlpsEssentials.getPlugin(), "BungeeCord", out.toByteArray());
        } else {
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1f, 1f);
            player.sendMessage(ChatUtils.getAlertMessageFormat(LangUtil.getInstance().get(player, LangPaths.SERVER_IS_OFFLINE)));
        }
    }
}
