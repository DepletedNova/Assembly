package com.depletednova.assembly.content.connection.patchCable;

import com.depletednova.assembly.content.logistics.punchCard.PunchCardHelper;
import com.depletednova.assembly.registry.AItems;
import com.depletednova.assembly.registry.AShapes;
import com.depletednova.assembly.registry.ATileEntities;
import com.simibubi.create.content.contraptions.wrench.IWrenchable;
import com.simibubi.create.foundation.block.ITE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;

@ParametersAreNonnullByDefault
public class CableBlock extends DirectionalBlock implements ITE<CableTileEntity>, IWrenchable {
	public CableBlock(Properties properties) {
		super(properties);
		registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
		stateBuilder.add(FACING);
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
	public @NotNull VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
		Direction dir = state.getValue(FACING);
		VoxelShape shape = AShapes.CABLE_CONNECTOR.get(dir.getAxis().isHorizontal() ? dir : dir.getOpposite());
		return shape != null ? shape : AShapes.CABLE_CONNECTOR.get(Direction.DOWN);
	}
	
	@Override
	public void onRemove(BlockState state, @NotNull Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!state.hasBlockEntity() || state.getBlock() == newState.getBlock())
			return;
		withTileEntityDo(level, pos, te -> {
			for (int i = 0; i < te.connectedCables.size(); i++)
				CableTileEntity.unlinkCables(level, pos, te.connectedCables.get(i));
		});
		level.removeBlockEntity(pos);
	}
	
	// Port
	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		ItemStack stack = player.getItemInHand(hand);
		Optional<CableTileEntity> ote = getTileEntityOptional(level, pos);
		if (ote.isPresent() && (stack.isEmpty() || stack.is(AItems.BYTE_CARD.get()))) {
			ote.get().port = PunchCardHelper.getRowRaw(stack, 1);
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}
