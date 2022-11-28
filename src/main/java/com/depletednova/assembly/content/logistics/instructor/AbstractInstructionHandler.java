package com.depletednova.assembly.content.logistics.instructor;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.item.Item;

import java.util.List;

public abstract class AbstractInstructionHandler {
	public abstract Item getInstructionItem();
	
	public abstract void init(InstructorScreen screen);
	
	public void renderExtra(PoseStack ms, float leftPos, float topPos) {}
}
