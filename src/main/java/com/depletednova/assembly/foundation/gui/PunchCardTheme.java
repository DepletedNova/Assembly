package com.depletednova.assembly.foundation.gui;

import com.depletednova.assembly.Assembly;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;

public enum PunchCardTheme {
	BLUE("blue_card")
	;
	
	public final ResourceLocation location;
	
	PunchCardTheme(String location) {
		this.location = Assembly.getResource("textures/gui/punch_theme/" + location + ".png");
	}
	
	public void bind() {
		RenderSystem.setShaderTexture(0, location);
	}
	
	public void render(PunchCardTexture tex, PoseStack ms, int x, int y) {
		bind();
		GuiComponent.blit(ms, x, y, 0, tex.x, tex.y, tex.width, tex.height, 256, 256);
	}
	
	public void render(PunchCardTexture tex, PoseStack ms, int x, int y, GuiComponent component) {
		bind();
		component.blit(ms, x, y, tex.x, tex.y, tex.width, tex.height);
	}
	
	public enum PunchCardTexture {
		NAMEPLATE(0, 0, 188, 24),
		MID_SECTION(0, 25, 188, 16),
		BOTTOM(0, 42, 196, 27),
		ACCEPT(0, 69, 12, 12),
		ACCEPT_HIGHLIGHT(12, 69, 12, 12),
		CANCEL(0, 81, 12, 12),
		CANCEL_HIGHLIGHT(12, 81, 12, 12),
		PUNCH_ZERO(0, 93, 12, 12),
		PUNCH_ONE(12, 93, 12, 12)
		;
		
		PunchCardTexture(int x, int y, int width, int height) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}
		
		public final int x, y;
		public final int width, height;
	}
}
