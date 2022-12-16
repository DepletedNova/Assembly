package com.depletednova.assembly.registry;

import com.depletednova.assembly.Assembly;
import com.depletednova.assembly.content.logistics.instructor.component.ComponentDriveColor;
import com.depletednova.assembly.content.logistics.instructor.component.ComponentDriveItem;
import com.depletednova.assembly.content.logistics.punchCard.PunchCardItem;
import com.depletednova.assembly.foundation.gui.PunchCardTheme;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;

import static com.simibubi.create.content.AllSections.*;

public class AItems {
	private static final CreateRegistrate REGISTRATE = Assembly.getRegistrate()
			.creativeModeTab(() -> Assembly.BASE_TAB);
	
	static { REGISTRATE.startSection(CURIOSITIES); }
	
	public static final ItemEntry<Item> HOLE_PUNCHER = REGISTRATE.item("hole_puncher", Item::new)
			.properties(p -> p.stacksTo(1))
			.model(AssetLookup.existingItemModel())
			.register();
	
	// programmers eye (Create: Assembly goggles variant)
	
	static { REGISTRATE.startSection(LOGISTICS); }
	
	public static final ItemEntry<PunchCardItem> BYTE_CARD = REGISTRATE.item("byte_card",
					p -> new PunchCardItem(p, 1, PunchCardTheme.BLUE))
			.properties(p -> p.stacksTo(1))
			.defaultModel()
			.register();
	
	public static final ItemEntry<ComponentDriveItem> COMPONENT_DRIVE = REGISTRATE.item("component_drive", ComponentDriveItem::new)
			.properties(p -> p.stacksTo(1))
			.color(() -> ComponentDriveColor::new)
			.model(AssetLookup.existingItemModel())
			.register();
	
	static { REGISTRATE.startSection(MATERIALS); }
	
	public static final ItemEntry<Item> BASIC_COMPONENT = REGISTRATE.item("basic_component", Item::new)
			.properties(p -> p.stacksTo(16))
			.defaultModel()
			.register();
	
	public static final ItemEntry<Item> ADVANCED_COMPONENT = REGISTRATE.item("advanced_component", Item::new)
			.properties(p -> p.stacksTo(16))
			.defaultModel()
			.register();
	
	// Static loading
	public static void register() {}
}
