package com.CompactMekanismMachines.common.config;

import com.CompactMekanismMachines.common.CompactMekanismMachines;
import mekanism.common.config.IMekanismConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.IConfigSpec;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;

import java.util.Map;

public class CompactMekanismMachinesConfigHelper {
    private CompactMekanismMachinesConfigHelper() {
    }

    public static void registerConfig(Map<IConfigSpec, IMekanismConfig> knownConfigs, ModContainer modContainer, IMekanismConfig config) {
        modContainer.registerConfig(config.getConfigType(), config.getConfigSpec(), CompactMekanismMachines.MOD_NAME + "/" + config.getFileName() + ".toml");
        knownConfigs.put(config.getConfigSpec(), config);
    }

    public static void onConfigLoad(ModConfigEvent event, String modid, Map<IConfigSpec, IMekanismConfig> knownConfigs) {
        //Note: We listen to both the initial load and the reload, to make sure that we fix any accidentally
        // cached values from calls before the initial loading
        ModConfig config = event.getConfig();
        //Make sure it is for the same modid as us
        if (config.getModId().equals(modid)) {
            IMekanismConfig mekanismConfig = knownConfigs.get(config.getSpec());
            if (mekanismConfig != null) {
                mekanismConfig.clearCache(event instanceof ModConfigEvent.Unloading);
            }
        }
    }
}
