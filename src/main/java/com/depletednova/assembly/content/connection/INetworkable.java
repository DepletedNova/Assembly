package com.depletednova.assembly.content.connection;

import com.depletednova.assembly.registry.ACapabilities;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Optional;
import java.util.function.BiConsumer;

public interface INetworkable {
	default void invokeNetwork(NetworkPacket<? extends BlockEntity> packet, BlockPos from) {
		if (!(this instanceof BlockEntity tile))
			return;
		if (tile.getLevel() == null)
			return;
		for (Direction side : Direction.values()) {
			if (tile.getBlockPos().relative(side).equals(from))
				continue;
			withNetwork(tile.getLevel(), tile.getBlockPos().relative(side), side.getOpposite(), (ste, network) -> {
				network.acceptNetwork(packet, tile.getBlockPos());
			});
		}
	}
	default void acceptNetwork(NetworkPacket<? extends BlockEntity> packet, BlockPos from) { }
	
	static void withNetwork(Level level, BlockPos pos, Direction from, BiConsumer<SmartTileEntity, INetworkable> consumer) {
		if (level == null)
			return;
		if (!(level.getExistingBlockEntity(pos) instanceof SmartTileEntity tile))
			return;
		Optional<INetworkable> optionalNet = tile.getCapability(ACapabilities.NETWORK, from).resolve();
		optionalNet.ifPresent(network -> consumer.accept(tile, network));
	}
}
