package com.CompactMekanismMachines.common.config;

import com.CompactMekanismMachines.common.CompactMekanismMachines;
import mekanism.common.config.IMekanismConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.IConfigSpec;
import net.neoforged.fml.event.config.ModConfigEvent;

import java.util.HashMap;
import java.util.Map;

public class CompactMekanismMachinesConfig {
    private CompactMekanismMachinesConfig() {
    }

    private static final Map<IConfigSpec, IMekanismConfig> KNOWN_CONFIGS = new HashMap<>();
    public static final CompactMekanismMachinesStorageConfig storage = new CompactMekanismMachinesStorageConfig();


    public static void registerConfigs(ModContainer modContainer) {
        CompactMekanismMachinesConfigHelper.registerConfig(KNOWN_CONFIGS, modContainer, storage);
    }

    public static void onConfigLoad(ModConfigEvent configEvent) {
        CompactMekanismMachinesConfigHelper.onConfigLoad(configEvent, CompactMekanismMachines.MOD_ID, KNOWN_CONFIGS);
    }
}
