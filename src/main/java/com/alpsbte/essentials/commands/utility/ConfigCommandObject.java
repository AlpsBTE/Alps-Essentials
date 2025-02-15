package com.alpsbte.essentials.commands.utility;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a command with a optional config that can be registered.
 *
 * @param path    The path to the config value that determines whether the command should be registered.
 * @param command The {@link AlpsCommand command} to be registered.
 */
public record ConfigCommandObject(@Nullable String path, @NotNull AlpsCommand command) {}
