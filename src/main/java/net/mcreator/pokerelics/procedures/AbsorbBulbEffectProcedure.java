package net.mcreator.pokerelics.procedures;

import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.AdvancementHolder;

import net.mcreator.pokerelics.network.PokerelicsModVariables;
import net.mcreator.pokerelics.init.PokerelicsModItems;

import javax.annotation.Nullable;

@EventBusSubscriber
public class AbsorbBulbEffectProcedure {
	@SubscribeEvent
	public static void onEntityTick(EntityTickEvent.Pre event) {
		execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof Player) {
			if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getItem() == PokerelicsModItems.BULB_OF_ABSORPTION.get()) {
				if (world.canSeeSkyFromBelowWater(BlockPos.containing(entity.getX(), entity.getY(), entity.getZ())) && world instanceof Level _lvl6 && _lvl6.isDay()) {
					if ((entity instanceof Player _plr ? _plr.getFoodData().getFoodLevel() : 0) < 20) {
						if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).heal_counter >= 400) {
							if (entity instanceof Player _player)
								_player.getFoodData().setFoodLevel((entity instanceof Player _plr ? _plr.getFoodData().getFoodLevel() : 0) + 1);
							{
								PokerelicsModVariables.PlayerVariables _vars = entity.getData(PokerelicsModVariables.PLAYER_VARIABLES);
								_vars.heal_counter = 0;
								_vars.time_since_heal = 15;
								_vars.markSyncDirty();
							}
							if (world instanceof ServerLevel _level)
								_level.sendParticles(ParticleTypes.COMPOSTER, x, (y + 1), z, 30, 1, 1, 1, 3);
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("item.bone_meal.use")), SoundSource.NEUTRAL, 1, 1);
								} else {
									_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("item.bone_meal.use")), SoundSource.NEUTRAL, 1, 1, false);
								}
							}
							{
								PokerelicsModVariables.PlayerVariables _vars = entity.getData(PokerelicsModVariables.PLAYER_VARIABLES);
								_vars.chlorophyll_streak = entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).chlorophyll_streak + 1;
								_vars.markSyncDirty();
							}
							if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).chlorophyll_streak >= 20) {
								if (entity instanceof ServerPlayer _player) {
									AdvancementHolder _adv = _player.server.getAdvancements().get(ResourceLocation.parse("pokerelics:chlorophyll_champion"));
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
						if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).heal_counter < 400) {
							{
								PokerelicsModVariables.PlayerVariables _vars = entity.getData(PokerelicsModVariables.PLAYER_VARIABLES);
								_vars.heal_counter = entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).heal_counter + 1;
								_vars.markSyncDirty();
							}
						}
					} else if ((entity instanceof Player _plr ? _plr.getFoodData().getFoodLevel() : 0) >= 20) {
						{
							PokerelicsModVariables.PlayerVariables _vars = entity.getData(PokerelicsModVariables.PLAYER_VARIABLES);
							_vars.heal_counter = 0;
							_vars.markSyncDirty();
						}
					}
				}
				if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).time_since_heal > 0) {
					{
						PokerelicsModVariables.PlayerVariables _vars = entity.getData(PokerelicsModVariables.PLAYER_VARIABLES);
						_vars.time_since_heal = entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).time_since_heal - 1;
						_vars.markSyncDirty();
					}
				}
			}
		}
	}
}