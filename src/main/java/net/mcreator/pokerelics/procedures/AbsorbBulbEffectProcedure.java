package net.mcreator.pokerelics.procedures;

import net.neoforged.neoforge.event.tick.EntityTickEvent;
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
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.AdvancementHolder;

import net.mcreator.pokerelics.network.PokerelicsModVariables;
import net.mcreator.pokerelics.init.PokerelicsModParticleTypes;
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
				if (world.canSeeSkyFromBelowWater(BlockPos.containing(entity.getX(), entity.getY(), entity.getZ())) && world.dayTime() <= 12000) {
					if ((entity instanceof Player _plr ? _plr.getFoodData().getFoodLevel() : 0) < 20) {
						if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("heal_counter") >= PokerelicsModVariables.max_bulb_cooldown) {
							if (entity instanceof Player _player)
								_player.getFoodData().setFoodLevel((entity instanceof Player _plr ? _plr.getFoodData().getFoodLevel() : 0) + 1);
							{
								final String _tagName = "heal_counter";
								final double _tagValue = 0;
								CustomData.update(DataComponents.CUSTOM_DATA, entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot, tag -> tag.putDouble(_tagName, _tagValue));
							}
							if (world instanceof ServerLevel _level)
								_level.sendParticles((SimpleParticleType) (PokerelicsModParticleTypes.ABSORB_LEAF.get()), x, (y + 1), z, 30, 0.25, 0.25, 0.25, 0.1);
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
						if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("heal_counter") < PokerelicsModVariables.max_bulb_cooldown) {
							{
								final String _tagName = "heal_counter";
								final double _tagValue = (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("heal_counter") + 1);
								CustomData.update(DataComponents.CUSTOM_DATA, entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot, tag -> tag.putDouble(_tagName, _tagValue));
							}
						}
					} else if ((entity instanceof Player _plr ? _plr.getFoodData().getFoodLevel() : 0) >= 20) {
						{
							final String _tagName = "heal_counter";
							final double _tagValue = 0;
							CustomData.update(DataComponents.CUSTOM_DATA, entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot, tag -> tag.putDouble(_tagName, _tagValue));
						}
					}
				}
			}
		}
	}
}