package com.CompactMekanismMachines.common.capabilities;

import com.CompactMekanismMachines.common.config.CompactMekanismMachinesConfig;
import com.CompactMekanismMachines.common.tile.TileEntityCompactIndustrialTurbine;
import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.IContentsListener;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.functions.ConstantPredicates;
import mekanism.common.capabilities.chemical.VariableCapacityChemicalTank;
import mekanism.common.registries.MekanismChemicals;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CompactTurbineChemicalTank extends VariableCapacityChemicalTank {

    TileEntityCompactIndustrialTurbine tile;

    public CompactTurbineChemicalTank(TileEntityCompactIndustrialTurbine tile, @Nullable IContentsListener listener) {
        super(
                CompactMekanismMachinesConfig.storage.turbineChemicalCapacity,
                ConstantPredicates.notExternal(),
                ConstantPredicates.alwaysTrueBi(),
                gas -> gas.is(MekanismChemicals.STEAM),
                null,
                listener
        );
        this.tile = tile;
    }

    @Override
    public @NotNull ChemicalStack insert(@NotNull ChemicalStack stack, @NotNull Action action, @NotNull AutomationType automationType) {
        ChemicalStack returned = super.insert(stack, action, automationType);
        if (action == Action.EXECUTE) {
            tile.newSteamInput += stack.getAmount() - returned.getAmount();
        }
        return returned;
    }
}
