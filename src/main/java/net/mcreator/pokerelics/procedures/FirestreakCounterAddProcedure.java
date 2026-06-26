package net.mcreator.pokerelics.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;

import net.mcreator.pokerelics.network.PokerelicsModVariables;
import net.mcreator.pokerelics.init.PokerelicsModItems;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.DoubleArgumentType;

public class FirestreakCounterAddProcedure {
	public static void execute(CommandContext<CommandSourceStack> arguments, Entity entity) {
		if (entity == null)
			return;
		try {
			for (Entity entityiterator : EntityArgument.getEntities(arguments, "targets")) {
				if (entityiterator.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getItem() == PokerelicsModItems.VIGOR_CANDLE.get()) {
					{
						PokerelicsModVariables.PlayerVariables _vars = entityiterator.getData(PokerelicsModVariables.PLAYER_VARIABLES);
						_vars.firestreak_counter = entityiterator.getData(PokerelicsModVariables.PLAYER_VARIABLES).firestreak_counter + DoubleArgumentType.getDouble(arguments, "count");
						_vars.markSyncDirty();
					}
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(
								Component
										.literal(
												(("Added " + new java.text.DecimalFormat("##").format(DoubleArgumentType.getDouble(arguments, "count"))) + ""
														+ ((" to " + entityiterator.getDisplayName().getString()) + ""
																+ ("'s Firestreak counter; their count is now " + new java.text.DecimalFormat("##").format(entityiterator.getData(PokerelicsModVariables.PLAYER_VARIABLES).firestreak_counter))))),
								false);
				} else {
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal((("Could not add Firestreak count for " + entityiterator.getDisplayName().getString()) + ", as they do not have the Vigor Candle equipped.")), false);
				}
			}
		} catch (CommandSyntaxException e) {
			e.printStackTrace();
		}
	}
}