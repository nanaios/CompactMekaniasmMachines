package com.CompactMekanismMachines.common.tile;

import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.IContentsListener;
import mekanism.api.RelativeSide;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.functions.ConstantPredicates;
import mekanism.api.math.MathUtils;
import mekanism.common.capabilities.chemical.VariableCapacityChemicalTank;
import mekanism.common.capabilities.energy.BasicEnergyContainer;
import mekanism.common.capabilities.energy.MachineEnergyContainer;
import mekanism.common.capabilities.fluid.VariableCapacityFluidTank;
import mekanism.common.capabilities.holder.chemical.ChemicalTankHelper;
import mekanism.common.capabilities.holder.chemical.IChemicalTankHolder;
import mekanism.common.capabilities.holder.energy.EnergyContainerHelper;
import mekanism.common.capabilities.holder.energy.IEnergyContainerHolder;
import mekanism.common.capabilities.holder.fluid.FluidTankHelper;
import mekanism.common.capabilities.holder.fluid.IFluidTankHolder;
import mekanism.common.config.MekanismConfig;
import mekanism.common.integration.computer.annotation.ComputerMethod;
import mekanism.common.integration.computer.annotation.SyntheticComputerMethod;
import mekanism.common.inventory.container.sync.dynamic.ContainerSync;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.registries.MekanismChemicals;
import mekanism.common.tile.TileEntityChemicalTank;
import mekanism.common.tile.component.TileComponentEjector;
import mekanism.common.tile.component.config.ConfigInfo;
import mekanism.common.tile.component.config.DataType;
import mekanism.common.tile.component.config.slot.ChemicalSlotInfo;
import mekanism.common.tile.component.config.slot.EnergySlotInfo;
import mekanism.common.tile.component.config.slot.FluidSlotInfo;
import mekanism.common.tile.prefab.TileEntityConfigurableMachine;
import mekanism.common.util.CableUtils;

import mekanism.generators.common.config.MekanismGeneratorsConfig;
import mekanism.generators.common.content.turbine.TurbineValidator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.CompactMekanismMachines.common.registries.CompactBlocks;
import com.CompactMekanismMachines.common.config.CompactMekanismMachinesConfig;

import java.util.EnumSet;
import java.util.Set;

public class TileEntityCompactIndustrialTurbine extends TileEntityConfigurableMachine {

    /**
     * The tank this block is storing fuel in.
     */
    public IChemicalTank gasTank;
    public VariableCapacityFluidTank ventTank;
    public BasicEnergyContainer energyContainer;
    private long maxoutput = Long.MAX_VALUE;

    public Integer lowerVolume = CompactMekanismMachinesConfig.machines.turbinevertuallowervolume.get();

    @SyntheticComputerMethod(getter = "getDumpingMode")
    public TileEntityChemicalTank.GasMode dumpMode = TileEntityChemicalTank.GasMode.IDLE;

    @SyntheticComputerMethod(getter = "getLastSteamInputRate")
    public long lastSteamInput;
    public long newSteamInput;

    @ContainerSync
    @SyntheticComputerMethod(getter = "getFlowRate")
    public long clientFlow;

