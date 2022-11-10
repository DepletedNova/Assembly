package com.depletednova.assembly.content.connection.patchCable;

import com.simibubi.create.foundation.networking.SimplePacketBase;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.function.Supplier;

import static com.depletednova.assembly.content.connection.patchCable.CablePlacementContext.fromByteBuffer;
import static com.depletednova.assembly.content.connection.patchCable.CablePlacementContext.writeByteBuffer;

public class CablePlacePacket extends SimplePacketBase {
	
	CablePlacementContext firstContext, secondContext;
	
	public CablePlacePacket(CablePlacementContext firstContext, CablePlacementContext secondContext) {
		this.firstContext = firstContext;
		this.secondContext = secondContext;
	}
	
	public CablePlacePacket(FriendlyByteBuf buffer) {
		this.firstContext = fromByteBuffer(buffer);
		this.secondContext = fromByteBuffer(buffer);
	}
	
	@Override
	public void write(FriendlyByteBuf buffer) {
		writeByteBuffer(buffer, this.firstContext);
		writeByteBuffer(buffer, this.secondContext);
	}
	
	@Override
	public void handle(Supplier<Context> context) {
		Context c = context.get();
		c.enqueueWork(() -> {
			ServerPlayer player = c.getSender();
			if (player == null) return;
			ServerLevel level = player.getLevel();
			
			CableItem.placeWires(firstContext, secondContext, level, player);
		});
		c.setPacketHandled(true);
	}
}
