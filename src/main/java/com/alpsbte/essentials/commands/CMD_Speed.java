package com.alpsbte.essentials.commands;

import com.alpsbte.essentials.commands.utility.AlpsCommand;
import com.alpsbte.essentials.utils.ChatUtils;
import com.alpsbte.essentials.utils.io.LangPaths;
import com.alpsbte.essentials.utils.io.LangUtil;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("UnstableApiUsage")
public class CMD_Speed implements AlpsCommand {
    @Override
    public @NotNull LiteralCommandNode<CommandSourceStack> node() {
        return Commands.literal("speed")
                .requires(stack -> this.canUseAndIsPlayer(stack.getSender()))
                .then(Commands.argument("modifier", IntegerArgumentType.integer(1, 3)).executes(this::execute))
                .build();
    }

    @Override
    public @Nullable String description() {
        return "Sets the flying speed of the player.";
    }

    private int execute(CommandContext<CommandSourceStack> ctx) {
        var modifier = ctx.getArgument("modifier", int.class);
        if (ctx.getSource().getSender() instanceof Player player && modifier != null) {
            float speed = (float) modifier / 10;
            if (speed >= 0.1 && speed <= 0.4) {
                player.setFlySpeed(speed);
                player.sendMessage(ChatUtils.getInfoMessageFormat(LangUtil.getInstance().get(player,
                        LangPaths.SET_PLAYER_SPEED, "<gold>" + modifier + "</gold>")));
            }
        }
        return Command.SINGLE_SUCCESS;
    }
}
