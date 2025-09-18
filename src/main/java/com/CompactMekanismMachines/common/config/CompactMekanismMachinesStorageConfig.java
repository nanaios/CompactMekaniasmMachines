package com.CompactMekanismMachines.common.config;

import mekanism.common.config.BaseMekanismConfig;
import mekanism.common.config.value.CachedDoubleValue;
import mekanism.common.config.value.CachedIntValue;
import mekanism.common.config.value.CachedLongValue;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public class CompactMekanismMachinesStorageConfig extends BaseMekanismConfig {
    private final ModConfigSpec configSpec;

    public final CachedLongValue cfrFuelTankCapacity;
    public final CachedLongValue cfrCoolantChemicalTankCapacity;
    public final CachedIntValue cfrCoolantFluidTankCapacity;
    public final CachedLongValue cfrHeatedCoolantTankCapacity;
    public final CachedDoubleValue cheaterTankCapacity;
    public final CachedLongValue cfrWasteTankCapacity;
    public final CachedLongValue cfrBurnRate;
    public final CachedLongValue cfrEnergyCapacity;
    public final CachedLongValue turbineEnergy;
    public final CachedLongValue turbineChemicalCapacity;
    public final CachedIntValue turbineFluidCapacity;
    public final CachedLongValue turbineEnergyCapacity;
    public final CachedDoubleValue turbineEnergyMultiply;
    public final CachedIntValue turbineVirtualCondenses;
    public final CachedIntValue turbineVirtualDispersers;
    public final CachedIntValue turbinevertualvents;
    public final CachedIntValue turbinevertualblades;
    public final CachedIntValue turbinevertuallowervolume;
    public final CachedIntValue evaporationheight;

    public CompactMekanismMachinesStorageConfig() {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.comment("CompactFissionReactor Settings").push("compact fission reactor");
        this.cfrFuelTankCapacity = CachedLongValue.wrap(this,         builder.comment("The capacity in mB of the chemical tank of fuel in the Compact Fission Reactor").defineInRange("tankCapacity", 18000000L, 1L, Long.MAX_VALUE));
        this.cfrCoolantChemicalTankCapacity = CachedLongValue.wrap(this,      builder.comment("The capacity in mB of the chemical coolant tank of fuel in the Compact Fission Reactor").defineInRange("chemicalCoolantTankCapacity", 18000000000L, 1L, Long.MAX_VALUE));
        this.cfrCoolantFluidTankCapacity = CachedIntValue.wrap(this,      builder.comment("The capacity in mB of the fluid coolant tank of fuel in the Compact Fission Reactor").defineInRange("fluidCoolantTankCapacity", Integer.MAX_VALUE, 1, Integer.MAX_VALUE));
        this.cfrHeatedCoolantTankCapacity = CachedLongValue.wrap(this,builder.comment("The capacity in mB of the heated coolant tank of fuel in the Compact Fission Reactor").defineInRange("heatedCoolantTankCapacity", 18000000000L, 1L, Long.MAX_VALUE));
        this.cfrWasteTankCapacity = CachedLongValue.wrap(this,builder.comment("The capacity in mB of the waste tank of fuel in the Compact Fission Reactor").defineInRange("tankCapacity", 18000000L, 1L, Long.MAX_VALUE));
        this.cfrBurnRate = CachedLongValue.wrap(this,                 builder.comment("Max fuel consume per tick of Compact Fission Reactor").defineInRange("burnRate",1920,1,Long.MAX_VALUE));
        this.cfrEnergyCapacity = CachedLongValue.wrap(this,           builder.comment("Energy Capacity of Compact Fission Reactor").defineInRange("energyCapacity",2500000000L,1L,Long.MAX_VALUE));
        this.cheaterTankCapacity = CachedDoubleValue.wrap(this, builder.comment("The heat capacity of Compact Fission Reactor")
                .defineInRange("HeatCapacity", 1_000_000D, 1, Double.MAX_VALUE));
        builder.pop();

        builder.comment("CompactFissionReactor Settings").push("compact turbine");
        this.turbineEnergy = CachedLongValue.wrap(this,builder.comment("Max Output of Compact Industrial Turbine","maxOutput").defineInRange("maxOutput",2000000000000L,1L,Long.MAX_VALUE));
        this.turbineChemicalCapacity = CachedLongValue.wrap(this,builder.comment("chemical Tank Capacity  of Compact Industrial Turbine","maxOutput").defineInRange("maxOutput",2000000000000L,1L,Long.MAX_VALUE));
        this.turbineEnergyCapacity = CachedLongValue.wrap(this,builder.comment("Energy Capacity of Compact Industrial Turbine").defineInRange("chemicalTankCapacity",2000000000000L,0L,Long.MAX_VALUE));
        this.turbineFluidCapacity = CachedIntValue.wrap(this,builder.comment("Fluid Tank Capacity of Compact Industrial Turbine").defineInRange("fluidTankCapacity",Integer.MAX_VALUE,0,Integer.MAX_VALUE));
        this.turbineEnergyMultiply = CachedDoubleValue.wrap(this,builder.comment("turbine energy production rate magnification").defineInRange("energyMagnification",25600000,0,Double.MAX_VALUE));
        this.turbineVirtualCondenses = CachedIntValue.wrap(this, builder.comment("amount of virtual turbine condenser block").defineInRange("virtualCondenser",500,0,Integer.MAX_VALUE));
        this.turbineVirtualDispersers = CachedIntValue.wrap(this, builder.comment("amount of virtual turbine disperser block").defineInRange("virtualVent",500,0,Integer.MAX_VALUE));
        this.turbinevertualvents = CachedIntValue.wrap(this,builder.comment("amount of virtual turbine vent block").defineInRange("upVolume",800,0,Integer.MAX_VALUE));
        this.turbinevertualblades = CachedIntValue.wrap(this,builder.comment("amount of virtual blade").defineInRange("blades",200,0,Integer.MAX_VALUE));
        this.turbinevertuallowervolume = CachedIntValue.wrap(this,builder.comment("virtual volume of lower section").defineInRange("lowerVolume",500,0,Integer.MAX_VALUE));

        this.evaporationheight = CachedIntValue.wrap(this,builder.comment("virtual height of compact thermal evaporation block").defineInRange("virtual_height",36,1,Integer.MAX_VALUE));

        this.configSpec = builder.build();
    }

    @Override
    public String getFileName() {
        return "machine-storage";
    }

    @Override
    public String getTranslation() {
        return "Storage Config";
    }

    @Override
    public ModConfigSpec getConfigSpec() {
        return configSpec;
    }

    @Override
    public ModConfig.Type getConfigType() {
        return ModConfig.Type.SERVER;
    }
}
