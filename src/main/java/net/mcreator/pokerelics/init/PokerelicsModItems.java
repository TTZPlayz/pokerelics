/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.pokerelics.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.item.ItemProperties;

import net.mcreator.pokerelics.procedures.*;
import net.mcreator.pokerelics.item.inventory.MultiquiverInventoryCapability;
import net.mcreator.pokerelics.item.*;
import net.mcreator.pokerelics.PokerelicsMod;

@EventBusSubscriber
public class PokerelicsModItems {
	public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(PokerelicsMod.MODID);
	public static final DeferredItem<Item> ANCIENT_SPOON;
	public static final DeferredItem<Item> VIGOR_CANDLE;
	public static final DeferredItem<Item> BULB_OF_ABSORPTION;
	public static final DeferredItem<Item> SHELL_SPRAYER;
	public static final DeferredItem<Item> STEEL_PLATING;
	public static final DeferredItem<Item> MAGNEDRONE_SPAWN_EGG;
	public static final DeferredItem<Item> MECHANICAL_SCRAP;
	public static final DeferredItem<Item> POINT_AND_CLIK;
	public static final DeferredItem<Item> SINISTER_GLOVE;
	public static final DeferredItem<Item> HYPNOTIC_PENDANT;
	public static final DeferredItem<Item> REGAL_AMULET;
	public static final DeferredItem<Item> ELDER_SEED;
	public static final DeferredItem<Item> CINDER_WAX;
	public static final DeferredItem<Item> SHELL_FRAGMENTS;
	public static final DeferredItem<Item> DRACONIC_PEARL;
	public static final DeferredItem<Item> WINGED_WAND;
	public static final DeferredItem<Item> LUCKY_EGG;
	public static final DeferredItem<Item> MULTIQUIVER;
	static {
		ANCIENT_SPOON = REGISTRY.register("ancient_spoon", AncientSpoonItem::new);
		VIGOR_CANDLE = REGISTRY.register("vigor_candle", VigorCandleItem::new);
		BULB_OF_ABSORPTION = REGISTRY.register("bulb_of_absorption", BulbOfAbsorptionItem::new);
		SHELL_SPRAYER = REGISTRY.register("shell_sprayer", ShellSprayerItem::new);
		STEEL_PLATING = block(PokerelicsModBlocks.STEEL_PLATING);
		MAGNEDRONE_SPAWN_EGG = REGISTRY.register("magnedrone_spawn_egg", () -> new DeferredSpawnEggItem(PokerelicsModEntities.MAGNEDRONE, -3345164, -12895686, new Item.Properties()));
		MECHANICAL_SCRAP = REGISTRY.register("mechanical_scrap", MechanicalScrapItem::new);
		POINT_AND_CLIK = REGISTRY.register("point_and_clik", PointAndClikItem::new);
		SINISTER_GLOVE = REGISTRY.register("sinister_glove", SinisterGloveItem::new);
		HYPNOTIC_PENDANT = REGISTRY.register("hypnotic_pendant", HypnoticPendantItem::new);
		REGAL_AMULET = REGISTRY.register("regal_amulet", RegalAmuletItem::new);
		ELDER_SEED = REGISTRY.register("elder_seed", ElderSeedItem::new);
		CINDER_WAX = REGISTRY.register("cinder_wax", CinderWaxItem::new);
		SHELL_FRAGMENTS = REGISTRY.register("shell_fragments", ShellFragmentsItem::new);
		DRACONIC_PEARL = REGISTRY.register("draconic_pearl", DraconicPearlItem::new);
		WINGED_WAND = REGISTRY.register("winged_wand", WingedWandItem::new);
		LUCKY_EGG = REGISTRY.register("lucky_egg", LuckyEggItem::new);
		MULTIQUIVER = REGISTRY.register("multiquiver", MultiquiverItem::new);
	}

	// Start of user code block custom items
	// End of user code block custom items
	@SubscribeEvent
	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerItem(Capabilities.ItemHandler.ITEM, (stack, context) -> new MultiquiverInventoryCapability(stack), MULTIQUIVER.get());
	}

	private static DeferredItem<Item> block(DeferredHolder<Block, Block> block) {
		return block(block, new Item.Properties());
	}

	private static DeferredItem<Item> block(DeferredHolder<Block, Block> block, Item.Properties properties) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), properties));
	}

	@EventBusSubscriber(Dist.CLIENT)
	public static class ItemsClientSideHandler {
		@SubscribeEvent
		@OnlyIn(Dist.CLIENT)
		public static void clientLoad(FMLClientSetupEvent event) {
			event.enqueueWork(() -> {
				ItemProperties.register(ANCIENT_SPOON.get(), ResourceLocation.parse("pokerelics:ancient_spoon_active"),
						(itemStackToRender, clientWorld, entity, itemEntityId) -> (float) AncientSpoonPropertyValueProviderActiveProcedure.execute(itemStackToRender));
				ItemProperties.register(VIGOR_CANDLE.get(), ResourceLocation.parse("pokerelics:vigor_candle_activation_timer"),
						(itemStackToRender, clientWorld, entity, itemEntityId) -> (float) VigorCandlePropertyValueProviderProcedure.execute(itemStackToRender));
				ItemProperties.register(BULB_OF_ABSORPTION.get(), ResourceLocation.parse("pokerelics:bulb_of_absorption_activated"),
						(itemStackToRender, clientWorld, entity, itemEntityId) -> (float) BulbOfAbsorptionPropertyValueProviderProcedure.execute(itemStackToRender));
				ItemProperties.register(SHELL_SPRAYER.get(), ResourceLocation.parse("pokerelics:shell_sprayer_fill_level"),
						(itemStackToRender, clientWorld, entity, itemEntityId) -> (float) ShellSprayerPropertyValueProviderProcedure.execute(itemStackToRender));
				ItemProperties.register(POINT_AND_CLIK.get(), ResourceLocation.parse("pokerelics:point_and_clik_activation"),
						(itemStackToRender, clientWorld, entity, itemEntityId) -> (float) PointAndClikPropertyValueProviderProcedure.execute(itemStackToRender));
				ItemProperties.register(SINISTER_GLOVE.get(), ResourceLocation.parse("pokerelics:sinister_glove_active"),
						(itemStackToRender, clientWorld, entity, itemEntityId) -> (float) SinisterGlovePropertyValueProviderProcedure.execute(itemStackToRender));
				ItemProperties.register(HYPNOTIC_PENDANT.get(), ResourceLocation.parse("pokerelics:hypnotic_pendant_active"),
						(itemStackToRender, clientWorld, entity, itemEntityId) -> (float) HypnoticPendantPropertyValueProviderActiveProcedure.execute(itemStackToRender));
				ItemProperties.register(MULTIQUIVER.get(), ResourceLocation.parse("pokerelics:multiquiver_arrows"),
						(itemStackToRender, clientWorld, entity, itemEntityId) -> (float) MultiquiverPropertyValueProviderProcedure.execute(itemStackToRender));
			});
		}
	}
}