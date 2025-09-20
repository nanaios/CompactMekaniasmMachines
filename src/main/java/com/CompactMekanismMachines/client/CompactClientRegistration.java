package com.CompactMekanismMachines.client;

import com.CompactMekanismMachines.client.gui.GuiCompactFissionReactor;
import com.CompactMekanismMachines.client.gui.GuiCompactIndustrialTurbine;
import com.CompactMekanismMachines.client.gui.GuiCompactThermalEvaporation;
import com.CompactMekanismMachines.common.CompactMekanismMachines;
import com.CompactMekanismMachines.common.registries.CompactContainerTypes;
import mekanism.client.ClientRegistrationUtil;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = CompactMekanismMachines.MOD_ID, value = Dist.CLIENT)
public class CompactClientRegistration {
    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        ClientRegistrationUtil.registerScreen(event, CompactContainerTypes.COMPACT_FISSION_REACTOR, GuiCompactFissionReactor::new);
        ClientRegistrationUtil.registerScreen(event, CompactContainerTypes.COMPACT_INDUSTRIAL_TURBINE, GuiCompactIndustrialTurbine::new);
        ClientRegistrationUtil.registerScreen(event, CompactContainerTypes.COMPACT_THERMAL_EVAPORATION, GuiCompactThermalEvaporation::new);
    }
}
