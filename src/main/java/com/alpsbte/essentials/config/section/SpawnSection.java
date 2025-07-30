package com.alpsbte.essentials.config.section;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public record SpawnSection (
        @Setting("world")
        String world,

        @Setting("x")
        double x,

        @Setting("y")
        double y,

        @Setting("z")
        double z,

        @Setting("pitch")
        double pitch,

        @Setting("yaw")
        double yaw
) {}
