package com.depletednova.assembly.registry;

import com.depletednova.assembly.foundation.block.blockstates.SidedDirection;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class ABlockProperties {
	public static final EnumProperty<SidedDirection> SIDED_DIRECTION = EnumProperty.create("sided_direction", SidedDirection.class);
}
