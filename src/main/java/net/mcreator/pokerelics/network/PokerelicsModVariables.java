package net.mcreator.pokerelics.network;

import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.HolderLookup;

import net.mcreator.pokerelics.PokerelicsMod;

import java.util.function.Supplier;

@EventBusSubscriber
public class PokerelicsModVariables {
	public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, PokerelicsMod.MODID);
	public static final Supplier<AttachmentType<PlayerVariables>> PLAYER_VARIABLES = ATTACHMENT_TYPES.register("player_variables", () -> AttachmentType.serializable(() -> new PlayerVariables()).build());
	public static ResourceLocation bulb_texture = null;
	public static ResourceLocation candle_texture_unlit = null;
	public static ResourceLocation candle_texture_lit = null;
	public static ResourceLocation sprayer_full_1 = null;
	public static ResourceLocation sprayer_full_2 = null;
	public static ResourceLocation sprayer_full_3 = null;
	public static ResourceLocation sprayer_full_4 = null;
	public static ResourceLocation sprayer_full_5 = null;
	public static ResourceLocation sprayer_full_6 = null;
	public static ResourceLocation sprayer_full_7 = null;
	public static ResourceLocation sprayer_full_8 = null;
	public static ResourceLocation sprayer_full_9 = null;
	public static ResourceLocation sprayer_full_10 = null;
	public static ResourceLocation sprayer_full_11 = null;
	public static ResourceLocation sprayer_full_12 = null;
	public static ResourceLocation sprayer_full_13 = null;
	public static ResourceLocation sprayer_full_14 = null;
	public static ResourceLocation regal_amulet = null;
	public static ResourceLocation sinister_glove = null;
	public static ResourceLocation lucky_egg = null;
	public static double max_amulet_cooldown = 300.0;
	public static double max_glove_cooldown = 100.0;
	public static double amulet_activation_chance = 0.1;
	public static double max_bulb_cooldown = 400.0;
	public static double max_quiver_storage = 64.0;
	public static double blaze_damage = 3.0;
	public static double quiver_base_damage = 3.0;
	public static double attuned_death_damage = 5.0;

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		PokerelicsMod.addNetworkMessage(PlayerVariablesSyncMessage.TYPE, PlayerVariablesSyncMessage.STREAM_CODEC, PlayerVariablesSyncMessage::handleData);
	}

	@SubscribeEvent
	public static void onPlayerLoggedInSyncPlayerVariables(PlayerEvent.PlayerLoggedInEvent event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			for (Entity entityiterator : player.level().players())
				if (entityiterator != player && entityiterator instanceof ServerPlayer playeriterator)
					PacketDistributor.sendToPlayer(player, new PlayerVariablesSyncMessage(playeriterator.getData(PLAYER_VARIABLES), playeriterator.getId()));
			PacketDistributor.sendToPlayersInDimension((ServerLevel) player.level(), new PlayerVariablesSyncMessage(player.getData(PLAYER_VARIABLES), player.getId()));
		}
	}

	@SubscribeEvent
	public static void onPlayerRespawnedSyncPlayerVariables(PlayerEvent.PlayerRespawnEvent event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			for (Entity entityiterator : player.level().players())
				if (entityiterator != player && entityiterator instanceof ServerPlayer playeriterator)
					PacketDistributor.sendToPlayer(player, new PlayerVariablesSyncMessage(playeriterator.getData(PLAYER_VARIABLES), playeriterator.getId()));
			PacketDistributor.sendToPlayersInDimension((ServerLevel) player.level(), new PlayerVariablesSyncMessage(player.getData(PLAYER_VARIABLES), player.getId()));
		}
	}

	@SubscribeEvent
	public static void onPlayerChangedDimensionSyncPlayerVariables(PlayerEvent.PlayerChangedDimensionEvent event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			for (Entity entityiterator : player.level().players())
				if (entityiterator != player && entityiterator instanceof ServerPlayer playeriterator)
					PacketDistributor.sendToPlayer(player, new PlayerVariablesSyncMessage(playeriterator.getData(PLAYER_VARIABLES), playeriterator.getId()));
			PacketDistributor.sendToPlayersInDimension((ServerLevel) player.level(), new PlayerVariablesSyncMessage(player.getData(PLAYER_VARIABLES), player.getId()));
		}
	}

	@SubscribeEvent
	public static void onPlayerTickUpdateSyncPlayerVariables(PlayerTickEvent.Post event) {
		if (event.getEntity() instanceof ServerPlayer player && player.getData(PLAYER_VARIABLES)._syncDirty) {
			PacketDistributor.sendToPlayersInDimension((ServerLevel) player.level(), new PlayerVariablesSyncMessage(player.getData(PLAYER_VARIABLES), player.getId()));
			player.getData(PLAYER_VARIABLES)._syncDirty = false;
		}
	}

	@SubscribeEvent
	public static void clonePlayer(PlayerEvent.Clone event) {
		PlayerVariables original = event.getOriginal().getData(PLAYER_VARIABLES);
		PlayerVariables clone = new PlayerVariables();
		clone.backSlot = original.backSlot;
		clone.lastBackSlot = original.lastBackSlot;
		clone.chlorophyll_streak = original.chlorophyll_streak;
		clone.attuned_item = original.attuned_item;
		clone.firestreak_chance = original.firestreak_chance;
		clone.sent_already = original.sent_already;
		clone.has_first_relic = original.has_first_relic;
		if (!event.isWasDeath()) {
			clone.is_relic_active = original.is_relic_active;
			clone.texture_timer = original.texture_timer;
			clone.firestreak_counter = original.firestreak_counter;
			clone.chosen_relic = original.chosen_relic;
		}
		event.getEntity().setData(PLAYER_VARIABLES, clone);
	}

	public static class PlayerVariables implements INBTSerializable<CompoundTag> {
		boolean _syncDirty = false;
		public ItemStack backSlot = ItemStack.EMPTY;
		public boolean is_relic_active = false;
		public ItemStack lastBackSlot = ItemStack.EMPTY;
		public double texture_timer = 0.5;
		public double firestreak_counter = 0;
		public double chlorophyll_streak = 0.0;
		public ItemStack attuned_item = ItemStack.EMPTY;
		public ItemStack chosen_relic = ItemStack.EMPTY;
		public double firestreak_chance = 0.2;
		public boolean sent_already = false;
		public boolean has_first_relic = false;

		@Override
		public CompoundTag serializeNBT(HolderLookup.Provider lookupProvider) {
			CompoundTag nbt = new CompoundTag();
			nbt.put("backSlot", backSlot.saveOptional(lookupProvider));
			nbt.putBoolean("is_relic_active", is_relic_active);
			nbt.put("lastBackSlot", lastBackSlot.saveOptional(lookupProvider));
			nbt.putDouble("texture_timer", texture_timer);
			nbt.putDouble("firestreak_counter", firestreak_counter);
			nbt.putDouble("chlorophyll_streak", chlorophyll_streak);
			nbt.put("attuned_item", attuned_item.saveOptional(lookupProvider));
			nbt.put("chosen_relic", chosen_relic.saveOptional(lookupProvider));
			nbt.putDouble("firestreak_chance", firestreak_chance);
			nbt.putBoolean("sent_already", sent_already);
			nbt.putBoolean("has_first_relic", has_first_relic);
			return nbt;
		}

		@Override
		public void deserializeNBT(HolderLookup.Provider lookupProvider, CompoundTag nbt) {
			backSlot = ItemStack.parseOptional(lookupProvider, nbt.getCompound("backSlot"));
			is_relic_active = nbt.getBoolean("is_relic_active");
			lastBackSlot = ItemStack.parseOptional(lookupProvider, nbt.getCompound("lastBackSlot"));
			texture_timer = nbt.getDouble("texture_timer");
			firestreak_counter = nbt.getDouble("firestreak_counter");
			chlorophyll_streak = nbt.getDouble("chlorophyll_streak");
			attuned_item = ItemStack.parseOptional(lookupProvider, nbt.getCompound("attuned_item"));
			chosen_relic = ItemStack.parseOptional(lookupProvider, nbt.getCompound("chosen_relic"));
			firestreak_chance = nbt.getDouble("firestreak_chance");
			sent_already = nbt.getBoolean("sent_already");
			has_first_relic = nbt.getBoolean("has_first_relic");
		}

		public void markSyncDirty() {
			_syncDirty = true;
		}
	}

	public record PlayerVariablesSyncMessage(PlayerVariables data, int player) implements CustomPacketPayload {
		public static final Type<PlayerVariablesSyncMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(PokerelicsMod.MODID, "player_variables_sync"));
		public static final StreamCodec<RegistryFriendlyByteBuf, PlayerVariablesSyncMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, PlayerVariablesSyncMessage message) -> {
			buffer.writeInt(message.player());
			buffer.writeNbt(message.data().serializeNBT(buffer.registryAccess()));
		}, (RegistryFriendlyByteBuf buffer) -> {
			PlayerVariablesSyncMessage message = new PlayerVariablesSyncMessage(new PlayerVariables(), buffer.readInt());
			message.data.deserializeNBT(buffer.registryAccess(), buffer.readNbt());
			return message;
		});

		@Override
		public Type<PlayerVariablesSyncMessage> type() {
			return TYPE;
		}

		public static void handleData(final PlayerVariablesSyncMessage message, final IPayloadContext context) {
			if (context.flow() == PacketFlow.CLIENTBOUND && message.data != null) {
				context.enqueueWork(() -> {
					Entity player = context.player().level().getEntity(message.player);
					if (player == null)
						return;
					player.getData(PLAYER_VARIABLES).deserializeNBT(context.player().registryAccess(), message.data.serializeNBT(context.player().registryAccess()));
				}).exceptionally(e -> {
					context.connection().disconnect(Component.literal(e.getMessage()));
					return null;
				});
			}
		}
	}
}