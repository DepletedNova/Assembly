package com.depletednova.assembly.registry;

import com.depletednova.assembly.Assembly;
import com.depletednova.assembly.content.connection.patchCable.CableBlock;
import com.depletednova.assembly.content.connection.patchCable.CableItem;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.client.renderer.RenderType;

import static com.simibubi.create.content.AllSections.LOGISTICS;

public class ABlocks {
	private static final CreateRegistrate REGISTRATE = Assembly.getRegistrate()
			.creativeModeTab(() -> Assembly.BASE_TAB);
	
	// Logistics
	static { REGISTRATE.startSection(LOGISTICS); }
	
	public static final BlockEntry<CableBlock> PATCH_CABLE = REGISTRATE
			.block("patch_cable", CableBlock::new)
			.initialProperties(SharedProperties::softMetal)
			.properties(p -> p.noCollission()
					.noOcclusion()
					.dynamicShape())
			.addLayer(() -> RenderType::translucent)
			//.blockstate()
			.item(CableItem::new)
			.build()
			.register();
	
	// Static loading
	public static void register() {}
}
