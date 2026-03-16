/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.pokerelics.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

import net.mcreator.pokerelics.PokerelicsMod;

public class PokerelicsModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PokerelicsMod.MODID);
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> POKERELICS = REGISTRY.register("pokerelics",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.pokerelics.pokerelics")).icon(() -> new ItemStack(PokerelicsModItems.ANCIENT_SPOON.get())).displayItems((parameters, tabData) -> {
				tabData.accept(PokerelicsModItems.ANCIENT_SPOON.get());
				tabData.accept(PokerelicsModItems.VIGOR_CANDLE.get());
				tabData.accept(PokerelicsModItems.BULB_OF_ABSORPTION.get());
				tabData.accept(PokerelicsModItems.SHELL_SPRAYER.get());
				tabData.accept(PokerelicsModItems.POINT_AND_CLIK.get());
				tabData.accept(PokerelicsModItems.SINISTER_GLOVE.get());
				tabData.accept(PokerelicsModItems.HYPNOTIC_PENDANT.get());
				tabData.accept(PokerelicsModItems.REGAL_AMULET.get());
			}).build());
}