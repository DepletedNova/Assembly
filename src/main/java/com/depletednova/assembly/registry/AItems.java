package com.depletednova.assembly.registry;

import com.depletednova.assembly.Assembly;
import com.depletednova.assembly.content.programmer.punchCard.PunchCardItem;
import com.depletednova.assembly.foundation.gui.PunchCardTheme;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;

import static com.simibubi.create.content.AllSections.CURIOSITIES;
import static com.simibubi.create.content.AllSections.LOGISTICS;

public class AItems {
	private static final CreateRegistrate REGISTRATE = Assembly.getRegistrate()
			.creativeModeTab(() -> Assembly.BASE_TAB);
	
	// Curiosities
	static { REGISTRATE.startSection(CURIOSITIES); }
	
	public static final ItemEntry<Item> HOLE_PUNCHER = REGISTRATE.item("hole_puncher", Item::new)
			.properties(p -> p.stacksTo(1))
			.register();
	
	// programmers eye (Create: Assembly goggles variant)
	
	// Logistics
	static { REGISTRATE.startSection(LOGISTICS); }
	
	public static final ItemEntry<PunchCardItem> BYTE_CARD = REGISTRATE.item("byte_card",
					p -> new PunchCardItem(p, 1, PunchCardTheme.BLUE))
			.properties(p -> p.stacksTo(1))
			.register();
	
	// Static loading
	public static void register() {}
}
