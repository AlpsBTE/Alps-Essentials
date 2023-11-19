package com.alpsbte.essentials.commands;

import com.alpsbte.essentials.AlpsEssentials;
import com.alpsbte.essentials.utils.ChatUtils;
import com.alpsbte.essentials.utils.io.LangPaths;
import com.alpsbte.essentials.utils.io.LangUtil;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CMD_Spawn extends BukkitCommand {
    public CMD_Spawn(@NotNull String name) {
        super(name);
        this.description = "Teleports to the spawn point.";
        this.usageMessage = "/spawn";
        this.setPermission("alpsbte.spawn");

        List<String> aliases = new ArrayList<>();
        aliases.add("hub");
        aliases.add("l");
        aliases.add("lobby");
        this.setAliases(aliases);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player)sender;

        player.teleport(AlpsEssentials.getSpawnLocation());
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
        player.sendMessage(ChatUtils.getInfoMessageFormat(LangUtil.getInstance().get(player, LangPaths.TELEPORTING_TO_SPAWN)));
        return true;
    }
}
