package com.CompactMekanismMachines.common.config;

import mekanism.common.config.MekanismConfigHelper;
import mekanism.generators.common.config.GeneratorsConfig;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;

import com.CompactMekanismMachines.common.config.MachinesConfig;
public class
CompactMekanismMachinesConfig{
    public static final MachinesConfig machines = new MachinesConfig();


    private CompactMekanismMachinesConfig() {
    }

    public static void registerConfigs(ModLoadingContext modLoadingContext) {
        ModContainer modContainer = modLoadingContext.getActiveContainer();
        MekanismConfigHelper.registerConfig(modContainer, machines);
    }
}
