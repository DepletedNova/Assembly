package com.depletednova.assembly.events;

import com.depletednova.assembly.content.CatenaryCommand;
import com.depletednova.assembly.foundation.connectivity.INetworkable;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CommonEvents {
	@SubscribeEvent
	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.register(INetworkable.class);
	}
	
	@SubscribeEvent
	public static void onCommandsRegister(RegisterCommandsEvent event) {
		new CatenaryCommand(event.getDispatcher());
	}
}
