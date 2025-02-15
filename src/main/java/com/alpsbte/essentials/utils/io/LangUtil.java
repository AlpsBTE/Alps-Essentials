package com.alpsbte.essentials.utils.io;

import com.alpsbte.essentials.AlpsEssentials;
import li.cinnazeyy.langlibs.core.Language;
import li.cinnazeyy.langlibs.core.file.LanguageFile;
import li.cinnazeyy.langlibs.core.language.LangLibAPI;
import li.cinnazeyy.langlibs.core.language.LanguageUtil;

public class LangUtil extends LanguageUtil {
    private static LangUtil langUtilInstance;

    public static void init() {
        if (langUtilInstance != null) return;
        LangLibAPI.register(AlpsEssentials.getPlugin(), new LanguageFile[]{
                new LanguageFile(Language.en_GB, 1.0),
                new LanguageFile(Language.de_DE, 1.0)
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
