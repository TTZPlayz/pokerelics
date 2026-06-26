/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.pokerelics.init;

import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.mcreator.pokerelics.client.model.*;

@EventBusSubscriber(Dist.CLIENT)
public class PokerelicsModModels {
	@SubscribeEvent
	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(SinisterGloveModel.LAYER_LOCATION, SinisterGloveModel::createBodyLayer);
		event.registerLayerDefinition(ShellSprayer.LAYER_LOCATION, ShellSprayer::createBodyLayer);
		event.registerLayerDefinition(ShellSprayer_Converted_2.LAYER_LOCATION, ShellSprayer_Converted_2::createBodyLayer);
		event.registerLayerDefinition(ModelVigor_Candle_Converted.LAYER_LOCATION, ModelVigor_Candle_Converted::createBodyLayer);
		event.registerLayerDefinition(ShellSprayer_Converted_3.LAYER_LOCATION, ShellSprayer_Converted_3::createBodyLayer);
		event.registerLayerDefinition(ShellSprayer_Converted_0.LAYER_LOCATION, ShellSprayer_Converted_0::createBodyLayer);
		event.registerLayerDefinition(ModelLuckyEgg_Converted.LAYER_LOCATION, ModelLuckyEgg_Converted::createBodyLayer);
		event.registerLayerDefinition(ShellSprayer_Converted_1.LAYER_LOCATION, ShellSprayer_Converted_1::createBodyLayer);
		event.registerLayerDefinition(BulbOfAbsorption.LAYER_LOCATION, BulbOfAbsorption::createBodyLayer);
		event.registerLayerDefinition(ModelMagnedrone.LAYER_LOCATION, ModelMagnedrone::createBodyLayer);
		event.registerLayerDefinition(RegalAmulet.LAYER_LOCATION, RegalAmulet::createBodyLayer);
	}
}