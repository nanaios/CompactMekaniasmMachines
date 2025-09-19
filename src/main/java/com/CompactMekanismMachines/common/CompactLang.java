package com.CompactMekanismMachines.common;

import mekanism.api.text.ILangEntry;
import net.minecraft.Util;
import org.jetbrains.annotations.NotNull;

public enum CompactLang implements ILangEntry {
    DESCRIPTION_COMPRESSED_WIND_GENERATOR_x131072("description","compressed_wind_generator_x131072"),
    DESCRIPTION_COMPRESSED_WIND_GENERATOR_x532480("description","compressed_wind_generator_x532480"),
    COMPACTMEKANISMMACHINES("other","mod_name");

    private final String key;
    CompactLang(String type, String path) {
        this(Util.makeDescriptionId(type, CompactMekanismMachines.rl(path)));
    }

    CompactLang(String key) {
        this.key = key;
    }


    @Override
    public @NotNull String getTranslationKey() {
        return key;
    }
}
