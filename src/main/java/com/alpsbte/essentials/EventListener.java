package com.alpsbte.essentials;

import com.alpsbte.alpslib.utils.item.ItemBuilder;
import com.alpsbte.essentials.config.section.CosmeticDetailSection;
import com.alpsbte.essentials.utils.ChatUtils;
import com.alpsbte.essentials.config.ConfigUtil;
import com.alpsbte.essentials.utils.io.LangPaths;
import com.alpsbte.essentials.utils.io.LangUtil;
import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
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
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EventListener implements Listener {
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        if (!ConfigUtil.getMainConfig().getSendJoinLeaveMessage()) event.joinMessage(null);

        // Teleport to the spawn point
        if (ConfigUtil.getMainConfig().getTeleportToSpawnOnJoin() || !event.getPlayer().hasPlayedBefore()) {
            event.getPlayer().teleport(AlpsEssentials.getSpawnLocation());
        }

        // Check for patreon hat cosmetic
        setCosmetics(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        if (!ConfigUtil.getMainConfig().getSendJoinLeaveMessage()) event.quitMessage(null);
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
        }, 20 * 60 * 10L); // in 10 minutes
    }

    @EventHandler
    public void onInventoryCreativeEvent(InventoryCreativeEvent event) {
        if (isAnyHat(event.getCursor())) {
            event.setResult(Event.Result.DENY);
            event.setCancelled(true);
        }

        if (event.getSlotType() == InventoryType.SlotType.ARMOR && isAnyHat(event.getCurrentItem())) {
            event.setResult(Event.Result.DENY);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerItemDropEvent(@NotNull PlayerDropItemEvent event) {
        if (isAnyHat(event.getItemDrop().getItemStack())) event.setCancelled(true);
    }

    private void setCosmetics(Player player) {
        if (!ConfigUtil.getMainConfig().getCosmeticSection().enabled()) return;

        List<CosmeticDetailSection> hats = ConfigUtil.getMainConfig().getCosmeticSection().hats();

        for (CosmeticDetailSection hat : hats) {
            if (player.hasPermission(hat.permission())) setHat(player, hat);
            else removeHat(player, hat);
        }
    }

    private void setHat(Player player, CosmeticDetailSection hat) {
        player.getInventory().setHelmet(new ItemBuilder(Material.PAPER)
                .setName(Component.text(hat.name(), NamedTextColor.YELLOW, TextDecoration.BOLD))
                .setItemModel(hat.modelData())
                .build());
    }

    private void removeHat(Player player, CosmeticDetailSection hat) {
        if (!isHat(player.getInventory().getHelmet(), hat)) return;
        player.getInventory().setHelmet(null);
    }

    private boolean isHat(ItemStack item, CosmeticDetailSection hat) {
        if (item == null) return false;
        if (!(item.displayName() instanceof TextComponent text)) return false;
        if (!text.content().equals(hat.name())) return false;
        return item.getItemMeta().getCustomModelDataComponent().getStrings().contains(hat.modelData());
    }

    private boolean isAnyHat(ItemStack item) {
        for (CosmeticDetailSection hat : ConfigUtil.getMainConfig().getCosmeticSection().hats()) {
            if (isHat(item, hat)) return true;
        }
        return false;
    }
}
