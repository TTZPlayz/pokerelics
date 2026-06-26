package net.mcreator.pokerelics.procedures;

import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.component.DataComponents;

import net.mcreator.pokerelics.network.PokerelicsModVariables;

public class SinisterGlovePropertyValueProviderProcedure {
	public static double execute(ItemStack itemstack) {
		if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("activation_cooldown") >= PokerelicsModVariables.max_glove_cooldown - 5) {
			return 1;
		}
		return 0;
	}
}