package com.CompactMekanismMachines.common.registries;

import com.CompactMekanismMachines.common.CompactLang;
import com.CompactMekanismMachines.common.config.CompactMekanismMachinesConfig;
import com.CompactMekanismMachines.common.tile.CompressedWindGenerator.*;
import com.CompactMekanismMachines.common.tile.TileEntityCompactFissionReactor;
import com.CompactMekanismMachines.common.tile.TileEntityCompactIndustrialTurbine;
import com.CompactMekanismMachines.common.tile.TileEntityCompactThermalEvaporation;
import com.CompactMekanismMachines.common.util.CompressedWindGeneratorLevel;
import mekanism.api.Upgrade;
import mekanism.api.text.ILangEntry;
import mekanism.common.MekanismLang;
import mekanism.common.block.attribute.AttributeCustomSelectionBox;
import mekanism.common.block.attribute.AttributeHasBounding.HandleBoundingBlock;
import mekanism.common.block.attribute.AttributeHasBounding.TriBooleanFunction;
import mekanism.common.block.attribute.AttributeUpgradeSupport;
import mekanism.common.block.attribute.Attributes;
import mekanism.common.content.blocktype.Machine;
import mekanism.common.inventory.container.MekanismContainer;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.registration.impl.ContainerTypeRegistryObject;
import mekanism.common.registration.impl.TileEntityTypeRegistryObject;
import mekanism.generators.common.GeneratorsLang;
import mekanism.generators.common.config.MekanismGeneratorsConfig;
import mekanism.generators.common.content.blocktype.BlockShapes;
import mekanism.generators.common.content.blocktype.Generator;
import mekanism.generators.common.registries.GeneratorsSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

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

    public static final Machine<TileEntityCompactThermalEvaporation> COMPACT_THERMAL_EVAPORATION = Machine.MachineBuilder
            .createMachine(() -> CompactTileEntityTypes.COMPACT_THERMAL_EVAPORATION, MekanismLang.DESCRIPTION_THERMAL_EVAPORATION_CONTROLLER)
            .withSideConfig(TransmissionType.FLUID,TransmissionType.ENERGY)
            .withGui(() -> CompactContainerTypes.COMPACT_THERMAL_EVAPORATION)
            .withSupportedUpgrades(Upgrade.MUFFLING)
            .build();

    public static final Generator<TileEntityCompressedWindGenerator_x2> WIND_GENERATOR_X2 = createWindGenerator(
            CompactTileEntityTypes.WIND_GENERATOR_X2, GeneratorsLang.DESCRIPTION_WIND_GENERATOR,
            CompactContainerTypes.WIND_GENERATOR_X2,CompressedWindGeneratorLevel.X2);

    public static final Generator<TileEntityCompressedWindGenerator_x8> WIND_GENERATOR_X8 = createWindGenerator(
            CompactTileEntityTypes.WIND_GENERATOR_X8, GeneratorsLang.DESCRIPTION_WIND_GENERATOR,
            CompactContainerTypes.WIND_GENERATOR_X8,CompressedWindGeneratorLevel.X8);

    public static final Generator<TileEntityCompressedWindGenerator_x32> WIND_GENERATOR_X32 = createWindGenerator(
            CompactTileEntityTypes.WIND_GENERATOR_X32, GeneratorsLang.DESCRIPTION_WIND_GENERATOR,
            CompactContainerTypes.WIND_GENERATOR_X32,CompressedWindGeneratorLevel.X32);

    public static final Generator<TileEntityCompressedWindGenerator_x128> WIND_GENERATOR_X128 = createWindGenerator(
            CompactTileEntityTypes.WIND_GENERATOR_X128, GeneratorsLang.DESCRIPTION_WIND_GENERATOR,
            CompactContainerTypes.WIND_GENERATOR_X128,CompressedWindGeneratorLevel.X128);

    public static final Generator<TileEntityCompressedWindGenerator_x512> WIND_GENERATOR_X512 = createWindGenerator(
            CompactTileEntityTypes.WIND_GENERATOR_X512, GeneratorsLang.DESCRIPTION_WIND_GENERATOR,
            CompactContainerTypes.WIND_GENERATOR_X512,CompressedWindGeneratorLevel.X512);

    public static final Generator<TileEntityCompressedWindGenerator_x2048> WIND_GENERATOR_X2048 = createWindGenerator(
            CompactTileEntityTypes.WIND_GENERATOR_X2048, GeneratorsLang.DESCRIPTION_WIND_GENERATOR,
            CompactContainerTypes.WIND_GENERATOR_X2048,CompressedWindGeneratorLevel.X2048);

    public static final Generator<TileEntityCompressedWindGenerator_x8192> WIND_GENERATOR_X8192 = createWindGenerator(
            CompactTileEntityTypes.WIND_GENERATOR_X8192, GeneratorsLang.DESCRIPTION_WIND_GENERATOR,
            CompactContainerTypes.WIND_GENERATOR_X8192,CompressedWindGeneratorLevel.X8192);

    public static final Generator<TileEntityCompressedWindGenerator_x32768> WIND_GENERATOR_X32768 = createWindGenerator(
            CompactTileEntityTypes.WIND_GENERATOR_X32768, GeneratorsLang.DESCRIPTION_WIND_GENERATOR,
            CompactContainerTypes.WIND_GENERATOR_X32768,CompressedWindGeneratorLevel.X32768);

    public static final Generator<TileEntityCompressedWindGenerator_x131072> WIND_GENERATOR_X131072 = createWindGenerator(
            CompactTileEntityTypes.WIND_GENERATOR_X131072, CompactLang.DESCRIPTION_COMPRESSED_WIND_GENERATOR_x131072,
            CompactContainerTypes.WIND_GENERATOR_X131072,CompressedWindGeneratorLevel.X131072);

    public static final Generator<TileEntityCompressedWindGenerator_x532480> WIND_GENERATOR_X532480 = createWindGenerator(
            CompactTileEntityTypes.WIND_GENERATOR_X532480, CompactLang.DESCRIPTION_COMPRESSED_WIND_GENERATOR_x532480,
            CompactContainerTypes.WIND_GENERATOR_X532480,CompressedWindGeneratorLevel.X532480);


    public static <
            TILE extends mekanism.common.tile.base.TileEntityMekanism
            > Generator<TILE> createWindGenerator(
                    TileEntityTypeRegistryObject<TILE> tileEntityRegistrar,
                    ILangEntry description,
                    ContainerTypeRegistryObject<? extends MekanismContainer> containerRegistrar,
                    CompressedWindGeneratorLevel level
    ) {
        return Generator.GeneratorBuilder.createGenerator(() ->tileEntityRegistrar, description)
                .withGui(() -> containerRegistrar)
                .withEnergyConfig(() -> MekanismGeneratorsConfig.storageConfig.windGenerator.get() * level.getMultiplier())
                .withCustomShape(BlockShapes.WIND_GENERATOR)
                .with(AttributeCustomSelectionBox.JAVA)
                .withSound(GeneratorsSounds.WIND_GENERATOR)
                .with(AttributeUpgradeSupport.MUFFLING_ONLY)
                .withBounding(new HandleBoundingBlock() {
                    @Override
                    public <DATA> boolean handle(Level level, BlockPos pos, BlockState state, DATA data, TriBooleanFunction<Level, BlockPos, DATA> consumer) {
                        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
                        for (int i = 0; i < 4; i++) {
                            mutable.setWithOffset(pos, 0, i + 1, 0);
                            if (!consumer.accept(level, mutable, data)) {
                                return false;
                            }
                        }
                        return true;
                    }
                })
                .build();
    }
}
