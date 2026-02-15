package com.alpsbte.essentials.config.section;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@ConfigSerializable
public record DonationSection(
        boolean enabled,
        @Comment("After how many minutes the message should be shown")
        int minutes,
        String url
) {}
