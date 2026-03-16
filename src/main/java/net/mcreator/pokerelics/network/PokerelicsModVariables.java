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
		clone.spoon_ticks_used = original.spoon_ticks_used;
		clone.has_candle = original.has_candle;
		clone.has_bulb = original.has_bulb;
		clone.has_sprayer = original.has_sprayer;
		clone.has_spoon = original.has_spoon;
		clone.has_glove = original.has_glove;
		clone.has_amulet = original.has_amulet;
		clone.has_pendant = original.has_pendant;
		clone.has_repaired_relic = original.has_repaired_relic;
		clone.chlorophyll_streak = original.chlorophyll_streak;
		if (!event.isWasDeath()) {
			clone.heal_counter = original.heal_counter;
			clone.marked_by_spoon = original.marked_by_spoon;
			clone.is_relic_active = original.is_relic_active;
			clone.candle_activation_timer = original.candle_activation_timer;
			clone.time_since_heal = original.time_since_heal;
			clone.spoon_time = original.spoon_time;
			clone.fill_level = original.fill_level;
			clone.time_since_spray = original.time_since_spray;
			clone.spoon_active = original.spoon_active;
			clone.point_and_clik_cooldown = original.point_and_clik_cooldown;
			clone.texture_timer = original.texture_timer;
			clone.glove_active = original.glove_active;
			clone.glove_activation_cooldown = original.glove_activation_cooldown;
			clone.amulet_activation_cooldown = original.amulet_activation_cooldown;
			clone.firestreak_counter = original.firestreak_counter;
		}
		event.getEntity().setData(PLAYER_VARIABLES, clone);
	}

	public static class PlayerVariables implements INBTSerializable<CompoundTag> {
		boolean _syncDirty = false;
		public ItemStack backSlot = ItemStack.EMPTY;
		public double heal_counter = 0;
		public boolean marked_by_spoon = false;
		public boolean is_relic_active = false;
		public double candle_activation_timer = 0;
		public double time_since_heal = 0;
		public ItemStack lastBackSlot = ItemStack.EMPTY;
		public double spoon_time = 0;
		public double spoon_ticks_used = 0;
		public double fill_level = 0;
		public double time_since_spray = 0;
		public boolean spoon_active = false;
		public double point_and_clik_cooldown = 0;
		public double texture_timer = 0;
		public boolean glove_active = false;
		public double glove_activation_cooldown = 0;
		public double amulet_activation_cooldown = 0;
		public boolean has_candle = false;
		public boolean has_bulb = false;
		public boolean has_sprayer = false;
		public boolean has_spoon = false;
		public boolean has_glove = false;
		public boolean has_amulet = false;
		public boolean has_pendant = false;
		public boolean has_repaired_relic = false;
		public double firestreak_counter = 0;
		public double chlorophyll_streak = 0;

		@Override
		public CompoundTag serializeNBT(HolderLookup.Provider lookupProvider) {
			CompoundTag nbt = new CompoundTag();
			nbt.put("backSlot", backSlot.saveOptional(lookupProvider));
			nbt.putDouble("heal_counter", heal_counter);
			nbt.putBoolean("marked_by_spoon", marked_by_spoon);
			nbt.putBoolean("is_relic_active", is_relic_active);
			nbt.putDouble("candle_activation_timer", candle_activation_timer);
			nbt.putDouble("time_since_heal", time_since_heal);
			nbt.put("lastBackSlot", lastBackSlot.saveOptional(lookupProvider));
			nbt.putDouble("spoon_time", spoon_time);
			nbt.putDouble("spoon_ticks_used", spoon_ticks_used);
			nbt.putDouble("fill_level", fill_level);
			nbt.putDouble("time_since_spray", time_since_spray);
			nbt.putBoolean("spoon_active", spoon_active);
			nbt.putDouble("point_and_clik_cooldown", point_and_clik_cooldown);
			nbt.putDouble("texture_timer", texture_timer);
			nbt.putBoolean("glove_active", glove_active);
			nbt.putDouble("glove_activation_cooldown", glove_activation_cooldown);
			nbt.putDouble("amulet_activation_cooldown", amulet_activation_cooldown);
			nbt.putBoolean("has_candle", has_candle);
			nbt.putBoolean("has_bulb", has_bulb);
			nbt.putBoolean("has_sprayer", has_sprayer);
			nbt.putBoolean("has_spoon", has_spoon);
			nbt.putBoolean("has_glove", has_glove);
			nbt.putBoolean("has_amulet", has_amulet);
			nbt.putBoolean("has_pendant", has_pendant);
			nbt.putBoolean("has_repaired_relic", has_repaired_relic);
			nbt.putDouble("firestreak_counter", firestreak_counter);
			nbt.putDouble("chlorophyll_streak", chlorophyll_streak);
			return nbt;
		}

		@Override
		public void deserializeNBT(HolderLookup.Provider lookupProvider, CompoundTag nbt) {
			backSlot = ItemStack.parseOptional(lookupProvider, nbt.getCompound("backSlot"));
			heal_counter = nbt.getDouble("heal_counter");
			marked_by_spoon = nbt.getBoolean("marked_by_spoon");
			is_relic_active = nbt.getBoolean("is_relic_active");
			candle_activation_timer = nbt.getDouble("candle_activation_timer");
			time_since_heal = nbt.getDouble("time_since_heal");
			lastBackSlot = ItemStack.parseOptional(lookupProvider, nbt.getCompound("lastBackSlot"));
			spoon_time = nbt.getDouble("spoon_time");
			spoon_ticks_used = nbt.getDouble("spoon_ticks_used");
			fill_level = nbt.getDouble("fill_level");
			time_since_spray = nbt.getDouble("time_since_spray");
			spoon_active = nbt.getBoolean("spoon_active");
			point_and_clik_cooldown = nbt.getDouble("point_and_clik_cooldown");
			texture_timer = nbt.getDouble("texture_timer");
			glove_active = nbt.getBoolean("glove_active");
			glove_activation_cooldown = nbt.getDouble("glove_activation_cooldown");
			amulet_activation_cooldown = nbt.getDouble("amulet_activation_cooldown");
			has_candle = nbt.getBoolean("has_candle");
			has_bulb = nbt.getBoolean("has_bulb");
			has_sprayer = nbt.getBoolean("has_sprayer");
			has_spoon = nbt.getBoolean("has_spoon");
			has_glove = nbt.getBoolean("has_glove");
			has_amulet = nbt.getBoolean("has_amulet");
			has_pendant = nbt.getBoolean("has_pendant");
			has_repaired_relic = nbt.getBoolean("has_repaired_relic");
			firestreak_counter = nbt.getDouble("firestreak_counter");
			chlorophyll_streak = nbt.getDouble("chlorophyll_streak");
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