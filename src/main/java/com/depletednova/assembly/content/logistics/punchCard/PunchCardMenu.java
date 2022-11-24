package com.depletednova.assembly.content.logistics.punchCard;

import com.depletednova.assembly.foundation.gui.PunchCardTheme;
import com.depletednova.assembly.foundation.gui.widgets.GenericButtonWidget;
import com.depletednova.assembly.registry.AItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.gui.AbstractSimiScreen;
import com.simibubi.create.foundation.gui.element.GuiGameElement;
import com.simibubi.create.foundation.gui.widget.AbstractSimiWidget;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.depletednova.assembly.foundation.gui.PunchCardTheme.PunchCardTexture.*;

public class PunchCardMenu extends AbstractSimiScreen {
	// Variables
	private final ItemStack stack;
	private final PunchCardItem item;
	private final PunchCardTheme theme;
	
	public PunchCardMenu(ItemStack stack, PunchCardTheme theme) {
		this.stack = stack;
		this.item = (PunchCardItem) stack.getItem();
		this.theme = theme;
		cardValues = new ArrayList<>();
		for (int i = 1; i < this.item.ROWS + 1; i++)
			cardValues.add(PunchCardHelper.getRow(stack, i));
	}
	
	@Override
	protected void init() {
		// Size
		setWindowSize(NAMEPLATE.width, NAMEPLATE.height + BOTTOM.height + this.item.ROWS * MID_SECTION.height);
		super.init();
		// widgets
		updateWidgets();
	}
	
	List<List<Boolean>> cardValues;
	protected void setCardValue(int row, int index, boolean value) {
		cardValues.get(row).set(index, value);
	}
	protected boolean getCardValue(int row, int index) {
		return cardValues.get(row).get(index);
	}
	
	Collection<AbstractSimiWidget> widgets = new ArrayList<>();
	protected void updateWidgets() {
		clearWidgets();
		removeWidgets(widgets);
		widgets.clear();
		
		// Hole Punch
		for (int y = 0; y < this.item.ROWS; y++) {
			for (int x = 0; x < 8; x++) {
				int finalX = x;
				int finalY = y;
				GenericButtonWidget holePunch = new GenericButtonWidget(guiLeft + 32 + 16 * x, guiTop + NAMEPLATE.height + MID_SECTION.height * y, 12, 12)
						.withClickCallback(() -> {
							setCardValue(finalY, finalX, !getCardValue(finalY, finalX));
						})
						.withRenderCallback((ms, _x, _y) -> {
							
							theme.render(getCardValue(finalY, finalX) ? PUNCH_ONE : PUNCH_ZERO, ms, _x, _y);
						});
				addRenderableWidget(holePunch);
				widgets.add(holePunch);
			}
		}
		// Exit buttons
		int height = guiTop + NAMEPLATE.height + MID_SECTION.height * item.ROWS + 10;
		GenericButtonWidget accept = new GenericButtonWidget(guiLeft + 170, height, 12, 12)
				.withClickCallback(() ->  {
					for (int row = 0; row < cardValues.size(); row++)
						PunchCardHelper.setRow(stack, row + 1, cardValues.get(row));
					this.onClose();
				})
				.withRenderCallback((ms, _x, _y, hovered) -> {
					theme.render(hovered ? ACCEPT_HIGHLIGHT : ACCEPT, ms, _x, _y);
				});
		addRenderableWidget(accept);
		widgets.add(accept);
		
		GenericButtonWidget close = new GenericButtonWidget(guiLeft + 154, height, 12, 12)
				.withClickCallback(this::onClose)
				.withRenderCallback((ms, _x, _y, hovered) -> {
					theme.render(hovered ? CANCEL_HIGHLIGHT : CANCEL, ms, _x, _y);
				});
		addRenderableWidget(close);
		widgets.add(close);
	}
	
	@Override
	protected void renderWindow(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
		// Render background
		theme.render(NAMEPLATE, ms, guiLeft, guiTop);
		for (int i = 0; i < item.ROWS; i++)
			theme.render(MID_SECTION, ms, guiLeft, guiTop + NAMEPLATE.height + MID_SECTION.height * i);
		theme.render(BOTTOM, ms, guiLeft, guiTop + NAMEPLATE.height + MID_SECTION.height * item.ROWS);
		// Render Text
		TranslatableComponent header = new TranslatableComponent(item.getDescriptionId());
		font.draw(ms, header, guiLeft + NAMEPLATE.width / 2f - font.width(header) / 2f, guiTop + 4, 0x0D2744);
		// Render Item
		GuiGameElement.of(AItems.HOLE_PUNCHER.asStack())
				.scale(3)
				.at(guiLeft + BOTTOM.width, guiTop + NAMEPLATE.height + MID_SECTION.height * item.ROWS + BOTTOM.height - 30)
				.render(ms);
	}
}
