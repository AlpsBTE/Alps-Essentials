package com.alpsbte.essentials.commands.utility;

import com.alpsbte.essentials.AlpsEssentials;
import com.alpsbte.essentials.commands.*;
import com.alpsbte.essentials.utils.io.ConfigPaths;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnstableApiUsage")
public class CommandLauncher {
    private CommandLauncher() {}

    /**
     * Register all specified {@link AlpsCommand commands}.
     */
    public static void register() {
        AlpsEssentials essentials = AlpsEssentials.getPlugin();

        // Register Commands
        LifecycleEventManager<@NotNull Plugin> manager = AlpsEssentials.getPlugin().getLifecycleManager(); manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();

            for (ConfigCommandObject cmd : alpsCommands) {
                if (cmd.path() == null || essentials.getConfig().getBoolean(cmd.path())) {
                    commands.register(cmd.command().node(), cmd.command().description(), cmd.command().aliases());
                }
            }
        });
    }

    /**
     * The commands to be registered.
     */
    static final ConfigCommandObject[]  alpsCommands = new ConfigCommandObject[]{
            new ConfigCommandObject(ConfigPaths.CMD_TPP, new TPPCmd()),
            new ConfigCommandObject(ConfigPaths.CMD_SPAWN, new SpawnCmd()),
            new ConfigCommandObject(ConfigPaths.CMD_SWITCH, new SwitchCmd()),
            new ConfigCommandObject(ConfigPaths.CMD_SPEED, new SpeedCmd()),
            new ConfigCommandObject(ConfigPaths.CMD_PTIME, new PTimeCmd()),
            new ConfigCommandObject(ConfigPaths.CMD_PWEATHER, new PWeatherCmd()),
            new ConfigCommandObject(null, new AlpsReloadCmd()),
            new ConfigCommandObject(ConfigPaths.CMD_HUB, new HubCmd())
    };
}
