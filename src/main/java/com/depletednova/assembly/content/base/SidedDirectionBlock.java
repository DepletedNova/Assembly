package com.depletednova.assembly.content.base;

import com.depletednova.assembly.foundation.block.blockstates.SidedDirection;
import com.depletednova.assembly.registry.ABlockProperties;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.Nullable;

public abstract class SidedDirectionBlock extends Block {
	protected static final EnumProperty<SidedDirection> SIDED_DIRECTION = ABlockProperties.SIDED_DIRECTION;
	
	public SidedDirectionBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState()
				.setValue(SIDED_DIRECTION, SidedDirection.NORTH));
	}
	
	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(SIDED_DIRECTION, SidedDirection.fromContext(context));
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(SIDED_DIRECTION);
	}
}
