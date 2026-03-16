package net.mcreator.pokerelics.procedures;

import net.minecraft.world.entity.Entity;

import net.mcreator.pokerelics.network.PokerelicsModVariables;

public class SinisterGlovePropertyValueProviderActiveProcedure {
	public static double execute(Entity entity) {
		if (entity == null)
			return 0;
		if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).glove_active) {
			return 1;
		}
		return 0;
	}
}