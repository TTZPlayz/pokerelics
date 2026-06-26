package net.mcreator.pokerelics.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;

public class CinderWaxItem extends Item {
	public CinderWaxItem() {
		super(new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON));
	}

	@Override
	public boolean isPiglinCurrency(ItemStack stack) {
		return true;
	}
}