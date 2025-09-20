package com.CompactMekanismMachines.client.gui.CompressedWindGenerator;

import com.CompactMekanismMachines.client.gui.GuiCompressedWindGenerator;
import com.CompactMekanismMachines.common.tile.CompressedWindGenerator.TileEntityCompressedWindGenerator_x2;
import com.CompactMekanismMachines.common.tile.CompressedWindGenerator.TileEntityCompressedWindGenerator_x32;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class GuiCompressedWindGenerator_x32 extends GuiCompressedWindGenerator<TileEntityCompressedWindGenerator_x32> {
    public GuiCompressedWindGenerator_x32(MekanismTileContainer<TileEntityCompressedWindGenerator_x32> container, Inventory inv, Component title) {
        super(container, inv, title);
    }
}
