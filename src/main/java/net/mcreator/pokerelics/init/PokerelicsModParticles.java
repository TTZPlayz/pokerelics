/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.pokerelics.init;

import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.mcreator.pokerelics.client.particle.VigorFlameParticle;
import net.mcreator.pokerelics.client.particle.TelekinesisParticleParticle;
import net.mcreator.pokerelics.client.particle.SinisterParticleParticle;
import net.mcreator.pokerelics.client.particle.RegalSparkleParticle;
import net.mcreator.pokerelics.client.particle.AbsorbLeafParticle;

@EventBusSubscriber(Dist.CLIENT)
public class PokerelicsModParticles {
	@SubscribeEvent
	public static void registerParticles(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(PokerelicsModParticleTypes.TELEKINESIS_PARTICLE.get(), TelekinesisParticleParticle::provider);
		event.registerSpriteSet(PokerelicsModParticleTypes.VIGOR_FLAME.get(), VigorFlameParticle::provider);
		event.registerSpriteSet(PokerelicsModParticleTypes.REGAL_SPARKLE.get(), RegalSparkleParticle::provider);
		event.registerSpriteSet(PokerelicsModParticleTypes.ABSORB_LEAF.get(), AbsorbLeafParticle::provider);
		event.registerSpriteSet(PokerelicsModParticleTypes.SINISTER_PARTICLE.get(), SinisterParticleParticle::provider);
	}
}