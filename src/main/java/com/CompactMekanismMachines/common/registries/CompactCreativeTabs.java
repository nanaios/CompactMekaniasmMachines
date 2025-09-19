package com.CompactMekanismMachines.common.registries;

import com.CompactMekanismMachines.common.CompactLang;
import com.CompactMekanismMachines.common.CompactMekanismMachines;
import mekanism.common.registration.MekanismDeferredHolder;
import mekanism.common.registration.impl.CreativeTabDeferredRegister;
import mekanism.common.registries.MekanismCreativeTabs;
import net.minecraft.world.item.CreativeModeTab;

public class CompactCreativeTabs {
    private CompactCreativeTabs() {}

    public static final CreativeTabDeferredRegister CREATIVE_TABS = new CreativeTabDeferredRegister(CompactMekanismMachines.MOD_ID);

    public static final MekanismDeferredHolder<CreativeModeTab,CreativeModeTab> TAB;

    static {
        TAB = CREATIVE_TABS.registerMain(CompactLang.COMPACTMEKANISMMACHINES, CompactBlocks.COMPACT_FISSION_REACTOR.getItemHolder(), builder ->
                builder.withTabsBefore(MekanismCreativeTabs.MEKANISM.getKey())
                        .displayItems((displayParameters, output) -> {
                            CreativeTabDeferredRegister.addToDisplay(CompactBlocks.BLOCKS, output);
                        })
        );
    }
}
