package com.alpsbte.essentials.config;

import com.alpsbte.essentials.AlpsEssentials;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.objectmapping.ObjectMapper;
import org.spongepowered.configurate.util.NamingSchemes;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;

public class ConfigUtil {
    private ConfigUtil() {}

    // Main Config
    private static MainConfig mainConfig = null;
    private static YamlConfigurationLoader configLoader = null;

    public static void init() throws ConfigurateException {
        File configFile = AlpsEssentials.getPlugin().getDataPath().resolve("config.yml").toFile();
        if (!configFile.exists()) {
            AlpsEssentials.getPlugin().saveResource("config.yml", false);
        }

        final ObjectMapper.Factory customFactory = ObjectMapper.factoryBuilder()
                .defaultNamingScheme(NamingSchemes.PASSTHROUGH)
                .build();

        configLoader = YamlConfigurationLoader.builder()
                .file(configFile)
                .nodeStyle(NodeStyle.BLOCK)
                .defaultOptions(opts -> opts.serializers(build -> build.registerAnnotatedObjects(customFactory)))
                .build();

        loadConfigFiles();
    }

    private static void loadConfigFiles() throws ConfigurateException {
        CommentedConfigurationNode configRoot = configLoader.load();
        mainConfig = configRoot.get(MainConfig.class);
    }

    public static void reloadAllConfigs() throws ConfigurateException {
        loadConfigFiles();
    }

    public static MainConfig getMainConfig() {return mainConfig;}
}
