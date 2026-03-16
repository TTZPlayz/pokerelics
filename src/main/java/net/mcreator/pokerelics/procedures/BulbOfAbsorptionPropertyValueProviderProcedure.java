package net.mcreator.pokerelics.procedures;

import net.minecraft.world.entity.Entity;

import net.mcreator.pokerelics.network.PokerelicsModVariables;

public class BulbOfAbsorptionPropertyValueProviderProcedure {
	public static double execute(Entity entity) {
		if (entity == null)
			return 0;
		if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).time_since_heal != 0) {
			return 1;
		} else {
			return 0;
		}
	}
}