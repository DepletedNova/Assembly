package com.depletednova.assembly.content.connection;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;

public class NetworkPacket<c extends BlockEntity> {
	public byte port;
	public c from;
	
	public CompoundTag data = new CompoundTag();
}