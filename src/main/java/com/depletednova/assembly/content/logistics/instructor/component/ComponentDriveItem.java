package com.depletednova.assembly.content.logistics.instructor.component;

import com.depletednova.assembly.content.logistics.instructor.AbstractInstructionHandler;
import com.depletednova.assembly.content.logistics.instructor.IInstructiveItem;
import com.jozufozu.flywheel.util.NonNullSupplier;
import net.minecraft.world.item.Item;

public class ComponentDriveItem extends Item implements IInstructiveItem {
	public ComponentDriveItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public NonNullSupplier<? extends AbstractInstructionHandler> getHandler() {
		return null;
	}
}
