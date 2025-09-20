package com.CompactMekanismMachines.client.gui.CompressedWindGenerator;

import com.CompactMekanismMachines.client.gui.GuiCompressedWindGenerator;
import com.CompactMekanismMachines.common.tile.CompressedWindGenerator.TileEntityCompressedWindGenerator_x2;
import com.CompactMekanismMachines.common.tile.CompressedWindGenerator.TileEntityCompressedWindGenerator_x8;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class GuiCompressedWindGenerator_x8 extends GuiCompressedWindGenerator<TileEntityCompressedWindGenerator_x8> {
    public GuiCompressedWindGenerator_x8(MekanismTileContainer<TileEntityCompressedWindGenerator_x8> container, Inventory inv, Component title) {
        super(container, inv, title);
    }
}
