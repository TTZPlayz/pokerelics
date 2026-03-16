package net.mcreator.pokerelics.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class BulbOfAbsorptionItem extends Item {
	public BulbOfAbsorptionItem() {
		super(new Item.Properties().durability(256).rarity(Rarity.UNCOMMON));
	}
}