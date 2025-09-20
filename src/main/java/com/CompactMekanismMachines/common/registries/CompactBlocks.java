package com.CompactMekanismMachines.common.registries;

import com.CompactMekanismMachines.common.CompactMekanismMachines;
import com.CompactMekanismMachines.common.tile.TileEntityCompactFissionReactor;
import com.CompactMekanismMachines.common.tile.TileEntityCompactIndustrialTurbine;
import com.CompactMekanismMachines.common.tile.TileEntityCompactThermalEvaporation;
import mekanism.common.block.interfaces.IHasDescription;
import mekanism.common.block.prefab.BlockTile;
import mekanism.common.content.blocktype.Machine;
import mekanism.common.item.block.ItemBlockTooltip;
import mekanism.common.registration.impl.BlockDeferredRegister;
import mekanism.common.registration.impl.BlockRegistryObject;
import mekanism.common.resource.BlockResourceInfo;
import mekanism.generators.common.content.blocktype.Generator;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class CompactBlocks {
    private CompactBlocks(){
    }
    public static final BlockDeferredRegister BLOCKS = new BlockDeferredRegister(CompactMekanismMachines.MOD_ID);

    public static final BlockRegistryObject<
            BlockTile.BlockTileModel<TileEntityCompactFissionReactor, Machine<TileEntityCompactFissionReactor>>,
            ItemBlockTooltip<BlockTile.BlockTileModel<TileEntityCompactFissionReactor, Machine<TileEntityCompactFissionReactor>>>
            > COMPACT_FISSION_REACTOR;

    public static final BlockRegistryObject<
            BlockTile.BlockTileModel<TileEntityCompactIndustrialTurbine, Generator<TileEntityCompactIndustrialTurbine>>,
            ItemBlockTooltip<BlockTile.BlockTileModel<TileEntityCompactIndustrialTurbine, Generator<TileEntityCompactIndustrialTurbine>>>
            > COMPACT_INDUSTRIAL_TURBINE;

    public static final BlockRegistryObject<
            BlockTile.BlockTileModel<TileEntityCompactThermalEvaporation, Machine<TileEntityCompactThermalEvaporation>>,
            ItemBlockTooltip<BlockTile.BlockTileModel<TileEntityCompactThermalEvaporation, Machine<TileEntityCompactThermalEvaporation>>>
            > COMPACT_THERMAL_EVAPORATION;

    static {
        COMPACT_FISSION_REACTOR = registerTooltipBlock(
                "compact_fission_reactor",
                () -> new BlockTile.BlockTileModel<>(CompactBlockTypes.COMPACT_FISSION_REACTOR, properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor()).strength(0.2F))
        );

        COMPACT_INDUSTRIAL_TURBINE = registerTooltipBlock(
                "compact_industrial_turbine",
                () -> new BlockTile.BlockTileModel<>(CompactBlockTypes.COMPACT_INDUSTRIAL_TURBINE, properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor()))
        );
        COMPACT_THERMAL_EVAPORATION = registerTooltipBlock(
                "compact_thermal_evaporation",
                () -> new BlockTile.BlockTileModel<>(CompactBlockTypes.COMPACT_THERMAL_EVAPORATION, properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor()).strength(0.2F))
        );
    }

    private static <BLOCK extends Block & IHasDescription> BlockRegistryObject<BLOCK, ItemBlockTooltip<BLOCK>> registerTooltipBlock(String name, Supplier<BLOCK> blockCreator) {
        return BLOCKS.register(name, blockCreator, ItemBlockTooltip::new);
    }
}
