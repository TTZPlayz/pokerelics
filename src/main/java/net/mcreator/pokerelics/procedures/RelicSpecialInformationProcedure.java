package net.mcreator.pokerelics.procedures;

import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.Screen;

import net.mcreator.pokerelics.init.PokerelicsModItems;

import javax.annotation.Nullable;

import java.util.List;

@EventBusSubscriber(value = {Dist.CLIENT})
public class RelicSpecialInformationProcedure {
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onItemTooltip(ItemTooltipEvent event) {
		execute(event, event.getItemStack(), event.getToolTip());
	}

	public static void execute(ItemStack itemstack, List<Component> tooltip) {
		execute(null, itemstack, tooltip);
	}

	private static void execute(@Nullable Event event, ItemStack itemstack, List<Component> tooltip) {
		if (tooltip == null)
			return;
		if (itemstack.is(ItemTags.create(ResourceLocation.parse("pokerelics:relics")))) {
			tooltip.add(1, Component.literal("Hold [\u00A77Shift\u00A7r] to see description"));
			if (Screen.hasShiftDown()) {
				tooltip.remove(1);
				if (itemstack.getDamageValue() == 0) {
					tooltip.add(1, Component.literal((("Durability: \u00A7a" + new java.text.DecimalFormat("###").format(itemstack.getMaxDamage())) + "" + ("\u00A7r / " + new java.text.DecimalFormat("###").format(itemstack.getMaxDamage())))));
				} else if (itemstack.getDamageValue() < itemstack.getMaxDamage() / 2d) {
					tooltip.add(1, Component
							.literal((("Durability: " + new java.text.DecimalFormat("###").format(itemstack.getMaxDamage() - itemstack.getDamageValue())) + "" + (" / " + new java.text.DecimalFormat("###").format(itemstack.getMaxDamage())))));
				} else if (itemstack.getDamageValue() <= itemstack.getMaxDamage() - itemstack.getMaxDamage() / 4d) {
					tooltip.add(1, Component.literal((("Durability: " + "\u00A7e") + ""
							+ (new java.text.DecimalFormat("###").format(itemstack.getMaxDamage() - itemstack.getDamageValue()) + "" + ("\u00A7r / " + new java.text.DecimalFormat("###").format(itemstack.getMaxDamage()))))));
				} else if (itemstack.getDamageValue() <= itemstack.getMaxDamage() - itemstack.getMaxDamage() / 8d) {
					tooltip.add(1, Component.literal((("Durability: " + "\u00A76") + ""
							+ (new java.text.DecimalFormat("###").format(itemstack.getMaxDamage() - itemstack.getDamageValue()) + "" + ("\u00A7r / " + new java.text.DecimalFormat("###").format(itemstack.getMaxDamage()))))));
				} else {
					tooltip.add(1, Component.literal((("Durability: " + "\u00A7c") + ""
							+ (new java.text.DecimalFormat("###").format(itemstack.getMaxDamage() - itemstack.getDamageValue()) + "" + ("\u00A7r / " + new java.text.DecimalFormat("###").format(itemstack.getMaxDamage()))))));
				}
				if (itemstack.is(ItemTags.create(ResourceLocation.parse("pokerelics:active_relics")))) {
					tooltip.add(2, Component.literal("\u00A77\u00A7o\u00A7lActive Relic\u00A7r"));
				} else {
					tooltip.add(2, Component.literal("\u00A77\u00A7oPassive Relic\u00A7r"));
				}
				if (itemstack.getItem() == PokerelicsModItems.BULB_OF_ABSORPTION.get()) {
					tooltip.add(3, Component.literal("\u00A7oPassively increases the player\u2019s hunger bar and saturation when worn in sunlight.\u00A7r"));
					tooltip.add(4, Component.literal("\u00A77\u00A7o\"Photosynthesis...\"\u00A7r"));
				}
				if (itemstack.getItem() == PokerelicsModItems.VIGOR_CANDLE.get()) {
					tooltip.add(3, Component.literal("\u00A7oHas a 20% chance to infuse your critical hits with Fire Aspect for 5 seconds at a time, and will give Flame to your bow shots when activated.\u00A7r"));
					tooltip.add(4, Component.literal("\u00A77\u00A7o\"Hot to the touch.\"\u00A7r"));
				}
				if (itemstack.getItem() == PokerelicsModItems.SHELL_SPRAYER.get()) {
					tooltip.add(3, Component.literal("\u00A7oSprays enemies with high-pressure water when attacked, dealing severe knockback.\u00A7r"));
					tooltip.add(4, Component.literal("\u00A77\u00A7o\"Water bucket..release!\"\u00A7r"));
				}
				if (itemstack.getItem() == PokerelicsModItems.ANCIENT_SPOON.get()) {
					tooltip.add(3, Component.literal("\u00A7oMakes entities levitate by holding Right-Click in their direction.\u00A7r"));
					tooltip.add(4, Component.literal("\u00A77\u00A7o\"What were they doing with this??\"\u00A7r"));
				}
				if (itemstack.getItem() == PokerelicsModItems.SINISTER_GLOVE.get()) {
					tooltip.add(3, Component.literal("\u00A7oWhen attacking from behind, has a 25% chance to stun enemies with Terror for 5 seconds.\u00A7r"));
					tooltip.add(4, Component.literal("\u00A77\u00A7o\"This glove seems\u2026sinister\u2026\"\u00A7r"));
				}
				if (itemstack.getItem() == PokerelicsModItems.HYPNOTIC_PENDANT.get()) {
					tooltip.add(3, Component.literal("\u00A7oPacifies angered neutral mobs on Right-Click.\u00A7r"));
					tooltip.add(4, Component.literal("\u00A77\u00A7o\"Forget\u2026forget it all...\"\u00A7r"));
				}
				if (itemstack.getItem() == PokerelicsModItems.REGAL_AMULET.get()) {
					tooltip.add(3, Component.literal("\u00A7oCauses killed mobs to drop special loot with a 10% chance.\u00A7r"));
					tooltip.add(4, Component.literal("\u00A77\u00A7o\"Cha-ching!\"\u00A7r"));
				}
			}
		}
	}
}