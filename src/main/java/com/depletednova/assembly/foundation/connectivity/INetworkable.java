package com.depletednova.assembly.foundation.connectivity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface INetworkable {
	default void invokeNetwork(CompoundTag packet, BlockPos from, boolean ping) {
		if (!(this instanceof BlockEntity tile))
			return;
		if (tile.getLevel() == null)
			return;
		for (Direction side : Direction.values()) {
			if (tile.getBlockPos().relative(side).equals(from))
				continue;
			
			NetworkHelper.withNetwork(tile.getLevel(), tile.getBlockPos(), side, (ste, network) -> {
				network.acceptNetwork(packet, tile.getBlockPos(), ping);
			});
		}
	}
	void acceptNetwork(CompoundTag packet, BlockPos from, boolean ping);
}
