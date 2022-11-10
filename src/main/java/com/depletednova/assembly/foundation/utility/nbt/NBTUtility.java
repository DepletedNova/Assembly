package com.depletednova.assembly.foundation.utility.nbt;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

public class NBTUtility {
	// Write
	public static void putBlockpos(String key, BlockPos pos, CompoundTag nbt) {
		nbt.putInt(key + "X", pos.getX());
		nbt.putInt(key + "Y", pos.getY());
		nbt.putInt(key + "Z", pos.getZ());
	}
	
	// Read
	public static BlockPos readBlockpos(String key, CompoundTag nbt) {
		if (!nbt.hasUUID(key + "X"))
			return null;
		return new BlockPos(nbt.getInt(key+"X"), nbt.getInt(key+"Y"), nbt.getInt(key+"Z"));
	}
	
	// Check
	public static boolean hasBlockpos(String key, CompoundTag nbt) {
		return nbt.hasUUID(key + "X");
	}
}
