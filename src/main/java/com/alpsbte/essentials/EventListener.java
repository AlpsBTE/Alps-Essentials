package com.alpsbte.essentials;

import com.alpsbte.essentials.utils.io.ConfigPaths;
import com.alpsbte.essentials.utils.io.ConfigUtil;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Openable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;

public class EventListener implements Listener {
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        // Teleport to the spawn point
        if (AlpsEssentials.getPlugin().getConfig().getBoolean(ConfigPaths.TELEPORT_PLAYER_TO_SPAWN_ON_JOIN) ||
                !event.getPlayer().hasPlayedBefore()) {
            event.getPlayer().teleport(AlpsEssentials.getSpawnLocation());
        }
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        // Open/Close iron trap door when right-clicking
        if (!AlpsEssentials.getPlugin().getConfig().getBoolean(ConfigPaths.RIGHT_CLICK_IRON_TRAP_DOORS)) return;
        if (event.getAction().isRightClick() && event.getHand() != EquipmentSlot.OFF_HAND && !event.getPlayer().isSneaking()) {
            if (event.getClickedBlock() != null && event.getItem() == null && event.getClickedBlock().getType() == Material.IRON_TRAPDOOR) {
                RegionQuery query = WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();
                com.sk89q.worldedit.util.Location loc = BukkitAdapter.adapt(event.getPlayer().getLocation());
                com.sk89q.worldedit.world.World world = BukkitAdapter.adapt(event.getPlayer().getWorld());
                if (!WorldGuard.getInstance().getPlatform().getSessionManager().hasBypass(WorldGuardPlugin.inst().wrapPlayer(event.getPlayer()), world)) {
                    if (query.testState(loc, WorldGuardPlugin.inst().wrapPlayer(event.getPlayer()), Flags.INTERACT)) {
                        BlockData data = event.getClickedBlock().getBlockData();

                        if (data instanceof Openable) {
                            Openable door = (Openable) data;

                            if (!door.isOpen()) {
                                door.setOpen(true);
                                event.getPlayer().playSound(event.getClickedBlock().getLocation(), "block.iron_trapdoor.open", 1f, 1f);
                            } else {
                                door.setOpen(false);
                                event.getPlayer().playSound(event.getClickedBlock().getLocation(), "block.iron_trapdoor.close", 1f, 1f);
                            }
                        }
                    }
                }
            }
        }
    }
}
