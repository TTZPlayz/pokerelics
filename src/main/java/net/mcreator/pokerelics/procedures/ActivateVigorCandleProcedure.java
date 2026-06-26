package net.mcreator.pokerelics.procedures;

import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.AdvancementHolder;

import net.mcreator.pokerelics.network.PokerelicsModVariables;
import net.mcreator.pokerelics.init.PokerelicsModParticleTypes;
import net.mcreator.pokerelics.init.PokerelicsModItems;

import javax.annotation.Nullable;

@EventBusSubscriber
public class ActivateVigorCandleProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingIncomingDamageEvent event) {
		if (event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity(), event.getSource().getEntity());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		execute(null, world, x, y, z, entity, sourceentity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if (sourceentity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getItem() == PokerelicsModItems.VIGOR_CANDLE.get()) {
			if (sourceentity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("activation_timer") == 0) {
				if (Math.random() <= sourceentity.getData(PokerelicsModVariables.PLAYER_VARIABLES).firestreak_chance) {
					if (sourceentity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("extinguish_timer") == 0) {
						{
							final String _tagName = "activation_timer";
							final double _tagValue = 100;
							CustomData.update(DataComponents.CUSTOM_DATA, sourceentity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot, tag -> tag.putDouble(_tagName, _tagValue));
						}
						{
							PokerelicsModVariables.PlayerVariables _vars = sourceentity.getData(PokerelicsModVariables.PLAYER_VARIABLES);
							_vars.firestreak_counter = sourceentity.getData(PokerelicsModVariables.PLAYER_VARIABLES).firestreak_counter + 1;
							_vars.markSyncDirty();
						}
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("item.firecharge.use")), SoundSource.NEUTRAL, 1, 1);
							} else {
								_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("item.firecharge.use")), SoundSource.NEUTRAL, 1, 1, false);
							}
						}
						entity.igniteForSeconds(5);
						if (world instanceof ServerLevel _level) {
							sourceentity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.hurtAndBreak(1, _level, null, _stkprov -> {
							});
						}
					} else {
						if (world instanceof ServerLevel _level)
							_level.sendParticles(ParticleTypes.CLOUD, x, (y + 1), z, 10, 0.25, 0.25, 0.25, 0.1);
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.fire.extinguish")), SoundSource.NEUTRAL, 1, 1);
							} else {
								_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.fire.extinguish")), SoundSource.NEUTRAL, 1, 1, false);
							}
						}
					}
				} else if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getItem() == PokerelicsModItems.BULB_OF_ABSORPTION.get()) {
					{
						PokerelicsModVariables.PlayerVariables _vars = sourceentity.getData(PokerelicsModVariables.PLAYER_VARIABLES);
						_vars.firestreak_counter = sourceentity.getData(PokerelicsModVariables.PLAYER_VARIABLES).firestreak_counter + 1;
						_vars.markSyncDirty();
					}
					if (world instanceof ServerLevel _level)
						_level.sendParticles((SimpleParticleType) (PokerelicsModParticleTypes.VIGOR_FLAME.get()), x, (y + 1), z, 10, 0.25, 0.25, 0.25, 0.1);
					if (world instanceof ServerLevel _level)
						_level.sendParticles(ParticleTypes.ENCHANTED_HIT, x, (y + 1), z, 10, 0.25, 0.25, 0.25, 0.1);
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("item.firecharge.use")), SoundSource.NEUTRAL, 1, 1);
						} else {
							_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("item.firecharge.use")), SoundSource.NEUTRAL, 1, 1, false);
						}
					}
					{
						final String _tagName = "activation_timer";
						final double _tagValue = 200;
						CustomData.update(DataComponents.CUSTOM_DATA, sourceentity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot, tag -> tag.putDouble(_tagName, _tagValue));
					}
					entity.igniteForSeconds(10);
					if (world instanceof ServerLevel _level) {
						sourceentity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.hurtAndBreak(1, _level, null, _stkprov -> {
						});
					}
				} else if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getItem() == PokerelicsModItems.SHELL_SPRAYER.get()) {
					if (world instanceof ServerLevel _level)
						_level.sendParticles(ParticleTypes.CLOUD, x, (y + 1), z, 10, 0.25, 0.25, 0.25, 0.1);
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.fire.extinguish")), SoundSource.NEUTRAL, 1, 1);
						} else {
							_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.fire.extinguish")), SoundSource.NEUTRAL, 1, 1, false);
						}
					}
					if (world instanceof ServerLevel _level) {
						sourceentity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.hurtAndBreak(1, _level, null, _stkprov -> {
						});
					}
				}
			} else if (sourceentity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("activation_timer") > 0) {
				if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getItem() == PokerelicsModItems.BULB_OF_ABSORPTION.get()) {
					if (world instanceof ServerLevel _level)
						_level.sendParticles((SimpleParticleType) (PokerelicsModParticleTypes.VIGOR_FLAME.get()), x, (y + 1), z, 10, 0.25, 0.25, 0.25, 0.1);
					if (world instanceof ServerLevel _level)
						_level.sendParticles(ParticleTypes.ENCHANTED_HIT, x, (y + 1), z, 10, 0.25, 0.25, 0.25, 0.1);
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("item.firecharge.use")), SoundSource.NEUTRAL, 1, 1);
						} else {
							_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("item.firecharge.use")), SoundSource.NEUTRAL, 1, 1, false);
						}
					}
					{
						final String _tagName = "heal_counter";
						final double _tagValue = 0;
						CustomData.update(DataComponents.CUSTOM_DATA, entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot, tag -> tag.putDouble(_tagName, _tagValue));
					}
					{
						final String _tagName = "activation_timer";
						final double _tagValue = (sourceentity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("activation_timer") + 100);
						CustomData.update(DataComponents.CUSTOM_DATA, sourceentity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot, tag -> tag.putDouble(_tagName, _tagValue));
					}
					{
						PokerelicsModVariables.PlayerVariables _vars = sourceentity.getData(PokerelicsModVariables.PLAYER_VARIABLES);
						_vars.firestreak_counter = sourceentity.getData(PokerelicsModVariables.PLAYER_VARIABLES).firestreak_counter + 2;
						_vars.markSyncDirty();
					}
					entity.igniteForSeconds(10);
					if (world instanceof ServerLevel _level) {
						entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.hurtAndBreak(1, _level, null, _stkprov -> {
						});
					}
				} else if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getItem() == PokerelicsModItems.SHELL_SPRAYER.get()) {
					if (world instanceof ServerLevel _level)
						_level.sendParticles(ParticleTypes.CLOUD, x, (y + 1), z, 10, 0.25, 0.25, 0.25, 0.1);
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.fire.extinguish")), SoundSource.NEUTRAL, 1, 1);
						} else {
							_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.fire.extinguish")), SoundSource.NEUTRAL, 1, 1, false);
						}
					}
					entity.igniteForSeconds(3);
					if (sourceentity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("activation_timer") - 50 < 0) {
						{
							final String _tagName = "activation_timer";
							final double _tagValue = 0;
							CustomData.update(DataComponents.CUSTOM_DATA, sourceentity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot, tag -> tag.putDouble(_tagName, _tagValue));
						}
						if (sourceentity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal("\u00A7l\u00A77Extinguished!\u00A7r"), true);
					} else {
						{
							final String _tagName = "activation_timer";
							final double _tagValue = (sourceentity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("activation_timer") - 50);
							CustomData.update(DataComponents.CUSTOM_DATA, sourceentity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot, tag -> tag.putDouble(_tagName, _tagValue));
						}
					}
					{
						PokerelicsModVariables.PlayerVariables _vars = sourceentity.getData(PokerelicsModVariables.PLAYER_VARIABLES);
						_vars.firestreak_counter = sourceentity.getData(PokerelicsModVariables.PLAYER_VARIABLES).firestreak_counter + 1;
						_vars.markSyncDirty();
					}
					if (world instanceof ServerLevel _level) {
						sourceentity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.hurtAndBreak(2, _level, null, _stkprov -> {
						});
					}
				} else {
					entity.igniteForSeconds(5);
					if (world instanceof ServerLevel _level) {
						sourceentity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.hurtAndBreak(1, _level, null, _stkprov -> {
						});
					}
				}
			}
			if (sourceentity.getData(PokerelicsModVariables.PLAYER_VARIABLES).firestreak_counter >= 10) {
				if (sourceentity instanceof ServerPlayer _player) {
					AdvancementHolder _adv = _player.server.getAdvancements().get(ResourceLocation.parse("pokerelics:pyromaniac"));
					if (_adv != null) {
						AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
						if (!_ap.isDone()) {
							for (String criteria : _ap.getRemainingCriteria())
								_player.getAdvancements().award(_adv, criteria);
						}
					}
				}
			}
		}
	}
}