package com.CompactMekanismMachines.client;

import com.CompactMekanismMachines.client.gui.CompressedWindGenerator.*;
import com.CompactMekanismMachines.client.gui.GuiCompactFissionReactor;
import com.CompactMekanismMachines.client.gui.GuiCompactIndustrialTurbine;
import com.CompactMekanismMachines.client.gui.GuiCompactThermalEvaporation;
import com.CompactMekanismMachines.client.render.RenderCompressedWindGenerator;
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
        event.registerBlockEntityRenderer(CompactTileEntityTypes.WIND_GENERATOR_X2.get(), RenderCompressedWindGenerator::new);
        event.registerBlockEntityRenderer(CompactTileEntityTypes.WIND_GENERATOR_X8.get(), RenderCompressedWindGenerator::new);
        event.registerBlockEntityRenderer(CompactTileEntityTypes.WIND_GENERATOR_X32.get(), RenderCompressedWindGenerator::new);
        event.registerBlockEntityRenderer(CompactTileEntityTypes.WIND_GENERATOR_X128.get(), RenderCompressedWindGenerator::new);
        event.registerBlockEntityRenderer(CompactTileEntityTypes.WIND_GENERATOR_X512.get(), RenderCompressedWindGenerator::new);
        event.registerBlockEntityRenderer(CompactTileEntityTypes.WIND_GENERATOR_X2048.get(), RenderCompressedWindGenerator::new);
        event.registerBlockEntityRenderer(CompactTileEntityTypes.WIND_GENERATOR_X8192.get(), RenderCompressedWindGenerator::new);
        event.registerBlockEntityRenderer(CompactTileEntityTypes.WIND_GENERATOR_X32768.get(), RenderCompressedWindGenerator::new);
        event.registerBlockEntityRenderer(CompactTileEntityTypes.WIND_GENERATOR_X131072.get(), RenderCompressedWindGenerator::new);
        event.registerBlockEntityRenderer(CompactTileEntityTypes.WIND_GENERATOR_X532480.get(), RenderCompressedWindGenerator::new);
    }
    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        ClientRegistrationUtil.registerScreen(event, CompactContainerTypes.COMPACT_FISSION_REACTOR, GuiCompactFissionReactor::new);
        ClientRegistrationUtil.registerScreen(event, CompactContainerTypes.COMPACT_INDUSTRIAL_TURBINE, GuiCompactIndustrialTurbine::new);
        ClientRegistrationUtil.registerScreen(event, CompactContainerTypes.COMPACT_THERMAL_EVAPORATION, GuiCompactThermalEvaporation::new);

        ClientRegistrationUtil.registerScreen(event,CompactContainerTypes.WIND_GENERATOR_X2, GuiCompressedWindGenerator_x2::new);
        ClientRegistrationUtil.registerScreen(event,CompactContainerTypes.WIND_GENERATOR_X8, GuiCompressedWindGenerator_x8::new);
        ClientRegistrationUtil.registerScreen(event,CompactContainerTypes.WIND_GENERATOR_X32, GuiCompressedWindGenerator_x32::new);
        ClientRegistrationUtil.registerScreen(event,CompactContainerTypes.WIND_GENERATOR_X128, GuiCompressedWindGenerator_x128::new);
        ClientRegistrationUtil.registerScreen(event,CompactContainerTypes.WIND_GENERATOR_X512, GuiCompressedWindGenerator_x512::new);
        ClientRegistrationUtil.registerScreen(event,CompactContainerTypes.WIND_GENERATOR_X2048, GuiCompressedWindGenerator_x2048::new);
        ClientRegistrationUtil.registerScreen(event,CompactContainerTypes.WIND_GENERATOR_X8192, GuiCompressedWindGenerator_x8192::new);
        ClientRegistrationUtil.registerScreen(event,CompactContainerTypes.WIND_GENERATOR_X32768,GuiCompressedWindGenerator_x32768::new);
        ClientRegistrationUtil.registerScreen(event,CompactContainerTypes.WIND_GENERATOR_X131072, GuiCompressedWindGenerator_x131072::new);
        ClientRegistrationUtil.registerScreen(event,CompactContainerTypes.WIND_GENERATOR_X532480, GuiCompressedWindGenerator_x532480::new);

    }

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(new RenderPropertiesProvider.MekRenderProperties(RenderWindGeneratorItem.RENDERER), CompactBlocks.WIND_GENERATOR_X2.getItemHolder());
        event.registerItem(new RenderPropertiesProvider.MekRenderProperties(RenderWindGeneratorItem.RENDERER), CompactBlocks.WIND_GENERATOR_X8.getItemHolder());
        event.registerItem(new RenderPropertiesProvider.MekRenderProperties(RenderWindGeneratorItem.RENDERER), CompactBlocks.WIND_GENERATOR_X32.getItemHolder());
        event.registerItem(new RenderPropertiesProvider.MekRenderProperties(RenderWindGeneratorItem.RENDERER), CompactBlocks.WIND_GENERATOR_X128.getItemHolder());
        event.registerItem(new RenderPropertiesProvider.MekRenderProperties(RenderWindGeneratorItem.RENDERER), CompactBlocks.WIND_GENERATOR_X512.getItemHolder());
        event.registerItem(new RenderPropertiesProvider.MekRenderProperties(RenderWindGeneratorItem.RENDERER), CompactBlocks.WIND_GENERATOR_X2048.getItemHolder());
        event.registerItem(new RenderPropertiesProvider.MekRenderProperties(RenderWindGeneratorItem.RENDERER), CompactBlocks.WIND_GENERATOR_X8192.getItemHolder());
        event.registerItem(new RenderPropertiesProvider.MekRenderProperties(RenderWindGeneratorItem.RENDERER), CompactBlocks.WIND_GENERATOR_X32768.getItemHolder());
        event.registerItem(new RenderPropertiesProvider.MekRenderProperties(RenderWindGeneratorItem.RENDERER), CompactBlocks.WIND_GENERATOR_X131072.getItemHolder());
        event.registerItem(new RenderPropertiesProvider.MekRenderProperties(RenderWindGeneratorItem.RENDERER), CompactBlocks.WIND_GENERATOR_X532480.getItemHolder());
    }
}
