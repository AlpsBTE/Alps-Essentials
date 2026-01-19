package com.alpsbte.essentials.config;

import com.alpsbte.essentials.config.section.*;
import org.bukkit.entity.Player;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Setting;

import java.util.List;

@ConfigSerializable
public class MainConfig {
    @Comment("Chat Formatting")
    @Setting("chat-format")
    private ChatSection chat;

    @Comment("Spawn Location")
    @Setting("spawn")
    private SpawnSection spawn;

    @Comment("Servers")
    @Setting("servers")
    private List<ServerSection> servers;

    @Comment("The name of the server which acts as a hub. (needed for /hub command)")
    @Setting("hub-server")
    private String hubServer;

    @Comment("Enable/Disable Commands")
    @Setting("commands")
    private CommandSection commands;

    @Comment("Enable/Disable join and leave messages")
    @Setting("send-join-leave-message")
    private boolean sendJoinLeaveMessage;

    @Comment("Teleport the player to the configured spawn location on join")
    @Setting("teleport-player-to-spawn-on-join")
    private boolean teleportToSpawnOnJoin;

    @Comment("Teleport the player to the configured spawn location on first join")
    @Setting("teleport-player-to-spawn-on-first-join")
    private boolean teleportToSpawnOnFirstJoin;

    @Comment("Cosmetics")
    @Setting("cosmetics")
    private CosmeticSection cosmetics;

    @Comment("Configurable donation message")
    @Setting("donation-message")
    private DonationSection donation;

    @SuppressWarnings("unused")
    @Comment("NOTE: Do not change!")
    @Setting("version")
    private String version;

    public ChatSection getChatSection() {return chat;}

    public SpawnSection getSpawnSection() {return spawn;}

    public List<ServerSection> getServerSections() {return servers;}

    public String getHubServerName() {return hubServer;}

    public CommandSection getCommandSection() {return commands;}

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean getSendJoinLeaveMessage() {return sendJoinLeaveMessage;}

    public boolean getTeleportToSpawnOnJoin() {return teleportToSpawnOnJoin;}

    public boolean getTeleportToSpawnOnFirstJoin(Player p ) {return teleportToSpawnOnFirstJoin && !p.hasPlayedBefore();}

    public CosmeticSection getCosmeticSection() {
        return cosmetics;
    }

    public DonationSection getDonationSection() {
        return donation;
    }
}
