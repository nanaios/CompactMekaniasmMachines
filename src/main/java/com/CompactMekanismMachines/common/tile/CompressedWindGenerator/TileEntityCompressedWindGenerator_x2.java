package com.CompactMekanismMachines.common.tile.CompressedWindGenerator;

import com.CompactMekanismMachines.common.registries.CompactBlocks;
import com.CompactMekanismMachines.common.tile.TileEntityCompressedWindGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityCompressedWindGenerator_x2 extends TileEntityCompressedWindGenerator<TileEntityCompressedWindGenerator_x2> {

    public TileEntityCompressedWindGenerator_x2(BlockPos pos, BlockState state) {
        super(CompactBlocks.WIND_GENERATOR_X2, pos, state, 2L);
    }
}