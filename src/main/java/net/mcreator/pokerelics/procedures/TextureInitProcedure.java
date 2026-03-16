package net.mcreator.pokerelics.procedures;

import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.resources.ResourceLocation;

import net.mcreator.pokerelics.network.PokerelicsModVariables;

import javax.annotation.Nullable;

@EventBusSubscriber(Dist.CLIENT)
public class TextureInitProcedure {
	@SubscribeEvent
	public static void afterModelRegistration(EntityRenderersEvent.AddLayers event) {
		execute(event);
	}

	public static void execute() {
		execute(null);
	}

	private static void execute(@Nullable Event event) {
		PokerelicsModVariables.bulb_texture = ResourceLocation.parse("pokerelics:textures/entities/bulb_of_absorption_model.png");
		PokerelicsModVariables.candle_texture_unlit = ResourceLocation.parse("pokerelics:textures/entities/vigor_candle_inactive_model.png");
		PokerelicsModVariables.candle_texture_lit = ResourceLocation.parse("pokerelics:textures/entities/vigor_candle_active_model.png");
		PokerelicsModVariables.sprayer_full_1 = ResourceLocation.parse("pokerelics:textures/entities/shell_sprayer_model_1.png");
		PokerelicsModVariables.sprayer_full_2 = ResourceLocation.parse("pokerelics:textures/entities/shell_sprayer_model_2.png");
		PokerelicsModVariables.sprayer_full_3 = ResourceLocation.parse("pokerelics:textures/entities/shell_sprayer_model_3.png");
		PokerelicsModVariables.sprayer_full_4 = ResourceLocation.parse("pokerelics:textures/entities/shell_sprayer_model_4.png");
		PokerelicsModVariables.sprayer_full_5 = ResourceLocation.parse("pokerelics:textures/entities/shell_sprayer_model_5.png");
		PokerelicsModVariables.sprayer_full_6 = ResourceLocation.parse("pokerelics:textures/entities/shell_sprayer_model_6.png");
		PokerelicsModVariables.sprayer_full_7 = ResourceLocation.parse("pokerelics:textures/entities/shell_sprayer_model_7.png");
		PokerelicsModVariables.sprayer_full_8 = ResourceLocation.parse("pokerelics:textures/entities/shell_sprayer_model_8.png");
		PokerelicsModVariables.sprayer_full_9 = ResourceLocation.parse("pokerelics:textures/entities/shell_sprayer_model_9.png");
		PokerelicsModVariables.sprayer_full_10 = ResourceLocation.parse("pokerelics:textures/entities/shell_sprayer_model_10.png");
		PokerelicsModVariables.sprayer_full_11 = ResourceLocation.parse("pokerelics:textures/entities/shell_sprayer_model_11.png");
		PokerelicsModVariables.sprayer_full_12 = ResourceLocation.parse("pokerelics:textures/entities/shell_sprayer_model_12.png");
		PokerelicsModVariables.sprayer_full_13 = ResourceLocation.parse("pokerelics:textures/entities/shell_sprayer_model_13.png");
		PokerelicsModVariables.sprayer_full_14 = ResourceLocation.parse("pokerelics:textures/entities/shell_sprayer_model_14.png");
		PokerelicsModVariables.regal_amulet = ResourceLocation.parse("pokerelics:textures/entities/regal_amulet_model.png");
		PokerelicsModVariables.sinister_glove = ResourceLocation.parse("pokerelics:textures/entities/sinister_glove_model.png");
	}
}