package net.mcreator.pokerelics.procedures;

import net.minecraft.world.entity.Entity;

import net.mcreator.pokerelics.network.PokerelicsModVariables;

public class ShellSprayerPropertyValueProviderProcedure {
	public static double execute(Entity entity) {
		if (entity == null)
			return 0;
		return entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).fill_level;
	}
}