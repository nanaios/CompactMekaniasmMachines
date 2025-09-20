package com.CompactMekanismMachines.common.registries;

import com.CompactMekanismMachines.common.CompactMekanismMachines;
import com.CompactMekanismMachines.common.tile.CompressedWindGenerator.*;
import com.CompactMekanismMachines.common.tile.TileEntityCompactFissionReactor;
import com.CompactMekanismMachines.common.tile.TileEntityCompactIndustrialTurbine;
import com.CompactMekanismMachines.common.tile.TileEntityCompactThermalEvaporation;
import com.CompactMekanismMachines.common.util.CompressedWindGeneratorLevel;
import mekanism.common.attachments.containers.ContainerType;
import mekanism.common.attachments.containers.item.ItemSlotsBuilder;
import mekanism.common.block.prefab.BlockTile.BlockTileModel;
import mekanism.common.content.blocktype.BlockTypeTile;
import mekanism.common.content.blocktype.Machine;
import mekanism.common.item.block.ItemBlockTooltip;
import mekanism.common.registration.impl.BlockDeferredRegister;
import mekanism.common.registration.impl.BlockRegistryObject;
import mekanism.common.resource.BlockResourceInfo;
import mekanism.common.tile.base.TileEntityMekanism;
import mekanism.generators.common.content.blocktype.Generator;
import net.minecraft.world.level.material.MapColor;

public class CompactBlocks {
    private CompactBlocks(){
    }
    public static final BlockDeferredRegister BLOCKS = new BlockDeferredRegister(CompactMekanismMachines.MOD_ID);

    public static final BlockRegistryObject<
            BlockTileModel<TileEntityCompactFissionReactor, Machine<TileEntityCompactFissionReactor>>,
            ItemBlockTooltip<BlockTileModel<TileEntityCompactFissionReactor, Machine<TileEntityCompactFissionReactor>>>
            > COMPACT_FISSION_REACTOR;

    public static final BlockRegistryObject<
            BlockTileModel<TileEntityCompactIndustrialTurbine, Generator<TileEntityCompactIndustrialTurbine>>,
            ItemBlockTooltip<BlockTileModel<TileEntityCompactIndustrialTurbine, Generator<TileEntityCompactIndustrialTurbine>>>
            > COMPACT_INDUSTRIAL_TURBINE;

    public static final BlockRegistryObject<
            BlockTileModel<TileEntityCompactThermalEvaporation, Machine<TileEntityCompactThermalEvaporation>>,
            ItemBlockTooltip<BlockTileModel<TileEntityCompactThermalEvaporation, Machine<TileEntityCompactThermalEvaporation>>>
            > COMPACT_THERMAL_EVAPORATION;

    public static final BlockRegistryObject<
            BlockTileModel<TileEntityCompressedWindGenerator_x2, Generator<TileEntityCompressedWindGenerator_x2>>,
            ItemBlockTooltip<BlockTileModel<TileEntityCompressedWindGenerator_x2, Generator<TileEntityCompressedWindGenerator_x2>>>
            > WIND_GENERATOR_X2;
    public static final BlockRegistryObject<
            BlockTileModel<TileEntityCompressedWindGenerator_x8, Generator<TileEntityCompressedWindGenerator_x8>>,
            ItemBlockTooltip<BlockTileModel<TileEntityCompressedWindGenerator_x8, Generator<TileEntityCompressedWindGenerator_x8>>>
            > WIND_GENERATOR_X8;
    public static final BlockRegistryObject<
            BlockTileModel<TileEntityCompressedWindGenerator_x32, Generator<TileEntityCompressedWindGenerator_x32>>,
            ItemBlockTooltip<BlockTileModel<TileEntityCompressedWindGenerator_x32, Generator<TileEntityCompressedWindGenerator_x32>>>
            > WIND_GENERATOR_X32;
    public static final BlockRegistryObject<
            BlockTileModel<TileEntityCompressedWindGenerator_x128, Generator<TileEntityCompressedWindGenerator_x128>>,
            ItemBlockTooltip<BlockTileModel<TileEntityCompressedWindGenerator_x128, Generator<TileEntityCompressedWindGenerator_x128>>>
            > WIND_GENERATOR_X128;
    public static final BlockRegistryObject<
            BlockTileModel<TileEntityCompressedWindGenerator_x512, Generator<TileEntityCompressedWindGenerator_x512>>,
            ItemBlockTooltip<BlockTileModel<TileEntityCompressedWindGenerator_x512, Generator<TileEntityCompressedWindGenerator_x512>>>
            > WIND_GENERATOR_X512;
    public static final BlockRegistryObject<
            BlockTileModel<TileEntityCompressedWindGenerator_x2048, Generator<TileEntityCompressedWindGenerator_x2048>>,
            ItemBlockTooltip<BlockTileModel<TileEntityCompressedWindGenerator_x2048, Generator<TileEntityCompressedWindGenerator_x2048>>>
            > WIND_GENERATOR_X2048;
    public static final BlockRegistryObject<
            BlockTileModel<TileEntityCompressedWindGenerator_x8192, Generator<TileEntityCompressedWindGenerator_x8192>>,
            ItemBlockTooltip<BlockTileModel<TileEntityCompressedWindGenerator_x8192, Generator<TileEntityCompressedWindGenerator_x8192>>>
            > WIND_GENERATOR_X8192;
    public static final BlockRegistryObject<
            BlockTileModel<TileEntityCompressedWindGenerator_x32768, Generator<TileEntityCompressedWindGenerator_x32768>>,
            ItemBlockTooltip<BlockTileModel<TileEntityCompressedWindGenerator_x32768, Generator<TileEntityCompressedWindGenerator_x32768>>>
            > WIND_GENERATOR_X32768;
    public static final BlockRegistryObject<
            BlockTileModel<TileEntityCompressedWindGenerator_x131072, Generator<TileEntityCompressedWindGenerator_x131072>>,
            ItemBlockTooltip<BlockTileModel<TileEntityCompressedWindGenerator_x131072, Generator<TileEntityCompressedWindGenerator_x131072>>>
            > WIND_GENERATOR_X131072;
    public static final BlockRegistryObject<
            BlockTileModel<TileEntityCompressedWindGenerator_x532480, Generator<TileEntityCompressedWindGenerator_x532480>>,
            ItemBlockTooltip<BlockTileModel<TileEntityCompressedWindGenerator_x532480, Generator<TileEntityCompressedWindGenerator_x532480>>>
            > WIND_GENERATOR_X532480;


