package com.alpsbte.essentials.utils;

import com.alpsbte.alpslib.utils.AlpsUtils;
import com.alpsbte.essentials.config.ConfigUtil;
import com.alpsbte.essentials.config.section.ChatSection;
import net.kyori.adventure.text.Component;

import static net.kyori.adventure.text.format.NamedTextColor.GREEN;
import static net.kyori.adventure.text.format.NamedTextColor.RED;

public class ChatUtils {
    private ChatUtils() {}

    public static void updateChatFormat() {
        ChatSection chatConfig = ConfigUtil.getMainConfig().getChatSection();
        infoPrefix = AlpsUtils.deserialize(chatConfig.infoPrefix());
        alertPrefix = AlpsUtils.deserialize(chatConfig.alertPrefix());
    }

    private static Component infoPrefix, alertPrefix;

    public static Component getInfoMessageFormat(String info) {
        return infoPrefix.append(AlpsUtils.deserialize(info).color(GREEN));
    }

    public static Component getInfoMessageFormat(Component info) {
        return infoPrefix.append(info);
    }

    public static Component getAlertMessageFormat(String alert) {
        return alertPrefix.append(AlpsUtils.deserialize(alert).color(RED));
    }
}
