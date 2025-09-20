package com.CompactMekanismMachines.common.tile;

import com.CompactMekanismMachines.common.capabilities.CompactTurbineChemicalTank;
import com.CompactMekanismMachines.common.config.CompactMekanismMachinesConfig;
import com.CompactMekanismMachines.common.registries.CompactBlocks;
import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.IContentsListener;
import mekanism.api.RelativeSide;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.energy.IEnergyContainer;
import mekanism.api.fluid.IExtendedFluidTank;
import mekanism.api.math.MathUtils;
import mekanism.common.capabilities.energy.BasicEnergyContainer;
import mekanism.common.capabilities.fluid.VariableCapacityFluidTank;
import mekanism.common.capabilities.holder.chemical.ChemicalTankHelper;
import mekanism.common.capabilities.holder.chemical.IChemicalTankHolder;
import mekanism.common.capabilities.holder.energy.EnergyContainerHelper;
import mekanism.common.capabilities.holder.energy.IEnergyContainerHolder;
import mekanism.common.capabilities.holder.fluid.FluidTankHelper;
import mekanism.common.capabilities.holder.fluid.IFluidTankHolder;
import mekanism.common.config.MekanismConfig;
import mekanism.common.inventory.container.sync.dynamic.ContainerSync;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.tile.TileEntityChemicalTank.GasMode;
import mekanism.common.tile.component.TileComponentEjector;
import mekanism.common.tile.component.config.ConfigInfo;
import mekanism.common.tile.component.config.DataType;
import mekanism.common.tile.component.config.slot.ChemicalSlotInfo;
import mekanism.common.tile.component.config.slot.EnergySlotInfo;
import mekanism.common.tile.component.config.slot.FluidSlotInfo;
import mekanism.common.tile.prefab.TileEntityConfigurableMachine;
import mekanism.generators.common.config.MekanismGeneratorsConfig;
import mekanism.generators.common.content.turbine.TurbineValidator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

public class TileEntityCompactIndustrialTurbine extends TileEntityConfigurableMachine {

    @ContainerSync
    public IChemicalTank chemicalTank;
    @ContainerSync
    public IExtendedFluidTank ventTank;
    @ContainerSync
    public IEnergyContainer energyContainer;

    public GasMode dumpMode = GasMode.IDLE;

    @ContainerSync
    public long lastSteamInput;
    public long newSteamInput;
    @ContainerSync
    public long clientFlow;

    public TileEntityCompactIndustrialTurbine(BlockPos pos, BlockState state) {
        super(CompactBlocks.COMPACT_INDUSTRIAL_TURBINE, pos, state);

        ConfigInfo chemicalConfig = configComponent.getConfig(TransmissionType.CHEMICAL);
        if (chemicalConfig !=null){
            chemicalConfig.addSlotInfo(DataType.INPUT, new ChemicalSlotInfo(true,false, chemicalTank));
            chemicalConfig.setDataType(DataType.INPUT, RelativeSide.FRONT);
        }
        ConfigInfo fluidConfig = configComponent.getConfig(TransmissionType.FLUID);
        if (fluidConfig!=null){
            fluidConfig.addSlotInfo(DataType.OUTPUT,new FluidSlotInfo(false,true,ventTank));
            fluidConfig.setDataType(DataType.OUTPUT,RelativeSide.TOP);
        }
        ConfigInfo energyConfig = configComponent.getConfig(TransmissionType.ENERGY);
        if (energyConfig!=null){
            energyConfig.addSlotInfo(DataType.OUTPUT,new EnergySlotInfo(false,true,energyContainer));
            energyConfig.setDataType(DataType.OUTPUT,RelativeSide.BOTTOM);
        }

        ejectorComponent = new TileComponentEjector(this, ()->Long.MAX_VALUE,()->Integer.MAX_VALUE,()-> Long.MAX_VALUE);
        ejectorComponent.setOutputData(configComponent, TransmissionType.CHEMICAL,TransmissionType.FLUID,TransmissionType.ENERGY);
    }

    @Override
    public @Nullable IChemicalTankHolder getInitialChemicalTanks(IContentsListener listener) {
        ChemicalTankHelper builder = ChemicalTankHelper.forSideWithConfig(this);

        builder.addTank(chemicalTank = new CompactTurbineChemicalTank(this, listener));

        return  builder.build();
    }

    @Override
    protected @Nullable IFluidTankHolder getInitialFluidTanks(IContentsListener listener) {
        FluidTankHelper builder = FluidTankHelper.forSideWithConfig(this);

        builder.addTank(ventTank = VariableCapacityFluidTank.output(
                CompactMekanismMachinesConfig.storage.turbineFluidCapacity::get,
                fluid -> fluid.is(FluidTags.WATER),
                listener
        ));

        return builder.build();
    }

