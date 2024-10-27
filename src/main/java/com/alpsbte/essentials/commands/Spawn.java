package com.alpsbte.essentials.commands;

import com.alpsbte.essentials.AlpsEssentials;
import com.alpsbte.essentials.utils.ChatUtils;
import com.alpsbte.essentials.utils.io.LangPaths;
import com.alpsbte.essentials.utils.io.LangUtil;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

import static com.alpsbte.essentials.utils.ServerUtils.PERMISSION_PREFIX;

public class Spawn implements BasicCommand {

    @Override
    public void execute(CommandSourceStack commandSourceStack, @NotNull String[] strings) {
        if (!(commandSourceStack.getSender() instanceof Player player)) return;
        player.teleport(AlpsEssentials.getSpawnLocation());
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
        player.sendMessage(ChatUtils.getInfoMessageFormat(LangUtil.getInstance().get(player, LangPaths.TELEPORTING_TO_SPAWN)));
    }

    @Override
    public @Nullable String permission() {
        return PERMISSION_PREFIX + "spawn";
    }

    public static void register(Commands commands) {
        commands.register("spawn", "Teleports to the spawn point.", List.of("hub", "l", "lobby"), new Spawn());
    }
}
