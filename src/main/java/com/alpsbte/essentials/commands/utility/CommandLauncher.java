package com.alpsbte.essentials.commands.utility;

import com.alpsbte.essentials.AlpsEssentials;
import com.alpsbte.essentials.commands.*;
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
        // Register Commands
        LifecycleEventManager<@NotNull Plugin> manager = AlpsEssentials.getPlugin().getLifecycleManager(); manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();

            for (AlpsCommand cmd : alpsCommands) {
                if (!cmd.isEnabled()) continue;
                commands.register(cmd.node(), cmd.description(), cmd.aliases());
            }
        });
    }

    /**
     * The commands to be registered.
     */
    static final AlpsCommand[]  alpsCommands = new AlpsCommand[]{
            new TPPCmd(),
            new SpawnCmd(),
            new SwitchCmd(),
            new SpeedCmd(),
            new PTimeCmd(),
            new PWeatherCmd(),
            new AlpsReloadCmd(),
            new HubCmd()
    };
}
