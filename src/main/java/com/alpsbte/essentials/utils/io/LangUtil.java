package com.alpsbte.essentials.utils.io;

import com.alpsbte.essentials.AlpsEssentials;
import li.cinnazeyy.langlibs.core.language.Language;
import li.cinnazeyy.langlibs.core.file.LanguageFile;
import li.cinnazeyy.langlibs.core.LangLibAPI;
import li.cinnazeyy.langlibs.core.language.LanguageUtil;
import org.bukkit.plugin.Plugin;

public class LangUtil extends LanguageUtil {
    private static LangUtil langUtilInstance;

    public static void init() {
        if (langUtilInstance != null) return;
        Plugin plugin = AlpsEssentials.getPlugin();
        LangLibAPI.register(plugin, new LanguageFile[]{
                new LanguageFile(plugin, 1.0, Language.en_GB),
                new LanguageFile(plugin, 1.0, Language.de_DE)
        });
        langUtilInstance = new LangUtil();
    }

    public LangUtil() {
        super(AlpsEssentials.getPlugin());
    }

    public static LangUtil getInstance() {
        return langUtilInstance;
    }
}
