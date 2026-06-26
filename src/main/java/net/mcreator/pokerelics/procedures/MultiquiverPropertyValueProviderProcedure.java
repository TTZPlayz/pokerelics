package net.mcreator.pokerelics.procedures;

import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.capabilities.Capabilities;

import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;

public class MultiquiverPropertyValueProviderProcedure {
	public static double execute(ItemStack itemstack) {
		if ((getItemStackFromItemStackSlot(1, itemstack)).is(ItemTags.create(ResourceLocation.parse("minecraft:arrows")))) {
			return 1;
		}
		return 0;
	}

	private static ItemStack getItemStackFromItemStackSlot(int slotID, ItemStack itemStack) {
		IItemHandler itemHandler = itemStack.getCapability(Capabilities.ItemHandler.ITEM, null);
		if (itemHandler != null)
			return itemHandler.getStackInSlot(slotID).copy();
		return ItemStack.EMPTY;
	}
}