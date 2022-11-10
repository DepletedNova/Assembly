package com.depletednova.assembly.foundation.utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BinaryHelper {
	public static final byte EMPTY = 0b0;
	
	public static byte fromBoolean(List<Boolean> ar) {
		byte b = EMPTY;
		if (ar.size() > 8) return EMPTY;
		for (Boolean bool : ar) {
			b = (byte) ((b << 1) | (bool ? 1 : 0));
		}
		return b;
	}
	
	public static List<Boolean> writeBooleans(byte b) {
		List<Boolean> ar = new ArrayList<>();
		for (int x = 0; x < 8; x++) {
			ar.add((b & 1) != 0);
			b >>>= 1;
		}
		Collections.reverse(ar);
		return ar;
	}
}