    @Override
    protected @Nullable IEnergyContainerHolder getInitialEnergyContainers(IContentsListener listener) {
        EnergyContainerHelper builder = EnergyContainerHelper.forSideWithConfig(this);

        builder.addContainer(energyContainer = BasicEnergyContainer.output(
                CompactMekanismMachinesConfig.storage.turbineEnergyCapacity.get(),
                listener
        ));

        return builder.build();
    }

    @Override
    protected boolean onUpdateServer() {
        boolean needsPacket = super.onUpdateServer();

        if(chemicalTank.isEmpty() || !canFunction()) {
            setActive(false);
            return needsPacket;
        }

        setActive(true);

        lastSteamInput = newSteamInput;
        newSteamInput = 0;
        long stored = chemicalTank.getStored();


        long energyNeeded = energyContainer.getNeeded();
        if (stored > 0 && energyNeeded > 0L) {
            double energyMultiplier = getEnergyMultiplier();
            if (energyMultiplier < Mth.EPSILON) {
                clientFlow = 0;
            } else {
                double rate = getRate();
                double proportion = stored / (double) getSteamCapacity();
                rate = Math.min(Math.min(stored, rate), (energyNeeded / energyMultiplier)) * proportion;
                clientFlow = MathUtils.clampToLong(rate);
                if (clientFlow > 0) {
                    energyContainer.insert(MathUtils.clampToLong(energyMultiplier * rate), Action.EXECUTE, AutomationType.INTERNAL);
                    chemicalTank.shrinkStack(clientFlow, Action.EXECUTE);
                    ventTank.insert(new FluidStack(Fluids.WATER, Math.min(MathUtils.clampToInt(rate), CompactMekanismMachinesConfig.storage.turbineVirtualCondenses.get() * MekanismGeneratorsConfig.generators.condenserRate.get())), Action.EXECUTE, AutomationType.INTERNAL);
                }
            }
        } else {
            clientFlow = 0;
        }

        if (dumpMode != GasMode.IDLE && !chemicalTank.isEmpty()) {
            long amount = chemicalTank.getStored();
            if (dumpMode == GasMode.DUMPING) {
                chemicalTank.shrinkStack(getDumpingAmount(amount), Action.EXECUTE);
            } else {//DUMPING_EXCESS
                //Don't allow dumping more than the configured amount
                long targetLevel = MathUtils.clampToLong(chemicalTank.getCapacity() * MekanismConfig.general.dumpExcessKeepRatio.get());
                if (targetLevel < amount) {
                    chemicalTank.shrinkStack(Math.min(amount - targetLevel, getDumpingAmount(amount)), Action.EXECUTE);
                }
            }
        }

        return needsPacket;
    }

    public long getSteamCapacity() {
        return CompactMekanismMachinesConfig.storage.turbineVirtualLowerVolume.get() * MekanismGeneratorsConfig.generators.turbineChemicalPerTank.get();
    }

    public void setDumpMode(GasMode mode) {
        if (dumpMode != mode) {
            dumpMode = mode;
        }
    }

    void incrementDumpingMode() {
        setDumpMode(dumpMode.getNext());
    }

    void decrementDumpingMode() {
        setDumpMode(dumpMode.getPrevious());
    }

    private long getDumpingAmount(long stored) {
        return Math.min(stored, Math.max(stored / 50, lastSteamInput * 2));
    }

    public static double getRate() {
        double rate = CompactMekanismMachinesConfig.storage.turbineVirtualLowerVolume.get() * (CompactMekanismMachinesConfig.storage.turbineVirtualDispersers.get() * MekanismGeneratorsConfig.generators.turbineDisperserChemicalFlow.get());
        return Math.min(rate, CompactMekanismMachinesConfig.storage.turbineVirtualVents.get() * MekanismGeneratorsConfig.generators.turbineVentChemicalFlow.get());
    }

    public long getMaxFlowRate() {
        return MathUtils.clampToLong(getRate());
    }

    public double getMaxProduction() {
        long energyMultiplier = MekanismConfig.general.maxEnergyPerSteam.get() / TurbineValidator.MAX_BLADES * CompactMekanismMachinesConfig.storage.turbineVirtualBlades.get();
        return energyMultiplier * getRate();
    }

    public long getProductionRate() {
        long energyMultiplier = MekanismConfig.general.maxEnergyPerSteam.get() / TurbineValidator.MAX_BLADES *
                CompactMekanismMachinesConfig.storage.turbineVirtualBlades.get();
        return energyMultiplier * clientFlow;
    }

    public IEnergyContainer getEnergyContainer() {
        return energyContainer;
    }

    public static double getEnergyMultiplier() {
        return CompactMekanismMachinesConfig.storage.turbineEnergyMultiply.get();
    }
}