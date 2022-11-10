package com.depletednova.assembly.content.programmer.punchCard;

import com.depletednova.assembly.foundation.AssemblyLang;
import com.depletednova.assembly.foundation.gui.PunchCardTheme;
import com.depletednova.assembly.foundation.item.DescriptionHelper;
import com.depletednova.assembly.foundation.item.ICustomDescription;
import com.depletednova.assembly.registry.AItems;
import com.simibubi.create.foundation.gui.ScreenOpener;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.ChatFormatting.GRAY;
import static net.minecraft.ChatFormatting.YELLOW;
public class PunchCardItem extends Item implements ICustomDescription {
	public PunchCardItem(Properties properties, int rows, PunchCardTheme theme) {
		super(properties);
		this.ROWS = rows;
		this.THEME = theme;
	}
	public final int ROWS;
	public final PunchCardTheme THEME;
	
	@Override
	public @NotNull ItemStack getDefaultInstance() {
		ItemStack stack = super.getDefaultInstance();
		for (int row = 1; row < ROWS + 1; row++)
			PunchCardHelper.setRow(stack, row, PunchCardHelper.generateRow());
		return stack;
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack binaryStack = player.getItemInHand(hand);
		ItemStack plierStack = player.getItemInHand(hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND);
		if (player.isShiftKeyDown() && plierStack.getItem().equals(AItems.HOLE_PUNCHER.get())) {
			if (level.isClientSide) {
				DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
					openGUI(binaryStack, hand);
				});
			}
			return InteractionResultHolder.consume(binaryStack);
		}
		
		return InteractionResultHolder.pass(binaryStack);
	}
	
	@OnlyIn(Dist.CLIENT)
	protected void openGUI(ItemStack item, InteractionHand hand) {
		ScreenOpener.open(new PunchCardMenu(item, THEME));
	}
	
	public static ItemStack getPunchCard(Player player) {
		ItemStack mainHand = player.getItemInHand(InteractionHand.MAIN_HAND);
		return (mainHand.getItem() instanceof PunchCardItem) ? mainHand : player.getItemInHand(InteractionHand.OFF_HAND);
	}
	
	@Override
	public List<Component> getCustomDescription(ItemStack stack, Player player) {
		List<Component> slots = new ArrayList<>();

		AssemblyLang.translate("tooltip.punch_card")
				.style(GRAY)
				.addTo(slots);

		for (int i = 1; i < ROWS + 1; i++) {
			AssemblyLang.lang()
					.text(DescriptionHelper.createBinaryBar(PunchCardHelper.getRow(stack, i)))
					.style(YELLOW)
					.addTo(slots);
		}

		return slots;
	}
}