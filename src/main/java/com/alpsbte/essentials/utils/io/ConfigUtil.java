package com.alpsbte.essentials.utils.io;

import com.alpsbte.alpslib.io.config.ConfigNotImplementedException;
import com.alpsbte.alpslib.io.config.ConfigurationUtil;

import java.nio.file.Paths;

public class ConfigUtil {
    private static ConfigurationUtil configUtilInstance;

    public static void init() throws ConfigNotImplementedException {
        if (configUtilInstance != null) return;
        configUtilInstance = new ConfigurationUtil(new ConfigurationUtil.ConfigFile[]{
                new ConfigurationUtil.ConfigFile(Paths.get("config.yml"), 1.5, true)
        });
    }

    public static ConfigurationUtil getInstance() {
        return configUtilInstance;
    }
}
