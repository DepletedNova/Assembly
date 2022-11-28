package com.depletednova.assembly.foundation.gui;

import com.depletednova.assembly.Assembly;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.gui.element.ScreenElement;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public enum AllUITextures implements ScreenElement {
	// Instructor
	INSTRUCTOR("instructor", 0, 0, 205, 138),
	INSTRUCTOR_INVENTORY("instructor", 0, 138, 179, 91),
	INSTRUCTOR_TAB_ACTIVE("instructor", 0, 229, 21, 9),
	INSTRUCTOR_TAB_INACTIVE("instructor", 21, 229, 21, 9),
	;
	
	public final ResourceLocation location;
	public final int width, height;
	public final int startX, startY;
	
	AllUITextures(String location, int startX, int startY, int width, int height) {
		this.location = Assembly.getResource("textures/gui/" + location + ".png");
		this.width = width;
		this.height = height;
		this.startX = startX;
		this.startY = startY;
	}
	
	public void bind() {
		RenderSystem.setShaderTexture(0, location);
	}
	
	@Override
	public void render(PoseStack ms, int x, int y) {
		bind();
		GuiComponent.blit(ms, x, y, 0, startX, startY, width, height, 256, 256);
	}
	
	public void render(PoseStack ms, int x, int y, GuiComponent component) {
		bind();
		component.blit(ms, x, y, startX, startY, width, height);
	}
}
