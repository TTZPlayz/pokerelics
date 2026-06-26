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

public class FirestreakChanceAddProcedure {
	public static void execute(CommandContext<CommandSourceStack> arguments, Entity entity) {
		if (entity == null)
			return;
		try {
			for (Entity entityiterator : EntityArgument.getEntities(arguments, "targets")) {
				if (entityiterator.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getItem() == PokerelicsModItems.VIGOR_CANDLE.get()) {
					if (entityiterator.getData(PokerelicsModVariables.PLAYER_VARIABLES).firestreak_chance + DoubleArgumentType.getDouble(arguments, "chance") / 100 >= 1) {
						{
							PokerelicsModVariables.PlayerVariables _vars = entityiterator.getData(PokerelicsModVariables.PLAYER_VARIABLES);
							_vars.firestreak_chance = 1;
							_vars.markSyncDirty();
						}
						if (entity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal(("Set Firestreak chance to 100% (max) for " + entityiterator.getDisplayName().getString())), false);
					} else {
						{
							PokerelicsModVariables.PlayerVariables _vars = entityiterator.getData(PokerelicsModVariables.PLAYER_VARIABLES);
							_vars.firestreak_chance = entityiterator.getData(PokerelicsModVariables.PLAYER_VARIABLES).firestreak_chance + DoubleArgumentType.getDouble(arguments, "chance") / 100;
							_vars.markSyncDirty();
						}
						if (entity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(
									Component.literal((("Added " + new java.text.DecimalFormat("##").format(DoubleArgumentType.getDouble(arguments, "chance"))) + "" + (("% Firestreak chance for " + entityiterator.getDisplayName().getString()) + ""
											+ ("; current chance is now " + new java.text.DecimalFormat("##%").format(entityiterator.getData(PokerelicsModVariables.PLAYER_VARIABLES).firestreak_chance))))),
									false);
					}
				} else {
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal((("Could not add Firestreak chance for " + entityiterator.getDisplayName().getString()) + ", as they do not have the Vigor Candle equipped.")), false);
				}
			}
		} catch (CommandSyntaxException e) {
			e.printStackTrace();
		}
	}
}