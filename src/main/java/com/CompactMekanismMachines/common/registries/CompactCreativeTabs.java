package com.CompactMekanismMachines.common.registries;

import com.CompactMekanismMachines.common.CompactLang;
import mekanism.common.registration.impl.CreativeTabDeferredRegister;
import mekanism.common.registration.impl.CreativeTabRegistryObject;
import mekanism.common.registries.MekanismCreativeTabs;
import mekanism.generators.common.MekanismGenerators;
import mekanism.generators.common.registries.GeneratorsBlocks;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;

public class CompactCreativeTabs {
    public static final CreativeTabDeferredRegister CREATIVE_TABS = new CreativeTabDeferredRegister(MekanismGenerators.MODID, CompactCreativeTabs::addToExistingTabs);

    public static final CreativeTabRegistryObject GENERATORS = CREATIVE_TABS.registerMain(CompactLang.COMPACTMEKANISMMACHINES, CompactBlocks.COMPACT_FISSION_REACTOR, builder ->
            builder.withBackgroundLocation(MekanismGenerators.rl("textures/gui/creative_tab.png"))
                    .withSearchBar(50)//Allow our tabs to be searchable for convenience purposes
                    .withTabsBefore(MekanismCreativeTabs.MEKANISM.key())
                    .displayItems((displayParameters, output) -> {
                        CreativeTabDeferredRegister.addToDisplay(CompactBlocks.BLOCKS, output);
                    })
    );

    private static void addToExistingTabs(BuildCreativeModeTabContentsEvent event) {
        ResourceKey<CreativeModeTab> tabKey = event.getTabKey();
        if (tabKey == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            CreativeTabDeferredRegister.addToDisplay(event,
                    CompactBlocks.WIND_GENERATOR_X2,
                    CompactBlocks.WIND_GENERATOR_X8,
                    CompactBlocks.WIND_GENERATOR_X32,
                    CompactBlocks.WIND_GENERATOR_X128,
                    CompactBlocks.WIND_GENERATOR_X512,
                    CompactBlocks.WIND_GENERATOR_X2048,
                    CompactBlocks.WIND_GENERATOR_X8192,
                    CompactBlocks.WIND_GENERATOR_X32768,
                    CompactBlocks.WIND_GENERATOR_X131072,
                    CompactBlocks.WIND_GENERATOR_X532480,
                    CompactBlocks.COMPACT_FISSION_REACTOR,
                    CompactBlocks.COMPACT_INDUSTRIAL_TURBINE);
        }
    }
}
