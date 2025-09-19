package com.CompactMekanismMachines.common.tile;

import com.CompactMekanismMachines.common.config.CompactMekanismMachinesConfig;
import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.IContentsListener;
import mekanism.api.RelativeSide;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.chemical.attribute.ChemicalAttributeValidator;
import mekanism.api.chemical.attribute.ChemicalAttributes;
import mekanism.api.datamaps.IMekanismDataMapTypes;
import mekanism.api.datamaps.chemical.attribute.CooledCoolant;
import mekanism.api.fluid.IExtendedFluidTank;
import mekanism.api.functions.ConstantPredicates;
import mekanism.api.heat.HeatAPI;
import mekanism.api.heat.IHeatCapacitor;
import mekanism.api.math.MathUtils;
import mekanism.common.capabilities.chemical.VariableCapacityChemicalTank;
import mekanism.common.capabilities.heat.CachedAmbientTemperature;
import mekanism.common.capabilities.heat.VariableHeatCapacitor;
import mekanism.common.capabilities.holder.chemical.ChemicalTankHelper;
import mekanism.common.capabilities.holder.chemical.IChemicalTankHolder;
import mekanism.common.capabilities.holder.heat.HeatCapacitorHelper;
import mekanism.common.capabilities.holder.heat.IHeatCapacitorHolder;
import mekanism.common.capabilities.merged.MergedTank;
import mekanism.common.content.boiler.BoilerMultiblockData;
import mekanism.common.inventory.container.sync.dynamic.ContainerSync;
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
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class TileEntityCompactFissionReactor extends TileEntityConfigurableMachine {

    private static final double INVERSE_INSULATION_COEFFICIENT = 10_000;
    private static final double INVERSE_CONDUCTION_COEFFICIENT = 10;
    private static final double waterConductivity = 0.5;

    @ContainerSync
    public IChemicalTank fuelTank;
    @ContainerSync
    public IChemicalTank wasteTank;
    @ContainerSync
    public IChemicalTank coolantChemicalTank;
    @ContainerSync
    public IChemicalTank heatedCoolantTank;
    @ContainerSync
    public IExtendedFluidTank coolantFluidTank;
    @ContainerSync
    public MergedTank coolantTank;
    @ContainerSync
    public IHeatCapacitor heatCapacitor;

    @ContainerSync
    public long lastBoilRate = 0;
    private double biomeAmbientTemp;

    public TileEntityCompactFissionReactor(Holder<Block> blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state);

        // super内部でgetInitial**といったメソッドが呼び出されている。
        // そのため、この時点で2つのタンクは初期化されており問題なく使用できる
        coolantTank = MergedTank.create(
                coolantFluidTank,
                coolantChemicalTank
        );

        biomeAmbientTemp = HeatAPI.getAmbientTemp(this.getLevel(), this.getBlockPos());

        ConfigInfo chemicalConfig = configComponent.getConfig(TransmissionType.CHEMICAL);
        if (chemicalConfig !=null){
            chemicalConfig.addSlotInfo(DataType.INPUT_1, new ChemicalSlotInfo(true,false,fuelTank));
            chemicalConfig.addSlotInfo(DataType.INPUT_2, new ChemicalSlotInfo(true,false, coolantChemicalTank));
            chemicalConfig.addSlotInfo(DataType.OUTPUT_1,new ChemicalSlotInfo(false,true,wasteTank));
            chemicalConfig.addSlotInfo(DataType.OUTPUT_2,new ChemicalSlotInfo(false,true,heatedCoolantTank));
            chemicalConfig.setDataType(DataType.INPUT_1, RelativeSide.FRONT);
            chemicalConfig.setDataType(DataType.INPUT_2,RelativeSide.TOP);
            chemicalConfig.setDataType(DataType.OUTPUT_1,RelativeSide.BOTTOM);
            chemicalConfig.setDataType(DataType.OUTPUT_2,RelativeSide.BACK);

            chemicalConfig.setCanEject(true);
            chemicalConfig.setEjecting(true);
        }

        ConfigInfo fluidConfig = configComponent.getConfig(TransmissionType.FLUID);
        if (fluidConfig!=null){
            fluidConfig.addSlotInfo(DataType.INPUT,new FluidSlotInfo(true,false,coolantFluidTank));

            fluidConfig.setCanEject(false);
            fluidConfig.setEjecting(false);
        }
        ejectorComponent = new TileComponentEjector(this, ()->Long.MAX_VALUE,()->Integer.MAX_VALUE,()-> Long.MAX_VALUE);
        ejectorComponent.setOutputData(configComponent, TransmissionType.CHEMICAL);
    }

    @Override
    public @Nullable IChemicalTankHolder getInitialChemicalTanks(IContentsListener listener) {
        ChemicalTankHelper builder = ChemicalTankHelper.forSideWithConfig(this);

        builder.addTank(fuelTank = VariableCapacityChemicalTank.create(
                CompactMekanismMachinesConfig.storage.cfrFuelTankCapacity::get,
                ConstantPredicates.notExternal(),
                ConstantPredicates.alwaysTrueBi(),
                chemical -> chemical.is(MekanismChemicals.FISSILE_FUEL),
                null,
                listener
        ));
        builder.addTank(coolantChemicalTank = VariableCapacityChemicalTank.create(
                CompactMekanismMachinesConfig.storage.cfrCoolantChemicalTankCapacity::get,
                ConstantPredicates.notExternal(),
                ConstantPredicates.alwaysTrueBi(),
                BoilerMultiblockData.IS_COOLED_COOLANT,
                null,
                listener
        ));
        builder.addTank(heatedCoolantTank = VariableCapacityChemicalTank.output(
                CompactMekanismMachinesConfig.storage.cfrHeatedCoolantTankCapacity::get,
                chemical -> chemical.is(MekanismChemicals.STEAM) || BoilerMultiblockData.IS_HEATED_COOLANT.test(chemical),
                listener
        ));
        builder.addTank(wasteTank = VariableCapacityChemicalTank.create(
                CompactMekanismMachinesConfig.storage.cfrWasteTankCapacity::get,
                ConstantPredicates.alwaysTrueBi(),
                ConstantPredicates.internalOnly(),
                chemical -> chemical.is(MekanismChemicals.NUCLEAR_WASTE),
                ChemicalAttributeValidator.ALWAYS_ALLOW,
                listener
        ));

        return builder.build();
    }

    @Override
    protected @Nullable IHeatCapacitorHolder getInitialHeatCapacitors(IContentsListener listener, CachedAmbientTemperature ambientTemperature) {
        HeatCapacitorHelper builder = HeatCapacitorHelper.forSideWithConfig(this);

        heatCapacitor = VariableHeatCapacitor.create(
                CompactMekanismMachinesConfig.storage.cfrHeatTankCapacity.get(),
                () -> INVERSE_CONDUCTION_COEFFICIENT,
                () -> INVERSE_INSULATION_COEFFICIENT,
                () -> biomeAmbientTemp, null
        );

        return builder.build();
    }

    @Override
    protected boolean onUpdateServer() {
        boolean needPacket = super.onUpdateServer();

        //稼働状態および燃料の確認
        if(fuelTank.isEmpty() || !canFunction()) return needPacket;

        setActive(true);

        //燃焼効率を1あげるのに必要な燃料量
        long fuelPerEfficiency = fuelTank.getCapacity() / CompactMekanismMachinesConfig.storage.cfrMaxBurnRate.get();
        //燃焼効率の自動計算
        long rate = Math.ceilDiv(fuelTank.getStored(),fuelPerEfficiency);



        return needPacket;
    }

    private void handleCoolant() {
        double heat = getBoilEfficiency() * (heatCapacitor.getHeat() - HeatUtils.BASE_BOIL_TEMP * heatCapacitor.getHeatCapacity());

        switch (coolantTank.getCurrentType()) {
            case EMPTY -> lastBoilRate = 0;
            case FLUID -> {
                IExtendedFluidTank fluidCoolantTank = coolantTank.getFluidTank();
                double caseCoolantHeat = heat * waterConductivity;
                lastBoilRate = clampCoolantHeated(HeatUtils.getSteamEnergyEfficiency() * caseCoolantHeat / HeatUtils.getWaterThermalEnthalpy(),
                        fluidCoolantTank.getFluidAmount());
                if (lastBoilRate > 0) {
                    MekanismUtils.logMismatchedStackSize(fluidCoolantTank.shrinkStack((int) lastBoilRate, Action.EXECUTE), lastBoilRate);
                    // extra steam is dumped
                    heatedCoolantTank.insert(MekanismChemicals.STEAM.asStack(lastBoilRate), Action.EXECUTE, AutomationType.INTERNAL);
                    caseCoolantHeat = lastBoilRate * HeatUtils.getWaterThermalEnthalpy() / HeatUtils.getSteamEnergyEfficiency();
                    heatCapacitor.handleHeat(-caseCoolantHeat);
                } else {
                    lastBoilRate = 0;
                }
            }
            case CHEMICAL -> {
                IChemicalTank chemicalCoolantTank = coolantTank.getChemicalTank();
                CooledCoolant coolantType = getCooledCoolant(chemicalCoolantTank.getStack());
                if (coolantType != null) {
                    double caseCoolantHeat = heat * coolantType.conductivity();
                    lastBoilRate = clampCoolantHeated(caseCoolantHeat / coolantType.thermalEnthalpy(), chemicalCoolantTank.getStored());
                    if (lastBoilRate > 0) {
                        MekanismUtils.logMismatchedStackSize(chemicalCoolantTank.shrinkStack(lastBoilRate, Action.EXECUTE), lastBoilRate);
                        heatedCoolantTank.insert(coolantType.heat(lastBoilRate), Action.EXECUTE, AutomationType.INTERNAL);
                        caseCoolantHeat = lastBoilRate * coolantType.thermalEnthalpy();
                        heatCapacitor.handleHeat(-caseCoolantHeat);
                    }
                } else {
                    lastBoilRate = 0;
                }
            }
        }
    }

    @Nullable
    @SuppressWarnings("removal")
    private CooledCoolant getCooledCoolant(ChemicalStack stack) {
        if (stack.isEmpty()) {
            return null;
        }
        CooledCoolant coolant = stack.getData(IMekanismDataMapTypes.INSTANCE.cooledChemicalCoolant());
        if (coolant == null) {//TODO - 1.22: Remove this handling of legacy data
            ChemicalAttributes.CooledCoolant legacyCoolant = stack.getLegacy(ChemicalAttributes.CooledCoolant.class);
            if (legacyCoolant != null) {
                return legacyCoolant.asModern();
            }
        }
        return coolant;
    }

    private long clampCoolantHeated(double heated, long stored) {
        return Mth.clamp(MathUtils.clampToLong(heated), 0, stored);
    }

     public double getBoilEfficiency() {
        /*
        if (fuelAssemblies == 0) {
            //If for some reason the assemblies somehow haven't been initialized (even though they have to be to form)
            // just return that it can't boil
            return 0;
        }
        double avgSurfaceArea = surfaceArea / (double) fuelAssemblies;
        return Math.min(1, avgSurfaceArea / MekanismGeneratorsConfig.generators.fissionSurfaceAreaTarget.get());

        */
         return 1.0;
    }
}
