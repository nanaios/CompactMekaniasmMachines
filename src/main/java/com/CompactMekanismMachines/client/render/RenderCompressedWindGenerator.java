package com.CompactMekanismMachines.client.render;

import com.CompactMekanismMachines.common.tile.TileEntityCompressedWindGenerator;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mekanism.client.render.MekanismRenderer;
import mekanism.client.render.tileentity.IWireFrameRenderer;
import mekanism.client.render.tileentity.ModelTileEntityRenderer;
import mekanism.generators.client.model.ModelWindGenerator;
import mekanism.generators.common.GeneratorsProfilerConstants;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

public class RenderCompressedWindGenerator<TILE extends TileEntityCompressedWindGenerator<?>> extends ModelTileEntityRenderer<TILE, ModelWindGenerator> implements IWireFrameRenderer {
    public RenderCompressedWindGenerator(BlockEntityRendererProvider.Context context) {
        super(context, ModelWindGenerator::new);
    }

    @Override
    protected void render(@NotNull TILE tile, float partialTick, @NotNull PoseStack matrix, @NotNull MultiBufferSource renderer, int light, int overlayLight, @NotNull ProfilerFiller profiler) {
        float angle = setupRenderer(tile, partialTick, matrix);
        model.render(matrix, renderer, angle, light, overlayLight, false);
        matrix.popPose();
    }

    @Override
    protected @NotNull String getProfilerSection() {
        return GeneratorsProfilerConstants.WIND_GENERATOR;
    }

    @Override
    public boolean shouldRenderOffScreen(@NotNull TILE tile) {
        return true;
    }

    @Override
    public @NotNull AABB getRenderBoundingBox(TILE tile) {
        //Note: we just extend it to the max size (including blades) it could be ignoring what direction it is actually facing
        BlockPos pos = tile.getBlockPos();
        return AABB.encapsulatingFullBlocks(pos.offset(-2, 0, -2), pos.offset(2, 6, 2));
    }

    @Override
    public void renderWireFrame(BlockEntity tile, float partialTick, PoseStack matrix, VertexConsumer buffer) {
        if (tile instanceof TileEntityCompressedWindGenerator<?> windGenerator) {
            float angle = setupRenderer(windGenerator, partialTick, matrix);
            model.renderWireFrame(matrix, buffer, angle);
            matrix.popPose();
        }
    }

    private float setupRenderer(TileEntityCompressedWindGenerator<?> tile, float partialTick, PoseStack matrix) {
        matrix.pushPose();
        matrix.translate(0.5, 1.5, 0.5);
        MekanismRenderer.rotate(matrix, tile.getDirection(), 0, 180, 90, 270);
        matrix.mulPose(Axis.ZP.rotationDegrees(180));
        float angle = tile.getAngle();
        if (tile.getActive() && partialTick > 0) {
            angle = (angle + tile.getHeightSpeedRatio() * partialTick) % 360;
        }
        return angle;
    }

}
