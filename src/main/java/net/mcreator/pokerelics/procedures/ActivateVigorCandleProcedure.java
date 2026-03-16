package net.mcreator.pokerelics.procedures;

import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.AdvancementHolder;

import net.mcreator.pokerelics.network.PokerelicsModVariables;
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
			if (sourceentity.getData(PokerelicsModVariables.PLAYER_VARIABLES).candle_activation_timer == 0) {
				if (Math.random() <= 0.2) {
					{
						PokerelicsModVariables.PlayerVariables _vars = sourceentity.getData(PokerelicsModVariables.PLAYER_VARIABLES);
						_vars.candle_activation_timer = 100;
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
				}
			} else if (sourceentity.getData(PokerelicsModVariables.PLAYER_VARIABLES).candle_activation_timer > 0) {
				entity.igniteForSeconds(5);
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