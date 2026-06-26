/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.pokerelics.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleType;

import net.mcreator.pokerelics.PokerelicsMod;

public class PokerelicsModParticleTypes {
	public static final DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister.create(Registries.PARTICLE_TYPE, PokerelicsMod.MODID);
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> TELEKINESIS_PARTICLE = REGISTRY.register("telekinesis_particle", () -> new SimpleParticleType(false));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> VIGOR_FLAME = REGISTRY.register("vigor_flame", () -> new SimpleParticleType(true));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> REGAL_SPARKLE = REGISTRY.register("regal_sparkle", () -> new SimpleParticleType(false));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> ABSORB_LEAF = REGISTRY.register("absorb_leaf", () -> new SimpleParticleType(false));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SINISTER_PARTICLE = REGISTRY.register("sinister_particle", () -> new SimpleParticleType(false));
}