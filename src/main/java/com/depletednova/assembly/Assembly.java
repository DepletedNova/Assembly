package com.depletednova.assembly;

import com.depletednova.assembly.content.logistics.instructor.InstructorTabRegistry;
import com.depletednova.assembly.registry.*;
import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Assembly.MOD_ID)
public class Assembly {
	public static final String MOD_ID = "assembly";
	public static final Logger LOGGER = LogUtils.getLogger();
	
	public Assembly() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get()
				.getModEventBus();
		IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
		
		forgeEventBus.register(this);
		
		InstructorTabRegistry.register();
		
		ABlocks.register();
		ATileEntities.register();
		AItems.register();
		ABlockPartials.register();
		AContainerTypes.register();
		
		modEventBus.addListener(EventPriority.LOW, Assembly::init);
	}
	
	public static void init(final FMLCommonSetupEvent event) {
		APackets.registerPackets();
	}
	
	public static ResourceLocation getResource(String path) {
		return new ResourceLocation(MOD_ID, path);
	}
	
	// Registrate
	private static final NonNullSupplier<CreateRegistrate> REGISTRATE = CreateRegistrate.lazy(MOD_ID);
	public static CreateRegistrate getRegistrate() { return REGISTRATE.get(); }
	
	// Tabs
	public static final CreativeModeTab BASE_TAB =new CreativeModeTab(MOD_ID) {
		@Override public ItemStack makeIcon() { return new ItemStack(ABlocks.PATCH_CABLE.get()); }
	};
}
