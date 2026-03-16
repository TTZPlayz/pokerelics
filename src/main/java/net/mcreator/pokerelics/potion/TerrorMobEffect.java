package net.mcreator.pokerelics.potion;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

import net.mcreator.pokerelics.procedures.TerrorOnEffectActiveTickMovementProcedure;

public class TerrorMobEffect extends MobEffect {
	public TerrorMobEffect() {
		super(MobEffectCategory.HARMFUL, -13434829);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return true;
	}

	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		TerrorOnEffectActiveTickMovementProcedure.execute(entity);
		return super.applyEffectTick(entity, amplifier);
	}
}