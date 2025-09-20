package com.CompactMekanismMachines.client.gui.CompressedWindGenerator;

import com.CompactMekanismMachines.client.gui.GuiCompressedWindGenerator;
import com.CompactMekanismMachines.common.tile.CompressedWindGenerator.TileEntityCompressedWindGenerator_x128;
import com.CompactMekanismMachines.common.tile.CompressedWindGenerator.TileEntityCompressedWindGenerator_x2;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class GuiCompressedWindGenerator_x128 extends GuiCompressedWindGenerator<TileEntityCompressedWindGenerator_x128> {
    public GuiCompressedWindGenerator_x128(MekanismTileContainer<TileEntityCompressedWindGenerator_x128> container, Inventory inv, Component title) {
        super(container, inv, title);
    }
}
