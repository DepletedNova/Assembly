package com.depletednova.assembly.content.connection.patchCable;

import com.depletednova.assembly.content.connection.INetworkable;
import com.depletednova.assembly.content.connection.NetworkPacket;
import com.depletednova.assembly.content.programmer.punchCard.PunchCardHelper;
import com.depletednova.assembly.foundation.utility.nbt.NBTUtility;
import com.depletednova.assembly.registry.ABlockProperties;
import com.depletednova.assembly.registry.ACapabilities;
import com.depletednova.assembly.registry.AItems;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CableTileEntity extends SmartTileEntity implements INetworkable {
	public CableTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
		connectedCables = new ArrayList<>();
		storedItem = ItemStack.EMPTY;
		port = 0;
		capability = LazyOptional.of(() -> this);
	}
	
	// Variables
	public List<BlockPos> connectedCables;
	public ItemStack storedItem;
	private byte port;
	
	// Behavior
	@Override
	public void addBehaviours(List<TileEntityBehaviour> behaviours) {
	
	}
	
	// Cable Network
	@Override
	public void acceptNetwork(NetworkPacket<? extends BlockEntity> packet, BlockPos from) {
		if (this.level == null)
			return;
		// Direct signal
		if (packet.port == this.port) {
			Direction direction = this.getBlockState().getValue(ABlockProperties.SIDED_DIRECTION).toDirection();
			INetworkable.withNetwork(this.level, this.getBlockPos(), direction, (ste, network) -> {
				if (this.getBlockPos().relative(direction) != from)
					network.acceptNetwork(packet, this.getBlockPos());
			});
		}
		// Continue signal
		this.invokeNetwork(packet, from);
	}
	
	@Override
	public void invokeNetwork(NetworkPacket<? extends BlockEntity> packet, BlockPos from) {
		if (this.level == null)
			return;
		for (BlockPos to : connectedCables) {
			if (to == null || to.equals(from))
				continue;
			
			BlockEntity entity = this.level.getExistingBlockEntity(to);
			if (!(entity instanceof CableTileEntity cable))
				continue;
			cable.acceptNetwork(packet, this.getBlockPos());
		}
	}
	
	// Capability
	private final LazyOptional<INetworkable> capability;
	
	@NotNull
	@Override
	public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		BlockState state = this.getBlockState();
		Direction dir = state.getValue(ABlockProperties.SIDED_DIRECTION).toDirection();
		if ((side == null || side.equals(dir)) && cap.equals(ACapabilities.NETWORK))
			return this.capability.cast();
		
		return super.getCapability(cap, side);
	}
	
	// Data
	@Override
	protected void read(CompoundTag tag, boolean clientPacket) {
		super.read(tag, clientPacket);
		port = tag.getByte("Port");
		// Generate item
		boolean hasItem = tag.getBoolean("Item");
		if (hasItem) {
			storedItem = AItems.BYTE_CARD.asStack();
			PunchCardHelper.setRowRaw(storedItem, 1, port);
		}
		// Input cable positions
		connectedCables.clear();
		for (int i = 0; i < getMaxConnections(); i++) {
			if (!NBTUtility.hasBlockpos("Cable_" + i, tag))
				continue;
			connectedCables.set(i, NBTUtility.readBlockpos("Cable_" + i, tag));
		}
	}
	
	@Override
	protected void write(CompoundTag tag, boolean clientPacket) {
		super.write(tag, clientPacket);
		tag.putByte("Port", port);
		tag.putBoolean("Item", storedItem != null);
		for (int i = 0; i < connectedCables.size(); i++) {
			BlockPos newPos = connectedCables.get(i);
			if (newPos == null)
				continue;
			NBTUtility.putBlockpos("Cable_" + i, newPos, tag);
		}
	}
	
	// Routing
	public void updateConnections() {
		this.notifyUpdate();
		// todo update curves
	}
	public void addConnection(BlockPos pos) {
		if (pos == null || connectedCables.contains(pos) || connectedCables.size() >= getMaxConnections())
			return;
		connectedCables.add(pos);
		this.updateConnections();
	}
	public void removeConnection(BlockPos pos) {
		if (pos == null || !connectedCables.contains(pos))
			return;
		connectedCables.remove(pos);
		this.updateConnections();
	}
	
	// Static
	public static void linkCables(Level level, BlockPos posA, BlockPos posB) {
		BlockEntity firstEntity = level.getExistingBlockEntity(posA);
		BlockEntity secondEntity = level.getExistingBlockEntity(posB);
		if (!(firstEntity instanceof CableTileEntity c1) || !(secondEntity instanceof CableTileEntity c2))
			return;
		linkCables(c1, c2);
	}
	public static void unlinkCables(Level level, BlockPos posA, BlockPos posB) {
		if (posA == null || posB == null)
			return;
		BlockEntity firstEntity = level.getExistingBlockEntity(posA);
		BlockEntity secondEntity = level.getExistingBlockEntity(posB);
		if (!(firstEntity instanceof CableTileEntity c1) || !(secondEntity instanceof CableTileEntity c2))
			return;
		unlinkCables(c1, c2);
	}
	public static void linkCables(CableTileEntity cableA, CableTileEntity cableB) {
		cableA.addConnection(cableB.getBlockPos());
		cableB.addConnection(cableA.getBlockPos());
	}
	
	public static void unlinkCables(CableTileEntity cableA, CableTileEntity cableB) {
		cableA.removeConnection(cableB.getBlockPos());
		cableB.removeConnection(cableA.getBlockPos());
	}
	
	// Settings
	public int getMaxConnections() {
		return 8;
	}
}
