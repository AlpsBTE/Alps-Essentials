package com.alpsbte.essentials.commands;

import com.alpsbte.essentials.utils.ChatUtils;
import com.alpsbte.essentials.utils.io.LangPaths;
import com.alpsbte.essentials.utils.io.LangUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.RED;

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
        if (!(sender instanceof Player player)) return true;

        if (args.length == 1) {
            Player targetPlayer = player.getServer().getPlayer(args[0]);

            if (targetPlayer != null) {
                player.teleport(targetPlayer);
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
                player.sendMessage(ChatUtils.getInfoMessageFormat(LangUtil.getInstance().get(player, LangPaths.TELEPORTING_TO_PLAYER)));
                return true;
            }
        }

        player.sendMessage(ChatUtils.getAlertMessageFormat(LangUtil.getInstance().get(sender, LangPaths.COMMAND_USAGE))
                .append(text(": /tpp <player>", RED)));
        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            return Bukkit.getOnlinePlayers().stream()
                    .filter(p -> !p.getUniqueId().toString().equals(player.getUniqueId().toString()))
                    .map(Player::getName).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
