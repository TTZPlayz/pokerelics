package net.mcreator.pokerelics.procedures;

import net.minecraft.world.entity.Entity;

import net.mcreator.pokerelics.network.PokerelicsModVariables;

public class StarterRelicGUICandleTextProcedure {
	public static String execute(Entity entity) {
		if (entity == null)
			return "";
		return ("\u00A7eVigor Candle\u00A7r: Has a " + new java.text.DecimalFormat("##%").format(entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).firestreak_chance))
				+ " chance to infuse your critical hits with Fire Aspect for 5 seconds at a time, and will give Flame to your bow shots when activated.";
	}
}