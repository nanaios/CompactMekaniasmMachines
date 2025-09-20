package com.CompactMekanismMachines.common.tile.CompressedWindGenerator;

import com.CompactMekanismMachines.common.registries.CompactBlocks;
import com.CompactMekanismMachines.common.tile.TileEntityCompressedWindGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityCompressedWindGenerator_x2 extends TileEntityCompressedWindGenerator<TileEntityCompressedWindGenerator_x2> {

    public TileEntityCompressedWindGenerator_x2(BlockPos pos, BlockState state) {
        super(CompactBlocks.WIND_GENERATOR_X2, pos, state, 2L);
    }

    @Override
    public long getProductionRate() {
        // 2倍風力発電機なので、2Lを返す（必要に応じて調整）
        return 2L;
    }
}