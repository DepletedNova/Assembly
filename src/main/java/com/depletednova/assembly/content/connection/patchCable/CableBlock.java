package com.depletednova.assembly.content.connection.patchCable;

import com.depletednova.assembly.content.base.SidedDirectionBlock;
import com.depletednova.assembly.registry.AShapes;
import com.depletednova.assembly.registry.ATileEntities;
import com.simibubi.create.content.contraptions.wrench.IWrenchable;
import com.simibubi.create.foundation.block.ITE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CableBlock extends SidedDirectionBlock implements ITE<CableTileEntity>, IWrenchable {
	public CableBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	public Class<CableTileEntity> getTileEntityClass() {
		return CableTileEntity.class;
	}
	
	@Override
	public BlockEntityType<? extends CableTileEntity> getTileEntityType() {
		return ATileEntities.PATCH_CABLE.get();
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
		Direction dir = state.getValue(SIDED_DIRECTION).toDirection();
		VoxelShape shape = AShapes.CABLE_CONNECTOR.get(dir.getAxis().isHorizontal() ? dir : dir.getOpposite());
		return shape != null ? shape : AShapes.CABLE_CONNECTOR.get(Direction.DOWN);
	}
	
	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!state.hasBlockEntity() || state.getBlock() == newState.getBlock())
			return;
		withTileEntityDo(level, pos, te -> {
			for (int i = 0; i < te.connectedCables.size(); i++)
				CableTileEntity.unlinkCables(level, pos, te.connectedCables.get(i));
		});
		level.removeBlockEntity(pos);
	}
	
	private final List<Object> lines = new ArrayList<>();
	@Override
	@ParametersAreNonnullByDefault
	public void animateTick(BlockState state, Level level, BlockPos pos, Random random) {
		if (!level.isClientSide)
			return;
		withTileEntityDo(level, pos, te ->  {
//			for (int c = 0; c < te.connectedCables.size(); c++) {
//				if (lines.get(c) == null)
//					lines.set(c, new Object());
//				BlockPos newPos = te.connectedCables.get(c);
//				if (newPos == null)
//					continue;
//				CreateClient.OUTLINER.showLine(lines.get(c), Vec3.atCenterOf(pos), Vec3.atCenterOf(newPos));
//			}
		});
	}
}
