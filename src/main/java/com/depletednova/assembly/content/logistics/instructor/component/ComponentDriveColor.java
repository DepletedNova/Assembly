package com.depletednova.assembly.content.logistics.instructor.component;

import com.jozufozu.flywheel.util.Color;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

public class ComponentDriveColor implements ItemColor {
	@Override
	public int getColor(ItemStack stack, int layer) {
		return layer == 0 ? 0xFFFFFF : Color.mixColors(0xCC4039, 0x1C1C1D,
				(Mth.sin((float) (AnimationTickHolder.getRenderTime() / 10f + Math.PI) * 1.15f) + 1) / 2);
	}
}
