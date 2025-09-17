package com.CompactMekanismMachines.common;

import com.CompactMekanismMachines.common.config.CompactMekanismMachinesConfig;
import com.CompactMekanismMachines.common.registries.CompactBlocks;
import com.CompactMekanismMachines.common.registries.CompactContainerTypes;
import com.CompactMekanismMachines.common.registries.CompactCreativeTabs;
import com.CompactMekanismMachines.common.registries.CompactTileEntityTypes;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(CompactMekanismMachines.MODID)
public class CompactMekanismMachines {

    public static final String MODID = "compactmekanismmachines";
    private static final Logger LOGGER = LogUtils.getLogger();

    public CompactMekanismMachines()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        CompactMekanismMachinesConfig.registerConfigs(ModLoadingContext.get());

        CompactBlocks.BLOCKS.register(modEventBus);
        CompactCreativeTabs.CREATIVE_TABS.register(modEventBus);
        CompactContainerTypes.CONTAINER_TYPES.register(modEventBus);
        CompactTileEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);

    }
    public static ResourceLocation rl(String path) {
        return new ResourceLocation(CompactMekanismMachines.MODID, path);
    }
    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

}
