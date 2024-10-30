package com.CompactMekanismMachines.client.gui.CompressedWindGenerator;

import com.CompactMekanismMachines.client.gui.GuiCompressedWindGenerator;
import com.CompactMekanismMachines.common.tile.CompressedWindGenerator.TileEntityCompressedWindGenerator_x2048;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class GuiCompressedWindGenerator_x2048 extends GuiCompressedWindGenerator<TileEntityCompressedWindGenerator_x2048, MekanismTileContainer<TileEntityCompressedWindGenerator_x2048>> {

    public GuiCompressedWindGenerator_x2048(MekanismTileContainer<TileEntityCompressedWindGenerator_x2048> container, Inventory inv, Component title) {
        super(container, inv, title);
    }
}
