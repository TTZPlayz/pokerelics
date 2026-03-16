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
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> TELEKINESIS_PARTICLE_2 = REGISTRY.register("telekinesis_particle_2", () -> new SimpleParticleType(false));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> TELEKINESIS_PARTICLE_3 = REGISTRY.register("telekinesis_particle_3", () -> new SimpleParticleType(false));
}