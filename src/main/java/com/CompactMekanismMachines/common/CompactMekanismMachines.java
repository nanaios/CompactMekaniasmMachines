package com.CompactMekanismMachines.common;

import com.CompactMekanismMachines.common.config.CompactMekanismMachinesConfig;
import com.CompactMekanismMachines.common.registries.*;
import com.mojang.logging.LogUtils;
import mekanism.common.lib.Version;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(CompactMekanismMachines.MOD_ID)
public class CompactMekanismMachines {

    public static final String MOD_ID = "compactmekanismmachines";
    public static final String MOD_NAME = "Compact Mekanism Machines";
    private static final Logger LOGGER = LogUtils.getLogger();

    public final Version versionNumber;

    public CompactMekanismMachines(IEventBus modEventBus, ModContainer modContainer)
    {
        versionNumber = new Version(modContainer);

        CompactMekanismMachinesConfig.registerConfigs(modContainer);
        CompactBlocks.BLOCKS.register(modEventBus);
        CompactContainerTypes.CONTAINER_TYPES.register(modEventBus);
        CompactTileEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);
        CompactCreativeTabs.CREATIVE_TABS.register(modEventBus);

        modEventBus.addListener(CompactMekanismMachinesConfig::onConfigLoad);
    }

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(CompactMekanismMachines.MOD_ID, path);
    }
}
