package com.depletednova.assembly.events;

import com.depletednova.assembly.Assembly;
import com.depletednova.assembly.AssemblyClient;
import com.depletednova.assembly.foundation.item.ICustomDescription;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.utility.Components;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {
	public static String ITEM_PREFIX = "item." + Assembly.MOD_ID;
	public static String BLOCK_PREFIX = "block." + Assembly.MOD_ID;
	
	@SubscribeEvent
	public static void onTick(ClientTickEvent event) {
		if (!isGameActive())
			return;
		
		if (event.phase == Phase.START)
			return;
		
		AssemblyClient.CABLE_HANDLER.tick();
	}
	
	@SubscribeEvent(priority = EventPriority.HIGH) // Run ahead of Create for Stress tooltips
	public static void addToItemTooltip(ItemTooltipEvent event) {
		if (event.getPlayer() == null)
			return;
		
		ItemStack stack = event.getItemStack();
		String tKey = stack.getItem().getDescriptionId(stack);
		
		if (tKey.startsWith(ITEM_PREFIX) || tKey.startsWith(BLOCK_PREFIX)) {
			if (TooltipHelper.hasTooltip(stack, event.getPlayer())) {
				List<Component> itemTooltip = event.getToolTip();
				List<Component> toolTip = new ArrayList<>();
				toolTip.add(itemTooltip.remove(0));
				TooltipHelper.getTooltip(stack)
						.addInformation(toolTip);
				itemTooltip.addAll(0, toolTip);
			}
		}
		
		if (stack.getItem() instanceof ICustomDescription item) {
			List<Component> customDescription = item.getCustomDescription(stack, event.getPlayer());
			if (!customDescription.isEmpty()) {
				event.getToolTip().add(Components.immutableEmpty());
				event.getToolTip().addAll(customDescription);
			}
		}
	}
	
	private static boolean isGameActive() {
		return !(Minecraft.getInstance().level == null || Minecraft.getInstance().player == null);
	}
}
