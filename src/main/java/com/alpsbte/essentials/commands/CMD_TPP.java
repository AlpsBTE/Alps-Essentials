package com.alpsbte.essentials.commands;

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

public class CMD_TPP extends BukkitCommand {
    public CMD_TPP(@NotNull String name) {
        super(name);
        this.description = "Teleports to another player.";
        this.usageMessage = "/tpp <player>";
        this.setPermission("alpsbte.tpp");

        List<String> aliases = new ArrayList<>();
        aliases.add("tp");
        this.setAliases(aliases);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player)sender;

        if (args.length == 1) {
            Player targetPlayer = player.getServer().getPlayer(args[0]);

            if (targetPlayer != null) {
                player.teleport(targetPlayer);
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
                player.sendMessage(ChatUtils.getInfoMessageFormat(LangUtil.getInstance().get(player, LangPaths.TELEPORTING_TO_PLAYER)));
                return true;
            }
        }

        player.sendMessage(ChatUtils.getAlertMessageFormat("Usage: /tpp <Player>"));
        return true;
    }
}
