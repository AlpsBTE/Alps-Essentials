package com.alpsbte.essentials;

import com.alpsbte.alpslib.utils.item.ItemBuilder;
import com.alpsbte.essentials.utils.ChatUtils;
import com.alpsbte.essentials.utils.io.ConfigPaths;
import com.alpsbte.essentials.utils.io.ConfigUtil;
import com.alpsbte.essentials.utils.io.LangPaths;
import com.alpsbte.essentials.utils.io.LangUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventListener implements Listener {
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        if (!AlpsEssentials.getPlugin().getConfig().getBoolean(ConfigPaths.SEND_JOIN_MESSAGE))
            event.joinMessage(null);

        // Teleport to the spawn point
        if (AlpsEssentials.getPlugin().getConfig().getBoolean(ConfigPaths.TELEPORT_PLAYER_TO_SPAWN_ON_JOIN) ||
                !event.getPlayer().hasPlayedBefore()) {
            event.getPlayer().teleport(AlpsEssentials.getSpawnLocation());
        }

        // Check for patreon hat cosmetic
        if (event.getPlayer().hasPermission("alpsbte.patreonTier1")) {
            event.getPlayer().getInventory().setHelmet(new ItemBuilder(Material.PAPER)
                    .setName(Component.text("Construction Helmet", NamedTextColor.YELLOW, TextDecoration.BOLD))
                    .setItemModel(ConfigUtil.getInstance().configs[0].getInt(ConfigPaths.COSMETIC_PATREON_HAT_MODEL_DATA))
                    .build());
        } else if (event.getPlayer().getInventory().getHelmet() != null && event.getPlayer().getInventory().getHelmet()
                .getItemMeta().getCustomModelData() == ConfigUtil.getInstance().configs[0].getInt(ConfigPaths.COSMETIC_PATREON_HAT_MODEL_DATA)) {
            event.getPlayer().getInventory().setHelmet(null);
        }
    }

    @EventHandler
    public void onPlayerJoinShowDonationMessage(PlayerJoinEvent event) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(AlpsEssentials.getPlugin(), () -> {
            Player p = event.getPlayer();

            String introduction = LangUtil.getInstance().get(p, LangPaths.DONATION_MESSAGE_INTRODUCTION);
            String text = LangUtil.getInstance().get(p, LangPaths.DONATION_MESSAGE_TEXT);
            String link = LangUtil.getInstance().get(p, LangPaths.DONATION_MESSAGE_LINK);
            String thanks = LangUtil.getInstance().get(p, LangPaths.DONATION_MESSAGE_THANKS);
            String linkHover = LangUtil.getInstance().get(p, LangPaths.LINK_HOVER);

            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.MASTER, 1f, 1f);

            p.sendMessage("");
            p.sendMessage(ChatUtils.getInfoMessageFormat(Component.text(introduction, NamedTextColor.YELLOW)));
            p.sendMessage("");
            p.sendMessage(Component.text(text, NamedTextColor.GRAY));
            p.sendMessage("");
            p.sendMessage(Component.text("» ", NamedTextColor.DARK_GRAY)
                    .append(Component.text(link, NamedTextColor.GREEN)
                            .clickEvent(ClickEvent.openUrl("https://www.tipeeestream.com/alps-bte/donation"))
                            .hoverEvent(HoverEvent.showText(Component.text(linkHover, NamedTextColor.GRAY)))));
            p.sendMessage("");
            p.sendMessage(Component.text(thanks, NamedTextColor.GRAY));
            p.sendMessage("");
        }, 20 * 60 * 10); // in 10 minutes
    }

    @EventHandler
    public void onInventoryCreativeEvent(InventoryCreativeEvent event) {
        if (event.getCursor().hasItemMeta() && event.getCursor().getItemMeta().hasCustomModelData() && event.getCursor().getItemMeta().getCustomModelData() ==
                ConfigUtil.getInstance().configs[0].getInt(ConfigPaths.COSMETIC_PATREON_HAT_MODEL_DATA)) {
            event.setResult(Event.Result.DENY);
            event.setCancelled(true);
        }

        if (event.getSlotType() == InventoryType.SlotType.ARMOR) {
            if (event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta() &&
                    event.getCurrentItem().getItemMeta().hasCustomModelData() && event.getCurrentItem().getItemMeta().getCustomModelData() ==
                    ConfigUtil.getInstance().configs[0].getInt(ConfigPaths.COSMETIC_PATREON_HAT_MODEL_DATA)) {
                event.setResult(Event.Result.DENY);
                event.setCancelled(true);
            }
        }
    }
}
