package net.mcreator.pokerelics.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.Entity;

import net.mcreator.pokerelics.network.PokerelicsModVariables;
import net.mcreator.pokerelics.init.PokerelicsModItems;

public class SelectSprayerProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		{
			PokerelicsModVariables.PlayerVariables _vars = entity.getData(PokerelicsModVariables.PLAYER_VARIABLES);
			_vars.chosen_relic = new ItemStack(PokerelicsModItems.SHELL_SPRAYER.get()).copy();
			_vars.markSyncDirty();
		}
	}
}