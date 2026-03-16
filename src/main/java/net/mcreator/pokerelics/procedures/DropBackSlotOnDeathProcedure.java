package net.mcreator.pokerelics.procedures;

import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;

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
				if (!(ItemStack.EMPTY.getItem() == entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getItem())) {
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