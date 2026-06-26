package net.mcreator.pokerelics.procedures;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.GameType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.InteractionHand;
import net.minecraft.tags.ItemTags;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.Minecraft;

import net.mcreator.pokerelics.world.inventory.StarterRelicGUIMenu;
import net.mcreator.pokerelics.network.PokerelicsModVariables;
import net.mcreator.pokerelics.init.PokerelicsModGameRules;

import io.netty.buffer.Unpooled;

public class ActivateRelicOnKeyPressedProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (world.getLevelData().getGameRules().getBoolean(PokerelicsModGameRules.SHOW_STARTER_RELIC_GUI_ON_BOOT) == true) {
			if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).has_first_relic == false) {
				if (entity instanceof ServerPlayer _ent) {
					BlockPos _bpos = BlockPos.containing(x, y, z);
					_ent.openMenu(new MenuProvider() {
						@Override
						public Component getDisplayName() {
							return Component.literal("StarterRelicGUI");
						}

						@Override
						public boolean shouldTriggerClientSideContainerClosingOnOpen() {
							return false;
						}

						@Override
						public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
							return new StarterRelicGUIMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(_bpos));
						}
					}, _bpos);
				}
			}
		}
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
						ItemStack _setstack13 = entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).lastBackSlot.copy();
						_setstack13.setCount(entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).lastBackSlot.getCount());
						_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack13);
						if (_entity instanceof Player _player)
							_player.getInventory().setChanged();
					}
				}
			} else if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Blocks.AIR.asItem()) {
				if (entity instanceof LivingEntity _entity) {
					ItemStack _setstack17 = entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.copy();
					_setstack17.setCount(entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getCount());
					_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack17);
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