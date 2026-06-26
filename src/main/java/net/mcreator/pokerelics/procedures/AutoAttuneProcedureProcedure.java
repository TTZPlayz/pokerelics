package net.mcreator.pokerelics.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.ItemTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.AdvancementHolder;

import net.mcreator.pokerelics.network.PokerelicsModVariables;
import net.mcreator.pokerelics.init.PokerelicsModItems;
import net.mcreator.pokerelics.init.PokerelicsModGameRules;

public class AutoAttuneProcedureProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		boolean attuned = false;
		if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).has_first_relic == false) {
			if (world.getLevelData().getGameRules().getBoolean(PokerelicsModGameRules.AUTO_ATTUNE) == true) {
				{
					PokerelicsModVariables.PlayerVariables _vars = entity.getData(PokerelicsModVariables.PLAYER_VARIABLES);
					_vars.attuned_item = itemstack.copy();
					_vars.markSyncDirty();
				}
				{
					final String _tagName = "attuned_player";
					final String _tagValue = (entity.getDisplayName().getString());
					CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putString(_tagName, _tagValue));
				}
				attuned = true;
				if (attuned == true) {
					if (itemstack.getRarity() == Rarity.COMMON) {
						if (entity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal(("You are now attuned to the " + (itemstack.getDisplayName().getString() + "!"))), true);
					} else if (itemstack.getRarity() == Rarity.UNCOMMON) {
						if (entity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal(("You are now attuned to the \u00A7e" + (itemstack.getDisplayName().getString() + "\u00A7r!"))), true);
					} else if (itemstack.getRarity() == Rarity.RARE) {
						if (entity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal(("You are now attuned to the \u00A7b" + (itemstack.getDisplayName().getString() + "\u00A7r!"))), true);
					} else if (itemstack.getRarity() == Rarity.EPIC) {
						if (entity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal(("You are now attuned to the \u00A7d" + (itemstack.getDisplayName().getString() + "\u00A7r!"))), true);
					}
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.note_block.chime")), SoundSource.NEUTRAL, 1, 1);
						} else {
							_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.note_block.chime")), SoundSource.NEUTRAL, 1, 1, false);
						}
					}
				} else {
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal("\u00A7cCould not attune.\u00A7r"), true);
				}
			}
			if (!(entity instanceof ServerPlayer _plr27 && _plr27.level() instanceof ServerLevel && _plr27.getAdvancements().getOrStartProgress(_plr27.server.getAdvancements().get(ResourceLocation.parse("pokerelics:i_choose_you"))).isDone())) {
				if (entity instanceof ServerPlayer _player) {
					AdvancementHolder _adv = _player.server.getAdvancements().get(ResourceLocation.parse("pokerelics:i_choose_you"));
					if (_adv != null) {
						AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
						if (!_ap.isDone()) {
							for (String criteria : _ap.getRemainingCriteria())
								_player.getAdvancements().award(_adv, criteria);
						}
					}
				}
			}
			{
				PokerelicsModVariables.PlayerVariables _vars = entity.getData(PokerelicsModVariables.PLAYER_VARIABLES);
				_vars.has_first_relic = true;
				_vars.markSyncDirty();
			}
		}
		if (itemstack.is(ItemTags.create(ResourceLocation.parse("pokerelics:starter_relics")))) {
			if (itemstack.getItem() == PokerelicsModItems.BULB_OF_ABSORPTION.get()) {
				if (!(entity instanceof ServerPlayer _plr33 && _plr33.level() instanceof ServerLevel && _plr33.getAdvancements().getOrStartProgress(_plr33.server.getAdvancements().get(ResourceLocation.parse("pokerelics:leaf_me_alone"))).isDone())) {
					if (entity instanceof ServerPlayer _player) {
						AdvancementHolder _adv = _player.server.getAdvancements().get(ResourceLocation.parse("pokerelics:leaf_me_alone"));
						if (_adv != null) {
							AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
							if (!_ap.isDone()) {
								for (String criteria : _ap.getRemainingCriteria())
									_player.getAdvancements().award(_adv, criteria);
							}
						}
					}
				}
			} else if (itemstack.getItem() == PokerelicsModItems.VIGOR_CANDLE.get()) {
				if (!(entity instanceof ServerPlayer _plr37 && _plr37.level() instanceof ServerLevel
						&& _plr37.getAdvancements().getOrStartProgress(_plr37.server.getAdvancements().get(ResourceLocation.parse("pokerelics:playing_with_fire"))).isDone())) {
					if (entity instanceof ServerPlayer _player) {
						AdvancementHolder _adv = _player.server.getAdvancements().get(ResourceLocation.parse("pokerelics:playing_with_fire"));
						if (_adv != null) {
							AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
							if (!_ap.isDone()) {
								for (String criteria : _ap.getRemainingCriteria())
									_player.getAdvancements().award(_adv, criteria);
							}
						}
					}
				}
			} else if (itemstack.getItem() == PokerelicsModItems.SHELL_SPRAYER.get()) {
				if (!(entity instanceof ServerPlayer _plr41 && _plr41.level() instanceof ServerLevel && _plr41.getAdvancements().getOrStartProgress(_plr41.server.getAdvancements().get(ResourceLocation.parse("pokerelics:shell_shocked"))).isDone())) {
					if (entity instanceof ServerPlayer _player) {
						AdvancementHolder _adv = _player.server.getAdvancements().get(ResourceLocation.parse("pokerelics:shell_shocked"));
						if (_adv != null) {
							AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
							if (!_ap.isDone()) {
								for (String criteria : _ap.getRemainingCriteria())
									_player.getAdvancements().award(_adv, criteria);
							}
						}
					}
				}
			}
			if (entity instanceof ServerPlayer _plr43 && _plr43.level() instanceof ServerLevel && _plr43.getAdvancements().getOrStartProgress(_plr43.server.getAdvancements().get(ResourceLocation.parse("pokerelics:leaf_me_alone"))).isDone()
					&& entity instanceof ServerPlayer _plr44 && _plr44.level() instanceof ServerLevel && _plr44.getAdvancements().getOrStartProgress(_plr44.server.getAdvancements().get(ResourceLocation.parse("pokerelics:playing_with_fire"))).isDone()
					&& entity instanceof ServerPlayer _plr45 && _plr45.level() instanceof ServerLevel && _plr45.getAdvancements().getOrStartProgress(_plr45.server.getAdvancements().get(ResourceLocation.parse("pokerelics:shell_shocked"))).isDone()) {
				if (entity instanceof ServerPlayer _player) {
					AdvancementHolder _adv = _player.server.getAdvancements().get(ResourceLocation.parse("pokerelics:i_choose_all_of_you"));
					if (_adv != null) {
						AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
						if (!_ap.isDone()) {
							for (String criteria : _ap.getRemainingCriteria())
								_player.getAdvancements().award(_adv, criteria);
						}
					}
				}
			}
		} else {
			if (itemstack.getItem() == PokerelicsModItems.ANCIENT_SPOON.get()) {
				if (!(entity instanceof ServerPlayer _plr49 && _plr49.level() instanceof ServerLevel
						&& _plr49.getAdvancements().getOrStartProgress(_plr49.server.getAdvancements().get(ResourceLocation.parse("pokerelics:bippity_boppity_boo"))).isDone())) {
					if (entity instanceof ServerPlayer _player) {
						AdvancementHolder _adv = _player.server.getAdvancements().get(ResourceLocation.parse("pokerelics:bippity_boppity_boo"));
						if (_adv != null) {
							AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
							if (!_ap.isDone()) {
								for (String criteria : _ap.getRemainingCriteria())
									_player.getAdvancements().award(_adv, criteria);
							}
						}
					}
				}
			}
			if (itemstack.getItem() == PokerelicsModItems.SINISTER_GLOVE.get()) {
				if (!(entity instanceof ServerPlayer _plr53 && _plr53.level() instanceof ServerLevel && _plr53.getAdvancements().getOrStartProgress(_plr53.server.getAdvancements().get(ResourceLocation.parse("pokerelics:spooky"))).isDone())) {
					if (entity instanceof ServerPlayer _player) {
						AdvancementHolder _adv = _player.server.getAdvancements().get(ResourceLocation.parse("pokerelics:spooky"));
						if (_adv != null) {
							AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
							if (!_ap.isDone()) {
								for (String criteria : _ap.getRemainingCriteria())
									_player.getAdvancements().award(_adv, criteria);
							}
						}
					}
				}
			}
			if (itemstack.getItem() == PokerelicsModItems.HYPNOTIC_PENDANT.get()) {
				if (!(entity instanceof ServerPlayer _plr57 && _plr57.level() instanceof ServerLevel
						&& _plr57.getAdvancements().getOrStartProgress(_plr57.server.getAdvancements().get(ResourceLocation.parse("pokerelics:youre_getting_sleepy"))).isDone())) {
					if (entity instanceof ServerPlayer _player) {
						AdvancementHolder _adv = _player.server.getAdvancements().get(ResourceLocation.parse("pokerelics:youre_getting_sleepy"));
						if (_adv != null) {
							AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
							if (!_ap.isDone()) {
								for (String criteria : _ap.getRemainingCriteria())
									_player.getAdvancements().award(_adv, criteria);
							}
						}
					}
				}
			}
			if (itemstack.getItem() == PokerelicsModItems.REGAL_AMULET.get()) {
				if (!(entity instanceof ServerPlayer _plr61 && _plr61.level() instanceof ServerLevel
						&& _plr61.getAdvancements().getOrStartProgress(_plr61.server.getAdvancements().get(ResourceLocation.parse("pokerelics:prepare_for_trouble"))).isDone())) {
					if (entity instanceof ServerPlayer _player) {
						AdvancementHolder _adv = _player.server.getAdvancements().get(ResourceLocation.parse("pokerelics:prepare_for_trouble"));
						if (_adv != null) {
							AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
							if (!_ap.isDone()) {
								for (String criteria : _ap.getRemainingCriteria())
									_player.getAdvancements().award(_adv, criteria);
							}
						}
					}
				}
			}
			if (itemstack.getItem() == PokerelicsModItems.LUCKY_EGG.get()) {
				if (!(entity instanceof ServerPlayer _plr65 && _plr65.level() instanceof ServerLevel && _plr65.getAdvancements().getOrStartProgress(_plr65.server.getAdvancements().get(ResourceLocation.parse("pokerelics:eggstra_lucky"))).isDone())) {
					if (entity instanceof ServerPlayer _player) {
						AdvancementHolder _adv = _player.server.getAdvancements().get(ResourceLocation.parse("pokerelics:eggstra_lucky"));
						if (_adv != null) {
							AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
							if (!_ap.isDone()) {
								for (String criteria : _ap.getRemainingCriteria())
									_player.getAdvancements().award(_adv, criteria);
							}
						}
					}
				}
			}
			if (itemstack.getItem() == PokerelicsModItems.MULTIQUIVER.get()) {
				if (!(entity instanceof ServerPlayer _plr69 && _plr69.level() instanceof ServerLevel && _plr69.getAdvancements().getOrStartProgress(_plr69.server.getAdvancements().get(ResourceLocation.parse("pokerelics:robin_hood"))).isDone())) {
					if (entity instanceof ServerPlayer _player) {
						AdvancementHolder _adv = _player.server.getAdvancements().get(ResourceLocation.parse("pokerelics:robin_hood"));
						if (_adv != null) {
							AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
							if (!_ap.isDone()) {
								for (String criteria : _ap.getRemainingCriteria())
									_player.getAdvancements().award(_adv, criteria);
							}
						}
					}
				}
			}
			if (itemstack.getItem() == PokerelicsModItems.WINGED_WAND.get()) {
				if (!(entity instanceof ServerPlayer _plr73 && _plr73.level() instanceof ServerLevel && _plr73.getAdvancements().getOrStartProgress(_plr73.server.getAdvancements().get(ResourceLocation.parse("pokerelics:touch_the_sky"))).isDone())) {
					if (entity instanceof ServerPlayer _player) {
						AdvancementHolder _adv = _player.server.getAdvancements().get(ResourceLocation.parse("pokerelics:touch_the_sky"));
						if (_adv != null) {
							AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
							if (!_ap.isDone()) {
								for (String criteria : _ap.getRemainingCriteria())
									_player.getAdvancements().award(_adv, criteria);
							}
						}
					}
				}
			}
			if (entity instanceof ServerPlayer _plr75 && _plr75.level() instanceof ServerLevel && _plr75.getAdvancements().getOrStartProgress(_plr75.server.getAdvancements().get(ResourceLocation.parse("pokerelics:leaf_me_alone"))).isDone()
					&& entity instanceof ServerPlayer _plr76 && _plr76.level() instanceof ServerLevel && _plr76.getAdvancements().getOrStartProgress(_plr76.server.getAdvancements().get(ResourceLocation.parse("pokerelics:playing_with_fire"))).isDone()
					&& entity instanceof ServerPlayer _plr77 && _plr77.level() instanceof ServerLevel && _plr77.getAdvancements().getOrStartProgress(_plr77.server.getAdvancements().get(ResourceLocation.parse("pokerelics:shell_shocked"))).isDone()
					&& entity instanceof ServerPlayer _plr78 && _plr78.level() instanceof ServerLevel
					&& _plr78.getAdvancements().getOrStartProgress(_plr78.server.getAdvancements().get(ResourceLocation.parse("pokerelics:bippity_boppity_boo"))).isDone() && entity instanceof ServerPlayer _plr79
					&& _plr79.level() instanceof ServerLevel && _plr79.getAdvancements().getOrStartProgress(_plr79.server.getAdvancements().get(ResourceLocation.parse("pokerelics:spooky"))).isDone() && entity instanceof ServerPlayer _plr80
					&& _plr80.level() instanceof ServerLevel && _plr80.getAdvancements().getOrStartProgress(_plr80.server.getAdvancements().get(ResourceLocation.parse("pokerelics:youre_getting_sleepy"))).isDone()
					&& entity instanceof ServerPlayer _plr81 && _plr81.level() instanceof ServerLevel
					&& _plr81.getAdvancements().getOrStartProgress(_plr81.server.getAdvancements().get(ResourceLocation.parse("pokerelics:prepare_for_trouble"))).isDone() && entity instanceof ServerPlayer _plr82
					&& _plr82.level() instanceof ServerLevel && _plr82.getAdvancements().getOrStartProgress(_plr82.server.getAdvancements().get(ResourceLocation.parse("pokerelics:eggstra_lucky"))).isDone() && entity instanceof ServerPlayer _plr83
					&& _plr83.level() instanceof ServerLevel && _plr83.getAdvancements().getOrStartProgress(_plr83.server.getAdvancements().get(ResourceLocation.parse("pokerelics:robin_hood"))).isDone() && entity instanceof ServerPlayer _plr84
					&& _plr84.level() instanceof ServerLevel && _plr84.getAdvancements().getOrStartProgress(_plr84.server.getAdvancements().get(ResourceLocation.parse("pokerelics:touch_the_sky"))).isDone()) {
				if (entity instanceof ServerPlayer _player) {
					AdvancementHolder _adv = _player.server.getAdvancements().get(ResourceLocation.parse("pokerelics:pokemon_master"));
					if (_adv != null) {
						AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
						if (!_ap.isDone()) {
							for (String criteria : _ap.getRemainingCriteria())
								_player.getAdvancements().award(_adv, criteria);
						}
					}
				}
			}
		}
		if (!(entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).attuned_item.getItem() == itemstack.getItem())
				&& (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("attuned_player")).equals(entity.getDisplayName().getString())) {
			{
				final String _tagName = "attuned_player";
				final String _tagValue = "";
				CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putString(_tagName, _tagValue));
			}
		}
	}
}