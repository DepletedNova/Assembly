package com.depletednova.assembly.registry;

import com.depletednova.assembly.Assembly;
import com.jozufozu.flywheel.core.PartialModel;

public class ABlockPartials {
	// Patch Cable
	public static final PartialModel
		CONNECTOR_COG = block("patch_cable/cog");
	
	private static PartialModel block(String path) {
		return new PartialModel(Assembly.getResource("block/" + path));
	}
	
	public static void register() { }
}
