package com.alpsbte.essentials.utils;

import com.alpsbte.essentials.AlpsEssentials;
import com.alpsbte.essentials.utils.io.ConfigPaths;
import com.alpsbte.essentials.utils.io.ConfigUtil;
import com.google.common.io.ByteArrayDataInput;
import org.bukkit.configuration.file.FileConfiguration;

public enum Server {
    HUB_PLOT("name", "localhost", 0, 0),
    TERRA("name","localhost", 0, 0);

    private String name;
    private String IP;
    private int port;
    private int onlinePlayers;
    private static boolean configDataSet = false;

    Server(String name, String IP, int port, int onlinePlayers) {
        this.name = name;
        this.IP = IP;
        this.port = port;
        this.onlinePlayers = onlinePlayers;
    }

    public String getName() {
        return name;
    }

    public String getIP() {
        return IP;
    }

    public int getPort() {
        return port;
    }

    public int getOnlinePlayers() {
        return onlinePlayers;
    }

    public static void setConfigData() {
        if (ConfigUtil.getInstance() == null || ConfigUtil.getInstance().configs.length == 0) return;
        FileConfiguration config = AlpsEssentials.getPlugin().getConfig();
        HUB_PLOT.name = config.getString(ConfigPaths.Server.SERVERS_PLOT_NAME);
        HUB_PLOT.IP = config.getString(ConfigPaths.Server.SERVERS_PLOT_IP);
        HUB_PLOT.port = config.getInt(ConfigPaths.Server.SERVERS_PLOT_PORT);
        TERRA.name = config.getString(ConfigPaths.Server.SERVERS_TERRA_NAME);
        TERRA.IP = config.getString(ConfigPaths.Server.SERVERS_TERRA_IP);
        TERRA.port = config.getInt(ConfigPaths.Server.SERVERS_TERRA_PORT);
        configDataSet = true;
    }

    public static void setPlayerData(ByteArrayDataInput in) {
        String subChannel = in.readUTF();
        if (!subChannel.equals("PlayerCount")) return;

        String server = in.readUTF().toUpperCase();

        if (HUB_PLOT.getName().equals(server)) {
            HUB_PLOT.onlinePlayers = in.readInt();
        } else if (TERRA.getName().equals(server)) {
            TERRA.onlinePlayers = in.readInt();
        }
    }

    public static boolean isConfigDataSet() {
        return configDataSet;
    }
}
