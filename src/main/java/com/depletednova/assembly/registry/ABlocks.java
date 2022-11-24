package com.depletednova.assembly.registry;

import com.depletednova.assembly.Assembly;
import com.depletednova.assembly.content.connection.patchCable.CableBlock;
import com.depletednova.assembly.content.connection.patchCable.CableItem;
import com.depletednova.assembly.content.logistics.instructor.InstructorBlock;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import static com.simibubi.create.content.AllSections.*;
import static com.simibubi.create.foundation.data.ModelGen.*;

public class ABlocks {
	private static final CreateRegistrate REGISTRATE = Assembly.getRegistrate()
			.creativeModeTab(() -> Assembly.BASE_TAB);
	
	// Logistics
	static { REGISTRATE.startSection(LOGISTICS); }
	
	public static final BlockEntry<CableBlock> PATCH_CABLE = REGISTRATE
			.block("patch_cable", CableBlock::new)
			.initialProperties(SharedProperties::softMetal)
			.properties(p -> p.noCollission()
					.noOcclusion())
			.blockstate((c, p) -> p.getExistingVariantBuilder(c.get()))
			.item(CableItem::new)
			.transform(customItemModel())
			.register();
	
	public static final BlockEntry<InstructorBlock> INSTRUCTOR = REGISTRATE
			.block("instructor", InstructorBlock::new)
			.initialProperties(SharedProperties::softMetal)
			.properties(BlockBehaviour.Properties::noOcclusion)
			.simpleItem()
			.register();
	
	// Static loading
	public static void register() {}
}
