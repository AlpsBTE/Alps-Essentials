package com.alpsbte.essentials.config.section;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public record CommandSection(
        @Setting("hub")
        boolean enableHub,
        @Setting("spawn")
        boolean enableSpawn,
        @Setting("switch")
        boolean enableSwitch,
        @Setting("tpp")
        boolean enableTpp,
        @Setting("speed")
        boolean enableSpeed,
        @Setting("ptime")
        boolean enablePTime,
        @Setting("pweather")
        boolean enablePWeather
) {}
