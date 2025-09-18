package com.CompactMekanismMachines.common;

import com.mojang.logging.LogUtils;
import mekanism.common.lib.Version;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(CompactMekanismMachines.MODID)
public class CompactMekanismMachines {

    public static final String MODID = "compactmekanismmachines";
    private static final Logger LOGGER = LogUtils.getLogger();

    public final Version versionNumber;

    public CompactMekanismMachines(IEventBus modEventBus, ModContainer modContainer)
    {
        versionNumber = new Version(modContainer);
    }

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(CompactMekanismMachines.MODID, path);
    }
}
