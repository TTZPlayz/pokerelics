package net.mcreator.pokerelics.init;

import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.Minecraft;

import net.mcreator.pokerelics.client.model.SinisterGloveModel;
import net.mcreator.pokerelics.client.model.ShellSprayer;
import net.mcreator.pokerelics.client.model.RegalAmulet;
import net.mcreator.pokerelics.client.model.ModelVigor_Candle_Converted;
import net.mcreator.pokerelics.client.model.BulbOfAbsorption;

import java.util.Map;
import java.util.Collections;

@EventBusSubscriber(Dist.CLIENT)
public class PokerelicsModHumanoidModels {
	public static PlayerModel BULB_MODEL;
	public static PlayerModel CANDLE_MODEL;
	public static PlayerModel SPRAYER_MODEL;
	public static PlayerModel GLOVE_MODEL;
	public static PlayerModel AMULET_MODEL;

	@SubscribeEvent
	public static void initModels(EntityRenderersEvent.AddLayers event) {
		BulbOfAbsorption bulb_model_temp = new BulbOfAbsorption(Minecraft.getInstance().getEntityModels().bakeLayer(BulbOfAbsorption.LAYER_LOCATION));
		BULB_MODEL = new PlayerModel(new ModelPart(Collections.emptyList(),
				Map.ofEntries(Map.entry("hat", new ModelPart(Collections.emptyList(), Collections.emptyMap())), Map.entry("head", new ModelPart(Collections.emptyList(), Collections.emptyMap())), Map.entry("body", bulb_model_temp.group),
						Map.entry("left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap())), Map.entry("right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap())),
						Map.entry("left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap())), Map.entry("right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap())),
						Map.entry("left_sleeve", new ModelPart(Collections.emptyList(), Collections.emptyMap())), Map.entry("right_sleeve", new ModelPart(Collections.emptyList(), Collections.emptyMap())),
						Map.entry("left_pants", new ModelPart(Collections.emptyList(), Collections.emptyMap())), Map.entry("right_pants", new ModelPart(Collections.emptyList(), Collections.emptyMap())),
						Map.entry("jacket", new ModelPart(Collections.emptyList(), Collections.emptyMap())), Map.entry("cloak", new ModelPart(Collections.emptyList(), Collections.emptyMap())),
						Map.entry("ear", new ModelPart(Collections.emptyList(), Collections.emptyMap())))),
				false);
		ModelVigor_Candle_Converted candle_model_temp = new ModelVigor_Candle_Converted(Minecraft.getInstance().getEntityModels().bakeLayer(ModelVigor_Candle_Converted.LAYER_LOCATION));
		CANDLE_MODEL = new PlayerModel(new ModelPart(Collections.emptyList(),
				Map.ofEntries(Map.entry("hat", new ModelPart(Collections.emptyList(), Collections.emptyMap())), Map.entry("head", new ModelPart(Collections.emptyList(), Collections.emptyMap())), Map.entry("body", candle_model_temp.group),
						Map.entry("left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap())), Map.entry("right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap())),
						Map.entry("left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap())), Map.entry("right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap())),
						Map.entry("left_sleeve", new ModelPart(Collections.emptyList(), Collections.emptyMap())), Map.entry("right_sleeve", new ModelPart(Collections.emptyList(), Collections.emptyMap())),
						Map.entry("left_pants", new ModelPart(Collections.emptyList(), Collections.emptyMap())), Map.entry("right_pants", new ModelPart(Collections.emptyList(), Collections.emptyMap())),
						Map.entry("jacket", new ModelPart(Collections.emptyList(), Collections.emptyMap())), Map.entry("cloak", new ModelPart(Collections.emptyList(), Collections.emptyMap())),
						Map.entry("ear", new ModelPart(Collections.emptyList(), Collections.emptyMap())))),
				false);
		ShellSprayer sprayer_model_temp = new ShellSprayer(Minecraft.getInstance().getEntityModels().bakeLayer(ShellSprayer.LAYER_LOCATION));
		SPRAYER_MODEL = new PlayerModel(new ModelPart(Collections.emptyList(),
				Map.ofEntries(Map.entry("hat", new ModelPart(Collections.emptyList(), Collections.emptyMap())), Map.entry("head", new ModelPart(Collections.emptyList(), Collections.emptyMap())),
						Map.entry("body", new ModelPart(Collections.emptyList(), Collections.emptyMap())), Map.entry("left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap())), Map.entry("right_arm", sprayer_model_temp.group),
						Map.entry("left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap())), Map.entry("right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap())),
						Map.entry("left_sleeve", new ModelPart(Collections.emptyList(), Collections.emptyMap())), Map.entry("right_sleeve", new ModelPart(Collections.emptyList(), Collections.emptyMap())),
						Map.entry("left_pants", new ModelPart(Collections.emptyList(), Collections.emptyMap())), Map.entry("right_pants", new ModelPart(Collections.emptyList(), Collections.emptyMap())),
						Map.entry("jacket", new ModelPart(Collections.emptyList(), Collections.emptyMap())), Map.entry("cloak", new ModelPart(Collections.emptyList(), Collections.emptyMap())),
						Map.entry("ear", new ModelPart(Collections.emptyList(), Collections.emptyMap())))),
				false);
		SinisterGloveModel glove_model_temp = new SinisterGloveModel(Minecraft.getInstance().getEntityModels().bakeLayer(SinisterGloveModel.LAYER_LOCATION));
		GLOVE_MODEL = new PlayerModel(new ModelPart(Collections.emptyList(),
				Map.ofEntries(Map.entry("hat", new ModelPart(Collections.emptyList(), Collections.emptyMap())), Map.entry("head", new ModelPart(Collections.emptyList(), Collections.emptyMap())),
						Map.entry("body", new ModelPart(Collections.emptyList(), Collections.emptyMap())), Map.entry("left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap())), Map.entry("right_arm", glove_model_temp.bone),
						Map.entry("left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap())), Map.entry("right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap())),
						Map.entry("left_sleeve", new ModelPart(Collections.emptyList(), Collections.emptyMap())), Map.entry("right_sleeve", new ModelPart(Collections.emptyList(), Collections.emptyMap())),
						Map.entry("left_pants", new ModelPart(Collections.emptyList(), Collections.emptyMap())), Map.entry("right_pants", new ModelPart(Collections.emptyList(), Collections.emptyMap())),
						Map.entry("jacket", new ModelPart(Collections.emptyList(), Collections.emptyMap())), Map.entry("cloak", new ModelPart(Collections.emptyList(), Collections.emptyMap())),
						Map.entry("ear", new ModelPart(Collections.emptyList(), Collections.emptyMap())))),
				false);
		RegalAmulet amulet_model_temp = new RegalAmulet(Minecraft.getInstance().getEntityModels().bakeLayer(RegalAmulet.LAYER_LOCATION));
		AMULET_MODEL = new PlayerModel(new ModelPart(Collections.emptyList(),
				Map.ofEntries(Map.entry("hat", new ModelPart(Collections.emptyList(), Collections.emptyMap())), Map.entry("head", new ModelPart(Collections.emptyList(), Collections.emptyMap())), Map.entry("body", amulet_model_temp.bone),
						Map.entry("left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap())), Map.entry("right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap())),
						Map.entry("left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap())), Map.entry("right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap())),
						Map.entry("left_sleeve", new ModelPart(Collections.emptyList(), Collections.emptyMap())), Map.entry("right_sleeve", new ModelPart(Collections.emptyList(), Collections.emptyMap())),
						Map.entry("left_pants", new ModelPart(Collections.emptyList(), Collections.emptyMap())), Map.entry("right_pants", new ModelPart(Collections.emptyList(), Collections.emptyMap())),
						Map.entry("jacket", new ModelPart(Collections.emptyList(), Collections.emptyMap())), Map.entry("cloak", new ModelPart(Collections.emptyList(), Collections.emptyMap())),
						Map.entry("ear", new ModelPart(Collections.emptyList(), Collections.emptyMap())))),
				false);
	}
}