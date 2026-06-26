/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.pokerelics.init;

import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.GameRules;

@EventBusSubscriber
public class PokerelicsModGameRules {
	public static GameRules.Key<GameRules.BooleanValue> AUTO_ATTUNE;
	public static GameRules.Key<GameRules.BooleanValue> SHOW_STARTER_RELIC_GUI_ON_BOOT;

	@SubscribeEvent
	public static void registerGameRules(FMLCommonSetupEvent event) {
		AUTO_ATTUNE = GameRules.register("autoAttune", GameRules.Category.PLAYER, GameRules.BooleanValue.create(false));
		SHOW_STARTER_RELIC_GUI_ON_BOOT = GameRules.register("showStarterRelicGUIOnBoot", GameRules.Category.PLAYER, GameRules.BooleanValue.create(false));
	}
}