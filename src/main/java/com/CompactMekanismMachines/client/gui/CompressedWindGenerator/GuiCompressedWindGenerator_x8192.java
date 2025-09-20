package com.CompactMekanismMachines.client.gui.CompressedWindGenerator;

import com.CompactMekanismMachines.client.gui.GuiCompressedWindGenerator;
import com.CompactMekanismMachines.common.tile.CompressedWindGenerator.TileEntityCompressedWindGenerator_x512;
import com.CompactMekanismMachines.common.tile.CompressedWindGenerator.TileEntityCompressedWindGenerator_x8192;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class GuiCompressedWindGenerator_x8192 extends GuiCompressedWindGenerator<TileEntityCompressedWindGenerator_x8192> {
    public GuiCompressedWindGenerator_x8192(MekanismTileContainer<TileEntityCompressedWindGenerator_x8192> container, Inventory inv, Component title) {
        super(container, inv, title);
    }
}
