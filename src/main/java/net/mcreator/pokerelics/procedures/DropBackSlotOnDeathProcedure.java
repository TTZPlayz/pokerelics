package net.mcreator.pokerelics.procedures;

import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.core.component.DataComponents;

import net.mcreator.pokerelics.network.PokerelicsModVariables;

import javax.annotation.Nullable;

@EventBusSubscriber
public class DropBackSlotOnDeathProcedure {
	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event) {
		if (event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		ItemStack copySlot = ItemStack.EMPTY;
		if (entity instanceof Player) {
			if (!world.getLevelData().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
				if (!(entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getItem() == ItemStack.EMPTY.getItem())) {
					if ((entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("attuned_player")).equals(entity.getDisplayName().getString())) {
						if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getDamageValue() + PokerelicsModVariables.attuned_death_damage >= entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getMaxDamage()) {
							if (entity instanceof Player _player && !_player.level().isClientSide())
								_player.displayClientMessage(Component.literal(("\u00A7cYour " + (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getDisplayName().getString() + " has broken!\u00A7r"))), false);
						} else {
							if (world instanceof ServerLevel _level) {
								entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.hurtAndBreak(5, _level, null, _stkprov -> {
								});
							}
							if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getRarity() == Rarity.COMMON) {
								if (entity instanceof Player _player && !_player.level().isClientSide())
									_player.displayClientMessage(Component.literal(("Your " + (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getDisplayName().getString() + ""
											+ (" recieved " + (new java.text.DecimalFormat("#").format(PokerelicsModVariables.attuned_death_damage) + " damage."))))), false);
							} else if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getRarity() == Rarity.UNCOMMON) {
								if (entity instanceof Player _player && !_player.level().isClientSide())
									_player.displayClientMessage(Component.literal(("Your \u00A7e" + (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getDisplayName().getString() + ""
											+ ("\u00A7r recieved " + (new java.text.DecimalFormat("#").format(PokerelicsModVariables.attuned_death_damage) + " damage."))))), false);
							} else if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getRarity() == Rarity.RARE) {
								if (entity instanceof Player _player && !_player.level().isClientSide())
									_player.displayClientMessage(Component.literal(("Your \u00A7b" + (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getDisplayName().getString() + ""
											+ ("\u00A7r recieved " + (new java.text.DecimalFormat("#").format(PokerelicsModVariables.attuned_death_damage) + " damage."))))), false);
							} else if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getRarity() == Rarity.EPIC) {
								if (entity instanceof Player _player && !_player.level().isClientSide())
									_player.displayClientMessage(Component.literal(("Your \u00A7d" + (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getDisplayName().getString() + ""
											+ ("\u00A7r recieved " + (new java.text.DecimalFormat("#").format(PokerelicsModVariables.attuned_death_damage) + " damage."))))), false);
							}
						}
					} else {
						copySlot = entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.copy();
						{
							PokerelicsModVariables.PlayerVariables _vars = entity.getData(PokerelicsModVariables.PLAYER_VARIABLES);
							_vars.backSlot = ItemStack.EMPTY.copy();
							_vars.markSyncDirty();
						}
						if (world instanceof ServerLevel _level) {
							ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, copySlot);
							entityToSpawn.setPickUpDelay(10);
							_level.addFreshEntity(entityToSpawn);
						}
					}
				}
			}
		}
	}
}