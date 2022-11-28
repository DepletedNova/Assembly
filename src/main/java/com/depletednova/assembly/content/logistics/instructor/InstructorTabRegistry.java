package com.depletednova.assembly.content.logistics.instructor;

import com.depletednova.assembly.Assembly;
import com.simibubi.create.foundation.data.CreateRegistrate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class InstructorTabRegistry {
	public static final List<TabRegistry<? extends AbstractInstructorTab>> registries = new ArrayList<>();
	public static final TabRegistry<InventoryInstructorTab> INVENTORY = new TabRegistry<>(new InventoryInstructorTab(), InventoryInstructorTab::addSlots);
	public static final TabRegistry<TestInstructorTab> TEST = new TabRegistry<>(new TestInstructorTab(), AbstractInstructorTab::noSlots);
	
	public static void register() {}
	
	public static TabRegistry<?> getRegistry(String tag) {
		for (TabRegistry<? extends AbstractInstructorTab> tab : registries) {
			if (tab.id.equals(tag)) {
				return tab;
			}
		}
		return INVENTORY;
	}
	
	public static class TabRegistry<T extends AbstractInstructorTab> {
		public TabRegistry(T tab, BiConsumer<InstructorContainer, T> slotFactory) {
			this.id = tab.getId();
			this.instructorTab = tab;
			this.slotFactory = slotFactory;
			registries.add(this);
		}
		
		public final String id;
		public final T instructorTab;
		public final BiConsumer<InstructorContainer, T> slotFactory;
		
		public void acceptSlots(InstructorContainer container) {
			slotFactory.accept(container, instructorTab);
		}
	}
}
