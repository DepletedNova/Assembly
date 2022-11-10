package com.depletednova.assembly.registry;

import com.depletednova.assembly.content.connection.INetworkable;
import com.depletednova.assembly.events.CommonEvents;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

/** 
*	Remember to add to {@link CommonEvents#registerCapabilities(RegisterCapabilitiesEvent)}
 */
public class ACapabilities {
	public static final Capability<INetworkable> NETWORK = CapabilityManager.get(new CapabilityToken<>() { });
}
