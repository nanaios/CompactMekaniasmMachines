package com.CompactMekanismMachines.common.registries;

import com.CompactMekanismMachines.common.CompactMekanismMachines;
import com.CompactMekanismMachines.common.tile.TileEntityCompactFissionReactor;
import com.CompactMekanismMachines.common.tile.TileEntityCompactIndustrialTurbine;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.registration.impl.ContainerTypeDeferredRegister;
import mekanism.common.registration.impl.ContainerTypeRegistryObject;

public class CompactContainerTypes {
    private CompactContainerTypes(){

    }

    public static final ContainerTypeDeferredRegister CONTAINER_TYPES = new ContainerTypeDeferredRegister(CompactMekanismMachines.MOD_ID);

    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityCompactFissionReactor>> COMPACT_FISSION_REACTOR = CONTAINER_TYPES.register(CompactBlocks.COMPACT_FISSION_REACTOR, TileEntityCompactFissionReactor.class);
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityCompactIndustrialTurbine>> COMPACT_INDUSTRIAL_TURBINE = CONTAINER_TYPES.register(CompactBlocks.COMPACT_INDUSTRIAL_TURBINE, TileEntityCompactIndustrialTurbine.class);
}
