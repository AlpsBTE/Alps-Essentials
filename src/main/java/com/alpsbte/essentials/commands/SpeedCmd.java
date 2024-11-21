package com.alpsbte.essentials.commands;

import com.alpsbte.essentials.commands.utility.AlpsCommand;
import com.alpsbte.essentials.utils.ChatUtils;
import com.alpsbte.essentials.utils.io.LangPaths;
import com.alpsbte.essentials.utils.io.LangUtil;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("UnstableApiUsage")
public class SpeedCmd implements AlpsCommand {
    @Override
    public @NotNull LiteralCommandNode<CommandSourceStack> node() {
        return Commands.literal("speed")
                .requires(stack -> this.canUseAndIsPlayer(stack.getSender()))
                .then(Commands.literal("1").executes(ctx -> execute(ctx, 1)))
                .then(Commands.literal("2").executes(ctx -> execute(ctx, 2)))
                .then(Commands.literal("3").executes(ctx -> execute(ctx, 3))).build();
    }

    @Override
    public @Nullable String description() {
        return "Sets the flying speed of the player.";
    }

    private int execute(CommandContext<CommandSourceStack> ctx, int modifier) {
        Player player = ((Player) ctx.getSource().getSender()).getPlayer();
            if (player != null) {
                float speed = (float) modifier / 10;
                if (speed >= 0.1 && speed <= 0.4) {
                    player.setFlySpeed(speed);
                    player.sendMessage(ChatUtils.getInfoMessageFormat(LangUtil.getInstance().get(player,
                            LangPaths.SET_PLAYER_SPEED, "<gold>" + modifier + "</gold>")));
                }
            }
        return Command.SINGLE_SUCCESS;
    }

    @Override
    public @Nullable String permission() {
        return PERMISSION_PREFIX + "speed";
    }
}
