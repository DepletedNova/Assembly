package com.depletednova.assembly;

import com.depletednova.assembly.content.connection.patchCable.CableSelectionHandler;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class AssemblyClient {
	public static final CableSelectionHandler CABLE_HANDLER = new CableSelectionHandler();
}
