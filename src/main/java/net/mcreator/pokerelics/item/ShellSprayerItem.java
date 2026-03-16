package net.mcreator.pokerelics.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class ShellSprayerItem extends Item {
	public ShellSprayerItem() {
		super(new Item.Properties().durability(256).rarity(Rarity.UNCOMMON));
	}
}