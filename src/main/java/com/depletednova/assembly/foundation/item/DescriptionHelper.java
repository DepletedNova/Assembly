package com.depletednova.assembly.foundation.item;

import com.depletednova.assembly.foundation.utility.BinaryHelper;

import java.util.List;

public class DescriptionHelper {
	public static String createBinaryBar(List<Boolean> bools) {
		String bar = "";
		for (Boolean bool : bools)
			bar += bool ? "\u2588" : "\u2592";
		return bar;
	}
	
	public static String createBinaryBar(byte data) {
		return createBinaryBar(BinaryHelper.writeBooleans(data));
	}
}
