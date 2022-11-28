package com.depletednova.assembly.content.logistics.instructor;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;

import java.util.List;

public abstract class AbstractInstructorTab {
	public abstract String getId();
	public abstract void render(PoseStack ms, float partialTicks, int x, int y);
	public List<? extends Widget> addRenderables(int x, int y) { return List.of(); }
	public void addWidgets(InstructorScreen screen, int x, int y) {}
	
	public static void noSlots(InstructorContainer container, AbstractInstructorTab tab) { }
	
	public static class InstructorSlot extends Slot {
		public InstructorSlot(AbstractInstructorTab tab, Container container, int index, int x, int y) {
			super(container, index, x, y);
			this.tab = tab;
			active = false;
		}
		
		public final AbstractInstructorTab tab;
		
		private boolean active;
		public boolean setActive(boolean x) {
			this.active = x;
			return x;
		}
		
		@Override
		public boolean isActive() {
			return active;
		}
	}
}
