package com.depletednova.assembly.content.logistics.instructor;

import com.depletednova.assembly.registry.ATileEntities;
import com.simibubi.create.content.contraptions.base.HorizontalKineticBlock;
import com.simibubi.create.foundation.block.ITE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.network.NetworkHooks;

public class InstructorBlock extends HorizontalKineticBlock implements ITE<InstructorTileEntity> {
	public InstructorBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (player == null)
			return InteractionResult.PASS;
		if (player.isSteppingCarefully())
			return InteractionResult.PASS;
		
		if (player instanceof FakePlayer)
			return InteractionResult.PASS;
		if (level.isClientSide)
			return InteractionResult.SUCCESS;
		
		withTileEntityDo(level, pos,
				instructor -> NetworkHooks.openGui((ServerPlayer) player, instructor, instructor::sendToContainer));
		
		return InteractionResult.SUCCESS;
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Direction preferred = getPreferredHorizontalFacing(context);
		if (preferred != null)
			return this.defaultBlockState().setValue(HORIZONTAL_FACING, preferred.getOpposite());
		return this.defaultBlockState().setValue(HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
	}
	
	@Override
	public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
		return face == state.getValue(HORIZONTAL_FACING).getOpposite();
	}
	
	@Override
	public Direction.Axis getRotationAxis(BlockState state) {
		return state.getValue(HORIZONTAL_FACING).getAxis();
	}
	
	@Override
	public boolean isPathfindable(BlockState state, BlockGetter reader, BlockPos pos, PathComputationType type) {
		return false;
	}
	
	@Override
	public Class<InstructorTileEntity> getTileEntityClass() {
		return InstructorTileEntity.class;
	}
	
	@Override
	public BlockEntityType<? extends InstructorTileEntity> getTileEntityType() {
		return ATileEntities.INSTRUCTOR.get();
	}
}
