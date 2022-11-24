package com.depletednova.assembly.foundation.connectivity;

import com.depletednova.assembly.registry.ACapabilities;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.Level;

import java.util.Optional;
import java.util.function.BiConsumer;

public class NetworkHelper {
	public static CompoundTag createPacket(BlockPos origin, byte port) {
		CompoundTag tag = new CompoundTag();
		NbtUtils.writeBlockPos(origin);
		tag.putByte("Port", port);
		return tag;
	}
	public static CompoundTag createPacket(BlockPos origin) {
		return createPacket(origin, (byte) 0);
	}
	
	public static void withNetwork(Level level, BlockPos pos, Direction from, BiConsumer<SmartTileEntity, INetworkable> consumer) {
		if (level == null)
			return;
		
		if (!(level.getExistingBlockEntity(pos.relative(from)) instanceof SmartTileEntity tile))
			return;
		
		Optional<INetworkable> optionalNet = tile.getCapability(ACapabilities.NETWORK, from.getOpposite()).resolve();
		optionalNet.ifPresent(network -> consumer.accept(tile, network));
	}
}
