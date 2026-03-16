package net.mcreator.pokerelics.procedures;

import net.minecraft.world.entity.Entity;

import net.mcreator.pokerelics.network.PokerelicsModVariables;

public class AncientSpoonPropertyValueProviderActiveProcedure {
	public static double execute(Entity entity) {
		if (entity == null)
			return 0;
		if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).spoon_active) {
			return 1;
		}
		return 0;
	}
}