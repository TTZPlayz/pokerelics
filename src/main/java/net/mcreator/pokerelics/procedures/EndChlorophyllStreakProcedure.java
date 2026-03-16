package net.mcreator.pokerelics.procedures;

import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.component.DataComponents;

import net.mcreator.pokerelics.network.PokerelicsModVariables;

import javax.annotation.Nullable;

@EventBusSubscriber
public class EndChlorophyllStreakProcedure {
	@SubscribeEvent
	public static void onUseItemFinish(LivingEntityUseItemEvent.Finish event) {
		if (event.getEntity() != null) {
			execute(event, event.getEntity(), event.getItem());
		}
	}

	public static void execute(Entity entity, ItemStack itemstack) {
		execute(null, entity, itemstack);
	}

	private static void execute(@Nullable Event event, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		if (itemstack.has(DataComponents.FOOD)) {
			{
				PokerelicsModVariables.PlayerVariables _vars = entity.getData(PokerelicsModVariables.PLAYER_VARIABLES);
				_vars.chlorophyll_streak = 0;
				_vars.markSyncDirty();
			}
		}
	}
}