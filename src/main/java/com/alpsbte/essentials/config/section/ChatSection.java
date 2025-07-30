package com.alpsbte.essentials.config.section;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public record ChatSection (
        @Setting("info-prefix")
        String infoPrefix,

        @Setting("alert-prefix")
        String alertPrefix
) {}