package net.mcreator.pokerelics.potion;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

import net.mcreator.pokerelics.procedures.TelekinesisEffectStartedappliedProcedure;
import net.mcreator.pokerelics.procedures.TelekinesisActiveTickConditionProcedure;

public class TelekinesisMobEffect extends MobEffect {
	public TelekinesisMobEffect() {
		super(MobEffectCategory.NEUTRAL, -1);
	}

	@Override
	public void onEffectStarted(LivingEntity entity, int amplifier) {
		TelekinesisEffectStartedappliedProcedure.execute(entity);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return true;
	}

	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		TelekinesisActiveTickConditionProcedure.execute(entity.level(), entity);
		return super.applyEffectTick(entity, amplifier);
	}
}