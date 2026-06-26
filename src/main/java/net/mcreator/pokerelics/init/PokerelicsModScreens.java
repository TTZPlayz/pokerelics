/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.pokerelics.init;

import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.mcreator.pokerelics.client.gui.StarterRelicGUIScreen;
import net.mcreator.pokerelics.client.gui.QuiverGUIScreen;

@EventBusSubscriber(Dist.CLIENT)
public class PokerelicsModScreens {
	@SubscribeEvent
	public static void clientLoad(RegisterMenuScreensEvent event) {
		event.register(PokerelicsModMenus.STARTER_RELIC_GUI.get(), StarterRelicGUIScreen::new);
		event.register(PokerelicsModMenus.QUIVER_GUI.get(), QuiverGUIScreen::new);
	}

	public interface ScreenAccessor {
		void updateMenuState(int elementType, String name, Object elementState);
	}
}