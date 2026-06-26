/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.pokerelics.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;

import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.registries.Registries;
import net.minecraft.client.Minecraft;

import net.mcreator.pokerelics.world.inventory.StarterRelicGUIMenu;
import net.mcreator.pokerelics.world.inventory.QuiverGUIMenu;
import net.mcreator.pokerelics.network.MenuStateUpdateMessage;
import net.mcreator.pokerelics.PokerelicsMod;

import java.util.Map;

public class PokerelicsModMenus {
	public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(Registries.MENU, PokerelicsMod.MODID);
	public static final DeferredHolder<MenuType<?>, MenuType<StarterRelicGUIMenu>> STARTER_RELIC_GUI = REGISTRY.register("starter_relic_gui", () -> IMenuTypeExtension.create(StarterRelicGUIMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<QuiverGUIMenu>> QUIVER_GUI = REGISTRY.register("quiver_gui", () -> IMenuTypeExtension.create(QuiverGUIMenu::new));

	public interface MenuAccessor {
		Map<String, Object> getMenuState();

		Map<Integer, Slot> getSlots();

		default void sendMenuStateUpdate(Player player, int elementType, String name, Object elementState, boolean needClientUpdate) {
			getMenuState().put(elementType + ":" + name, elementState);
			if (player instanceof ServerPlayer serverPlayer) {
				PacketDistributor.sendToPlayer(serverPlayer, new MenuStateUpdateMessage(elementType, name, elementState));
			} else if (player.level().isClientSide) {
				if (Minecraft.getInstance().screen instanceof PokerelicsModScreens.ScreenAccessor accessor && needClientUpdate)
					accessor.updateMenuState(elementType, name, elementState);
				PacketDistributor.sendToServer(new MenuStateUpdateMessage(elementType, name, elementState));
			}
		}

		default <T> T getMenuState(int elementType, String name, T defaultValue) {
			try {
				return (T) getMenuState().getOrDefault(elementType + ":" + name, defaultValue);
			} catch (ClassCastException e) {
				return defaultValue;
			}
		}
	}
}