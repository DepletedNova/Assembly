package com.depletednova.assembly.content.logistics.punchCard;

import com.depletednova.assembly.foundation.utility.BinaryHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.TntBlock;

import java.util.Arrays;
import java.util.List;

public class PunchCardHelper {
	public static List<Boolean> generateRow() {
		Boolean[] row = new Boolean[8];
		Arrays.fill(row, false);
		return List.of(row);
	}
	
	public static ItemStack setRow(ItemStack stack, int rowNum, List<Boolean> row) {
		return setRowRaw(stack, rowNum, BinaryHelper.fromBoolean(row));
	}
	public static List<Boolean> getRow(ItemStack stack, int rowNum) {
		return BinaryHelper.writeBooleans(getRowRaw(stack, rowNum));
	}
	
	public static ItemStack setRowRaw(ItemStack stack, int rowNum, byte row) {
		stack.getOrCreateTag().putByte("PunchRow" + rowNum, row);
		return stack;
	}
	
	public static byte getRowRaw(ItemStack stack, int rowNum) {
		return stack.getOrCreateTag().getByte("PunchRow" + rowNum);
	}
}
