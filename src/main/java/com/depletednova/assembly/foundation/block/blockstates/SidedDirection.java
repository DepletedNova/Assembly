package com.depletednova.assembly.foundation.block.blockstates;

import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;

public enum SidedDirection implements StringRepresentable {
	NORTH("north", Direction.NORTH, 0),
	EAST("east", Direction.EAST, 0),
	SOUTH("south", Direction.SOUTH, 0),
	WEST("west", Direction.WEST, 0),
	NORTH_UP("north_up", Direction.NORTH, 1),
	EAST_UP("east_up", Direction.EAST, 1),
	SOUTH_UP("south_up", Direction.SOUTH, 1),
	WEST_UP("west_up", Direction.WEST, 1),
	NORTH_DOWN("north_down", Direction.NORTH, -1),
	EAST_DOWN("east_down", Direction.EAST, -1),
	SOUTH_DOWN("south_down", Direction.SOUTH, -1),
	WEST_DOWN("west_down", Direction.WEST, -1)
	;
	
	private final String name;
	public final Direction HorizontalDirection;
	public final byte VerticalDirection;
	
	public static SidedDirection fromDirection(Direction direction, int vertical) {
		for (SidedDirection value : SidedDirection.values()) {
			if (direction.equals(value.HorizontalDirection) &&
					((byte)vertical) == value.VerticalDirection)
				return value;
		}
		return NORTH;
	}
	
	public static SidedDirection fromContext(BlockPlaceContext context) {
		Direction clickedFace = context.getClickedFace();
		return fromDirection(
				clickedFace.getAxis().isHorizontal() ? clickedFace.getOpposite() : context.getHorizontalDirection(),
				clickedFace == Direction.UP ? -1 : clickedFace == Direction.DOWN ? 1 : 0
		);
	}
	
	public static SidedDirection fromName(String name) {
		for (SidedDirection value : SidedDirection.values()) {
			if (value.getSerializedName().equals(name))
				return value;
		}
		return NORTH;
	}
	
	SidedDirection(String name, Direction Horizontal, int Vertical) {
		this.name = name;
		this.HorizontalDirection = Horizontal;
		this.VerticalDirection = (byte)Vertical;
	}
	
	public Direction toDirection() {
		return this.VerticalDirection == 0 ? this.HorizontalDirection :
				this.VerticalDirection == 1 ? Direction.UP :
						Direction.DOWN;
	}
	
	@Override
	public String getSerializedName() {
		return this.name;
	}
}
