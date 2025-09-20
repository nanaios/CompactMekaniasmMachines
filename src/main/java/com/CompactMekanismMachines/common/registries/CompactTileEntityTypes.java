package com.CompactMekanismMachines.common.registries;

import com.CompactMekanismMachines.common.CompactMekanismMachines;
import com.CompactMekanismMachines.common.tile.CompressedWindGenerator.*;
import com.CompactMekanismMachines.common.tile.TileEntityCompactFissionReactor;
import com.CompactMekanismMachines.common.tile.TileEntityCompactIndustrialTurbine;
import com.CompactMekanismMachines.common.tile.TileEntityCompactThermalEvaporation;
import com.CompactMekanismMachines.common.tile.TileEntityCompressedWindGenerator;
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
    public static final TileEntityTypeRegistryObject<TileEntityCompressedWindGenerator_x2> WIND_GENERATOR_X2;
    public static final TileEntityTypeRegistryObject<TileEntityCompressedWindGenerator_x8> WIND_GENERATOR_X8;
    public static final TileEntityTypeRegistryObject<TileEntityCompressedWindGenerator_x32> WIND_GENERATOR_X32;
    public static final TileEntityTypeRegistryObject<TileEntityCompressedWindGenerator_x128> WIND_GENERATOR_X128;
    public static final TileEntityTypeRegistryObject<TileEntityCompressedWindGenerator_x512> WIND_GENERATOR_X512;
    public static final TileEntityTypeRegistryObject<TileEntityCompressedWindGenerator_x2048> WIND_GENERATOR_X2048;
    public static final TileEntityTypeRegistryObject<TileEntityCompressedWindGenerator_x8192> WIND_GENERATOR_X8192;
    public static final TileEntityTypeRegistryObject<TileEntityCompressedWindGenerator_x32768> WIND_GENERATOR_X32768;
    public static final TileEntityTypeRegistryObject<TileEntityCompressedWindGenerator_x131072> WIND_GENERATOR_X131072;
    public static final TileEntityTypeRegistryObject<TileEntityCompressedWindGenerator_x532480> WIND_GENERATOR_X532480;

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

        WIND_GENERATOR_X2 = TILE_ENTITY_TYPES.mekBuilder(CompactBlocks.WIND_GENERATOR_X2, TileEntityCompressedWindGenerator_x2::new)
                .clientTicker(TileEntityMekanism::tickClient)
                .serverTicker(TileEntityMekanism::tickServer)
                .withSimple(Capabilities.CONFIG_CARD)
                .build();
        WIND_GENERATOR_X8 = TILE_ENTITY_TYPES.mekBuilder(CompactBlocks.WIND_GENERATOR_X8, TileEntityCompressedWindGenerator_x8::new)
                .clientTicker(TileEntityMekanism::tickClient)
                .serverTicker(TileEntityMekanism::tickServer)
                .withSimple(Capabilities.CONFIG_CARD)
                .build();
        WIND_GENERATOR_X32 = TILE_ENTITY_TYPES.mekBuilder(CompactBlocks.WIND_GENERATOR_X32, TileEntityCompressedWindGenerator_x32::new)
                .clientTicker(TileEntityMekanism::tickClient)
                .serverTicker(TileEntityMekanism::tickServer)
                .withSimple(Capabilities.CONFIG_CARD)
                .build();
        WIND_GENERATOR_X128 = TILE_ENTITY_TYPES.mekBuilder(CompactBlocks.WIND_GENERATOR_X128, TileEntityCompressedWindGenerator_x128::new)
                .clientTicker(TileEntityMekanism::tickClient)
                .serverTicker(TileEntityMekanism::tickServer)
                .withSimple(Capabilities.CONFIG_CARD)
                .build();
        WIND_GENERATOR_X512 = TILE_ENTITY_TYPES.mekBuilder(CompactBlocks.WIND_GENERATOR_X512, TileEntityCompressedWindGenerator_x512::new)
                .clientTicker(TileEntityMekanism::tickClient)
                .serverTicker(TileEntityMekanism::tickServer)
                .withSimple(Capabilities.CONFIG_CARD)
                .build();
        WIND_GENERATOR_X2048 = TILE_ENTITY_TYPES.mekBuilder(CompactBlocks.WIND_GENERATOR_X2048, TileEntityCompressedWindGenerator_x2048::new)
                .clientTicker(TileEntityMekanism::tickClient)
                .serverTicker(TileEntityMekanism::tickServer)
                .withSimple(Capabilities.CONFIG_CARD)
                .build();
        WIND_GENERATOR_X8192 = TILE_ENTITY_TYPES.mekBuilder(CompactBlocks.WIND_GENERATOR_X8192, TileEntityCompressedWindGenerator_x8192::new)
                .clientTicker(TileEntityMekanism::tickClient)
                .serverTicker(TileEntityMekanism::tickServer)
                .withSimple(Capabilities.CONFIG_CARD)
                .build();
        WIND_GENERATOR_X32768 = TILE_ENTITY_TYPES.mekBuilder(CompactBlocks.WIND_GENERATOR_X32768, TileEntityCompressedWindGenerator_x32768::new)
                .clientTicker(TileEntityMekanism::tickClient)
                .serverTicker(TileEntityMekanism::tickServer)
                .withSimple(Capabilities.CONFIG_CARD)
                .build();
        WIND_GENERATOR_X131072 = TILE_ENTITY_TYPES.mekBuilder(CompactBlocks.WIND_GENERATOR_X131072, TileEntityCompressedWindGenerator_x131072::new)
                .clientTicker(TileEntityMekanism::tickClient)
                .serverTicker(TileEntityMekanism::tickServer)
                .withSimple(Capabilities.CONFIG_CARD)
                .build();
        WIND_GENERATOR_X532480 = TILE_ENTITY_TYPES.mekBuilder(CompactBlocks.WIND_GENERATOR_X532480, TileEntityCompressedWindGenerator_x532480::new)
                .clientTicker(TileEntityMekanism::tickClient)
                .serverTicker(TileEntityMekanism::tickServer)
                .withSimple(Capabilities.CONFIG_CARD)
                .build();
    }
}
