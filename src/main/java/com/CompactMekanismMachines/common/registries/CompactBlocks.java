package com.CompactMekanismMachines.common.registries;

import com.CompactMekanismMachines.common.CompactMekanismMachines;
import com.CompactMekanismMachines.common.tile.CompressedWindGenerator.*;
import com.CompactMekanismMachines.common.tile.TileEntityCompactFissionReactor;
import com.CompactMekanismMachines.common.tile.TileEntityCompactIndustrialTurbine;
import com.CompactMekanismMachines.common.tile.TileEntityCompactThermalEvaporation;
import mekanism.common.attachments.containers.ContainerType;
import mekanism.common.attachments.containers.item.ItemSlotsBuilder;
import mekanism.common.block.prefab.BlockTile.BlockTileModel;
import mekanism.common.content.blocktype.Machine;
import mekanism.common.item.block.ItemBlockTooltip;
import mekanism.common.registration.impl.BlockDeferredRegister;
import mekanism.common.registration.impl.BlockRegistryObject;
import mekanism.common.resource.BlockResourceInfo;
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

        WIND_GENERATOR_X2 = BLOCKS.registerDetails(
                "compressed_wind_generator_x2",
                () -> new BlockTileModel<>(CompactBlockTypes.WIND_GENERATOR_X2, properties -> properties.mapColor(MapColor.METAL))
        ).forItemHolder(holder -> holder.addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder().addEnergy().build()));
        WIND_GENERATOR_X8 = BLOCKS.registerDetails(
                "compressed_wind_generator_x8",
                () -> new BlockTileModel<>(CompactBlockTypes.WIND_GENERATOR_X8, properties -> properties.mapColor(MapColor.METAL))
        ).forItemHolder(holder -> holder.addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder().addEnergy().build()));
        WIND_GENERATOR_X32 = BLOCKS.registerDetails(
                "compressed_wind_generator_x32",
                () -> new BlockTileModel<>(CompactBlockTypes.WIND_GENERATOR_X32, properties -> properties.mapColor(MapColor.METAL))
        ).forItemHolder(holder -> holder.addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder().addEnergy().build()));
        WIND_GENERATOR_X128 = BLOCKS.registerDetails(
                "compressed_wind_generator_x128",
                () -> new BlockTileModel<>(CompactBlockTypes.WIND_GENERATOR_X128, properties -> properties.mapColor(MapColor.METAL))
        ).forItemHolder(holder -> holder.addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder().addEnergy().build()));
        WIND_GENERATOR_X512 = BLOCKS.registerDetails(
                "compressed_wind_generator_x512",
                () -> new BlockTileModel<>(CompactBlockTypes.WIND_GENERATOR_X512, properties -> properties.mapColor(MapColor.METAL))
        ).forItemHolder(holder -> holder.addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder().addEnergy().build()));

    }
}
