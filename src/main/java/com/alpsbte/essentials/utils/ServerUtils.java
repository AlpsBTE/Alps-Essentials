package com.alpsbte.essentials.utils;

import com.alpsbte.essentials.AlpsEssentials;
import com.alpsbte.essentials.utils.io.LangPaths;
import com.alpsbte.essentials.utils.io.LangUtil;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import static net.kyori.adventure.text.Component.text;

public class ServerUtils {

    public static final String PERMISSION_PREFIX = "alpsbte.";

    private ServerUtils() {
        throw new IllegalStateException("Utility class"); // Disable instantiation (Static Class)
    }

    public static boolean isOnline(Server server) {
        return checkForConnection(server);
    }

    public static boolean checkForConnection(Server server) {
        if (!Server.isConfigDataSet()) return false;
        try (Socket s = new Socket()) {
            s.connect(new InetSocketAddress(server.getIP(), server.getPort()), 30);
            return true;
        } catch (IOException ignore) {
            AlpsEssentials.getPlugin().getComponentLogger().warn(text("Could not connect to server (" + server.getName() +
                " - " + server.getIP() + ":" + server.getPort() + ")"));
        }
        return false;
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
