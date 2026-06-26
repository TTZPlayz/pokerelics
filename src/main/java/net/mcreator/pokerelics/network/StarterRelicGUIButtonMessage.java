package net.mcreator.pokerelics.network;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.core.BlockPos;

import net.mcreator.pokerelics.procedures.SelectSprayerProcedure;
import net.mcreator.pokerelics.procedures.SelectCandleProcedure;
import net.mcreator.pokerelics.procedures.SelectBulbProcedure;
import net.mcreator.pokerelics.procedures.ExitStarterRelicGUIProcedure;
import net.mcreator.pokerelics.PokerelicsMod;

@EventBusSubscriber
public record StarterRelicGUIButtonMessage(int buttonID, int x, int y, int z) implements CustomPacketPayload {

	public static final Type<StarterRelicGUIButtonMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(PokerelicsMod.MODID, "starter_relic_gui_buttons"));
	public static final StreamCodec<RegistryFriendlyByteBuf, StarterRelicGUIButtonMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, StarterRelicGUIButtonMessage message) -> {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}, (RegistryFriendlyByteBuf buffer) -> new StarterRelicGUIButtonMessage(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt()));
	@Override
	public Type<StarterRelicGUIButtonMessage> type() {
		return TYPE;
	}

	public static void handleData(final StarterRelicGUIButtonMessage message, final IPayloadContext context) {
		if (context.flow() == PacketFlow.SERVERBOUND) {
			context.enqueueWork(() -> handleButtonAction(context.player(), message.buttonID, message.x, message.y, message.z)).exceptionally(e -> {
				context.connection().disconnect(Component.literal(e.getMessage()));
				return null;
			});
		}
	}

	public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
		Level world = entity.level();
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(new BlockPos(x, y, z)))
			return;
		if (buttonID == 0) {

			ExitStarterRelicGUIProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 1) {

			SelectCandleProcedure.execute(entity);
		}
		if (buttonID == 2) {

			SelectBulbProcedure.execute(entity);
		}
		if (buttonID == 3) {

			SelectSprayerProcedure.execute(entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		PokerelicsMod.addNetworkMessage(StarterRelicGUIButtonMessage.TYPE, StarterRelicGUIButtonMessage.STREAM_CODEC, StarterRelicGUIButtonMessage::handleData);
	}
}