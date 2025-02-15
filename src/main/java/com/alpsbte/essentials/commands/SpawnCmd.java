package com.alpsbte.essentials.commands;

import com.alpsbte.essentials.AlpsEssentials;
import com.alpsbte.essentials.commands.utility.AlpsCommand;
import com.alpsbte.essentials.utils.ChatUtils;
import com.alpsbte.essentials.utils.io.LangPaths;
import com.alpsbte.essentials.utils.io.LangUtil;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class SpawnCmd implements AlpsCommand {
    @Override
    public @NotNull LiteralCommandNode<CommandSourceStack> node() {
        return Commands.literal("spawn")
                .requires(stack -> this.canUseAndIsPlayer(stack.getSender()))
                .executes(this::execute)
                .build();
    }

    @Override
    public @Nullable String description() {
        return "Teleports to the spawn point.";
    }

    @Override
    public @NotNull Collection<String> aliases() {
        return List.of("hub", "l", "lobby");
    }

    private int execute(CommandContext<CommandSourceStack> ctx) {
        if (ctx.getSource().getSender() instanceof Player player) {
            player.teleport(AlpsEssentials.getSpawnLocation());
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
            player.sendMessage(ChatUtils.getInfoMessageFormat(LangUtil.getInstance().get(player, LangPaths.TELEPORTING_TO_SPAWN)));
        }
        return Command.SINGLE_SUCCESS;
    }

    @Override
    public @Nullable String permission() {
        return PERMISSION_PREFIX + "spawn";
    }
}
