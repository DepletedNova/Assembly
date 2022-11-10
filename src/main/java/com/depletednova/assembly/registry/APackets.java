package com.depletednova.assembly.registry;

import com.depletednova.assembly.Assembly;
import com.depletednova.assembly.content.connection.patchCable.CablePlacePacket;
import com.simibubi.create.foundation.networking.SimplePacketBase;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static net.minecraftforge.network.NetworkDirection.PLAY_TO_SERVER;

public enum APackets {
	// Client to Server
	CABLE_PLACE(CablePlacePacket.class, CablePlacePacket::new, PLAY_TO_SERVER)
	
	// Server to Client
	
	;
	public static final ResourceLocation CHANNEL_NAME = Assembly.getResource("main");
	public static final int NETWORK_VERSION = 1;
	public static final String NETWORK_VERSION_STR = String.valueOf(NETWORK_VERSION);
	public static SimpleChannel channel;
	
	private LoadedPacket<?> packet;
	
	<T extends SimplePacketBase> APackets(Class<T> type, Function<FriendlyByteBuf, T> factory,
										  NetworkDirection direction) {
		packet = new LoadedPacket<>(type, factory, direction);
	}
	
	public static void registerPackets() {
		channel = NetworkRegistry.ChannelBuilder.named(CHANNEL_NAME)
				.serverAcceptedVersions(NETWORK_VERSION_STR::equals)
				.clientAcceptedVersions(NETWORK_VERSION_STR::equals)
				.networkProtocolVersion(() -> NETWORK_VERSION_STR)
				.simpleChannel();
		for (APackets packet : values())
			packet.packet.register();
	}
	
	private static class LoadedPacket<T extends SimplePacketBase> {
		private static int index = 0;
		
		private BiConsumer<T, FriendlyByteBuf> encoder;
		private Function<FriendlyByteBuf, T> decoder;
		private BiConsumer<T, Supplier<NetworkEvent.Context>> handler;
		private Class<T> type;
		private NetworkDirection direction;
		
		private LoadedPacket(Class<T> type, Function<FriendlyByteBuf, T> factory, NetworkDirection direction) {
			encoder = T::write;
			decoder = factory;
			handler = T::handle;
			this.type = type;
			this.direction = direction;
		}
		
		private void register() {
			channel.messageBuilder(type, index++, direction)
					.encoder(encoder)
					.decoder(decoder)
					.consumer(handler)
					.add();
		}
	}
}
