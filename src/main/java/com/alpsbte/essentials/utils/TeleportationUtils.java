package com.alpsbte.essentials.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TeleportationUtils {
    public static boolean teleport(Player player, Location location) {
        return (player.getPassengers().isEmpty() ||
                location.getWorld().equals(player.getWorld()) || player.eject()) &&
                player.teleport(location);
    }
}
