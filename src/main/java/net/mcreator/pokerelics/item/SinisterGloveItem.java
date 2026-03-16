package net.mcreator.pokerelics.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class SinisterGloveItem extends Item {
	public SinisterGloveItem() {
		super(new Item.Properties().durability(256).fireResistant().rarity(Rarity.RARE));
	}
}