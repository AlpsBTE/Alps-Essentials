package com.alpsbte.essentials.commands;

import com.alpsbte.essentials.AlpsEssentials;
import com.alpsbte.essentials.utils.ChatUtils;
import com.alpsbte.essentials.utils.io.LangPaths;
import com.alpsbte.essentials.utils.io.LangUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;

public class CMD_Reload extends BukkitCommand {
    public CMD_Reload(@NotNull String name) {
        super(name);
        this.description = "Reloads all Alps-BTE config files.";
        this.usageMessage = "/alpsreload";
        this.setPermission("alpsbte.alpsreload");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        AlpsEssentials.getPlugin().reloadConfig();

        // Reload other alps-bte plugins
        if (AlpsEssentials.getPlugin().getServer().getPluginManager().isPluginEnabled("Alps-Hub")) {
            try {
                Class.forName("com.alpsbte.hub.AlpsHub").getMethod("reloadPlugin")
                        .invoke(this);
            } catch (NoSuchMethodException | ClassNotFoundException |
                     InvocationTargetException | IllegalAccessException ex) {
                Bukkit.getLogger().warning("Could not reload Alps-Hub plugin.");
            }
        }

        if (AlpsEssentials.getPlugin().getServer().getPluginManager().isPluginEnabled("Alps-Terra")) {
            try {
                Class.forName("com.alpsbte.hub.AlpsTerra").getMethod("reloadPlugin")
                        .invoke(this);
            } catch (NoSuchMethodException | ClassNotFoundException |
                     InvocationTargetException | IllegalAccessException ex) {
                Bukkit.getLogger().warning("Could not reload Alps-Terra plugin.");
            }
        }

        sender.sendMessage(ChatUtils.getInfoMessageFormat(LangUtil.getInstance().get(sender, LangPaths.SUCCESSFULLY_RELOADED_PLUGIN)));
        return true;
    }
}
