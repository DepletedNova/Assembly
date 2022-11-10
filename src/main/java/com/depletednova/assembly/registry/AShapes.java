package com.depletednova.assembly.registry;

import com.simibubi.create.AllShapes.Builder;
import com.simibubi.create.foundation.utility.VoxelShaper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;

import static net.minecraft.core.Direction.NORTH;

public class AShapes {
	public static final VoxelShaper
			CABLE_CONNECTOR = shape(5.0D, 5.0D, 0.0D, 11.0D, 11.0D, 7.0D)
			.forDirectional(NORTH);
	
	private static Builder shape(VoxelShape shape) {
		return new Builder(shape);
	}
	
	private static Builder shape(double x1, double y1, double z1, double x2, double y2, double z2) {
		return shape(cuboid(x1, y1, z1, x2, y2, z2));
	}
	
	private static VoxelShape cuboid(double x1, double y1, double z1, double x2, double y2, double z2) {
		return Block.box(x1, y1, z1, x2, y2, z2);
	}
}
