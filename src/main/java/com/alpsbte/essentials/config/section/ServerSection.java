package com.alpsbte.essentials.config.section;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public record ServerSection(
        @Setting("name")
        String name,
        @Setting("address")
        String address,
        @Setting("port")
        int port
) {}
