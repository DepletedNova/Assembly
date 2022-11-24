package com.depletednova.assembly.content.connection.patchCable;

import com.depletednova.assembly.foundation.AssemblyLang;
import com.depletednova.assembly.foundation.connectivity.INetworkable;
import com.depletednova.assembly.foundation.connectivity.NetworkHelper;
import com.depletednova.assembly.foundation.item.DescriptionHelper;
import com.depletednova.assembly.foundation.utility.NBTUtility;
import com.depletednova.assembly.registry.ABlocks;
import com.depletednova.assembly.registry.ACapabilities;
import com.simibubi.create.content.contraptions.goggles.IHaveHoveringInformation;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.ChatFormatting.GRAY;
import static net.minecraft.ChatFormatting.YELLOW;

public class CableTileEntity extends SmartTileEntity implements INetworkable, IHaveHoveringInformation {
	public CableTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
		connectedCables = new ArrayList<>();
		port = 0;
		capability = LazyOptional.of(() -> this);
	}
	
	// Variables
	public List<BlockPos> connectedCables;
	protected byte port;
	
	// Behaviors
	@Override
	public void addBehaviours(List<TileEntityBehaviour> behaviours) {
	
	}
	
	// Cable Network
	@Override
	public void acceptNetwork(CompoundTag packet, BlockPos from, boolean ping) {
		if (this.level == null)
			return;
		
		// Update packet if necessary
		if (packet.getByte("Port") == (byte) 0 && !level.getBlockState(from).is(ABlocks.PATCH_CABLE.get()))
			packet.putByte("Port", port);
		
		// Direct signal
		if (packet.getByte("Port") == this.port) {
			Direction direction = this.getBlockState().getValue(DirectionalBlock.FACING);
			if (!this.getBlockPos().relative(direction).equals(from)) {
				NetworkHelper.withNetwork(this.level, this.getBlockPos(), direction, (ste, network) -> {
					network.acceptNetwork(packet, this.getBlockPos(), ping);
				});
			}
		}
		
		// Continue signal
		this.invokeNetwork(packet, from, ping);
	}
	
	@Override
	public void invokeNetwork(CompoundTag packet, BlockPos from, boolean ping) {
		if (this.level == null)
			return;
		for (BlockPos to : connectedCables) {
			if (to == null || to.equals(from))
				continue;
			
			BlockEntity entity = this.level.getExistingBlockEntity(to);
			if (!(entity instanceof CableTileEntity cable))
				continue;
			
			cable.acceptNetwork(packet, this.getBlockPos(), ping);
		}
	}
	
	// Capability
	private final LazyOptional<INetworkable> capability;
	
	@NotNull
	@Override
	public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		BlockState state = this.getBlockState();
		Direction dir = state.getValue(DirectionalBlock.FACING);
		if ((side == null || side.equals(dir)) && cap.equals(ACapabilities.NETWORK))
			return this.capability.cast();
		
		return super.getCapability(cap, side);
	}
	
	// Data
	@Override
	protected void read(CompoundTag tag, boolean clientPacket) {
		super.read(tag, clientPacket);
		port = tag.getByte("Port");
		// Cable positions
		connectedCables.clear();
		if (!tag.hasUUID("Cables") || !(tag.get("Cables") instanceof ListTag cables))
			return;
		for (int i = 0; i < getMaxConnections(); i++) {
			if (!NBTUtility.hasBlockpos("Cable_" + i, tag))
				continue;
			connectedCables.add(NBTUtility.readBlockpos("Cable_" + i, tag));
		}
	}
	
	@Override
	protected void write(CompoundTag tag, boolean clientPacket) {
		super.write(tag, clientPacket);
		tag.putByte("Port", port);
		// Cable positions
		for (int i = 0; i < connectedCables.size(); i++)
			NBTUtility.putBlockpos("Cable_" + i, connectedCables.get(i), tag);
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
	
	// Port
	@Override
	public boolean addToTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
		AssemblyLang.translate("tooltip.cable")
				.style(GRAY)
				.forGoggles(tooltip);
		
		AssemblyLang.lang()
				.text(DescriptionHelper.createBinaryBar(port) + " ")
				.style(YELLOW)
				.forGoggles(tooltip, -1);
		
		return true;
	}
}
