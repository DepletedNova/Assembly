package com.depletednova.assembly.foundation.gui.widgets;

import com.depletednova.assembly.foundation.utility.consumers.QuadConsumer;
import com.depletednova.assembly.foundation.utility.consumers.TriConsumer;
import com.jozufozu.flywheel.util.NonNullSupplier;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.gui.widget.AbstractSimiWidget;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.function.BiConsumer;

public class GenericButtonWidget extends AbstractSimiWidget {
	public GenericButtonWidget(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	// Callback Events
	private BiConsumer<Double, Double> clickCallback;
	public GenericButtonWidget withClickCallback(BiConsumer<Double, Double> callback) {
		this.clickCallback = callback;
		return this;
	}
	public GenericButtonWidget withClickCallback(Runnable callback) {
		return withClickCallback((_$, _$$) -> callback.run());
	}
	private QuadConsumer<PoseStack, Integer, Integer, Boolean> renderCallback;
	public GenericButtonWidget withRenderCallback(QuadConsumer<PoseStack, Integer, Integer, Boolean> callback) {
		this.renderCallback = callback;
		return this;
	}
	public GenericButtonWidget withRenderCallback(TriConsumer<PoseStack, Integer, Integer> callback) {
		return withRenderCallback((ms, x, y, _$) -> callback.accept(ms, x, y));
	}
	
	public GenericButtonWidget setTooltip(java.util.List<Component> components) {
		this.toolTip = components;
		return this;
	}
	
	// Settings
	SoundEvent sound;
	public void setSoundEvent(SoundEvent sound) {
		this.sound = sound;
	}
	
	@Override
	public void playDownSound(SoundManager manager) {
		manager.play(SimpleSoundInstance.forUI(sound == null ? SoundEvents.UI_BUTTON_CLICK : sound, 1.0f));
	}
	
	@Override
	public void renderButton(@NotNull PoseStack ms, int mouseX, int mouseY, float partialTicks) {
		if (renderCallback != null)
			renderCallback.accept(ms, x, y, isHovered);
	}
	
	@Override
	public void onClick(double mouseX, double mouseY) {
		if (clickCallback != null)
			clickCallback.accept(mouseX, mouseY);
	}
}
