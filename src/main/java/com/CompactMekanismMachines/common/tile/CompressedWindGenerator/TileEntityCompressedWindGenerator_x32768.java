package com.CompactMekanismMachines.common.tile.CompressedWindGenerator;

import com.CompactMekanismMachines.common.registries.CompactBlocks;
import com.CompactMekanismMachines.common.tile.TileEntityCompressedWindGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityCompressedWindGenerator_x32768 extends TileEntityCompressedWindGenerator<TileEntityCompressedWindGenerator_x32768> {

    public TileEntityCompressedWindGenerator_x32768(BlockPos pos, BlockState state) {
        super(CompactBlocks.WIND_GENERATOR_X32768, pos, state, 32768L);
    }
}