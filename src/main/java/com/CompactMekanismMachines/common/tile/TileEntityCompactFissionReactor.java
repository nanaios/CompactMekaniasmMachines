package com.CompactMekanismMachines.common.tile;

import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.IContentsListener;
import mekanism.api.RelativeSide;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.chemical.attribute.ChemicalAttributeValidator;
import mekanism.api.datamaps.IMekanismDataMapTypes;
import mekanism.api.datamaps.chemical.attribute.CooledCoolant;
import mekanism.api.fluid.IExtendedFluidTank;
import mekanism.api.functions.ConstantPredicates;
import mekanism.api.heat.HeatAPI;
import mekanism.api.math.MathUtils;
import mekanism.common.capabilities.chemical.VariableCapacityChemicalTank;
import mekanism.common.capabilities.fluid.VariableCapacityFluidTank;
import mekanism.common.capabilities.heat.CachedAmbientTemperature;
import mekanism.common.capabilities.heat.VariableHeatCapacitor;
import mekanism.common.capabilities.holder.chemical.ChemicalTankHelper;
import mekanism.common.capabilities.holder.chemical.IChemicalTankHolder;
import mekanism.common.capabilities.holder.fluid.FluidTankHelper;
import mekanism.common.capabilities.holder.fluid.IFluidTankHolder;
import mekanism.common.capabilities.holder.heat.HeatCapacitorHelper;
import mekanism.common.capabilities.holder.heat.IHeatCapacitorHolder;
import mekanism.common.content.boiler.BoilerMultiblockData;
import mekanism.common.integration.computer.SpecialComputerMethodWrapper;
import mekanism.common.integration.computer.annotation.ComputerMethod;
import mekanism.common.integration.computer.annotation.WrappingComputerMethod;
import mekanism.common.inventory.container.MekanismContainer;
import mekanism.common.inventory.container.sync.SyncableDouble;
import mekanism.common.inventory.container.sync.SyncableInt;
import mekanism.common.inventory.container.sync.SyncableLong;
import mekanism.common.inventory.container.sync.dynamic.ContainerSync;
import mekanism.common.inventory.slot.EnergyInventorySlot;
import mekanism.common.inventory.slot.chemical.ChemicalInventorySlot;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.registries.MekanismChemicals;
import mekanism.common.tile.component.TileComponentEjector;
import mekanism.common.tile.component.config.ConfigInfo;
import mekanism.common.tile.component.config.DataType;
import mekanism.common.tile.component.config.slot.ChemicalSlotInfo;
import mekanism.common.tile.component.config.slot.FluidSlotInfo;
import mekanism.common.tile.prefab.TileEntityConfigurableMachine;
import mekanism.common.util.HeatUtils;
import mekanism.common.util.MekanismUtils;

import mekanism.generators.common.config.MekanismGeneratorsConfig;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.CompactMekanismMachines.common.registries.CompactBlocks;
import com.CompactMekanismMachines.common.config.CompactMekanismMachinesConfig;

public class TileEntityCompactFissionReactor extends TileEntityConfigurableMachine {

    private static final double INVERSE_INSULATION_COEFFICIENT = 10_000;
    private static final double INVERSE_CONDUCTION_COEFFICIENT = 10;
    private static final double waterConductivity = 0.5;

    @ContainerSync
    public IChemicalTank fuelTank;
    @ContainerSync
    public IChemicalTank coolantGasTank;
    @ContainerSync
    public IExtendedFluidTank coolantFluidTank;
    @ContainerSync
    public IChemicalTank heatedCoolantTank;
    @ContainerSync
    public IChemicalTank wasteTank;
    @ContainerSync
    public HeatCapacitor heatCapacitor;

