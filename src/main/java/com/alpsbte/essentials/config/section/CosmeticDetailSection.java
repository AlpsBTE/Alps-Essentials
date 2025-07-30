package com.alpsbte.essentials.config.section;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public record CosmeticDetailSection(
        @Setting("name")
        String name,
        @Setting("permission")
        String permission,
        @Setting("material")
        String material,
        @Setting("model-data")
        String modelData
) {}
