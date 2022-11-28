package com.depletednova.assembly.content.logistics.instructor;

import com.depletednova.assembly.foundation.gui.AllUITextures;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.world.inventory.Slot;

import java.util.Collections;
import java.util.List;

public class InventoryInstructorTab extends AbstractInstructorTab {
	@Override
	public String getId() {
		return "inventory";
	}
	
	@Override
	public void render(PoseStack ms, float partialTicks, int x, int y) {
		AllUITextures.INSTRUCTOR_INVENTORY.render(ms, x + 6, y);
	}
	
	public static void addSlots(InstructorContainer container, InventoryInstructorTab tab) {
		for (int hotbarSlot = 0; hotbarSlot < 9; ++hotbarSlot) {
			container.addSlot(new InstructorSlot(tab, container.playerInventory, hotbarSlot, hotbarSlot * 18 + 19, 83));
		}
		for (int row = 0; row < 3; ++row) {
			for (int col = 0; col < 9; ++col) {
				container.addSlot(new InstructorSlot(tab, container.playerInventory, col + row * 9 + 9, col * 18 + 19, row * 18 + 24));
			}
		}
	}
}