    public TileEntityCompactIndustrialTurbine(BlockPos pos, BlockState state) {
        super(CompactBlocks.COMPACT_INDUSTRIAL_TURBINE, pos, state);

        ConfigInfo chemicalConfig = configComponent.getConfig(TransmissionType.CHEMICAL);
        if (chemicalConfig !=null){
            chemicalConfig.addSlotInfo(DataType.INPUT, new ChemicalSlotInfo(true,false, gasTank));
            chemicalConfig.setDataType(DataType.INPUT,RelativeSide.FRONT);
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
        ejectorComponent = new TileComponentEjector(this, ()->Long.MAX_VALUE,()->Integer.MAX_VALUE,()->Long.MAX_VALUE);
        ejectorComponent.setOutputData(configComponent, TransmissionType.CHEMICAL,TransmissionType.FLUID,TransmissionType.ENERGY);
    }

    @Override
    public @Nullable IChemicalTankHolder getInitialChemicalTanks(IContentsListener listener) {
        ChemicalTankHelper builder = ChemicalTankHelper.forSideWithConfig(this);
        builder.addTank(gasTank = VariableCapacityChemicalTank.create(
                CompactMekanismMachinesConfig.machines.turbinegascapacity,
                ConstantPredicates.notExternal(),
                ConstantPredicates.alwaysTrueBi(),
                gas -> gas.is(MekanismChemicals.STEAM),
                null,
                listener
        ));
        return builder.build();
    }

    @NotNull
    @Override
    public IFluidTankHolder getInitialFluidTanks(IContentsListener listener){
        FluidTankHelper builder = FluidTankHelper.forSideWithConfig(this);
        builder.addTank(ventTank = VariableCapacityFluidTank.create(
                CompactMekanismMachinesConfig.machines.turbinefluidcapacity,
                ConstantPredicates.alwaysTrueBi(),
                ConstantPredicates.notExternal(),
                fluid -> fluid.is(Fluids.WATER),
                null
        ));
        return builder.build();
    }
    @NotNull
    @Override
    protected IEnergyContainerHolder getInitialEnergyContainers(IContentsListener listener) {
        EnergyContainerHelper builder = EnergyContainerHelper.forSide(this::getDirection);
        builder.addContainer(energyContainer = BasicEnergyContainer.output(MachineEnergyContainer.validateBlock(this).getStorage(), listener),getEnergySides());
        return builder.build();
    }

    protected RelativeSide[] getEnergySides() {
        return new RelativeSide[]{RelativeSide.FRONT,RelativeSide.BACK,RelativeSide.BOTTOM,RelativeSide.TOP,RelativeSide.RIGHT,RelativeSide.LEFT};
    }

    @Override
    protected boolean onUpdateServer() {
        super.onUpdateServer();
        lastSteamInput = newSteamInput;
        newSteamInput = 0;
        long stored = gasTank.getStored();
        double flowRate = 0;
        if (!gasTank.isEmpty() && canFunction()) {
            setActive(true);
            long energyNeeded = energyContainer.getNeeded();
            if (stored > 0 && !(energyNeeded == 0)) {
                double energyMultiplier = CompactMekanismMachinesConfig.machines.turbineenergymultiply.getAsDouble();
                if (energyMultiplier == 0) {
                    clientFlow = 0;
                } else {
                    double rate = lowerVolume * (CompactMekanismMachinesConfig.machines.turbinevertualdispersers.get() * MekanismGeneratorsConfig.generators.turbineDisperserChemicalFlow.get());
                    rate = Math.min(rate, CompactMekanismMachinesConfig.machines.turbinevertualvents.get() * MekanismGeneratorsConfig.generators.turbineVentChemicalFlow.get());
                    double proportion = stored / (double) getSteamCapacity();
                    rate = Math.min(Math.min(stored, rate), (double) energyNeeded /energyMultiplier) * proportion*100000;
                    clientFlow = MathUtils.clampToLong(rate);
                    if (clientFlow > 0) {
                        energyContainer.insert((long) (energyMultiplier * rate), Action.EXECUTE, AutomationType.INTERNAL);
                        gasTank.shrinkStack(clientFlow, Action.EXECUTE);
                        ventTank.setStack(new FluidStack(Fluids.WATER, Math.min(MathUtils.clampToInt(rate), CompactMekanismMachinesConfig.machines.turbinevertualcondensors.get()* MekanismGeneratorsConfig.generators.condenserRate.get())));
                    }
                }
            } else {
                clientFlow = 0;
            }

            if (dumpMode != TileEntityChemicalTank.GasMode.IDLE && !gasTank.isEmpty()) {
                long amount = gasTank.getStored();
                if (dumpMode == TileEntityChemicalTank.GasMode.DUMPING) {
                    gasTank.shrinkStack(getDumpingAmount(amount), Action.EXECUTE);
                } else {//DUMPING_EXCESS
                    //Don't allow dumping more than the configured amount
                    long targetLevel = MathUtils.clampToLong(gasTank.getCapacity() * MekanismConfig.general.dumpExcessKeepRatio.get());
                    if (targetLevel < amount) {
                        gasTank.shrinkStack(Math.min(amount - targetLevel, getDumpingAmount(amount)), Action.EXECUTE);
                    }
                }
            }

        }
        return true;
    }
    public long getSteamCapacity() {
        return lowerVolume * MekanismGeneratorsConfig.generators.turbineChemicalPerTank.get();
    }

    @ComputerMethod(nameOverride = "setDumpingMode")
    public void setDumpMode(TileEntityChemicalTank.GasMode mode) {
        if (dumpMode != mode) {
            dumpMode = mode;
        }
    }

    @ComputerMethod
    void incrementDumpingMode() {
        setDumpMode(dumpMode.getNext());
    }

    @ComputerMethod
    void decrementDumpingMode() {
        setDumpMode(dumpMode.getPrevious());
    }

    private long getDumpingAmount(long stored) {
        return Math.min(stored, Math.max(stored / 50, lastSteamInput * 2));
    }

    @ComputerMethod
    public long getMaxFlowRate() {
        double rate = lowerVolume * (CompactMekanismMachinesConfig.machines.turbinevertualdispersers.get() * MekanismGeneratorsConfig.generators.turbineDisperserChemicalFlow.get());
        rate = Math.min(rate, CompactMekanismMachinesConfig.machines.turbinevertualvents.get() * MekanismGeneratorsConfig.generators.turbineVentChemicalFlow.get());
        return MathUtils.clampToLong(rate);
    }

    @ComputerMethod
    public long getMaxProduction() {
        long energyMultiplier = MekanismConfig.general.maxEnergyPerSteam.get() / TurbineValidator.MAX_BLADES * CompactMekanismMachinesConfig.machines.turbinevertualblades.get();
        double rate = lowerVolume * (CompactMekanismMachinesConfig.machines.turbinevertualdispersers.get() * MekanismGeneratorsConfig.generators.turbineDisperserChemicalFlow.get());
        rate = Math.min(rate, CompactMekanismMachinesConfig.machines.turbinevertualvents.get() * MekanismGeneratorsConfig.generators.turbineVentChemicalFlow.get());
        return (long) (energyMultiplier * rate);
    }

    @ComputerMethod
    public long getProductionRate() {
        long energyMultiplier = MekanismConfig.general.maxEnergyPerSteam.get() /TurbineValidator.MAX_BLADES * CompactMekanismMachinesConfig.machines.turbinevertualblades.get();
        return energyMultiplier * clientFlow;
    }

    public BasicEnergyContainer getEnergyContainer() {
        return energyContainer;
    }
}
