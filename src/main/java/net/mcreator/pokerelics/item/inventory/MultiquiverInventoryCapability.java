package net.mcreator.pokerelics.item.inventory;

import net.neoforged.neoforge.items.ComponentItemHandler;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;
import net.neoforged.neoforge.common.MutableDataComponentHolder;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.component.DataComponents;

import net.mcreator.pokerelics.world.inventory.QuiverGUIMenu;
import net.mcreator.pokerelics.init.PokerelicsModItems;

import javax.annotation.Nonnull;

@EventBusSubscriber
public class MultiquiverInventoryCapability extends ComponentItemHandler {
	@SubscribeEvent
	public static void onItemDropped(ItemTossEvent event) {
		if (event.getEntity().getItem().getItem() == PokerelicsModItems.MULTIQUIVER.get()) {
			Player player = event.getPlayer();
			if (player.containerMenu instanceof QuiverGUIMenu)
				player.closeContainer();
		}
	}

	public MultiquiverInventoryCapability(MutableDataComponentHolder parent) {
		super(parent, DataComponents.CONTAINER, 10);
	}

	@Override
	public int getSlotLimit(int slot) {
		return 64;
	}

	@Override
	public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
		return stack.getItem() != PokerelicsModItems.MULTIQUIVER.get();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return super.getStackInSlot(slot).copy();
	}
}