package com.CompactMekanismMachines.client.gui.CompressedWindGenerator;

import com.CompactMekanismMachines.client.gui.GuiCompressedWindGenerator;
import com.CompactMekanismMachines.common.tile.CompressedWindGenerator.TileEntityCompressedWindGenerator_x32768;
import com.CompactMekanismMachines.common.tile.CompressedWindGenerator.TileEntityCompressedWindGenerator_x512;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class GuiCompressedWindGenerator_x32768 extends GuiCompressedWindGenerator<TileEntityCompressedWindGenerator_x32768> {
    public GuiCompressedWindGenerator_x32768(MekanismTileContainer<TileEntityCompressedWindGenerator_x32768> container, Inventory inv, Component title) {
        super(container, inv, title);
    }
}
