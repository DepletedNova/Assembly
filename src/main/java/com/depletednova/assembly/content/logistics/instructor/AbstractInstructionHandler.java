package com.depletednova.assembly.content.logistics.instructor;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.item.Item;

import java.util.List;

public abstract class AbstractInstructionHandler {
	public abstract void init(InstructorScreen screen);
	
	public abstract List<? extends AbstractInstructorTab> getTabs();
	
	public void render(PoseStack ms, float leftPos, float topPos) {}
}
