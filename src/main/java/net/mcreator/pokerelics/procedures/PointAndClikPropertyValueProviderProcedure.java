package net.mcreator.pokerelics.procedures;

import net.minecraft.world.entity.Entity;

import net.mcreator.pokerelics.network.PokerelicsModVariables;

public class PointAndClikPropertyValueProviderProcedure {
	public static double execute(Entity entity) {
		if (entity == null)
			return 0;
		if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).point_and_clik_cooldown == 6000) {
			return 2;
		} else if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).point_and_clik_cooldown > 0) {
			return 0;
		}
		return 1;
	}
}