    static {
        COMPACT_FISSION_REACTOR = BLOCKS.registerDetails(
                "compact_fission_reactor",
                () -> new BlockTileModel<>(CompactBlockTypes.COMPACT_FISSION_REACTOR, properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor()).strength(0.2F))
        );

        COMPACT_INDUSTRIAL_TURBINE = BLOCKS.registerDetails(
                "compact_industrial_turbine",
                () -> new BlockTileModel<>(CompactBlockTypes.COMPACT_INDUSTRIAL_TURBINE, properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor()))
        );
        COMPACT_THERMAL_EVAPORATION = BLOCKS.registerDetails(
                "compact_thermal_evaporation",
                () -> new BlockTileModel<>(CompactBlockTypes.COMPACT_THERMAL_EVAPORATION, properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor()).strength(0.2F))
        );

        WIND_GENERATOR_X2 = createWindGenerator(CompressedWindGeneratorLevel.X2, CompactBlockTypes.WIND_GENERATOR_X2);
        WIND_GENERATOR_X8 = createWindGenerator(CompressedWindGeneratorLevel.X8, CompactBlockTypes.WIND_GENERATOR_X8);
        WIND_GENERATOR_X32 = createWindGenerator(CompressedWindGeneratorLevel.X32, CompactBlockTypes.WIND_GENERATOR_X32);
        WIND_GENERATOR_X128 = createWindGenerator(CompressedWindGeneratorLevel.X128, CompactBlockTypes.WIND_GENERATOR_X128);
        WIND_GENERATOR_X512 = createWindGenerator(CompressedWindGeneratorLevel.X512, CompactBlockTypes.WIND_GENERATOR_X512);
        WIND_GENERATOR_X2048 = createWindGenerator(CompressedWindGeneratorLevel.X2048, CompactBlockTypes.WIND_GENERATOR_X2048);
        WIND_GENERATOR_X8192 = createWindGenerator(CompressedWindGeneratorLevel.X8192, CompactBlockTypes.WIND_GENERATOR_X8192);
        WIND_GENERATOR_X32768 = createWindGenerator(CompressedWindGeneratorLevel.X32768, CompactBlockTypes.WIND_GENERATOR_X32768);
        WIND_GENERATOR_X131072 = createWindGenerator(CompressedWindGeneratorLevel.X131072, CompactBlockTypes.WIND_GENERATOR_X131072);
        WIND_GENERATOR_X532480 = createWindGenerator(CompressedWindGeneratorLevel.X532480, CompactBlockTypes.WIND_GENERATOR_X532480);

    }

    public static  <TILE extends TileEntityMekanism, BLOCK extends BlockTypeTile<TILE>> BlockRegistryObject<BlockTileModel<TILE, BLOCK>, ItemBlockTooltip<BlockTileModel<TILE, BLOCK>>> createWindGenerator(CompressedWindGeneratorLevel level, BLOCK block){
        return BLOCKS.registerDetails(
                "compressed_wind_generator_x"+level.getMultiplier(),
                () -> new BlockTileModel<>(block, properties -> properties.mapColor(MapColor.METAL))
        ).forItemHolder(holder -> holder.addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder().addEnergy().build()));

    }
}
