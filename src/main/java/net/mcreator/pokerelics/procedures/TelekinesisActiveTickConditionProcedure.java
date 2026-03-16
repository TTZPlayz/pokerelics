package net.mcreator.pokerelics.procedures;

import org.checkerframework.checker.units.qual.A;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.SimpleParticleType;

import net.mcreator.pokerelics.init.PokerelicsModParticleTypes;

public class TelekinesisActiveTickConditionProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		double yAmount = 0;
		double A = 0;
		double XAmount = 0;
		double zAmount = 0;
		double H = 0;
		double CenterZ = 0;
		double CenterY = 0;
		double XAng = 0;
		double CenterX = 0;
		double Strength = 0;
		double O = 0;
		if (world instanceof ServerLevel _level)
			_level.sendParticles((SimpleParticleType) (PokerelicsModParticleTypes.TELEKINESIS_PARTICLE.get()), (entity.getX()), (entity.getY() + 1), (entity.getZ()), 2, 0.25, 0.25, 0.25, 0.1);
		if (world instanceof ServerLevel _level)
			_level.sendParticles((SimpleParticleType) (PokerelicsModParticleTypes.TELEKINESIS_PARTICLE_2.get()), (entity.getX()), (entity.getY() + 1), (entity.getZ()), 2, 0.25, 0.25, 0.25, 0.1);
		if (world instanceof ServerLevel _level)
			_level.sendParticles((SimpleParticleType) (PokerelicsModParticleTypes.TELEKINESIS_PARTICLE_3.get()), (entity.getX()), (entity.getY() + 1), (entity.getZ()), 2, 0.25, 0.25, 0.25, 0.1);
		if ((entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(MobEffects.GLOWING) ? _livEnt.getEffect(MobEffects.GLOWING).getDuration() : 0) <= 1) {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 60, 1, false, false));
		}
	}
}