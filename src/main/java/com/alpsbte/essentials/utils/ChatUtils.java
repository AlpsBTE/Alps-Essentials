package com.alpsbte.essentials.utils;

import com.alpsbte.alpslib.utils.AlpsUtils;
import net.kyori.adventure.text.Component;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.GREEN;
import static net.kyori.adventure.text.format.NamedTextColor.RED;

public class ChatUtils {
    public static void setChatFormat(String infoPrefix, String alertPrefix, String arrow) {
        ChatUtils.infoPrefix = AlpsUtils.deserialize(infoPrefix);
        ChatUtils.alertPrefix = AlpsUtils.deserialize(alertPrefix);
        ChatUtils.arrow = AlpsUtils.deserialize(arrow);
    }
    private static Component infoPrefix;
    private static Component alertPrefix;
    private static Component arrow;

    public static Component getInfoMessageFormat(String info) {
        return infoPrefix.append(arrow).append(text(info, GREEN));
    }

    public static Component getAlertMessageFormat(String alert) {
        return alertPrefix.append(arrow).append(text(alert, RED));
    }

    public static Component getArrow() {
        return arrow;
    }
}
