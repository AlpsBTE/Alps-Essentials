package com.alpsbte.essentials.utils.io;

public abstract class ConfigPaths {
    // Chat Formatting
    private static final String CHAT_FORMAT = "chat-format.";
    public static final String CHAT_FORMAT_INFO_PREFIX = CHAT_FORMAT + "info-prefix";
    public static final String CHAT_FORMAT_ALERT_PREFIX = CHAT_FORMAT + "alert-prefix";
    public static final String CHAT_FORMAT_ARROW = CHAT_FORMAT + "arrow";

    // Spawn
    private static final String SPAWN = "spawn.";
    public static final String SPAWN_WORLD = SPAWN + "world";
    public static final String SPAWN_X = SPAWN + "x";
    public static final String SPAWN_Y = SPAWN + "y";
    public static final String SPAWN_Z = SPAWN + "z";
    public static final String SPAWN_YAW = SPAWN + "yaw";
    public static final String SPAWN_PITCH = SPAWN + "pitch";

    // Servers
    public static final class Server {
        private static final String SERVERS = "servers.";
        private static final String SERVERS_PLOT = SERVERS + "plot.plot-";
        public static final String SERVERS_PLOT_NAME = SERVERS_PLOT + "name";
        public static final String SERVERS_PLOT_IP = SERVERS_PLOT + "IP";
        public static final String SERVERS_PLOT_PORT = SERVERS_PLOT + "port";
        private static final String SERVERS_TERRA = SERVERS + "terra.terra-";
        public static final String SERVERS_TERRA_NAME = SERVERS_TERRA + "name";
        public static final String SERVERS_TERRA_IP = SERVERS_TERRA + "IP";
        public static final String SERVERS_TERRA_PORT = SERVERS_TERRA + "port";
    }

    // Commands
    public static final String CMD_HUB = "cmd-hub";
    public static final String CMD_SPAWN = "cmd-spawn";
    public static final String CMD_SWITCH = "cmd-switch";
    public static final String CMD_TPP = "cmd-tpp";
    public static final String CMD_SPEED = "cmd-speed";
    public static final String CMD_PTIME = "cmd-ptime";
    public static final String CMD_PWEATHER = "cmd-pweather";

    // Others
    public static final String RIGHT_CLICK_IRON_TRAP_DOORS = "right-click-iron-trap-doors";
    public static final String TELEPORT_PLAYER_TO_SPAWN_ON_JOIN = "teleport-player-to-spawn-on-join";
}
