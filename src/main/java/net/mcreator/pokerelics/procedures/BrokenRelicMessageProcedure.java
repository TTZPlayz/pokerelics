package net.mcreator.pokerelics.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

public class BrokenRelicMessageProcedure {
	public static boolean execute(Entity user, ItemStack relic) {
		if (user == null)
			return false;
		if (relic.getDamageValue() == relic.getMaxDamage() - 1) {
			if (user instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal("This relic is too damaged to use."), true);
			return true;
		}
		return false;
	}
}