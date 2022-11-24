package com.depletednova.assembly.content.connection.patchCable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;

public class CablePlacementContext {
	public BlockPos position;
	public Direction direction;
	public boolean places;
	
	public static CablePlacementContext fromCable(UseOnContext context) {
		CablePlacementContext placement = new CablePlacementContext();
		placement.places = false;
		placement.position = context.getClickedPos();
		return placement;
	}
	
	public static CablePlacementContext fromPlace(BlockPlaceContext context) {
		CablePlacementContext placement = new CablePlacementContext();
		placement.places = true;
		placement.position = context.getClickedPos();
		placement.direction = context.getClickedFace().getOpposite();
		return placement;
	}
	
	public static void writeByteBuffer(FriendlyByteBuf buffer, CablePlacementContext context) {
		buffer.writeBlockPos(context.position);
		buffer.writeBoolean(context.places);
		if (context.places)
			buffer.writeUtf(context.direction.getSerializedName());
	}
	public static CablePlacementContext fromByteBuffer(FriendlyByteBuf buffer) {
		CablePlacementContext context = new CablePlacementContext();
		context.position = buffer.readBlockPos();
		context.places = buffer.readBoolean();
		if (context.places)
			context.direction = Direction.byName(buffer.readUtf());
		return context;
	}
}
