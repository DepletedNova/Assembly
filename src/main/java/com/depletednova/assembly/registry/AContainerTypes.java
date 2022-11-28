package com.depletednova.assembly.registry;

import com.depletednova.assembly.Assembly;
import com.depletednova.assembly.content.logistics.instructor.InstructorContainer;
import com.depletednova.assembly.content.logistics.instructor.InstructorScreen;
import com.tterrag.registrate.builders.MenuBuilder;
import com.tterrag.registrate.util.entry.MenuEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class AContainerTypes {
	public static final MenuEntry<InstructorContainer> INSTRUCTOR =
			register("instructor", InstructorContainer::new, () -> InstructorScreen::new);
	
	private static <C extends AbstractContainerMenu, S extends Screen & MenuAccess<C>> MenuEntry<C> register(
			String name, MenuBuilder.ForgeMenuFactory<C> factory, NonNullSupplier<MenuBuilder.ScreenFactory<C, S>> screenFactory) {
		return Assembly.getRegistrate()
				.menu(name, factory, screenFactory)
				.register();
	}
	
	public static void register() {}
}
