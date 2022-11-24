package com.depletednova.assembly.registry;

import com.depletednova.assembly.Assembly;
import com.depletednova.assembly.content.connection.patchCable.CableRenderer;
import com.depletednova.assembly.content.connection.patchCable.CableTileEntity;
import com.depletednova.assembly.content.logistics.instructor.InstructorTileEntity;
import com.simibubi.create.content.contraptions.base.HorizontalHalfShaftInstance;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class ATileEntities {
	private static final CreateRegistrate REGISTRATE = Assembly.getRegistrate();
	
	// Connection
	public static final BlockEntityEntry<CableTileEntity> PATCH_CABLE = REGISTRATE
			.tileEntity("patch_cable", CableTileEntity::new)
			//.instance(() -> ShaftInstance::new, false)
			.validBlock(ABlocks.PATCH_CABLE)
			.renderer(() -> CableRenderer::new)
			.register();
	
	// Programmables
	public static final BlockEntityEntry<InstructorTileEntity> INSTRUCTOR = REGISTRATE
			.tileEntity("instructor", InstructorTileEntity::new)
			.instance(() -> HorizontalHalfShaftInstance::new)
			.validBlock(ABlocks.INSTRUCTOR)
			//.renderer()
			.register();
	
	// Static loading
	public static void register() {}
}
