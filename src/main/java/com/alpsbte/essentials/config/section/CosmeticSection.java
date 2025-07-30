package com.alpsbte.essentials.config.section;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

import java.util.List;

@ConfigSerializable
public record CosmeticSection(
        @Setting("enabled")
        boolean enabled,
        @Setting("hats")
        List<CosmeticDetailSection> hats
) {}
