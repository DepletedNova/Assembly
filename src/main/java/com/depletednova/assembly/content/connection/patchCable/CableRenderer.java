package com.depletednova.assembly.content.connection.patchCable;

import com.depletednova.assembly.registry.ABlockPartials;
import com.jozufozu.flywheel.core.PartialModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import com.simibubi.create.foundation.tileEntity.renderer.SafeTileEntityRenderer;
import com.simibubi.create.foundation.utility.AngleHelper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;

public class CableRenderer extends SafeTileEntityRenderer<CableTileEntity> {
	public CableRenderer(BlockEntityRendererProvider.Context context) { }
	
	private float cogRotation = 0f;
	@Override
	protected void renderSafe(CableTileEntity te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
		// Render Cog
		VertexConsumer vb = buffer.getBuffer(RenderType.solid());
		BlockState state = te.getBlockState();
		Direction facing = state.getValue(DirectionalBlock.FACING);
		transformed(ABlockPartials.CONNECTOR_COG, state, facing)
				.translate(0d, 0d, 12d/16d)
				.rotateZ(Mth.lerp(partialTicks, cogRotation, cogRotation+=0.25f))
				.unCentre()
				.light(light).renderInto(ms, vb);
	}
	
	private SuperByteBuffer transformed(PartialModel model, BlockState blockState, Direction facing) {
		return CachedBufferer.partial(model, blockState)
				.centre()
				.rotateY(AngleHelper.horizontalAngle(facing))
				.rotateX(AngleHelper.verticalAngle(facing));
	}
}
