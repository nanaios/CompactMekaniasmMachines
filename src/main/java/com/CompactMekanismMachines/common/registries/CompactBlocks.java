package com.CompactMekanismMachines.common.registries;

import com.CompactMekanismMachines.common.CompactMekanismMachines;
import com.CompactMekanismMachines.common.tile.TileEntityCompactFissionReactor;
import mekanism.common.block.interfaces.IHasDescription;
import mekanism.common.block.prefab.BlockTile;
import mekanism.common.content.blocktype.Machine;
import mekanism.common.item.block.ItemBlockTooltip;
import mekanism.common.registration.impl.BlockDeferredRegister;
import mekanism.common.registration.impl.BlockRegistryObject;
import mekanism.common.resource.BlockResourceInfo;
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

    static {
        COMPACT_FISSION_REACTOR = registerTooltipBlock(
                "compact_fission_reactor",
                () -> new BlockTile.BlockTileModel<>(CompactBlockTypes.COMPACT_FISSION_REACTOR, properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor()).strength(0.2F))
        );
    }

    private static <BLOCK extends Block & IHasDescription> BlockRegistryObject<BLOCK, ItemBlockTooltip<BLOCK>> registerTooltipBlock(String name, Supplier<BLOCK> blockCreator) {
        return BLOCKS.register(name, blockCreator, ItemBlockTooltip::new);
    }
}
