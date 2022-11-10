package com.depletednova.assembly.content.connection.patchCable;

import com.depletednova.assembly.AssemblyClient;
import com.depletednova.assembly.registry.ABlockProperties;
import com.depletednova.assembly.registry.ABlocks;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class CableItem extends BlockItem {
	public CableItem(Block block, Properties properties) {
		super(block, properties);
	}
	
	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		Player player = context.getPlayer();
		
		if (player == null || !player.mayBuild())
			return InteractionResult.PASS;
		
		if (level.isClientSide && AssemblyClient.CABLE_HANDLER.useOn(context))
			return InteractionResult.SUCCESS;
		
		return InteractionResult.FAIL;
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		
		if (!player.mayBuild())
			return InteractionResultHolder.pass(stack);
		
		if (level.isClientSide && AssemblyClient.CABLE_HANDLER.use(level, player, hand))
			return InteractionResultHolder.success(stack);
		
		return InteractionResultHolder.fail(stack);
	}
	
	public static void placeWires(CablePlacementContext firstContext, CablePlacementContext secondContext, Level level, Player player) {
		InteractionHand hand = getHandWithCable(player);
		if (hand == null) return;
		ItemStack stack = player.getItemInHand(hand);
		
		// Place first
		boolean instabuild = player.getAbilities().instabuild;
		if (firstContext.places && (stack.getCount() > 0 || instabuild) && !place(firstContext, level, player))
			return;
		
		// Place second
		if (secondContext.places && (stack.getCount() > (firstContext.places ? 1 : 0) || instabuild) && !place(secondContext, level, player)) {
			if (firstContext.places && !instabuild)
				stack.shrink(1);
			return;
		}
		
		// Item deduction
		if (!instabuild) {
			stack.shrink(firstContext.places && secondContext.places ? 2 :
					firstContext.places || secondContext.places ? 1 : 0);
		}
		
		// Safe cable linking
		CableTileEntity.linkCables(level, firstContext.position, secondContext.position);
	}
	
	public static boolean place(CablePlacementContext context, Level level, Player player) {
		BlockState state = ABlocks.PATCH_CABLE.getDefaultState().setValue(ABlockProperties.SIDED_DIRECTION, context.direction);
		if (!level.setBlock(context.position, state, 11))
			return false;
		
		level.gameEvent(player, GameEvent.BLOCK_PLACE, context.position);
		SoundType soundtype = state.getSoundType(level, context.position, player);
		level.playSound(player, context.position, soundtype.getPlaceSound(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
		return true;
	}
	
	public static InteractionHand getHandWithCable(Player player) {
		return player.getMainHandItem().getItem() instanceof CableItem ? InteractionHand.MAIN_HAND :
				player.getOffhandItem().getItem() instanceof CableItem ? InteractionHand.OFF_HAND :
						null;
	}
}
