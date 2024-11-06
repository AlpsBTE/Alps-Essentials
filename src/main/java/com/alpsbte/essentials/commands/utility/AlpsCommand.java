package com.alpsbte.essentials.commands.utility;

import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;

@SuppressWarnings("UnstableApiUsage")
public interface AlpsCommand {
    /**
     * The permission prefix for all commands.
     */
    String PERMISSION_PREFIX = "alpsbte.";

    /**
     * Returns the command node of the command.
     *
     * @return the root command node of the command
     */
    @NotNull LiteralCommandNode<CommandSourceStack> node();

    @Nullable String description();

    default @NotNull Collection<String> aliases() {
        return Collections.emptyList();
    }

    /**
     * Checks whether a command sender can receive and run the root command.
     *
     * @param sender the command sender trying to execute the command
     * @return whether the command sender fulfills the root command requirement
     * @see #permission()
     */
    default boolean canUse(final CommandSender sender) {
        final String permission = this.permission();
        return permission == null || sender.hasPermission(permission);
    }

    /**
     * Checks whether a command sender is a player and has the permission to run the command.
     *
     * @param sender the command sender trying to execute the command
     * @return whether the command sender fulfills the root command requirement and is a player
     * @see #canUse(CommandSender)
     */
    default boolean canUseAndIsPlayer(final CommandSender sender) {
        return sender instanceof org.bukkit.entity.Player && this.canUse(sender);
    }

    /**
     * Returns the permission for the root command used in {@link #canUse(CommandSender)} by default.
     *
     * @return the permission for the root command used in {@link #canUse(CommandSender)}
     */
    default @Nullable String permission() {
        return PERMISSION_PREFIX + this.getClass().getSimpleName().toLowerCase();
    }
}