    private long burnTicks;
    private int maxBurnTicks;
    private long generationRate = 0L;
    private final double biomeAmbientTemp;
    private double gasUsedLastTick;
    public  double partialWaste;
    public long lastBoilRate = 0;

    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.class, methodNames = "getFuelItem", docPlaceholder = "fuel item slot")
    ChemicalInventorySlot fuelSlot;
    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.class, methodNames = "getEnergyItem", docPlaceholder = "energy item slot")
    EnergyInventorySlot energySlot;

    public TileEntityCompactFissionReactor(BlockPos pos, BlockState state) {
        super(CompactBlocks.COMPACT_FISSION_REACTOR, pos, state);
        biomeAmbientTemp = HeatAPI.getAmbientTemp(this.getLevel(), this.getBlockPos());

        ConfigInfo gasConfig = configComponent.getConfig(TransmissionType.CHEMICAL);
        if (gasConfig !=null){
            gasConfig.addSlotInfo(DataType.INPUT_1, new ChemicalSlotInfo(true,false,fuelTank));
            gasConfig.addSlotInfo(DataType.INPUT_2, new ChemicalSlotInfo(true,false,coolantGasTank));
            gasConfig.addSlotInfo(DataType.OUTPUT_1,new ChemicalSlotInfo(false,true,wasteTank));
            gasConfig.addSlotInfo(DataType.OUTPUT_2,new ChemicalSlotInfo(false,true,heatedCoolantTank));
            gasConfig.setDataType(DataType.INPUT_1,RelativeSide.FRONT);
            gasConfig.setDataType(DataType.INPUT_2,RelativeSide.TOP);
            gasConfig.setDataType(DataType.OUTPUT_1,RelativeSide.BOTTOM);
            gasConfig.setDataType(DataType.OUTPUT_2,RelativeSide.BACK);
        }
        ConfigInfo fluidConfig = configComponent.getConfig(TransmissionType.FLUID);
        if (fluidConfig!=null){
            fluidConfig.addSlotInfo(DataType.INPUT,new FluidSlotInfo(true,false,coolantFluidTank));
        }
        ejectorComponent = new TileComponentEjector(this, ()->Long.MAX_VALUE,()->Integer.MAX_VALUE,()-> Long.MAX_VALUE);
        ejectorComponent.setOutputData(configComponent, TransmissionType.CHEMICAL,TransmissionType.FLUID)
                .setCanTankEject(tank -> (tank == heatedCoolantTank)||(tank == wasteTank));
    }

    @Override
    public @Nullable IChemicalTankHolder getInitialChemicalTanks(IContentsListener listener) {
        ChemicalTankHelper builder = ChemicalTankHelper.forSideWithConfig(this);

        builder.addTank(fuelTank = VariableCapacityChemicalTank.create(
                CompactMekanismMachinesConfig.machines.cfrFuelTankCapacity::get,
                ConstantPredicates.notExternal(),
                ConstantPredicates.alwaysTrueBi(),
                gas -> gas.is(MekanismChemicals.FISSILE_FUEL),
                null,
                listener
        ));
        builder.addTank(coolantGasTank = VariableCapacityChemicalTank.create(
                CompactMekanismMachinesConfig.machines.cfrCoolantGasTankCapacity::get,
                ConstantPredicates.notExternal(),
                ConstantPredicates.alwaysTrueBi(),
                chemical -> chemical.is(MekanismChemicals.STEAM) || BoilerMultiblockData.IS_HEATED_COOLANT.test(chemical),
                null,
                listener
        ));
        builder.addTank(wasteTank = VariableCapacityChemicalTank.create(
                CompactMekanismMachinesConfig.machines.cfrHeatedCoolantTankCapacity::get,
                ConstantPredicates.alwaysTrueBi(),
                ConstantPredicates.internalOnly(),
                chemical -> chemical.is(MekanismChemicals.STEAM) || BoilerMultiblockData.IS_COOLED_COOLANT.test(chemical),
                ChemicalAttributeValidator.ALWAYS_ALLOW,
                listener
        ));
        builder.addTank(heatedCoolantTank = VariableCapacityChemicalTank.create(
                CompactMekanismMachinesConfig.machines.cfrHeatedCoolantTankCapacity::get,
                ConstantPredicates.alwaysTrueBi(),
                ConstantPredicates.internalOnly(),
                chemical -> chemical.is(MekanismChemicals.NUCLEAR_WASTE),
                ChemicalAttributeValidator.ALWAYS_ALLOW,
                listener
        ));

        return builder.build();
    }

    @NotNull
    @Override
    public IFluidTankHolder getInitialFluidTanks(IContentsListener listener){
        FluidTankHelper builder = FluidTankHelper.forSideWithConfig(this);

        builder.addTank(coolantFluidTank = VariableCapacityFluidTank.create(
                CompactMekanismMachinesConfig.machines.cfrCoolantFluidTankCapacity,
                ConstantPredicates.notExternal(),
                ConstantPredicates.alwaysTrueBi(),
                fluid -> fluid.is(Tags.Fluids.WATER), listener
        ));

        return builder.build();
    }

    @NotNull
    @Override
    public IHeatCapacitorHolder getInitialHeatCapacitors(IContentsListener listener, CachedAmbientTemperature ambientTemperature){
        HeatCapacitorHelper builder = HeatCapacitorHelper.forSide(this::getDirection);
        builder.addCapacitor(heatCapacitor = new HeatCapacitor(listener));
        return builder.build();
    }


    @Override
    protected boolean onUpdateServer() {
        boolean needsPacket = super.onUpdateServer();
        if (!fuelTank.isEmpty() && canFunction()) {
            setActive(true);

            if (!fuelTank.isEmpty()) {
                maxBurnTicks = 1;
                generationRate = 50000L;

            }

            long toUse = getToUse();
            long toUseGeneration = generationRate * toUse;

            long total = burnTicks + fuelTank.getStored() * maxBurnTicks;
            total -= toUse;
            if (!fuelTank.isEmpty()) {
                //TODO: Improve this as it is sort of hacky
                fuelTank.setStack(new ChemicalStack(fuelTank.getTypeHolder(), total / maxBurnTicks));
            }

            heatCapacitor.handleHeat(toUse * MekanismGeneratorsConfig.generators.energyPerFissionFuel.get());
            if (heatCapacitor.getTemperature()>1600){
                heatCapacitor.setHeat(heatCapacitor.getHeatCapacity()*1600);
            }
            double temp = heatCapacitor.getTemperature();
            double heat = toUse * (temp - HeatUtils.BASE_BOIL_TEMP) * heatCapacitor.getHeatCapacity();
            if (!coolantFluidTank.isEmpty()){
                double caseCoolantHeat = heat * waterConductivity;
                lastBoilRate = clampCoolantHeated(HeatUtils.getSteamEnergyEfficiency() * caseCoolantHeat / HeatUtils.getWaterThermalEnthalpy(),
                        coolantFluidTank.getFluidAmount());
                if (lastBoilRate > 0) {
                    MekanismUtils.logMismatchedStackSize(coolantFluidTank.shrinkStack((int) lastBoilRate, Action.EXECUTE), lastBoilRate);
                    // extra steam is dumped
                    heatedCoolantTank.insert(MekanismChemicals.STEAM.asStack(lastBoilRate), Action.EXECUTE, AutomationType.INTERNAL);
                    caseCoolantHeat = lastBoilRate * HeatUtils.getWaterThermalEnthalpy() / HeatUtils.getSteamEnergyEfficiency();
                    heatCapacitor.handleHeat(-caseCoolantHeat);
                }
            }   else if (!coolantGasTank.isEmpty()) {
                CooledCoolant cooledCoolant = coolantGasTank.getStack().getData(IMekanismDataMapTypes.INSTANCE.cooledChemicalCoolant());
                if(cooledCoolant != null) {
                    double caseCoolantHeat = heat * cooledCoolant.conductivity();
                    lastBoilRate = clampCoolantHeated(caseCoolantHeat / cooledCoolant.thermalEnthalpy(), coolantGasTank.getStored());
                    if (lastBoilRate > 0) {
                        MekanismUtils.logMismatchedStackSize(coolantGasTank.shrinkStack(lastBoilRate, Action.EXECUTE), lastBoilRate);
                        heatedCoolantTank.insert(cooledCoolant.heat(lastBoilRate), Action.EXECUTE, AutomationType.INTERNAL);
                        caseCoolantHeat = lastBoilRate * cooledCoolant.thermalEnthalpy();
                        heatCapacitor.handleHeat(-caseCoolantHeat);
                    }
                }
            }
            partialWaste += toUse;
            long newWaste = (long) Math.floor(partialWaste);
            if (newWaste>0){
                partialWaste %= 1;
                ChemicalStack wasteToAdd = MekanismChemicals.NUCLEAR_WASTE.asStack(newWaste);
                wasteTank.insert(wasteToAdd, Action.EXECUTE, AutomationType.INTERNAL);
            }
            burnTicks = total % maxBurnTicks;
            gasUsedLastTick = toUse / (double) maxBurnTicks;
            needsPacket = true;
        } else {
            if (fuelTank.isEmpty() && burnTicks == 0) {
                reset();
            }
            gasUsedLastTick = 0;
            setActive(false);
        }
        return needsPacket;
    }

    private void reset() {
        burnTicks = 0;
        maxBurnTicks = 0;
        generationRate = 0;
    }

    private long getToUse() {
        if (generationRate == 0 || fuelTank.isEmpty()) {
            return 0;
        }
        long max = (long) Math.ceil(CompactMekanismMachinesConfig.machines.cfrBurnRate.get() * (fuelTank.getStored() / (double) fuelTank.getCapacity()));
        max = Math.min(maxBurnTicks * fuelTank.getStored() + burnTicks, max);
        return max;
    }

    private long clampCoolantHeated(double heated, long stored) {
        long heatedLong = MathUtils.clampToLong(heated);
        if (heatedLong < 0) {
            return 0;
        } else if (heatedLong > stored) {
            return stored;
        }
        return heatedLong;
    }

    public long getGenerationRate() {
        return generationRate;
    }

    @ComputerMethod(nameOverride = "getBurnRate")
    public double getUsed() {
        return Math.round(gasUsedLastTick * 100) / 100D;
    }

    public int getMaxBurnTicks() {
        return maxBurnTicks;
    }

    @Override
    public int getRedstoneLevel() {
        return MekanismUtils.redstoneLevelFromContents(fuelTank.getStored(), fuelTank.getCapacity());
    }

    @Override
    public void addContainerTrackers(MekanismContainer container) {
        super.addContainerTrackers(container);
        container.track(SyncableLong.create(this::getGenerationRate, value -> generationRate = value));
        container.track(SyncableDouble.create(this::getUsed, value -> gasUsedLastTick = value));
        container.track(SyncableInt.create(this::getMaxBurnTicks, value -> maxBurnTicks = value));
    }

    public class HeatCapacitor extends VariableHeatCapacitor{
        protected HeatCapacitor(@Nullable IContentsListener listener){
            super(CompactMekanismMachinesConfig.machines.cfrHeatTankCpacity.get(),() -> INVERSE_CONDUCTION_COEFFICIENT, () -> INVERSE_INSULATION_COEFFICIENT, () -> biomeAmbientTemp, null);
        }
    }

}
