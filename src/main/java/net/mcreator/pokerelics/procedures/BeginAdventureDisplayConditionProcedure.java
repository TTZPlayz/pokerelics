package net.mcreator.pokerelics.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.Entity;

import net.mcreator.pokerelics.network.PokerelicsModVariables;

public class BeginAdventureDisplayConditionProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (!(entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).chosen_relic.getItem() == ItemStack.EMPTY.getItem())) {
			return true;
		}
		return false;
	}
}