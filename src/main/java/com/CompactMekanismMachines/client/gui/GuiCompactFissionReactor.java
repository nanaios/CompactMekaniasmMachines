package com.CompactMekanismMachines.client.gui;

import com.CompactMekanismMachines.common.tile.TileEntityCompactFissionReactor;
import mekanism.client.gui.GuiConfigurableTile;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiChemicalGauge;
import mekanism.client.gui.element.gauge.GuiFluidGauge;
import mekanism.client.gui.element.tab.GuiHeatTab;
import mekanism.common.MekanismLang;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.UnitDisplayUtils;
import mekanism.generators.common.GeneratorsLang;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class GuiCompactFissionReactor extends GuiConfigurableTile<TileEntityCompactFissionReactor, MekanismTileContainer<TileEntityCompactFissionReactor>> {
    public GuiCompactFissionReactor(MekanismTileContainer<TileEntityCompactFissionReactor> container, Inventory inv, Component title) {
        super(container, inv, title);
        dynamicSlots = true;
        titleLabelY = 5;
        inventoryLabelY += 3;
    }
    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        addRenderableWidget(new GuiChemicalGauge(() -> tile.heatedCoolantTank, ()->tile.getChemicalTanks(null),  GaugeType.STANDARD,this,122,13));
        addRenderableWidget(new GuiChemicalGauge(() -> tile.wasteTank, ()->tile.getChemicalTanks(null),  GaugeType.STANDARD,this, 100,13));
        addRenderableWidget(new GuiChemicalGauge(() -> tile.fuelTank, ()->tile.getChemicalTanks(null),  GaugeType.STANDARD,this, 45,13));
        addRenderableWidget(new GuiFluidGauge(() -> tile.coolantFluidTank, ()->tile.getFluidTanks(null),GaugeType.STANDARD,this, 23,13));
        addRenderableWidget(new GuiChemicalGauge(() -> tile.coolantChemicalTank, ()->tile.getChemicalTanks(null),  GaugeType.STANDARD,this, 5,13));
        addRenderableWidget(new GuiHeatTab( this, ()->{
            Component environment = MekanismUtils.getTemperatureDisplay(tile.heatCapacitor.getTemperature(), UnitDisplayUtils.TemperatureUnit.KELVIN,false);
            return Collections.singletonList(MekanismLang.TEMPERATURE.translate(environment));
        }));
    }

    @Override
    protected void drawForegroundText(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY)
    {
        super.drawForegroundText(guiGraphics, mouseX, mouseY);
        renderTitleText(guiGraphics);


        drawScrollingString(guiGraphics, GeneratorsLang.GAS_BURN_RATE.translate(tile.lastBurned), 45, inventoryLabelY, TextAlignment.CENTER, titleTextColor(), 4, false);
    }
}
