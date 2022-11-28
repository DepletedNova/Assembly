package com.depletednova.assembly.content.logistics.instructor;

import com.depletednova.assembly.foundation.AssemblyLang;
import com.depletednova.assembly.foundation.gui.AllUITextures;
import com.depletednova.assembly.foundation.gui.widgets.GenericButtonWidget;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.gui.container.AbstractSimiContainerScreen;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

import java.util.ArrayList;
import java.util.List;

public class InstructorScreen extends AbstractSimiContainerScreen<InstructorContainer> {
	public InstructorScreen(InstructorContainer container, Inventory inv, Component title) {
		super(container, inv, title);
	}
	
	protected AllUITextures background;
	protected AllUITextures tabActive;
	protected AllUITextures tabInactive;
	protected int screenLeft;
	protected int screenTop;
	
	protected List<String> tabList;
	protected int tabIndex;
	
	protected AbstractInstructorTab currentTab;
	
	@Override
	protected void init() {
		background = AllUITextures.INSTRUCTOR;
		tabActive = AllUITextures.INSTRUCTOR_TAB_ACTIVE;
		tabInactive = AllUITextures.INSTRUCTOR_TAB_INACTIVE;
		setWindowSize(background.width - 8, background.height);
		super.init();
		screenLeft = leftPos + 3;
		screenTop = topPos + 16;
		
		resetTabs();
	}
	
	public void resetTabs() {
		tabList = new ArrayList<>();
		tabList.add(InstructorTabRegistry.INVENTORY.id);
		tabList.add(InstructorTabRegistry.TEST.id);
		updateTab(0);
	}
	
	public void updateTab(int index) {
		tabIndex = index;
		String tag = tabList.get(tabIndex);
		currentTab = InstructorTabRegistry.getRegistry(tag).instructorTab;
		for (Slot slot : this.menu.slots) {
			if (!(slot instanceof AbstractInstructorTab.InstructorSlot instructorSlot))
				continue;
			instructorSlot.setActive(instructorSlot.tab.equals(this.currentTab));
		}
		updateWidgets();
	}
	
	public void updateWidgets() {
		clearWidgets();
		// Tab widgets
		for (Widget widget : currentTab.addRenderables(screenLeft, screenTop))
			addRenderableOnly(widget);
		currentTab.addWidgets(this, screenLeft, screenTop);
		
		// Tabs
		for (int tab = 0; tab < tabList.size(); tab++) {
			boolean active = currentTab.getId().equals(tabList.get(tab));
			GenericButtonWidget tabButton = new GenericButtonWidget(screenLeft + 2 + (tabActive.width + 2) * tab, screenTop - 3, tabActive.width, tabActive.height)
					.setTooltip(List.of(AssemblyLang.translateText("instructor.tab." + tabList.get(tab))));
			if (active) {
				tabButton.withRenderCallback((ms, x, y) -> {
					tabActive.render(ms, x, y);
				});
			} else {
				final int localTabIndex = tab;
				tabButton.withRenderCallback((ms, x, y) -> {
					tabInactive.render(ms, x, y);
				}).withClickCallback(() -> {
					updateTab(localTabIndex);
				});
			}
			addRenderableWidget(tabButton);
		}
	}
	
	@Override
	protected void renderBg(PoseStack ms, float partialTicks, int mouseX, int mouseY) {
		// Background
		background.render(ms, leftPos, topPos, this);
		font.draw(ms, title, leftPos + (background.width - 8f) / 2f - font.width(title) / 2f, topPos + 3, 0x505050);
		
		// Tab
		if (currentTab == null)
			resetTabs();
		currentTab.render(ms, partialTicks, screenLeft, screenTop);
	}
	
	@Override
	public <T extends GuiEventListener & Widget & NarratableEntry> T addRenderableWidget(T widget) {
		return super.addRenderableWidget(widget);
	}
	
	@Override
	public <T extends GuiEventListener & NarratableEntry> T addWidget(T widget) {
		return super.addWidget(widget);
	}
}
