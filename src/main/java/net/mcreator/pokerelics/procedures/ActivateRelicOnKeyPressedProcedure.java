package net.mcreator.pokerelics.procedures;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.GameType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionHand;
import net.minecraft.tags.ItemTags;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.Minecraft;

import net.mcreator.pokerelics.network.PokerelicsModVariables;

public class ActivateRelicOnKeyPressedProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (getEntityGameType(entity) == GameType.SURVIVAL || getEntityGameType(entity) == GameType.ADVENTURE) {
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).is(ItemTags.create(ResourceLocation.parse("pokerelics:relics")))) {
				if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getItem() == Blocks.AIR.asItem()) {
					{
						PokerelicsModVariables.PlayerVariables _vars = entity.getData(PokerelicsModVariables.PLAYER_VARIABLES);
						_vars.backSlot = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).copy();
						_vars.markSyncDirty();
					}
					(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).shrink(1);
				} else if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.is(ItemTags.create(ResourceLocation.parse("pokerelics:relics")))) {
					{
						PokerelicsModVariables.PlayerVariables _vars = entity.getData(PokerelicsModVariables.PLAYER_VARIABLES);
						_vars.lastBackSlot = entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.copy();
						_vars.backSlot = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).copy();
						_vars.markSyncDirty();
					}
					if (entity instanceof LivingEntity _entity) {
						ItemStack _setstack11 = entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).lastBackSlot.copy();
						_setstack11.setCount(entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).lastBackSlot.getCount());
						_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack11);
						if (_entity instanceof Player _player)
							_player.getInventory().setChanged();
					}
				}
			} else if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Blocks.AIR.asItem()) {
				if (entity instanceof LivingEntity _entity) {
					ItemStack _setstack15 = entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.copy();
					_setstack15.setCount(entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getCount());
					_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack15);
					if (_entity instanceof Player _player)
						_player.getInventory().setChanged();
				}
				{
					PokerelicsModVariables.PlayerVariables _vars = entity.getData(PokerelicsModVariables.PLAYER_VARIABLES);
					_vars.backSlot = new ItemStack(Blocks.AIR).copy();
					_vars.markSyncDirty();
				}
			} else {
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("Cannot replace relic"), true);
			}
		}
	}

	private static GameType getEntityGameType(Entity entity) {
		if (entity instanceof ServerPlayer serverPlayer) {
			return serverPlayer.gameMode.getGameModeForPlayer();
		} else if (entity instanceof Player player && player.level().isClientSide()) {
			PlayerInfo playerInfo = Minecraft.getInstance().getConnection().getPlayerInfo(player.getGameProfile().getId());
			if (playerInfo != null)
				return playerInfo.getGameMode();
		}
		return null;
	}
}