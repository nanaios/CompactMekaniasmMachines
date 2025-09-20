package com.CompactMekanismMachines.common.registries;

import com.CompactMekanismMachines.common.config.CompactMekanismMachinesConfig;
import com.CompactMekanismMachines.common.tile.TileEntityCompactFissionReactor;
import com.CompactMekanismMachines.common.tile.TileEntityCompactIndustrialTurbine;
import mekanism.api.Upgrade;
import mekanism.common.block.attribute.Attributes;
import mekanism.common.content.blocktype.Machine;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.generators.common.GeneratorsLang;
import mekanism.generators.common.content.blocktype.Generator;
import mekanism.generators.common.registries.GeneratorsSounds;

public class CompactBlockTypes {
    private CompactBlockTypes(){}

    public static final Machine<TileEntityCompactFissionReactor> COMPACT_FISSION_REACTOR = Machine.MachineBuilder
            .createMachine(() -> CompactTileEntityTypes.COMPACT_FISSION_REACTOR, GeneratorsLang.DESCRIPTION_GAS_BURNING_GENERATOR)
            .withSideConfig(TransmissionType.FLUID,TransmissionType.CHEMICAL)
            .withGui(() -> CompactContainerTypes.COMPACT_FISSION_REACTOR)
            .withEnergyConfig(CompactMekanismMachinesConfig.storage.cfrEnergyCapacity::get)
            .withSound(GeneratorsSounds.FISSION_REACTOR)
            .withSupportedUpgrades(Upgrade.MUFFLING)
            .replace(Attributes.ACTIVE_MELT_LIGHT)
            .build();

    public static final Generator<TileEntityCompactIndustrialTurbine> COMPACT_INDUSTRIAL_TURBINE = Generator.GeneratorBuilder
            .createGenerator(() -> CompactTileEntityTypes.COMPACT_INDUSTRIAL_TURBINE, GeneratorsLang.DESCRIPTION_GAS_BURNING_GENERATOR)
            .withSideConfig(TransmissionType.FLUID,TransmissionType.ENERGY)
            .withGui(() -> CompactContainerTypes.COMPACT_INDUSTRIAL_TURBINE)
            .withEnergyConfig(CompactMekanismMachinesConfig.storage.turbineEnergyCapacity::get)
            .withSound(GeneratorsSounds.FISSION_REACTOR)
            .withSupportedUpgrades(Upgrade.MUFFLING)
            .replace(Attributes.ACTIVE_MELT_LIGHT)
            .build();
}
