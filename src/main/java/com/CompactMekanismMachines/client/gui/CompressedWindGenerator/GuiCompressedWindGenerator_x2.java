package com.CompactMekanismMachines.client.gui.CompressedWindGenerator;

import com.CompactMekanismMachines.client.gui.GuiCompressedWindGenerator;
import com.CompactMekanismMachines.common.tile.CompressedWindGenerator.TileEntityCompressedWindGenerator_x2;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class GuiCompressedWindGenerator_x2 extends GuiCompressedWindGenerator<TileEntityCompressedWindGenerator_x2> {
    public GuiCompressedWindGenerator_x2(MekanismTileContainer<TileEntityCompressedWindGenerator_x2> container, Inventory inv, Component title) {
        super(container, inv, title);
    }
}
