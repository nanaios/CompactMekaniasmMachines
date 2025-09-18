package com.CompactMekanismMachines.common.tile;

import mekanism.api.IContentsListener;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.functions.ConstantPredicates;
import mekanism.common.capabilities.chemical.VariableCapacityChemicalTank;
import mekanism.common.capabilities.holder.chemical.ChemicalTankHelper;
import mekanism.common.capabilities.holder.chemical.IChemicalTankHolder;
import mekanism.common.registries.MekanismChemicals;
import mekanism.common.tile.prefab.TileEntityConfigurableMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class TileEntityCompactFissionReactor extends TileEntityConfigurableMachine {
    public IChemicalTank fuelTank;

    public TileEntityCompactFissionReactor(Holder<Block> blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state);
    }

    @Override
    public @Nullable IChemicalTankHolder getInitialChemicalTanks(IContentsListener listener) {
        ChemicalTankHelper builder = ChemicalTankHelper.forSideWithConfig(this);

        builder.addTank(fuelTank = VariableCapacityChemicalTank.create(
                () -> 10000L,
                ConstantPredicates.notExternal(),
                ConstantPredicates.alwaysTrueBi(),
                gas -> gas.is(MekanismChemicals.FISSILE_FUEL),
                null,
                listener
        ));

        return builder.build();
    }

    @Override
    protected boolean onUpdateServer() {
        boolean needPacket = super.onUpdateServer();


        return needPacket;
    }
}
