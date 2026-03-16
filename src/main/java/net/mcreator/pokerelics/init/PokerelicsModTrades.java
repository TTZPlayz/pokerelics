/*
*	MCreator note: This file will be REGENERATED on each build.
*/
package net.mcreator.pokerelics.init;

import net.neoforged.neoforge.event.village.WandererTradesEvent;
import net.neoforged.neoforge.common.BasicItemListing;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;

@EventBusSubscriber
public class PokerelicsModTrades {
	@SubscribeEvent
	public static void registerWanderingTrades(WandererTradesEvent event) {
		event.getGenericTrades().add(new BasicItemListing(new ItemStack(Items.EMERALD, 12), new ItemStack(PokerelicsModItems.HYPNOTIC_PENDANT.get()), 10, 5, 0.05f));
	}
}