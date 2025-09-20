package com.CompactMekanismMachines.client.gui;

import com.CompactMekanismMachines.common.config.CompactMekanismMachinesConfig;
import com.CompactMekanismMachines.common.tile.TileEntityCompactIndustrialTurbine;
import mekanism.api.text.EnumColor;
import mekanism.client.gui.GuiConfigurableTile;
import mekanism.client.gui.element.GuiInnerScreen;
import mekanism.client.gui.element.bar.GuiBar;
import mekanism.client.gui.element.bar.GuiVerticalPowerBar;
import mekanism.client.gui.element.bar.GuiVerticalRateBar;
import mekanism.client.gui.element.button.GuiGasMode;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiChemicalGauge;
import mekanism.client.gui.element.tab.GuiEnergyTab;
import mekanism.client.gui.tooltip.TooltipUtils;
import mekanism.common.MekanismLang;
import mekanism.common.config.MekanismConfig;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.util.text.EnergyDisplay;
import mekanism.common.util.text.TextUtils;
import mekanism.generators.common.GeneratorsLang;
import mekanism.generators.common.config.MekanismGeneratorsConfig;
import mekanism.generators.common.content.turbine.TurbineValidator;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GuiCompactIndustrialTurbine extends GuiConfigurableTile<TileEntityCompactIndustrialTurbine, MekanismTileContainer<TileEntityCompactIndustrialTurbine>> {
    public GuiCompactIndustrialTurbine(MekanismTileContainer<TileEntityCompactIndustrialTurbine> container, Inventory inv, Component title) {
        super(container, inv, title);
        inventoryLabelY += 2;
        titleLabelY = 5;
        dynamicSlots = true;
    }
    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        addRenderableWidget(new GuiInnerScreen(this, 50, 18, 112, 50, () -> {
            List<Component> list = new ArrayList<>();
            list.add(GeneratorsLang.TURBINE_PRODUCTION_AMOUNT.translate(EnergyDisplay.of(tile.getProductionRate())));
            list.add(GeneratorsLang.TURBINE_FLOW_RATE.translate(TextUtils.format(tile.clientFlow)));
            list.add(GeneratorsLang.TURBINE_CAPACITY.translate(TextUtils.format(tile.getSteamCapacity())));
            list.add(GeneratorsLang.TURBINE_MAX_FLOW.translate(TextUtils.format(tile.getMaxFlowRate())));
            return list;
        }));
        //addRenderableWidget(new GuiTurbineTab(this, tile, GuiTurbineTab.TurbineTab.STAT));
        addRenderableWidget(new GuiVerticalPowerBar(this, new GuiBar.IBarInfoHandler() {
            @Override
            public Component getTooltip() {
                return EnergyDisplay.of(tile.energyContainer).getTextComponent();
            }

            @Override
            public double getLevel() {
                return (double) tile.energyContainer.getEnergy() / tile.energyContainer.getMaxEnergy();
            }
        }, 164, 16));
        addRenderableWidget(new GuiVerticalRateBar(this, new GuiBar.IBarInfoHandler() {
            @Override
            public Component getTooltip() {
                return GeneratorsLang.TURBINE_STEAM_INPUT_RATE.translate(TextUtils.format(tile.lastSteamInput));
            }

            @Override
            public double getLevel() {
                double rate = Math.min(CompactMekanismMachinesConfig.storage.turbineVirtualLowerVolume.get() * CompactMekanismMachinesConfig.storage.turbineVirtualDispersers.get() * MekanismGeneratorsConfig.generators.turbineDisperserChemicalFlow.get(),
                        CompactMekanismMachinesConfig.storage.turbineVirtualVents.get() * MekanismGeneratorsConfig.generators.turbineVentChemicalFlow.get());
                if (rate == 0) {
                    return 0;
                }
                return Math.min(1, tile.lastSteamInput / rate);
            }
        }, 40, 13));
        addRenderableWidget(new GuiChemicalGauge(() -> tile.chemicalTank, () -> tile.getChemicalTanks(null), GaugeType.MEDIUM, this, 6, 13));
        addRenderableWidget(new GuiEnergyTab(this, () -> {
            EnergyDisplay storing;
            EnergyDisplay producing;
            storing = EnergyDisplay.of(tile.energyContainer);
            producing = EnergyDisplay.of(MekanismConfig.general.maxEnergyPerSteam.get() /TurbineValidator.MAX_BLADES
                     * tile.clientFlow * CompactMekanismMachinesConfig.storage.turbineVirtualBlades.get());
            return List.of(MekanismLang.STORING.translate(storing), GeneratorsLang.PRODUCING_AMOUNT.translate(producing));
        }));

        Component warning = GeneratorsLang.TURBINE_DUMPING_STEAM_WARNING.translateColored(EnumColor.RED);

        addRenderableWidget(new GuiGasMode(this, 173, 72, true, () -> tile.dumpMode, tile.getBlockPos(), 0,
                TooltipUtils.create(GeneratorsLang.TURBINE_DUMPING_EXCESS_STEAM.translate(), warning),
                TooltipUtils.create(GeneratorsLang.TURBINE_DUMPING_STEAM.translate(), warning)));

        addRenderableWidget(new GuiVerticalPowerBar(this, tile.energyContainer, 164, 15));
    }

    @Override
    protected void drawForegroundText(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        renderTitleText(guiGraphics);
        //drawString(guiGraphics, playerInventoryTitle, inventoryLabelX, inventoryLabelY, titleTextColor());
        super.drawForegroundText(guiGraphics, mouseX, mouseY);
    }
}
