package com.CompactMekanismMachines.common.registries;

import com.CompactMekanismMachines.common.CompactMekanismMachines;
import com.CompactMekanismMachines.common.tile.TileEntityCompactFissionReactor;
import com.CompactMekanismMachines.common.tile.TileEntityCompactIndustrialTurbine;
import com.CompactMekanismMachines.common.tile.TileEntityCompactThermalEvaporation;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.registration.impl.TileEntityTypeDeferredRegister;
import mekanism.common.registration.impl.TileEntityTypeRegistryObject;
import mekanism.common.tile.base.TileEntityMekanism;

public class CompactTileEntityTypes {
    private CompactTileEntityTypes(){

    }
    public static final TileEntityTypeDeferredRegister TILE_ENTITY_TYPES = new TileEntityTypeDeferredRegister(CompactMekanismMachines.MOD_ID);

    public static final TileEntityTypeRegistryObject<TileEntityCompactFissionReactor> COMPACT_FISSION_REACTOR;
    public static final TileEntityTypeRegistryObject<TileEntityCompactIndustrialTurbine> COMPACT_INDUSTRIAL_TURBINE;
    public static final TileEntityTypeRegistryObject<TileEntityCompactThermalEvaporation> COMPACT_THERMAL_EVAPORATION;

    static {
        COMPACT_FISSION_REACTOR = TILE_ENTITY_TYPES.mekBuilder(CompactBlocks.COMPACT_FISSION_REACTOR,TileEntityCompactFissionReactor::new)
                .clientTicker(TileEntityMekanism::tickClient)
                .serverTicker(TileEntityMekanism::tickServer)
                .withSimple(Capabilities.CONFIG_CARD)
                .build();
        COMPACT_INDUSTRIAL_TURBINE = TILE_ENTITY_TYPES.mekBuilder(CompactBlocks.COMPACT_INDUSTRIAL_TURBINE,TileEntityCompactIndustrialTurbine::new)
                .clientTicker(TileEntityMekanism::tickClient)
                .serverTicker(TileEntityMekanism::tickServer)
                .withSimple(Capabilities.CONFIG_CARD)
                .build();
        COMPACT_THERMAL_EVAPORATION = TILE_ENTITY_TYPES.mekBuilder(CompactBlocks.COMPACT_THERMAL_EVAPORATION, TileEntityCompactThermalEvaporation::new)
                .clientTicker(TileEntityMekanism::tickClient)
                .serverTicker(TileEntityMekanism::tickServer)
                .withSimple(Capabilities.CONFIG_CARD)
                .build();
    }
}
