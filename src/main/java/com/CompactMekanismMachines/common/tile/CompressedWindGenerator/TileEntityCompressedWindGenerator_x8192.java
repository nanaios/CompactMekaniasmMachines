package com.CompactMekanismMachines.common.tile.CompressedWindGenerator;

import com.CompactMekanismMachines.common.registries.CompactBlocks;
import com.CompactMekanismMachines.common.tile.TileEntityCompressedWindGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityCompressedWindGenerator_x8192 extends TileEntityCompressedWindGenerator<TileEntityCompressedWindGenerator_x8192> {

    public TileEntityCompressedWindGenerator_x8192(BlockPos pos, BlockState state) {
        super(CompactBlocks.WIND_GENERATOR_X8192, pos, state, 8192L);
    }
}