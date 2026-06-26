package net.mcreator.pokerelics.procedures;

import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;
import net.minecraft.core.component.DataComponents;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;

import net.mcreator.pokerelics.network.PokerelicsModVariables;
import net.mcreator.pokerelics.init.PokerelicsModItems;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.DoubleArgumentType;

public class FirestreakAddProcedure {
	public static void execute(CommandContext<CommandSourceStack> arguments, Entity entity) {
		if (entity == null)
			return;
		try {
			for (Entity entityiterator : EntityArgument.getEntities(arguments, "targets")) {
				if (entityiterator.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getItem() == PokerelicsModItems.VIGOR_CANDLE.get()) {
					{
						final String _tagName = "activation_timer";
						final double _tagValue = (entityiterator.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("activation_timer")
								+ DoubleArgumentType.getDouble(arguments, "duration"));
						CustomData.update(DataComponents.CUSTOM_DATA, entityiterator.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot, tag -> tag.putDouble(_tagName, _tagValue));
					}
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(
								Component.literal(
										(("Added " + new java.text.DecimalFormat("##").format(DoubleArgumentType.getDouble(arguments, "duration"))) + "" + ((" ticks to " + entityiterator.getDisplayName().getString()) + "'s Firestreak duration "))),
								false);
				} else {
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal((("Could not add Firestreak duration for " + entityiterator.getDisplayName().getString()) + ", as they do not have the Vigor Candle equipped.")), false);
				}
			}
		} catch (CommandSyntaxException e) {
			e.printStackTrace();
		}
	}
}