/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.pokerelics.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.core.registries.Registries;

import net.mcreator.pokerelics.procedures.TelekinesisEffectExpiresProcedure;
import net.mcreator.pokerelics.potion.TerrorMobEffect;
import net.mcreator.pokerelics.potion.TelekinesisMobEffect;
import net.mcreator.pokerelics.PokerelicsMod;

@EventBusSubscriber
public class PokerelicsModMobEffects {
	public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(Registries.MOB_EFFECT, PokerelicsMod.MODID);
	public static final DeferredHolder<MobEffect, MobEffect> TELEKINESIS = REGISTRY.register("telekinesis", () -> new TelekinesisMobEffect());
	public static final DeferredHolder<MobEffect, MobEffect> TERROR = REGISTRY.register("terror", () -> new TerrorMobEffect());

	@SubscribeEvent
	public static void onEffectRemoved(MobEffectEvent.Remove event) {
		MobEffectInstance effectInstance = event.getEffectInstance();
		if (effectInstance != null) {
			expireEffects(event.getEntity(), effectInstance);
		}
	}

	@SubscribeEvent
	public static void onEffectExpired(MobEffectEvent.Expired event) {
		MobEffectInstance effectInstance = event.getEffectInstance();
		if (effectInstance != null) {
			expireEffects(event.getEntity(), effectInstance);
		}
	}

	private static void expireEffects(Entity entity, MobEffectInstance effectInstance) {
		if (effectInstance.getEffect().is(TELEKINESIS)) {
			TelekinesisEffectExpiresProcedure.execute(entity);
		}
	}
}