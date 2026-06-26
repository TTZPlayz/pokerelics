package net.mcreator.pokerelics.procedures;

import net.neoforged.neoforge.items.ItemHandlerHelper;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.client.Minecraft;

import net.mcreator.pokerelics.world.inventory.StarterRelicGUIMenu;
import net.mcreator.pokerelics.network.PokerelicsModVariables;

public class ExitStarterRelicGUIProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		boolean attuned = false;
		if (entity instanceof Player _plr0 && _plr0.containerMenu instanceof StarterRelicGUIMenu) {
			if (entity instanceof Player _player)
				_player.closeContainer();
			if (world.isClientSide())
				Minecraft.getInstance().gameRenderer.displayItemActivation(entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).chosen_relic);
			if (entity instanceof Player _player) {
				ItemStack _setstack = entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).chosen_relic.copy();
				_setstack.setCount(1);
				ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
			}
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.note_block.chime")), SoundSource.NEUTRAL, 1, 1);
				} else {
					_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.note_block.chime")), SoundSource.NEUTRAL, 1, 1, false);
				}
			}
		}
	}
}