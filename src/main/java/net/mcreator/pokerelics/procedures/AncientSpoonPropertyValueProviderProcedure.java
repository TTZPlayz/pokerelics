package net.mcreator.pokerelics.procedures;

import net.minecraft.world.item.ItemStack;

public class AncientSpoonPropertyValueProviderProcedure {
	public static double execute(ItemStack itemstack) {
		return itemstack.getDamageValue();
	}
}