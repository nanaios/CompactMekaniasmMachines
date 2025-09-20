package com.CompactMekanismMachines.client;

import com.CompactMekanismMachines.client.gui.GuiCompactFissionReactor;
import com.CompactMekanismMachines.client.gui.GuiCompactIndustrialTurbine;
import com.CompactMekanismMachines.client.gui.GuiCompactThermalEvaporation;
import com.CompactMekanismMachines.client.render.CompressedWindGenerator.RenderCompressedWindGenerator_x2;
import com.CompactMekanismMachines.common.CompactMekanismMachines;
import com.CompactMekanismMachines.common.registries.CompactBlocks;
import com.CompactMekanismMachines.common.registries.CompactContainerTypes;
import com.CompactMekanismMachines.common.registries.CompactTileEntityTypes;
import mekanism.client.ClientRegistrationUtil;
import mekanism.client.render.RenderPropertiesProvider;
import mekanism.generators.client.render.item.RenderWindGeneratorItem;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(modid = CompactMekanismMachines.MOD_ID, value = Dist.CLIENT)
public class CompactClientRegistration {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(CompactTileEntityTypes.WIND_GENERATOR_X2.get(), RenderCompressedWindGenerator_x2::new);
    }
    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        ClientRegistrationUtil.registerScreen(event, CompactContainerTypes.COMPACT_FISSION_REACTOR, GuiCompactFissionReactor::new);
        ClientRegistrationUtil.registerScreen(event, CompactContainerTypes.COMPACT_INDUSTRIAL_TURBINE, GuiCompactIndustrialTurbine::new);
        ClientRegistrationUtil.registerScreen(event, CompactContainerTypes.COMPACT_THERMAL_EVAPORATION, GuiCompactThermalEvaporation::new);
    }

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(new RenderPropertiesProvider.MekRenderProperties(RenderWindGeneratorItem.RENDERER), CompactBlocks.WIND_GENERATOR_X2.getItemHolder());
    }
}
