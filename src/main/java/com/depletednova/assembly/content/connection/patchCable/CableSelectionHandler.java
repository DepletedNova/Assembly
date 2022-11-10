package com.depletednova.assembly.content.connection.patchCable;

import com.depletednova.assembly.foundation.AssemblyLang;
import com.depletednova.assembly.registry.APackets;
import com.simibubi.create.AllSpecialTextures;
import com.simibubi.create.CreateClient;
import com.simibubi.create.foundation.utility.RaycastHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;

public class CableSelectionHandler {
	// Data to send
	private CablePlacementContext firstContext;
	private CablePlacementContext secondContext;
	private boolean canPath;
	
	// Called Functions
	public boolean useOn(UseOnContext context) {
		
		if (resetContext(context.getPlayer()))
			return true;
		
		if (placeBlock(context.getLevel(), context.getPlayer()))
			return true;
		
		canPath = evaluatePath(context.getLevel());
		
		if (firstContext == null) {
			if (usedOnCable(context))
				firstContext = CablePlacementContext.fromCable(context);
			else {
				BlockPlaceContext placeContext = new BlockPlaceContext(context);
				if (placeContext.canPlace()) {
					firstContext = CablePlacementContext.fromPlace(placeContext);
				} else return false;
			}
			canPath = evaluatePath(context.getLevel());
			return true;
		} else if (secondContext == null || !canPath) {
			if (usedOnCable(context))
				secondContext = CablePlacementContext.fromCable(context);
			else {
				BlockPlaceContext placeContext = new BlockPlaceContext(context);
				if (placeContext.canPlace() && !firstContext.position.equals(placeContext.getClickedPos())) {
					secondContext = CablePlacementContext.fromPlace(placeContext);
				} else return false;
			}
			canPath = evaluatePath(context.getLevel());
			return true;
		}
		
		return false;
	}
	
	public boolean use(Level level, Player player, InteractionHand hand) {
		if (resetContext(player))
			return true;
		
		if (placeBlock(level, player))
			return true;
		
		canPath = evaluatePath(level);
		
		return false;
	}
	
	// Use Utility
	private boolean placeBlock(Level level, Player player) {
		if (!canPath || secondContext == null) return false;
		
		APackets.channel.sendToServer(new CablePlacePacket(firstContext, secondContext));
		firstContext = null;
		secondContext = null;
		return true;
	}
	private boolean evaluatePath(Level level) {
		if (secondContext == null)
			return true;
		return RaycastHelper.rayTraceUntil(Vec3.atCenterOf(firstContext.position), Vec3.atCenterOf(secondContext.position), pos -> {
			BlockState state = level.getBlockState(pos);
			return !(state.isAir() || state.getCollisionShape(level, pos).isEmpty());
		}).missed();
	}
	private boolean usedOnCable(UseOnContext context) {
		return context.getLevel().getBlockState(context.getClickedPos()).getBlock() instanceof CableBlock;
	}
	private boolean resetContext(Player player) {
		if (player.isShiftKeyDown() && firstContext != null) {
			firstContext = null;
			secondContext = null;
			return true;
		}
		return false;
	}
	
	// Colors
	private static final int ACTIVE_HIGHLIGHT = 0x68c586;
	private static final int ACTIVE = 0x4D9162;
	private static final int FAIL = 0xc5b548;
	private static final int FAIL_HIGHLIGHT = 0xEAD864;
	
	// Outline objects
	private final Object firstOutline = new Object();
	private final Object secondOutline = new Object();
	private final Object connectionLine = new Object();
	
	// Tick visuals
	public void tick() {
		Minecraft mc = Minecraft.getInstance();
		LocalPlayer player = mc.player;
		Level level = mc.level;
		
		if (level == null || player == null)
			return;
		
		InteractionHand hand = CableItem.getHandWithCable(player);
		if (hand == null)
			return;
		
		// First context
		if (firstContext == null)
			return;
		
		CreateClient.OUTLINER.showAABB(firstOutline, Shapes.block()
						.bounds()
						.move(firstContext.position))
				.colored(canPath ? ACTIVE : FAIL)
				.lineWidth(0.02f)
				.withFaceTexture(AllSpecialTextures.SELECTION)
				.disableNormals();
		
		// Status
		if (canPath)
			AssemblyLang.translate("cable.remove")
					.color(ACTIVE_HIGHLIGHT)
					.sendStatus(player);
		
		// Second Context
		if (secondContext == null)
			return;
		
		CreateClient.OUTLINER.showAABB(secondOutline, Shapes.block()
						.bounds()
						.move(secondContext.position))
				.colored(canPath ? ACTIVE : FAIL)
				.lineWidth(0.02f)
				.withFaceTexture(AllSpecialTextures.SELECTION)
				.disableNormals();
		
		// Connection
		CreateClient.OUTLINER.showLine(connectionLine, Vec3.atCenterOf(firstContext.position), Vec3.atCenterOf(secondContext.position))
				.colored(canPath ? ACTIVE_HIGHLIGHT : FAIL_HIGHLIGHT)
				.lineWidth(0.04f);
		
		if (!canPath)
			AssemblyLang.translate("cable.replace")
					.color(FAIL_HIGHLIGHT)
					.sendStatus(player);
	}
}
