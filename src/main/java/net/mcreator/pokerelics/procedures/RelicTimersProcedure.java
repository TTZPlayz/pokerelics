package net.mcreator.pokerelics.procedures;

import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.network.chat.Component;
import net.minecraft.core.particles.ParticleTypes;

import net.mcreator.pokerelics.network.PokerelicsModVariables;
import net.mcreator.pokerelics.init.PokerelicsModItems;

import javax.annotation.Nullable;

@EventBusSubscriber
public class RelicTimersProcedure {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double particleRadius = 0;
		double particleAmount = 0;
		if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getItem() == PokerelicsModItems.VIGOR_CANDLE.get()) {
			if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).candle_activation_timer > 0) {
				{
					PokerelicsModVariables.PlayerVariables _vars = entity.getData(PokerelicsModVariables.PLAYER_VARIABLES);
					_vars.candle_activation_timer = entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).candle_activation_timer - 1;
					_vars.markSyncDirty();
				}
				particleAmount = 4;
				particleRadius = 0.25;
				for (int index0 = 0; index0 < (int) particleAmount; index0++) {
					world.addParticle(ParticleTypes.FLAME, (x + 0 + Mth.nextDouble(RandomSource.create(), -0.1, 0.1) * particleRadius), (y + 1 + Mth.nextDouble(RandomSource.create(), 0, 0) * particleRadius),
							(z + 0 + Mth.nextDouble(RandomSource.create(), -0.1, 0.1) * particleRadius), (Mth.nextDouble(RandomSource.create(), -0.1, 0.1)), 0.2, (Mth.nextDouble(RandomSource.create(), -0.1, 0.1)));
				}
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(
							Component.literal(("\u00A7o\u00A7lFirestreak:\u00A7r \u00A76" + (new java.text.DecimalFormat("#.#").format(entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).candle_activation_timer / 20) + "s\u00A7r"))), true);
			} else {
				{
					PokerelicsModVariables.PlayerVariables _vars = entity.getData(PokerelicsModVariables.PLAYER_VARIABLES);
					_vars.firestreak_counter = 0;
					_vars.markSyncDirty();
				}
			}
		} else if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getItem() == PokerelicsModItems.SINISTER_GLOVE.get()) {
			if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).glove_activation_cooldown > 0) {
				{
					PokerelicsModVariables.PlayerVariables _vars = entity.getData(PokerelicsModVariables.PLAYER_VARIABLES);
					_vars.glove_activation_cooldown = entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).glove_activation_cooldown - 1;
					_vars.markSyncDirty();
				}
				if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).glove_activation_cooldown / 20 <= 10) {
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(
								Component.literal(("\u00A7o\u00A7lCooldown:\u00A7r \u00A75" + (new java.text.DecimalFormat("#.#").format(entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).glove_activation_cooldown / 20) + "s\u00A7r"))), true);
				} else {
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(
								Component.literal(("\u00A7o\u00A7lCooldown:\u00A7r \u00A75" + (new java.text.DecimalFormat("##").format(entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).glove_activation_cooldown / 20) + "s\u00A7r"))), true);
				}
			}
		} else if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getItem() == PokerelicsModItems.REGAL_AMULET.get()) {
			if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).amulet_activation_cooldown > 0) {
				{
					PokerelicsModVariables.PlayerVariables _vars = entity.getData(PokerelicsModVariables.PLAYER_VARIABLES);
					_vars.amulet_activation_cooldown = entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).amulet_activation_cooldown - 1;
					_vars.markSyncDirty();
				}
				if (particleRadius / 20 <= 10) {
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(
								Component.literal(("\u00A7o\u00A7lCooldown:\u00A7r \u00A7e" + (new java.text.DecimalFormat("#.#").format(entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).amulet_activation_cooldown / 20) + "s\u00A7r"))), true);
				} else {
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(
								Component.literal(("\u00A7o\u00A7lCooldown:\u00A7r \u00A7e" + (new java.text.DecimalFormat("##").format(entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).amulet_activation_cooldown / 20) + "s\u00A7r"))), true);
				}
			}
		}
	}
}