package net.mcreator.pokerelics.procedures;

import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.component.DataComponents;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;

import net.mcreator.pokerelics.network.PokerelicsModVariables;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;

public class RelicDeAttuneCommandProcedure {
	public static void execute(CommandContext<CommandSourceStack> arguments, Entity entity) {
		if (entity == null)
			return;
		if ((((commandParameterEntity(arguments, "player")) instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("attuned_player"))
				.equals((commandParameterEntity(arguments, "player")).getDisplayName().getString())) {
			{
				PokerelicsModVariables.PlayerVariables _vars = (commandParameterEntity(arguments, "player")).getData(PokerelicsModVariables.PLAYER_VARIABLES);
				_vars.attuned_item = ItemStack.EMPTY.copy();
				_vars.markSyncDirty();
			}
			{
				final String _tagName = "attuned_player";
				final String _tagValue = "";
				CustomData.update(DataComponents.CUSTOM_DATA, ((commandParameterEntity(arguments, "player")) instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putString(_tagName, _tagValue));
			}
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(
						Component.literal(("Deattuned from the " + ((((commandParameterEntity(arguments, "player")) instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getDisplayName().getString()) + "."))), false);
		} else if (!((((commandParameterEntity(arguments, "player")) instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("attuned_player"))
				.equals((commandParameterEntity(arguments, "player")).getDisplayName().getString()))
				&& ((commandParameterEntity(arguments, "player")) instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).is(ItemTags.create(ResourceLocation.parse("pokerelics:relics")))) {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal("You are not attuned to this Relic!"), false);
		} else {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((("\u00A7cAttuned Relic must be in main-hand of " + (commandParameterEntity(arguments, "player")).getDisplayName().getString()) + " in order to deattune.\u00A7r")), false);
		}
	}

	private static Entity commandParameterEntity(CommandContext<CommandSourceStack> arguments, String parameter) {
		try {
			return EntityArgument.getEntity(arguments, parameter);
		} catch (CommandSyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}
}