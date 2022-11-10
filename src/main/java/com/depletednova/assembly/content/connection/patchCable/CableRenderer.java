package com.depletednova.assembly.content.connection.patchCable;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.tileEntity.renderer.SafeTileEntityRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

import java.util.ArrayList;
import java.util.List;

public class CableRenderer extends SafeTileEntityRenderer<CableTileEntity> {
	List<Object> lines = new ArrayList<>();
	
	public CableRenderer(BlockEntityRendererProvider.Context context) {
	
	}
	
	@Override
	protected void renderSafe(CableTileEntity te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
//		for (int i = 0; i < te.transmissionBehavior.connectedCables.size(); i++) {
//			BlockPos origin = te.getBlockPos();
//			BlockPos towards = te.transmissionBehavior.connectedCables.get(i);
//			if (towards == null)
//				continue;
//			if (lines.get(i) == null)
//				lines.add(new Object());
//			CreateClient.OUTLINER.showLine(lines.get(i), Vec3.atCenterOf(origin), Vec3.atCenterOf(towards));
//		}
	}
}
