/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.pokerelics.init;

import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.mcreator.pokerelics.client.particle.TelekinesisParticleParticle;
import net.mcreator.pokerelics.client.particle.TelekinesisParticle3Particle;
import net.mcreator.pokerelics.client.particle.TelekinesisParticle2Particle;

@EventBusSubscriber(Dist.CLIENT)
public class PokerelicsModParticles {
	@SubscribeEvent
	public static void registerParticles(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(PokerelicsModParticleTypes.TELEKINESIS_PARTICLE.get(), TelekinesisParticleParticle::provider);
		event.registerSpriteSet(PokerelicsModParticleTypes.TELEKINESIS_PARTICLE_2.get(), TelekinesisParticle2Particle::provider);
		event.registerSpriteSet(PokerelicsModParticleTypes.TELEKINESIS_PARTICLE_3.get(), TelekinesisParticle3Particle::provider);
	}
}