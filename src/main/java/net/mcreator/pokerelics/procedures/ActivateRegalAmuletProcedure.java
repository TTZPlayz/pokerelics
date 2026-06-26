package net.mcreator.pokerelics.procedures;

import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;
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
public class ActivateRegalAmuletProcedure {
	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event) {
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
		double random = 0;
		if (sourceentity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getItem() == PokerelicsModItems.REGAL_AMULET.get()) {
			if (sourceentity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("activation_cooldown") == 0) {
				random = Math.random();
				if (random <= PokerelicsModVariables.amulet_activation_chance) {
					{
						final String _tagName = "activation_cooldown";
						final double _tagValue = PokerelicsModVariables.max_amulet_cooldown;
						CustomData.update(DataComponents.CUSTOM_DATA, sourceentity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot, tag -> tag.putDouble(_tagName, _tagValue));
					}
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.piglin.admiring_item")), SoundSource.NEUTRAL, 1, 1);
						} else {
							_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.piglin.admiring_item")), SoundSource.NEUTRAL, 1, 1, false);
						}
					}
					if (sourceentity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal(((BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString()).replace(":", ":entities/"))), false);
					if (random >= 0.5) {
						if (!world.isClientSide() && world.getServer() != null) {
							for (ItemStack itemstackiterator : world.getServer().reloadableRegistries()
									.getLootTable(
											ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.parse((((BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString()).replace(":", ":entities/"))).toLowerCase(java.util.Locale.ENGLISH))))
									.getRandomItems(new LootParams.Builder((ServerLevel) world).create(LootContextParamSets.EMPTY))) {
								if (world instanceof ServerLevel _level) {
									ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, itemstackiterator);
									entityToSpawn.setPickUpDelay(10);
									_level.addFreshEntity(entityToSpawn);
								}
								if (world instanceof ServerLevel _level) {
									ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, itemstackiterator);
									entityToSpawn.setPickUpDelay(10);
									_level.addFreshEntity(entityToSpawn);
								}
							}
						}
						world.addParticle((SimpleParticleType) (PokerelicsModParticleTypes.REGAL_SPARKLE.get()), x, y, z, 0, 1, 0);
						world.addParticle((SimpleParticleType) (PokerelicsModParticleTypes.REGAL_SPARKLE.get()), x, y, z, 0, 1, 0);
					} else if (random >= 0.25) {
						if (!world.isClientSide() && world.getServer() != null) {
							for (ItemStack itemstackiterator : world.getServer().reloadableRegistries()
									.getLootTable(
											ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.parse((((BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString()).replace(":", ":entities/"))).toLowerCase(java.util.Locale.ENGLISH))))
									.getRandomItems(new LootParams.Builder((ServerLevel) world).create(LootContextParamSets.EMPTY))) {
								if (world instanceof ServerLevel _level) {
									ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, itemstackiterator);
									entityToSpawn.setPickUpDelay(10);
									_level.addFreshEntity(entityToSpawn);
								}
								if (world instanceof ServerLevel _level) {
									ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, itemstackiterator);
									entityToSpawn.setPickUpDelay(10);
									_level.addFreshEntity(entityToSpawn);
								}
								if (world instanceof ServerLevel _level) {
									ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, itemstackiterator);
									entityToSpawn.setPickUpDelay(10);
									_level.addFreshEntity(entityToSpawn);
								}
							}
						}
						world.addParticle((SimpleParticleType) (PokerelicsModParticleTypes.REGAL_SPARKLE.get()), x, y, z, 0, 1, 0);
						world.addParticle((SimpleParticleType) (PokerelicsModParticleTypes.REGAL_SPARKLE.get()), x, y, z, 0, 1, 0);
						world.addParticle((SimpleParticleType) (PokerelicsModParticleTypes.REGAL_SPARKLE.get()), x, y, z, 0, 1, 0);
					} else if (random >= 0.1) {
						if (!world.isClientSide() && world.getServer() != null) {
							for (ItemStack itemstackiterator : world.getServer().reloadableRegistries()
									.getLootTable(
											ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.parse((((BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString()).replace(":", ":entities/"))).toLowerCase(java.util.Locale.ENGLISH))))
									.getRandomItems(new LootParams.Builder((ServerLevel) world).create(LootContextParamSets.EMPTY))) {
								if (world instanceof ServerLevel _level) {
									ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, itemstackiterator);
									entityToSpawn.setPickUpDelay(10);
									_level.addFreshEntity(entityToSpawn);
								}
								if (world instanceof ServerLevel _level) {
									ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, itemstackiterator);
									entityToSpawn.setPickUpDelay(10);
									_level.addFreshEntity(entityToSpawn);
								}
								if (world instanceof ServerLevel _level) {
									ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, itemstackiterator);
									entityToSpawn.setPickUpDelay(10);
									_level.addFreshEntity(entityToSpawn);
								}
								if (world instanceof ServerLevel _level) {
									ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, itemstackiterator);
									entityToSpawn.setPickUpDelay(10);
									_level.addFreshEntity(entityToSpawn);
								}
							}
						}
						world.addParticle((SimpleParticleType) (PokerelicsModParticleTypes.REGAL_SPARKLE.get()), x, y, z, 0, 1, 0);
						world.addParticle((SimpleParticleType) (PokerelicsModParticleTypes.REGAL_SPARKLE.get()), x, y, z, 0, 1, 0);
						world.addParticle((SimpleParticleType) (PokerelicsModParticleTypes.REGAL_SPARKLE.get()), x, y, z, 0, 1, 0);
						world.addParticle((SimpleParticleType) (PokerelicsModParticleTypes.REGAL_SPARKLE.get()), x, y, z, 0, 1, 0);
					} else if (random >= 0.05) {
						if (!world.isClientSide() && world.getServer() != null) {
							for (ItemStack itemstackiterator : world.getServer().reloadableRegistries()
									.getLootTable(
											ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.parse((((BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString()).replace(":", ":entities/"))).toLowerCase(java.util.Locale.ENGLISH))))
									.getRandomItems(new LootParams.Builder((ServerLevel) world).create(LootContextParamSets.EMPTY))) {
								if (world instanceof ServerLevel _level) {
									ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, itemstackiterator);
									entityToSpawn.setPickUpDelay(10);
									_level.addFreshEntity(entityToSpawn);
								}
								if (world instanceof ServerLevel _level) {
									ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, itemstackiterator);
									entityToSpawn.setPickUpDelay(10);
									_level.addFreshEntity(entityToSpawn);
								}
								if (world instanceof ServerLevel _level) {
									ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, itemstackiterator);
									entityToSpawn.setPickUpDelay(10);
									_level.addFreshEntity(entityToSpawn);
								}
								if (world instanceof ServerLevel _level) {
									ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, itemstackiterator);
									entityToSpawn.setPickUpDelay(10);
									_level.addFreshEntity(entityToSpawn);
								}
								if (world instanceof ServerLevel _level) {
									ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, itemstackiterator);
									entityToSpawn.setPickUpDelay(10);
									_level.addFreshEntity(entityToSpawn);
								}
							}
						}
						world.addParticle((SimpleParticleType) (PokerelicsModParticleTypes.REGAL_SPARKLE.get()), x, y, z, 0, 1, 0);
						world.addParticle((SimpleParticleType) (PokerelicsModParticleTypes.REGAL_SPARKLE.get()), x, y, z, 0, 1, 0);
						world.addParticle((SimpleParticleType) (PokerelicsModParticleTypes.REGAL_SPARKLE.get()), x, y, z, 0, 1, 0);
						world.addParticle((SimpleParticleType) (PokerelicsModParticleTypes.REGAL_SPARKLE.get()), x, y, z, 0, 1, 0);
						world.addParticle((SimpleParticleType) (PokerelicsModParticleTypes.REGAL_SPARKLE.get()), x, y, z, 0, 1, 0);
					} else {
						if (!world.isClientSide() && world.getServer() != null) {
							for (ItemStack itemstackiterator : world.getServer().reloadableRegistries()
									.getLootTable(
											ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.parse((((BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString()).replace(":", ":entities/"))).toLowerCase(java.util.Locale.ENGLISH))))
									.getRandomItems(new LootParams.Builder((ServerLevel) world).create(LootContextParamSets.EMPTY))) {
								if (world instanceof ServerLevel _level) {
									ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, itemstackiterator);
									entityToSpawn.setPickUpDelay(10);
									_level.addFreshEntity(entityToSpawn);
								}
								if (world instanceof ServerLevel _level) {
									ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, itemstackiterator);
									entityToSpawn.setPickUpDelay(10);
									_level.addFreshEntity(entityToSpawn);
								}
								if (world instanceof ServerLevel _level) {
									ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, itemstackiterator);
									entityToSpawn.setPickUpDelay(10);
									_level.addFreshEntity(entityToSpawn);
								}
								if (world instanceof ServerLevel _level) {
									ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, itemstackiterator);
									entityToSpawn.setPickUpDelay(10);
									_level.addFreshEntity(entityToSpawn);
								}
								if (world instanceof ServerLevel _level) {
									ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, itemstackiterator);
									entityToSpawn.setPickUpDelay(10);
									_level.addFreshEntity(entityToSpawn);
								}
								if (world instanceof ServerLevel _level) {
									ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, itemstackiterator);
									entityToSpawn.setPickUpDelay(10);
									_level.addFreshEntity(entityToSpawn);
								}
								if (world instanceof ServerLevel _level) {
									ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, itemstackiterator);
									entityToSpawn.setPickUpDelay(10);
									_level.addFreshEntity(entityToSpawn);
								}
								if (world instanceof ServerLevel _level) {
									ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, itemstackiterator);
									entityToSpawn.setPickUpDelay(10);
									_level.addFreshEntity(entityToSpawn);
								}
								if (world instanceof ServerLevel _level) {
									ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, itemstackiterator);
									entityToSpawn.setPickUpDelay(10);
									_level.addFreshEntity(entityToSpawn);
								}
								if (world instanceof ServerLevel _level) {
									ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, itemstackiterator);
									entityToSpawn.setPickUpDelay(10);
									_level.addFreshEntity(entityToSpawn);
								}
							}
						}
						world.addParticle((SimpleParticleType) (PokerelicsModParticleTypes.REGAL_SPARKLE.get()), x, y, z, 0, 1, 0);
						world.addParticle((SimpleParticleType) (PokerelicsModParticleTypes.REGAL_SPARKLE.get()), x, y, z, 0, 1, 0);
						world.addParticle((SimpleParticleType) (PokerelicsModParticleTypes.REGAL_SPARKLE.get()), x, y, z, 0, 1, 0);
						world.addParticle((SimpleParticleType) (PokerelicsModParticleTypes.REGAL_SPARKLE.get()), x, y, z, 0, 1, 0);
						world.addParticle((SimpleParticleType) (PokerelicsModParticleTypes.REGAL_SPARKLE.get()), x, y, z, 0, 1, 0);
						world.addParticle((SimpleParticleType) (PokerelicsModParticleTypes.REGAL_SPARKLE.get()), x, y, z, 0, 1, 0);
						world.addParticle((SimpleParticleType) (PokerelicsModParticleTypes.REGAL_SPARKLE.get()), x, y, z, 0, 1, 0);
						world.addParticle((SimpleParticleType) (PokerelicsModParticleTypes.REGAL_SPARKLE.get()), x, y, z, 0, 1, 0);
						world.addParticle((SimpleParticleType) (PokerelicsModParticleTypes.REGAL_SPARKLE.get()), x, y, z, 0, 1, 0);
						world.addParticle((SimpleParticleType) (PokerelicsModParticleTypes.REGAL_SPARKLE.get()), x, y, z, 0, 1, 0);
						if (entity instanceof ServerPlayer _player) {
							AdvancementHolder _adv = _player.server.getAdvancements().get(ResourceLocation.parse("pokerelics:and_when_you_hit_the_jackpot"));
							if (_adv != null) {
								AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
								if (!_ap.isDone()) {
									for (String criteria : _ap.getRemainingCriteria())
										_player.getAdvancements().award(_adv, criteria);
								}
							}
						}
					}
					if (world instanceof ServerLevel _level) {
						sourceentity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.hurtAndBreak(1, _level, null, _stkprov -> {
						});
					}
				}
			}
		}
	}
}