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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;

import static net.kyori.adventure.text.Component.text;

@SuppressWarnings("UnstableApiUsage")
public class CMD_AlpsReload implements AlpsCommand {

    @Override
    public @NotNull LiteralCommandNode<CommandSourceStack> node() {
        //noinspection UnstableApiUsage
        return Commands.literal("alpsreload")
                .requires(stack -> this.canUse(stack.getSender()))
                .executes(this::execute)
                .build();
    }

    @Override
    public @Nullable String description() {
        return "Reloads all Alps-BTE config files.";
    }

    private int execute(CommandContext<CommandSourceStack> ctx) {
        AlpsEssentials.getPlugin().reloadConfig();

        // Reload other alps-bte plugins
        if (AlpsEssentials.getPlugin().getServer().getPluginManager().isPluginEnabled("Alps-Hub")) {
            try {
                Class.forName("com.alpsbte.hub.AlpsHub").getMethod("reloadPlugin").invoke(this);
            } catch (NoSuchMethodException | ClassNotFoundException | InvocationTargetException | IllegalAccessException ex) {
                AlpsEssentials.getPlugin().getComponentLogger().warn(text("Could not reload Alps-Hub plugin."));
            }
        }

        if (AlpsEssentials.getPlugin().getServer().getPluginManager().isPluginEnabled("Alps-Terra")) {
            try {
                Class.forName("com.alpsbte.terra.AlpsTerra").getMethod("reloadPlugin").invoke(this);
            } catch (NoSuchMethodException | ClassNotFoundException | InvocationTargetException | IllegalAccessException ex) {
                AlpsEssentials.getPlugin().getComponentLogger().warn(text("Could not reload Alps-Terra plugin."));
            }
        }

        ctx.getSource().getSender().sendMessage(ChatUtils.getInfoMessageFormat(LangUtil.getInstance().get(ctx.getSource().getSender(),
                LangPaths.SUCCESSFULLY_RELOADED_PLUGIN)));
        return Command.SINGLE_SUCCESS;
    }
}
