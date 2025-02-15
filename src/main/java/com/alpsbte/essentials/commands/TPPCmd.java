package com.alpsbte.essentials.commands;

import com.alpsbte.essentials.commands.utility.AlpsCommand;
import com.alpsbte.essentials.utils.ChatUtils;
import com.alpsbte.essentials.utils.io.LangPaths;
import com.alpsbte.essentials.utils.io.LangUtil;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class TPPCmd implements AlpsCommand {
    @Override
    public @NotNull LiteralCommandNode<CommandSourceStack> node() {
        return Commands.literal("tpp")
                .requires(stack -> this.canUseAndIsPlayer(stack.getSender()))
                .then(Commands.argument("player", ArgumentTypes.player()).executes(this::execute)).build();
    }

    @Override
    public @Nullable String description() {
        return "Teleports to another player.";
    }

    @Override
    public @NotNull Collection<String> aliases() {
        return List.of("tp");
    }

    private int execute(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        Player targetPlayer = ctx.getArgument("player", PlayerSelectorArgumentResolver.class).resolve(ctx.getSource()).getFirst();
        Player executor = (Player) ctx.getSource().getExecutor();

        if (executor != null) {
            executor.teleport(targetPlayer);
            executor.playSound(executor, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
            executor.sendMessage(ChatUtils.getInfoMessageFormat(LangUtil.getInstance().get(executor, LangPaths.TELEPORTING_TO_PLAYER)));
        }
        return Command.SINGLE_SUCCESS;
    }

    @Override
    public @Nullable String permission() {
        return PERMISSION_PREFIX + "tpp";
    }
}
