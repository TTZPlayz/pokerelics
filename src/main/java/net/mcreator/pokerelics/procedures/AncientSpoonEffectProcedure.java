package net.mcreator.pokerelics.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.BlockPos;

import net.mcreator.pokerelics.init.PokerelicsModMobEffects;

import java.util.Comparator;

public class AncientSpoonEffectProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		Entity spoon_marked_entity = null;
		Entity spoon_user = null;
		double cursorX = 0;
		double cursorY = 0;
		double cursorZ = 0;
		double dx = 0;
		double dy = 0;
		double dz = 0;
		double distance = 0;
		double forceX = 0;
		double forceY = 0;
		double forceZ = 0;
		double currentVX = 0;
		double currentVY = 0;
		double currentVZ = 0;
		double newVX = 0;
		double newVY = 0;
		double newVZ = 0;
		double totalSpeed = 0;
		boolean activated = false;
		if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBoolean("spoon_active") == true) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.beacon.ambient")), SoundSource.NEUTRAL, (float) 0.05, 1);
				} else {
					_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.beacon.ambient")), SoundSource.NEUTRAL, (float) 0.05, 1, false);
				}
			}
			if (BrokenRelicMessageProcedure.execute(entity, itemstack) == false) {
				cursorX = entity.level().clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(2)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getBlockPos().getX();
				cursorY = entity.level().clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(2)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getBlockPos().getY();
				cursorZ = entity.level().clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(2)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getBlockPos().getZ();
				{
					final Vec3 _center = new Vec3(cursorX, cursorY, cursorZ);
					for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(3 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList()) {
						if (!(entityiterator == entity)) {
							dx = cursorX - entityiterator.getX();
							dy = cursorY - entityiterator.getY();
							dz = cursorZ - entityiterator.getZ();
							distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2) + Math.pow(dz, 2));
							if (distance > 0.1) {
								forceX = (dx / distance) * 0.15;
								forceY = (dy / distance) * 0.15 + 0.12;
								forceZ = (dz / distance) * 0.15;
							} else {
								forceX = 0;
								forceY = 0.12;
								forceZ = 0;
							}
							currentVX = entityiterator.getDeltaMovement().x();
							currentVY = entityiterator.getDeltaMovement().y();
							currentVZ = entityiterator.getDeltaMovement().z();
							newVX = currentVX * 0.8 + forceX;
							newVY = currentVY * 0.8 + forceY;
							newVZ = currentVZ * 0.8 + forceZ;
							totalSpeed = Math.sqrt(Math.pow(newVX, 2) + Math.pow(newVY, 2) + Math.pow(newVZ, 2));
							if (totalSpeed > 0.7) {
								newVX = (newVX / totalSpeed) * 0.7;
								newVY = (newVY / totalSpeed) * 0.7;
								newVZ = (newVZ / totalSpeed) * 0.7;
							}
							entityiterator.setDeltaMovement(new Vec3(newVX, newVY, newVZ));
							if (entityiterator instanceof LivingEntity) {
								if (!(entityiterator instanceof LivingEntity _livEnt16 && _livEnt16.hasEffect(PokerelicsModMobEffects.TELEKINESIS))) {
									if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
										_entity.addEffect(new MobEffectInstance(PokerelicsModMobEffects.TELEKINESIS, 20, 1, false, false));
								}
								if ((entityiterator instanceof LivingEntity _livEnt && _livEnt.hasEffect(PokerelicsModMobEffects.TELEKINESIS) ? _livEnt.getEffect(PokerelicsModMobEffects.TELEKINESIS).getDuration() : 0) <= 1) {
									if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
										_entity.addEffect(new MobEffectInstance(PokerelicsModMobEffects.TELEKINESIS, 20, 1, true, false));
								}
							}
						}
						activated = true;
					}
				}
				if (activated == true) {
					{
						final String _tagName = "spoon_time";
						final double _tagValue = (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("spoon_time") + 1);
						CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
					}
					activated = false;
				}
				if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("spoon_time") == 20) {
					{
						final String _tagName = "spoon_time";
						final double _tagValue = 0;
						CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
					}
					if (world instanceof ServerLevel _level) {
						itemstack.hurtAndBreak(1, _level, null, _stkprov -> {
						});
					}
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.beacon.ambient")), SoundSource.NEUTRAL, (float) 0.25, 1);
					} else {
						_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.beacon.ambient")), SoundSource.NEUTRAL, (float) 0.25, 1, false);
					}
				}
			} else {
				{
					final String _tagName = "spoon_active";
					final boolean _tagValue = false;
					CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putBoolean(_tagName, _tagValue));
				}
			}
		}
	}
}