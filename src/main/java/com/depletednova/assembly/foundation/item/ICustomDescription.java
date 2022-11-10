package com.depletednova.assembly.foundation.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface ICustomDescription {
	List<Component> getCustomDescription(ItemStack stack, Player player);
}
