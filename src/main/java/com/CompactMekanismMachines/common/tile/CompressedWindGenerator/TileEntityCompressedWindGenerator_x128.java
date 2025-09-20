package com.CompactMekanismMachines.common.tile.CompressedWindGenerator;

import com.CompactMekanismMachines.common.registries.CompactBlocks;
import com.CompactMekanismMachines.common.tile.TileEntityCompressedWindGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityCompressedWindGenerator_x128 extends TileEntityCompressedWindGenerator<TileEntityCompressedWindGenerator_x128> {

    public TileEntityCompressedWindGenerator_x128(BlockPos pos, BlockState state) {
        super(CompactBlocks.WIND_GENERATOR_X128, pos, state, 128L);
    }
}